package com.p107tb.appyunsdk.bean;

import java.io.Serializable;
import java.util.List;

/* renamed from: com.tb.appyunsdk.bean.DeviceListResponse */
public class DeviceListResponse implements Serializable {
    private int count;
    private String next;
    private String previous;
    private List<ResultsBean> results;

    public int getCount() {
        return this.count;
    }

    public void setCount(int i) {
        this.count = i;
    }

    public String getNext() {
        return this.next;
    }

    public void setNext(String str) {
        this.next = str;
    }

    public Object getPrevious() {
        return this.previous;
    }

    public void setPrevious(String str) {
        this.previous = str;
    }

    public List<ResultsBean> getResults() {
        return this.results;
    }

    public void setResults(List<ResultsBean> list) {
        this.results = list;
    }

    /* renamed from: com.tb.appyunsdk.bean.DeviceListResponse$ResultsBean */
    public static class ResultsBean implements Serializable {
        private List<ChildDevicesBean> child_devices;
        private String create_date;
        private String device_code;
        private ExtraInfoBean extra_info;
        private boolean is_active;
        private String mac_address;
        private String name;
        private boolean online;
        private String owner_name;
        private String owner_slug;
        private String product_code;
        private String qrcode_uri;
        private String slug;
        private StateInfoBean state_info;
        private String update_date;
        private VersionBean version;
        private Integer wifiSignal;

        public Integer getWifiSignal() {
            return this.wifiSignal;
        }

        public void setWifiSignal(Integer num) {
            this.wifiSignal = num;
        }

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

        public ExtraInfoBean getExtra_info() {
            return this.extra_info;
        }

        public void setExtra_info(ExtraInfoBean extraInfoBean) {
            this.extra_info = extraInfoBean;
        }

        /* renamed from: com.tb.appyunsdk.bean.DeviceListResponse$ResultsBean$StateInfoBean */
        public static class StateInfoBean implements Serializable {
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

        /* renamed from: com.tb.appyunsdk.bean.DeviceListResponse$ResultsBean$ChildDevicesBean */
        public static class ChildDevicesBean implements Serializable {
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

            public String toString() {
                return "ChildDevicesBean{code='" + this.code + '\'' + ", sdid='" + this.sdid + '\'' + ", version='" + this.version + '\'' + ", online=" + this.online + ", slug='" + this.slug + '\'' + ", create_date='" + this.create_date + '\'' + ", name ='" + this.name + '\'' + '}';
            }
        }

        /* renamed from: com.tb.appyunsdk.bean.DeviceListResponse$ResultsBean$VersionBean */
        public static class VersionBean implements Serializable {
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

            public String toString() {
                return "VersionBean{wifi_software='" + this.wifi_software + '\'' + ", wifi_hardware='" + this.wifi_hardware + '\'' + ", mcu_software='" + this.mcu_software + '\'' + ", mcu_hardware='" + this.mcu_hardware + '\'' + '}';
            }
        }

        public String toString() {
            return "ResultsBean{name='" + this.name + '\'' + ", mac_address='" + this.mac_address + '\'' + ", version=" + this.version + ", product_code='" + this.product_code + '\'' + ", device_code='" + this.device_code + '\'' + ", qrcode_uri='" + this.qrcode_uri + '\'' + ", owner_slug='" + this.owner_slug + '\'' + ", owner_name='" + this.owner_name + '\'' + ", online=" + this.online + ", state_info=" + this.state_info + ", create_date='" + this.create_date + '\'' + ", update_date='" + this.update_date + '\'' + ", slug='" + this.slug + '\'' + ", is_active=" + this.is_active + ", child_devices=" + this.child_devices + ", extra_info=" + this.extra_info + '}';
        }
    }

    public String toString() {
        return "DeviceListResponse{count=" + this.count + ", next='" + this.next + '\'' + ", previous='" + this.previous + '\'' + ", results=" + this.results + '}';
    }
}
