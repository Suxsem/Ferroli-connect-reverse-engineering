package p110io.reactivex.flowables;

import p110io.reactivex.Flowable;
import p110io.reactivex.annotations.NonNull;
import p110io.reactivex.disposables.Disposable;
import p110io.reactivex.functions.Consumer;
import p110io.reactivex.internal.functions.Functions;
import p110io.reactivex.internal.operators.flowable.FlowableAutoConnect;
import p110io.reactivex.internal.operators.flowable.FlowableRefCount;
import p110io.reactivex.internal.util.ConnectConsumer;
import p110io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.flowables.ConnectableFlowable */
public abstract class ConnectableFlowable<T> extends Flowable<T> {
    public abstract void connect(@NonNull Consumer<? super Disposable> consumer);

    public final Disposable connect() {
        ConnectConsumer connectConsumer = new ConnectConsumer();
        connect(connectConsumer);
        return connectConsumer.disposable;
    }

    @NonNull
    public Flowable<T> refCount() {
        return RxJavaPlugins.onAssembly(new FlowableRefCount(this));
    }

    @NonNull
    public Flowable<T> autoConnect() {
        return autoConnect(1);
    }

    @NonNull
    public Flowable<T> autoConnect(int i) {
        return autoConnect(i, Functions.emptyConsumer());
    }

    @NonNull
    public Flowable<T> autoConnect(int i, @NonNull Consumer<? super Disposable> consumer) {
        if (i > 0) {
            return RxJavaPlugins.onAssembly(new FlowableAutoConnect(this, i, consumer));
        }
        connect(consumer);
        return RxJavaPlugins.onAssembly(this);
    }
}
