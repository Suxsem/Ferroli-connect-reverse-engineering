package javax.servlet.descriptor;

import java.util.Collection;

public interface JspConfigDescriptor {
    Collection<JspPropertyGroupDescriptor> getJspPropertyGroups();

    Collection<TaglibDescriptor> getTaglibs();
}
