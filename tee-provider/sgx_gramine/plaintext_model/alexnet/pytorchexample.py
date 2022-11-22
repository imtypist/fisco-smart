# This PyTorch image classification example is based off
# https://www.learnopencv.com/pytorch-for-beginners-image-classification-using-pre-trained-models/

from torchvision import models
import torch
# import os
import sys
import time

print("[SMART][PYTHON][" + str(int(round(time.time()*1000))) + "] start to execute model inference")
# Load the model from a file
alexnet = torch.load("alexnet-pretrained.pt")

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
alexnet.eval()
out = alexnet(batch_t)

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

# print("Done. The result was written to `result.txt`.")

# if not os.path.exists("/dev/attestation/user_report_data"):
#     print("Cannot find `/dev/attestation/user_report_data`; "
#           "are you running under SGX, with remote attestation enabled?")
#     sys.exit(1)

# with open('/dev/attestation/attestation_type') as f:
#     print(f"Detected attestation type: {f.read()}")

print("[SMART][PYTHON][" + str(int(round(time.time()*1000))) + "] start to attestation (generate quote)")

with open("/dev/attestation/user_report_data", "wb") as f:
    f.write(str([(classes[idx], percentage[idx].item()) for idx in indices[0][:5]]).encode()[:64]) # max report data len is 64B 

with open("/dev/attestation/quote", "rb") as f:
    quote = f.read()

with open("result.quote", "wb") as outfile:
    outfile.write(quote)

print("[SMART][PYTHON][" + str(int(round(time.time()*1000))) + "] Done. The result was written to `result.quote`.")

# print(f"Extracted SGX quote with size = {len(quote)} and the following fields:")
# print(f"  ATTRIBUTES.FLAGS: {quote[96:104].hex()}  [ Debug bit: {quote[96] & 2 > 0} ]")
# print(f"  ATTRIBUTES.XFRM:  {quote[104:112].hex()}")
# print(f"  MRENCLAVE:        {quote[112:144].hex()}")
# print(f"  MRSIGNER:         {quote[176:208].hex()}")
# print(f"  ISVPRODID:        {quote[304:306].hex()}")
# print(f"  ISVSVN:           {quote[306:308].hex()}")
# print(f"  REPORTDATA:       {quote[368:400].hex()}")
# print(f"                    {quote[400:432].hex()}")
