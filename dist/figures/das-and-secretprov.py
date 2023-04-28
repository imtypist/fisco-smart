import matplotlib.pyplot as plt
import matplotlib
import numpy as np
import os
import linecache

matplotlib.rcParams['font.size'] = 14

labels = ['TEE provider register/update', 'distributed attestation', 'secret key provisioning']
# all_reg = [4.624, 4.616, 4.764, 4.766, 4.734]
# all_attest = [0.227, 0.209, 0.210, 0.163, 0.171, 0.166, 0.168, 0.172, 0.179, 0.180]
# secret_prov_time = [12.481, 40.978, 16.545, 30.190, 22.880]
all_reg = []
all_attest = []
secret_prov_time = []
files = ['alexnet', 'densenet161', 'resnet18', 'squeezenet1_0', 'vgg16']

for file in files:
    st = int(linecache.getline("../../eval_logs/%s.log.sorted" % file, 14).split('[')[3][:13])
    ed = int(linecache.getline("../../eval_logs/%s.log.sorted" % file, 17).split('[')[3][:13])
    all_reg.append((ed-st)/1000)
    st = int(linecache.getline("../../eval_logs/%s.log.sorted" % file, 30).split('[')[3][:13]) + int(linecache.getline("../../eval_logs/%s.log.sorted" % file, 46).split('[')[3][:13])
    ed = int(linecache.getline("../../eval_logs/%s.log.sorted" % file, 31).split('[')[3][:13]) + int(linecache.getline("../../eval_logs/%s.log.sorted" % file, 47).split('[')[3][:13])
    all_attest.append((ed-st)/1000)
    st = int(linecache.getline("../../eval_logs/%s.log.sorted" % file, 41).split('[')[3][:13]) + int(linecache.getline("../../eval_logs/%s.log.sorted" % file, 47).split('[')[3][:13])
    ed = int(linecache.getline("../../eval_logs/%s.log.sorted" % file, 42).split('[')[3][:13]) + int(linecache.getline("../../eval_logs/%s.log.sorted" % file, 48).split('[')[3][:13])
    all_attest.append((ed-st)/1000)
    st = int(linecache.getline("../../eval_logs/%s.log.sorted" % file, 38).split('[')[3][:13])
    ed = int(linecache.getline("../../eval_logs/%s.log.sorted" % file, 39).split('[')[3][:13])
    secret_prov_time.append((ed-st)/1000)

all_data = [all_reg, all_attest, secret_prov_time]
print(all_data)

width = 0.35


fig, (ax1, ax2, ax3) = plt.subplots(nrows=1, ncols=3, figsize=(9, 6))
bplot1 = ax1.boxplot(all_data[0],
                    #  showfliers=False,
                     vert=True,  # vertical box alignment
                     patch_artist=True,  # fill with color
                     labels=[labels[0]])  # will be used to label x-ticks

# Add some text for labels, title and custom x-axis tick labels, etc.
ax1.set_ylabel('time cost (seconds)')
# ax.set_xticks(x,labels, rotation=5)

bplot2 = ax2.boxplot(all_data[1],
                    #  notch=True,  # notch shape
                     vert=True,  # vertical box alignment
                     patch_artist=True,  # fill with color
                     labels=[labels[1]])  # will be used to label x-ticks

bplot3 = ax3.boxplot(all_data[2],
                     vert=True,  # vertical box alignment
                     patch_artist=True,  # fill with color
                     labels=[labels[2]])  # will be used to label x-ticks

# ax.bar_label(rects1, padding=3, rotation=45)
# ax.bar_label(rects2, padding=3, rotation=45)

fig.tight_layout()

# fill with colors
colors = ['snow', 'lightgray', 'lightyellow']

for patch in bplot1['boxes']:
    patch.set_facecolor(colors[0])

for patch in bplot2['boxes']:
    patch.set_facecolor(colors[1])

for patch in bplot3['boxes']:
    patch.set_facecolor(colors[2])

# adding horizontal grid lines
for ax in [ax1, ax2, ax3]:
    ax.yaxis.grid(True)

plt.show()