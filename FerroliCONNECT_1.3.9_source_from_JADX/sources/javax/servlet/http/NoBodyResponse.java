package javax.servlet.http;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ResourceBundle;
import javax.servlet.ServletOutputStream;

/* compiled from: HttpServlet */
class NoBodyResponse extends HttpServletResponseWrapper {
    private static final ResourceBundle lStrings = ResourceBundle.getBundle("javax.servlet.http.LocalStrings");
    private boolean didSetContentLength;
    private NoBodyOutputStream noBody = new NoBodyOutputStream();
    private boolean usingOutputStream;
    private PrintWriter writer;

    NoBodyResponse(HttpServletResponse httpServletResponse) {
        super(httpServletResponse);
    }

    /* access modifiers changed from: package-private */
    public void setContentLength() {
        if (!this.didSetContentLength) {
            PrintWriter printWriter = this.writer;
            if (printWriter != null) {
                printWriter.flush();
            }
            setContentLength(this.noBody.getContentLength());
        }
    }

    public void setContentLength(int i) {
        super.setContentLength(i);
        this.didSetContentLength = true;
    }

    public ServletOutputStream getOutputStream() throws IOException {
        if (this.writer == null) {
            this.usingOutputStream = true;
            return this.noBody;
        }
        throw new IllegalStateException(lStrings.getString("err.ise.getOutputStream"));
    }

    public PrintWriter getWriter() throws UnsupportedEncodingException {
        if (!this.usingOutputStream) {
            if (this.writer == null) {
                this.writer = new PrintWriter(new OutputStreamWriter(this.noBody, getCharacterEncoding()));
            }
            return this.writer;
        }
        throw new IllegalStateException(lStrings.getString("err.ise.getWriter"));
    }
}
