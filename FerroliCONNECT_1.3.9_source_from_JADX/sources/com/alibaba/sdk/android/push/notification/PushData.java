package com.alibaba.sdk.android.push.notification;

import android.content.Context;
import android.os.Build;
import com.alibaba.sdk.android.ams.common.logger.AmsLogger;
import com.alibaba.sdk.android.ams.common.util.StringUtil;
import com.alibaba.sdk.android.push.common.util.JSONUtils;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class PushData {
    public static final String KEY_BIG_BODY = "big_body";
    public static final String KEY_BIG_PICTURE = "big_picture";
    public static final String KEY_BIG_TITLE = "big_title";
    public static final String KEY_CHANNEL = "notification_channel";
    public static final String KEY_CONTENT = "content";
    public static final String KEY_CUSTOM_NOTIFICAITON_ID = "custom_notification_id";
    public static final String KEY_EXT = "ext";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_INBOX_CONTENT = "inbox_content";
    public static final String KEY_MSG_ID = "msg_id";
    public static final String KEY_MUSIC = "music";
    public static final String KEY_NOTIFICATION_PRIORITY = "_ALIYUN_NOTIFICATION_PRIORITY_";
    public static final String KEY_NOTIFY_ID = "notify_id";
    public static final String KEY_NOTIFY_TYPE = "remind";
    public static final String KEY_STYLE = "style";
    public static final String KEY_TITLE = "title";
    public static final int NOTIFY_TYPE_SILENT = 0;
    public static final int NOTIFY_TYPE_SOUND = 2;
    public static final int NOTIFY_TYPE_VIBRATE = 1;
    public static final int NOTIFY_TYPE_VIBRATE_SOUND = 3;
    public static final int NO_CUSTOM_NOTIFICATION = 0;
    private static final String TAG = "MPS:PushData";
    private static AmsLogger logger = AmsLogger.getLogger(TAG);
    private String bigBody;
    private String bigPicture;
    private String bigTitle;
    private String contentText;
    private int customNotificationId = 0;
    private Map<String, String> extraMap;
    private String image;
    private String inboxContent;
    private String msgId;
    private String notificationChannel;
    private int notifyId;
    private int notifyType;
    private int priority = 0;
    private String sound;
    private String style;
    private String title;

    public PushData() {
        int i = Build.VERSION.SDK_INT;
    }

    public static PushData parse(Context context, Map<String, String> map) {
        String str = map.get("title");
        String str2 = map.get("content");
        String str3 = null;
        if (StringUtil.isEmpty(str) || StringUtil.isEmpty(str2)) {
            AmsLogger amsLogger = logger;
            amsLogger.mo9541e("title or content of notify is empty: " + map);
            return null;
        }
        PushData pushData = new PushData();
        String str4 = map.get("remind");
        if (StringUtil.isEmpty(str4)) {
            str4 = String.valueOf(2);
        }
        String str5 = map.get("music");
        String str6 = map.get("ext");
        pushData.setTitle(str);
        pushData.setContentText(str2);
        pushData.setNotifyType(Integer.parseInt(str4));
        pushData.setNotificationChannel(map.get("notification_channel"));
        pushData.setImage(map.get("image"));
        pushData.setStyle(map.get("style"));
        pushData.setBigTitle(map.get("big_title"));
        pushData.setBigBody(map.get("big_body"));
        pushData.setBigPicture(map.get("big_picture"));
        pushData.setInboxContent(map.get("inbox_content"));
        pushData.setMsgId(map.get("msg_id"));
        try {
            pushData.setNotifyId(Integer.parseInt(map.get("notify_id")));
        } catch (Throwable unused) {
        }
        if (!StringUtil.isEmpty(str5)) {
            str3 = str5;
        }
        pushData.setSound(str3);
        if (!StringUtil.isEmpty(str6)) {
            try {
                Map<String, String> map2 = JSONUtils.toMap(new JSONObject(str6));
                pushData.setPriority(map2.containsKey("_ALIYUN_NOTIFICATION_PRIORITY_") ? map2.get("_ALIYUN_NOTIFICATION_PRIORITY_") : Build.VERSION.SDK_INT >= 16 ? String.valueOf(0) : String.valueOf(0));
                pushData.setExtraMap(map2);
            } catch (JSONException e) {
                logger.mo9542e("Parse inner json(ext) error:", e);
            }
        }
        if (map.containsKey(KEY_CUSTOM_NOTIFICAITON_ID)) {
            pushData.setCustomNotificationId(Integer.parseInt(map.get(KEY_CUSTOM_NOTIFICAITON_ID)));
        }
        return pushData;
    }

    private void setBigBody(String str) {
        this.bigBody = str;
    }

    private void setBigPicture(String str) {
        this.bigPicture = str;
    }

    private void setBigTitle(String str) {
        this.bigTitle = str;
    }

    private void setContentText(String str) {
        this.contentText = str;
    }

    private void setCustomNotificationId(int i) {
        this.customNotificationId = i;
    }

    private void setExtraMap(Map<String, String> map) {
        this.extraMap = map;
    }

    private void setInboxContent(String str) {
        this.inboxContent = str;
    }

    private void setMsgId(String str) {
        this.msgId = str;
    }

    private void setNotificationChannel(String str) {
        this.notificationChannel = str;
    }

    private void setNotifyId(int i) {
        this.notifyId = i;
    }

    private void setNotifyType(int i) {
        this.notifyType = i;
    }

    private void setPriority(String str) {
        try {
            this.priority = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            logger.mo9542e("formar error:数字格式错误", e);
        }
    }

    private void setSound(String str) {
        this.sound = str;
    }

    private void setStyle(String str) {
        this.style = str;
    }

    private void setTitle(String str) {
        this.title = str;
    }

    public String getBigBody() {
        return this.bigBody;
    }

    public String getBigPicture() {
        return this.bigPicture;
    }

    public String getBigTitle() {
        return this.bigTitle;
    }

    public String getContentText() {
        return this.contentText;
    }

    public int getCustomNotificationId() {
        return this.customNotificationId;
    }

    public Map<String, String> getExtraMap() {
        return this.extraMap;
    }

    public String getImage() {
        return this.image;
    }

    public String getInboxContent() {
        return this.inboxContent;
    }

    public String getMsgId() {
        return this.msgId;
    }

    public String getNotificationChannel() {
        return this.notificationChannel;
    }

    public int getNotifyId() {
        return this.notifyId;
    }

    public int getNotifyType() {
        return this.notifyType;
    }

    public int getPriority() {
        return this.priority;
    }

    public String getSound() {
        return this.sound;
    }

    public String getStyle() {
        return this.style;
    }

    public String getTitle() {
        return this.title;
    }

    public void setImage(String str) {
        this.image = str;
    }

    public String toString() {
        return "PushData{title='" + this.title + '\'' + ", contentText='" + this.contentText + '\'' + ", image='" + this.image + '\'' + ", notifyType=" + this.notifyType + ", sound='" + this.sound + '\'' + ", notificationChannel='" + this.notificationChannel + '\'' + ", priority=" + this.priority + ", customNotificationId=" + this.customNotificationId + ", extraMap=" + this.extraMap + ", style='" + this.style + '\'' + ", bigTitle='" + this.bigTitle + '\'' + ", bigPicture='" + this.bigPicture + '\'' + ", bigBody='" + this.bigBody + '\'' + ", inboxContent='" + this.inboxContent + '\'' + ", notifyId=" + this.notifyId + ", msgId='" + this.msgId + '\'' + '}';
    }
}
