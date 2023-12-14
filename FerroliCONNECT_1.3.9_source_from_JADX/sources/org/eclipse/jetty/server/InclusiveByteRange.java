package org.eclipse.jetty.server;

import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;
import org.eclipse.jetty.http.HttpHeaderValues;
import org.eclipse.jetty.util.LazyList;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class InclusiveByteRange {
    private static final Logger LOG = Log.getLogger((Class<?>) InclusiveByteRange.class);
    long first = 0;
    long last = 0;

    public InclusiveByteRange(long j, long j2) {
        this.first = j;
        this.last = j2;
    }

    public long getFirst() {
        return this.first;
    }

    public long getLast() {
        return this.last;
    }

    public static List satisfiableRanges(Enumeration enumeration, long j) {
        long j2;
        long j3;
        Object obj = null;
        while (enumeration.hasMoreElements()) {
            StringTokenizer stringTokenizer = new StringTokenizer((String) enumeration.nextElement(), "=,", false);
            Object obj2 = obj;
            String str = null;
            while (true) {
                try {
                    if (!stringTokenizer.hasMoreTokens()) {
                        break;
                    }
                    try {
                        str = stringTokenizer.nextToken().trim();
                        int indexOf = str.indexOf(45);
                        if (indexOf >= 0) {
                            int i = indexOf + 1;
                            if (str.indexOf("-", i) < 0) {
                                if (indexOf == 0) {
                                    if (i < str.length()) {
                                        j2 = Long.parseLong(str.substring(i).trim());
                                        j3 = -1;
                                    } else {
                                        LOG.warn("Bad range format: {}", str);
                                    }
                                } else if (i < str.length()) {
                                    j3 = Long.parseLong(str.substring(0, indexOf).trim());
                                    j2 = Long.parseLong(str.substring(i).trim());
                                } else {
                                    j3 = Long.parseLong(str.substring(0, indexOf).trim());
                                    j2 = -1;
                                }
                                if (j3 != -1 || j2 != -1) {
                                    if (j3 != -1 && j2 != -1 && j3 > j2) {
                                        break;
                                    } else if (j3 < j) {
                                        obj2 = LazyList.add(obj2, new InclusiveByteRange(j3, j2));
                                    }
                                } else {
                                    break;
                                }
                            }
                        }
                        if (!HttpHeaderValues.BYTES.equals(str)) {
                            LOG.warn("Bad range format: {}", str);
                            break;
                        }
                    } catch (NumberFormatException e) {
                        LOG.warn("Bad range format: {}", str);
                        LOG.ignore(e);
                    }
                } catch (Exception e2) {
                    LOG.warn("Bad range format: {}", str);
                    LOG.ignore(e2);
                }
            }
            obj = obj2;
        }
        return LazyList.getList(obj, true);
    }

    public long getFirst(long j) {
        long j2 = this.first;
        if (j2 >= 0) {
            return j2;
        }
        long j3 = j - this.last;
        if (j3 < 0) {
            return 0;
        }
        return j3;
    }

    public long getLast(long j) {
        if (this.first < 0) {
            return j - 1;
        }
        long j2 = this.last;
        return (j2 < 0 || j2 >= j) ? j - 1 : j2;
    }

    public long getSize(long j) {
        return (getLast(j) - getFirst(j)) + 1;
    }

    public String toHeaderRangeString(long j) {
        StringBuilder sb = new StringBuilder(40);
        sb.append("bytes ");
        sb.append(getFirst(j));
        sb.append('-');
        sb.append(getLast(j));
        sb.append("/");
        sb.append(j);
        return sb.toString();
    }

    public static String to416HeaderRangeString(long j) {
        StringBuilder sb = new StringBuilder(40);
        sb.append("bytes */");
        sb.append(j);
        return sb.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(60);
        sb.append(Long.toString(this.first));
        sb.append(":");
        sb.append(Long.toString(this.last));
        return sb.toString();
    }
}
