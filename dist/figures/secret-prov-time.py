import matplotlib.pyplot as plt
import matplotlib
import numpy as np

matplotlib.rcParams['font.size'] = 14

labels = ['secret provisioning']

secret_prov_time = [1.133, 0.951]

fig, ax = plt.subplots(nrows=1, ncols=1, figsize=(3, 6))

bplot1 = ax.boxplot(secret_prov_time,
                     vert=True,  # vertical box alignment
                     patch_artist=True,  # fill with color
                     labels=labels)  # will be used to label x-ticks

ax.set_ylabel('time cost (seconds)')
fig.tight_layout()

for patch in bplot1['boxes']:
        patch.set_facecolor('lightgreen')

ax.yaxis.grid(True)

plt.show()