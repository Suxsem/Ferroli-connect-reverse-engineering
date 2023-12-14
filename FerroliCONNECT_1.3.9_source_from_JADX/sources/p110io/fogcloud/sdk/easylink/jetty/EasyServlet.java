package p110io.fogcloud.sdk.easylink.jetty;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import p110io.fogcloud.sdk.easylink.helper.ComHelper;
import p110io.fogcloud.sdk.easylink.helper.EasyLinkCallBack;
import p110io.fogcloud.sdk.easylink.helper.EasyLinkErrCode;

/* renamed from: io.fogcloud.sdk.easylink.jetty.EasyServlet */
public class EasyServlet extends HttpServlet {
    private static final long serialVersionUID = 1;
    private ComHelper comfunc = new ComHelper();
    private EasyLinkCallBack elcb;

    public EasyServlet(EasyLinkCallBack easyLinkCallBack) {
        this.elcb = easyLinkCallBack;
    }

    /* access modifiers changed from: protected */
    public void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        PrintWriter writer = httpServletResponse.getWriter();
        writer.println("{}");
        writer.flush();
        this.comfunc.successCBEasyLink(EasyLinkErrCode.CALLBACK_CODE, readFully(httpServletRequest.getInputStream(), "utf8"), this.elcb);
    }

    public String readFully(InputStream inputStream, String str) throws IOException {
        return new String(readFully(inputStream), str);
    }

    private byte[] readFully(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] bArr = new byte[1024];
        while (true) {
            int read = inputStream.read(bArr);
            if (read == -1) {
                return byteArrayOutputStream.toByteArray();
            }
            byteArrayOutputStream.write(bArr, 0, read);
        }
    }
}
