package org.eclipse.jetty.util.component;

import java.io.IOException;

public interface Dumpable {
    String dump();

    void dump(Appendable appendable, String str) throws IOException;
}
