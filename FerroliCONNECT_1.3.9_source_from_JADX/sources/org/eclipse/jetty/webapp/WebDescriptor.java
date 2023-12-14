package org.eclipse.jetty.webapp;

import com.p107tb.appyunsdk.Constant;
import com.taobao.accs.common.Constants;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.Servlet;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.xml.XmlParser;

public class WebDescriptor extends Descriptor {
    private static final Logger LOG = Log.getLogger((Class<?>) WebDescriptor.class);
    protected static XmlParser _parserSingleton;
    protected ArrayList<String> _classNames = new ArrayList<>();
    protected boolean _distributable;
    protected boolean _isOrdered = false;
    protected int _majorVersion = 3;
    protected MetaDataComplete _metaDataComplete;
    protected int _minorVersion = 0;
    protected List<String> _ordering = new ArrayList();

    public void ensureParser() throws ClassNotFoundException {
        if (_parserSingleton == null) {
            _parserSingleton = newParser();
        }
        this._parser = _parserSingleton;
    }

    public XmlParser newParser() throws ClassNotFoundException {
        URL url;
        URL url2;
        URL url3;
        boolean z;
        boolean z2;
        boolean z3;
        XmlParser xmlParser = new XmlParser();
        URL resource = Loader.getResource(Servlet.class, "javax/servlet/resources/web-app_2_2.dtd", true);
        URL resource2 = Loader.getResource(Servlet.class, "javax/servlet/resources/web-app_2_3.dtd", true);
        URL resource3 = Loader.getResource(Servlet.class, "javax/servlet/resources/j2ee_1_4.xsd", true);
        URL resource4 = Loader.getResource(Servlet.class, "javax/servlet/resources/web-app_2_4.xsd", true);
        URL resource5 = Loader.getResource(Servlet.class, "javax/servlet/resources/web-app_2_5.xsd", true);
        URL resource6 = Loader.getResource(Servlet.class, "javax/servlet/resources/web-app_3_0.xsd", true);
        URL resource7 = Loader.getResource(Servlet.class, "javax/servlet/resources/web-common_3_0.xsd", true);
        URL resource8 = Loader.getResource(Servlet.class, "javax/servlet/resources/web-fragment_3_0.xsd", true);
        URL resource9 = Loader.getResource(Servlet.class, "javax/servlet/resources/XMLSchema.dtd", true);
        URL resource10 = Loader.getResource(Servlet.class, "javax/servlet/resources/xml.xsd", true);
        URL resource11 = Loader.getResource(Servlet.class, "javax/servlet/resources/j2ee_web_services_client_1_1.xsd", true);
        URL resource12 = Loader.getResource(Servlet.class, "javax/servlet/resources/javaee_web_services_client_1_2.xsd", true);
        URL resource13 = Loader.getResource(Servlet.class, "javax/servlet/resources/datatypes.dtd", true);
        URL url4 = null;
        try {
            Class loadClass = Loader.loadClass(WebXmlConfiguration.class, "javax.servlet.jsp.JspPage");
            url2 = loadClass.getResource("/javax/servlet/resources/jsp_2_0.xsd");
            url3 = loadClass.getResource("/javax/servlet/resources/jsp_2_1.xsd");
            if (url2 == null) {
                url = resource13;
                z3 = true;
                url2 = Loader.getResource(Servlet.class, "javax/servlet/resources/jsp_2_0.xsd", true);
            } else {
                url = resource13;
                z3 = true;
            }
            if (url3 == null) {
                url3 = Loader.getResource(Servlet.class, "javax/servlet/resources/jsp_2_1.xsd", z3);
            }
        } catch (Exception e) {
            url = resource13;
            LOG.ignore(e);
            if (0 == 0) {
                z = true;
                url4 = Loader.getResource(Servlet.class, "javax/servlet/resources/jsp_2_0.xsd", true);
            } else {
                z = true;
            }
            url3 = Loader.getResource(Servlet.class, "javax/servlet/resources/jsp_2_1.xsd", z);
        } catch (Throwable th) {
            if (0 == 0) {
                z2 = true;
                Loader.getResource(Servlet.class, "javax/servlet/resources/jsp_2_0.xsd", true);
            } else {
                z2 = true;
            }
            Loader.getResource(Servlet.class, "javax/servlet/resources/jsp_2_1.xsd", z2);
            throw th;
        }
        URL url5 = url3;
        URL url6 = url2;
        redirect(xmlParser, "web-app_2_2.dtd", resource);
        redirect(xmlParser, "-//Sun Microsystems, Inc.//DTD Web Application 2.2//EN", resource);
        redirect(xmlParser, "web.dtd", resource2);
        redirect(xmlParser, "web-app_2_3.dtd", resource2);
        redirect(xmlParser, "-//Sun Microsystems, Inc.//DTD Web Application 2.3//EN", resource2);
        redirect(xmlParser, "XMLSchema.dtd", resource9);
        redirect(xmlParser, "http://www.w3.org/2001/XMLSchema.dtd", resource9);
        redirect(xmlParser, "-//W3C//DTD XMLSCHEMA 200102//EN", resource9);
        redirect(xmlParser, "jsp_2_0.xsd", url6);
        redirect(xmlParser, "http://java.sun.com/xml/ns/j2ee/jsp_2_0.xsd", url6);
        redirect(xmlParser, "http://java.sun.com/xml/ns/javaee/jsp_2_1.xsd", url5);
        redirect(xmlParser, "j2ee_1_4.xsd", resource3);
        redirect(xmlParser, "http://java.sun.com/xml/ns/j2ee/j2ee_1_4.xsd", resource3);
        redirect(xmlParser, "web-app_2_4.xsd", resource4);
        redirect(xmlParser, "http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd", resource4);
        redirect(xmlParser, "web-app_2_5.xsd", resource5);
        redirect(xmlParser, "http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd", resource5);
        redirect(xmlParser, "web-app_3_0.xsd", resource6);
        redirect(xmlParser, "http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd", resource6);
        redirect(xmlParser, "web-common_3_0.xsd", resource7);
        redirect(xmlParser, "http://java.sun.com/xml/ns/javaee/web-common_3_0.xsd", resource7);
        redirect(xmlParser, "web-fragment_3_0.xsd", resource8);
        redirect(xmlParser, "http://java.sun.com/xml/ns/javaee/web-fragment_3_0.xsd", resource8);
        URL url7 = resource10;
        redirect(xmlParser, "xml.xsd", url7);
        redirect(xmlParser, "http://www.w3.org/2001/xml.xsd", url7);
        URL url8 = url;
        redirect(xmlParser, "datatypes.dtd", url8);
        redirect(xmlParser, "http://www.w3.org/2001/datatypes.dtd", url8);
        URL url9 = resource11;
        redirect(xmlParser, "j2ee_web_services_client_1_1.xsd", url9);
        redirect(xmlParser, "http://www.ibm.com/webservices/xsd/j2ee_web_services_client_1_1.xsd", url9);
        URL url10 = resource12;
        redirect(xmlParser, "javaee_web_services_client_1_2.xsd", url10);
        redirect(xmlParser, "http://www.ibm.com/webservices/xsd/javaee_web_services_client_1_2.xsd", url10);
        return xmlParser;
    }

    public WebDescriptor(Resource resource) {
        super(resource);
    }

    public void parse() throws Exception {
        super.parse();
        processVersion();
        processOrdering();
    }

    public MetaDataComplete getMetaDataComplete() {
        return this._metaDataComplete;
    }

    public int getMajorVersion() {
        return this._majorVersion;
    }

    public int getMinorVersion() {
        return this._minorVersion;
    }

    public void processVersion() {
        String attribute = this._root.getAttribute(Constants.SP_KEY_VERSION, "DTD");
        if ("DTD".equals(attribute)) {
            this._majorVersion = 2;
            this._minorVersion = 3;
            String dtd = this._parser.getDTD();
            if (dtd != null && dtd.indexOf("web-app_2_2") >= 0) {
                this._majorVersion = 2;
                this._minorVersion = 2;
            }
        } else {
            int indexOf = attribute.indexOf(".");
            if (indexOf > 0) {
                this._majorVersion = Integer.parseInt(attribute.substring(0, indexOf));
                this._minorVersion = Integer.parseInt(attribute.substring(indexOf + 1));
            }
        }
        if (this._majorVersion >= 2 || this._minorVersion >= 5) {
            String attribute2 = this._root.getAttribute("metadata-complete");
            if (attribute2 == null) {
                this._metaDataComplete = MetaDataComplete.NotSet;
            } else {
                this._metaDataComplete = Boolean.valueOf(attribute2).booleanValue() ? MetaDataComplete.True : MetaDataComplete.False;
            }
        } else {
            this._metaDataComplete = MetaDataComplete.True;
        }
        if (LOG.isDebugEnabled()) {
            Logger logger = LOG;
            logger.debug(this._xml.toString() + ": Calculated metadatacomplete = " + this._metaDataComplete + " with version=" + attribute, new Object[0]);
        }
    }

    public void processOrdering() {
        XmlParser.Node node = this._root.get("absolute-ordering");
        if (node != null) {
            this._isOrdered = true;
            Iterator it = node.iterator();
            while (it.hasNext()) {
                Object next = it.next();
                if (next instanceof XmlParser.Node) {
                    XmlParser.Node node2 = (XmlParser.Node) next;
                    if (node2.getTag().equalsIgnoreCase("others")) {
                        this._ordering.add("others");
                    } else if (node2.getTag().equalsIgnoreCase(Constant.NAME)) {
                        this._ordering.add(node2.toString(false, true));
                    }
                }
            }
        }
    }

    public void addClassName(String str) {
        if (!this._classNames.contains(str)) {
            this._classNames.add(str);
        }
    }

    public ArrayList<String> getClassNames() {
        return this._classNames;
    }

    public void setDistributable(boolean z) {
        this._distributable = z;
    }

    public boolean isDistributable() {
        return this._distributable;
    }

    public void setValidating(boolean z) {
        this._validating = z;
    }

    public boolean isOrdered() {
        return this._isOrdered;
    }

    public List<String> getOrdering() {
        return this._ordering;
    }
}
