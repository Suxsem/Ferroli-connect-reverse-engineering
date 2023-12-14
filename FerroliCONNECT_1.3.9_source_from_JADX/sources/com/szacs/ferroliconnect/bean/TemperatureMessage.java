package com.szacs.ferroliconnect.bean;

import android.text.TextUtils;
import com.google.gson.annotations.SerializedName;
import com.szacs.ferroliconnect.util.Constant;
import com.topband.sdk.boiler.FormatUtil;
import com.topband.sdk.boiler.MessageFormatException;
import com.topband.sdk.boiler.message.boiler.OutTemperature;
import com.topband.sdk.boiler.message.thermostat.ComfortTemp;
import com.topband.sdk.boiler.message.thermostat.EconTemp;
import com.topband.sdk.boiler.message.thermostat.FrostTemp;
import com.topband.sdk.boiler.message.thermostat.Holiday;
import com.topband.sdk.boiler.message.thermostat.ManualTemp;
import com.topband.sdk.boiler.message.thermostat.OffFrostTemp;
import com.topband.sdk.boiler.message.thermostat.OverrideTemp;
import com.topband.sdk.boiler.message.thermostat.Program;
import com.topband.sdk.boiler.message.thermostat.RoomCurrentTemp;
import com.topband.sdk.boiler.message.thermostat.RoomTargetTemp;
import java.io.Serializable;
import java.lang.reflect.Array;

public class TemperatureMessage implements Serializable {
    public static final short TPI_TEMP_BAND = 4148;
    private static final long serialVersionUID = 1;
    @SerializedName("0013")
    private String flagBatteryState;
    @SerializedName("0005")
    private String flagDaylightTime;
    @SerializedName("2030")
    private String flagOutTempProbeModel;
    @SerializedName("200C")
    private String flagOutTemperature;
    @SerializedName("2020")
    private String flagWarmingUpStatus;
    @SerializedName("1027")
    private String frostzenAllow;
    @SerializedName("1028")
    private String frostzenState;
    @SerializedName("1033")
    private String heatMode;
    @SerializedName("1026")
    private String heatOptimizeStart;
    @SerializedName("0006")
    private String inDaylightTime;
    @SerializedName("1039")
    private String offFrostTemp;
    @SerializedName("1008")
    private String roomAllowHeat;
    @SerializedName("101A")
    private String roomBoostEndTime;
    @SerializedName("1018")
    private String roomBoostState;
    @SerializedName("1019")
    private String roomBoostTemp;
    @SerializedName("1004")
    private String roomComfortTemp;
    @SerializedName("1000")
    private String roomCurrentTemp;
    @SerializedName("1015")
    private String roomDhwProgram;
    @SerializedName("1005")
    private String roomEconTemp;
    @SerializedName("1006")
    private String roomFrostTemp;
    @SerializedName("1009")
    private String roomHeatStatus;
    @SerializedName("101B")
    private String roomHolidayData;
    @SerializedName("1010")
    private String roomManualTemp;
    @SerializedName("1007")
    private String roomNightTemp;
    @SerializedName("1016")
    private String roomOverrideState;
    @SerializedName("1017")
    private String roomOverrideTemp;
    @SerializedName("1014")
    private String roomSeason;
    @SerializedName("1001")
    private String roomTargetTemp;
    @SerializedName("1002")
    private String roomTargetTempMax;
    @SerializedName("1003")
    private String roomTargetTempMin;
    @SerializedName("1012")
    private String roomTempCurve;
    @SerializedName("100D")
    private String roomTempReturnOff;
    @SerializedName("100C")
    private String roomTempReturnOn;
    @SerializedName("1013")
    private String roomTempSensorInfluces;
    @SerializedName("100E")
    private String roomTempSensorStatus;
    @SerializedName("100B")
    private String roomTempUnit;
    @SerializedName("1011")
    private String roomTemporaryTempStatus;
    @SerializedName("100F")
    private String roomWorkMode;
    @SerializedName("1030")
    private String salveAvoidStuck;
    @SerializedName("102E")
    private String salveMasterAddress;
    @SerializedName("102F")
    private String salveMasterInfo;
    @SerializedName("1024")
    private String tempDhwMax;
    @SerializedName("1025")
    private String tempDhwMin;
    @SerializedName("1022")
    private String tempHeatProgramFri;
    @SerializedName("101E")
    private String tempHeatProgramMon;
    @SerializedName("1023")
    private String tempHeatProgramSat;
    @SerializedName("101D")
    private String tempHeatProgramSun;
    @SerializedName("1021")
    private String tempHeatProgramThur;
    @SerializedName("101F")
    private String tempHeatProgramTue;
    @SerializedName("1020")
    private String tempHeatProgramWeb;
    @SerializedName("1036")
    private String temperatureChangeRatio;
    @SerializedName("102D")
    private String thermostatList;
    @SerializedName("100A")
    private String thermostatRelayState;
    @SerializedName("1035")
    private String tpiBurnPeriod;
    @SerializedName("1034")
    private String tpiTempBand;
    private String utcMilliseconds;
    @SerializedName("1029")
    private String valveCurrnetPos;
    @SerializedName("102C")
    private String valveFullPosAllow;
    @SerializedName("101C")
    private String valveOpenPercent;
    @SerializedName("102A")
    private String valveTargetPos;
    @SerializedName("102B")
    private String valveTargetRange;
    @SerializedName("0018")
    private String wifiSignal;
    @SerializedName("1031")
    private String windowDetect;
    @SerializedName("1032")
    private String windowState;

    public int getHeatState() {
        if (isStringEmpty(this.flagWarmingUpStatus)) {
            return 0;
        }
        return Integer.parseInt(this.flagWarmingUpStatus, 16);
    }

    public float getRoomCurrentTemp() {
        if (isStringEmpty(this.roomCurrentTemp)) {
            return 0.0f;
        }
        RoomCurrentTemp roomCurrentTemp2 = new RoomCurrentTemp();
        try {
            roomCurrentTemp2.onParseData(FormatUtil.getHexData(this.roomCurrentTemp));
            return roomCurrentTemp2.getValue();
        } catch (MessageFormatException e) {
            e.printStackTrace();
            return 0.0f;
        }
    }

    public float getTargetTemp() {
        if (isStringEmpty(this.roomTargetTemp)) {
            return 0.0f;
        }
        RoomTargetTemp roomTargetTemp2 = new RoomTargetTemp();
        try {
            roomTargetTemp2.onParseData(FormatUtil.getHexData(this.roomTargetTemp));
            return roomTargetTemp2.getValue();
        } catch (MessageFormatException e) {
            e.printStackTrace();
            return 0.0f;
        }
    }

    public float getRoomComfortTemp() {
        if (isStringEmpty(this.roomComfortTemp)) {
            return 0.0f;
        }
        ComfortTemp comfortTemp = new ComfortTemp();
        try {
            comfortTemp.onParseData(FormatUtil.getHexData(this.roomComfortTemp));
            float value = comfortTemp.getValue();
            if (value > 4.0f) {
                return comfortTemp.getValue();
            }
            return value;
        } catch (MessageFormatException e) {
            e.printStackTrace();
            return 0.0f;
        }
    }

    public int getTherHeatStatus() {
        if (isStringEmpty(this.roomHeatStatus)) {
            return 0;
        }
        return Integer.parseInt(this.roomHeatStatus, 16);
    }

    public int getRoomWorkMode() {
        if (isStringEmpty(this.roomWorkMode)) {
            return 0;
        }
        return Integer.parseInt(this.roomWorkMode, 16);
    }

    public float getRoomManualTemp() {
        if (isStringEmpty(this.roomManualTemp)) {
            return 0.0f;
        }
        ManualTemp manualTemp = new ManualTemp();
        try {
            manualTemp.onParseData(FormatUtil.getHexData(this.roomManualTemp));
            return manualTemp.getValue();
        } catch (MessageFormatException e) {
            e.printStackTrace();
            return 0.0f;
        }
    }

    public Integer getRoomHolidayData() {
        if (isStringEmpty(this.roomHolidayData)) {
            return 0;
        }
        Holiday holiday = new Holiday();
        try {
            holiday.onParseData(FormatUtil.getHexData(this.roomHolidayData));
            return Integer.valueOf(holiday.getEndTime());
        } catch (MessageFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getWifiSignal() {
        if (isStringEmpty(this.wifiSignal)) {
            return 0;
        }
        Constant.wifiSingal = Integer.valueOf(Integer.parseInt(this.wifiSignal, 16));
        return Integer.parseInt(this.wifiSignal, 16);
    }

    public int getOutProbeModel() {
        if (isStringEmpty(this.flagOutTempProbeModel)) {
            return 0;
        }
        return Integer.parseInt(this.flagOutTempProbeModel, 16);
    }

    public float getOutTemp() {
        if (isStringEmpty(this.flagOutTemperature)) {
            return 0.0f;
        }
        OutTemperature outTemperature = new OutTemperature();
        try {
            outTemperature.onParseData(FormatUtil.getHexData(this.flagOutTemperature));
            return outTemperature.getValue();
        } catch (MessageFormatException e) {
            e.printStackTrace();
            return 0.0f;
        }
    }

    public boolean isDayLightTime() {
        if (!isStringEmpty(this.flagDaylightTime) && Integer.parseInt(this.flagDaylightTime, 16) == 1) {
            return true;
        }
        return false;
    }

    public boolean isLowBattery() {
        if (!isStringEmpty(this.flagBatteryState) && Integer.parseInt(this.flagBatteryState, 16) == 1) {
            return true;
        }
        return false;
    }

    private boolean isStringEmpty(String str) {
        return TextUtils.isEmpty(str);
    }

    public boolean isInDayLightTime() {
        if (!isStringEmpty(this.inDaylightTime) && Integer.parseInt(this.inDaylightTime, 16) == 1) {
            return true;
        }
        return false;
    }

    public int[] getProgram(int i) {
        int[][] iArr = (int[][]) Array.newInstance(int.class, new int[]{7, 48});
        if (i < 0 || i > 6) {
            i = 0;
        }
        Program newInstance = Program.newInstance(i);
        try {
            switch (newInstance.getFlag()) {
                case 4125:
                    if (!isStringEmpty(this.tempHeatProgramSun)) {
                        newInstance.onParseData(FormatUtil.getHexData(this.tempHeatProgramSun));
                        iArr[0] = newInstance.getData();
                        break;
                    } else {
                        break;
                    }
                case 4126:
                    if (!isStringEmpty(this.tempHeatProgramMon)) {
                        newInstance.onParseData(FormatUtil.getHexData(this.tempHeatProgramMon));
                        iArr[1] = newInstance.getData();
                        break;
                    } else {
                        break;
                    }
                case 4127:
                    if (!isStringEmpty(this.tempHeatProgramTue)) {
                        newInstance.onParseData(FormatUtil.getHexData(this.tempHeatProgramTue));
                        iArr[2] = newInstance.getData();
                        break;
                    } else {
                        break;
                    }
                case 4128:
                    if (!isStringEmpty(this.tempHeatProgramWeb)) {
                        newInstance.onParseData(FormatUtil.getHexData(this.tempHeatProgramWeb));
                        iArr[3] = newInstance.getData();
                        break;
                    } else {
                        break;
                    }
                case 4129:
                    if (!isStringEmpty(this.tempHeatProgramThur)) {
                        newInstance.onParseData(FormatUtil.getHexData(this.tempHeatProgramThur));
                        iArr[4] = newInstance.getData();
                        break;
                    } else {
                        break;
                    }
                case 4130:
                    if (!isStringEmpty(this.tempHeatProgramFri)) {
                        newInstance.onParseData(FormatUtil.getHexData(this.tempHeatProgramFri));
                        iArr[5] = newInstance.getData();
                        break;
                    } else {
                        break;
                    }
                case 4131:
                    if (!isStringEmpty(this.tempHeatProgramSat)) {
                        newInstance.onParseData(FormatUtil.getHexData(this.tempHeatProgramSat));
                        iArr[6] = newInstance.getData();
                        break;
                    } else {
                        break;
                    }
                default:
                    if (!isStringEmpty(this.tempHeatProgramSun)) {
                        newInstance.onParseData(FormatUtil.getHexData(this.tempHeatProgramSun));
                        iArr[0] = newInstance.getData();
                        break;
                    } else {
                        break;
                    }
            }
        } catch (MessageFormatException e) {
            e.printStackTrace();
        }
        return iArr[i];
    }

    public float getFrostTemp() {
        if (isStringEmpty(this.roomFrostTemp)) {
            return 0.0f;
        }
        FrostTemp frostTemp = new FrostTemp();
        try {
            frostTemp.onParseData(FormatUtil.getHexData(this.roomFrostTemp));
            return frostTemp.getValue();
        } catch (MessageFormatException e) {
            e.printStackTrace();
            return 0.0f;
        }
    }

    public float getEconTemp() {
        if (isStringEmpty(this.roomEconTemp)) {
            return 0.0f;
        }
        EconTemp econTemp = new EconTemp();
        try {
            econTemp.onParseData(FormatUtil.getHexData(this.roomEconTemp));
            if (econTemp.getValue() > 4.0f) {
                return econTemp.getValue();
            }
            return 0.0f;
        } catch (MessageFormatException e) {
            e.printStackTrace();
            return 0.0f;
        }
    }

    public float getComfortTemp() {
        if (isStringEmpty(this.roomComfortTemp)) {
            return 0.0f;
        }
        ComfortTemp comfortTemp = new ComfortTemp();
        try {
            comfortTemp.onParseData(FormatUtil.getHexData(this.roomComfortTemp));
            if (comfortTemp.getValue() > 4.0f) {
                return comfortTemp.getValue();
            }
            return 0.0f;
        } catch (MessageFormatException e) {
            e.printStackTrace();
            return 0.0f;
        }
    }

    public int getMode() {
        if (isStringEmpty(this.roomWorkMode)) {
            return 0;
        }
        return Integer.parseInt(this.roomWorkMode, 16);
    }

    public int getOverride() {
        if (!isStringEmpty(this.roomOverrideState) && Integer.parseInt(this.roomOverrideState, 16) == 1) {
            return 1;
        }
        return 0;
    }

    public float getOverrideTemp() {
        if (isStringEmpty(this.roomOverrideTemp)) {
            return 0.0f;
        }
        OverrideTemp overrideTemp = new OverrideTemp();
        try {
            overrideTemp.onParseData(FormatUtil.getHexData(this.roomOverrideTemp));
            return overrideTemp.getValue();
        } catch (MessageFormatException e) {
            e.printStackTrace();
            return 0.0f;
        }
    }

    public float getOffFrostTemp() {
        if (isStringEmpty(this.offFrostTemp)) {
            return 0.0f;
        }
        OffFrostTemp offFrostTemp2 = new OffFrostTemp();
        try {
            offFrostTemp2.onParseData(FormatUtil.getHexData(this.offFrostTemp));
            return offFrostTemp2.getValue();
        } catch (MessageFormatException e) {
            e.printStackTrace();
            return 0.0f;
        }
    }

    public String toString() {
        return "TemperatureMessage{flagDaylightTime='" + this.flagDaylightTime + '\'' + ", inDaylightTime='" + this.inDaylightTime + '\'' + ", flagBatteryState='" + this.flagBatteryState + '\'' + ", roomCurrentTemp='" + this.roomCurrentTemp + '\'' + ", roomTargetTemp='" + this.roomTargetTemp + '\'' + ", roomTargetTempMax='" + this.roomTargetTempMax + '\'' + ", roomTargetTempMin='" + this.roomTargetTempMin + '\'' + ", roomComfortTemp='" + this.roomComfortTemp + '\'' + ", roomEconTemp='" + this.roomEconTemp + '\'' + ", roomFrostTemp='" + this.roomFrostTemp + '\'' + ", roomNightTemp='" + this.roomNightTemp + '\'' + ", roomAllowHeat='" + this.roomAllowHeat + '\'' + ", roomHeatStatus='" + this.roomHeatStatus + '\'' + ", thermostatRelayState='" + this.thermostatRelayState + '\'' + ", roomTempUnit='" + this.roomTempUnit + '\'' + ", roomTempReturnOn='" + this.roomTempReturnOn + '\'' + ", roomTempReturnOff='" + this.roomTempReturnOff + '\'' + ", roomTempSensorStatus='" + this.roomTempSensorStatus + '\'' + ", roomWorkMode='" + this.roomWorkMode + '\'' + ", roomManualTemp='" + this.roomManualTemp + '\'' + ", roomTemporaryTempStatus='" + this.roomTemporaryTempStatus + '\'' + ", roomTempCurve='" + this.roomTempCurve + '\'' + ", roomTempSensorInfluces='" + this.roomTempSensorInfluces + '\'' + ", roomSeason='" + this.roomSeason + '\'' + ", roomDhwProgram='" + this.roomDhwProgram + '\'' + ", roomOverrideState='" + this.roomOverrideState + '\'' + ", roomOverrideTemp='" + this.roomOverrideTemp + '\'' + ", roomBoostState='" + this.roomBoostState + '\'' + ", roomBoostTemp='" + this.roomBoostTemp + '\'' + ", roomBoostEndTime='" + this.roomBoostEndTime + '\'' + ", roomHolidayData='" + this.roomHolidayData + '\'' + ", valveOpenPercent='" + this.valveOpenPercent + '\'' + ", tempHeatProgramSun='" + this.tempHeatProgramSun + '\'' + ", tempHeatProgramMon='" + this.tempHeatProgramMon + '\'' + ", tempHeatProgramTue='" + this.tempHeatProgramTue + '\'' + ", tempHeatProgramWeb='" + this.tempHeatProgramWeb + '\'' + ", tempHeatProgramThur='" + this.tempHeatProgramThur + '\'' + ", tempHeatProgramFri='" + this.tempHeatProgramFri + '\'' + ", tempHeatProgramSat='" + this.tempHeatProgramSat + '\'' + ", tempDhwMax='" + this.tempDhwMax + '\'' + ", tempDhwMin='" + this.tempDhwMin + '\'' + ", heatOptimizeStart='" + this.heatOptimizeStart + '\'' + ", frostzenAllow='" + this.frostzenAllow + '\'' + ", frostzenState='" + this.frostzenState + '\'' + ", valveCurrnetPos='" + this.valveCurrnetPos + '\'' + ", valveTargetPos='" + this.valveTargetPos + '\'' + ", valveTargetRange='" + this.valveTargetRange + '\'' + ", valveFullPosAllow='" + this.valveFullPosAllow + '\'' + ", thermostatList='" + this.thermostatList + '\'' + ", salveMasterAddress='" + this.salveMasterAddress + '\'' + ", salveMasterInfo='" + this.salveMasterInfo + '\'' + ", salveAvoidStuck='" + this.salveAvoidStuck + '\'' + ", windowDetect='" + this.windowDetect + '\'' + ", windowState='" + this.windowState + '\'' + ", heatMode='" + this.heatMode + '\'' + ", tpiTempBand='" + this.tpiTempBand + '\'' + ", tpiBurnPeriod='" + this.tpiBurnPeriod + '\'' + ", temperatureChangeRatio='" + this.temperatureChangeRatio + '\'' + ", offFrostTemp='" + this.offFrostTemp + '\'' + ", flagOutTemperature='" + this.flagOutTemperature + '\'' + ", flagWarmingUpStatus='" + this.flagWarmingUpStatus + '\'' + ", flagOutTempProbeModel='" + this.flagOutTempProbeModel + '\'' + ", utcMilliseconds='" + this.utcMilliseconds + '\'' + '}';
    }

    public String getUtcMilliseconds() {
        return this.utcMilliseconds;
    }
}
