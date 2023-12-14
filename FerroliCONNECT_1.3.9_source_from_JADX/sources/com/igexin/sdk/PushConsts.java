package com.igexin.sdk;

public class PushConsts {
    public static final String ACTION_BROADCAST_NETWORK_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";
    public static final String ACTION_BROADCAST_NOTIFICATION_CLICK = "com.igexin.action.notification.click";
    public static final String ACTION_BROADCAST_PUSHMANAGER = "com.igexin.sdk.action.pushmanager";
    public static final String ACTION_BROADCAST_TO_BOOT = "android.intent.action.BOOT_COMPLETED";
    public static final String ACTION_BROADCAST_USER_PRESENT = "android.intent.action.USER_PRESENT";
    public static final int ACTION_NOTIFICATION_ARRIVED = 10011;
    public static final int ACTION_NOTIFICATION_CLICKED = 10012;
    public static String ACTION_SERVICE_INITIALIZE = "com.igexin.action.initialize";
    public static final String ACTION_SERVICE_INITIALIZE_SLAVE = "com.igexin.action.initialize.slave";
    public static String ACTION_SERVICE_ONRESUME = "com.igexin.action.onresume";
    public static final int ALIAS_CID_LOST = 30005;
    public static final int ALIAS_CONNECT_LOST = 30006;
    public static final int ALIAS_ERROR_FREQUENCY = 30001;
    public static final int ALIAS_INVALID = 30007;
    public static final int ALIAS_OPERATE_ALIAS_FAILED = 30004;
    public static final int ALIAS_OPERATE_PARAM_ERROR = 30002;
    public static final int ALIAS_REQUEST_FILTER = 30003;
    public static final int ALIAS_SN_INVALID = 30008;
    public static final int BIND_ALIAS_RESULT = 10010;
    public static final int BIND_ALIAS_SUCCESS = 0;
    public static final int CHECK_CLIENTID = 10005;
    public static final String CMD_ACTION = "action";
    public static final int GET_CLIENTID = 10002;
    public static final int GET_MSG_DATA = 10001;
    public static final int GET_SDKONLINESTATE = 10007;
    public static final int GET_SDKSERVICEPID = 10008;
    public static final String KEY_CLIENT_ID = "clientid";
    public static final String KEY_CMD_MSG = "cmd_msg";
    public static final int KEY_CMD_RESULT = 10010;
    public static final String KEY_MESSAGE_DATA = "transmit_data";
    public static final String KEY_NOTIFICATION_ARRIVED = "notification_arrived";
    public static final String KEY_NOTIFICATION_CLICKED = "notification_clicked";
    public static final String KEY_ONLINE_STATE = "onlineState";
    public static final String KEY_SERVICE_PIT = "pid";
    public static final int MAX_FEEDBACK_ACTION = 90999;
    public static final int MIN_FEEDBACK_ACTION = 90001;
    public static final String SEND_MESSAGE_ERROR = "20000";
    public static final String SEND_MESSAGE_ERROR_GENERAL = "20001";
    public static final String SEND_MESSAGE_ERROR_TIME_OUT = "20002";
    public static final int SETTAG_ERROR_COUNT = 20001;
    public static final int SETTAG_ERROR_EXCEPTION = 20005;
    public static final int SETTAG_ERROR_FREQUENCY = 20002;
    public static final int SETTAG_ERROR_NULL = 20006;
    public static final int SETTAG_ERROR_REPEAT = 20003;
    public static final int SETTAG_ERROR_UNBIND = 20004;
    public static final int SETTAG_IN_BLACKLIST = 20009;
    public static final int SETTAG_NOTONLINE = 20008;
    public static final int SETTAG_NUM_EXCEED = 20010;
    public static final int SETTAG_SN_NULL = 20007;
    public static final int SETTAG_SUCCESS = 0;
    public static final int SETTAG_TAG_ILLEGAL = 20011;
    public static final int SET_TAG_RESULT = 10009;
    public static final int THIRDPART_FEEDBACK = 10006;
    public static final int UNBIND_ALIAS_RESULT = 10011;
    public static final int UNBIND_ALIAS_SUCCESS = 0;
}
