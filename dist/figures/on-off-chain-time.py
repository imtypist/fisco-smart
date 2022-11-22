import matplotlib.pyplot as plt
import matplotlib
import numpy as np

matplotlib.rcParams['font.size'] = 16

labels = ['non-SGX, PM', 'SGX, PM', 'SGX, SM']
times = [4.711, 87.502, 86.667]
width = 0.35

fig, ax = plt.subplots()
ax.bar(labels, times, width=width)
# x = np.arange(len(labels))  # the label locations

# Add some text for labels, title and custom x-axis tick labels, etc.
ax.set_ylabel('time cost (seconds)')
# ax.set_xticks(x,labels, rotation=5)

# ax.bar_label(rects1, padding=3, rotation=45)
# ax.bar_label(rects2, padding=3, rotation=45)

fig.tight_layout()

plt.show()

# data = np.asarray([ubuntu18, windows11])
# plt.boxplot(data,labels=labels)
# plt.show()