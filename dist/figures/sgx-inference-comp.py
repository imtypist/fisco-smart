import matplotlib.pyplot as plt
import matplotlib
from brokenaxes import brokenaxes
import numpy as np

matplotlib.rcParams['font.size'] = 14

labels = ['alex', 'dense161', 'res18', 'squeeze1.0', 'vgg16']
_nonsgx = [0.207, 0.3, 0.097, 0.075, 0.363]
_sgx = [15.225, 14.376, 6.604, 5.312, 31.873]
# macs = [0.72, 7.82, 1.82, 0.83, 15.5] # G
# params = [61.1, 28.68, 11.69, 1.25, 138.36] # M

x = np.arange(len(labels))  # the label locations
width = 0.35  # the width of the bars

fig = plt.figure()
bax = brokenaxes(ylims=((0, .5), (2, 7), (14, 18), (31, 32)), hspace=.05)

rects1 = bax.bar(x - width/2, _nonsgx, width, label='non-SGX', color='lightyellow', edgecolor='black', hatch='/')
rects2 = bax.bar(x + width/2, _sgx, width, label='SGX', color='lightgrey', edgecolor='black')
# rects3 = bax.bar(x + width/2, macs, width, label='#MACs (G)', color='lightseagreen', edgecolor='black')
# rects4 = bax.bar(x + width*3/2, params, width, label='#Params (M)', color='lightsalmon', edgecolor='black')

# Add some text for labels, title and custom x-axis tick labels, etc.
bax.set_ylabel('time cost (seconds)')
bax.set_xticks(x, labels)
bax.legend(loc=2)
# bax.grid(axis='y', which='major', ls='-')

# ax.bar_label(rects2, padding=3, rotation=45)


# ax.yaxis.grid(True)

plt.show()