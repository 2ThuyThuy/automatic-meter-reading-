# from crypt import methods
from cv2 import destroyWindow, waitKey
import torch
from paddleocr import PaddleOCR
from PIL import Image
from flask import Flask, request, jsonify, make_response
from matplotlib import image
import numpy as np
import cv2
import io
import json



app= Flask(__name__)
global model
global ocr_model



# Encoding numpy to json
class NumpyEncoder(json.JSONEncoder):
    '''
    Encoding numpy into json
    '''
    def default(self, obj):
        if isinstance(obj, np.ndarray):
            return obj.tolist()
        if isinstance(obj, np.int32):
            return int(obj)
        if isinstance(obj, np.int64):
            return int(obj)
        if isinstance(obj, np.float32):
            return float(obj)
        if isinstance(obj, np.float64):
            return float(obj)
        return json.JSONEncoder.default(self, obj)
	



@app.route("/", methods=["GET"])
def _hello_world():
	return "Hi Thùy"

@app.route("/upload", methods=["POST"])
def upload():
	data = {"success": False}
	if request.files.get('image'):
		
		
		image  = request.files['image'].read()
		image = Image.open(io.BytesIO(image)).convert('RGB')
		cvImage = cv2.cvtColor(np.array(image), cv2.COLOR_RGB2BGR)
		print(cvImage.shape) 
		
		#predict
		results = model(cvImage)
		results.pandas().xyxy[0]
		dataImg=results.pandas().xyxy[0]
		x,y,w,h = dataImg.iloc[0,:4].values
		x,y,w,h = int(x),int(y),int(w),int(h)

		cropImg = cvImage[ y:h,x:w]
		# cv2.imshow('hehe',cropImg)
		# cv2.waitKey(0)
		# cv2.destroyAllWindows()

		strPosition = str(x)+' '+str(y)+' '+str(w)+' '+str(h)
		position, res = ocr_model.ocr(cropImg)[0]
		ans,ac = res
		print(ans, ac)
		#data["predict"] = dict("ans",ans)
		data["predict"] = {"ans":ans,"position":strPosition}
		data["success"] = True

		#ehe = make_response("Hello, World!")
		#hehe.headers['server']='ASD'
		print(jsonify({"success":str(data["success"]),"predict": str(ans),"position":strPosition}))
		#return hehe
		hehe = jsonify({"success":str(data["success"]),"predict": str(ans),"position":strPosition})
		hehe.headers['server']=str({"ans":ans,"position":strPosition})
		return hehe

		#return json.dumps(data, ensure_ascii=False)
		#return json.dumps(data, ensure_ascii=False, cls=NumpyEncoder)

		
if __name__ == "__main__":
	print("App run!")
	model = torch.hub.load('ultralytics/yolov5','custom' ,'best_YoLov5.pt',force_reload=True)
	ocr_model = PaddleOCR(lang='en')
	app.run(debug=False, host="192.168.43.252", threaded=False)