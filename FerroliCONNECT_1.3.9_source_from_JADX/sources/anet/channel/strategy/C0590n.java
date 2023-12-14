package anet.channel.strategy;

import java.io.File;
import java.util.Comparator;

/* renamed from: anet.channel.strategy.n */
/* compiled from: Taobao */
final class C0590n implements Comparator<File> {
    C0590n() {
    }

    /* renamed from: a */
    public int compare(File file, File file2) {
        return (int) (file2.lastModified() - file.lastModified());
    }
}
