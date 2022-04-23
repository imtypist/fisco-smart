import time
import requests
import os

base_url = "https://img.cryptokitties.co/0x06012c8cf97bead5deae237070f9587f8e7a266d/"
file_path = './cryptokitties/'

cookies = {
    '_ga': 'GA1.2.371924346.1649670064',
    '_fbp': 'fb.1.1649670064897.950014125',
    'ajs_anonymous_id': '670adca1-c18d-4550-ba64-806352e2f8bd',
    '_gid': 'GA1.2.1191067487.1650595057',
}

headers = {
    'authority': 'img.cryptokitties.co',
    'accept': 'text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9',
    'accept-language': 'zh-CN,zh;q=0.9,en;q=0.8',
    # Requests sorts cookies= alphabetically
    # 'cookie': '_ga=GA1.2.371924346.1649670064; _fbp=fb.1.1649670064897.950014125; ajs_anonymous_id=670adca1-c18d-4550-ba64-806352e2f8bd; _gid=GA1.2.1191067487.1650595057',
    'sec-ch-ua': '" Not A;Brand";v="99", "Chromium";v="100", "Google Chrome";v="100"',
    'sec-ch-ua-mobile': '?0',
    'sec-ch-ua-platform': '"macOS"',
    'sec-fetch-dest': 'document',
    'sec-fetch-mode': 'navigate',
    'sec-fetch-site': 'none',
    'sec-fetch-user': '?1',
    'upgrade-insecure-requests': '1',
    'user-agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.88 Safari/537.36',
}

def download_kitty_by_id(kitty_id):
    file_name = kitty_id + ".svg"
    response = requests.get(base_url+file_name, headers=headers, cookies=cookies)
    if response.status_code == 200:
        with open(file_path + file_name, "wb+") as f:
            f.write(response.content)
    return response.status_code


kitty_num = 25350
err_file = open('errors.txt','w+')

for i in range(kitty_num):
    if os.path.exists(file_path+str(i+1)+".svg"):
        print("kitty_id=", i+1, "skip")
        continue
    try:
        res = download_kitty_by_id(str(i+1))
        if res != 200:
            err_file.write(time.strftime("%Y-%m-%d %H:%M:%S", time.localtime()) + " | response code=" + str(res) + " | kitty_id=" + str(i+1) + "\n")
            print("kitty_id=", i+1, "##response code=", res) # kitties that may be special edition kitties have no *.svg pictures but *.png ones, so requests return 404.
        else:
            print("kitty_id=", i+1, "##ok")
    except Exception:
        err_file.write(time.strftime("%Y-%m-%d %H:%M:%S", time.localtime()) + " | Exception | kitty_id=" + str(i+1) + "\n")
        print("kitty_id=", i+1, "##exception")
    time.sleep(0.1)

err_file.close()