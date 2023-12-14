package com.szacs.ferroliconnect.bean;

import com.szacs.ferroliconnect.util.Constant;
import com.szacs.ferroliconnect.util.LogUtil;
import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.MessagePacket;
import com.topband.sdk.boiler.message.boiler.BathWaterMaxTemperature;
import com.topband.sdk.boiler.message.boiler.BathWaterMinTemperature;
import com.topband.sdk.boiler.message.boiler.BathWaterRealTemperature;
import com.topband.sdk.boiler.message.boiler.BathWaterTargetTemperature;
import com.topband.sdk.boiler.message.boiler.BoilerControlType;
import com.topband.sdk.boiler.message.boiler.BoilerRelayState;
import com.topband.sdk.boiler.message.boiler.DeviceFunctionType;
import com.topband.sdk.boiler.message.boiler.DeviceInstallationType;
import com.topband.sdk.boiler.message.boiler.ErrorCode;
import com.topband.sdk.boiler.message.boiler.ErrorInfo;
import com.topband.sdk.boiler.message.boiler.FlameStatus;
import com.topband.sdk.boiler.message.boiler.FlueTemperature;
import com.topband.sdk.boiler.message.boiler.FuelGasType;
import com.topband.sdk.boiler.message.boiler.OutProbeModel;
import com.topband.sdk.boiler.message.boiler.OutTemperature;
import com.topband.sdk.boiler.message.boiler.OutTemperaturePayState;
import com.topband.sdk.boiler.message.boiler.OutWaterTemperature;
import com.topband.sdk.boiler.message.boiler.PWMValue;
import com.topband.sdk.boiler.message.boiler.ReturnTemperature;
import com.topband.sdk.boiler.message.boiler.RoomThermostatState;
import com.topband.sdk.boiler.message.boiler.SlaveOTSate;
import com.topband.sdk.boiler.message.boiler.WarmingUpStatus;
import com.topband.sdk.boiler.message.boiler.WarmingWaterMaxSettingTemperature;
import com.topband.sdk.boiler.message.boiler.WarmingWaterMaxTemperature;
import com.topband.sdk.boiler.message.boiler.WarmingWaterMinTemperature;
import com.topband.sdk.boiler.message.boiler.WarmingWaterRealTemperature;
import com.topband.sdk.boiler.message.boiler.WarmingWaterTargetTemperature;
import com.topband.sdk.boiler.message.boiler.WaterPressure;
import com.topband.sdk.boiler.message.boiler.WaterRate;
import com.topband.sdk.boiler.message.boiler.WaterSupplySwitch;
import com.topband.sdk.boiler.message.boiler.WindPressureSwitch;
import com.topband.sdk.boiler.message.boiler.WinterSummerMode;
import com.topband.sdk.boiler.message.system.BatteryUsage;
import com.topband.sdk.boiler.message.system.DaylightTime;
import com.topband.sdk.boiler.message.system.InDaylightTime;
import com.topband.sdk.boiler.message.system.SystemTime;
import com.topband.sdk.boiler.message.system.WiFiSignal;
import com.topband.sdk.boiler.message.thermostat.Boost;
import com.topband.sdk.boiler.message.thermostat.BurnPeriod;
import com.topband.sdk.boiler.message.thermostat.ComfortTemp;
import com.topband.sdk.boiler.message.thermostat.EconTemp;
import com.topband.sdk.boiler.message.thermostat.FrostTemp;
import com.topband.sdk.boiler.message.thermostat.FrozenFunction;
import com.topband.sdk.boiler.message.thermostat.FrozenState;
import com.topband.sdk.boiler.message.thermostat.HeatAllow;
import com.topband.sdk.boiler.message.thermostat.HeatMode;
import com.topband.sdk.boiler.message.thermostat.HeatState;
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
import com.topband.sdk.boiler.message.thermostat.ThermostatRelayStatus;
import com.topband.sdk.boiler.message.thermostat.Unit;
import com.topband.sdk.boiler.message.thermostat.WorkMode;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class MsgBean implements Serializable {
    private static final String TAG = "MsgBean";
    private float MaxTargetTemp;
    private float MinTargetTemp;
    private float bathCurrentTemp;
    private float bathMaxTemp;
    private float bathMinTemp;
    private float bathTargetTemp;
    private int boilerRelayState;
    private int boost;
    private int burnPeriod;
    private float comfortTemp;
    private int controlType;
    private float currentTemp;
    private float econTemp;
    private int errorCode;
    private int errorInfo;
    private int flameStatus;
    private int flueTemp;
    private float frostTemp;
    private int frozenAllow;
    private int frozenState;
    private int functionType;
    private int gasType;
    private int heatAllow;
    private float heatCurrentTemp;
    private float heatMaxSetTemp;
    private float heatMaxTemp;
    private float heatMinTemp;
    private int heatMode;
    private int heatState;
    private int heatStatus;
    private float heatTargetTemp;
    private int holidayEndTime;
    private int holidayStartTime;
    private int holidayState;
    private int installType;
    private boolean isCache = false;
    private boolean isDayLightTime;
    private boolean isDhwMode = false;
    private boolean isHeatingMode = false;
    private boolean isInDayLightTime;
    private boolean isLowBattery;
    private String mDeviceCode;
    private String mProductCode;
    private long mWifiStamp;
    private float manualTemp;
    private int mode;
    private float nightTemp;
    private float offFrostTemp;
    private int outProbeModel;
    private float outTemp;
    private int outTemperaturPayState;
    private float outWaterTemp;
    private int override;
    private float overrideTemp;
    private int prestart;
    private int[][] programs = ((int[][]) Array.newInstance(int.class, new int[]{7, 48}));
    private int pwm;
    private int relayState;
    private float returnTemp;
    private int returnTempOff;
    private int returnTempOn;
    private boolean roomThermostatState;
    private int season;
    private int sensorInfluence;
    private int sensorState;
    private String ssid;
    private int systemTime;
    private float targetTemp;
    private int tempBand;
    private int tempChangeRate;
    private int tempCurve;
    private int temporaryTempState;
    private int therHeatStatus;
    private int unit;
    private float waterPressure;
    private float waterRate;
    private int waterSwitchState;
    private int wifiSignal;
    private int windPressureState;

    public static String getTAG() {
        return TAG;
    }

    public boolean isHeatingMode() {
        return this.isHeatingMode;
    }

    public void setHeatingMode(boolean z) {
        this.isHeatingMode = z;
    }

    public boolean isDhwMode() {
        return this.isDhwMode;
    }

    public void setDhwMode(boolean z) {
        this.isDhwMode = z;
    }

    public boolean isRoomThermostatState() {
        return this.roomThermostatState;
    }

    public void setRoomThermostatState(boolean z) {
        this.roomThermostatState = z;
    }

    public int getWifiSignal() {
        return this.wifiSignal;
    }

    public void setWifiSignal(int i) {
        this.wifiSignal = i;
    }

    public int getPwm() {
        return this.pwm;
    }

    public void setPwm(int i) {
        this.pwm = i;
    }

    public int getFlameStatus() {
        return this.flameStatus;
    }

    public void setFlameStatus(int i) {
        this.flameStatus = i;
    }

    public int getHeatStatus() {
        return this.heatStatus;
    }

    public void setHeatStatus(int i) {
        this.heatStatus = i;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(int i) {
        this.errorCode = i;
    }

    public float getHeatCurrentTemp() {
        return this.heatCurrentTemp;
    }

    public void setHeatCurrentTemp(float f) {
        this.heatCurrentTemp = f;
    }

    public float getHeatTargetTemp() {
        return this.heatTargetTemp;
    }

    public void setHeatTargetTemp(float f) {
        this.heatTargetTemp = f;
    }

    public float getHeatMaxTemp() {
        float f = this.heatMaxTemp;
        if (f == 0.0f) {
            return 85.0f;
        }
        return f;
    }

    public void setHeatMaxTemp(float f) {
        this.heatMaxTemp = f;
    }

    public float getHeatMinTemp() {
        float f = this.heatMinTemp;
        if (f == 0.0f) {
            return 20.0f;
        }
        return f;
    }

    public void setHeatMinTemp(float f) {
        this.heatMinTemp = f;
    }

    public float getBathCurrentTemp() {
        return this.bathCurrentTemp;
    }

    public void setBathCurrentTemp(float f) {
        this.bathCurrentTemp = f;
    }

    public float getBathTargetTemp() {
        return this.bathTargetTemp;
    }

    public void setBathTargetTemp(float f) {
        this.bathTargetTemp = f;
    }

    public float getBathMaxTemp() {
        float f = this.bathMaxTemp;
        if (f == 0.0f) {
            return 60.0f;
        }
        return f;
    }

    public void setBathMaxTemp(float f) {
        this.bathMaxTemp = f;
    }

    public float getBathMinTemp() {
        float f = this.bathMinTemp;
        if (f == 0.0f) {
            return 20.0f;
        }
        return f;
    }

    public void setBathMinTemp(float f) {
        this.bathMinTemp = f;
    }

    public float getWaterPressure() {
        return this.waterPressure;
    }

    public void setWaterPressure(float f) {
        this.waterPressure = f;
    }

    public String getmProductCode() {
        return this.mProductCode;
    }

    public void setmProductCode(String str) {
        this.mProductCode = str;
    }

    public String getmDeviceCode() {
        return this.mDeviceCode;
    }

    public void setmDeviceCode(String str) {
        this.mDeviceCode = str;
    }

    public float getHeatMaxSetTemp() {
        float f = this.heatMaxSetTemp;
        if (f == 0.0f) {
            return 80.0f;
        }
        return f;
    }

    public void setHeatMaxSetTemp(float f) {
        this.heatMaxSetTemp = f;
    }

    public int getWaterSwitchState() {
        return this.waterSwitchState;
    }

    public void setWaterSwitchState(int i) {
        this.waterSwitchState = i;
    }

    public int getControlType() {
        return this.controlType;
    }

    public void setControlType(int i) {
        this.controlType = i;
    }

    public int getInstallType() {
        return this.installType;
    }

    public void setInstallType(int i) {
        this.installType = i;
    }

    public int getFunctionType() {
        return this.functionType;
    }

    public void setFunctionType(int i) {
        this.functionType = i;
    }

    public int getGasType() {
        return this.gasType;
    }

    public void setGasType(int i) {
        this.gasType = i;
    }

    public int getErrorInfo() {
        return this.errorInfo;
    }

    public void setErrorInfo(int i) {
        this.errorInfo = i;
    }

    public int getWindPressureState() {
        return this.windPressureState;
    }

    public void setWindPressureState(int i) {
        this.windPressureState = i;
    }

    public float getReturnTemp() {
        return this.returnTemp;
    }

    public void setReturnTemp(float f) {
        this.returnTemp = f;
    }

    public float getOutWaterTemp() {
        return this.outWaterTemp;
    }

    public void setOutWaterTemp(float f) {
        this.outWaterTemp = f;
    }

    public int getFlueTemp() {
        return this.flueTemp;
    }

    public void setFlueTemp(int i) {
        this.flueTemp = i;
    }

    public float getWaterRate() {
        return this.waterRate;
    }

    public void setWaterRate(float f) {
        this.waterRate = f;
    }

    public int getOutTemperaturPayState() {
        return this.outTemperaturPayState;
    }

    public void setOutTemperaturPayState(int i) {
        this.outTemperaturPayState = i;
    }

    public int getBoilerRelayState() {
        return this.boilerRelayState;
    }

    public void setBoilerRelayState(int i) {
        this.boilerRelayState = i;
    }

    public int getOutProbeModel() {
        return this.outProbeModel;
    }

    public void setOutProbeModel(int i) {
        this.outProbeModel = i;
    }

    public boolean isLowBattery() {
        return this.isLowBattery;
    }

    public void setLowBattery(boolean z) {
        this.isLowBattery = z;
    }

    public boolean isInDayLightTime() {
        return this.isInDayLightTime;
    }

    public void setInDayLightTime(boolean z) {
        this.isInDayLightTime = z;
    }

    public float getOutTemp() {
        return this.outTemp;
    }

    public void setOutTemp(float f) {
        this.outTemp = f;
    }

    public int getMode() {
        return this.mode;
    }

    public void setMode(int i) {
        this.mode = i;
    }

    public int getUnit() {
        return this.unit;
    }

    public void setUnit(int i) {
        this.unit = i;
    }

    public float getManualTemp() {
        return this.manualTemp;
    }

    public void setManualTemp(float f) {
        this.manualTemp = f;
    }

    public float getCurrentTemp() {
        return this.currentTemp;
    }

    public void setCurrentTemp(float f) {
        this.currentTemp = f;
    }

    public float getNightTemp() {
        return this.nightTemp;
    }

    public void setNightTemp(float f) {
        this.nightTemp = f;
    }

    public float getTargetTemp() {
        return this.targetTemp;
    }

    public void setTargetTemp(float f) {
        this.targetTemp = f;
    }

    public float getMaxTargetTemp() {
        return this.MaxTargetTemp;
    }

    public void setMaxTargetTemp(float f) {
        this.MaxTargetTemp = f;
    }

    public float getMinTargetTemp() {
        return this.MinTargetTemp;
    }

    public void setMinTargetTemp(float f) {
        this.MinTargetTemp = f;
    }

    public float getComfortTemp() {
        return this.comfortTemp;
    }

    public void setComfortTemp(float f) {
        this.comfortTemp = f;
    }

    public float getEconTemp() {
        return this.econTemp;
    }

    public void setEconTemp(float f) {
        this.econTemp = f;
    }

    public float getFrostTemp() {
        return this.frostTemp;
    }

    public void setFrostTemp(float f) {
        this.frostTemp = f;
    }

    public float getOffFrostTemp() {
        return this.offFrostTemp;
    }

    public void setOffFrostTemp(float f) {
        this.offFrostTemp = f;
    }

    public int getBoost() {
        return this.boost;
    }

    public void setBoost(int i) {
        this.boost = i;
    }

    public int getOverride() {
        return this.override;
    }

    public void setOverride(int i) {
        this.override = i;
    }

    public float getOverrideTemp() {
        return this.overrideTemp;
    }

    public void setOverrideTemp(float f) {
        this.overrideTemp = f;
    }

    public int getSeason() {
        return this.season;
    }

    public void setSeason(int i) {
        this.season = i;
    }

    public int getHeatMode() {
        return this.heatMode;
    }

    public void setHeatMode(int i) {
        this.heatMode = i;
    }

    public int getHeatAllow() {
        return this.heatAllow;
    }

    public void setHeatAllow(int i) {
        this.heatAllow = i;
    }

    public int getHeatState() {
        return this.heatState;
    }

    public void setHeatState(int i) {
        this.heatState = i;
    }

    public int getRelayState() {
        return this.relayState;
    }

    public void setRelayState(int i) {
        this.relayState = i;
    }

    public int getFrozenState() {
        return this.frozenState;
    }

    public void setFrozenState(int i) {
        this.frozenState = i;
    }

    public int getFrozenAllow() {
        return this.frozenAllow;
    }

    public void setFrozenAllow(int i) {
        this.frozenAllow = i;
    }

    public int getTempBand() {
        return this.tempBand;
    }

    public void setTempBand(int i) {
        this.tempBand = i;
    }

    public int getTempChangeRate() {
        return this.tempChangeRate;
    }

    public void setTempChangeRate(int i) {
        this.tempChangeRate = i;
    }

    public int getTempCurve() {
        return this.tempCurve;
    }

    public void setTempCurve(int i) {
        this.tempCurve = i;
    }

    public int getSensorInfluence() {
        return this.sensorInfluence;
    }

    public void setSensorInfluence(int i) {
        this.sensorInfluence = i;
    }

    public int getSensorState() {
        return this.sensorState;
    }

    public void setSensorState(int i) {
        this.sensorState = i;
    }

    public int getReturnTempOn() {
        return this.returnTempOn;
    }

    public void setReturnTempOn(int i) {
        this.returnTempOn = i;
    }

    public int getReturnTempOff() {
        return this.returnTempOff;
    }

    public void setReturnTempOff(int i) {
        this.returnTempOff = i;
    }

    public int getPrestart() {
        return this.prestart;
    }

    public void setPrestart(int i) {
        this.prestart = i;
    }

    public int getBurnPeriod() {
        return this.burnPeriod;
    }

    public void setBurnPeriod(int i) {
        this.burnPeriod = i;
    }

    public int getTemporaryTempState() {
        return this.temporaryTempState;
    }

    public void setTemporaryTempState(int i) {
        this.temporaryTempState = i;
    }

    public int getHolidayState() {
        return this.holidayState;
    }

    public void setHolidayState(int i) {
        this.holidayState = i;
    }

    public int getHolidayEndTime() {
        return this.holidayEndTime;
    }

    public void setHolidayEndTime(int i) {
        this.holidayEndTime = i;
    }

    public int getHolidayStartTime() {
        return this.holidayStartTime;
    }

    public void setHolidayStartTime(int i) {
        this.holidayStartTime = i;
    }

    public String getSsid() {
        return this.ssid;
    }

    public void setSsid(String str) {
        this.ssid = str;
    }

    public boolean isDayLightTime() {
        return this.isDayLightTime;
    }

    public void setDayLightTime(boolean z) {
        this.isDayLightTime = z;
    }

    public int getSystemTime() {
        return this.systemTime;
    }

    public void setSystemTime(int i) {
        this.systemTime = i;
    }

    public long getmWifiStamp() {
        return this.mWifiStamp;
    }

    public void setmWifiStamp(long j) {
        this.mWifiStamp = j;
    }

    public boolean isCache() {
        return this.isCache;
    }

    public void setCache(boolean z) {
        this.isCache = z;
    }

    public int[] getProgram(int i) {
        if (i < 0 || i > 6) {
            i = 0;
        }
        return this.programs[i];
    }

    public int[][] getProgram() {
        int[][] iArr = (int[][]) Array.newInstance(int.class, new int[]{7, 48});
        for (int i = 0; i < 7; i++) {
            for (int i2 = 0; i2 < 48; i2++) {
                iArr[i][i2] = this.programs[i][i2];
            }
        }
        return iArr;
    }

    public void setProgram(int i, int i2, int i3) {
        if (i >= 0 && i <= 6 && i2 <= 47 && i2 >= 0) {
            this.programs[i][i2] = i3;
        }
    }

    public void setProgram(int i, int[] iArr) {
        if (i < 0 || i > 6) {
            i = 0;
        }
        this.programs[i] = iArr;
    }

    public int getTherHeatStatus() {
        return this.therHeatStatus;
    }

    public void setTherHeatStatus(int i) {
        this.therHeatStatus = i;
    }

    public void parseData(MessagePacket messagePacket) {
        for (Message next : messagePacket.getMessages()) {
            String str = "ON";
            int i = 1;
            if (next instanceof FlameStatus) {
                this.flameStatus = ((FlameStatus) next).isFiring() ? 1 : 0;
                StringBuilder sb = new StringBuilder();
                sb.append("火焰状态：");
                if (this.flameStatus != 1) {
                    str = "OFF";
                }
                sb.append(str);
                LogUtil.m3315d(TAG, sb.toString());
            } else if (next instanceof WarmingUpStatus) {
                this.heatStatus = ((WarmingUpStatus) next).isOpen() ? 1 : 0;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("供暖状态：");
                if (this.heatStatus != 1) {
                    str = "OFF";
                }
                sb2.append(str);
                LogUtil.m3315d(TAG, sb2.toString());
            } else if (next instanceof ErrorCode) {
                this.errorCode = ((ErrorCode) next).getCode();
                LogUtil.m3315d(TAG, "错误代码：" + this.errorCode);
            } else if (next instanceof WarmingWaterRealTemperature) {
                this.heatCurrentTemp = ((WarmingWaterRealTemperature) next).getValue();
                LogUtil.m3315d(TAG, "采暖水当前温度：" + this.heatCurrentTemp);
            } else if (next instanceof WarmingWaterTargetTemperature) {
                this.heatTargetTemp = ((WarmingWaterTargetTemperature) next).getValue();
                LogUtil.m3315d(TAG, "采暖水目标温度：" + this.heatTargetTemp);
            } else if (next instanceof WarmingWaterMaxTemperature) {
                this.heatMaxTemp = (float) ((WarmingWaterMaxTemperature) next).getValue();
                LogUtil.m3315d(TAG, "采暖水最大温度：" + this.heatMaxTemp);
            } else if (next instanceof WarmingWaterMinTemperature) {
                this.heatMinTemp = (float) ((WarmingWaterMinTemperature) next).getValue();
                LogUtil.m3315d(TAG, "采暖水最小温度：" + this.heatMinTemp);
            } else if (next instanceof BathWaterRealTemperature) {
                this.bathCurrentTemp = ((BathWaterRealTemperature) next).getValue();
                LogUtil.m3315d(TAG, "卫浴水当前温度：" + this.bathCurrentTemp);
            } else if (next instanceof BathWaterTargetTemperature) {
                this.bathTargetTemp = ((BathWaterTargetTemperature) next).getValue();
                LogUtil.m3315d(TAG, "卫浴水目标温度：" + this.bathTargetTemp);
            } else if (next instanceof BathWaterMaxTemperature) {
                this.bathMaxTemp = (float) ((BathWaterMaxTemperature) next).getValue();
                LogUtil.m3315d(TAG, "卫浴水最大温度：" + this.bathMaxTemp);
            } else if (next instanceof BathWaterMinTemperature) {
                this.bathMinTemp = (float) ((BathWaterMinTemperature) next).getValue();
                LogUtil.m3315d(TAG, "卫浴水最小温度：" + this.bathMinTemp);
            } else if (next instanceof OutTemperaturePayState) {
                this.outTemperaturPayState = ((OutTemperaturePayState) next).isOpen() ? 1 : 0;
                LogUtil.m3315d(TAG, "锅炉探头是否启用：" + this.outTemperaturPayState);
            } else if (next instanceof OutTemperature) {
                this.outTemp = ((OutTemperature) next).getValue();
                LogUtil.m3315d(TAG, "室外温度：" + this.outTemp);
            } else if (next instanceof WaterPressure) {
                this.waterPressure = ((WaterPressure) next).getValue();
                LogUtil.m3315d(TAG, "水压：" + this.waterPressure);
            } else if (next instanceof WinterSummerMode) {
                this.mode = ((WinterSummerMode) next).getMode();
                LogUtil.m3315d(TAG, "工作模式：" + this.mode);
            } else if (next instanceof WaterSupplySwitch) {
                this.waterSwitchState = ((WaterSupplySwitch) next).isOpen() ? 1 : 0;
                StringBuilder sb3 = new StringBuilder();
                sb3.append("水流开关：");
                if (this.waterSwitchState != 1) {
                    str = "OFF";
                }
                sb3.append(str);
                LogUtil.m3315d(TAG, sb3.toString());
            } else if (next instanceof WarmingWaterMaxSettingTemperature) {
                this.heatMaxSetTemp = ((WarmingWaterMaxSettingTemperature) next).getValue();
                LogUtil.m3315d(TAG, "采暖水温度最大设置点：" + this.heatMaxSetTemp);
            } else if (next instanceof PWMValue) {
                this.pwm = ((PWMValue) next).get();
                LogUtil.m3315d(TAG, "比例阀百分比：" + this.pwm);
            } else {
                boolean z = next instanceof BoilerControlType;
                String str2 = Constant.BOILER_TPE_OT;
                if (z) {
                    this.controlType = ((BoilerControlType) next).isOTType() ? 1 : 0;
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("锅炉类型：");
                    if (this.controlType != 1) {
                        str2 = Constant.BOILER_TYPE_ON_OFF;
                    }
                    sb4.append(str2);
                    LogUtil.m3315d(TAG, sb4.toString());
                } else if (z) {
                    this.controlType = ((BoilerControlType) next).isOTType() ? 1 : 0;
                    StringBuilder sb5 = new StringBuilder();
                    sb5.append("锅炉类型：");
                    if (this.controlType != 1) {
                        str2 = Constant.BOILER_TYPE_ON_OFF;
                    }
                    sb5.append(str2);
                    LogUtil.m3315d(TAG, sb5.toString());
                } else if (next instanceof DeviceInstallationType) {
                    this.installType = ((DeviceInstallationType) next).isWallType() ? 1 : 0;
                    StringBuilder sb6 = new StringBuilder();
                    sb6.append("安装方式：");
                    sb6.append(this.installType == 1 ? "壁挂" : "地暖");
                    LogUtil.m3315d(TAG, sb6.toString());
                } else if (next instanceof DeviceFunctionType) {
                    this.functionType = ((DeviceFunctionType) next).isMultipleType() ? 1 : 0;
                    StringBuilder sb7 = new StringBuilder();
                    sb7.append("机器类型：");
                    sb7.append(this.functionType == 1 ? "两用" : "单暖");
                    LogUtil.m3315d(TAG, sb7.toString());
                } else if (next instanceof FuelGasType) {
                    this.gasType = ((FuelGasType) next).isNaturalGas() ? 1 : 0;
                    StringBuilder sb8 = new StringBuilder();
                    sb8.append("燃气类型：");
                    sb8.append(this.gasType == 1 ? "天然气" : "液化气");
                    LogUtil.m3315d(TAG, sb8.toString());
                } else {
                    if (next instanceof ErrorInfo) {
                        char[] errorCArr = ((ErrorInfo) next).getErrorCArr();
                        int length = errorCArr.length;
                        String str3 = errorCArr[length - 2] + "";
                        for (int i2 = 0; i2 < length; i2++) {
                            LogUtil.m3315d(TAG, " CHAR ARR INDEX " + i2 + " VAL = " + errorCArr[i2]);
                        }
                        LogUtil.m3315d(TAG, " 错误类型 length = " + length + " 类型Char = " + str3 + " 补齐后错误信息二进制字符串 = " + Arrays.toString(errorCArr));
                        this.errorInfo = Integer.parseInt(str3);
                        StringBuilder sb9 = new StringBuilder();
                        sb9.append("错误信息：");
                        sb9.append(this.errorInfo);
                        LogUtil.m3315d(TAG, sb9.toString());
                    } else if (next instanceof WindPressureSwitch) {
                        this.windPressureState = ((WindPressureSwitch) next).isOpen() ? 1 : 0;
                        StringBuilder sb10 = new StringBuilder();
                        sb10.append("风压开关状态：");
                        sb10.append(this.windPressureState == 1 ? "开" : "关");
                        LogUtil.m3315d(TAG, sb10.toString());
                    } else if (next instanceof ReturnTemperature) {
                        this.returnTemp = ((ReturnTemperature) next).getValue();
                        LogUtil.m3315d(TAG, "回水温度：" + this.returnTemp);
                    } else if (next instanceof OutWaterTemperature) {
                        this.outWaterTemp = ((OutWaterTemperature) next).getValue();
                        LogUtil.m3315d(TAG, "出水温度：" + this.outWaterTemp);
                    } else if (next instanceof FlueTemperature) {
                        this.flueTemp = ((FlueTemperature) next).getValue();
                        LogUtil.m3315d(TAG, "烟气温度：" + this.flueTemp);
                    } else if (next instanceof WaterRate) {
                        this.waterRate = ((WaterRate) next).getValue();
                        LogUtil.m3315d(TAG, "生活水流量：" + this.waterRate);
                    } else if (next instanceof BoilerRelayState) {
                        if (((BoilerRelayState) next).isOpen()) {
                            i = 2;
                        }
                        this.boilerRelayState = i;
                        LogUtil.m3315d(TAG, "ON/OFF型锅炉开关状态：" + this.boilerRelayState);
                    } else if (next instanceof OutProbeModel) {
                        this.outProbeModel = ((OutProbeModel) next).isOpen() ? 1 : 0;
                        LogUtil.m3315d(TAG, "锅炉探头是否启用：" + this.outProbeModel);
                    } else if (next instanceof WorkMode) {
                        this.mode = ((WorkMode) next).getMode();
                        LogUtil.m3315d(TAG, "工作模式：" + this.mode);
                    } else if (next instanceof Unit) {
                        this.unit = ((Unit) next).isCentigrade() ^ true ? 1 : 0;
                        LogUtil.m3315d(TAG, "单位：" + this.unit);
                    } else if (next instanceof ManualTemp) {
                        this.manualTemp = ((ManualTemp) next).getValue();
                        LogUtil.m3315d(TAG, "手动温度：" + this.manualTemp);
                    } else if (next instanceof NightTemp) {
                        this.nightTemp = ((NightTemp) next).getValue();
                        LogUtil.m3315d(TAG, "晚间温度：" + this.nightTemp);
                    } else if (next instanceof RoomCurrentTemp) {
                        this.currentTemp = ((RoomCurrentTemp) next).getValue();
                        LogUtil.m3315d(TAG, "当前温度：" + this.currentTemp);
                    } else if (next instanceof RoomTargetTemp) {
                        this.targetTemp = ((RoomTargetTemp) next).getValue();
                        LogUtil.m3315d(TAG, "目标温度：" + this.targetTemp);
                    } else if (next instanceof RoomTargetTempMax) {
                        this.MaxTargetTemp = ((RoomTargetTempMax) next).getValue();
                        LogUtil.m3315d(TAG, "目标温度上限：" + this.MaxTargetTemp);
                    } else if (next instanceof RoomTargetTempMin) {
                        this.MinTargetTemp = ((RoomTargetTempMin) next).getValue();
                        LogUtil.m3315d(TAG, "目标温度下限：" + this.MinTargetTemp);
                    } else if (next instanceof ComfortTemp) {
                        ComfortTemp comfortTemp2 = (ComfortTemp) next;
                        if (comfortTemp2.getValue() > 4.0f) {
                            this.comfortTemp = comfortTemp2.getValue();
                        }
                        LogUtil.m3315d(TAG, "舒适温度：" + this.comfortTemp);
                    } else if (next instanceof EconTemp) {
                        EconTemp econTemp2 = (EconTemp) next;
                        if (econTemp2.getValue() > 4.0f) {
                            this.econTemp = econTemp2.getValue();
                        }
                        LogUtil.m3315d(TAG, "节能温度：" + this.econTemp);
                    } else if (next instanceof FrostTemp) {
                        this.frostTemp = ((FrostTemp) next).getValue();
                        LogUtil.m3315d(TAG, "防冻温度：" + this.frostTemp);
                    } else if (next instanceof OffFrostTemp) {
                        this.offFrostTemp = ((OffFrostTemp) next).getValue();
                        LogUtil.m3315d(TAG, "关机防冻温度：" + this.offFrostTemp);
                    } else if (next instanceof Boost) {
                        this.boost = ((Boost) next).isActive() ? 1 : 0;
                        StringBuilder sb11 = new StringBuilder();
                        sb11.append("Boost：");
                        if (this.boost != 1) {
                            str = "OFF";
                        }
                        sb11.append(str);
                        LogUtil.m3315d(TAG, sb11.toString());
                    } else if (next instanceof Override) {
                        this.override = ((Override) next).isActive() ? 1 : 0;
                        StringBuilder sb12 = new StringBuilder();
                        sb12.append("Override：");
                        if (this.override != 1) {
                            str = "OFF";
                        }
                        sb12.append(str);
                        LogUtil.m3315d(TAG, sb12.toString());
                    } else if (next instanceof OverrideTemp) {
                        this.overrideTemp = ((OverrideTemp) next).getValue();
                        LogUtil.m3315d(TAG, "当前临时温度: " + this.overrideTemp);
                    } else if (next instanceof Season) {
                        this.season = ((Season) next).isWinter() ? 1 : 0;
                        StringBuilder sb13 = new StringBuilder();
                        sb13.append("季节：");
                        sb13.append(this.season == 1 ? "冬季" : "夏季");
                        LogUtil.m3315d(TAG, sb13.toString());
                    } else if (next instanceof HeatState) {
                        this.therHeatStatus = ((HeatState) next).isHeating() ? 1 : 0;
                        StringBuilder sb14 = new StringBuilder();
                        sb14.append("供暖状态：");
                        sb14.append(this.heatStatus == 1 ? "正在供暖" : "停止");
                        LogUtil.m3315d(TAG, sb14.toString());
                    } else {
                        String str4 = "允许";
                        if (next instanceof HeatAllow) {
                            this.heatAllow = ((HeatAllow) next).isEnable() ? 1 : 0;
                            StringBuilder sb15 = new StringBuilder();
                            sb15.append("供暖允许：");
                            if (this.heatAllow != 1) {
                                str4 = "禁止";
                            }
                            sb15.append(str4);
                            LogUtil.m3315d(TAG, sb15.toString());
                        } else if (next instanceof HeatMode) {
                            this.heatMode = ((HeatMode) next).getMode();
                            LogUtil.m3315d(TAG, "供暖模式：" + this.heatMode);
                        } else if (next instanceof ThermostatRelayStatus) {
                            this.relayState = ((ThermostatRelayStatus) next).isOpen() ? 1 : 0;
                            StringBuilder sb16 = new StringBuilder();
                            sb16.append("继电器状态：");
                            if (this.relayState != 1) {
                                str = "OFF";
                            }
                            sb16.append(str);
                            LogUtil.m3315d(TAG, sb16.toString());
                        } else if (next instanceof FrozenState) {
                            this.frozenState = ((FrozenState) next).isActive() ? 1 : 0;
                            StringBuilder sb17 = new StringBuilder();
                            sb17.append("防冻状态：");
                            if (this.frozenState != 1) {
                                str = "OFF";
                            }
                            sb17.append(str);
                            LogUtil.m3315d(TAG, sb17.toString());
                        } else if (next instanceof FrozenFunction) {
                            this.frozenAllow = ((FrozenFunction) next).isEnable() ? 1 : 0;
                            StringBuilder sb18 = new StringBuilder();
                            sb18.append("防冻允许：");
                            if (this.frozenAllow != 1) {
                                str4 = "禁止";
                            }
                            sb18.append(str4);
                            LogUtil.m3315d(TAG, sb18.toString());
                        } else if (next instanceof TemperatureBand) {
                            this.tempBand = ((TemperatureBand) next).getBand();
                            LogUtil.m3315d(TAG, "温度带宽：" + this.tempBand);
                        } else if (next instanceof TemperatureChangeRate) {
                            this.tempChangeRate = ((TemperatureChangeRate) next).getPeriod();
                            LogUtil.m3315d(TAG, "温度变化率：" + this.tempChangeRate);
                        } else if (next instanceof TemperatureOffSetCurve) {
                            this.tempCurve = ((TemperatureOffSetCurve) next).getNumber();
                            LogUtil.m3315d(TAG, "温度曲线：" + this.tempCurve);
                        } else if (next instanceof TemporaryTempState) {
                            this.temporaryTempState = ((TemporaryTempState) next).isActive() ? 1 : 0;
                            StringBuilder sb19 = new StringBuilder();
                            sb19.append("临时温度状态：");
                            if (this.temporaryTempState != 1) {
                                str = "OFF";
                            }
                            sb19.append(str);
                            LogUtil.m3315d(TAG, sb19.toString());
                        } else if (next instanceof TempSensorInfluence) {
                            this.sensorInfluence = ((TempSensorInfluence) next).getValue();
                            LogUtil.m3315d(TAG, "传感器影响系数：" + this.sensorInfluence);
                        } else if (next instanceof TempSensorState) {
                            this.sensorState = ((TempSensorState) next).isActive() ? 1 : 0;
                            StringBuilder sb20 = new StringBuilder();
                            sb20.append("传感器状态：");
                            if (this.sensorState != 1) {
                                str = "OFF";
                            }
                            sb20.append(str);
                            LogUtil.m3315d(TAG, sb20.toString());
                        } else if (next instanceof ReturnTempOn) {
                            this.returnTempOn = ((ReturnTempOn) next).getValue();
                            LogUtil.m3315d(TAG, "回差开启温度：" + this.returnTempOn);
                        } else if (next instanceof ReturnTempOff) {
                            this.returnTempOff = ((ReturnTempOff) next).getValue();
                            LogUtil.m3315d(TAG, "回差关闭温度：" + this.returnTempOff);
                        } else if (next instanceof BurnPeriod) {
                            this.burnPeriod = ((BurnPeriod) next).getRate();
                            LogUtil.m3315d(TAG, "燃烧周期：" + this.burnPeriod);
                        } else if (next instanceof PreStart) {
                            this.prestart = ((PreStart) next).getValue();
                            LogUtil.m3315d(TAG, "提前启动时间：" + this.prestart);
                        } else if (next instanceof ThermostatID2) {
                            this.ssid = ((ThermostatID2) next).getId();
                            LogUtil.m3315d(TAG, "温控器ID：" + this.ssid);
                        } else if (next instanceof Holiday) {
                            Holiday holiday = (Holiday) next;
                            this.holidayStartTime = holiday.getStartTime();
                            this.holidayEndTime = holiday.getEndTime();
                            this.holidayState = holiday.getEnable();
                            StringBuilder sb21 = new StringBuilder();
                            sb21.append("度假模式开启状态：");
                            if (this.holidayState != 1) {
                                str = "OFF";
                            }
                            sb21.append(str);
                            LogUtil.m3315d(TAG, sb21.toString());
                            LogUtil.m3315d(TAG, "度假模式开始时间：" + this.holidayStartTime);
                            LogUtil.m3315d(TAG, "度假模式结束时间：" + this.holidayEndTime);
                        } else if (next instanceof SystemTime) {
                            this.systemTime = ((SystemTime) next).getSeconds();
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            LogUtil.m3315d(TAG, "温控器现在时间：" + simpleDateFormat.format(new Date((long) (this.systemTime * 1000))));
                        } else if (next instanceof Program) {
                            Program program = (Program) next;
                            switch (program.getFlag()) {
                                case 4125:
                                    this.programs[0] = program.getData();
                                    break;
                                case 4126:
                                    this.programs[1] = program.getData();
                                    break;
                                case 4127:
                                    this.programs[2] = program.getData();
                                    break;
                                case 4128:
                                    this.programs[3] = program.getData();
                                    break;
                                case 4129:
                                    this.programs[4] = program.getData();
                                    break;
                                case 4130:
                                    this.programs[5] = program.getData();
                                    break;
                                case 4131:
                                    this.programs[6] = program.getData();
                                    break;
                            }
                            LogUtil.m3315d(TAG, "编程数据：flag==" + program.getFlag() + "; datas==" + Arrays.toString(program.getData()));
                        } else if (next instanceof DaylightTime) {
                            this.isDayLightTime = ((DaylightTime) next).isOpen();
                            LogUtil.m3315d(TAG, "夏令时是否启用：" + this.isDayLightTime);
                        } else if (next instanceof InDaylightTime) {
                            this.isInDayLightTime = ((InDaylightTime) next).isInDaylightTime();
                            LogUtil.m3315d(TAG, "当前时间是否处于夏令时: " + this.isInDayLightTime);
                        } else if (next instanceof BatteryUsage) {
                            this.isLowBattery = ((BatteryUsage) next).isLowBattery();
                            LogUtil.m3315d(TAG, "当前是否低电: " + this.isLowBattery);
                        } else if (next instanceof WiFiSignal) {
                            WiFiSignal wiFiSignal = (WiFiSignal) next;
                            this.wifiSignal = wiFiSignal.getStrenght();
                            Constant.wifiSingal = Integer.valueOf(wiFiSignal.getStrenght());
                            LogUtil.m3315d(TAG, "wifi信号强度：" + this.wifiSignal);
                        } else if (next instanceof SlaveOTSate) {
                            SlaveOTSate slaveOTSate = (SlaveOTSate) next;
                            this.isHeatingMode = slaveOTSate.isHeatingModeOn();
                            this.isDhwMode = slaveOTSate.isDhwModeOn();
                        } else if (next instanceof RoomThermostatState) {
                            RoomThermostatState roomThermostatState2 = (RoomThermostatState) next;
                            this.roomThermostatState = roomThermostatState2.isActive();
                            LogUtil.m3315d(TAG, "房间温控器是否有效：" + roomThermostatState2);
                        }
                    }
                }
            }
        }
    }
}
