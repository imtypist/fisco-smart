# This PyTorch image classification example is based off
# https://www.learnopencv.com/pytorch-for-beginners-image-classification-using-pre-trained-models/

from torchvision import models
import torch
import time
import sys

print("[SMART][PYTHON][" + str(int(round(time.time()*1000))) + "] start to execute model inference")

# Load the model from a file
resnet = torch.load("resnet18-pretrained.pt")

# Prepare a transform to get the input image into a format (e.g., x,y dimensions) the classifier
# expects.
from torchvision import transforms
transform = transforms.Compose([
    transforms.Resize(256),
    transforms.CenterCrop(224),
    transforms.ToTensor(),
    transforms.Normalize(
    mean=[0.485, 0.456, 0.406],
    std=[0.229, 0.224, 0.225]
)])

# Load the image.
from PIL import Image
img = Image.open("input.jpg")

# Apply the transform to the image.
img_t = transform(img)

# Magic (not sure what this does).
batch_t = torch.unsqueeze(img_t, 0)

# Prepare the model and run the classifier.
resnet.eval()
out = resnet(batch_t)

# Load the classes from disk.
with open('classes.txt') as f:
    classes = [line.strip() for line in f.readlines()]

# Sort the predictions.
_, indices = torch.sort(out, descending=True)

# Convert into percentages.
percentage = torch.nn.functional.softmax(out, dim=1)[0] * 100

# Print the 5 most likely predictions.
# with open("result.txt", "w") as outfile:
#     outfile.write(str([(classes[idx], percentage[idx].item()) for idx in indices[0][:5]]))

# print("[SMART][PYTHON][" + str(int(round(time.time()*1000))) + "] Done. The result was written to `result.txt`.")

print("[SMART][PYTHON][" + str(int(round(time.time()*1000))) + "] start to attestation (generate quote)")

with open("/dev/attestation/user_report_data", "wb") as f:
    f.write(str([(classes[idx], percentage[idx].item()) for idx in indices[0][:5]]).encode()[:64]) # max report data len is 64B 

with open("/dev/attestation/quote", "rb") as f:
    quote = f.read()

with open("result.quote", "wb") as outfile:
    outfile.write(quote)

print("[SMART][PYTHON][" + str(int(round(time.time()*1000))) + "] Done. The result was written to `result.quote`.")