package p110io.reactivex.internal.fuseable;

import p110io.reactivex.SingleSource;

/* renamed from: io.reactivex.internal.fuseable.HasUpstreamSingleSource */
public interface HasUpstreamSingleSource<T> {
    SingleSource<T> source();
}
