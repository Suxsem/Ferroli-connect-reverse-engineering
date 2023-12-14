package com.szacs.ferroliconnect.bean;

import android.text.TextUtils;
import android.util.Log;
import com.google.gson.annotations.SerializedName;
import com.szacs.ferroliconnect.util.Constant;
import com.szacs.ferroliconnect.util.LogUtil;
import com.topband.sdk.boiler.FormatUtil;
import com.topband.sdk.boiler.MessageFormatException;
import com.topband.sdk.boiler.message.boiler.BathWaterRealTemperature;
import com.topband.sdk.boiler.message.boiler.BathWaterTargetTemperature;
import com.topband.sdk.boiler.message.boiler.OutTemperature;
import com.topband.sdk.boiler.message.boiler.WarmingWaterMaxSettingTemperature;
import com.topband.sdk.boiler.message.boiler.WarmingWaterRealTemperature;
import com.topband.sdk.boiler.message.boiler.WarmingWaterTargetTemperature;
import java.io.Serializable;

public class BoilerMessage implements Serializable {
    private static final String TAG = "BoilerMessage";
    private static final long serialVersionUID = 1;
    @SerializedName("2002")
    private String bathCurrentTemp;
    @SerializedName("2009")
    private String bathMaxTemp;
    @SerializedName("200A")
    private String bathMinTemp;
    @SerializedName("2024")
    private String boilerId;
    @SerializedName("2011")
    private String boilerRelayState;
    @SerializedName("200E")
    private String errorCode;
    @SerializedName("200D")
    private String errorInfo;
    @SerializedName("2003")
    private String flagBathWaterTargetTemperature;
    @SerializedName("200F")
    private String flagBoilerControlType;
    @SerializedName("201E")
    private String flagDeviceFunctionType;
    @SerializedName("201D")
    private String flagDeviceInstallationType;
    @SerializedName("2025")
    private String flagFlueTemp;
    @SerializedName("201C")
    private String flagFuelGasType;
    @SerializedName("2028")
    private String flagOutWaterTemp;
    @SerializedName("2027")
    private String flagReturnWaterTemp;
    @SerializedName("2007")
    private String flagWarmingTargetTemperatureMax;
    @SerializedName("2026")
    private String flagWaterRate;
    @SerializedName("201B")
    private String flagWaterSupplySwitch;
    @SerializedName("2022")
    private String flagWindPressureSwitch;
    @SerializedName("201A")
    private String flameStatus;
    @SerializedName("2000")
    private String heatCurrentTemp;
    @SerializedName("2006")
    private String heatMaxTemp;
    @SerializedName("2008")
    private String heatMinTemp;
    @SerializedName("2020")
    private String heatStatus;
    @SerializedName("200B")
    private String heatTargetTemp;
    @SerializedName("2030")
    private String outProbeModel;
    @SerializedName("200C")
    private String outTemp;
    @SerializedName("2023")
    private String pwm;
    @SerializedName("2014")
    private String slaveOTSate;
    @SerializedName("2019")
    private String waterPressure;
    @SerializedName("0018")
    private String wifiSignal;
    @SerializedName("2018")
    private String winterSummerMode;

    public int getBathTargetTemp() {
        if (isStringEmpty(this.flagBathWaterTargetTemperature)) {
            return 0;
        }
        BathWaterTargetTemperature bathWaterTargetTemperature = new BathWaterTargetTemperature();
        try {
            bathWaterTargetTemperature.onParseData(FormatUtil.getHexData(this.flagBathWaterTargetTemperature));
            return (int) bathWaterTargetTemperature.getValue();
        } catch (MessageFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getBathMinTemp() {
        if (!isStringEmpty(this.bathMinTemp) && Integer.parseInt(this.bathMinTemp, 16) != 0) {
            return Integer.parseInt(this.bathMinTemp, 16);
        }
        return 25;
    }

    public int getHeatMinTemp() {
        if (!isStringEmpty(this.heatMinTemp) && Integer.parseInt(this.heatMinTemp, 16) != 0) {
            return Integer.parseInt(this.heatMinTemp, 16);
        }
        return 30;
    }

    public int getHeatTargetTemp() {
        if (isStringEmpty(this.heatTargetTemp)) {
            return 0;
        }
        WarmingWaterTargetTemperature warmingWaterTargetTemperature = new WarmingWaterTargetTemperature();
        try {
            warmingWaterTargetTemperature.onParseData(FormatUtil.getHexData(this.heatTargetTemp));
            return (int) warmingWaterTargetTemperature.getValue();
        } catch (MessageFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getMode() {
        if (isStringEmpty(this.winterSummerMode)) {
            return 0;
        }
        return Integer.parseInt(this.winterSummerMode, 16);
    }

    public float getHeatCurrentTemp() {
        if (isStringEmpty(this.heatCurrentTemp)) {
            return 0.0f;
        }
        WarmingWaterRealTemperature warmingWaterRealTemperature = new WarmingWaterRealTemperature();
        try {
            warmingWaterRealTemperature.onParseData(FormatUtil.getHexData(this.heatCurrentTemp));
            return warmingWaterRealTemperature.getValue();
        } catch (MessageFormatException e) {
            e.printStackTrace();
            return 0.0f;
        }
    }

    public float getWaterPressure() {
        if (isStringEmpty(this.waterPressure)) {
            return 0.0f;
        }
        return ((float) Integer.parseInt(this.waterPressure, 16)) / 10.0f;
    }

    public float getHeatMaxSetTemp() {
        if (isStringEmpty(this.heatTargetTemp)) {
            return 80.0f;
        }
        WarmingWaterMaxSettingTemperature warmingWaterMaxSettingTemperature = new WarmingWaterMaxSettingTemperature();
        try {
            warmingWaterMaxSettingTemperature.onParseData(FormatUtil.getHexData(this.heatTargetTemp));
            float value = warmingWaterMaxSettingTemperature.getValue();
            if (value == 0.0f) {
                return 80.0f;
            }
            return value;
        } catch (MessageFormatException e) {
            e.printStackTrace();
            return 0.0f;
        }
    }

    public float getBathMaxTemp() {
        if (!isStringEmpty(this.bathMaxTemp) && Integer.parseInt(this.bathMaxTemp, 16) != 0) {
            return (float) Integer.parseInt(this.bathMaxTemp, 16);
        }
        return 60.0f;
    }

    public int getHeatStatus() {
        if (isStringEmpty(this.heatStatus)) {
            return 0;
        }
        return Integer.parseInt(this.heatStatus, 16);
    }

    public int getFlameStatus() {
        if (isStringEmpty(this.flameStatus)) {
            return 0;
        }
        return Integer.parseInt(this.flameStatus, 16);
    }

    public int getErrorInfo() {
        if (isStringEmpty(this.errorInfo)) {
            return 0;
        }
        String binaryString = Integer.toBinaryString(Integer.parseInt(this.errorInfo, 16));
        Log.d(TAG, " 十进制错误信息 = " + Integer.parseInt(this.errorInfo, 16));
        int length = 8 - binaryString.length();
        StringBuilder sb = new StringBuilder(binaryString);
        for (int i = 0; i < length; i++) {
            sb.insert(0, "0");
        }
        char[] charArray = sb.toString().toCharArray();
        int length2 = charArray.length;
        String str = charArray[length2 - 2] + "";
        for (int i2 = 0; i2 < length2; i2++) {
            LogUtil.m3315d(TAG, " CHAR ARR INDEX " + i2 + " VAL = " + charArray[i2]);
        }
        LogUtil.m3315d(TAG, " 错误类型 length = " + length2 + " 类型Char = " + str + " 补齐后错误信息二进制字符串 = " + charArray.toString());
        return Integer.parseInt(str, 16);
    }

    public int getErrorCode() {
        if (isStringEmpty(this.errorCode)) {
            return 0;
        }
        return Integer.parseInt(this.errorCode, 16);
    }

    public int getOutProbeModel() {
        if (isStringEmpty(this.outProbeModel)) {
            return 0;
        }
        return Integer.parseInt(this.outProbeModel, 16);
    }

    public float getOutTemp() {
        if (isStringEmpty(this.outTemp)) {
            return 0.0f;
        }
        OutTemperature outTemperature = new OutTemperature();
        try {
            outTemperature.onParseData(FormatUtil.getHexData(this.outTemp));
            return outTemperature.getValue();
        } catch (MessageFormatException e) {
            e.printStackTrace();
            return 0.0f;
        }
    }

    public float getBathCurrentTemp() {
        if (isStringEmpty(this.bathCurrentTemp)) {
            return 0.0f;
        }
        BathWaterRealTemperature bathWaterRealTemperature = new BathWaterRealTemperature();
        try {
            bathWaterRealTemperature.onParseData(FormatUtil.getHexData(this.bathCurrentTemp));
            return bathWaterRealTemperature.getValue();
        } catch (MessageFormatException e) {
            e.printStackTrace();
            return 0.0f;
        }
    }

    public int getPwm() {
        if (isStringEmpty(this.pwm)) {
            return 0;
        }
        return Integer.parseInt(this.pwm, 16);
    }

    public boolean isHeatingMode() {
        if (!isStringEmpty(this.slaveOTSate) && (Integer.parseInt(this.slaveOTSate, 16) & 2) == 2) {
            return true;
        }
        return false;
    }

    public boolean isDhwMode() {
        if (!isStringEmpty(this.slaveOTSate) && (Integer.parseInt(this.slaveOTSate, 16) & 4) == 4) {
            return true;
        }
        return false;
    }

    public int getWifiSignal() {
        if (isStringEmpty(this.wifiSignal)) {
            return 0;
        }
        Constant.wifiSingal = Integer.valueOf(Integer.parseInt(this.wifiSignal, 16));
        return Integer.parseInt(this.wifiSignal, 16);
    }

    public int getBoilerRelayState() {
        if (!isStringEmpty(this.boilerRelayState) && Integer.parseInt(this.boilerRelayState, 16) == 2) {
            return 2;
        }
        return 1;
    }

    private boolean isStringEmpty(String str) {
        return TextUtils.isEmpty(str);
    }

    public int getHeatMaxTemp() {
        if (!isStringEmpty(this.heatMaxTemp) && Integer.parseInt(this.heatMaxTemp, 16) != 0) {
            return Integer.parseInt(this.heatMaxTemp, 16);
        }
        return 85;
    }

    public String toString() {
        return "BoilerMessage{wifiSignal='" + this.wifiSignal + '\'' + ", heatCurrentTemp='" + this.heatCurrentTemp + '\'' + ", heatTargetTemp='" + this.heatTargetTemp + '\'' + ", heatMaxTemp='" + this.heatMaxTemp + '\'' + ", flagWarmingTargetTemperatureMax='" + this.flagWarmingTargetTemperatureMax + '\'' + ", heatMinTemp='" + this.heatMinTemp + '\'' + ", bathMaxTemp='" + this.bathMaxTemp + '\'' + ", bathMinTemp='" + this.bathMinTemp + '\'' + ", bathCurrentTemp='" + this.bathCurrentTemp + '\'' + ", flagBathWaterTargetTemperature='" + this.flagBathWaterTargetTemperature + '\'' + ", outTemp='" + this.outTemp + '\'' + ", flagWindPressureSwitch='" + this.flagWindPressureSwitch + '\'' + ", winterSummerMode='" + this.winterSummerMode + '\'' + ", flagWaterSupplySwitch='" + this.flagWaterSupplySwitch + '\'' + ", flameStatus='" + this.flameStatus + '\'' + ", waterPressure='" + this.waterPressure + '\'' + ", heatStatus='" + this.heatStatus + '\'' + ", pwm='" + this.pwm + '\'' + ", flagReturnWaterTemp='" + this.flagReturnWaterTemp + '\'' + ", flagOutWaterTemp='" + this.flagOutWaterTemp + '\'' + ", errorInfo='" + this.errorInfo + '\'' + ", errorCode='" + this.errorCode + '\'' + ", flagBoilerControlType='" + this.flagBoilerControlType + '\'' + ", boilerRelayState='" + this.boilerRelayState + '\'' + ", slaveOTSate='" + this.slaveOTSate + '\'' + ", flagFuelGasType='" + this.flagFuelGasType + '\'' + ", flagDeviceInstallationType='" + this.flagDeviceInstallationType + '\'' + ", flagDeviceFunctionType='" + this.flagDeviceFunctionType + '\'' + ", flagFlueTemp='" + this.flagFlueTemp + '\'' + ", flagWaterRate='" + this.flagWaterRate + '\'' + ", boilerId='" + this.boilerId + '\'' + ", outProbeModel='" + this.outProbeModel + '\'' + '}';
    }
}
