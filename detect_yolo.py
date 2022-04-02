import torch
import easyocr
model = torch.hub.load('ultralytics/yolov5','custom' ,'best_YoLov5.pt',force_reload=True)
path = 'meter1221.jpg'
results = model(path)
results.pandas().xyxy[0]
dataImg=results.pandas().xyxy[0]
x,y,w,h = dataImg.iloc[0,:4].values
print(x,y,w,h )


from PIL import Image
img = Image.open(path)
im1 = img.crop((x,y,w,h))

reader = easyocr.Reader(['en'])
res= reader.readtext(im1)
(position,ans,accuracy)= res[0]
print('ans = ',ans)

im1.show()

