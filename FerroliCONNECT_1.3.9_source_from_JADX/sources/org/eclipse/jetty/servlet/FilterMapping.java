package org.eclipse.jetty.servlet;

import java.io.IOException;
import java.util.EnumSet;
import javax.servlet.DispatcherType;
import org.eclipse.jetty.http.PathMap;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.component.AggregateLifeCycle;
import org.eclipse.jetty.util.component.Dumpable;

public class FilterMapping implements Dumpable {
    public static final int ALL = 31;
    public static final int ASYNC = 16;
    public static final int DEFAULT = 0;
    public static final int ERROR = 8;
    public static final int FORWARD = 2;
    public static final int INCLUDE = 4;
    public static final int REQUEST = 1;
    private int _dispatches = 0;
    private String _filterName;
    private transient FilterHolder _holder;
    private String[] _pathSpecs;
    private String[] _servletNames;

    public static DispatcherType dispatch(String str) {
        if ("request".equalsIgnoreCase(str)) {
            return DispatcherType.REQUEST;
        }
        if ("forward".equalsIgnoreCase(str)) {
            return DispatcherType.FORWARD;
        }
        if ("include".equalsIgnoreCase(str)) {
            return DispatcherType.INCLUDE;
        }
        if ("error".equalsIgnoreCase(str)) {
            return DispatcherType.ERROR;
        }
        if ("async".equalsIgnoreCase(str)) {
            return DispatcherType.ASYNC;
        }
        throw new IllegalArgumentException(str);
    }

    /* renamed from: org.eclipse.jetty.servlet.FilterMapping$1 */
    static /* synthetic */ class C24361 {
        static final /* synthetic */ int[] $SwitchMap$javax$servlet$DispatcherType = new int[DispatcherType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(12:0|1|2|3|4|5|6|7|8|9|10|12) */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            return;
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        /* JADX WARNING: Missing exception handler attribute for start block: B:7:0x002a */
        /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0035 */
        static {
            /*
                javax.servlet.DispatcherType[] r0 = javax.servlet.DispatcherType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$javax$servlet$DispatcherType = r0
                int[] r0 = $SwitchMap$javax$servlet$DispatcherType     // Catch:{ NoSuchFieldError -> 0x0014 }
                javax.servlet.DispatcherType r1 = javax.servlet.DispatcherType.REQUEST     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$javax$servlet$DispatcherType     // Catch:{ NoSuchFieldError -> 0x001f }
                javax.servlet.DispatcherType r1 = javax.servlet.DispatcherType.ASYNC     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$javax$servlet$DispatcherType     // Catch:{ NoSuchFieldError -> 0x002a }
                javax.servlet.DispatcherType r1 = javax.servlet.DispatcherType.FORWARD     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                int[] r0 = $SwitchMap$javax$servlet$DispatcherType     // Catch:{ NoSuchFieldError -> 0x0035 }
                javax.servlet.DispatcherType r1 = javax.servlet.DispatcherType.INCLUDE     // Catch:{ NoSuchFieldError -> 0x0035 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0035 }
                r2 = 4
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0035 }
            L_0x0035:
                int[] r0 = $SwitchMap$javax$servlet$DispatcherType     // Catch:{ NoSuchFieldError -> 0x0040 }
                javax.servlet.DispatcherType r1 = javax.servlet.DispatcherType.ERROR     // Catch:{ NoSuchFieldError -> 0x0040 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0040 }
                r2 = 5
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0040 }
            L_0x0040:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.servlet.FilterMapping.C24361.<clinit>():void");
        }
    }

    public static int dispatch(DispatcherType dispatcherType) {
        int i = C24361.$SwitchMap$javax$servlet$DispatcherType[dispatcherType.ordinal()];
        if (i == 1) {
            return 1;
        }
        int i2 = 2;
        if (i == 2) {
            return 16;
        }
        if (i != 3) {
            i2 = 4;
            if (i != 4) {
                if (i == 5) {
                    return 8;
                }
                throw new IllegalArgumentException(dispatcherType.toString());
            }
        }
        return i2;
    }

    /* access modifiers changed from: package-private */
    public boolean appliesTo(String str, int i) {
        if (appliesTo(i)) {
            int i2 = 0;
            while (true) {
                String[] strArr = this._pathSpecs;
                if (i2 >= strArr.length) {
                    break;
                } else if (strArr[i2] != null && PathMap.match(strArr[i2], str, true)) {
                    return true;
                } else {
                    i2++;
                }
            }
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public boolean appliesTo(int i) {
        int i2 = this._dispatches;
        if (i2 == 0) {
            if (i == 1 || (i == 16 && this._holder.isAsyncSupported())) {
                return true;
            }
            return false;
        } else if ((i & i2) != 0) {
            return true;
        } else {
            return false;
        }
    }

    public String getFilterName() {
        return this._filterName;
    }

    /* access modifiers changed from: package-private */
    public FilterHolder getFilterHolder() {
        return this._holder;
    }

    public String[] getPathSpecs() {
        return this._pathSpecs;
    }

    public void setDispatcherTypes(EnumSet<DispatcherType> enumSet) {
        this._dispatches = 0;
        if (enumSet != null) {
            if (enumSet.contains(DispatcherType.ERROR)) {
                this._dispatches |= 8;
            }
            if (enumSet.contains(DispatcherType.FORWARD)) {
                this._dispatches |= 2;
            }
            if (enumSet.contains(DispatcherType.INCLUDE)) {
                this._dispatches |= 4;
            }
            if (enumSet.contains(DispatcherType.REQUEST)) {
                this._dispatches |= 1;
            }
            if (enumSet.contains(DispatcherType.ASYNC)) {
                this._dispatches |= 16;
            }
        }
    }

    public void setDispatches(int i) {
        this._dispatches = i;
    }

    public void setFilterName(String str) {
        this._filterName = str;
    }

    /* access modifiers changed from: package-private */
    public void setFilterHolder(FilterHolder filterHolder) {
        this._holder = filterHolder;
        setFilterName(filterHolder.getName());
    }

    public void setPathSpecs(String[] strArr) {
        this._pathSpecs = strArr;
    }

    public void setPathSpec(String str) {
        this._pathSpecs = new String[]{str};
    }

    public String[] getServletNames() {
        return this._servletNames;
    }

    public void setServletNames(String[] strArr) {
        this._servletNames = strArr;
    }

    public void setServletName(String str) {
        this._servletNames = new String[]{str};
    }

    public String toString() {
        return TypeUtil.asList(this._pathSpecs) + "/" + TypeUtil.asList(this._servletNames) + "==" + this._dispatches + "=>" + this._filterName;
    }

    public void dump(Appendable appendable, String str) throws IOException {
        appendable.append(String.valueOf(this)).append("\n");
    }

    public String dump() {
        return AggregateLifeCycle.dump((Dumpable) this);
    }
}
