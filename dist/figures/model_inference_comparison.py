import matplotlib.pyplot as plt
import matplotlib
import numpy as np

matplotlib.rcParams['font.size'] = 14

labels = ['non-SGX', 'SGX']
non_sgx = [0.353, 0.448, 0.333]
sgx = [18.029, 13.184, 20.448, 12.835, 15.643, 12.465]
all_data = [non_sgx, sgx]
width = 0.35


fig, (ax1, ax2) = plt.subplots(nrows=1, ncols=2, figsize=(6, 6))
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

# ax.bar_label(rects1, padding=3, rotation=45)
# ax.bar_label(rects2, padding=3, rotation=45)

fig.tight_layout()

# fill with colors
colors = ['lightyellow', 'lightgrey']

for patch in bplot1['boxes']:
    patch.set_facecolor(colors[0])

for patch in bplot2['boxes']:
    patch.set_facecolor(colors[1])

# adding horizontal grid lines
for ax in [ax1, ax2]:
    ax.yaxis.grid(True)

plt.show()

# data = np.asarray([ubuntu18, windows11])
# plt.boxplot(data,labels=labels)
# plt.show()