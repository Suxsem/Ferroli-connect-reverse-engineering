package kotlin.concurrent;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;

@Metadata(mo22147bv = {1, 0, 3}, mo22148d1 = {"\u0000\u0011\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016¨\u0006\u0004"}, mo22149d2 = {"kotlin/concurrent/ThreadsKt$thread$thread$1", "Ljava/lang/Thread;", "run", "", "kotlin-stdlib"}, mo22150k = 1, mo22151mv = {1, 1, 15})
/* compiled from: Thread.kt */
public final class ThreadsKt$thread$thread$1 extends Thread {
    final /* synthetic */ Function0 $block;

    ThreadsKt$thread$thread$1(Function0 function0) {
        this.$block = function0;
    }

    public void run() {
        this.$block.invoke();
    }
}