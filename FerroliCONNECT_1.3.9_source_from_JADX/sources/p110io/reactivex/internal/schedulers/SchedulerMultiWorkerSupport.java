package p110io.reactivex.internal.schedulers;

import p110io.reactivex.Scheduler;
import p110io.reactivex.annotations.Experimental;
import p110io.reactivex.annotations.NonNull;

@Experimental
/* renamed from: io.reactivex.internal.schedulers.SchedulerMultiWorkerSupport */
public interface SchedulerMultiWorkerSupport {

    /* renamed from: io.reactivex.internal.schedulers.SchedulerMultiWorkerSupport$WorkerCallback */
    public interface WorkerCallback {
        void onWorker(int i, @NonNull Scheduler.Worker worker);
    }

    void createWorkers(int i, @NonNull WorkerCallback workerCallback);
}
