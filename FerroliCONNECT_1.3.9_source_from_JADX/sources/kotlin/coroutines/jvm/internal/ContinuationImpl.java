package kotlin.coroutines.jvm.internal;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationInterceptor;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mo22147bv = {1, 0, 3}, mo22148d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\b!\u0018\u00002\u00020\u0001B\u0019\b\u0016\u0012\u0010\u0010\u0002\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005B!\u0012\u0010\u0010\u0002\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0003J\b\u0010\r\u001a\u00020\u000eH\u0014R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\u00078VX\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0018\u0010\f\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0018\u00010\u0003X\u000e¢\u0006\u0002\n\u0000¨\u0006\u000f"}, mo22149d2 = {"Lkotlin/coroutines/jvm/internal/ContinuationImpl;", "Lkotlin/coroutines/jvm/internal/BaseContinuationImpl;", "completion", "Lkotlin/coroutines/Continuation;", "", "(Lkotlin/coroutines/Continuation;)V", "_context", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlin/coroutines/Continuation;Lkotlin/coroutines/CoroutineContext;)V", "context", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "intercepted", "releaseIntercepted", "", "kotlin-stdlib"}, mo22150k = 1, mo22151mv = {1, 1, 15})
@SinceKotlin(version = "1.3")
/* compiled from: ContinuationImpl.kt */
public abstract class ContinuationImpl extends BaseContinuationImpl {
    private final CoroutineContext _context;
    private transient Continuation<Object> intercepted;

    public ContinuationImpl(@Nullable Continuation<Object> continuation, @Nullable CoroutineContext coroutineContext) {
        super(continuation);
        this._context = coroutineContext;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public ContinuationImpl(@Nullable Continuation<Object> continuation) {
        this(continuation, continuation != null ? continuation.getContext() : null);
    }

    @NotNull
    public CoroutineContext getContext() {
        CoroutineContext coroutineContext = this._context;
        if (coroutineContext == null) {
            Intrinsics.throwNpe();
        }
        return coroutineContext;
    }

    @NotNull
    public final Continuation<Object> intercepted() {
        Continuation<Object> continuation = this.intercepted;
        if (continuation == null) {
            ContinuationInterceptor continuationInterceptor = (ContinuationInterceptor) getContext().get(ContinuationInterceptor.Key);
            if (continuationInterceptor == null || (continuation = continuationInterceptor.interceptContinuation(this)) == null) {
                continuation = this;
            }
            this.intercepted = continuation;
        }
        return continuation;
    }

    /* access modifiers changed from: protected */
    public void releaseIntercepted() {
        Continuation<Object> continuation = this.intercepted;
        if (!(continuation == null || continuation == this)) {
            CoroutineContext.Element element = getContext().get(ContinuationInterceptor.Key);
            if (element == null) {
                Intrinsics.throwNpe();
            }
            ((ContinuationInterceptor) element).releaseInterceptedContinuation(continuation);
        }
        this.intercepted = CompletedContinuation.INSTANCE;
    }
}
