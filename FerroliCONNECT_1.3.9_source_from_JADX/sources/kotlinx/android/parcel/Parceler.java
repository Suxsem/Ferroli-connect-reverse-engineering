package kotlinx.android.parcel;

import android.os.Parcel;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import org.jetbrains.annotations.NotNull;

@Metadata(mo22147bv = {1, 0, 3}, mo22148d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\bf\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002J\u0015\u0010\u0003\u001a\u00028\u00002\u0006\u0010\u0004\u001a\u00020\u0005H&¢\u0006\u0002\u0010\u0006J\u001b\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\b2\u0006\u0010\t\u001a\u00020\nH\u0016¢\u0006\u0002\u0010\u000bJ!\u0010\f\u001a\u00020\r*\u00028\u00002\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\nH&¢\u0006\u0002\u0010\u000f¨\u0006\u0010"}, mo22149d2 = {"Lkotlinx/android/parcel/Parceler;", "T", "", "create", "parcel", "Landroid/os/Parcel;", "(Landroid/os/Parcel;)Ljava/lang/Object;", "newArray", "", "size", "", "(I)[Ljava/lang/Object;", "write", "", "flags", "(Ljava/lang/Object;Landroid/os/Parcel;I)V", "kotlin-android-extensions-runtime"}, mo22150k = 1, mo22151mv = {1, 1, 15})
/* compiled from: Parceler.kt */
public interface Parceler<T> {
    T create(@NotNull Parcel parcel);

    @NotNull
    T[] newArray(int i);

    void write(T t, @NotNull Parcel parcel, int i);

    @Metadata(mo22147bv = {1, 0, 3}, mo22150k = 3, mo22151mv = {1, 1, 15})
    /* compiled from: Parceler.kt */
    public static final class DefaultImpls {
        @NotNull
        public static <T> T[] newArray(Parceler<T> parceler, int i) {
            throw new NotImplementedError("Generated by Android Extensions automatically");
        }
    }
}
