package com.topband.tsmart;

import android.content.Context;
import com.topband.tsmart.entity.MessageEntity;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class TBMessageDispose {
    private static volatile TBMessageDispose tbMessageDispose;
    private boolean isDispose = false;
    private Queue<MessageEntity> mQueue = new ConcurrentLinkedQueue();

    private TBMessageDispose() {
    }

    public static TBMessageDispose getInstance() {
        if (tbMessageDispose == null) {
            synchronized (TSmartPush.class) {
                if (tbMessageDispose == null) {
                    tbMessageDispose = new TBMessageDispose();
                }
            }
        }
        return tbMessageDispose;
    }

    public void setDispose(boolean z) {
        this.isDispose = z;
        if (!this.isDispose && !this.mQueue.isEmpty()) {
            while (true) {
                MessageEntity poll = this.mQueue.poll();
                if (poll != null) {
                    int type = poll.getType();
                    if (type != 0) {
                        if (type != 1) {
                            if (type == 2 && TSmartPush.instance().getPushDataCallback() != null) {
                                TSmartPush.instance().getPushDataCallback().onNotificationReceivedInApp(poll.getContext(), poll.getTitle(), poll.getSummary(), poll.getExtraMap(), poll.getOpenType(), poll.getOpenActivity(), poll.getOpenUrl());
                            }
                        } else if (TSmartPush.instance().getPushDataCallback() != null) {
                            TSmartPush.instance().getPushDataCallback().onMessage(poll.getContext(), poll.getMessage());
                        }
                    } else if (TSmartPush.instance().getPushDataCallback() != null) {
                        TSmartPush.instance().getPushDataCallback().onNotification(poll.getContext(), poll.getTitle(), poll.getSummary(), poll.getExtraMap());
                    }
                } else {
                    return;
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void onNotification(Context context, String str, String str2, Map<String, String> map) {
        if (this.isDispose) {
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setType(0);
            messageEntity.setContext(context);
            messageEntity.setTitle(str);
            messageEntity.setSummary(str2);
            messageEntity.setExtraMap(map);
            this.mQueue.offer(messageEntity);
        } else if (TSmartPush.instance().getPushDataCallback() != null) {
            TSmartPush.instance().getPushDataCallback().onNotification(context, str, str2, map);
        }
    }

    /* access modifiers changed from: package-private */
    public void onMessage(Context context, TBPushMessage tBPushMessage) {
        if (this.isDispose) {
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setType(1);
            messageEntity.setContext(context);
            messageEntity.setMessage(tBPushMessage);
            this.mQueue.offer(messageEntity);
        } else if (TSmartPush.instance().getPushDataCallback() != null) {
            TSmartPush.instance().getPushDataCallback().onMessage(context, tBPushMessage);
        }
    }

    /* access modifiers changed from: package-private */
    public void onNotificationReceivedInApp(Context context, String str, String str2, Map<String, String> map, int i, String str3, String str4) {
        if (this.isDispose) {
            MessageEntity messageEntity = new MessageEntity();
            messageEntity.setType(2);
            Context context2 = context;
            messageEntity.setContext(context);
            String str5 = str;
            messageEntity.setTitle(str);
            String str6 = str2;
            messageEntity.setSummary(str2);
            Map<String, String> map2 = map;
            messageEntity.setExtraMap(map);
            int i2 = i;
            messageEntity.setOpenType(i);
            messageEntity.setOpenActivity(str3);
            messageEntity.setOpenUrl(str4);
            this.mQueue.offer(messageEntity);
        } else if (TSmartPush.instance().getPushDataCallback() != null) {
            TSmartPush.instance().getPushDataCallback().onNotificationReceivedInApp(context, str, str2, map, i, str3, str4);
        }
    }
}
