import os

for root, dirs, files in os.walk("./"):
    for file in files:
        if not file.endswith(".log"):
            continue
        
        print(file)
        sort_log = []
        with open(file, "r") as f:
            for line in f:
                ts = line.split('[')[3][:13]
                sort_log.append((int(ts), line))
        
        sort_log = sorted(sort_log, key=lambda kv: kv[0])
        f = open("%s.sorted" % file, "w")
        for kv in sort_log:
            f.write(kv[1])
        f.close()
