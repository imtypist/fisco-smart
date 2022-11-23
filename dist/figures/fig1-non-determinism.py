import matplotlib.pyplot as plt
import matplotlib
import numpy as np

matplotlib.rcParams['font.size'] = 14

labels = ['Labrador retriever', 'golden retriever', 'Saluki', 'whippet', 'Ibizan hound']
ubuntu18 = [41.585174560546875, 16.591665267944336, 16.286867141723633, 2.8539085388183594, 2.392474889755249]
windows11 = [42.46735763549805, 16.6086483001709, 15.473832130432129, 2.7881932258605957, 2.3617053031921387]

x = np.arange(len(labels))  # the label locations
width = 0.35  # the width of the bars

fig, ax = plt.subplots()
rects1 = ax.bar(x - width/2, ubuntu18, width, label='Ubuntu 18.04', color='pink', edgecolor='black', hatch='/')
rects2 = ax.bar(x + width/2, windows11, width, label='Windows 11', color='lightblue', edgecolor='black')

# Add some text for labels, title and custom x-axis tick labels, etc.
ax.set_ylabel('Alexnet (classification model)')
ax.set_xticks(x, labels, rotation=15)
ax.legend()

ax.bar_label(rects1, padding=3, rotation=45)
ax.bar_label(rects2, padding=3, rotation=45)

fig.tight_layout()

plt.show()

# data = np.asarray([ubuntu18, windows11])
# plt.boxplot(data,labels=labels)
# plt.show()