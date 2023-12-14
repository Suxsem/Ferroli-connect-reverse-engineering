package org.eclipse.jetty.webapp;

import com.p107tb.appyunsdk.Constant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.xml.XmlParser;

public class FragmentDescriptor extends WebDescriptor {
    public static final String NAMELESS = "@@-NAMELESS-@@";
    protected static int _counter;
    protected List<String> _afters = new ArrayList();
    protected List<String> _befores = new ArrayList();
    protected String _name;
    protected OtherType _otherType = OtherType.None;

    public enum OtherType {
        None,
        Before,
        After
    }

    public List<String> getOrdering() {
        return null;
    }

    public FragmentDescriptor(Resource resource) throws Exception {
        super(resource);
    }

    public String getName() {
        return this._name;
    }

    public void parse() throws Exception {
        super.parse();
        processName();
    }

    public void processName() {
        String node;
        XmlParser.Node node2 = getRoot().get(Constant.NAME);
        StringBuilder sb = new StringBuilder();
        sb.append(NAMELESS);
        int i = _counter;
        _counter = i + 1;
        sb.append(i);
        this._name = sb.toString();
        if (node2 != null && (node = node2.toString(false, true)) != null && node.length() > 0) {
            this._name = node;
        }
    }

    public void processOrdering() {
        XmlParser.Node node = getRoot().get("ordering");
        if (node != null) {
            this._isOrdered = true;
            processBefores(node);
            processAfters(node);
        }
    }

    public void processBefores(XmlParser.Node node) {
        XmlParser.Node node2 = node.get("before");
        if (node2 != null) {
            Iterator it = node2.iterator();
            while (it.hasNext()) {
                Object next = it.next();
                if (next instanceof XmlParser.Node) {
                    XmlParser.Node node3 = (XmlParser.Node) next;
                    if (node3.getTag().equalsIgnoreCase("others")) {
                        if (this._otherType == OtherType.None) {
                            this._otherType = OtherType.Before;
                        } else {
                            throw new IllegalStateException("Duplicate <other> clause detected in " + this._xml.getURI());
                        }
                    } else if (node3.getTag().equalsIgnoreCase(Constant.NAME)) {
                        this._befores.add(node3.toString(false, true));
                    }
                }
            }
        }
    }

    public void processAfters(XmlParser.Node node) {
        XmlParser.Node node2 = node.get("after");
        if (node2 != null) {
            Iterator it = node2.iterator();
            while (it.hasNext()) {
                Object next = it.next();
                if (next instanceof XmlParser.Node) {
                    XmlParser.Node node3 = (XmlParser.Node) next;
                    if (node3.getTag().equalsIgnoreCase("others")) {
                        if (this._otherType == OtherType.None) {
                            this._otherType = OtherType.After;
                        } else {
                            throw new IllegalStateException("Duplicate <other> clause detected in " + this._xml.getURI());
                        }
                    } else if (node3.getTag().equalsIgnoreCase(Constant.NAME)) {
                        this._afters.add(node3.toString(false, true));
                    }
                }
            }
        }
    }

    public List<String> getBefores() {
        return Collections.unmodifiableList(this._befores);
    }

    public List<String> getAfters() {
        return Collections.unmodifiableList(this._afters);
    }

    public OtherType getOtherType() {
        return this._otherType;
    }
}
