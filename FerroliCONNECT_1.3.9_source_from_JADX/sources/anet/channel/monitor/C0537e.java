package anet.channel.monitor;

/* renamed from: anet.channel.monitor.e */
/* compiled from: Taobao */
class C0537e {

    /* renamed from: a */
    private long f307a = 0;

    /* renamed from: b */
    private double f308b;

    /* renamed from: c */
    private double f309c;

    /* renamed from: d */
    private double f310d;

    /* renamed from: e */
    private double f311e;

    /* renamed from: f */
    private double f312f;

    /* renamed from: g */
    private double f313g;

    /* renamed from: h */
    private double f314h;

    /* renamed from: i */
    private double f315i = 0.0d;

    /* renamed from: j */
    private double f316j = 0.0d;

    /* renamed from: k */
    private double f317k = 0.0d;

    C0537e() {
    }

    /* renamed from: a */
    public double mo8848a(double d, double d2) {
        double d3 = d / d2;
        if (d3 >= 8.0d) {
            long j = this.f307a;
            if (j == 0) {
                this.f315i = d3;
                this.f314h = this.f315i;
                double d4 = this.f314h;
                this.f310d = d4 * 0.1d;
                this.f309c = 0.02d * d4;
                this.f311e = 0.1d * d4 * d4;
            } else if (j == 1) {
                this.f316j = d3;
                this.f314h = this.f316j;
            } else {
                double d5 = this.f316j;
                double d6 = d3 - d5;
                this.f315i = d5;
                this.f316j = d3;
                this.f308b = d3 / 0.95d;
                this.f313g = this.f308b - (this.f314h * 0.95d);
                char c = 0;
                double sqrt = Math.sqrt(this.f310d);
                double d7 = this.f313g;
                if (d7 >= 4.0d * sqrt) {
                    this.f313g = (d7 * 0.75d) + (sqrt * 2.0d);
                    c = 1;
                } else if (d7 <= -4.0d * sqrt) {
                    this.f313g = (sqrt * -1.0d) + (d7 * 0.75d);
                    c = 2;
                }
                double d8 = this.f313g;
                this.f310d = Math.min(Math.max(Math.abs((this.f310d * 1.05d) - ((0.0025d * d8) * d8)), this.f310d * 0.8d), this.f310d * 1.25d);
                double d9 = this.f311e;
                this.f312f = d9 / ((0.9025d * d9) + this.f310d);
                this.f314h = this.f314h + (1.0526315789473684d * d6) + (this.f312f * this.f313g);
                if (c == 1) {
                    this.f314h = Math.min(this.f314h, this.f308b);
                } else if (c == 2) {
                    this.f314h = Math.max(this.f314h, this.f308b);
                }
                this.f311e = (1.0d - (0.95d * this.f312f)) * (this.f311e + this.f309c);
            }
            double d10 = this.f314h;
            if (d10 < 0.0d) {
                this.f317k = this.f316j * 0.7d;
                this.f314h = this.f317k;
            } else {
                this.f317k = d10;
            }
            return this.f317k;
        } else if (this.f307a != 0) {
            return this.f317k;
        } else {
            this.f317k = d3;
            return this.f317k;
        }
    }

    /* renamed from: a */
    public void mo8849a() {
        this.f307a = 0;
        this.f317k = 0.0d;
    }
}
