package com.szacs.ferroliconnect.util.APConfig;

import java.util.Timer;
import java.util.TimerTask;

public class HeartbeatTimer {
    /* access modifiers changed from: private */
    public OnScheduleListener mListener;
    private TimerTask task;
    private Timer timer = new Timer();

    public interface OnScheduleListener {
        void onSchedule();
    }

    public void startTimer(long j, long j2) {
        this.task = new TimerTask() {
            public void run() {
                if (HeartbeatTimer.this.mListener != null) {
                    HeartbeatTimer.this.mListener.onSchedule();
                }
            }
        };
        this.timer.schedule(this.task, j, j2);
    }

    public void exit() {
        Timer timer2 = this.timer;
        if (timer2 != null) {
            timer2.cancel();
        }
        TimerTask timerTask = this.task;
        if (timerTask != null) {
            timerTask.cancel();
        }
    }

    public void setOnScheduleListener(OnScheduleListener onScheduleListener) {
        this.mListener = onScheduleListener;
    }
}
