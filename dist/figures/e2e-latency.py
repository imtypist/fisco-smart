import matplotlib.pyplot as plt
import matplotlib
import numpy as np

matplotlib.rcParams['font.size'] = 14

labels = ['public model', 'private model']
send_contract_call = np.array([1.872 + 1.877, 2.120 + 2.182])/2
off_chain_pre_check = np.array([69.551 + 68.844, 69.223 + 73.305])/2
model_inference = np.array([15.643 + 20.448, 12.465 + 12.836])/2
generate_quote = np.array([1.157 + 1.505, 1.217 + 1.208])/2
verify_quote = np.array([0.282 + 0.159, 0.156 + 0.16])/2

send_contract_call_std = np.array([np.std([1.872, 1.877]), np.std([2.120, 2.182])])
off_chain_pre_check_std = np.array([np.std([69.551, 68.844]), np.std([69.223 + 73.305])])
model_inference_std = np.array([np.std([15.643, 20.448]), np.std([12.465, 12.836])])
generate_quote_std = np.array([np.std([1.157, 1.505]), np.std([1.217, 1.208])])
verify_quote_std = np.array([np.std([0.282, 0.159]), np.std([0.156, 0.16])])

width = 0.3


fig, ax = plt.subplots()
ax.barh(labels, send_contract_call, width, label='send contract call', xerr=send_contract_call_std, color='lightyellow', hatch='///')
ax.barh(labels, off_chain_pre_check, width, left=send_contract_call, label='enclave initialization', xerr=off_chain_pre_check_std, color='lightgray', hatch='.')
ax.barh(labels, model_inference, width, left=off_chain_pre_check+send_contract_call, label='model inference', xerr=model_inference_std, color='lightblue', hatch='x')
ax.barh(labels, generate_quote, width, left=off_chain_pre_check+send_contract_call+model_inference, label='attestation', xerr=generate_quote_std, color='lightpink', hatch='o')
ax.barh(labels, verify_quote, width, left=off_chain_pre_check+send_contract_call+model_inference+generate_quote, label='verification', xerr=verify_quote_std, color='lightseagreen', hatch='+')
# x = np.arange(len(labels))  # the label locations

# Add some text for labels, title and custom x-axis tick labels, etc.
ax.set_xlabel('time cost (seconds)')
x = np.arange(len(labels))
ax.set_yticks(x,labels,rotation=90,va='center')

# ax.bar_label(rects1, padding=3, rotation=45)
# ax.bar_label(rects2, padding=3, rotation=45)
ax.xaxis.grid(True)

fig.tight_layout()
ax.legend(loc='best')

plt.show()

# data = np.asarray([ubuntu18, windows11])
# plt.boxplot(data,labels=labels)
# plt.show()