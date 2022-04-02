# from crypt import methods
from cv2 import destroyWindow, waitKey
import torch
from paddleocr import PaddleOCR
from PIL import Image
from flask import Flask, request
from matplotlib import image
import numpy as np
import cv2
import io

app= Flask(__name__)
global model
global ocr_model

@app.route("/", methods=["GET"])
def _hello_world():
	return "Hi Thùy"

@app.route("/upload", methods=["POST"])
def _upload():
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

		
		position, res = ocr_model.ocr(cropImg)[0]
		ans,ac = res
		print(ans, ac)
		return ans

		
if __name__ == "__main__":
	print("App run!")
	model = torch.hub.load('ultralytics/yolov5','custom' ,'best_YoLov5.pt',force_reload=True)
	ocr_model = PaddleOCR(lang='en')
	app.run(debug=False, host="127.0.0.1", threaded=False)