import matplotlib.pyplot as plt
import matplotlib
import numpy as np

matplotlib.rcParams['font.size'] = 16

labels = ['SGX, PM', 'SGX, SM']
on_chain_outsource = np.array([1.872, 2.120])
off_chain_pre_check = np.array([69.551, 69.223])
off_chain_exeution = np.array([15.643, 12.465])
generate_quote = np.array([0.013, 0.014])
return_to_on_chain = np.array([1.144, 1.203])
verify_quote = np.array([0.282, 0.156])

width = 0.35


fig, ax = plt.subplots()
ax.barh(labels, on_chain_outsource, width, label='On-chain call')
ax.barh(labels, off_chain_pre_check, width, left=on_chain_outsource, label='SGX pre-check')
ax.barh(labels, off_chain_exeution, width, left=off_chain_pre_check+on_chain_outsource, label='Model inference')
ax.barh(labels, generate_quote, width, left=off_chain_pre_check+on_chain_outsource+off_chain_exeution, label='Generate quote')
ax.barh(labels, return_to_on_chain, width, left=off_chain_pre_check+on_chain_outsource+off_chain_exeution+generate_quote, label='Return to on-chain')
ax.barh(labels, verify_quote, width, left=off_chain_pre_check+on_chain_outsource+off_chain_exeution+generate_quote+return_to_on_chain, label='Verify quote')
# x = np.arange(len(labels))  # the label locations

# Add some text for labels, title and custom x-axis tick labels, etc.
ax.set_xlabel('time cost (seconds)')
x = np.arange(len(labels))
ax.set_yticks(x,labels,rotation=90)

# ax.bar_label(rects1, padding=3, rotation=45)
# ax.bar_label(rects2, padding=3, rotation=45)

fig.tight_layout()
ax.legend(loc='best')

plt.show()

# data = np.asarray([ubuntu18, windows11])
# plt.boxplot(data,labels=labels)
# plt.show()