package javax.servlet;

import java.util.Map;
import java.util.Set;

public interface Registration {

    public interface Dynamic extends Registration {
        void setAsyncSupported(boolean z);
    }

    String getClassName();

    String getInitParameter(String str);

    Map<String, String> getInitParameters();

    String getName();

    boolean setInitParameter(String str, String str2);

    Set<String> setInitParameters(Map<String, String> map);
}
