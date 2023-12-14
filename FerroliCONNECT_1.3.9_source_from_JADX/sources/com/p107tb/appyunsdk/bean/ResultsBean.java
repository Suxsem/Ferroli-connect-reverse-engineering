package com.p107tb.appyunsdk.bean;

import java.io.Serializable;
import java.util.List;

/* renamed from: com.tb.appyunsdk.bean.ResultsBean */
public class ResultsBean implements Serializable {
    private List<ChildDevicesBean> child_devices;
    private String create_date;
    private String device_code;
    private boolean is_active;
    private String mac_address;
    private String name;
    private boolean online;
    private String owner_name;
    private String owner_slug;
    private String product_code;
    private String product_name;
    private String qrcode_uri;
    private String slug;
    private StateInfoBean state_info;
    private String update_date;
    private VersionBean version;

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

    public VersionBean getVersion() {
        return this.version;
    }

    public void setVersion(VersionBean versionBean) {
        this.version = versionBean;
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

    public StateInfoBean getState_info() {
        return this.state_info;
    }

    public void setState_info(StateInfoBean stateInfoBean) {
        this.state_info = stateInfoBean;
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

    public List<ChildDevicesBean> getChild_devices() {
        return this.child_devices;
    }

    public void setChild_devices(List<ChildDevicesBean> list) {
        this.child_devices = list;
    }

    /* renamed from: com.tb.appyunsdk.bean.ResultsBean$VersionBean */
    public static class VersionBean {
        private String mcu_hardware;
        private String mcu_software;
        private String wifi_hardware;
        private String wifi_software;

        public String getWifi_software() {
            return this.wifi_software;
        }

        public void setWifi_software(String str) {
            this.wifi_software = str;
        }

        public String getWifi_hardware() {
            return this.wifi_hardware;
        }

        public void setWifi_hardware(String str) {
            this.wifi_hardware = str;
        }

        public String getMcu_software() {
            return this.mcu_software;
        }

        public void setMcu_software(String str) {
            this.mcu_software = str;
        }

        public String getMcu_hardware() {
            return this.mcu_hardware;
        }

        public void setMcu_hardware(String str) {
            this.mcu_hardware = str;
        }
    }

    /* renamed from: com.tb.appyunsdk.bean.ResultsBean$StateInfoBean */
    public static class StateInfoBean {
        private boolean clean_sess;
        private String client_id;
        private String connected_at;
        private String ipaddress;
        private int keepalive;
        private int port;
        private int proto_ver;
        private String username;

        public String getClient_id() {
            return this.client_id;
        }

        public void setClient_id(String str) {
            this.client_id = str;
        }

        public String getUsername() {
            return this.username;
        }

        public void setUsername(String str) {
            this.username = str;
        }

        public String getIpaddress() {
            return this.ipaddress;
        }

        public void setIpaddress(String str) {
            this.ipaddress = str;
        }

        public int getPort() {
            return this.port;
        }

        public void setPort(int i) {
            this.port = i;
        }

        public boolean isClean_sess() {
            return this.clean_sess;
        }

        public void setClean_sess(boolean z) {
            this.clean_sess = z;
        }

        public int getProto_ver() {
            return this.proto_ver;
        }

        public void setProto_ver(int i) {
            this.proto_ver = i;
        }

        public int getKeepalive() {
            return this.keepalive;
        }

        public void setKeepalive(int i) {
            this.keepalive = i;
        }

        public String getConnected_at() {
            return this.connected_at;
        }

        public void setConnected_at(String str) {
            this.connected_at = str;
        }
    }

    /* renamed from: com.tb.appyunsdk.bean.ResultsBean$ChildDevicesBean */
    public static class ChildDevicesBean {
        private String code;
        private String create_date;
        private String name;
        private boolean online;
        private String sdid;
        private String slug;
        private String version;

        public String getCode() {
            return this.code;
        }

        public void setCode(String str) {
            this.code = str;
        }

        public String getSdid() {
            return this.sdid;
        }

        public void setSdid(String str) {
            this.sdid = str;
        }

        public String getVersion() {
            return this.version;
        }

        public void setVersion(String str) {
            this.version = str;
        }

        public boolean isOnline() {
            return this.online;
        }

        public void setOnline(boolean z) {
            this.online = z;
        }

        public String getSlug() {
            return this.slug;
        }

        public void setSlug(String str) {
            this.slug = str;
        }

        public String getCreate_date() {
            return this.create_date;
        }

        public void setCreate_date(String str) {
            this.create_date = str;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String str) {
            this.name = str;
        }
    }
}
