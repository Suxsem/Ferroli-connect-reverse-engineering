package org.eclipse.jetty.webapp;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.eclipse.jetty.xml.XmlParser;

public abstract class IterativeDescriptorProcessor implements DescriptorProcessor {
    public static final Class<?>[] __signature = {WebAppContext.class, Descriptor.class, XmlParser.Node.class};
    protected Map<String, Method> _visitors = new HashMap();

    public abstract void end(WebAppContext webAppContext, Descriptor descriptor);

    public abstract void start(WebAppContext webAppContext, Descriptor descriptor);

    public void registerVisitor(String str, Method method) {
        this._visitors.put(str, method);
    }

    public void process(WebAppContext webAppContext, Descriptor descriptor) throws Exception {
        if (descriptor != null) {
            start(webAppContext, descriptor);
            Iterator it = descriptor.getRoot().iterator();
            while (it.hasNext()) {
                Object next = it.next();
                if (next instanceof XmlParser.Node) {
                    visit(webAppContext, descriptor, (XmlParser.Node) next);
                }
            }
            end(webAppContext, descriptor);
        }
    }

    /* access modifiers changed from: protected */
    public void visit(WebAppContext webAppContext, Descriptor descriptor, XmlParser.Node node) throws Exception {
        Method method = this._visitors.get(node.getTag());
        if (method != null) {
            method.invoke(this, new Object[]{webAppContext, descriptor, node});
        }
    }
}
