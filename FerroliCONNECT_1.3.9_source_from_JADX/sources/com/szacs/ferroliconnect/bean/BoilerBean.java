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
import java.io.Serializable;

public class BoilerBean implements Serializable {
    private static final String TAG = "BoilerBean";
    private static final long serialVersionUID = 12358869846L;
    private float bathCurrentTemp;
    private float bathMaxTemp;
    private float bathMinTemp;
    private float bathTargetTemp;
    private int boilerRelayState;
    private int controlType;
    private int errorCode;
    private int errorInfo;
    private int flameStatus;
    private int flueTemp;
    private int functionType;
    private int gasType;
    private float heatCurrentTemp;
    private float heatMaxSetTemp;
    private float heatMaxTemp;
    private float heatMinTemp;
    private int heatStatus;
    private float heatTargetTemp;
    private int installType;
    private String mDeviceCode;
    private String mProductCode;
    private int mode;
    private int outProbeModel;
    private float outTemp;
    private int outTemperaturPayState;
    private float outWaterTemp;
    private int pwm;
    private float returnTemp;
    private float waterPressure;
    private float waterRate;
    private int waterSwitchState;
    private int windPressureState;

    public static String getTAG() {
        return TAG;
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
        return this.heatMaxTemp;
    }

    public void setHeatMaxTemp(float f) {
        this.heatMaxTemp = f;
    }

    public float getHeatMinTemp() {
        float f = this.heatMinTemp;
        if (f == 0.0f) {
            return 30.0f;
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
            return 25.0f;
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
                } else if (next instanceof ErrorInfo) {
                    char[] errorCArr = ((ErrorInfo) next).getErrorCArr();
                    int length = errorCArr.length;
                    String str3 = errorCArr[length - 2] + "";
                    for (int i2 = 0; i2 < length; i2++) {
                        LogUtil.m3315d(TAG, " CHAR ARR INDEX " + i2 + " VAL = " + errorCArr[i2]);
                    }
                    LogUtil.m3315d(TAG, " 错误类型 length = " + length + " 类型Char = " + str3 + " 补齐后错误信息二进制字符串 = " + errorCArr.toString());
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
                    if (!((BoilerRelayState) next).isClosed()) {
                        i = 2;
                    }
                    this.boilerRelayState = i;
                    LogUtil.m3315d(TAG, "ON/OFF型锅炉开关状态：" + this.boilerRelayState);
                } else if (next instanceof OutProbeModel) {
                    OutProbeModel outProbeModel2 = (OutProbeModel) next;
                    this.outProbeModel = outProbeModel2.isOpen() ? 1 : 0;
                    LogUtil.m3315d(TAG, "锅炉探头是否启用：" + outProbeModel2);
                }
            }
        }
    }
}
