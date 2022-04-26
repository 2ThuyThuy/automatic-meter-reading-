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


model = torch.hub.load('ultralytics/yolov5','custom' ,'best_YoLov5.pt',force_reload=True)
ocr_model =  PaddleOCR(lang='en')

img = cv2.imread("phone.jpg", cv2.IMREAD_COLOR)

results = model(img)
results.pandas().xyxy[0]
dataImg=results.pandas().xyxy[0]
x,y,w,h = dataImg.iloc[0,:4].values
x,y,w,h = int(x),int(y),int(w),int(h)

cropImg = img[ y:h,x:w]

		
position, res = ocr_model.ocr(cropImg)[0]
ans,ac = res
anss = ''
for i in ans:
    if i != ' ':
        anss +=i

#print(anss, ac)

image = cv2.rectangle(img, (x, y), (w,h), (56,56,255), 5)
#cv2.putText(image, anss, (x, y-10), cv2.FONT_HERSHEY_SIMPLEX, 2, (56,56,255), 6)

scale_percent = 30 # percent of original size
width = int(image.shape[1] * scale_percent / 100)
height = int(image.shape[0] * scale_percent / 100)
dim = (width, height)

# resize image
resized = cv2.resize(image, dim, interpolation = cv2.INTER_AREA)

cv2.imshow('hehe',resized)
cv2.waitKey(0)
cv2.destroyAllWindows()