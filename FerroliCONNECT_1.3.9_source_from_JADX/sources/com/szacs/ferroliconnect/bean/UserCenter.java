package com.szacs.ferroliconnect.bean;

import android.content.SharedPreferences;
import com.p107tb.appyunsdk.Constant;
import com.p107tb.appyunsdk.bean.DeviceListResponse;
import com.p107tb.appyunsdk.bean.UserResponse;
import com.szacs.ferroliconnect.MainApplication;

public class UserCenter {
    static BoilerBean boilerBean;
    static DeviceListResponse.ResultsBean.ChildDevicesBean childDevicesBean;
    static UserResponse mUserResponse;
    static MsgBean msgBean;
    static DeviceListResponse.ResultsBean resultsBean;
    static ThermostatBean thermostatBean;

    public static void setUserInfo(UserResponse userResponse) {
        mUserResponse = userResponse;
    }

    public static UserResponse getUserInfo() {
        if (mUserResponse == null) {
            SharedPreferences sharedPreferences = MainApplication.getInstance().getSharedPreferences("ferroli_user", 0);
            mUserResponse = new UserResponse();
            mUserResponse.setName(sharedPreferences.getString(Constant.APP_USERNAME, ""));
            mUserResponse.setEmail(sharedPreferences.getString("email", ""));
            mUserResponse.setSlug(sharedPreferences.getString("user_slug", ""));
            mUserResponse.setUpdate_date(sharedPreferences.getString("update_date", ""));
            mUserResponse.setCreate_date(sharedPreferences.getString("create_date", ""));
        }
        return mUserResponse;
    }

    public static DeviceListResponse.ResultsBean getResultsBean() {
        return resultsBean;
    }

    public static void setResultsBean(DeviceListResponse.ResultsBean resultsBean2) {
        resultsBean = resultsBean2;
    }

    public static DeviceListResponse.ResultsBean.ChildDevicesBean getChildDevicesBean() {
        return childDevicesBean;
    }

    public static void setChildDevicesBean(DeviceListResponse.ResultsBean.ChildDevicesBean childDevicesBean2) {
        childDevicesBean = childDevicesBean2;
    }

    public static BoilerBean getBoilerBean() {
        return boilerBean;
    }

    public static void setBoilerBean(BoilerBean boilerBean2) {
        boilerBean = boilerBean2;
    }

    public static ThermostatBean getThermostatBean() {
        return thermostatBean;
    }

    public static void setThermostatBean(ThermostatBean thermostatBean2) {
        thermostatBean = thermostatBean2;
    }

    public static MsgBean getMsgBean() {
        return msgBean;
    }

    public static void setMsgBean(MsgBean msgBean2) {
        msgBean = msgBean2;
    }
}
