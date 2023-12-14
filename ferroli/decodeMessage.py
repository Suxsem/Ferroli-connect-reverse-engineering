import ssl
import paho.mqtt.client as mqtt
import json
import time
import requests

messageConfigs = {
    0: None,
    1: None,
    2: None,
    3: { "name":"SystemTime", "length":4, "parse": lambda x : int(x, base=16) },
    4: None,
    5: { "name":"DaylightTime", "parse": lambda x : int(x, base=16) == 1 },
    6: { "name":"InDaylightTime", "parse": lambda x : int(x, base=16) == 1 },
    7: None,
    8: None,
    9: None,
    10: None,
    11: None,
    12: None,
    13: None,
    14: None,
    15: None,
    16: None,
    17: { "name":"ConnectionState", "parse": lambda x : { 0: "No WIFI", 1: "No Server", 2: "Ok"}.get(int(x, base=16), "? "+ str(int(x, base=16))) },
    18: None,
    19: { "name":"LowBattery", "parse": lambda x : int(x, base=16) == 1 },
    20: None,
    21: { "name":"FaultCode", "parse": lambda x : int(x, base=16) },
    22: None,
    23: { "name":"ThermostatID", "length": 4, "parse": lambda x : x },
    24: { "name":"WiFiSignal", "parse": lambda x : int(x, base=16) },
    4096: { "name":"RoomCurrentTemp", "length": 2, "parse": lambda x : int(x, base=16) / 10 },
    4097: { "name":"RoomTargetTemp", "length": 2, "parse": lambda x : int(x, base=16) / 10 },
    4098: { "name":"RoomTargetTempMax", "length": 2, "parse": lambda x : int(x, base=16) / 10 },
    4099: { "name":"RoomTargetTempMin", "length": 2, "parse": lambda x : int(x, base=16) / 10 },
    4100: { "name":"ComfortTemp", "length": 2, "parse": lambda x : int(x, base=16) / 10 },
    4101: { "name":"EconTemp", "length": 2, "parse": lambda x : int(x, base=16) / 10 },
    4102: { "name":"FrostTemp", "length": 2, "parse": lambda x : int(x, base=16) / 10 },
    4103: { "name":"NightTemp", "length": 2, "parse": lambda x : int(x, base=16) / 10 },
    4104: { "name":"HeatAllow", "parse": lambda x : int(x, base=16) == 1 },
    4105: { "name":"IsHeating", "parse": lambda x : int(x, base=16) == 1 },
    4106: { "name":"ThermostatRelayClosed", "parse": lambda x : int(x, base=16) == 1 },
    4107: { "name":"TemperatureUnit", "parse": lambda x : { 0: "C", 1: "F" }.get(int(x, base=16), "? "+ str(int(x, base=16))) },
    4108: { "name":"ReturnTempOn", "parse": lambda x : int(x, base=16) / 10 },
    4109: { "name":"ReturnTempOff", "parse": lambda x : int(x, base=16) / 10 },
    4110: { "name":"TempSensorActive", "parse": lambda x : int(x, base=16) == 1 },
    4111: { "name":"WorkMode", "parse": lambda x : { 0: "Auto", 1: "Manual", 2: "Out", 3: "Party", 4: "Off", 5: "Hot water" }.get(int(x, base=16), "? "+ str(int(x, base=16))) },
    4112: { "name":"ManualTemp", "length": 2, "parse": lambda x : int(x, base=16) / 10 },
    4113: None,
    4114: None,
    4115: None,
    4116: { "name":"Season", "parse": lambda x : { 0: "Summer", 1: "Winter" }.get(int(x, base=16), "? "+ str(int(x, base=16))) },
    4117: None,
    4118: { "name":"RoomOverridden", "parse": lambda x : int(x, base=16) == 1 },
    4119: { "name":"RoomOverrideTemp", "length": 2, "parse": lambda x : int(x, base=16) / 10 },
    4120: None,
    4121: None,
    4122: None,
    4123: None, #TODO
    4124: None,
    4125: None, #TODO
    4126: None, #TODO
    4127: None, #TODO
    4128: None, #TODO
    4129: None, #TODO
    4130: None, #TODO
    4131: None, #TODO
    4132: { "name":"HeatWaterTempMax", "length": 2, "parse": lambda x : int(x, base=16) / 10 },
    4133: { "name":"HeatWaterTempMin", "length": 2, "parse": lambda x : int(x, base=16) / 10 },
    4134: None,
    4135: { "name":"AntiFrozenEnabled", "parse": lambda x : int(x, base=16) == 1 },
    4136: { "name":"AntiFrozenRunning", "parse": lambda x : int(x, base=16) == 1 },
    4137: None,
    4138: None,
    4139: None,
    4140: None,
    4141: { "name":"ThermostatSensitivity", "length": 4, "parse": lambda x : int(x[3:4], base=16) },
    4142: None,
    4143: None,
    4144: None,
    4145: None,
    4146: None,
    4147: { "name":"HeatMode", "parse": lambda x : { 0: "Normal", 1: "OS", 2: "TPI" }.get(int(x, base=16), "? "+ str(int(x, base=16))) },
    4148: None,
    4149: None,
    4150: None,
    8192: { "name":"WarmingWaterRealTemperature", "length": 2, "parse": lambda x : int(x, base=16) / 10 },
    8193: { "name":"HeatingWaterTemp", "length": 2, "parse": lambda x : int(x, base=16) / 10 },
    8194: { "name":"BathWaterRealTemperature", "length": 2, "parse": lambda x : int(x, base=16) / 10 },
    8195: { "name":"BathWaterTargetTemperature", "length": 2, "parse": lambda x : int(x, base=16) / 10 },
    8196: None,
    8197: None,
    8198: None,
    8199: { "name":"WarmingWaterMaxTemperature", "length": 2, "parse": lambda x : int(x, base=16) },
    8200: { "name":"WarmingWaterMinTemperature", "length": 2, "parse": lambda x : int(x, base=16) },
    8201: { "name":"BathWaterMaxTemperature", "length": 2, "parse": lambda x : int(x, base=16) },
    8202: { "name":"BathWaterMinTemperature", "length": 2, "parse": lambda x : int(x, base=16) },
    8203: { "name":"WarmingWaterTargetTemperature", "length": 2, "parse": lambda x : int(x, base=16) / 10 },
    8204: { "name":"OutTemperature", "length": 2, "parse": lambda x : int(x, base=16) / 10 },
    8205: { "name":"ErrorInfo", "parse": lambda x : { 0: "N", 17: "A", 18: "F" }.get(int(x, base=16), "? "+ str(int(x, base=16))) },
    8206: { "name":"ErrorCode", "parse": lambda x :int(x, base=16) },
    8207: { "name":"BoilerControlType", "parse": lambda x : { 0: "OnOff", 1: "OpenTherm" }.get(int(x, base=16), "? "+ str(int(x, base=16))) },
    8208: { "name":"OpenThermConnected", "parse": lambda x : int(x, base=16) == 1 },
    8209: { "name":"BoilerRelayClosed", "parse": lambda x : int(x, base=16) == 1 or int(x, base=16) == 16 },
    8210: { "name":"RoomThermostatClosed", "parse": lambda x : int(x, base=16) == 1 },
    8211: None,
    8212: None, #TODO
    8213: None,
    8214: None,
    8215: None,
    8216: { "name":"WinterSummerMode", "parse": lambda x : { 0: "Summer", 1: "Winter", 2: "Off" }.get(int(x, base=16), "? "+ str(int(x, base=16))) },
    8217: { "name":"WaterPressure", "parse": lambda x : int(x, base=16) / 10 },
    8218: { "name":"FlameFiring", "parse": lambda x : int(x, base=16) == 1 },

    8240: { "name":"OutTemperatureProbeMethod", "parse": lambda x : { 0: "Network", 1: "Local" }.get(int(x, base=16), "? "+ str(int(x, base=16))) }

}

jsonTime=None
jsonRssi=None
jsonReason=None
jsonCount=None
jsonThermostatId=None
jsonThermostatOnline=None
jsonBoilerId=None
jsonBoilerType=None
jsonBoilerOnline=None

receivedMessages = {}

def on_connect(client, userdata, flags, rc):
    print("Connected with result code "+str(rc))
    client.subscribe("/dev_realt/Q85VUC0001/Q85VUC000116599425****")
    client.subscribe("/dev2ser_req_info/Q85VUC0001/Q85VUC000116599425****")
    client.subscribe("/dev2app/Q85VUC0001/Q85VUC000116599425****/#")

def on_message(client, userdata, msg):
    
    global jsonTime
    global jsonRssi
    global jsonReason
    global jsonCount
    global jsonThermostatId
    global jsonThermostatOnline
    global jsonBoilerId
    global jsonBoilerType
    global jsonBoilerOnline
    
    global receivedMessages

    if (msg.topic == "/dev_realt/Q85VUC0001/Q85VUC000116599425****"):

        hexmessage = msg.payload.hex()
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

        for message in messages:
            if (message["type"] in messageConfigs):
                if (messageConfigs[message["type"]] is not None):
                    messageConfig = messageConfigs[message["type"]]
                    receivedMessages[messageConfig["name"]] = messageConfig["parse"](message["value"])
            else:
                print("Unknown message: " + str(message["type"]) + " - value: " + str(message["value"]))

        print("---------------------")
        for key in receivedMessages:
            print(key + ": "+  str(receivedMessages[key]))
        print("---------------------")
    elif (msg.topic == "/dev2app/Q85VUC0001/Q85VUC000116599425****/json"):
        jsonPayload = json.loads(str(msg.payload.decode("utf-8")))
        if ("time" in jsonPayload):
            jsonTime = jsonPayload["time"]
        if ("rssi" in jsonPayload):
            jsonRssi = jsonPayload["rssi"]
        if ("reason" in jsonPayload):
            jsonReason = jsonPayload["reason"]
        if ("ccnt" in jsonPayload):
            jsonCount = jsonPayload["ccnt"]
        if ("type" in jsonPayload):
            if (jsonPayload["type"] == "status"):
                if ("thermostats" in jsonPayload["data"]):
                    if (len(jsonPayload["data"]["thermostats"]) == 0):
                        jsonThermostatId="unknown"
                        jsonThermostatOnline=False
                    else:
                        jsonThermostatId=jsonPayload["data"]["thermostats"][0]["thermostats_id"]
                        jsonThermostatOnline=jsonPayload["data"]["thermostats"][0]["online"]
                elif ("boiler" in jsonPayload["data"]):
                    if (len(jsonPayload["data"]["boiler"]) == 0):
                        jsonBoilerId="unknown"
                        jsonBoilerType="unknown"
                        jsonBoilerOnline=False
                    else:
                        jsonBoilerId=jsonPayload["data"]["boiler"][0]["boiler_id"]
                        jsonBoilerType=jsonPayload["data"]["boiler"][0]["type"]
                        jsonBoilerOnline=jsonPayload["data"]["boiler"][0]["online"]
        print("---------------------")
        print("jsonTime: " + str(jsonTime))
        print("jsonRssi: " + str(jsonRssi))
        print("jsonReason: " + str(jsonReason))
        print("jsonCount: " + str(jsonCount))
        print("jsonThermostatId: " + str(jsonThermostatId))
        print("jsonThermostatOnline: " + str(jsonThermostatOnline))
        print("jsonBoilerId: " + str(jsonBoilerId))
        print("jsonBoilerType: " + str(jsonBoilerType))
        print("jsonBoilerOnline: " + str(jsonBoilerOnline))
        print("---------------------")
    elif (msg.topic == "/dev2ser_req_info/Q85VUC0001/Q85VUC000116599425****"):
        jsonPayload = json.loads(str(msg.payload.decode("utf-8")))
        if (jsonPayload["type"] == "timeStamp"):
            print("device wants timestamp")
            epoch_time = int(time.time())
            timestampResp = {
                "type": "timeStamp",
                "data": epoch_time
            }
            #client.publish("/ser2dev_noti/Q85VUC0001/Q85VUC000116599425****", json.dumps(timestampResp), 0, False)
        elif (jsonPayload["type"] == "weather"):
            print("device wants weather")
            # weatherResp = {
            #     "type": "weather",
            #     "data": [
            #         {
            #         "location": {
            #             "id": "SRHVTDB2WZ2P",
            #             "name": "Lecce",
            #             "country": "IT",
            #             "path": "Lecce,Apulia,Italy",
            #             "timezone": "Europe/Rome",
            #             "timezone_offset": "+02:00"
            #         },
            #         "now": {
            #             "text": "Overcast",
            #             "code": "9",
            #             "temperature": "11",
            #             "feels_like": "",
            #             "pressure": "1015",
            #             "humidity": "69",
            #             "visibility": "10000",
            #             "wind_direction": "NNW",
            #             "wind_direction_degree": "328",
            #             "wind_speed": "32.0",
            #             "wind_scale": "5",
            #             "clouds": "99",
            #             "dew_point": ""
            #         },
            #         "last_update": "2023-04-06T15:12:50+02:00"
            #         }
            #     ]
            # }
            weatherPayload = {'location': 'SRHVTDB2WZ2P', 'language': 'en', 'unit': 'c'}
            weatherHeaders = {'APPSLUG': 'd970e7bd-35f6-46ee-8943-c32c77bc67aa'}
            weatherResponse = requests.get('https://eu-api.topband-cloud.com/apps/topiot_third_party_service/api/seniverse_weather/now', params=weatherPayload, headers=weatherHeaders)
            weatherData = weatherResponse.json()["results"]
            weatherResp = {
                "type": "weather",
                "data": weatherData
            }
            #client.publish("/ser2dev_noti/Q85VUC0001/Q85VUC000116599425****", json.dumps(weatherResp), 0, False)
            weatherTemp = weatherData[0]["now"]["temperature"]
            weatherRespOnlyTemp = {
                "type": "status",
                "data": {
                    "gatway": {
                        "temperature": weatherTemp
                    }
                }
            }
            #client.publish("/app2dev/Q85VUC0001/Q85VUC000116599425****/json", json.dumps(weatherRespOnlyTemp), 0, False)
client = mqtt.Client()
client.on_connect = on_connect
client.on_message = on_message

client.username_pw_set("ferroli_python", "password")
client.tls_set(cert_reqs=ssl.CERT_NONE)
client.tls_insecure_set(True)
client.connect("localhost", 8883, 60)

#hexmessage="0003 1309 6400 0001 8756 cb36 3800 0001 8756 cb34 0120 0017 0004 06c6 99c1 1021 001a 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 0000 2012 1800 "
#hexmessage=hexmessage.replace(" ","");

client.loop_forever()
