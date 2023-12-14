package com.topband.sdk.boiler;

import android.support.p000v4.app.FragmentTransaction;
import android.support.p000v4.view.InputDeviceCompat;
import com.topband.sdk.boiler.message.boiler.BathWaterMaxTemperature;
import com.topband.sdk.boiler.message.boiler.BathWaterMinTemperature;
import com.topband.sdk.boiler.message.boiler.BathWaterRealTemperature;
import com.topband.sdk.boiler.message.boiler.BathWaterTargetTemperature;
import com.topband.sdk.boiler.message.boiler.BoilerControlType;
import com.topband.sdk.boiler.message.boiler.BoilerID;
import com.topband.sdk.boiler.message.boiler.BoilerRelayState;
import com.topband.sdk.boiler.message.boiler.DeviceFunctionType;
import com.topband.sdk.boiler.message.boiler.DeviceInstallationType;
import com.topband.sdk.boiler.message.boiler.DeviceRuntimeInfo;
import com.topband.sdk.boiler.message.boiler.ErrorCode;
import com.topband.sdk.boiler.message.boiler.ErrorInfo;
import com.topband.sdk.boiler.message.boiler.FlameStatus;
import com.topband.sdk.boiler.message.boiler.FlueTemperature;
import com.topband.sdk.boiler.message.boiler.FrozenPreventionStatus;
import com.topband.sdk.boiler.message.boiler.FuelGasType;
import com.topband.sdk.boiler.message.boiler.MasterOTStatus;
import com.topband.sdk.boiler.message.boiler.OTCommandList;
import com.topband.sdk.boiler.message.boiler.OTConnectState;
import com.topband.sdk.boiler.message.boiler.OTData;
import com.topband.sdk.boiler.message.boiler.OutProbeModel;
import com.topband.sdk.boiler.message.boiler.OutTemperature;
import com.topband.sdk.boiler.message.boiler.OutTemperaturePayState;
import com.topband.sdk.boiler.message.boiler.OutWaterTemperature;
import com.topband.sdk.boiler.message.boiler.PWMValue;
import com.topband.sdk.boiler.message.boiler.RecoveryMachineError;
import com.topband.sdk.boiler.message.boiler.ReturnTemperature;
import com.topband.sdk.boiler.message.boiler.RoomThermostatState;
import com.topband.sdk.boiler.message.boiler.SlaveOTConfigure;
import com.topband.sdk.boiler.message.boiler.SlaveOTSate;
import com.topband.sdk.boiler.message.boiler.WarmingUpStatus;
import com.topband.sdk.boiler.message.boiler.WarmingWaterMaxTemperature;
import com.topband.sdk.boiler.message.boiler.WarmingWaterMinTemperature;
import com.topband.sdk.boiler.message.boiler.WarmingWaterRealTemperature;
import com.topband.sdk.boiler.message.boiler.WarmingWaterTargetTemperature;
import com.topband.sdk.boiler.message.boiler.WaterBottleFrozenPrevention;
import com.topband.sdk.boiler.message.boiler.WaterPressure;
import com.topband.sdk.boiler.message.boiler.WaterRate;
import com.topband.sdk.boiler.message.boiler.WaterSupplySwitch;
import com.topband.sdk.boiler.message.boiler.WindPressureSwitch;
import com.topband.sdk.boiler.message.boiler.WinterSummerMode;
import com.topband.sdk.boiler.message.system.AutoTest;
import com.topband.sdk.boiler.message.system.BackLightTime;
import com.topband.sdk.boiler.message.system.BatteryUsage;
import com.topband.sdk.boiler.message.system.Channel;
import com.topband.sdk.boiler.message.system.DaylightTime;
import com.topband.sdk.boiler.message.system.DeviceNumber;
import com.topband.sdk.boiler.message.system.DeviceType;
import com.topband.sdk.boiler.message.system.FaultCode;
import com.topband.sdk.boiler.message.system.InDaylightTime;
import com.topband.sdk.boiler.message.system.RFTest;
import com.topband.sdk.boiler.message.system.RadioLostFrame;
import com.topband.sdk.boiler.message.system.RadioNoise;
import com.topband.sdk.boiler.message.system.RadioPair;
import com.topband.sdk.boiler.message.system.RadioReceiveFrame;
import com.topband.sdk.boiler.message.system.RadioRecieveSensitivity;
import com.topband.sdk.boiler.message.system.RadioSendFrame;
import com.topband.sdk.boiler.message.system.RadioWatt;
import com.topband.sdk.boiler.message.system.Reset;
import com.topband.sdk.boiler.message.system.RoomUsage;
import com.topband.sdk.boiler.message.system.SystemTime;
import com.topband.sdk.boiler.message.system.SystemTimeStruct;
import com.topband.sdk.boiler.message.system.WiFiConnectState;
import com.topband.sdk.boiler.message.system.WiFiModelSoftVersion;
import com.topband.sdk.boiler.message.system.WiFiSignal;
import com.topband.sdk.boiler.message.thermostat.AvoidStuckFunction;
import com.topband.sdk.boiler.message.thermostat.Boost;
import com.topband.sdk.boiler.message.thermostat.BoostEndTime;
import com.topband.sdk.boiler.message.thermostat.BoostTemp;
import com.topband.sdk.boiler.message.thermostat.BurnPeriod;
import com.topband.sdk.boiler.message.thermostat.ComfortTemp;
import com.topband.sdk.boiler.message.thermostat.DHWProgram;
import com.topband.sdk.boiler.message.thermostat.EconTemp;
import com.topband.sdk.boiler.message.thermostat.FrostTemp;
import com.topband.sdk.boiler.message.thermostat.FrozenFunction;
import com.topband.sdk.boiler.message.thermostat.FrozenState;
import com.topband.sdk.boiler.message.thermostat.HeatAllow;
import com.topband.sdk.boiler.message.thermostat.HeatMode;
import com.topband.sdk.boiler.message.thermostat.HeatState;
import com.topband.sdk.boiler.message.thermostat.HeatWaterTempMax;
import com.topband.sdk.boiler.message.thermostat.HeatWaterTempMin;
import com.topband.sdk.boiler.message.thermostat.Holiday;
import com.topband.sdk.boiler.message.thermostat.ManualTemp;
import com.topband.sdk.boiler.message.thermostat.NightTemp;
import com.topband.sdk.boiler.message.thermostat.OffFrostTemp;
import com.topband.sdk.boiler.message.thermostat.Override;
import com.topband.sdk.boiler.message.thermostat.OverrideTemp;
import com.topband.sdk.boiler.message.thermostat.PreStart;
import com.topband.sdk.boiler.message.thermostat.Program;
import com.topband.sdk.boiler.message.thermostat.ReturnTempOff;
import com.topband.sdk.boiler.message.thermostat.ReturnTempOn;
import com.topband.sdk.boiler.message.thermostat.RoomCurrentTemp;
import com.topband.sdk.boiler.message.thermostat.RoomTargetTemp;
import com.topband.sdk.boiler.message.thermostat.RoomTargetTempMax;
import com.topband.sdk.boiler.message.thermostat.RoomTargetTempMin;
import com.topband.sdk.boiler.message.thermostat.Season;
import com.topband.sdk.boiler.message.thermostat.TempSensorInfluence;
import com.topband.sdk.boiler.message.thermostat.TempSensorState;
import com.topband.sdk.boiler.message.thermostat.TemperatureBand;
import com.topband.sdk.boiler.message.thermostat.TemperatureChangeRate;
import com.topband.sdk.boiler.message.thermostat.TemperatureOffSetCurve;
import com.topband.sdk.boiler.message.thermostat.TemporaryTempState;
import com.topband.sdk.boiler.message.thermostat.ThermostatID2;
import com.topband.sdk.boiler.message.thermostat.ThermostatList;
import com.topband.sdk.boiler.message.thermostat.ThermostatRelayStatus;
import com.topband.sdk.boiler.message.thermostat.Unit;
import com.topband.sdk.boiler.message.thermostat.ValveCurrentPos;
import com.topband.sdk.boiler.message.thermostat.ValveDistance;
import com.topband.sdk.boiler.message.thermostat.ValveFullDistanceAllow;
import com.topband.sdk.boiler.message.thermostat.ValveMasterAddress;
import com.topband.sdk.boiler.message.thermostat.ValveMasterInfo;
import com.topband.sdk.boiler.message.thermostat.ValveRatio;
import com.topband.sdk.boiler.message.thermostat.ValveTargetPos;
import com.topband.sdk.boiler.message.thermostat.WindowDetectFunction;
import com.topband.sdk.boiler.message.thermostat.WindowState;
import com.topband.sdk.boiler.message.thermostat.WorkMode;
import com.topband.sdk.boiler.message.thermostat.heatingWaterTemp;
import com.topband.sdk.boiler.util.BinaryUtils;

public class MessageFactory {
    public static Message create(byte[] bArr) throws MessageFormatException, UnknownMessageException {
        if (bArr == null) {
            return null;
        }
        short bytes2shortHtL = BinaryUtils.bytes2shortHtL(bArr[0], bArr[1]);
        int bytes2shortHtL2 = BinaryUtils.bytes2shortHtL(bArr[2], bArr[3]);
        int i = bytes2shortHtL2 + 4;
        if (bArr.length == i) {
            byte[] bArr2 = new byte[bytes2shortHtL2];
            System.arraycopy(bArr, 4, bArr2, 0, bytes2shortHtL2);
            Message newInstance = newInstance(bytes2shortHtL);
            newInstance.onParseData(bArr2);
            return newInstance;
        }
        throw new MessageFormatException("package data length required : " + i + "; found : " + bArr.length);
    }

    private static Message newInstance(short s) throws UnknownMessageException {
        Message message;
        if (s == 4153) {
            message = new OffFrostTemp();
        } else if (s != 8240) {
            switch (s) {
                case 0:
                    message = new WiFiModelSoftVersion();
                    break;
                case 1:
                    message = new DeviceType();
                    break;
                case 2:
                    message = new DeviceNumber();
                    break;
                case 3:
                    message = new SystemTime();
                    break;
                case 4:
                    message = new SystemTimeStruct();
                    break;
                case 5:
                    message = new DaylightTime();
                    break;
                case 6:
                    message = new InDaylightTime();
                    break;
                case 7:
                    message = new Channel();
                    break;
                case 8:
                    message = new RadioWatt();
                    break;
                case 9:
                    message = new RadioRecieveSensitivity();
                    break;
                case 10:
                    message = new RadioNoise();
                    break;
                case 11:
                    message = new RadioSendFrame();
                    break;
                case 12:
                    message = new RadioReceiveFrame();
                    break;
                case 13:
                    message = new RadioLostFrame();
                    break;
                case 14:
                    message = new RadioPair();
                    break;
                case 15:
                    message = new RoomUsage();
                    break;
                case 16:
                    message = new RFTest();
                    break;
                case 17:
                    message = new WiFiConnectState();
                    break;
                case 18:
                    message = new BackLightTime();
                    break;
                case 19:
                    message = new BatteryUsage();
                    break;
                case 20:
                    message = new Reset();
                    break;
                case 21:
                    message = new FaultCode();
                    break;
                case 22:
                    message = new AutoTest();
                    break;
                case 23:
                    message = new ThermostatID2();
                    break;
                case 24:
                    message = new WiFiSignal();
                    break;
                default:
                    switch (s) {
                        case 4096:
                            message = new RoomCurrentTemp();
                            break;
                        case FragmentTransaction.TRANSIT_FRAGMENT_OPEN:
                            message = new RoomTargetTemp();
                            break;
                        case InputDeviceCompat.SOURCE_TOUCHSCREEN:
                            message = new RoomTargetTempMax();
                            break;
                        case FragmentTransaction.TRANSIT_FRAGMENT_FADE:
                            message = new RoomTargetTempMin();
                            break;
                        case 4100:
                            message = new ComfortTemp();
                            break;
                        case 4101:
                            message = new EconTemp();
                            break;
                        case 4102:
                            message = new FrostTemp();
                            break;
                        case 4103:
                            message = new NightTemp();
                            break;
                        case 4104:
                            message = new HeatAllow();
                            break;
                        case 4105:
                            message = new HeatState();
                            break;
                        case 4106:
                            message = new ThermostatRelayStatus();
                            break;
                        case 4107:
                            message = new Unit();
                            break;
                        case 4108:
                            message = new ReturnTempOn();
                            break;
                        case 4109:
                            message = new ReturnTempOff();
                            break;
                        case 4110:
                            message = new TempSensorState();
                            break;
                        case 4111:
                            message = new WorkMode();
                            break;
                        case 4112:
                            message = new ManualTemp();
                            break;
                        case 4113:
                            message = new TemporaryTempState();
                            break;
                        case 4114:
                            message = new TemperatureOffSetCurve();
                            break;
                        case 4115:
                            message = new TempSensorInfluence();
                            break;
                        case 4116:
                            message = new Season();
                            break;
                        case 4117:
                            message = new DHWProgram();
                            break;
                        case 4118:
                            message = new Override();
                            break;
                        case 4119:
                            message = new OverrideTemp();
                            break;
                        case 4120:
                            message = new Boost();
                            break;
                        case 4121:
                            message = new BoostTemp();
                            break;
                        case 4122:
                            message = new BoostEndTime();
                            break;
                        case 4123:
                            message = new Holiday();
                            break;
                        case 4124:
                            message = new ValveRatio();
                            break;
                        case 4125:
                            message = Program.newInstance(0);
                            break;
                        case 4126:
                            message = Program.newInstance(1);
                            break;
                        case 4127:
                            message = Program.newInstance(2);
                            break;
                        case 4128:
                            message = Program.newInstance(3);
                            break;
                        case 4129:
                            message = Program.newInstance(4);
                            break;
                        case 4130:
                            message = Program.newInstance(5);
                            break;
                        case 4131:
                            message = Program.newInstance(6);
                            break;
                        case 4132:
                            message = new HeatWaterTempMax();
                            break;
                        case 4133:
                            message = new HeatWaterTempMin();
                            break;
                        case 4134:
                            message = new PreStart();
                            break;
                        case 4135:
                            message = new FrozenFunction();
                            break;
                        case 4136:
                            message = new FrozenState();
                            break;
                        case 4137:
                            message = new ValveCurrentPos();
                            break;
                        case 4138:
                            message = new ValveTargetPos();
                            break;
                        case 4139:
                            message = new ValveDistance();
                            break;
                        case 4140:
                            message = new ValveFullDistanceAllow();
                            break;
                        case 4141:
                            message = new ThermostatList();
                            break;
                        case 4142:
                            message = new ValveMasterAddress();
                            break;
                        case 4143:
                            message = new ValveMasterInfo();
                            break;
                        case 4144:
                            message = new AvoidStuckFunction();
                            break;
                        case 4145:
                            message = new WindowDetectFunction();
                            break;
                        case 4146:
                            message = new WindowState();
                            break;
                        case 4147:
                            message = new HeatMode();
                            break;
                        case 4148:
                            message = new TemperatureBand();
                            break;
                        case 4149:
                            message = new BurnPeriod();
                            break;
                        case 4150:
                            message = new TemperatureChangeRate();
                            break;
                        default:
                            switch (s) {
                                case 8192:
                                    message = new WarmingWaterRealTemperature();
                                    break;
                                case 8193:
                                    message = new heatingWaterTemp();
                                    break;
                                case 8194:
                                    message = new BathWaterRealTemperature();
                                    break;
                                case 8195:
                                    message = new BathWaterTargetTemperature();
                                    break;
                                case 8196:
                                    message = new OutTemperaturePayState();
                                    break;
                                case 8197:
                                    message = new OTData();
                                    break;
                                case 8198:
                                    message = new OTCommandList();
                                    break;
                                case 8199:
                                    message = new WarmingWaterMaxTemperature();
                                    break;
                                case 8200:
                                    message = new WarmingWaterMinTemperature();
                                    break;
                                case 8201:
                                    message = new BathWaterMaxTemperature();
                                    break;
                                case 8202:
                                    message = new BathWaterMinTemperature();
                                    break;
                                case 8203:
                                    message = new WarmingWaterTargetTemperature();
                                    break;
                                case 8204:
                                    message = new OutTemperature();
                                    break;
                                case 8205:
                                    message = new ErrorInfo();
                                    break;
                                case 8206:
                                    message = new ErrorCode();
                                    break;
                                case 8207:
                                    message = new BoilerControlType();
                                    break;
                                case 8208:
                                    message = new OTConnectState();
                                    break;
                                case 8209:
                                    message = new BoilerRelayState();
                                    break;
                                case 8210:
                                    message = new RoomThermostatState();
                                    break;
                                case 8211:
                                    message = new MasterOTStatus();
                                    break;
                                case 8212:
                                    message = new SlaveOTSate();
                                    break;
                                case 8213:
                                    message = new SlaveOTConfigure();
                                    break;
                                case 8214:
                                    message = new DeviceRuntimeInfo();
                                    break;
                                case 8215:
                                    message = new RecoveryMachineError();
                                    break;
                                case 8216:
                                    message = new WinterSummerMode();
                                    break;
                                case 8217:
                                    message = new WaterPressure();
                                    break;
                                case 8218:
                                    message = new FlameStatus();
                                    break;
                                case 8219:
                                    message = new WaterSupplySwitch();
                                    break;
                                case 8220:
                                    message = new FuelGasType();
                                    break;
                                case 8221:
                                    message = new DeviceInstallationType();
                                    break;
                                case 8222:
                                    message = new DeviceFunctionType();
                                    break;
                                case 8223:
                                    message = new FrozenPreventionStatus();
                                    break;
                                case 8224:
                                    message = new WarmingUpStatus();
                                    break;
                                case 8225:
                                    message = new WaterBottleFrozenPrevention();
                                    break;
                                case 8226:
                                    message = new WindPressureSwitch();
                                    break;
                                case 8227:
                                    message = new PWMValue();
                                    break;
                                case 8228:
                                    message = new BoilerID();
                                    break;
                                case 8229:
                                    message = new FlueTemperature();
                                    break;
                                case 8230:
                                    message = new WaterRate();
                                    break;
                                case 8231:
                                    message = new ReturnTemperature();
                                    break;
                                case 8232:
                                    message = new OutWaterTemperature();
                                    break;
                                default:
                                    message = null;
                                    break;
                            }
                    }
            }
        } else {
            message = new OutProbeModel();
        }
        if (message != null) {
            return message;
        }
        throw new UnknownMessageException("unknown message flag : " + s);
    }
}
