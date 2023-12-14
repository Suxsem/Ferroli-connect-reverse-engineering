package kotlin.text;

import android.support.p000v4.internal.view.SupportMenu;
import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UByte;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UShort;
import kotlin.UnsignedKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mo22147bv = {1, 0, 3}, mo22148d1 = {"\u0000,\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0013\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0005\u0010\u0006\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0004\b\b\u0010\t\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\n2\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0004\b\u000b\u0010\f\u001a\u001e\u0010\u0000\u001a\u00020\u0001*\u00020\r2\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0004\b\u000e\u0010\u000f\u001a\u0014\u0010\u0010\u001a\u00020\u0002*\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0011\u001a\u001c\u0010\u0010\u001a\u00020\u0002*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001a\u0011\u0010\u0013\u001a\u0004\u0018\u00010\u0002*\u00020\u0001H\u0007ø\u0001\u0000\u001a\u0019\u0010\u0013\u001a\u0004\u0018\u00010\u0002*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000\u001a\u0014\u0010\u0014\u001a\u00020\u0007*\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0015\u001a\u001c\u0010\u0014\u001a\u00020\u0007*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0016\u001a\u0011\u0010\u0017\u001a\u0004\u0018\u00010\u0007*\u00020\u0001H\u0007ø\u0001\u0000\u001a\u0019\u0010\u0017\u001a\u0004\u0018\u00010\u0007*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000\u001a\u0014\u0010\u0018\u001a\u00020\n*\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0019\u001a\u001c\u0010\u0018\u001a\u00020\n*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u001a\u001a\u0011\u0010\u001b\u001a\u0004\u0018\u00010\n*\u00020\u0001H\u0007ø\u0001\u0000\u001a\u0019\u0010\u001b\u001a\u0004\u0018\u00010\n*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000\u001a\u0014\u0010\u001c\u001a\u00020\r*\u00020\u0001H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u001d\u001a\u001c\u0010\u001c\u001a\u00020\r*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u001e\u001a\u0011\u0010\u001f\u001a\u0004\u0018\u00010\r*\u00020\u0001H\u0007ø\u0001\u0000\u001a\u0019\u0010\u001f\u001a\u0004\u0018\u00010\r*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0007ø\u0001\u0000\u0002\u0004\n\u0002\b\u0019¨\u0006 "}, mo22149d2 = {"toString", "", "Lkotlin/UByte;", "radix", "", "toString-LxnNnR4", "(BI)Ljava/lang/String;", "Lkotlin/UInt;", "toString-V7xB4Y4", "(II)Ljava/lang/String;", "Lkotlin/ULong;", "toString-JSWoG40", "(JI)Ljava/lang/String;", "Lkotlin/UShort;", "toString-olVBNx4", "(SI)Ljava/lang/String;", "toUByte", "(Ljava/lang/String;)B", "(Ljava/lang/String;I)B", "toUByteOrNull", "toUInt", "(Ljava/lang/String;)I", "(Ljava/lang/String;I)I", "toUIntOrNull", "toULong", "(Ljava/lang/String;)J", "(Ljava/lang/String;I)J", "toULongOrNull", "toUShort", "(Ljava/lang/String;)S", "(Ljava/lang/String;I)S", "toUShortOrNull", "kotlin-stdlib"}, mo22150k = 2, mo22151mv = {1, 1, 15})
@JvmName(name = "UStringsKt")
/* compiled from: UStrings.kt */
public final class UStringsKt {
    @ExperimentalUnsignedTypes
    @NotNull
    @SinceKotlin(version = "1.3")
    /* renamed from: toString-LxnNnR4  reason: not valid java name */
    public static final String m4818toStringLxnNnR4(byte b, int i) {
        String num = Integer.toString(b & UByte.MAX_VALUE, CharsKt.checkRadix(i));
        Intrinsics.checkExpressionValueIsNotNull(num, "java.lang.Integer.toStri…(this, checkRadix(radix))");
        return num;
    }

    @ExperimentalUnsignedTypes
    @NotNull
    @SinceKotlin(version = "1.3")
    /* renamed from: toString-olVBNx4  reason: not valid java name */
    public static final String m4820toStringolVBNx4(short s, int i) {
        String num = Integer.toString(s & UShort.MAX_VALUE, CharsKt.checkRadix(i));
        Intrinsics.checkExpressionValueIsNotNull(num, "java.lang.Integer.toStri…(this, checkRadix(radix))");
        return num;
    }

    @ExperimentalUnsignedTypes
    @NotNull
    @SinceKotlin(version = "1.3")
    /* renamed from: toString-V7xB4Y4  reason: not valid java name */
    public static final String m4819toStringV7xB4Y4(int i, int i2) {
        String l = Long.toString(((long) i) & 4294967295L, CharsKt.checkRadix(i2));
        Intrinsics.checkExpressionValueIsNotNull(l, "java.lang.Long.toString(this, checkRadix(radix))");
        return l;
    }

    @ExperimentalUnsignedTypes
    @NotNull
    @SinceKotlin(version = "1.3")
    /* renamed from: toString-JSWoG40  reason: not valid java name */
    public static final String m4817toStringJSWoG40(long j, int i) {
        return UnsignedKt.ulongToString(j, CharsKt.checkRadix(i));
    }

    @ExperimentalUnsignedTypes
    @SinceKotlin(version = "1.3")
    public static final byte toUByte(@NotNull String str) {
        Intrinsics.checkParameterIsNotNull(str, "$this$toUByte");
        UByte uByteOrNull = toUByteOrNull(str);
        if (uByteOrNull != null) {
            return uByteOrNull.m4010unboximpl();
        }
        StringsKt.numberFormatError(str);
        throw null;
    }

    @ExperimentalUnsignedTypes
    @SinceKotlin(version = "1.3")
    public static final byte toUByte(@NotNull String str, int i) {
        Intrinsics.checkParameterIsNotNull(str, "$this$toUByte");
        UByte uByteOrNull = toUByteOrNull(str, i);
        if (uByteOrNull != null) {
            return uByteOrNull.m4010unboximpl();
        }
        StringsKt.numberFormatError(str);
        throw null;
    }

    @ExperimentalUnsignedTypes
    @SinceKotlin(version = "1.3")
    public static final short toUShort(@NotNull String str) {
        Intrinsics.checkParameterIsNotNull(str, "$this$toUShort");
        UShort uShortOrNull = toUShortOrNull(str);
        if (uShortOrNull != null) {
            return uShortOrNull.m4215unboximpl();
        }
        StringsKt.numberFormatError(str);
        throw null;
    }

    @ExperimentalUnsignedTypes
    @SinceKotlin(version = "1.3")
    public static final short toUShort(@NotNull String str, int i) {
        Intrinsics.checkParameterIsNotNull(str, "$this$toUShort");
        UShort uShortOrNull = toUShortOrNull(str, i);
        if (uShortOrNull != null) {
            return uShortOrNull.m4215unboximpl();
        }
        StringsKt.numberFormatError(str);
        throw null;
    }

    @ExperimentalUnsignedTypes
    @SinceKotlin(version = "1.3")
    public static final int toUInt(@NotNull String str) {
        Intrinsics.checkParameterIsNotNull(str, "$this$toUInt");
        UInt uIntOrNull = toUIntOrNull(str);
        if (uIntOrNull != null) {
            return uIntOrNull.m4079unboximpl();
        }
        StringsKt.numberFormatError(str);
        throw null;
    }

    @ExperimentalUnsignedTypes
    @SinceKotlin(version = "1.3")
    public static final int toUInt(@NotNull String str, int i) {
        Intrinsics.checkParameterIsNotNull(str, "$this$toUInt");
        UInt uIntOrNull = toUIntOrNull(str, i);
        if (uIntOrNull != null) {
            return uIntOrNull.m4079unboximpl();
        }
        StringsKt.numberFormatError(str);
        throw null;
    }

    @ExperimentalUnsignedTypes
    @SinceKotlin(version = "1.3")
    public static final long toULong(@NotNull String str) {
        Intrinsics.checkParameterIsNotNull(str, "$this$toULong");
        ULong uLongOrNull = toULongOrNull(str);
        if (uLongOrNull != null) {
            return uLongOrNull.m4148unboximpl();
        }
        StringsKt.numberFormatError(str);
        throw null;
    }

    @ExperimentalUnsignedTypes
    @SinceKotlin(version = "1.3")
    public static final long toULong(@NotNull String str, int i) {
        Intrinsics.checkParameterIsNotNull(str, "$this$toULong");
        ULong uLongOrNull = toULongOrNull(str, i);
        if (uLongOrNull != null) {
            return uLongOrNull.m4148unboximpl();
        }
        StringsKt.numberFormatError(str);
        throw null;
    }

    @ExperimentalUnsignedTypes
    @SinceKotlin(version = "1.3")
    @Nullable
    public static final UByte toUByteOrNull(@NotNull String str) {
        Intrinsics.checkParameterIsNotNull(str, "$this$toUByteOrNull");
        return toUByteOrNull(str, 10);
    }

    @ExperimentalUnsignedTypes
    @SinceKotlin(version = "1.3")
    @Nullable
    public static final UByte toUByteOrNull(@NotNull String str, int i) {
        Intrinsics.checkParameterIsNotNull(str, "$this$toUByteOrNull");
        UInt uIntOrNull = toUIntOrNull(str, i);
        if (uIntOrNull == null) {
            return null;
        }
        int r1 = uIntOrNull.m4079unboximpl();
        if (UnsignedKt.uintCompare(r1, UInt.m4036constructorimpl(255)) > 0) {
            return null;
        }
        return UByte.m3963boximpl(UByte.m3969constructorimpl((byte) r1));
    }

    @ExperimentalUnsignedTypes
    @SinceKotlin(version = "1.3")
    @Nullable
    public static final UShort toUShortOrNull(@NotNull String str) {
        Intrinsics.checkParameterIsNotNull(str, "$this$toUShortOrNull");
        return toUShortOrNull(str, 10);
    }

    @ExperimentalUnsignedTypes
    @SinceKotlin(version = "1.3")
    @Nullable
    public static final UShort toUShortOrNull(@NotNull String str, int i) {
        Intrinsics.checkParameterIsNotNull(str, "$this$toUShortOrNull");
        UInt uIntOrNull = toUIntOrNull(str, i);
        if (uIntOrNull == null) {
            return null;
        }
        int r1 = uIntOrNull.m4079unboximpl();
        if (UnsignedKt.uintCompare(r1, UInt.m4036constructorimpl(SupportMenu.USER_MASK)) > 0) {
            return null;
        }
        return UShort.m4168boximpl(UShort.m4174constructorimpl((short) r1));
    }

    @ExperimentalUnsignedTypes
    @SinceKotlin(version = "1.3")
    @Nullable
    public static final UInt toUIntOrNull(@NotNull String str) {
        Intrinsics.checkParameterIsNotNull(str, "$this$toUIntOrNull");
        return toUIntOrNull(str, 10);
    }

    @ExperimentalUnsignedTypes
    @SinceKotlin(version = "1.3")
    @Nullable
    public static final UInt toUIntOrNull(@NotNull String str, int i) {
        Intrinsics.checkParameterIsNotNull(str, "$this$toUIntOrNull");
        CharsKt.checkRadix(i);
        int length = str.length();
        if (length == 0) {
            return null;
        }
        int i2 = 0;
        char charAt = str.charAt(0);
        int i3 = 1;
        if (charAt >= '0') {
            i3 = 0;
        } else if (length == 1 || charAt != '+') {
            return null;
        }
        int r4 = UInt.m4036constructorimpl(i);
        int r2 = UnsignedKt.m4234uintDivideJ1ME1BU(-1, r4);
        while (i3 < length) {
            int digitOf = CharsKt.digitOf(str.charAt(i3), i);
            if (digitOf < 0 || UnsignedKt.uintCompare(i2, r2) > 0) {
                return null;
            }
            int r3 = UInt.m4036constructorimpl(i2 * r4);
            int r5 = UInt.m4036constructorimpl(UInt.m4036constructorimpl(digitOf) + r3);
            if (UnsignedKt.uintCompare(r5, r3) < 0) {
                return null;
            }
            i3++;
            i2 = r5;
        }
        return UInt.m4030boximpl(i2);
    }

    @ExperimentalUnsignedTypes
    @SinceKotlin(version = "1.3")
    @Nullable
    public static final ULong toULongOrNull(@NotNull String str) {
        Intrinsics.checkParameterIsNotNull(str, "$this$toULongOrNull");
        return toULongOrNull(str, 10);
    }

    @ExperimentalUnsignedTypes
    @SinceKotlin(version = "1.3")
    @Nullable
    public static final ULong toULongOrNull(@NotNull String str, int i) {
        Intrinsics.checkParameterIsNotNull(str, "$this$toULongOrNull");
        CharsKt.checkRadix(i);
        int length = str.length();
        if (length == 0) {
            return null;
        }
        int i2 = 0;
        char charAt = str.charAt(0);
        if (charAt < '0') {
            if (length == 1 || charAt != '+') {
                return null;
            }
            i2 = 1;
        }
        long r5 = ((long) UInt.m4036constructorimpl(i)) & 4294967295L;
        long r2 = UnsignedKt.m4236ulongDivideeb3DHEI(-1, ULong.m4105constructorimpl(r5));
        long j = 0;
        while (i2 < length) {
            int digitOf = CharsKt.digitOf(str.charAt(i2), i);
            if (digitOf < 0 || UnsignedKt.ulongCompare(j, r2) > 0) {
                return null;
            }
            long r9 = ULong.m4105constructorimpl(j * ULong.m4105constructorimpl(r5));
            long r11 = ULong.m4105constructorimpl(ULong.m4105constructorimpl(((long) UInt.m4036constructorimpl(digitOf)) & 4294967295L) + r9);
            if (UnsignedKt.ulongCompare(r11, r9) < 0) {
                return null;
            }
            i2++;
            j = r11;
        }
        return ULong.m4099boximpl(j);
    }
}
