package org.eclipse.jetty.server.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.http.MimeTypes;
import org.eclipse.jetty.server.AbstractHttpConnection;
import org.eclipse.jetty.server.Dispatcher;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.util.ByteArrayISO8859Writer;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class ErrorHandler extends AbstractHandler {
    public static final String ERROR_PAGE = "org.eclipse.jetty.server.error_page";
    private static final Logger LOG = Log.getLogger((Class<?>) ErrorHandler.class);
    String _cacheControl = "must-revalidate,no-cache,no-store";
    boolean _showMessageInTitle = true;
    boolean _showStacks = true;

    public interface ErrorPageMapper {
        String getErrorPage(HttpServletRequest httpServletRequest);
    }

    public void handle(String str, Request request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        String errorPage;
        String str2;
        AbstractHttpConnection currentConnection = AbstractHttpConnection.getCurrentConnection();
        String method = httpServletRequest.getMethod();
        if (method.equals("GET") || method.equals("POST") || method.equals("HEAD")) {
            if ((this instanceof ErrorPageMapper) && (errorPage = ((ErrorPageMapper) this).getErrorPage(httpServletRequest)) != null && httpServletRequest.getServletContext() != null && ((str2 = (String) httpServletRequest.getAttribute("org.eclipse.jetty.server.error_page")) == null || !str2.equals(errorPage))) {
                httpServletRequest.setAttribute("org.eclipse.jetty.server.error_page", errorPage);
                Dispatcher dispatcher = (Dispatcher) httpServletRequest.getServletContext().getRequestDispatcher(errorPage);
                if (dispatcher != null) {
                    try {
                        dispatcher.error(httpServletRequest, httpServletResponse);
                        return;
                    } catch (ServletException e) {
                        LOG.warn(Log.EXCEPTION, (Throwable) e);
                        return;
                    }
                } else {
                    Logger logger = LOG;
                    logger.warn("No error page " + errorPage, new Object[0]);
                }
            }
            currentConnection.getRequest().setHandled(true);
            httpServletResponse.setContentType(MimeTypes.TEXT_HTML_8859_1);
            String str3 = this._cacheControl;
            if (str3 != null) {
                httpServletResponse.setHeader("Cache-Control", str3);
            }
            ByteArrayISO8859Writer byteArrayISO8859Writer = new ByteArrayISO8859Writer(4096);
            handleErrorPage(httpServletRequest, byteArrayISO8859Writer, currentConnection.getResponse().getStatus(), currentConnection.getResponse().getReason());
            byteArrayISO8859Writer.flush();
            httpServletResponse.setContentLength(byteArrayISO8859Writer.size());
            byteArrayISO8859Writer.writeTo(httpServletResponse.getOutputStream());
            byteArrayISO8859Writer.destroy();
            return;
        }
        currentConnection.getRequest().setHandled(true);
    }

    /* access modifiers changed from: protected */
    public void handleErrorPage(HttpServletRequest httpServletRequest, Writer writer, int i, String str) throws IOException {
        writeErrorPage(httpServletRequest, writer, i, str, this._showStacks);
    }

    /* access modifiers changed from: protected */
    public void writeErrorPage(HttpServletRequest httpServletRequest, Writer writer, int i, String str, boolean z) throws IOException {
        if (str == null) {
            str = HttpStatus.getMessage(i);
        }
        String str2 = str;
        writer.write("<html>\n<head>\n");
        writeErrorPageHead(httpServletRequest, writer, i, str2);
        writer.write("</head>\n<body>");
        writeErrorPageBody(httpServletRequest, writer, i, str2, z);
        writer.write("\n</body>\n</html>\n");
    }

    /* access modifiers changed from: protected */
    public void writeErrorPageHead(HttpServletRequest httpServletRequest, Writer writer, int i, String str) throws IOException {
        writer.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\"/>\n");
        writer.write("<title>Error ");
        writer.write(Integer.toString(i));
        if (this._showMessageInTitle) {
            writer.write(32);
            write(writer, str);
        }
        writer.write("</title>\n");
    }

    /* access modifiers changed from: protected */
    public void writeErrorPageBody(HttpServletRequest httpServletRequest, Writer writer, int i, String str, boolean z) throws IOException {
        writeErrorPageMessage(httpServletRequest, writer, i, str, httpServletRequest.getRequestURI());
        if (z) {
            writeErrorPageStacks(httpServletRequest, writer);
        }
        writer.write("<hr /><i><small>Powered by Jetty://</small></i>");
        for (int i2 = 0; i2 < 20; i2++) {
            writer.write("<br/>                                                \n");
        }
    }

    /* access modifiers changed from: protected */
    public void writeErrorPageMessage(HttpServletRequest httpServletRequest, Writer writer, int i, String str, String str2) throws IOException {
        writer.write("<h2>HTTP ERROR ");
        writer.write(Integer.toString(i));
        writer.write("</h2>\n<p>Problem accessing ");
        write(writer, str2);
        writer.write(". Reason:\n<pre>    ");
        write(writer, str);
        writer.write("</pre></p>");
    }

    /* access modifiers changed from: protected */
    public void writeErrorPageStacks(HttpServletRequest httpServletRequest, Writer writer) throws IOException {
        for (Throwable th = (Throwable) httpServletRequest.getAttribute(RequestDispatcher.ERROR_EXCEPTION); th != null; th = th.getCause()) {
            writer.write("<h3>Caused by:</h3><pre>");
            StringWriter stringWriter = new StringWriter();
            PrintWriter printWriter = new PrintWriter(stringWriter);
            th.printStackTrace(printWriter);
            printWriter.flush();
            write(writer, stringWriter.getBuffer().toString());
            writer.write("</pre>\n");
        }
    }

    public String getCacheControl() {
        return this._cacheControl;
    }

    public void setCacheControl(String str) {
        this._cacheControl = str;
    }

    public boolean isShowStacks() {
        return this._showStacks;
    }

    public void setShowStacks(boolean z) {
        this._showStacks = z;
    }

    public void setShowMessageInTitle(boolean z) {
        this._showMessageInTitle = z;
    }

    public boolean getShowMessageInTitle() {
        return this._showMessageInTitle;
    }

    /* access modifiers changed from: protected */
    public void write(Writer writer, String str) throws IOException {
        if (str != null) {
            for (int i = 0; i < str.length(); i++) {
                char charAt = str.charAt(i);
                if (charAt == '&') {
                    writer.write("&amp;");
                } else if (charAt == '<') {
                    writer.write("&lt;");
                } else if (charAt == '>') {
                    writer.write("&gt;");
                } else if (!Character.isISOControl(charAt) || Character.isWhitespace(charAt)) {
                    writer.write(charAt);
                } else {
                    writer.write(63);
                }
            }
        }
    }
}
