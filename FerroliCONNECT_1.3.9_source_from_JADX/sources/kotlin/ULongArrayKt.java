package kotlin;

import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;

@Metadata(mo22147bv = {1, 0, 3}, mo22148d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a-\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00060\u0005H\bø\u0001\u0000¢\u0006\u0002\u0010\u0007\u001a\u001f\u0010\b\u001a\u00020\u00012\n\u0010\t\u001a\u00020\u0001\"\u00020\u0006H\bø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000b\u0002\u0004\n\u0002\b\u0019¨\u0006\f"}, mo22149d2 = {"ULongArray", "Lkotlin/ULongArray;", "size", "", "init", "Lkotlin/Function1;", "Lkotlin/ULong;", "(ILkotlin/jvm/functions/Function1;)[J", "ulongArrayOf", "elements", "ulongArrayOf-QwZRm1k", "([J)[J", "kotlin-stdlib"}, mo22150k = 2, mo22151mv = {1, 1, 15})
/* compiled from: ULongArray.kt */
public final class ULongArrayKt {
    @ExperimentalUnsignedTypes
    @SinceKotlin(version = "1.3")
    @InlineOnly
    /* renamed from: ulongArrayOf-QwZRm1k  reason: not valid java name */
    private static final long[] m4166ulongArrayOfQwZRm1k(long... jArr) {
        return jArr;
    }

    @ExperimentalUnsignedTypes
    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final long[] ULongArray(int i, Function1<? super Integer, ULong> function1) {
        long[] jArr = new long[i];
        int length = jArr.length;
        for (int i2 = 0; i2 < length; i2++) {
            jArr[i2] = function1.invoke(Integer.valueOf(i2)).m4148unboximpl();
        }
        return ULongArray.m4151constructorimpl(jArr);
    }
}
