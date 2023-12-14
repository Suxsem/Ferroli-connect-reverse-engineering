package com.p107tb.appyunsdk.bean;

import com.p107tb.appyunsdk.bean.ResultsBean;
import java.io.Serializable;
import java.util.List;

/* renamed from: com.tb.appyunsdk.bean.BindDeviceResponse */
public class BindDeviceResponse implements Serializable {
    private List<ResultsBean.ChildDevicesBean> child_devices;
    private String create_date;
    private String device_code;
    private ExtraInfoBean extra_info;
    private boolean is_active;
    private String mac_address;
    private String name;
    private boolean online;
    private String owner_name;
    private String owner_slug;
    private String pass_code;
    private String product_code;
    private String product_name;
    private String qrcode_uri;
    private String slug;
    private ResultsBean.StateInfoBean state_info;
    private String update_date;
    private ResultsBean.VersionBean version;

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getMac_address() {
        return this.mac_address;
    }

    public void setMac_address(String str) {
        this.mac_address = str;
    }

    public String getPass_code() {
        return this.pass_code;
    }

    public void setPass_code(String str) {
        this.pass_code = str;
    }

    public ResultsBean.VersionBean getVersion() {
        return this.version;
    }

    public void setVersion(ResultsBean.VersionBean versionBean) {
        this.version = versionBean;
    }

    public ExtraInfoBean getExtra_info() {
        return this.extra_info;
    }

    public void setExtra_info(ExtraInfoBean extraInfoBean) {
        this.extra_info = extraInfoBean;
    }

    public String getProduct_code() {
        return this.product_code;
    }

    public void setProduct_code(String str) {
        this.product_code = str;
    }

    public String getProduct_name() {
        return this.product_name;
    }

    public void setProduct_name(String str) {
        this.product_name = str;
    }

    public String getDevice_code() {
        return this.device_code;
    }

    public void setDevice_code(String str) {
        this.device_code = str;
    }

    public String getQrcode_uri() {
        return this.qrcode_uri;
    }

    public void setQrcode_uri(String str) {
        this.qrcode_uri = str;
    }

    public String getOwner_slug() {
        return this.owner_slug;
    }

    public void setOwner_slug(String str) {
        this.owner_slug = str;
    }

    public String getOwner_name() {
        return this.owner_name;
    }

    public void setOwner_name(String str) {
        this.owner_name = str;
    }

    public boolean isOnline() {
        return this.online;
    }

    public void setOnline(boolean z) {
        this.online = z;
    }

    public ResultsBean.StateInfoBean getState_info() {
        return this.state_info;
    }

    public void setState_info(ResultsBean.StateInfoBean stateInfoBean) {
        this.state_info = stateInfoBean;
    }

    public List<ResultsBean.ChildDevicesBean> getChild_devices() {
        return this.child_devices;
    }

    public void setChild_devices(List<ResultsBean.ChildDevicesBean> list) {
        this.child_devices = list;
    }

    public String getCreate_date() {
        return this.create_date;
    }

    public void setCreate_date(String str) {
        this.create_date = str;
    }

    public String getUpdate_date() {
        return this.update_date;
    }

    public void setUpdate_date(String str) {
        this.update_date = str;
    }

    public String getSlug() {
        return this.slug;
    }

    public void setSlug(String str) {
        this.slug = str;
    }

    public boolean isIs_active() {
        return this.is_active;
    }

    public void setIs_active(boolean z) {
        this.is_active = z;
    }

    public String toString() {
        return "BindDeviceResponse{name='" + this.name + '\'' + ", mac_address='" + this.mac_address + '\'' + ", pass_code='" + this.pass_code + '\'' + ", version=" + this.version + ", extra_info=" + this.extra_info + ", product_code='" + this.product_code + '\'' + ", product_name='" + this.product_name + '\'' + ", device_code='" + this.device_code + '\'' + ", qrcode_uri='" + this.qrcode_uri + '\'' + ", owner_slug='" + this.owner_slug + '\'' + ", owner_name='" + this.owner_name + '\'' + ", online=" + this.online + ", state_info=" + this.state_info + ", child_devices=" + this.child_devices + ", create_date='" + this.create_date + '\'' + ", update_date='" + this.update_date + '\'' + ", slug='" + this.slug + '\'' + ", is_active=" + this.is_active + '}';
    }
}
