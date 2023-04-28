import sys

quote_path = sys.argv[1]

with open(quote_path, "rb") as f:
    quote = f.read()

# print(f"Extracted SGX quote with size = {len(quote)} and the following fields:")
# print(f"  ATTRIBUTES.FLAGS: {quote[96:104].hex()}  [ Debug bit: {quote[96] & 2 > 0} ]")
# print(f"  ATTRIBUTES.XFRM:  {quote[104:112].hex()}")
# print(f"  MRENCLAVE:        {quote[112:144].hex()}")
# print(f"  MRSIGNER:         {quote[176:208].hex()}")
# print(f"  ISVPRODID:        {quote[304:306].hex()}")
# print(f"  ISVSVN:           {quote[306:308].hex()}")
# print(f"  REPORTDATA:       {quote[368:400].hex()}")
# print(f"                    {quote[400:432].hex()}")

# print("\nDecode REPORTDATA:", quote[368:432].decode())

with open("/home/junqin/fisco-smart/tee-provider/result.txt","w+") as f:
    f.write(quote[368:432].decode())