package com.szacs.ferroliconnect.bean;

import com.szacs.ferroliconnect.util.LogUtil;
import com.topband.sdk.boiler.Message;
import com.topband.sdk.boiler.MessagePacket;
import com.topband.sdk.boiler.message.boiler.OutProbeModel;
import com.topband.sdk.boiler.message.boiler.OutTemperature;
import com.topband.sdk.boiler.message.boiler.OutTemperaturePayState;
import com.topband.sdk.boiler.message.system.DaylightTime;
import com.topband.sdk.boiler.message.system.SystemTime;
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
import com.topband.sdk.boiler.message.thermostat.Override;
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

public class ThermostatBean implements Serializable {
    private static final String TAG = "ThermostatBean";
    private static final long serialVersionUID = 123586937;
    private float MaxTargetTemp;
    private float MinTargetTemp;
    private int boost;
    private int burnPeriod;
    private float comfortTemp;
    private float currentTemp;
    private float econTemp;
    private float frostTemp;
    private int frozenAllow;
    private int frozenState;
    private int heatAllow;
    private int heatMode;
    private int heatState;
    private int holidayEndTime;
    private int holidayStartTime;
    private int holidayState;
    private boolean isCache = false;
    private boolean isDayLightTime;
    private long mWifiStamp;
    private float manualTemp;
    private int mode;
    private float nightTemp;
    private int outProbeModel;
    private float outTemp;
    private int outTemperaturPayState;
    private int override;
    private int prestart;
    private int[][] programs = ((int[][]) Array.newInstance(int.class, new int[]{7, 48}));
    private int relayState;
    private int returnTempOff;
    private int returnTempOn;
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
    private int unit;

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

    public int getOutTemperaturPayState() {
        return this.outTemperaturPayState;
    }

    public void setOutTemperaturPayState(int i) {
        this.outTemperaturPayState = i;
    }

    public int getOutProbeModel() {
        return this.outProbeModel;
    }

    public void setOutProbeModel(int i) {
        this.outProbeModel = i;
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

    public void parseData(MessagePacket messagePacket) {
        for (Message next : messagePacket.getMessages()) {
            if (next instanceof WorkMode) {
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
                FrostTemp frostTemp2 = (FrostTemp) next;
                if (frostTemp2.getValue() > 4.0f) {
                    this.frostTemp = frostTemp2.getValue();
                }
                LogUtil.m3315d(TAG, "防冻温度：" + this.frostTemp);
            } else {
                String str = "ON";
                if (next instanceof Boost) {
                    this.boost = ((Boost) next).isActive() ? 1 : 0;
                    StringBuilder sb = new StringBuilder();
                    sb.append("Boost：");
                    if (this.boost != 1) {
                        str = "OFF";
                    }
                    sb.append(str);
                    LogUtil.m3315d(TAG, sb.toString());
                } else if (next instanceof Override) {
                    this.override = ((Override) next).isActive() ? 1 : 0;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Override：");
                    if (this.override != 1) {
                        str = "OFF";
                    }
                    sb2.append(str);
                    LogUtil.m3315d(TAG, sb2.toString());
                } else if (next instanceof Season) {
                    this.season = ((Season) next).isWinter() ? 1 : 0;
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("季节：");
                    sb3.append(this.season == 1 ? "冬季" : "夏季");
                    LogUtil.m3315d(TAG, sb3.toString());
                } else if (next instanceof HeatState) {
                    this.heatState = ((HeatState) next).isHeating() ? 1 : 0;
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("供暖状态：");
                    sb4.append(this.heatState == 1 ? "正在供暖" : "停止");
                    LogUtil.m3315d(TAG, sb4.toString());
                } else {
                    String str2 = "允许";
                    if (next instanceof HeatAllow) {
                        this.heatAllow = ((HeatAllow) next).isEnable() ? 1 : 0;
                        StringBuilder sb5 = new StringBuilder();
                        sb5.append("供暖允许：");
                        if (this.heatAllow != 1) {
                            str2 = "禁止";
                        }
                        sb5.append(str2);
                        LogUtil.m3315d(TAG, sb5.toString());
                    } else if (next instanceof HeatMode) {
                        this.heatMode = ((HeatMode) next).getMode();
                        LogUtil.m3315d(TAG, "供暖模式：" + this.heatMode);
                    } else if (next instanceof ThermostatRelayStatus) {
                        this.relayState = ((ThermostatRelayStatus) next).isOpen() ? 1 : 0;
                        StringBuilder sb6 = new StringBuilder();
                        sb6.append("继电器状态：");
                        if (this.relayState != 1) {
                            str = "OFF";
                        }
                        sb6.append(str);
                        LogUtil.m3315d(TAG, sb6.toString());
                    } else if (next instanceof FrozenState) {
                        this.frozenState = ((FrozenState) next).isActive() ? 1 : 0;
                        StringBuilder sb7 = new StringBuilder();
                        sb7.append("防冻状态：");
                        if (this.frozenState != 1) {
                            str = "OFF";
                        }
                        sb7.append(str);
                        LogUtil.m3315d(TAG, sb7.toString());
                    } else if (next instanceof FrozenFunction) {
                        this.frozenAllow = ((FrozenFunction) next).isEnable() ? 1 : 0;
                        StringBuilder sb8 = new StringBuilder();
                        sb8.append("防冻允许：");
                        if (this.frozenAllow != 1) {
                            str2 = "禁止";
                        }
                        sb8.append(str2);
                        LogUtil.m3315d(TAG, sb8.toString());
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
                        StringBuilder sb9 = new StringBuilder();
                        sb9.append("临时温度状态：");
                        if (this.temporaryTempState != 1) {
                            str = "OFF";
                        }
                        sb9.append(str);
                        LogUtil.m3315d(TAG, sb9.toString());
                    } else if (next instanceof TempSensorInfluence) {
                        this.sensorInfluence = ((TempSensorInfluence) next).getValue();
                        LogUtil.m3315d(TAG, "传感器影响系数：" + this.sensorInfluence);
                    } else if (next instanceof TempSensorState) {
                        this.sensorState = ((TempSensorState) next).isActive() ? 1 : 0;
                        StringBuilder sb10 = new StringBuilder();
                        sb10.append("传感器状态：");
                        if (this.sensorState != 1) {
                            str = "OFF";
                        }
                        sb10.append(str);
                        LogUtil.m3315d(TAG, sb10.toString());
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
                        StringBuilder sb11 = new StringBuilder();
                        sb11.append("度假模式开启状态：");
                        if (this.holidayState != 1) {
                            str = "OFF";
                        }
                        sb11.append(str);
                        LogUtil.m3315d(TAG, sb11.toString());
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
                    } else if (next instanceof OutTemperaturePayState) {
                        this.outTemperaturPayState = ((OutTemperaturePayState) next).isOpen() ? 1 : 0;
                        LogUtil.m3315d(TAG, "锅炉探头是否启用：" + this.outTemperaturPayState);
                    } else if (next instanceof OutTemperature) {
                        this.outTemp = ((OutTemperature) next).getValue();
                        LogUtil.m3315d(TAG, "室外温度：" + this.outTemp);
                    } else if (next instanceof OutProbeModel) {
                        OutProbeModel outProbeModel2 = (OutProbeModel) next;
                        this.outProbeModel = outProbeModel2.isOpen() ? 1 : 0;
                        LogUtil.m3315d(TAG, "锅炉探头是否启用：" + outProbeModel2);
                    } else if (next instanceof DaylightTime) {
                        this.isDayLightTime = ((DaylightTime) next).isOpen();
                        LogUtil.m3315d(TAG, "夏令时是否启用：" + this.isDayLightTime);
                    }
                }
            }
        }
    }
}
