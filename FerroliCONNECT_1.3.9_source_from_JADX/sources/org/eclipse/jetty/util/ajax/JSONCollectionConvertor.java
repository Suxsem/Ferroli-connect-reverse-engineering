package org.eclipse.jetty.util.ajax;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.ajax.JSON;

public class JSONCollectionConvertor implements JSON.Convertor {
    public void toJSON(Object obj, JSON.Output output) {
        output.addClass(obj.getClass());
        output.add("list", (Object) ((Collection) obj).toArray());
    }

    public Object fromJSON(Map map) {
        try {
            Collection collection = (Collection) Loader.loadClass(getClass(), (String) map.get("class")).newInstance();
            Collections.addAll(collection, (Object[]) map.get("list"));
            return collection;
        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                throw ((RuntimeException) e);
            }
            throw new RuntimeException(e);
        }
    }
}
