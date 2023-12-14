package kotlin.collections;

import java.util.Collection;
import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UByte;
import kotlin.UByteArray;
import kotlin.UInt;
import kotlin.UIntArray;
import kotlin.ULong;
import kotlin.ULongArray;
import kotlin.UShort;
import kotlin.UShortArray;
import kotlin.jvm.JvmName;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mo22147bv = {1, 0, 3}, mo22148d1 = {"\u0000F\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0004\u0010\u0005\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00010\u0002H\u0007ø\u0001\u0000¢\u0006\u0004\b\u0006\u0010\u0005\u001a\u001c\u0010\u0000\u001a\u00020\u0007*\b\u0012\u0004\u0012\u00020\u00070\u0002H\u0007ø\u0001\u0000¢\u0006\u0004\b\b\u0010\t\u001a\u001c\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\n0\u0002H\u0007ø\u0001\u0000¢\u0006\u0004\b\u000b\u0010\u0005\u001a\u001a\u0010\f\u001a\u00020\r*\b\u0012\u0004\u0012\u00020\u00030\u000eH\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u000f\u001a\u001a\u0010\u0010\u001a\u00020\u0011*\b\u0012\u0004\u0012\u00020\u00010\u000eH\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0012\u001a\u001a\u0010\u0013\u001a\u00020\u0014*\b\u0012\u0004\u0012\u00020\u00070\u000eH\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0015\u001a\u001a\u0010\u0016\u001a\u00020\u0017*\b\u0012\u0004\u0012\u00020\n0\u000eH\u0007ø\u0001\u0000¢\u0006\u0002\u0010\u0018\u0002\u0004\n\u0002\b\u0019¨\u0006\u0019"}, mo22149d2 = {"sum", "Lkotlin/UInt;", "", "Lkotlin/UByte;", "sumOfUByte", "(Ljava/lang/Iterable;)I", "sumOfUInt", "Lkotlin/ULong;", "sumOfULong", "(Ljava/lang/Iterable;)J", "Lkotlin/UShort;", "sumOfUShort", "toUByteArray", "Lkotlin/UByteArray;", "", "(Ljava/util/Collection;)[B", "toUIntArray", "Lkotlin/UIntArray;", "(Ljava/util/Collection;)[I", "toULongArray", "Lkotlin/ULongArray;", "(Ljava/util/Collection;)[J", "toUShortArray", "Lkotlin/UShortArray;", "(Ljava/util/Collection;)[S", "kotlin-stdlib"}, mo22150k = 5, mo22151mv = {1, 1, 15}, mo22153xi = 1, mo22154xs = "kotlin/collections/UCollectionsKt")
/* compiled from: _UCollections.kt */
class UCollectionsKt___UCollectionsKt {
    @ExperimentalUnsignedTypes
    @SinceKotlin(version = "1.3")
    @NotNull
    public static final byte[] toUByteArray(@NotNull Collection<UByte> collection) {
        Intrinsics.checkParameterIsNotNull(collection, "$this$toUByteArray");
        byte[] r0 = UByteArray.m4012constructorimpl(collection.size());
        int i = 0;
        for (UByte r2 : collection) {
            UByteArray.m4023setVurrAj0(r0, i, r2.m4010unboximpl());
            i++;
        }
        return r0;
    }

    @ExperimentalUnsignedTypes
    @SinceKotlin(version = "1.3")
    @NotNull
    public static final int[] toUIntArray(@NotNull Collection<UInt> collection) {
        Intrinsics.checkParameterIsNotNull(collection, "$this$toUIntArray");
        int[] r0 = UIntArray.m4081constructorimpl(collection.size());
        int i = 0;
        for (UInt r2 : collection) {
            UIntArray.m4092setVXSXFK8(r0, i, r2.m4079unboximpl());
            i++;
        }
        return r0;
    }

    @ExperimentalUnsignedTypes
    @SinceKotlin(version = "1.3")
    @NotNull
    public static final long[] toULongArray(@NotNull Collection<ULong> collection) {
        Intrinsics.checkParameterIsNotNull(collection, "$this$toULongArray");
        long[] r0 = ULongArray.m4150constructorimpl(collection.size());
        int i = 0;
        for (ULong r2 : collection) {
            ULongArray.m4161setk8EXiF4(r0, i, r2.m4148unboximpl());
            i++;
        }
        return r0;
    }

    @ExperimentalUnsignedTypes
    @SinceKotlin(version = "1.3")
    @NotNull
    public static final short[] toUShortArray(@NotNull Collection<UShort> collection) {
        Intrinsics.checkParameterIsNotNull(collection, "$this$toUShortArray");
        short[] r0 = UShortArray.m4217constructorimpl(collection.size());
        int i = 0;
        for (UShort r2 : collection) {
            UShortArray.m4228set01HTLdE(r0, i, r2.m4215unboximpl());
            i++;
        }
        return r0;
    }

    @ExperimentalUnsignedTypes
    @SinceKotlin(version = "1.3")
    @JvmName(name = "sumOfUInt")
    public static final int sumOfUInt(@NotNull Iterable<UInt> iterable) {
        Intrinsics.checkParameterIsNotNull(iterable, "$this$sum");
        int i = 0;
        for (UInt r1 : iterable) {
            i = UInt.m4036constructorimpl(i + r1.m4079unboximpl());
        }
        return i;
    }

    @ExperimentalUnsignedTypes
    @SinceKotlin(version = "1.3")
    @JvmName(name = "sumOfULong")
    public static final long sumOfULong(@NotNull Iterable<ULong> iterable) {
        Intrinsics.checkParameterIsNotNull(iterable, "$this$sum");
        long j = 0;
        for (ULong r2 : iterable) {
            j = ULong.m4105constructorimpl(j + r2.m4148unboximpl());
        }
        return j;
    }

    @ExperimentalUnsignedTypes
    @SinceKotlin(version = "1.3")
    @JvmName(name = "sumOfUByte")
    public static final int sumOfUByte(@NotNull Iterable<UByte> iterable) {
        Intrinsics.checkParameterIsNotNull(iterable, "$this$sum");
        int i = 0;
        for (UByte r1 : iterable) {
            i = UInt.m4036constructorimpl(i + UInt.m4036constructorimpl(r1.m4010unboximpl() & UByte.MAX_VALUE));
        }
        return i;
    }

    @ExperimentalUnsignedTypes
    @SinceKotlin(version = "1.3")
    @JvmName(name = "sumOfUShort")
    public static final int sumOfUShort(@NotNull Iterable<UShort> iterable) {
        Intrinsics.checkParameterIsNotNull(iterable, "$this$sum");
        int i = 0;
        for (UShort r1 : iterable) {
            i = UInt.m4036constructorimpl(i + UInt.m4036constructorimpl(r1.m4215unboximpl() & UShort.MAX_VALUE));
        }
        return i;
    }
}
