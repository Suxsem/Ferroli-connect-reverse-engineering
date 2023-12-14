print(int("00001200", base=16))
hexmessage = str("0003 1306 6400 0001 8760 8470 6800 0001 8760 83af a923 2000 0002 0187 2002 0002 0187 2003 0002 01b8 2007 0001 5020 0800 0114 2009 0001 3720 0a00 0128 200b 0002 028a 200c 0002 021c 200d 0001 0020 0e00 0100 200f 0001 0220 1200 0101 2014 0001 0020 1900 0100 201a 0001 0020 2000 0101 2023 0001 0020 2400 0455 55aa aa20 2500 0100 2026 0002 0000 2027 0002 0000 2030 0001 00".replace(" ",""))
type = int(hexmessage[0:4], base=16)                    # tipo di pacchetto (4100) ?
infoLength = int(hexmessage[4:6], base=16)              # lunghezza delle info in bytes (da messageId a timezone)
messageId = int(hexmessage[6:8], base=16)               # progressivo
versionCode = int(hexmessage[8:10], base=16)            # 1.0.0
wifiTimestamp = int(hexmessage[10:26], base=16)         # ms from epoch
timestamp = int(hexmessage[26:42], base=16)             # ms from epoch
timezone = int(hexmessage[42:44], base=16)              # ore da aggiungere a UTC, meno 12 ?

if (len(hexmessage) < 14):
    print("Message must at least 7 bytes")
    exit()

messages = []

pos = (infoLength + 3) * 2
while (pos < len(hexmessage)):
    messageType = int(hexmessage[pos:pos+4], base=16)
    messageLen = int(hexmessage[pos+4:pos+8], base=16)
    if (pos + messageLen >= len(hexmessage)):
        break
    messageValue = hexmessage[pos+8:pos+8+messageLen*2]
    messages.append(dict(type=messageType,value=messageValue))
    pos = pos+8+messageLen*2

print(messages)