package org.eclipse.jetty.servlet.jmx;

import org.eclipse.jetty.jmx.ObjectMBean;
import org.eclipse.jetty.servlet.Holder;

public class HolderMBean extends ObjectMBean {
    public HolderMBean(Object obj) {
        super(obj);
    }

    public String getObjectNameBasis() {
        String name;
        if (this._managed == null || !(this._managed instanceof Holder) || (name = ((Holder) this._managed).getName()) == null) {
            return HolderMBean.super.getObjectNameBasis();
        }
        return name;
    }
}
