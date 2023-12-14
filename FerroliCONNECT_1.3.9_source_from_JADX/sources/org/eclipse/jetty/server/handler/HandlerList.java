package org.eclipse.jetty.server.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;

public class HandlerList extends HandlerCollection {
    public void handle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException, ServletException {
        Handler[] handlers = getHandlers();
        if (handlers != null && isStarted()) {
            int i = 0;
            while (i < handlers.length) {
                handlers[i].handle(str, request, httpServletRequest, httpServletResponse);
                if (!request.isHandled()) {
                    i++;
                } else {
                    return;
                }
            }
        }
    }
}
