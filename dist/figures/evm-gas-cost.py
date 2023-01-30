import matplotlib.pyplot as plt
import matplotlib
import numpy as np

matplotlib.rcParams['font.size'] = 14
plt.rcParams.update({'figure.autolayout': True})
# plt.style.use('bmh')

labels = ['alexnet', 'vgg11', 'vgg13', 'vgg16', 'vgg19', 'resnet18', 'resnet34', 'resnet50', 'resnet101', 'resnet152', 'squeezenet1.0', 'squeezenet1.1', 'densenet121', 'densenet161', 'densenet169', 'densenet201', 'inception_v3']
MACs = [0.72, 7.63, 11.34, 15.5, 19.67, 1.82, 3.68, 4.12, 7.85, 11.58, 0.83, 0.36, 2.88, 7.82, 3.42, 4.37, 2.85]

fig, ax = plt.subplots()
ax.barh(labels, [8*i for i in MACs], color='lightblue', edgecolor='black')
labels = ax.get_xticklabels()

ax.axvline(8e-3, ls='--', color='r')

ax.set(xlabel='estimated gas cost (G)')
ax.set_xscale("log")
ax.xaxis.grid(True)

plt.show()