package kotlin.sequences;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(mo22147bv = {1, 0, 3}, mo22148d1 = {"\u0000\u0011\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010(\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u000f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00028\u00000\u0003H\u0002¨\u0006\u0004"}, mo22149d2 = {"kotlin/sequences/SequencesKt___SequencesKt$sortedWith$1", "Lkotlin/sequences/Sequence;", "iterator", "", "kotlin-stdlib"}, mo22150k = 1, mo22151mv = {1, 1, 15})
/* compiled from: _Sequences.kt */
public final class SequencesKt___SequencesKt$sortedWith$1 implements Sequence<T> {
    final /* synthetic */ Comparator $comparator;
    final /* synthetic */ Sequence $this_sortedWith;

    SequencesKt___SequencesKt$sortedWith$1(Sequence<? extends T> sequence, Comparator comparator) {
        this.$this_sortedWith = sequence;
        this.$comparator = comparator;
    }

    @NotNull
    public Iterator<T> iterator() {
        List mutableList = SequencesKt.toMutableList(this.$this_sortedWith);
        CollectionsKt.sortWith(mutableList, this.$comparator);
        return mutableList.iterator();
    }
}
