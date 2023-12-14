package com.p107tb.appyunsdk.bean;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/* renamed from: com.tb.appyunsdk.bean.MqttConfigResponse */
public class MqttConfigResponse implements Serializable {
    private String domain;
    private String host;
    private int port;
    private int ssl_port;
    private TopicBean topic;
    private int ws_port;
    private int wss_port;

    public String getHost() {
        return this.host;
    }

    public void setHost(String str) {
        this.host = str;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int i) {
        this.port = i;
    }

    public int getSsl_port() {
        return this.ssl_port;
    }

    public void setSsl_port(int i) {
        this.ssl_port = i;
    }

    public int getWs_port() {
        return this.ws_port;
    }

    public void setWs_port(int i) {
        this.ws_port = i;
    }

    public int getWss_port() {
        return this.wss_port;
    }

    public void setWss_port(int i) {
        this.wss_port = i;
    }

    public TopicBean getTopic() {
        return this.topic;
    }

    public void setTopic(TopicBean topicBean) {
        this.topic = topicBean;
    }

    public String getDomain() {
        return this.domain;
    }

    public void setDomain(String str) {
        this.domain = str;
    }

    /* renamed from: com.tb.appyunsdk.bean.MqttConfigResponse$TopicBean */
    public static class TopicBean implements Serializable {
        @SerializedName("1")
        private MqttConfigResponse$TopicBean$_$1Bean _$1;
        @SerializedName("2")
        private MqttConfigResponse$TopicBean$_$2Bean _$2;

        public MqttConfigResponse$TopicBean$_$1Bean get_$1() {
            return this._$1;
        }

        public void set_$1(MqttConfigResponse$TopicBean$_$1Bean mqttConfigResponse$TopicBean$_$1Bean) {
            this._$1 = mqttConfigResponse$TopicBean$_$1Bean;
        }

        public MqttConfigResponse$TopicBean$_$2Bean get_$2() {
            return this._$2;
        }

        public void set_$2(MqttConfigResponse$TopicBean$_$2Bean mqttConfigResponse$TopicBean$_$2Bean) {
            this._$2 = mqttConfigResponse$TopicBean$_$2Bean;
        }

        public String toString() {
            return "TopicBean{_$1=" + this._$1 + ", _$2=" + this._$2 + '}';
        }
    }

    public String toString() {
        return "MqttConfigResponse{host='" + this.host + '\'' + ", port=" + this.port + ", ssl_port=" + this.ssl_port + ", ws_port=" + this.ws_port + ", wss_port=" + this.wss_port + ", topic=" + this.topic + '}';
    }
}
