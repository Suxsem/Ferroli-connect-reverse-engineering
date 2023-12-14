package org.eclipse.jetty.util.ajax;

import java.lang.reflect.Method;
import java.util.Map;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.ajax.JSON;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class JSONEnumConvertor implements JSON.Convertor {
    private static final Logger LOG = Log.getLogger((Class<?>) JSONEnumConvertor.class);
    private boolean _fromJSON;
    private Method _valueOf;

    public JSONEnumConvertor() {
        this(false);
    }

    public JSONEnumConvertor(boolean z) {
        try {
            this._valueOf = Loader.loadClass(getClass(), "java.lang.Enum").getMethod("valueOf", new Class[]{Class.class, String.class});
            this._fromJSON = z;
        } catch (Exception e) {
            throw new RuntimeException("!Enums", e);
        }
    }

    public Object fromJSON(Map map) {
        if (this._fromJSON) {
            try {
                Class loadClass = Loader.loadClass(getClass(), (String) map.get("class"));
                return this._valueOf.invoke((Object) null, new Object[]{loadClass, map.get("value")});
            } catch (Exception e) {
                LOG.warn(e);
                return null;
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public void toJSON(Object obj, JSON.Output output) {
        if (this._fromJSON) {
            output.addClass(obj.getClass());
            output.add("value", (Object) ((Enum) obj).name());
            return;
        }
        output.add(((Enum) obj).name());
    }
}
