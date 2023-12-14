package org.eclipse.jetty.servlet.jmx;

import org.eclipse.jetty.jmx.ObjectMBean;
import org.eclipse.jetty.servlet.FilterMapping;

public class FilterMappingMBean extends ObjectMBean {
    public FilterMappingMBean(Object obj) {
        super(obj);
    }

    public String getObjectNameBasis() {
        String filterName;
        if (this._managed == null || !(this._managed instanceof FilterMapping) || (filterName = ((FilterMapping) this._managed).getFilterName()) == null) {
            return FilterMappingMBean.super.getObjectNameBasis();
        }
        return filterName;
    }
}
