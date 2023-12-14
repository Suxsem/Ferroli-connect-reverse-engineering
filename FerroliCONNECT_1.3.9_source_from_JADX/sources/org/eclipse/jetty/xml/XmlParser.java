package org.eclipse.jetty.xml;

import anetwork.channel.util.RequestConstant;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Stack;
import java.util.StringTokenizer;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.eclipse.jetty.util.LazyList;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.Resource;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class XmlParser {
    /* access modifiers changed from: private */
    public static final Logger LOG = Log.getLogger((Class<?>) XmlParser.class);
    /* access modifiers changed from: private */
    public String _dtd;
    /* access modifiers changed from: private */
    public Map<String, ContentHandler> _observerMap;
    /* access modifiers changed from: private */
    public Stack<ContentHandler> _observers = new Stack<>();
    /* access modifiers changed from: private */
    public SAXParser _parser;
    /* access modifiers changed from: private */
    public Map<String, URL> _redirectMap = new HashMap();
    private String _xpath;
    /* access modifiers changed from: private */
    public Object _xpaths;

    public XmlParser() {
        setValidating(Boolean.valueOf(System.getProperty("org.eclipse.jetty.xml.XmlParser.Validating", SAXParserFactory.newInstance().getClass().toString().startsWith("org.apache.xerces.") ? RequestConstant.TRUE : RequestConstant.FALSE)).booleanValue());
    }

    public XmlParser(boolean z) {
        setValidating(z);
    }

    public void setValidating(boolean z) {
        try {
            SAXParserFactory newInstance = SAXParserFactory.newInstance();
            newInstance.setValidating(z);
            this._parser = newInstance.newSAXParser();
            if (z) {
                try {
                    this._parser.getXMLReader().setFeature("http://apache.org/xml/features/validation/schema", z);
                } catch (Exception e) {
                    if (z) {
                        LOG.warn("Schema validation may not be supported: ", (Throwable) e);
                    } else {
                        LOG.ignore(e);
                    }
                }
            }
            this._parser.getXMLReader().setFeature("http://xml.org/sax/features/validation", z);
            this._parser.getXMLReader().setFeature("http://xml.org/sax/features/namespaces", true);
            this._parser.getXMLReader().setFeature("http://xml.org/sax/features/namespace-prefixes", false);
            if (z) {
                try {
                    this._parser.getXMLReader().setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", z);
                } catch (Exception e2) {
                    LOG.warn(e2.getMessage(), new Object[0]);
                }
            }
        } catch (Exception e3) {
            LOG.warn(Log.EXCEPTION, (Throwable) e3);
            throw new Error(e3.toString());
        }
    }

    public synchronized void redirectEntity(String str, URL url) {
        if (url != null) {
            this._redirectMap.put(str, url);
        }
    }

    public String getXpath() {
        return this._xpath;
    }

    public void setXpath(String str) {
        this._xpath = str;
        StringTokenizer stringTokenizer = new StringTokenizer(str, "| ");
        while (stringTokenizer.hasMoreTokens()) {
            this._xpaths = LazyList.add(this._xpaths, stringTokenizer.nextToken());
        }
    }

    public String getDTD() {
        return this._dtd;
    }

    public synchronized void addContentHandler(String str, ContentHandler contentHandler) {
        if (this._observerMap == null) {
            this._observerMap = new HashMap();
        }
        this._observerMap.put(str, contentHandler);
    }

    public synchronized Node parse(InputSource inputSource) throws IOException, SAXException {
        Node node;
        this._dtd = null;
        Handler handler = new Handler();
        XMLReader xMLReader = this._parser.getXMLReader();
        xMLReader.setContentHandler(handler);
        xMLReader.setErrorHandler(handler);
        xMLReader.setEntityResolver(handler);
        if (LOG.isDebugEnabled()) {
            Logger logger = LOG;
            logger.debug("parsing: sid=" + inputSource.getSystemId() + ",pid=" + inputSource.getPublicId(), new Object[0]);
        }
        this._parser.parse(inputSource, handler);
        if (handler._error == null) {
            node = (Node) handler._top.get(0);
            handler.clear();
        } else {
            throw handler._error;
        }
        return node;
    }

    public synchronized Node parse(String str) throws IOException, SAXException {
        if (LOG.isDebugEnabled()) {
            Logger logger = LOG;
            logger.debug("parse: " + str, new Object[0]);
        }
        return parse(new InputSource(str));
    }

    public synchronized Node parse(File file) throws IOException, SAXException {
        if (LOG.isDebugEnabled()) {
            Logger logger = LOG;
            logger.debug("parse: " + file, new Object[0]);
        }
        return parse(new InputSource(Resource.toURL(file).toString()));
    }

    public synchronized Node parse(InputStream inputStream) throws IOException, SAXException {
        Node node;
        this._dtd = null;
        Handler handler = new Handler();
        XMLReader xMLReader = this._parser.getXMLReader();
        xMLReader.setContentHandler(handler);
        xMLReader.setErrorHandler(handler);
        xMLReader.setEntityResolver(handler);
        this._parser.parse(new InputSource(inputStream), handler);
        if (handler._error == null) {
            node = (Node) handler._top.get(0);
            handler.clear();
        } else {
            throw handler._error;
        }
        return node;
    }

    private class NoopHandler extends DefaultHandler {
        int _depth;
        Handler _next;

        NoopHandler(Handler handler) {
            this._next = handler;
        }

        public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
            this._depth++;
        }

        public void endElement(String str, String str2, String str3) throws SAXException {
            int i = this._depth;
            if (i == 0) {
                XmlParser.this._parser.getXMLReader().setContentHandler(this._next);
            } else {
                this._depth = i - 1;
            }
        }
    }

    private class Handler extends DefaultHandler {
        private Node _context = this._top;
        SAXParseException _error;
        private NoopHandler _noop;
        Node _top = new Node((Node) null, (String) null, (Attributes) null);

        Handler() {
            this._noop = new NoopHandler(this);
        }

        /* access modifiers changed from: package-private */
        public void clear() {
            this._top = null;
            this._error = null;
            this._context = null;
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v17, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: org.xml.sax.ContentHandler} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void startElement(java.lang.String r10, java.lang.String r11, java.lang.String r12, org.xml.sax.Attributes r13) throws org.xml.sax.SAXException {
            /*
                r9 = this;
                org.eclipse.jetty.xml.XmlParser r0 = org.eclipse.jetty.xml.XmlParser.this
                javax.xml.parsers.SAXParser r0 = r0._parser
                boolean r0 = r0.isNamespaceAware()
                r1 = 0
                if (r0 == 0) goto L_0x000f
                r0 = r11
                goto L_0x0010
            L_0x000f:
                r0 = r1
            L_0x0010:
                if (r0 == 0) goto L_0x001a
                java.lang.String r2 = ""
                boolean r2 = r2.equals(r0)
                if (r2 == 0) goto L_0x001b
            L_0x001a:
                r0 = r12
            L_0x001b:
                org.eclipse.jetty.xml.XmlParser$Node r2 = new org.eclipse.jetty.xml.XmlParser$Node
                org.eclipse.jetty.xml.XmlParser$Node r3 = r9._context
                r2.<init>(r3, r0, r13)
                org.eclipse.jetty.xml.XmlParser r3 = org.eclipse.jetty.xml.XmlParser.this
                java.lang.Object r3 = r3._xpaths
                r4 = 0
                if (r3 == 0) goto L_0x008f
                java.lang.String r3 = r2.getPath()
                org.eclipse.jetty.xml.XmlParser r5 = org.eclipse.jetty.xml.XmlParser.this
                java.lang.Object r5 = r5._xpaths
                int r5 = org.eclipse.jetty.util.LazyList.size(r5)
                r6 = r5
                r5 = 0
            L_0x003b:
                if (r5 != 0) goto L_0x0075
                int r7 = r6 + -1
                if (r6 <= 0) goto L_0x0075
                org.eclipse.jetty.xml.XmlParser r5 = org.eclipse.jetty.xml.XmlParser.this
                java.lang.Object r5 = r5._xpaths
                java.lang.Object r5 = org.eclipse.jetty.util.LazyList.get(r5, r7)
                java.lang.String r5 = (java.lang.String) r5
                boolean r6 = r3.equals(r5)
                if (r6 != 0) goto L_0x0072
                boolean r6 = r5.startsWith(r3)
                if (r6 == 0) goto L_0x0070
                int r6 = r5.length()
                int r8 = r3.length()
                if (r6 <= r8) goto L_0x0070
                int r6 = r3.length()
                char r5 = r5.charAt(r6)
                r6 = 47
                if (r5 != r6) goto L_0x0070
                goto L_0x0072
            L_0x0070:
                r5 = 0
                goto L_0x0073
            L_0x0072:
                r5 = 1
            L_0x0073:
                r6 = r7
                goto L_0x003b
            L_0x0075:
                if (r5 == 0) goto L_0x007f
                org.eclipse.jetty.xml.XmlParser$Node r3 = r9._context
                r3.add(r2)
                r9._context = r2
                goto L_0x0096
            L_0x007f:
                org.eclipse.jetty.xml.XmlParser r2 = org.eclipse.jetty.xml.XmlParser.this
                javax.xml.parsers.SAXParser r2 = r2._parser
                org.xml.sax.XMLReader r2 = r2.getXMLReader()
                org.eclipse.jetty.xml.XmlParser$NoopHandler r3 = r9._noop
                r2.setContentHandler(r3)
                goto L_0x0096
            L_0x008f:
                org.eclipse.jetty.xml.XmlParser$Node r3 = r9._context
                r3.add(r2)
                r9._context = r2
            L_0x0096:
                org.eclipse.jetty.xml.XmlParser r2 = org.eclipse.jetty.xml.XmlParser.this
                java.util.Map r2 = r2._observerMap
                if (r2 == 0) goto L_0x00ab
                org.eclipse.jetty.xml.XmlParser r1 = org.eclipse.jetty.xml.XmlParser.this
                java.util.Map r1 = r1._observerMap
                java.lang.Object r0 = r1.get(r0)
                r1 = r0
                org.xml.sax.ContentHandler r1 = (org.xml.sax.ContentHandler) r1
            L_0x00ab:
                org.eclipse.jetty.xml.XmlParser r0 = org.eclipse.jetty.xml.XmlParser.this
                java.util.Stack r0 = r0._observers
                r0.push(r1)
            L_0x00b4:
                org.eclipse.jetty.xml.XmlParser r0 = org.eclipse.jetty.xml.XmlParser.this
                java.util.Stack r0 = r0._observers
                int r0 = r0.size()
                if (r4 >= r0) goto L_0x00de
                org.eclipse.jetty.xml.XmlParser r0 = org.eclipse.jetty.xml.XmlParser.this
                java.util.Stack r0 = r0._observers
                java.lang.Object r0 = r0.get(r4)
                if (r0 == 0) goto L_0x00db
                org.eclipse.jetty.xml.XmlParser r0 = org.eclipse.jetty.xml.XmlParser.this
                java.util.Stack r0 = r0._observers
                java.lang.Object r0 = r0.get(r4)
                org.xml.sax.ContentHandler r0 = (org.xml.sax.ContentHandler) r0
                r0.startElement(r10, r11, r12, r13)
            L_0x00db:
                int r4 = r4 + 1
                goto L_0x00b4
            L_0x00de:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.xml.XmlParser.Handler.startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes):void");
        }

        public void endElement(String str, String str2, String str3) throws SAXException {
            this._context = this._context._parent;
            for (int i = 0; i < XmlParser.this._observers.size(); i++) {
                if (XmlParser.this._observers.get(i) != null) {
                    ((ContentHandler) XmlParser.this._observers.get(i)).endElement(str, str2, str3);
                }
            }
            XmlParser.this._observers.pop();
        }

        public void ignorableWhitespace(char[] cArr, int i, int i2) throws SAXException {
            for (int i3 = 0; i3 < XmlParser.this._observers.size(); i3++) {
                if (XmlParser.this._observers.get(i3) != null) {
                    ((ContentHandler) XmlParser.this._observers.get(i3)).ignorableWhitespace(cArr, i, i2);
                }
            }
        }

        public void characters(char[] cArr, int i, int i2) throws SAXException {
            this._context.add(new String(cArr, i, i2));
            for (int i3 = 0; i3 < XmlParser.this._observers.size(); i3++) {
                if (XmlParser.this._observers.get(i3) != null) {
                    ((ContentHandler) XmlParser.this._observers.get(i3)).characters(cArr, i, i2);
                }
            }
        }

        public void warning(SAXParseException sAXParseException) {
            XmlParser.LOG.debug(Log.EXCEPTION, (Throwable) sAXParseException);
            Logger access$400 = XmlParser.LOG;
            access$400.warn("WARNING@" + getLocationString(sAXParseException) + " : " + sAXParseException.toString(), new Object[0]);
        }

        public void error(SAXParseException sAXParseException) throws SAXException {
            if (this._error == null) {
                this._error = sAXParseException;
            }
            XmlParser.LOG.debug(Log.EXCEPTION, (Throwable) sAXParseException);
            Logger access$400 = XmlParser.LOG;
            access$400.warn("ERROR@" + getLocationString(sAXParseException) + " : " + sAXParseException.toString(), new Object[0]);
        }

        public void fatalError(SAXParseException sAXParseException) throws SAXException {
            this._error = sAXParseException;
            XmlParser.LOG.debug(Log.EXCEPTION, (Throwable) sAXParseException);
            Logger access$400 = XmlParser.LOG;
            access$400.warn("FATAL@" + getLocationString(sAXParseException) + " : " + sAXParseException.toString(), new Object[0]);
            throw sAXParseException;
        }

        private String getLocationString(SAXParseException sAXParseException) {
            return sAXParseException.getSystemId() + " line:" + sAXParseException.getLineNumber() + " col:" + sAXParseException.getColumnNumber();
        }

        public InputSource resolveEntity(String str, String str2) {
            if (XmlParser.LOG.isDebugEnabled()) {
                Logger access$400 = XmlParser.LOG;
                access$400.debug("resolveEntity(" + str + ", " + str2 + ")", new Object[0]);
            }
            if (str2 != null && str2.endsWith(".dtd")) {
                String unused = XmlParser.this._dtd = str2;
            }
            URL url = str != null ? (URL) XmlParser.this._redirectMap.get(str) : null;
            if (url == null) {
                url = (URL) XmlParser.this._redirectMap.get(str2);
            }
            if (url == null) {
                String substring = str2.lastIndexOf(47) >= 0 ? str2.substring(str2.lastIndexOf(47) + 1) : str2;
                if (XmlParser.LOG.isDebugEnabled()) {
                    Logger access$4002 = XmlParser.LOG;
                    access$4002.debug("Can't exact match entity in redirect map, trying " + substring, new Object[0]);
                }
                url = (URL) XmlParser.this._redirectMap.get(substring);
            }
            if (url != null) {
                try {
                    InputStream openStream = url.openStream();
                    if (XmlParser.LOG.isDebugEnabled()) {
                        Logger access$4003 = XmlParser.LOG;
                        access$4003.debug("Redirected entity " + str2 + " --> " + url, new Object[0]);
                    }
                    InputSource inputSource = new InputSource(openStream);
                    inputSource.setSystemId(str2);
                    return inputSource;
                } catch (IOException e) {
                    XmlParser.LOG.ignore(e);
                }
            }
            return null;
        }
    }

    public static class Attribute {
        private String _name;
        private String _value;

        Attribute(String str, String str2) {
            this._name = str;
            this._value = str2;
        }

        public String getName() {
            return this._name;
        }

        public String getValue() {
            return this._value;
        }
    }

    public static class Node extends AbstractList<Object> {
        private Attribute[] _attrs;
        private boolean _lastString = false;
        /* access modifiers changed from: private */
        public ArrayList<Object> _list;
        Node _parent;
        private String _path;
        /* access modifiers changed from: private */
        public String _tag;

        Node(Node node, String str, Attributes attributes) {
            this._parent = node;
            this._tag = str;
            if (attributes != null) {
                this._attrs = new Attribute[attributes.getLength()];
                for (int i = 0; i < attributes.getLength(); i++) {
                    String localName = attributes.getLocalName(i);
                    if (localName == null || localName.equals("")) {
                        localName = attributes.getQName(i);
                    }
                    this._attrs[i] = new Attribute(localName, attributes.getValue(i));
                }
            }
        }

        public Node getParent() {
            return this._parent;
        }

        public String getTag() {
            return this._tag;
        }

        public String getPath() {
            if (this._path == null) {
                if (getParent() == null || getParent().getTag() == null) {
                    this._path = "/" + this._tag;
                } else {
                    this._path = getParent().getPath() + "/" + this._tag;
                }
            }
            return this._path;
        }

        public Attribute[] getAttributes() {
            return this._attrs;
        }

        public String getAttribute(String str) {
            return getAttribute(str, (String) null);
        }

        public String getAttribute(String str, String str2) {
            if (this._attrs != null && str != null) {
                int i = 0;
                while (true) {
                    Attribute[] attributeArr = this._attrs;
                    if (i >= attributeArr.length) {
                        break;
                    } else if (str.equals(attributeArr[i].getName())) {
                        return this._attrs[i].getValue();
                    } else {
                        i++;
                    }
                }
            }
            return str2;
        }

        public int size() {
            ArrayList<Object> arrayList = this._list;
            if (arrayList != null) {
                return arrayList.size();
            }
            return 0;
        }

        public Object get(int i) {
            ArrayList<Object> arrayList = this._list;
            if (arrayList != null) {
                return arrayList.get(i);
            }
            return null;
        }

        public Node get(String str) {
            if (this._list == null) {
                return null;
            }
            for (int i = 0; i < this._list.size(); i++) {
                Object obj = this._list.get(i);
                if (obj instanceof Node) {
                    Node node = (Node) obj;
                    if (str.equals(node._tag)) {
                        return node;
                    }
                }
            }
            return null;
        }

        public void add(int i, Object obj) {
            if (this._list == null) {
                this._list = new ArrayList<>();
            }
            if (obj instanceof String) {
                if (this._lastString) {
                    int size = this._list.size() - 1;
                    ArrayList<Object> arrayList = this._list;
                    arrayList.set(size, ((String) this._list.get(size)) + obj);
                } else {
                    this._list.add(i, obj);
                }
                this._lastString = true;
                return;
            }
            this._lastString = false;
            this._list.add(i, obj);
        }

        public void clear() {
            ArrayList<Object> arrayList = this._list;
            if (arrayList != null) {
                arrayList.clear();
            }
            this._list = null;
        }

        public String getString(String str, boolean z, boolean z2) {
            Node node = get(str);
            if (node == null) {
                return null;
            }
            String node2 = node.toString(z);
            return (node2 == null || !z2) ? node2 : node2.trim();
        }

        public synchronized String toString() {
            return toString(true);
        }

        public synchronized String toString(boolean z) {
            StringBuilder sb;
            sb = new StringBuilder();
            toString(sb, z);
            return sb.toString();
        }

        public synchronized String toString(boolean z, boolean z2) {
            String node;
            node = toString(z);
            if (node != null && z2) {
                node = node.trim();
            }
            return node;
        }

        private synchronized void toString(StringBuilder sb, boolean z) {
            if (z) {
                sb.append("<");
                sb.append(this._tag);
                if (this._attrs != null) {
                    for (int i = 0; i < this._attrs.length; i++) {
                        sb.append(' ');
                        sb.append(this._attrs[i].getName());
                        sb.append("=\"");
                        sb.append(this._attrs[i].getValue());
                        sb.append("\"");
                    }
                }
            }
            if (this._list != null) {
                if (z) {
                    sb.append(">");
                }
                for (int i2 = 0; i2 < this._list.size(); i2++) {
                    Object obj = this._list.get(i2);
                    if (obj != null) {
                        if (obj instanceof Node) {
                            ((Node) obj).toString(sb, z);
                        } else {
                            sb.append(obj.toString());
                        }
                    }
                }
                if (z) {
                    sb.append("</");
                    sb.append(this._tag);
                    sb.append(">");
                }
            } else if (z) {
                sb.append("/>");
            }
        }

        public Iterator<Node> iterator(final String str) {
            return new Iterator<Node>() {
                Node _node;

                /* renamed from: c */
                int f4126c = 0;

                public boolean hasNext() {
                    if (this._node != null) {
                        return true;
                    }
                    while (Node.this._list != null && this.f4126c < Node.this._list.size()) {
                        Object obj = Node.this._list.get(this.f4126c);
                        if (obj instanceof Node) {
                            Node node = (Node) obj;
                            if (str.equals(node._tag)) {
                                this._node = node;
                                return true;
                            }
                        }
                        this.f4126c++;
                    }
                    return false;
                }

                public Node next() {
                    try {
                        if (hasNext()) {
                            return this._node;
                        }
                        throw new NoSuchElementException();
                    } finally {
                        this._node = null;
                        this.f4126c++;
                    }
                }

                public void remove() {
                    throw new UnsupportedOperationException("Not supported");
                }
            };
        }
    }
}
