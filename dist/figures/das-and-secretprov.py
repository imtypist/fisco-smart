import matplotlib.pyplot as plt
import matplotlib
import numpy as np

matplotlib.rcParams['font.size'] = 14

labels = ['registration/update', 'remote attestation', 'secret provisioning']
all_reg = [9.419, 9.705, 9.512]
all_attest = [1.64, 1.66, 1.69]
secret_prov_time = [1.133, 0.951]
all_data = [all_reg, all_attest, secret_prov_time]
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