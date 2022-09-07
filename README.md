# fisco-smart

Switch source for faster compile

```bash
cat > ~/.gitconfig << EOF
[url "https://ghproxy.com/https://github.com/"]
        insteadOf = https://github.com/
[http]
        sslVerify = false
EOF
```

console v3.0.0调用预编译合约有问题，参见[https://github.com/FISCO-BCOS/console/issues/601](https://github.com/FISCO-BCOS/console/issues/601).