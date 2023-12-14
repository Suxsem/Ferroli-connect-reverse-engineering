package org.eclipse.jetty.p119io;

import java.io.EOFException;

/* renamed from: org.eclipse.jetty.io.EofException */
public class EofException extends EOFException {
    public EofException() {
    }

    public EofException(String str) {
        super(str);
    }

    public EofException(Throwable th) {
        if (th != null) {
            initCause(th);
        }
    }
}
