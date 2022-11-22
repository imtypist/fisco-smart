import matplotlib.pyplot as plt
import matplotlib
import numpy as np

matplotlib.rcParams['font.size'] = 16

labels = ['Registration/update', 'Remote attestation']
pckcache = [6.858, 0.061]
register_TPM = [2.544, 0]
quote_verify = [0, 0.152]
width = 0.2


fig, ax = plt.subplots()
ax.bar(labels, pckcache, width, label='PCK cache')
ax.bar(labels, register_TPM, width, bottom=pckcache, label='Register in TPM')
ax.bar(labels, quote_verify, width, bottom=register_TPM, label='Verify quote')
# x = np.arange(len(labels))  # the label locations

# Add some text for labels, title and custom x-axis tick labels, etc.
ax.set_ylabel('time cost (seconds)')
# ax.set_xticks(x,labels, rotation=5)

# ax.bar_label(rects1, padding=3, rotation=45)
# ax.bar_label(rects2, padding=3, rotation=45)

fig.tight_layout()
ax.legend()

plt.show()

# data = np.asarray([ubuntu18, windows11])
# plt.boxplot(data,labels=labels)
# plt.show()