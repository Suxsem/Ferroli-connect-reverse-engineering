package kotlin.sequences;

import com.szacs.ferroliconnect.util.LanguageUtil;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mo22147bv = {1, 0, 3}, mo22148d1 = {"\u0000\f\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u0002H\u0001H\n¢\u0006\u0004\b\u0004\u0010\u0005"}, mo22149d2 = {"<anonymous>", "T", "", "it", "invoke", "(Ljava/lang/Object;)Ljava/lang/Object;"}, mo22150k = 3, mo22151mv = {1, 1, 15})
/* compiled from: Sequences.kt */
final class SequencesKt__SequencesKt$generateSequence$1 extends Lambda implements Function1<T, T> {
    final /* synthetic */ Function0 $nextFunction;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    SequencesKt__SequencesKt$generateSequence$1(Function0 function0) {
        super(1);
        this.$nextFunction = function0;
    }

    @Nullable
    public final T invoke(@NotNull T t) {
        Intrinsics.checkParameterIsNotNull(t, LanguageUtil.LOGOGRAM_ITALY);
        return this.$nextFunction.invoke();
    }
}
