from torchvision import models
import torch

model_name = ['alexnet', 'densenet161', 'resnet18', 'squeezenet1_0', 'vgg16']

for name in model_name:
    output_filename = name + "/" + name + "-pretrained.pt"
    model = eval("models." + name + "(pretrained=True)")
    torch.save(model, output_filename)

    print("Pre-trained model was saved in \"%s\"" % output_filename)
