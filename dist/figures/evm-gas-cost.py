import matplotlib.pyplot as plt
import matplotlib
import numpy as np

matplotlib.rcParams['font.size'] = 14
plt.rcParams.update({'figure.autolayout': True})
# plt.style.use('bmh')

labels = ['alexnet', 'vgg11', 'vgg13', 'vgg16', 'vgg19', 'resnet18', 'resnet34', 'resnet50', 'resnet101', 'resnet152', 'squeezenet1.0', 'squeezenet1.1', 'densenet121', 'densenet161', 'densenet169', 'densenet201', 'inception_v3']
MACs = [0.72, 7.63, 11.34, 15.5, 19.67, 1.82, 3.68, 4.12, 7.85, 11.58, 0.83, 0.36, 2.88, 7.82, 3.42, 4.37, 2.85]

fig, ax = plt.subplots()
ax.bar(labels, [8*i for i in MACs], color='lightgrey', edgecolor='black')
labels = ax.get_xticklabels()

ax.axhline(30e-3, ls='--', color='r')

ax.set(ylabel='estimated gas cost (G)')
ax.set_yscale("log")
# ax.yaxis.grid(True)
plt.tight_layout()
plt.xticks(rotation=30, ha='right')
plt.annotate(text='Ethereum block gas upper limit 30M', xy=(6, 30e-3), xytext=(8, 0.8), fontsize=18, horizontalalignment='center', bbox={'fc': 'white'}, arrowprops=dict(arrowstyle='fancy'))
plt.show()