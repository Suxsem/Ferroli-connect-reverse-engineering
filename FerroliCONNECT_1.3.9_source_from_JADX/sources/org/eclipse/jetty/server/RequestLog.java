package org.eclipse.jetty.server;

import org.eclipse.jetty.util.component.LifeCycle;

public interface RequestLog extends LifeCycle {
    void log(Request request, Response response);
}
