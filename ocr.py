import torch
import easyocr
import os

path = 'meter1221.jpg'
model = torch.hub.load('ultralytics/yolov5','custom' ,'best_YoLov5.pt',force_reload=True)

results = model(path)
results.pandas().xyxy[0]
dataImg=results.pandas().xyxy[0]
x,y,w,h = dataImg.iloc[0,:4].values
x,y,w,h = int(x),int(y),int(w),int(h)

import cv2
img = cv2.imread(path)


cropImg = img[ y:h,x:w]

reader = easyocr.Reader(['en'])
res= reader.readtext(cropImg)
(position,ans,accuracy)= res[0]
print('ans = ',ans)


""" 
cv2.imshow("cropped", cropped_image)
cv2.waitKey(0)
cv2.destroyAllWindows()
"""