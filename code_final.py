#from paddleocr import PaddleOCR,draw_ocr
import cv2
import torch

#ocr = PaddleOCR(use_angle_cls=True, lang='en')
img_path =  'D:\\Study\\NCKH\\meter\\image\\train\\meter0007.jpg'
    
# model = torch.load('ultralytics/yolov5', 'custom', path_or_model='best_YoLov5.pt') 
model = torch.load("D:\\Study\\NCKH\\best_YoLov5.pt")
# results = model(img_path)
# labels, cord_thres = results.xyxyn[0][:, -1].numpy(), results.xyxyn[0][:, :-1].numpy()
#results = model(img_path)
model.eval()
#results.print()