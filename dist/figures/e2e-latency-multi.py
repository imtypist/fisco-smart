import matplotlib.pyplot as plt
import matplotlib
import numpy as np
import linecache

matplotlib.rcParams['font.size'] = 14

labels = ['alex', 'dense161', 'res18', 'squeeze1.0', 'vgg16']
send_contract_call = []
off_chain_pre_check = []
model_inference = []
generate_quote = []
execution_return = []
verify_quote = []

private_send_contract_call = []
private_off_chain_pre_check = []
private_key_provision = []
private_model_inference = []
private_generate_quote = []
private_execution_return = []
private_verify_quote = []

files = ['alexnet', 'densenet161', 'resnet18', 'squeezenet1_0', 'vgg16']
for file in files:
    st = int(linecache.getline("../../eval_logs/%s.log.sorted" % file, 26).split('[')[3][:13])
    ed = int(linecache.getline("../../eval_logs/%s.log.sorted" % file, 28).split('[')[3][:13])
    send_contract_call.append((ed-st)/1000)
    st = int(linecache.getline("../../eval_logs/%s.log.sorted" % file, 28).split('[')[3][:13])
    ed = int(linecache.getline("../../eval_logs/%s.log.sorted" % file, 29).split('[')[3][:13])
    off_chain_pre_check.append((ed-st)/1000)
    st = int(linecache.getline("../../eval_logs/%s.log.sorted" % file, 29).split('[')[3][:13])
    ed = int(linecache.getline("../../eval_logs/%s.log.sorted" % file, 30).split('[')[3][:13])
    model_inference.append((ed-st)/1000)
    st = int(linecache.getline("../../eval_logs/%s.log.sorted" % file, 30).split('[')[3][:13])
    ed = int(linecache.getline("../../eval_logs/%s.log.sorted" % file, 31).split('[')[3][:13])
    generate_quote.append((ed-st)/1000)
    st = int(linecache.getline("../../eval_logs/%s.log.sorted" % file, 31).split('[')[3][:13])
    ed = int(linecache.getline("../../eval_logs/%s.log.sorted" % file, 33).split('[')[3][:13])
    execution_return.append((ed-st)/1000)
    st = int(linecache.getline("../../eval_logs/%s.log.sorted" % file, 46).split('[')[3][:13])
    ed = int(linecache.getline("../../eval_logs/%s.log.sorted" % file, 47).split('[')[3][:13])
    verify_quote.append((ed-st)/1000)

    st = int(linecache.getline("../../eval_logs/%s.log.sorted" % file, 35).split('[')[3][:13])
    ed = int(linecache.getline("../../eval_logs/%s.log.sorted" % file, 37).split('[')[3][:13])
    private_send_contract_call.append((ed-st)/1000)
    st = int(linecache.getline("../../eval_logs/%s.log.sorted" % file, 37).split('[')[3][:13])
    ed = int(linecache.getline("../../eval_logs/%s.log.sorted" % file, 38).split('[')[3][:13])
    private_off_chain_pre_check.append((ed-st)/1000)
    st = int(linecache.getline("../../eval_logs/%s.log.sorted" % file, 38).split('[')[3][:13])
    ed = int(linecache.getline("../../eval_logs/%s.log.sorted" % file, 39).split('[')[3][:13])
    private_key_provision.append((ed-st)/1000)
    st = int(linecache.getline("../../eval_logs/%s.log.sorted" % file, 40).split('[')[3][:13])
    ed = int(linecache.getline("../../eval_logs/%s.log.sorted" % file, 41).split('[')[3][:13])
    private_model_inference.append((ed-st)/1000)
    st = int(linecache.getline("../../eval_logs/%s.log.sorted" % file, 41).split('[')[3][:13])
    ed = int(linecache.getline("../../eval_logs/%s.log.sorted" % file, 42).split('[')[3][:13])
    private_generate_quote.append((ed-st)/1000)
    st = int(linecache.getline("../../eval_logs/%s.log.sorted" % file, 42).split('[')[3][:13])
    ed = int(linecache.getline("../../eval_logs/%s.log.sorted" % file, 44).split('[')[3][:13])
    private_execution_return.append((ed-st)/1000)
    st = int(linecache.getline("../../eval_logs/%s.log.sorted" % file, 47).split('[')[3][:13])
    ed = int(linecache.getline("../../eval_logs/%s.log.sorted" % file, 48).split('[')[3][:13])
    private_verify_quote.append((ed-st)/1000)

width = 0.35
send_contract_call = np.array(send_contract_call)
off_chain_pre_check = np.array(off_chain_pre_check)
model_inference = np.array(model_inference)
generate_quote = np.array(generate_quote)
execution_return = np.array(execution_return)
verify_quote = np.array(verify_quote)

fig, ax = plt.subplots()
_pos = np.arange(len(labels))
private_send_contract_call = np.array(send_contract_call)
private_off_chain_pre_check = np.array(off_chain_pre_check)
private_key_provision = np.array(private_key_provision)
private_model_inference = np.array(model_inference)
private_generate_quote = np.array(generate_quote)
private_execution_return = np.array(execution_return)
private_verify_quote = np.array(verify_quote)

ax.bar(_pos+width/2, private_send_contract_call, width, label='on-chain contract call (transfer to off-chain)', color='lightyellow', hatch='///', edgecolor='black')
ax.bar(_pos+width/2, private_off_chain_pre_check, width, bottom=private_send_contract_call, label='enclave initialization', color='lightgray', hatch='.', edgecolor='black')
ax.bar(_pos+width/2, private_key_provision, width, bottom=private_send_contract_call+private_off_chain_pre_check, label='secret key provisioning (private model only)', color='orange', hatch='\\', edgecolor='black')
ax.bar(_pos+width/2, private_model_inference, width, bottom=private_send_contract_call+private_off_chain_pre_check+private_key_provision, label='model inference', color='lightblue', hatch='x', edgecolor='black')
ax.bar(_pos+width/2, private_generate_quote, width, bottom=private_send_contract_call+private_off_chain_pre_check+private_key_provision+private_model_inference, label='quote generation', color='lightpink', hatch='o', edgecolor='black')
ax.bar(_pos+width/2, private_verify_quote, width, bottom=private_send_contract_call+private_off_chain_pre_check+private_key_provision+private_model_inference+private_generate_quote, label='quote verification', color='lightseagreen', hatch='+', edgecolor='black')
ax.bar(_pos+width/2, private_execution_return, width, bottom=private_send_contract_call+private_off_chain_pre_check+private_key_provision+private_model_inference+private_generate_quote+private_verify_quote, label='contract context recovery (back to on-chain)', color='red', hatch='*', edgecolor='black')


ax.bar(_pos-width/2, send_contract_call, width, color='lightyellow', hatch='///', edgecolor='black')
ax.bar(_pos-width/2, off_chain_pre_check, width, bottom=send_contract_call, color='lightgray', hatch='.', edgecolor='black')
ax.bar(_pos-width/2, model_inference, width, bottom=off_chain_pre_check+send_contract_call, color='lightblue', hatch='x', edgecolor='black')
ax.bar(_pos-width/2, generate_quote, width, bottom=off_chain_pre_check+send_contract_call+model_inference, color='lightpink', hatch='o', edgecolor='black')
ax.bar(_pos-width/2, verify_quote, width, bottom=off_chain_pre_check+send_contract_call+model_inference+generate_quote, color='lightseagreen', hatch='+', edgecolor='black')
ax.bar(_pos-width/2, execution_return, width, bottom=off_chain_pre_check+send_contract_call+model_inference+generate_quote+verify_quote, color='red', hatch='*', edgecolor='black')

# Add some text for labels, title and custom x-axis tick labels, etc.
ax.set_ylabel('time cost (seconds)')
x = np.arange(len(labels))
ax.set_xticks(x,labels)


# ax.yaxis.grid(True)

fig.tight_layout()
ax.legend(loc='best')

plt.show()
