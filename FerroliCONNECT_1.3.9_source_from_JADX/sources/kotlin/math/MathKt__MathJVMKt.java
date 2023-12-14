package kotlin.math;

import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.DoubleCompanionObject;
import kotlin.jvm.internal.FloatCompanionObject;

@Metadata(mo22147bv = {1, 0, 3}, mo22148d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b7\u001a\u0011\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\b\u001a\u0011\u0010\u0016\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\b\u001a\u0011\u0010\u0016\u001a\u00020\t2\u0006\u0010\u0018\u001a\u00020\tH\b\u001a\u0011\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0018\u001a\u00020\fH\b\u001a\u0011\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\b\u001a\u0011\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\b\u001a\u0010\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0007\u001a\u0011\u0010\u001a\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\b\u001a\u0011\u0010\u001b\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\b\u001a\u0011\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\b\u001a\u0010\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0007\u001a\u0011\u0010\u001c\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\b\u001a\u0011\u0010\u001d\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\b\u001a\u0011\u0010\u001d\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\b\u001a\u0019\u0010\u001e\u001a\u00020\u00012\u0006\u0010\u001f\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\b\u001a\u0019\u0010\u001e\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\b\u001a\u0010\u0010 \u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0007\u001a\u0011\u0010 \u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\b\u001a\u0011\u0010!\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\b\u001a\u0011\u0010!\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\b\u001a\u0011\u0010\"\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\b\u001a\u0011\u0010\"\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\b\u001a\u0011\u0010#\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\b\u001a\u0011\u0010#\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\b\u001a\u0011\u0010$\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\b\u001a\u0011\u0010$\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\b\u001a\u0011\u0010%\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\b\u001a\u0011\u0010%\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\b\u001a\u0011\u0010&\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\b\u001a\u0011\u0010&\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\b\u001a\u0019\u0010'\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u00012\u0006\u0010\u001f\u001a\u00020\u0001H\b\u001a\u0019\u0010'\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u001f\u001a\u00020\u0006H\b\u001a\u0011\u0010(\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\b\u001a\u0011\u0010(\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\b\u001a\u0011\u0010)\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\b\u001a\u0011\u0010)\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\b\u001a\u0018\u0010*\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u00012\u0006\u0010+\u001a\u00020\u0001H\u0007\u001a\u0018\u0010*\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00062\u0006\u0010+\u001a\u00020\u0006H\u0007\u001a\u0011\u0010,\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\b\u001a\u0011\u0010,\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\b\u001a\u0010\u0010-\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0007\u001a\u0010\u0010-\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0007\u001a\u0019\u0010.\u001a\u00020\u00012\u0006\u0010/\u001a\u00020\u00012\u0006\u00100\u001a\u00020\u0001H\b\u001a\u0019\u0010.\u001a\u00020\u00062\u0006\u0010/\u001a\u00020\u00062\u0006\u00100\u001a\u00020\u0006H\b\u001a\u0019\u0010.\u001a\u00020\t2\u0006\u0010/\u001a\u00020\t2\u0006\u00100\u001a\u00020\tH\b\u001a\u0019\u0010.\u001a\u00020\f2\u0006\u0010/\u001a\u00020\f2\u0006\u00100\u001a\u00020\fH\b\u001a\u0019\u00101\u001a\u00020\u00012\u0006\u0010/\u001a\u00020\u00012\u0006\u00100\u001a\u00020\u0001H\b\u001a\u0019\u00101\u001a\u00020\u00062\u0006\u0010/\u001a\u00020\u00062\u0006\u00100\u001a\u00020\u0006H\b\u001a\u0019\u00101\u001a\u00020\t2\u0006\u0010/\u001a\u00020\t2\u0006\u00100\u001a\u00020\tH\b\u001a\u0019\u00101\u001a\u00020\f2\u0006\u0010/\u001a\u00020\f2\u0006\u00100\u001a\u00020\fH\b\u001a\u0011\u00102\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\b\u001a\u0011\u00102\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\b\u001a\u0011\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\b\u001a\u0011\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\b\u001a\u0011\u00103\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\b\u001a\u0011\u00103\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\b\u001a\u0011\u00104\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\b\u001a\u0011\u00104\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\b\u001a\u0011\u00105\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\b\u001a\u0011\u00105\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\b\u001a\u0011\u00106\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\b\u001a\u0011\u00106\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\b\u001a\u0011\u00107\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\b\u001a\u0011\u00107\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\b\u001a\u0010\u00108\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\u0007\u001a\u0010\u00108\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\u0007\u001a\u0015\u00109\u001a\u00020\u0001*\u00020\u00012\u0006\u0010:\u001a\u00020\u0001H\b\u001a\u0015\u00109\u001a\u00020\u0006*\u00020\u00062\u0006\u0010:\u001a\u00020\u0006H\b\u001a\r\u0010;\u001a\u00020\u0001*\u00020\u0001H\b\u001a\r\u0010;\u001a\u00020\u0006*\u00020\u0006H\b\u001a\u0015\u0010<\u001a\u00020\u0001*\u00020\u00012\u0006\u0010=\u001a\u00020\u0001H\b\u001a\u0015\u0010<\u001a\u00020\u0006*\u00020\u00062\u0006\u0010=\u001a\u00020\u0006H\b\u001a\r\u0010>\u001a\u00020\u0001*\u00020\u0001H\b\u001a\r\u0010>\u001a\u00020\u0006*\u00020\u0006H\b\u001a\u0015\u0010?\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u0001H\b\u001a\u0015\u0010?\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0018\u001a\u00020\tH\b\u001a\u0015\u0010?\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0006H\b\u001a\u0015\u0010?\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u0018\u001a\u00020\tH\b\u001a\f\u0010@\u001a\u00020\t*\u00020\u0001H\u0007\u001a\f\u0010@\u001a\u00020\t*\u00020\u0006H\u0007\u001a\f\u0010A\u001a\u00020\f*\u00020\u0001H\u0007\u001a\f\u0010A\u001a\u00020\f*\u00020\u0006H\u0007\u001a\u0015\u0010B\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0001H\b\u001a\u0015\u0010B\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u000f\u001a\u00020\tH\b\u001a\u0015\u0010B\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0006H\b\u001a\u0015\u0010B\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u000f\u001a\u00020\tH\b\"\u001f\u0010\u0000\u001a\u00020\u0001*\u00020\u00018Æ\u0002X\u0004¢\u0006\f\u0012\u0004\b\u0002\u0010\u0003\u001a\u0004\b\u0004\u0010\u0005\"\u001f\u0010\u0000\u001a\u00020\u0006*\u00020\u00068Æ\u0002X\u0004¢\u0006\f\u0012\u0004\b\u0002\u0010\u0007\u001a\u0004\b\u0004\u0010\b\"\u001f\u0010\u0000\u001a\u00020\t*\u00020\t8Æ\u0002X\u0004¢\u0006\f\u0012\u0004\b\u0002\u0010\n\u001a\u0004\b\u0004\u0010\u000b\"\u001f\u0010\u0000\u001a\u00020\f*\u00020\f8Æ\u0002X\u0004¢\u0006\f\u0012\u0004\b\u0002\u0010\r\u001a\u0004\b\u0004\u0010\u000e\"\u001f\u0010\u000f\u001a\u00020\u0001*\u00020\u00018Æ\u0002X\u0004¢\u0006\f\u0012\u0004\b\u0010\u0010\u0003\u001a\u0004\b\u0011\u0010\u0005\"\u001f\u0010\u000f\u001a\u00020\u0006*\u00020\u00068Æ\u0002X\u0004¢\u0006\f\u0012\u0004\b\u0010\u0010\u0007\u001a\u0004\b\u0011\u0010\b\"\u001e\u0010\u000f\u001a\u00020\t*\u00020\t8FX\u0004¢\u0006\f\u0012\u0004\b\u0010\u0010\n\u001a\u0004\b\u0011\u0010\u000b\"\u001e\u0010\u000f\u001a\u00020\t*\u00020\f8FX\u0004¢\u0006\f\u0012\u0004\b\u0010\u0010\r\u001a\u0004\b\u0011\u0010\u0012\"\u001f\u0010\u0013\u001a\u00020\u0001*\u00020\u00018Æ\u0002X\u0004¢\u0006\f\u0012\u0004\b\u0014\u0010\u0003\u001a\u0004\b\u0015\u0010\u0005\"\u001f\u0010\u0013\u001a\u00020\u0006*\u00020\u00068Æ\u0002X\u0004¢\u0006\f\u0012\u0004\b\u0014\u0010\u0007\u001a\u0004\b\u0015\u0010\b¨\u0006C"}, mo22149d2 = {"absoluteValue", "", "absoluteValue$annotations", "(D)V", "getAbsoluteValue", "(D)D", "", "(F)V", "(F)F", "", "(I)V", "(I)I", "", "(J)V", "(J)J", "sign", "sign$annotations", "getSign", "(J)I", "ulp", "ulp$annotations", "getUlp", "abs", "x", "n", "acos", "acosh", "asin", "asinh", "atan", "atan2", "y", "atanh", "ceil", "cos", "cosh", "exp", "expm1", "floor", "hypot", "ln", "ln1p", "log", "base", "log10", "log2", "max", "a", "b", "min", "round", "sin", "sinh", "sqrt", "tan", "tanh", "truncate", "IEEErem", "divisor", "nextDown", "nextTowards", "to", "nextUp", "pow", "roundToInt", "roundToLong", "withSign", "kotlin-stdlib"}, mo22150k = 5, mo22151mv = {1, 1, 15}, mo22153xi = 1, mo22154xs = "kotlin/math/MathKt")
/* compiled from: MathJVM.kt */
class MathKt__MathJVMKt extends MathKt__MathHKt {
    @SinceKotlin(version = "1.2")
    @InlineOnly
    public static /* synthetic */ void absoluteValue$annotations(double d) {
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    public static /* synthetic */ void absoluteValue$annotations(float f) {
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    public static /* synthetic */ void absoluteValue$annotations(int i) {
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    public static /* synthetic */ void absoluteValue$annotations(long j) {
    }

    public static final int getSign(int i) {
        if (i < 0) {
            return -1;
        }
        return i > 0 ? 1 : 0;
    }

    public static final int getSign(long j) {
        if (j < 0) {
            return -1;
        }
        return j > 0 ? 1 : 0;
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    public static /* synthetic */ void sign$annotations(double d) {
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    public static /* synthetic */ void sign$annotations(float f) {
    }

    @SinceKotlin(version = "1.2")
    public static /* synthetic */ void sign$annotations(int i) {
    }

    @SinceKotlin(version = "1.2")
    public static /* synthetic */ void sign$annotations(long j) {
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    public static /* synthetic */ void ulp$annotations(double d) {
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    public static /* synthetic */ void ulp$annotations(float f) {
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double sin(double d) {
        return Math.sin(d);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double cos(double d) {
        return Math.cos(d);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double tan(double d) {
        return Math.tan(d);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double asin(double d) {
        return Math.asin(d);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double acos(double d) {
        return Math.acos(d);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double atan(double d) {
        return Math.atan(d);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double atan2(double d, double d2) {
        return Math.atan2(d, d2);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double sinh(double d) {
        return Math.sinh(d);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double cosh(double d) {
        return Math.cosh(d);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double tanh(double d) {
        return Math.tanh(d);
    }

    @SinceKotlin(version = "1.2")
    public static final double asinh(double d) {
        if (d >= Constants.taylor_n_bound) {
            if (d <= Constants.upper_taylor_n_bound) {
                double d2 = (double) 1;
                Double.isNaN(d2);
                return Math.log(d + Math.sqrt((d * d) + d2));
            } else if (d > Constants.upper_taylor_2_bound) {
                return Math.log(d) + Constants.LN2;
            } else {
                double d3 = (double) 2;
                Double.isNaN(d3);
                double d4 = d * d3;
                double d5 = (double) 1;
                Double.isNaN(d5);
                return Math.log(d4 + (d5 / d4));
            }
        } else if (d <= (-Constants.taylor_n_bound)) {
            return -MathKt.asinh(-d);
        } else {
            if (Math.abs(d) < Constants.taylor_2_bound) {
                return d;
            }
            double d6 = (double) 6;
            Double.isNaN(d6);
            return d - (((d * d) * d) / d6);
        }
    }

    @SinceKotlin(version = "1.2")
    public static final double acosh(double d) {
        double d2 = (double) 1;
        if (d < d2) {
            return DoubleCompanionObject.INSTANCE.getNaN();
        }
        if (d > Constants.upper_taylor_2_bound) {
            return Math.log(d) + Constants.LN2;
        }
        Double.isNaN(d2);
        double d3 = d - d2;
        if (d3 >= Constants.taylor_n_bound) {
            Double.isNaN(d2);
            return Math.log(d + Math.sqrt((d * d) - d2));
        }
        double sqrt = Math.sqrt(d3);
        if (sqrt >= Constants.taylor_2_bound) {
            double d4 = (double) 12;
            Double.isNaN(d4);
            sqrt -= ((sqrt * sqrt) * sqrt) / d4;
        }
        return sqrt * Math.sqrt(2.0d);
    }

    @SinceKotlin(version = "1.2")
    public static final double atanh(double d) {
        if (Math.abs(d) >= Constants.taylor_n_bound) {
            double d2 = (double) 1;
            Double.isNaN(d2);
            Double.isNaN(d2);
            double log = Math.log((d2 + d) / (d2 - d));
            double d3 = (double) 2;
            Double.isNaN(d3);
            return log / d3;
        } else if (Math.abs(d) <= Constants.taylor_2_bound) {
            return d;
        } else {
            double d4 = (double) 3;
            Double.isNaN(d4);
            return d + (((d * d) * d) / d4);
        }
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double hypot(double d, double d2) {
        return Math.hypot(d, d2);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double sqrt(double d) {
        return Math.sqrt(d);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double exp(double d) {
        return Math.exp(d);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double expm1(double d) {
        return Math.expm1(d);
    }

    @SinceKotlin(version = "1.2")
    public static final double log(double d, double d2) {
        if (d2 <= 0.0d || d2 == 1.0d) {
            return DoubleCompanionObject.INSTANCE.getNaN();
        }
        return Math.log(d) / Math.log(d2);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    /* renamed from: ln */
    private static final double m3882ln(double d) {
        return Math.log(d);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double log10(double d) {
        return Math.log10(d);
    }

    @SinceKotlin(version = "1.2")
    public static final double log2(double d) {
        return Math.log(d) / Constants.LN2;
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double ln1p(double d) {
        return Math.log1p(d);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double ceil(double d) {
        return Math.ceil(d);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double floor(double d) {
        return Math.floor(d);
    }

    @SinceKotlin(version = "1.2")
    public static final double truncate(double d) {
        if (Double.isNaN(d) || Double.isInfinite(d)) {
            return d;
        }
        if (d > ((double) 0)) {
            return Math.floor(d);
        }
        return Math.ceil(d);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double round(double d) {
        return Math.rint(d);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double abs(double d) {
        return Math.abs(d);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double sign(double d) {
        return Math.signum(d);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double min(double d, double d2) {
        return Math.min(d, d2);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double max(double d, double d2) {
        return Math.max(d, d2);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double pow(double d, double d2) {
        return Math.pow(d, d2);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double pow(double d, int i) {
        return Math.pow(d, (double) i);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double IEEErem(double d, double d2) {
        return Math.IEEEremainder(d, d2);
    }

    private static final double getAbsoluteValue(double d) {
        return Math.abs(d);
    }

    private static final double getSign(double d) {
        return Math.signum(d);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double withSign(double d, double d2) {
        return Math.copySign(d, d2);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double withSign(double d, int i) {
        return Math.copySign(d, (double) i);
    }

    private static final double getUlp(double d) {
        return Math.ulp(d);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double nextUp(double d) {
        return Math.nextUp(d);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double nextDown(double d) {
        return Math.nextAfter(d, DoubleCompanionObject.INSTANCE.getNEGATIVE_INFINITY());
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final double nextTowards(double d, double d2) {
        return Math.nextAfter(d, d2);
    }

    @SinceKotlin(version = "1.2")
    public static final int roundToInt(double d) {
        if (Double.isNaN(d)) {
            throw new IllegalArgumentException("Cannot round NaN value.");
        } else if (d > ((double) Integer.MAX_VALUE)) {
            return Integer.MAX_VALUE;
        } else {
            if (d < ((double) Integer.MIN_VALUE)) {
                return Integer.MIN_VALUE;
            }
            return (int) Math.round(d);
        }
    }

    @SinceKotlin(version = "1.2")
    public static final long roundToLong(double d) {
        if (!Double.isNaN(d)) {
            return Math.round(d);
        }
        throw new IllegalArgumentException("Cannot round NaN value.");
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float sin(float f) {
        return (float) Math.sin((double) f);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float cos(float f) {
        return (float) Math.cos((double) f);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float tan(float f) {
        return (float) Math.tan((double) f);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float asin(float f) {
        return (float) Math.asin((double) f);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float acos(float f) {
        return (float) Math.acos((double) f);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float atan(float f) {
        return (float) Math.atan((double) f);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float atan2(float f, float f2) {
        return (float) Math.atan2((double) f, (double) f2);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float sinh(float f) {
        return (float) Math.sinh((double) f);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float cosh(float f) {
        return (float) Math.cosh((double) f);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float tanh(float f) {
        return (float) Math.tanh((double) f);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float asinh(float f) {
        return (float) MathKt.asinh((double) f);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float acosh(float f) {
        return (float) MathKt.acosh((double) f);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float atanh(float f) {
        return (float) MathKt.atanh((double) f);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float hypot(float f, float f2) {
        return (float) Math.hypot((double) f, (double) f2);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float sqrt(float f) {
        return (float) Math.sqrt((double) f);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float exp(float f) {
        return (float) Math.exp((double) f);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float expm1(float f) {
        return (float) Math.expm1((double) f);
    }

    @SinceKotlin(version = "1.2")
    public static final float log(float f, float f2) {
        if (f2 <= 0.0f || f2 == 1.0f) {
            return FloatCompanionObject.INSTANCE.getNaN();
        }
        return (float) (Math.log((double) f) / Math.log((double) f2));
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    /* renamed from: ln */
    private static final float m3883ln(float f) {
        return (float) Math.log((double) f);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float log10(float f) {
        return (float) Math.log10((double) f);
    }

    @SinceKotlin(version = "1.2")
    public static final float log2(float f) {
        return (float) (Math.log((double) f) / Constants.LN2);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float ln1p(float f) {
        return (float) Math.log1p((double) f);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float ceil(float f) {
        return (float) Math.ceil((double) f);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float floor(float f) {
        return (float) Math.floor((double) f);
    }

    @SinceKotlin(version = "1.2")
    public static final float truncate(float f) {
        double d;
        if (Float.isNaN(f) || Float.isInfinite(f)) {
            return f;
        }
        if (f > ((float) 0)) {
            d = Math.floor((double) f);
        } else {
            d = Math.ceil((double) f);
        }
        return (float) d;
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float round(float f) {
        return (float) Math.rint((double) f);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float abs(float f) {
        return Math.abs(f);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float sign(float f) {
        return Math.signum(f);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float min(float f, float f2) {
        return Math.min(f, f2);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float max(float f, float f2) {
        return Math.max(f, f2);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float pow(float f, float f2) {
        return (float) Math.pow((double) f, (double) f2);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float pow(float f, int i) {
        return (float) Math.pow((double) f, (double) i);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float IEEErem(float f, float f2) {
        return (float) Math.IEEEremainder((double) f, (double) f2);
    }

    private static final float getAbsoluteValue(float f) {
        return Math.abs(f);
    }

    private static final float getSign(float f) {
        return Math.signum(f);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float withSign(float f, float f2) {
        return Math.copySign(f, f2);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float withSign(float f, int i) {
        return Math.copySign(f, (float) i);
    }

    private static final float getUlp(float f) {
        return Math.ulp(f);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float nextUp(float f) {
        return Math.nextUp(f);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float nextDown(float f) {
        return Math.nextAfter(f, DoubleCompanionObject.INSTANCE.getNEGATIVE_INFINITY());
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final float nextTowards(float f, float f2) {
        return Math.nextAfter(f, (double) f2);
    }

    @SinceKotlin(version = "1.2")
    public static final int roundToInt(float f) {
        if (!Float.isNaN(f)) {
            return Math.round(f);
        }
        throw new IllegalArgumentException("Cannot round NaN value.");
    }

    @SinceKotlin(version = "1.2")
    public static final long roundToLong(float f) {
        return MathKt.roundToLong((double) f);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final int abs(int i) {
        return Math.abs(i);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final int min(int i, int i2) {
        return Math.min(i, i2);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final int max(int i, int i2) {
        return Math.max(i, i2);
    }

    private static final int getAbsoluteValue(int i) {
        return Math.abs(i);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final long abs(long j) {
        return Math.abs(j);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final long min(long j, long j2) {
        return Math.min(j, j2);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final long max(long j, long j2) {
        return Math.max(j, j2);
    }

    private static final long getAbsoluteValue(long j) {
        return Math.abs(j);
    }
}
