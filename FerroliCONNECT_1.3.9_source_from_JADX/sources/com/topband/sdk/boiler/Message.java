package com.topband.sdk.boiler;

import com.topband.sdk.boiler.util.BinaryUtils;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import kotlin.UByte;

public abstract class Message implements Serializable {
    public static final short BOILER_ID = 8228;
    public static final short FLAG_AUTO_TEST = 22;
    public static final short FLAG_BACK_LIGHT_FUNCTION = 18;
    public static final short FLAG_BATH_WATER_REAL_TEMPERATURE = 8194;
    public static final short FLAG_BATH_WATER_TARGET_TEMPERATURE = 8195;
    public static final short FLAG_BATH_WATER_TARGET_TEMPERATURE_MAX = 8201;
    public static final short FLAG_BATH_WATER_TARGET_TEMPERATURE_MIN = 8202;
    public static final short FLAG_BATTERY_STATE = 19;
    public static final short FLAG_BOILER_CONTROL_TYPE = 8207;
    public static final short FLAG_BOILER_RELAY_STATE = 8209;
    public static final short FLAG_DAYLIGHT_TIME = 5;
    public static final short FLAG_DAYLIGHT_TIME_STATE = 6;
    public static final short FLAG_DEVICE_ERROR = 8206;
    public static final short FLAG_DEVICE_ERROR_INFO = 8205;
    public static final short FLAG_DEVICE_FUNCTION_TYPE = 8222;
    public static final short FLAG_DEVICE_INSTALLATION_TYPE = 8221;
    public static final short FLAG_DEVICE_NO = 2;
    public static final short FLAG_DEVICE_RUNTIME_INFO = 8214;
    public static final short FLAG_DEVICE_TIME_INT = 3;
    public static final short FLAG_DEVICE_TIME_STRUCT = 4;
    public static final short FLAG_DEVICE_TYPE = 1;
    public static final short FLAG_FAUL_CODE = 21;
    public static final short FLAG_FLAME_STATUS = 8218;
    public static final short FLAG_FLUE_TEMP = 8229;
    public static final short FLAG_FROZEN_PREVENTION_STATUS = 8223;
    public static final short FLAG_FUEL_GAS_TYPE = 8220;
    public static final short FLAG_IS_THERMOSTAT_ACTIVE = 8210;
    public static final short FLAG_OT_STATE = 8208;
    public static final short FLAG_OUT_TEMPERATURE = 8204;
    public static final short FLAG_OUT_TEMP_PAY_STATE = 8196;
    public static final short FLAG_OUT_TEMP_PROBE_MODEL = 8240;
    public static final short FLAG_OUT_WATER_TEMP = 8232;
    public static final short FLAG_PAIR = 14;
    public static final short FLAG_PWM = 8227;
    public static final short FLAG_RADIO_LOST_FRAME_COUNT = 13;
    public static final short FLAG_RADIO_NOISE = 10;
    public static final short FLAG_RADIO_P = 8;
    public static final short FLAG_RADIO_RECEIVE_RATE = 9;
    public static final short FLAG_RADIO_REV_FRAME_COUNT = 12;
    public static final short FLAG_RADIO_SEND_FRAME_COUNT = 11;
    public static final short FLAG_READ_WRITE_OT_CMD = 8198;
    public static final short FLAG_READ_WRITE_OT_DATA = 8197;
    public static final short FLAG_RECOVERY_MACHINE_ERROR = 8215;
    public static final short FLAG_RESET = 20;
    public static final short FLAG_RETURN_WATER_TEMP = 8231;
    public static final short FLAG_RF_TEST = 16;
    public static final short FLAG_ROOM_NUMBER_USE_STATE = 15;
    public static final short FLAG_SET_CHANNEL = 7;
    public static final short FLAG_WARMING_UP_STATUS = 8224;
    public static final short FLAG_WARMING_WATER_REAL_TEMPERATURE = 8192;
    public static final short FLAG_WARMING_WATER_TARGET_TEMPERATURE = 8203;
    public static final short FLAG_WARMING_WATER_TARGET_TEMPERATURE_MAX = 8199;
    @Deprecated
    public static final short FLAG_WARMING_WATER_TARGET_TEMPERATURE_MAX_TEMP = 8203;
    public static final short FLAG_WARMING_WATER_TARGET_TEMPERATURE_MIN = 8200;
    public static final short FLAG_WATER_BOTTLE_FROZEN_PREVENTION_STATUS = 8225;
    public static final short FLAG_WATER_PRESSURE = 8217;
    public static final short FLAG_WATER_RATE = 8230;
    public static final short FLAG_WATER_SUPPLY_SWITCH = 8219;
    public static final short FLAG_WIFI_CONNECT_STATE = 17;
    public static final short FLAG_WIFI_MODEL_VERSION_SOFT = 0;
    public static final short FLAG_WIFI_SIGNAL_STRENGHT = 24;
    public static final short FLAG_WIND_PRESSURE_SWITCH = 8226;
    public static final short FLAG_WINTER_SUMMER_MODE = 8216;
    public static final short FROSTZEN_ALLOW = 4135;
    public static final short FROSTZEN_STATE = 4136;
    public static final short HEATING_WATER_TEMP = 8193;
    public static final short HEAT_MODE = 4147;
    public static final short HEAT_OPTIMIZE_START = 4134;
    public static final short OFF_FROST_TEMP = 4153;
    public static final short OT_MASTER_STATUS = 8211;
    public static final short OT_SLAVE_CONFIGURE = 8213;
    public static final short OT_SLAVE_STATUS = 8212;
    public static final short ROOM_ALLOW_HEAT = 4104;
    public static final short ROOM_BOOST_END_TIME = 4122;
    public static final short ROOM_BOOST_STATE = 4120;
    public static final short ROOM_BOOST_TEMP = 4121;
    public static final short ROOM_COMFORT_TEMP = 4100;
    public static final short ROOM_CURRENT_TEMP = 4096;
    public static final short ROOM_DHW_PROGRAM = 4117;
    public static final short ROOM_ECON_TEMP = 4101;
    public static final short ROOM_FROST_TEMP = 4102;
    public static final short ROOM_HEAT_STATUS = 4105;
    public static final short ROOM_HOLIDAY_DATA = 4123;
    public static final short ROOM_MANUAL_TEMP = 4112;
    public static final short ROOM_NIGHT_TEMP = 4103;
    public static final short ROOM_OVERRIDE_STATE = 4118;
    public static final short ROOM_OVERRIDE_TEMP = 4119;
    public static final short ROOM_SEASON = 4116;
    public static final short ROOM_TARGET_TEMP = 4097;
    public static final short ROOM_TARGET_TEMP_MAX = 4098;
    public static final short ROOM_TARGET_TEMP_MIN = 4099;
    public static final short ROOM_TEMPORARY_TEMP_STATUS = 4113;
    public static final short ROOM_TEMP_CURVE = 4114;
    public static final short ROOM_TEMP_RETURN_OFF = 4109;
    public static final short ROOM_TEMP_RETURN_ON = 4108;
    public static final short ROOM_TEMP_SENSOR_INFLUCES = 4115;
    public static final short ROOM_TEMP_SENSOR_STATUS = 4110;
    public static final short ROOM_TEMP_UNIT = 4107;
    public static final short ROOM_WORK_MODE = 4111;
    public static final short SALVE_AVOID_STUCK = 4144;
    public static final short SALVE_MASTER_ADDRESS = 4142;
    public static final short SALVE_MASTER_INFO = 4143;
    public static final short TEMPERATURE_CHANGE_RATIO = 4150;
    public static final short TEMP_DHW_MAX = 4132;
    public static final short TEMP_DHW_MIN = 4133;
    public static final short TEMP_HEAT_PROGRAM_FRI = 4130;
    public static final short TEMP_HEAT_PROGRAM_MON = 4126;
    public static final short TEMP_HEAT_PROGRAM_SAT = 4131;
    public static final short TEMP_HEAT_PROGRAM_SUN = 4125;
    public static final short TEMP_HEAT_PROGRAM_THUR = 4129;
    public static final short TEMP_HEAT_PROGRAM_TUE = 4127;
    public static final short TEMP_HEAT_PROGRAM_WEB = 4128;
    public static final short THERMOSTAT_ID = 23;
    public static final short THERMOSTAT_LIST = 4141;
    public static final short THERMOSTAT_RELAY_STATE = 4106;
    public static final short TPI_BURN_PERIOD = 4149;
    public static final short TPI_TEMP_BAND = 4148;
    public static final short VALVE_CURRNET_POS = 4137;
    public static final short VALVE_FULL_POS_ALLOW = 4140;
    public static final short VALVE_OPEN_PERCENT = 4124;
    public static final short VALVE_TARGET_POS = 4138;
    public static final short VALVE_TARGET_RANGE = 4139;
    public static final short WINDOW_DETECT = 4145;
    public static final short WINDOW_STATE = 4146;
    private static final long serialVersionUID = 1;
    private short mFlag;

    /* access modifiers changed from: protected */
    public int byte2int(byte b, byte b2) {
        return ((b & UByte.MAX_VALUE) << 8) | (b2 & UByte.MAX_VALUE);
    }

    public abstract byte[] onGetData();

    public abstract short onGetDataLength();

    public abstract void onParseData(byte[] bArr) throws MessageFormatException;

    public Message(short s) {
        this.mFlag = s;
    }

    public short getFlag() {
        return this.mFlag;
    }

    public int getLength() {
        return onGetDataLength() + 4;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("flag : " + this.mFlag);
        sb.append("; dataLength : " + onGetDataLength());
        sb.append("; data : ");
        for (byte b : onGetData()) {
            sb.append(" : " + b);
        }
        return sb.toString();
    }

    public byte[] toBytes() {
        byte[] onGetData = onGetData();
        if (onGetData == null) {
            return null;
        }
        short onGetDataLength = onGetDataLength();
        if (onGetDataLength == 0 || onGetDataLength == onGetData.length) {
            byte[] short2BytesHtL = BinaryUtils.short2BytesHtL(this.mFlag);
            byte[] short2BytesHtL2 = BinaryUtils.short2BytesHtL(onGetDataLength);
            byte[] bArr = new byte[(onGetDataLength + 4)];
            System.arraycopy(short2BytesHtL, 0, bArr, 0, 2);
            System.arraycopy(short2BytesHtL2, 0, bArr, 2, 2);
            System.arraycopy(onGetData, 0, bArr, 4, onGetDataLength);
            return bArr;
        }
        throw new IllegalStateException("flag: " + getFlag() + ", required data length : " + onGetDataLength + "; found data : " + new String(onGetData) + ", length : " + onGetData.length);
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof Message)) {
            return false;
        }
        Message message = (Message) obj;
        if (this.mFlag != message.mFlag || onGetDataLength() != message.onGetDataLength()) {
            return false;
        }
        byte[] onGetData = onGetData();
        byte[] onGetData2 = message.onGetData();
        if (onGetData == onGetData2) {
            return true;
        }
        if (onGetData == null || onGetData2 == null || onGetData.length != onGetData2.length) {
            return false;
        }
        for (int i = 0; i < onGetData.length; i++) {
            if (onGetData[i] != onGetData2[i]) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public short bytes2int(byte[] bArr, ByteOrder byteOrder) {
        ByteBuffer wrap = ByteBuffer.wrap(bArr);
        wrap.order(byteOrder);
        return wrap.getShort();
    }
}
