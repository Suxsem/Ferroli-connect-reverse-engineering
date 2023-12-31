package kotlin.coroutines.experimental.intrinsics;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.coroutines.experimental.Continuation;
import kotlin.coroutines.experimental.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mo22147bv = {1, 0, 3}, mo22148d1 = {"\u00005\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\b\u0007\n\u0002\b\u0007\n\u0002\b\u0007\n\u0002\b\u0007\n\u0002\b\u0007\n\u0002\b\u0007\n\u0002\u0010\u0003\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u0015\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u0002H\u0016¢\u0006\u0002\u0010\tJ\u0010\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u0014\u0010\u0003\u001a\u00020\u00048VX\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006¨\u0006\r¸\u0006\u0000"}, mo22149d2 = {"kotlin/coroutines/experimental/intrinsics/IntrinsicsKt__IntrinsicsJvmKt$buildContinuationByInvokeCall$continuation$1", "Lkotlin/coroutines/experimental/Continuation;", "", "context", "Lkotlin/coroutines/experimental/CoroutineContext;", "getContext", "()Lkotlin/coroutines/experimental/CoroutineContext;", "resume", "value", "(Lkotlin/Unit;)V", "resumeWithException", "exception", "", "kotlin-stdlib_coroutinesExperimental"}, mo22150k = 1, mo22151mv = {1, 1, 15})
/* renamed from: kotlin.coroutines.experimental.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnchecked$$inlined$buildContinuationByInvokeCall$IntrinsicsKt__IntrinsicsJvmKt$1 */
/* compiled from: IntrinsicsJvm.kt */
public final class C2312x6df69f96 implements Continuation<Unit> {
    final /* synthetic */ Continuation $completion;
    final /* synthetic */ Continuation $completion$inlined;
    final /* synthetic */ Function1 $this_createCoroutineUnchecked$inlined;

    public C2312x6df69f96(Continuation continuation, Function1 function1, Continuation continuation2) {
        this.$completion = continuation;
        this.$this_createCoroutineUnchecked$inlined = function1;
        this.$completion$inlined = continuation2;
    }

    @NotNull
    public CoroutineContext getContext() {
        return this.$completion.getContext();
    }

    public void resume(@NotNull Unit unit) {
        Intrinsics.checkParameterIsNotNull(unit, "value");
        Continuation continuation = this.$completion;
        try {
            Function1 function1 = this.$this_createCoroutineUnchecked$inlined;
            if (function1 != null) {
                Object invoke = ((Function1) TypeIntrinsics.beforeCheckcastToFunctionOfArity(function1, 1)).invoke(this.$completion$inlined);
                if (invoke == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    return;
                }
                if (continuation != null) {
                    continuation.resume(invoke);
                    return;
                }
                throw new TypeCastException("null cannot be cast to non-null type kotlin.coroutines.experimental.Continuation<kotlin.Any?>");
            }
            throw new TypeCastException("null cannot be cast to non-null type (kotlin.coroutines.experimental.Continuation<T>) -> kotlin.Any?");
        } catch (Throwable th) {
            continuation.resumeWithException(th);
        }
    }

    public void resumeWithException(@NotNull Throwable th) {
        Intrinsics.checkParameterIsNotNull(th, MqttServiceConstants.TRACE_EXCEPTION);
        this.$completion.resumeWithException(th);
    }
}
