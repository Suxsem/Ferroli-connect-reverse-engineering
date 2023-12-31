package javax.servlet;

public class UnavailableException extends ServletException {
    private boolean permanent;
    private int seconds;
    private Servlet servlet;

    public UnavailableException(Servlet servlet2, String str) {
        super(str);
        this.servlet = servlet2;
        this.permanent = true;
    }

    public UnavailableException(int i, Servlet servlet2, String str) {
        super(str);
        this.servlet = servlet2;
        if (i <= 0) {
            this.seconds = -1;
        } else {
            this.seconds = i;
        }
        this.permanent = false;
    }

    public UnavailableException(String str) {
        super(str);
        this.permanent = true;
    }

    public UnavailableException(String str, int i) {
        super(str);
        if (i <= 0) {
            this.seconds = -1;
        } else {
            this.seconds = i;
        }
        this.permanent = false;
    }

    public boolean isPermanent() {
        return this.permanent;
    }

    public Servlet getServlet() {
        return this.servlet;
    }

    public int getUnavailableSeconds() {
        if (this.permanent) {
            return -1;
        }
        return this.seconds;
    }
}
