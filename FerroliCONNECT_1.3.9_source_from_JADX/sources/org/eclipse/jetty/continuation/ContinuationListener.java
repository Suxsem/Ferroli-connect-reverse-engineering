package org.eclipse.jetty.continuation;

import java.util.EventListener;

public interface ContinuationListener extends EventListener {
    void onComplete(Continuation continuation);

    void onTimeout(Continuation continuation);
}
