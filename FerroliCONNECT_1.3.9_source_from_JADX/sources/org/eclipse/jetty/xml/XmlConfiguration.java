package org.eclipse.jetty.xml;

import com.p107tb.appyunsdk.Constant;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import org.android.agoo.common.AgooConstants;
import org.eclipse.jetty.util.ArrayQueue;
import org.eclipse.jetty.util.LazyList;
import org.eclipse.jetty.util.Loader;
import org.eclipse.jetty.util.TypeUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.xml.XmlParser;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlConfiguration {
    /* access modifiers changed from: private */
    public static final Logger LOG = Log.getLogger((Class<?>) XmlConfiguration.class);
    private static final Iterable<?> __factoryLoader;
    private static final XmlParser __parser = initParser();
    /* access modifiers changed from: private */
    public static final Class<?>[] __primitiveHolders = {Boolean.class, Character.class, Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class, Void.class};
    /* access modifiers changed from: private */
    public static final Class<?>[] __primitives = {Boolean.TYPE, Character.TYPE, Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Void.TYPE};
    /* access modifiers changed from: private */
    public static final Class<?>[] __supportedCollections = {ArrayList.class, ArrayQueue.class, HashSet.class, Queue.class, List.class, Set.class, Collection.class};
    private String _dtd;
    private final Map<String, Object> _idMap = new HashMap();
    private ConfigurationProcessor _processor;
    private final Map<String, String> _propertyMap = new HashMap();
    private URL _url;

    public void initializeDefaults(Object obj) {
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v3, resolved type: java.lang.Class<?>[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v4, resolved type: java.lang.Class<?>[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    static {
        /*
            java.lang.Class<org.eclipse.jetty.xml.XmlConfiguration> r0 = org.eclipse.jetty.xml.XmlConfiguration.class
            org.eclipse.jetty.util.log.Logger r0 = org.eclipse.jetty.util.log.Log.getLogger((java.lang.Class<?>) r0)
            LOG = r0
            r0 = 9
            java.lang.Class[] r1 = new java.lang.Class[r0]
            java.lang.Class r2 = java.lang.Boolean.TYPE
            r3 = 0
            r1[r3] = r2
            java.lang.Class r2 = java.lang.Character.TYPE
            r4 = 1
            r1[r4] = r2
            java.lang.Class r2 = java.lang.Byte.TYPE
            r5 = 2
            r1[r5] = r2
            java.lang.Class r2 = java.lang.Short.TYPE
            r6 = 3
            r1[r6] = r2
            java.lang.Class r2 = java.lang.Integer.TYPE
            r7 = 4
            r1[r7] = r2
            java.lang.Class r2 = java.lang.Long.TYPE
            r8 = 5
            r1[r8] = r2
            java.lang.Class r2 = java.lang.Float.TYPE
            r9 = 6
            r1[r9] = r2
            java.lang.Class r2 = java.lang.Double.TYPE
            r10 = 7
            r1[r10] = r2
            java.lang.Class r2 = java.lang.Void.TYPE
            r11 = 8
            r1[r11] = r2
            __primitives = r1
            java.lang.Class[] r0 = new java.lang.Class[r0]
            java.lang.Class<java.lang.Boolean> r1 = java.lang.Boolean.class
            r0[r3] = r1
            java.lang.Class<java.lang.Character> r1 = java.lang.Character.class
            r0[r4] = r1
            java.lang.Class<java.lang.Byte> r1 = java.lang.Byte.class
            r0[r5] = r1
            java.lang.Class<java.lang.Short> r1 = java.lang.Short.class
            r0[r6] = r1
            java.lang.Class<java.lang.Integer> r1 = java.lang.Integer.class
            r0[r7] = r1
            java.lang.Class<java.lang.Long> r1 = java.lang.Long.class
            r0[r8] = r1
            java.lang.Class<java.lang.Float> r1 = java.lang.Float.class
            r0[r9] = r1
            java.lang.Class<java.lang.Double> r1 = java.lang.Double.class
            r0[r10] = r1
            java.lang.Class<java.lang.Void> r1 = java.lang.Void.class
            r0[r11] = r1
            __primitiveHolders = r0
            java.lang.Class[] r0 = new java.lang.Class[r10]
            java.lang.Class<java.util.ArrayList> r1 = java.util.ArrayList.class
            r0[r3] = r1
            java.lang.Class<org.eclipse.jetty.util.ArrayQueue> r1 = org.eclipse.jetty.util.ArrayQueue.class
            r0[r4] = r1
            java.lang.Class<java.util.HashSet> r1 = java.util.HashSet.class
            r0[r5] = r1
            java.lang.Class<java.util.Queue> r1 = java.util.Queue.class
            r0[r6] = r1
            java.lang.Class<java.util.List> r1 = java.util.List.class
            r0[r7] = r1
            java.lang.Class<java.util.Set> r1 = java.util.Set.class
            r0[r8] = r1
            java.lang.Class<java.util.Collection> r1 = java.util.Collection.class
            r0[r9] = r1
            __supportedCollections = r0
            org.eclipse.jetty.xml.XmlParser r0 = initParser()
            __parser = r0
            r0 = 0
            java.lang.ClassLoader r1 = java.lang.ClassLoader.getSystemClassLoader()     // Catch:{ Exception -> 0x00b2 }
            java.lang.String r2 = "java.util.ServiceLoader"
            java.lang.Class r1 = r1.loadClass(r2)     // Catch:{ Exception -> 0x00b2 }
            java.lang.String r2 = "load"
            java.lang.Class[] r5 = new java.lang.Class[r4]     // Catch:{ Exception -> 0x00b2 }
            java.lang.Class<java.lang.Class> r6 = java.lang.Class.class
            r5[r3] = r6     // Catch:{ Exception -> 0x00b2 }
            java.lang.reflect.Method r1 = r1.getMethod(r2, r5)     // Catch:{ Exception -> 0x00b2 }
            java.lang.Object[] r2 = new java.lang.Object[r4]     // Catch:{ Exception -> 0x00b2 }
            java.lang.Class<org.eclipse.jetty.xml.ConfigurationProcessorFactory> r4 = org.eclipse.jetty.xml.ConfigurationProcessorFactory.class
            r2[r3] = r4     // Catch:{ Exception -> 0x00b2 }
            java.lang.Object r1 = r1.invoke(r0, r2)     // Catch:{ Exception -> 0x00b2 }
            java.lang.Iterable r1 = (java.lang.Iterable) r1     // Catch:{ Exception -> 0x00b2 }
            __factoryLoader = r1
            goto L_0x00ba
        L_0x00b0:
            r1 = move-exception
            goto L_0x00bb
        L_0x00b2:
            r1 = move-exception
            org.eclipse.jetty.util.log.Logger r2 = LOG     // Catch:{ all -> 0x00b0 }
            r2.ignore(r1)     // Catch:{ all -> 0x00b0 }
            __factoryLoader = r0
        L_0x00ba:
            return
        L_0x00bb:
            __factoryLoader = r0
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.xml.XmlConfiguration.<clinit>():void");
    }

    private static synchronized XmlParser initParser() {
        XmlParser xmlParser;
        synchronized (XmlConfiguration.class) {
            xmlParser = new XmlParser();
            URL resource = Loader.getResource(XmlConfiguration.class, "org/eclipse/jetty/xml/configure_6_0.dtd", true);
            URL resource2 = Loader.getResource(XmlConfiguration.class, "org/eclipse/jetty/xml/configure_7_6.dtd", true);
            xmlParser.redirectEntity("configure.dtd", resource2);
            xmlParser.redirectEntity("configure_1_0.dtd", resource);
            xmlParser.redirectEntity("configure_1_1.dtd", resource);
            xmlParser.redirectEntity("configure_1_2.dtd", resource);
            xmlParser.redirectEntity("configure_1_3.dtd", resource);
            xmlParser.redirectEntity("configure_6_0.dtd", resource);
            xmlParser.redirectEntity("configure_7_6.dtd", resource2);
            xmlParser.redirectEntity("http://jetty.mortbay.org/configure.dtd", resource2);
            xmlParser.redirectEntity("http://jetty.eclipse.org/configure.dtd", resource2);
            xmlParser.redirectEntity("http://www.eclipse.org/jetty/configure.dtd", resource2);
            xmlParser.redirectEntity("-//Mort Bay Consulting//DTD Configure//EN", resource2);
            xmlParser.redirectEntity("-//Jetty//Configure//EN", resource2);
        }
        return xmlParser;
    }

    public XmlConfiguration(URL url) throws SAXException, IOException {
        synchronized (__parser) {
            this._url = url;
            setConfig(__parser.parse(url.toString()));
            this._dtd = __parser.getDTD();
        }
    }

    public XmlConfiguration(String str) throws SAXException, IOException {
        InputSource inputSource = new InputSource(new StringReader("<?xml version=\"1.0\"  encoding=\"ISO-8859-1\"?>\n<!DOCTYPE Configure PUBLIC \"-//Mort Bay Consulting//DTD Configure 1.2//EN\" \"http://jetty.eclipse.org/configure_1_2.dtd\">" + str));
        synchronized (__parser) {
            setConfig(__parser.parse(inputSource));
            this._dtd = __parser.getDTD();
        }
    }

    public XmlConfiguration(InputStream inputStream) throws SAXException, IOException {
        InputSource inputSource = new InputSource(inputStream);
        synchronized (__parser) {
            setConfig(__parser.parse(inputSource));
            this._dtd = __parser.getDTD();
        }
    }

    private void setConfig(XmlParser.Node node) {
        if ("Configure".equals(node.getTag())) {
            this._processor = new JettyXmlConfiguration();
        } else {
            Iterable<?> iterable = __factoryLoader;
            if (iterable != null) {
                for (Object next : iterable) {
                    try {
                        this._processor = (ConfigurationProcessor) next.getClass().getMethod("getConfigurationProcessor", new Class[]{String.class, String.class}).invoke(next, new Object[]{this._dtd, node.getTag()});
                    } catch (Exception e) {
                        LOG.warn(e);
                    }
                    if (this._processor != null) {
                        break;
                    }
                }
                if (this._processor == null) {
                    throw new IllegalStateException("Unknown configuration type: " + node.getTag() + " in " + this);
                }
            } else {
                throw new IllegalArgumentException("Unknown XML tag:" + node.getTag());
            }
        }
        this._processor.init(this._url, node, this);
    }

    public Map<String, Object> getIdMap() {
        return this._idMap;
    }

    @Deprecated
    public void setIdMap(Map<String, Object> map) {
        this._idMap.clear();
        this._idMap.putAll(map);
    }

    @Deprecated
    public void setProperties(Map<String, String> map) {
        this._propertyMap.clear();
        this._propertyMap.putAll(map);
    }

    public Map<String, String> getProperties() {
        return this._propertyMap;
    }

    public Object configure(Object obj) throws Exception {
        return this._processor.configure(obj);
    }

    public Object configure() throws Exception {
        return this._processor.configure();
    }

    private static class JettyXmlConfiguration implements ConfigurationProcessor {
        XmlConfiguration _configuration;
        XmlParser.Node _root;

        private JettyXmlConfiguration() {
        }

        public void init(URL url, XmlParser.Node node, XmlConfiguration xmlConfiguration) {
            this._root = node;
            this._configuration = xmlConfiguration;
        }

        public Object configure(Object obj) throws Exception {
            Class<?> nodeClass = nodeClass(this._root);
            if (nodeClass == null || nodeClass.isInstance(obj)) {
                configure(obj, this._root, 0);
                return obj;
            }
            String str = nodeClass.getClassLoader() == obj.getClass().getClassLoader() ? "" : "Object Class and type Class are from different loaders.";
            throw new IllegalArgumentException("Object of class '" + obj.getClass().getCanonicalName() + "' is not of type '" + nodeClass.getCanonicalName() + "'. " + str);
        }

        public Object configure() throws Exception {
            Object obj;
            Class<?> nodeClass = nodeClass(this._root);
            String attribute = this._root.getAttribute(AgooConstants.MESSAGE_ID);
            if (attribute == null) {
                obj = null;
            } else {
                obj = this._configuration.getIdMap().get(attribute);
            }
            if (obj == null && nodeClass != null) {
                obj = nodeClass.newInstance();
                this._configuration.initializeDefaults(obj);
            }
            if (nodeClass == null || nodeClass.isInstance(obj)) {
                configure(obj, this._root, 0);
                return obj;
            }
            throw new ClassCastException(nodeClass.toString());
        }

        private static Class<?> nodeClass(XmlParser.Node node) throws ClassNotFoundException {
            String attribute = node.getAttribute("class");
            if (attribute == null) {
                return null;
            }
            return Loader.loadClass(XmlConfiguration.class, attribute, true);
        }

        public void configure(Object obj, XmlParser.Node node, int i) throws Exception {
            String attribute = node.getAttribute(AgooConstants.MESSAGE_ID);
            if (attribute != null) {
                this._configuration.getIdMap().put(attribute, obj);
            }
            while (i < node.size()) {
                Object obj2 = node.get(i);
                if (!(obj2 instanceof String)) {
                    XmlParser.Node node2 = (XmlParser.Node) obj2;
                    try {
                        String tag = node2.getTag();
                        if ("Set".equals(tag)) {
                            set(obj, node2);
                        } else if ("Put".equals(tag)) {
                            put(obj, node2);
                        } else if ("Call".equals(tag)) {
                            call(obj, node2);
                        } else if ("Get".equals(tag)) {
                            get(obj, node2);
                        } else if ("New".equals(tag)) {
                            newObj(obj, node2);
                        } else if ("Array".equals(tag)) {
                            newArray(obj, node2);
                        } else if ("Ref".equals(tag)) {
                            refObj(obj, node2);
                        } else if ("Property".equals(tag)) {
                            propertyObj(node2);
                        } else {
                            throw new IllegalStateException("Unknown tag: " + tag);
                        }
                    } catch (Exception e) {
                        Logger access$100 = XmlConfiguration.LOG;
                        access$100.warn("Config error at " + node2, e.toString());
                        throw e;
                    }
                }
                i++;
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:37:0x0102 A[Catch:{ NoSuchFieldException -> 0x0106 }] */
        /* JADX WARNING: Removed duplicated region for block: B:45:0x0119  */
        /* JADX WARNING: Removed duplicated region for block: B:69:0x0186 A[SYNTHETIC, Splitter:B:69:0x0186] */
        /* JADX WARNING: Removed duplicated region for block: B:96:0x01f0  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void set(java.lang.Object r18, org.eclipse.jetty.xml.XmlParser.Node r19) throws java.lang.Exception {
            /*
                r17 = this;
                java.lang.String r0 = "name"
                r1 = r19
                java.lang.String r2 = r1.getAttribute(r0)
                java.lang.StringBuilder r0 = new java.lang.StringBuilder
                r0.<init>()
                java.lang.String r3 = "set"
                r0.append(r3)
                r3 = 1
                r4 = 0
                java.lang.String r5 = r2.substring(r4, r3)
                java.util.Locale r6 = java.util.Locale.ENGLISH
                java.lang.String r5 = r5.toUpperCase(r6)
                r0.append(r5)
                java.lang.String r5 = r2.substring(r3)
                r0.append(r5)
                java.lang.String r5 = r0.toString()
                java.lang.Object r6 = r17.value(r18, r19)
                java.lang.Object[] r7 = new java.lang.Object[r3]
                r7[r4] = r6
                java.lang.Class r0 = nodeClass(r19)
                r1 = 0
                if (r0 == 0) goto L_0x003e
                r9 = r0
                r8 = r1
                goto L_0x0045
            L_0x003e:
                java.lang.Class r0 = r18.getClass()
                r8 = r18
                r9 = r0
            L_0x0045:
                java.lang.Class[] r10 = new java.lang.Class[r3]
                java.lang.Class<java.lang.Object> r0 = java.lang.Object.class
                r10[r4] = r0
                if (r6 == 0) goto L_0x0053
                java.lang.Class r0 = r6.getClass()
                r10[r4] = r0
            L_0x0053:
                org.eclipse.jetty.util.log.Logger r0 = org.eclipse.jetty.xml.XmlConfiguration.LOG
                boolean r0 = r0.isDebugEnabled()
                java.lang.String r11 = ")"
                java.lang.String r12 = "("
                java.lang.String r13 = "."
                if (r0 == 0) goto L_0x0097
                org.eclipse.jetty.util.log.Logger r0 = org.eclipse.jetty.xml.XmlConfiguration.LOG
                java.lang.StringBuilder r14 = new java.lang.StringBuilder
                r14.<init>()
                java.lang.String r15 = "XML "
                r14.append(r15)
                if (r8 == 0) goto L_0x0078
                java.lang.String r15 = r8.toString()
                goto L_0x007c
            L_0x0078:
                java.lang.String r15 = r9.getName()
            L_0x007c:
                r14.append(r15)
                r14.append(r13)
                r14.append(r5)
                r14.append(r12)
                r14.append(r6)
                r14.append(r11)
                java.lang.String r14 = r14.toString()
                java.lang.Object[] r15 = new java.lang.Object[r4]
                r0.debug((java.lang.String) r14, (java.lang.Object[]) r15)
            L_0x0097:
                java.lang.reflect.Method r0 = r9.getMethod(r5, r10)     // Catch:{ IllegalArgumentException -> 0x00b1, IllegalAccessException -> 0x00a8, NoSuchMethodException -> 0x009f }
                r0.invoke(r8, r7)     // Catch:{ IllegalArgumentException -> 0x00b1, IllegalAccessException -> 0x00a8, NoSuchMethodException -> 0x009f }
                return
            L_0x009f:
                r0 = move-exception
                org.eclipse.jetty.util.log.Logger r14 = org.eclipse.jetty.xml.XmlConfiguration.LOG
                r14.ignore(r0)
                goto L_0x00b9
            L_0x00a8:
                r0 = move-exception
                org.eclipse.jetty.util.log.Logger r14 = org.eclipse.jetty.xml.XmlConfiguration.LOG
                r14.ignore(r0)
                goto L_0x00b9
            L_0x00b1:
                r0 = move-exception
                org.eclipse.jetty.util.log.Logger r14 = org.eclipse.jetty.xml.XmlConfiguration.LOG
                r14.ignore(r0)
            L_0x00b9:
                r0 = r10[r4]     // Catch:{ NoSuchFieldException -> 0x00ec, IllegalArgumentException -> 0x00e3, IllegalAccessException -> 0x00da, NoSuchMethodException -> 0x00d1 }
                java.lang.String r14 = "TYPE"
                java.lang.reflect.Field r0 = r0.getField(r14)     // Catch:{ NoSuchFieldException -> 0x00ec, IllegalArgumentException -> 0x00e3, IllegalAccessException -> 0x00da, NoSuchMethodException -> 0x00d1 }
                java.lang.Object r0 = r0.get(r1)     // Catch:{ NoSuchFieldException -> 0x00ec, IllegalArgumentException -> 0x00e3, IllegalAccessException -> 0x00da, NoSuchMethodException -> 0x00d1 }
                java.lang.Class r0 = (java.lang.Class) r0     // Catch:{ NoSuchFieldException -> 0x00ec, IllegalArgumentException -> 0x00e3, IllegalAccessException -> 0x00da, NoSuchMethodException -> 0x00d1 }
                r10[r4] = r0     // Catch:{ NoSuchFieldException -> 0x00ec, IllegalArgumentException -> 0x00e3, IllegalAccessException -> 0x00da, NoSuchMethodException -> 0x00d1 }
                java.lang.reflect.Method r0 = r9.getMethod(r5, r10)     // Catch:{ NoSuchFieldException -> 0x00ec, IllegalArgumentException -> 0x00e3, IllegalAccessException -> 0x00da, NoSuchMethodException -> 0x00d1 }
                r0.invoke(r8, r7)     // Catch:{ NoSuchFieldException -> 0x00ec, IllegalArgumentException -> 0x00e3, IllegalAccessException -> 0x00da, NoSuchMethodException -> 0x00d1 }
                return
            L_0x00d1:
                r0 = move-exception
                org.eclipse.jetty.util.log.Logger r14 = org.eclipse.jetty.xml.XmlConfiguration.LOG
                r14.ignore(r0)
                goto L_0x00f4
            L_0x00da:
                r0 = move-exception
                org.eclipse.jetty.util.log.Logger r14 = org.eclipse.jetty.xml.XmlConfiguration.LOG
                r14.ignore(r0)
                goto L_0x00f4
            L_0x00e3:
                r0 = move-exception
                org.eclipse.jetty.util.log.Logger r14 = org.eclipse.jetty.xml.XmlConfiguration.LOG
                r14.ignore(r0)
                goto L_0x00f4
            L_0x00ec:
                r0 = move-exception
                org.eclipse.jetty.util.log.Logger r14 = org.eclipse.jetty.xml.XmlConfiguration.LOG
                r14.ignore(r0)
            L_0x00f4:
                java.lang.reflect.Field r0 = r9.getField(r2)     // Catch:{ NoSuchFieldException -> 0x0106 }
                int r2 = r0.getModifiers()     // Catch:{ NoSuchFieldException -> 0x0106 }
                boolean r2 = java.lang.reflect.Modifier.isPublic(r2)     // Catch:{ NoSuchFieldException -> 0x0106 }
                if (r2 == 0) goto L_0x010e
                r0.set(r8, r6)     // Catch:{ NoSuchFieldException -> 0x0106 }
                return
            L_0x0106:
                r0 = move-exception
                org.eclipse.jetty.util.log.Logger r2 = org.eclipse.jetty.xml.XmlConfiguration.LOG
                r2.ignore(r0)
            L_0x010e:
                java.lang.reflect.Method[] r2 = r9.getMethods()
                r14 = r1
                r1 = 0
            L_0x0114:
                if (r2 == 0) goto L_0x0184
                int r0 = r2.length
                if (r1 >= r0) goto L_0x0184
                r0 = r2[r1]
                java.lang.Class[] r15 = r0.getParameterTypes()
                r0 = r2[r1]
                java.lang.String r0 = r0.getName()
                boolean r0 = r5.equals(r0)
                if (r0 == 0) goto L_0x0180
                int r0 = r15.length
                if (r0 != r3) goto L_0x0180
                r14 = r2[r1]     // Catch:{ IllegalArgumentException -> 0x013f, IllegalAccessException -> 0x0136 }
                r0 = r2[r1]     // Catch:{ IllegalArgumentException -> 0x013f, IllegalAccessException -> 0x0136 }
                r0.invoke(r8, r7)     // Catch:{ IllegalArgumentException -> 0x013f, IllegalAccessException -> 0x0136 }
                return
            L_0x0136:
                r0 = move-exception
                org.eclipse.jetty.util.log.Logger r3 = org.eclipse.jetty.xml.XmlConfiguration.LOG
                r3.ignore(r0)
                goto L_0x0147
            L_0x013f:
                r0 = move-exception
                org.eclipse.jetty.util.log.Logger r3 = org.eclipse.jetty.xml.XmlConfiguration.LOG
                r3.ignore(r0)
            L_0x0147:
                java.lang.Class[] r0 = org.eclipse.jetty.xml.XmlConfiguration.__supportedCollections     // Catch:{ IllegalAccessException -> 0x0177 }
                int r3 = r0.length     // Catch:{ IllegalAccessException -> 0x0177 }
            L_0x014c:
                if (r4 >= r3) goto L_0x0175
                r18 = r3
                r3 = r0[r4]     // Catch:{ IllegalAccessException -> 0x0177 }
                r19 = r0
                r16 = 0
                r0 = r15[r16]     // Catch:{ IllegalAccessException -> 0x0177 }
                boolean r0 = r0.isAssignableFrom(r3)     // Catch:{ IllegalAccessException -> 0x0177 }
                if (r0 == 0) goto L_0x016d
                r0 = r2[r1]     // Catch:{ IllegalAccessException -> 0x0177 }
                r4 = 1
                java.lang.Object[] r15 = new java.lang.Object[r4]     // Catch:{ IllegalAccessException -> 0x0177 }
                java.util.Collection r3 = convertArrayToCollection(r6, r3)     // Catch:{ IllegalAccessException -> 0x0177 }
                r15[r16] = r3     // Catch:{ IllegalAccessException -> 0x0177 }
                r0.invoke(r8, r15)     // Catch:{ IllegalAccessException -> 0x0177 }
                return
            L_0x016d:
                r3 = 1
                int r4 = r4 + 1
                r3 = r18
                r0 = r19
                goto L_0x014c
            L_0x0175:
                r3 = 1
                goto L_0x0180
            L_0x0177:
                r0 = move-exception
                r3 = 1
                org.eclipse.jetty.util.log.Logger r4 = org.eclipse.jetty.xml.XmlConfiguration.LOG
                r4.ignore(r0)
            L_0x0180:
                int r1 = r1 + 1
                r4 = 0
                goto L_0x0114
            L_0x0184:
                if (r14 == 0) goto L_0x01f0
                java.lang.Class[] r0 = r14.getParameterTypes()     // Catch:{ NoSuchMethodException -> 0x01e5, IllegalAccessException -> 0x01da, InstantiationException -> 0x01cf }
                r1 = 0
                r0 = r0[r1]     // Catch:{ NoSuchMethodException -> 0x01e5, IllegalAccessException -> 0x01da, InstantiationException -> 0x01cf }
                boolean r1 = r0.isPrimitive()     // Catch:{ NoSuchMethodException -> 0x01e5, IllegalAccessException -> 0x01da, InstantiationException -> 0x01cf }
                if (r1 == 0) goto L_0x01b1
                r1 = 0
            L_0x0194:
                java.lang.Class[] r2 = org.eclipse.jetty.xml.XmlConfiguration.__primitives     // Catch:{ NoSuchMethodException -> 0x01e5, IllegalAccessException -> 0x01da, InstantiationException -> 0x01cf }
                int r2 = r2.length     // Catch:{ NoSuchMethodException -> 0x01e5, IllegalAccessException -> 0x01da, InstantiationException -> 0x01cf }
                if (r1 >= r2) goto L_0x01b1
                java.lang.Class[] r2 = org.eclipse.jetty.xml.XmlConfiguration.__primitives     // Catch:{ NoSuchMethodException -> 0x01e5, IllegalAccessException -> 0x01da, InstantiationException -> 0x01cf }
                r2 = r2[r1]     // Catch:{ NoSuchMethodException -> 0x01e5, IllegalAccessException -> 0x01da, InstantiationException -> 0x01cf }
                boolean r2 = r0.equals(r2)     // Catch:{ NoSuchMethodException -> 0x01e5, IllegalAccessException -> 0x01da, InstantiationException -> 0x01cf }
                if (r2 == 0) goto L_0x01ae
                java.lang.Class[] r0 = org.eclipse.jetty.xml.XmlConfiguration.__primitiveHolders     // Catch:{ NoSuchMethodException -> 0x01e5, IllegalAccessException -> 0x01da, InstantiationException -> 0x01cf }
                r0 = r0[r1]     // Catch:{ NoSuchMethodException -> 0x01e5, IllegalAccessException -> 0x01da, InstantiationException -> 0x01cf }
                goto L_0x01b1
            L_0x01ae:
                int r1 = r1 + 1
                goto L_0x0194
            L_0x01b1:
                java.lang.reflect.Constructor r0 = r0.getConstructor(r10)     // Catch:{ NoSuchMethodException -> 0x01e5, IllegalAccessException -> 0x01da, InstantiationException -> 0x01cf }
                java.lang.Object r0 = r0.newInstance(r7)     // Catch:{ NoSuchMethodException -> 0x01e5, IllegalAccessException -> 0x01da, InstantiationException -> 0x01cf }
                r1 = 0
                r7[r1] = r0     // Catch:{ NoSuchMethodException -> 0x01e5, IllegalAccessException -> 0x01da, InstantiationException -> 0x01cf }
                r2 = r17
                org.eclipse.jetty.xml.XmlConfiguration r0 = r2._configuration     // Catch:{ NoSuchMethodException -> 0x01cd, IllegalAccessException -> 0x01cb, InstantiationException -> 0x01c9 }
                r3 = r7[r1]     // Catch:{ NoSuchMethodException -> 0x01cd, IllegalAccessException -> 0x01cb, InstantiationException -> 0x01c9 }
                r0.initializeDefaults(r3)     // Catch:{ NoSuchMethodException -> 0x01cd, IllegalAccessException -> 0x01cb, InstantiationException -> 0x01c9 }
                r14.invoke(r8, r7)     // Catch:{ NoSuchMethodException -> 0x01cd, IllegalAccessException -> 0x01cb, InstantiationException -> 0x01c9 }
                return
            L_0x01c9:
                r0 = move-exception
                goto L_0x01d2
            L_0x01cb:
                r0 = move-exception
                goto L_0x01dd
            L_0x01cd:
                r0 = move-exception
                goto L_0x01e8
            L_0x01cf:
                r0 = move-exception
                r2 = r17
            L_0x01d2:
                org.eclipse.jetty.util.log.Logger r1 = org.eclipse.jetty.xml.XmlConfiguration.LOG
                r1.ignore(r0)
                goto L_0x01f2
            L_0x01da:
                r0 = move-exception
                r2 = r17
            L_0x01dd:
                org.eclipse.jetty.util.log.Logger r1 = org.eclipse.jetty.xml.XmlConfiguration.LOG
                r1.ignore(r0)
                goto L_0x01f2
            L_0x01e5:
                r0 = move-exception
                r2 = r17
            L_0x01e8:
                org.eclipse.jetty.util.log.Logger r1 = org.eclipse.jetty.xml.XmlConfiguration.LOG
                r1.ignore(r0)
                goto L_0x01f2
            L_0x01f0:
                r2 = r17
            L_0x01f2:
                java.lang.NoSuchMethodException r0 = new java.lang.NoSuchMethodException
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                r1.append(r9)
                r1.append(r13)
                r1.append(r5)
                r1.append(r12)
                r3 = 0
                r3 = r10[r3]
                r1.append(r3)
                r1.append(r11)
                java.lang.String r1 = r1.toString()
                r0.<init>(r1)
                goto L_0x0217
            L_0x0216:
                throw r0
            L_0x0217:
                goto L_0x0216
            */
            throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.xml.XmlConfiguration.JettyXmlConfiguration.set(java.lang.Object, org.eclipse.jetty.xml.XmlParser$Node):void");
        }

        /* JADX WARNING: Removed duplicated region for block: B:13:0x0041 A[RETURN] */
        /* JADX WARNING: Removed duplicated region for block: B:14:0x0042  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private static java.util.Collection<?> convertArrayToCollection(java.lang.Object r3, java.lang.Class<?> r4) {
            /*
                java.lang.Class r0 = r3.getClass()
                boolean r0 = r0.isArray()
                if (r0 == 0) goto L_0x003e
                java.lang.Class<java.util.ArrayList> r0 = java.util.ArrayList.class
                boolean r0 = r4.isAssignableFrom(r0)
                if (r0 == 0) goto L_0x0017
                java.util.ArrayList r0 = convertArrayToArrayList(r3)
                goto L_0x003f
            L_0x0017:
                java.lang.Class<java.util.HashSet> r0 = java.util.HashSet.class
                boolean r0 = r4.isAssignableFrom(r0)
                if (r0 == 0) goto L_0x0029
                java.util.HashSet r0 = new java.util.HashSet
                java.util.ArrayList r1 = convertArrayToArrayList(r3)
                r0.<init>(r1)
                goto L_0x003f
            L_0x0029:
                java.lang.Class<org.eclipse.jetty.util.ArrayQueue> r0 = org.eclipse.jetty.util.ArrayQueue.class
                boolean r0 = r4.isAssignableFrom(r0)
                if (r0 == 0) goto L_0x003e
                org.eclipse.jetty.util.ArrayQueue r0 = new org.eclipse.jetty.util.ArrayQueue
                r0.<init>()
                java.util.ArrayList r1 = convertArrayToArrayList(r3)
                r0.addAll(r1)
                goto L_0x003f
            L_0x003e:
                r0 = 0
            L_0x003f:
                if (r0 == 0) goto L_0x0042
                return r0
            L_0x0042:
                java.lang.IllegalArgumentException r0 = new java.lang.IllegalArgumentException
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.lang.String r2 = "Can't convert \""
                r1.append(r2)
                java.lang.Class r3 = r3.getClass()
                r1.append(r3)
                java.lang.String r3 = "\" to "
                r1.append(r3)
                r1.append(r4)
                java.lang.String r3 = r1.toString()
                r0.<init>(r3)
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.xml.XmlConfiguration.JettyXmlConfiguration.convertArrayToCollection(java.lang.Object, java.lang.Class):java.util.Collection");
        }

        private static ArrayList<Object> convertArrayToArrayList(Object obj) {
            int length = Array.getLength(obj);
            ArrayList<Object> arrayList = new ArrayList<>(length);
            for (int i = 0; i < length; i++) {
                arrayList.add(Array.get(obj, i));
            }
            return arrayList;
        }

        private void put(Object obj, XmlParser.Node node) throws Exception {
            if (obj instanceof Map) {
                String attribute = node.getAttribute(Constant.NAME);
                Object value = value(obj, node);
                ((Map) obj).put(attribute, value);
                if (XmlConfiguration.LOG.isDebugEnabled()) {
                    Logger access$100 = XmlConfiguration.LOG;
                    access$100.debug("XML " + obj + ".put(" + attribute + "," + value + ")", new Object[0]);
                    return;
                }
                return;
            }
            throw new IllegalArgumentException("Object for put is not a Map: " + obj);
        }

        private Object get(Object obj, XmlParser.Node node) throws Exception {
            Class<?> nodeClass = nodeClass(node);
            if (nodeClass != null) {
                obj = null;
            } else {
                nodeClass = obj.getClass();
            }
            String attribute = node.getAttribute(Constant.NAME);
            String attribute2 = node.getAttribute(AgooConstants.MESSAGE_ID);
            if (XmlConfiguration.LOG.isDebugEnabled()) {
                XmlConfiguration.LOG.debug("XML get " + attribute, new Object[0]);
            }
            try {
                obj = nodeClass.getMethod("get" + attribute.substring(0, 1).toUpperCase(Locale.ENGLISH) + attribute.substring(1), (Class[]) null).invoke(obj, (Object[]) null);
                configure(obj, node, 0);
            } catch (NoSuchMethodException e) {
                try {
                    obj = nodeClass.getField(attribute).get(obj);
                    configure(obj, node, 0);
                } catch (NoSuchFieldException unused) {
                    throw e;
                }
            }
            if (attribute2 != null) {
                this._configuration.getIdMap().put(attribute2, obj);
            }
            return obj;
        }

        private Object call(Object obj, XmlParser.Node node) throws Exception {
            String attribute = node.getAttribute(AgooConstants.MESSAGE_ID);
            Class<?> nodeClass = nodeClass(node);
            if (nodeClass != null) {
                obj = null;
            } else if (obj != null) {
                nodeClass = obj.getClass();
            }
            if (nodeClass != null) {
                int size = node.size();
                int i = 0;
                int i2 = 0;
                while (true) {
                    if (i >= node.size()) {
                        break;
                    }
                    Object obj2 = node.get(i);
                    if (!(obj2 instanceof String)) {
                        if (!((XmlParser.Node) obj2).getTag().equals("Arg")) {
                            size = i;
                            break;
                        }
                        i2++;
                    }
                    i++;
                }
                Object[] objArr = new Object[i2];
                int i3 = 0;
                int i4 = 0;
                while (i3 < i2) {
                    Object obj3 = node.get(i4);
                    if (!(obj3 instanceof String)) {
                        objArr[i3] = value(obj, (XmlParser.Node) obj3);
                        i3++;
                    }
                    i4++;
                }
                String attribute2 = node.getAttribute(Constant.NAME);
                if (XmlConfiguration.LOG.isDebugEnabled()) {
                    Logger access$100 = XmlConfiguration.LOG;
                    access$100.debug("XML call " + attribute2, new Object[0]);
                }
                try {
                    Object call = TypeUtil.call(nodeClass, attribute2, obj, objArr);
                    if (attribute != null) {
                        this._configuration.getIdMap().put(attribute, call);
                    }
                    configure(call, node, size);
                    return call;
                } catch (NoSuchMethodException e) {
                    IllegalStateException illegalStateException = new IllegalStateException("No Method: " + node + " on " + nodeClass);
                    illegalStateException.initCause(e);
                    throw illegalStateException;
                }
            } else {
                throw new IllegalArgumentException(node.toString());
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:51:0x00b5 A[SYNTHETIC] */
        /* JADX WARNING: Removed duplicated region for block: B:54:0x00c4 A[SYNTHETIC] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private java.lang.Object newObj(java.lang.Object r11, org.eclipse.jetty.xml.XmlParser.Node r12) throws java.lang.Exception {
            /*
                r10 = this;
                java.lang.Class r0 = nodeClass(r12)
                java.lang.String r1 = "id"
                java.lang.String r1 = r12.getAttribute(r1)
                int r2 = r12.size()
                r3 = 0
                r4 = 0
                r5 = 0
            L_0x0011:
                int r6 = r12.size()
                if (r4 >= r6) goto L_0x0035
                java.lang.Object r6 = r12.get((int) r4)
                boolean r7 = r6 instanceof java.lang.String
                if (r7 == 0) goto L_0x0020
                goto L_0x0032
            L_0x0020:
                org.eclipse.jetty.xml.XmlParser$Node r6 = (org.eclipse.jetty.xml.XmlParser.Node) r6
                java.lang.String r6 = r6.getTag()
                java.lang.String r7 = "Arg"
                boolean r6 = r6.equals(r7)
                if (r6 != 0) goto L_0x0030
                r2 = r4
                goto L_0x0035
            L_0x0030:
                int r5 = r5 + 1
            L_0x0032:
                int r4 = r4 + 1
                goto L_0x0011
            L_0x0035:
                java.lang.Object[] r4 = new java.lang.Object[r5]
                r6 = 0
                r7 = 0
            L_0x0039:
                if (r6 >= r5) goto L_0x0052
                java.lang.Object r8 = r12.get((int) r7)
                boolean r9 = r8 instanceof java.lang.String
                if (r9 == 0) goto L_0x0044
                goto L_0x004f
            L_0x0044:
                int r9 = r6 + 1
                org.eclipse.jetty.xml.XmlParser$Node r8 = (org.eclipse.jetty.xml.XmlParser.Node) r8
                java.lang.Object r8 = r10.value(r11, r8)
                r4[r6] = r8
                r6 = r9
            L_0x004f:
                int r7 = r7 + 1
                goto L_0x0039
            L_0x0052:
                org.eclipse.jetty.util.log.Logger r6 = org.eclipse.jetty.xml.XmlConfiguration.LOG
                boolean r6 = r6.isDebugEnabled()
                if (r6 == 0) goto L_0x0076
                org.eclipse.jetty.util.log.Logger r6 = org.eclipse.jetty.xml.XmlConfiguration.LOG
                java.lang.StringBuilder r7 = new java.lang.StringBuilder
                r7.<init>()
                java.lang.String r8 = "XML new "
                r7.append(r8)
                r7.append(r0)
                java.lang.String r7 = r7.toString()
                java.lang.Object[] r8 = new java.lang.Object[r3]
                r6.debug((java.lang.String) r7, (java.lang.Object[]) r8)
            L_0x0076:
                java.lang.reflect.Constructor[] r0 = r0.getConstructors()
                r6 = 0
            L_0x007b:
                if (r0 == 0) goto L_0x00c7
                int r7 = r0.length
                if (r6 >= r7) goto L_0x00c7
                r7 = r0[r6]
                java.lang.Class[] r7 = r7.getParameterTypes()
                int r7 = r7.length
                if (r7 == r5) goto L_0x008a
                goto L_0x00c4
            L_0x008a:
                r7 = 0
                r8 = r0[r6]     // Catch:{ IllegalAccessException -> 0x00aa, InstantiationException -> 0x00a1, IllegalArgumentException -> 0x0098 }
                java.lang.Object r7 = r8.newInstance(r4)     // Catch:{ IllegalAccessException -> 0x00aa, InstantiationException -> 0x00a1, IllegalArgumentException -> 0x0098 }
                org.eclipse.jetty.xml.XmlConfiguration r8 = r10._configuration     // Catch:{ IllegalAccessException -> 0x00aa, InstantiationException -> 0x00a1, IllegalArgumentException -> 0x0098 }
                r8.initializeDefaults(r7)     // Catch:{ IllegalAccessException -> 0x00aa, InstantiationException -> 0x00a1, IllegalArgumentException -> 0x0098 }
                r8 = 1
                goto L_0x00b3
            L_0x0098:
                r8 = move-exception
                org.eclipse.jetty.util.log.Logger r9 = org.eclipse.jetty.xml.XmlConfiguration.LOG
                r9.ignore(r8)
                goto L_0x00b2
            L_0x00a1:
                r8 = move-exception
                org.eclipse.jetty.util.log.Logger r9 = org.eclipse.jetty.xml.XmlConfiguration.LOG
                r9.ignore(r8)
                goto L_0x00b2
            L_0x00aa:
                r8 = move-exception
                org.eclipse.jetty.util.log.Logger r9 = org.eclipse.jetty.xml.XmlConfiguration.LOG
                r9.ignore(r8)
            L_0x00b2:
                r8 = 0
            L_0x00b3:
                if (r8 == 0) goto L_0x00c4
                if (r1 == 0) goto L_0x00c0
                org.eclipse.jetty.xml.XmlConfiguration r11 = r10._configuration
                java.util.Map r11 = r11.getIdMap()
                r11.put(r1, r7)
            L_0x00c0:
                r10.configure(r7, r12, r2)
                return r7
            L_0x00c4:
                int r6 = r6 + 1
                goto L_0x007b
            L_0x00c7:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.lang.String r2 = "No Constructor: "
                r1.append(r2)
                r1.append(r12)
                java.lang.String r12 = " on "
                r1.append(r12)
                r1.append(r11)
                java.lang.String r11 = r1.toString()
                r0.<init>(r11)
                goto L_0x00e7
            L_0x00e6:
                throw r0
            L_0x00e7:
                goto L_0x00e6
            */
            throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.xml.XmlConfiguration.JettyXmlConfiguration.newObj(java.lang.Object, org.eclipse.jetty.xml.XmlParser$Node):java.lang.Object");
        }

        private Object refObj(Object obj, XmlParser.Node node) throws Exception {
            String attribute = node.getAttribute(AgooConstants.MESSAGE_ID);
            Object obj2 = this._configuration.getIdMap().get(attribute);
            if (obj2 != null) {
                configure(obj2, node, 0);
                return obj2;
            }
            throw new IllegalStateException("No object for id=" + attribute);
        }

        private Object newArray(Object obj, XmlParser.Node node) throws Exception {
            Class cls = Object.class;
            String attribute = node.getAttribute("type");
            String attribute2 = node.getAttribute(AgooConstants.MESSAGE_ID);
            if (attribute != null && (cls = TypeUtil.fromName(attribute)) == null) {
                if ("String".equals(attribute)) {
                    cls = String.class;
                } else if ("URL".equals(attribute)) {
                    cls = URL.class;
                } else if ("InetAddress".equals(attribute)) {
                    cls = InetAddress.class;
                } else {
                    cls = Loader.loadClass(XmlConfiguration.class, attribute, true);
                }
            }
            Object obj2 = null;
            Iterator it = node.iterator();
            while (it.hasNext()) {
                XmlParser.Node node2 = (XmlParser.Node) it.next();
                String attribute3 = node2.getAttribute(AgooConstants.MESSAGE_ID);
                Object value = value(obj, node2);
                obj2 = LazyList.add(obj2, (value != null || !cls.isPrimitive()) ? value : 0);
                if (attribute3 != null) {
                    this._configuration.getIdMap().put(attribute3, value);
                }
            }
            Object array = LazyList.toArray(obj2, cls);
            if (attribute2 != null) {
                this._configuration.getIdMap().put(attribute2, array);
            }
            return array;
        }

        private Object newMap(Object obj, XmlParser.Node node) throws Exception {
            String attribute = node.getAttribute(AgooConstants.MESSAGE_ID);
            HashMap hashMap = new HashMap();
            if (attribute != null) {
                this._configuration.getIdMap().put(attribute, hashMap);
            }
            Iterator it = node.iterator();
            while (it.hasNext()) {
                Object next = it.next();
                if (!(next instanceof String)) {
                    XmlParser.Node node2 = (XmlParser.Node) next;
                    if (node2.getTag().equals("Entry")) {
                        Iterator it2 = node2.iterator();
                        XmlParser.Node node3 = null;
                        XmlParser.Node node4 = null;
                        while (it2.hasNext()) {
                            Object next2 = it2.next();
                            if (!(next2 instanceof String)) {
                                XmlParser.Node node5 = (XmlParser.Node) next2;
                                if (!node5.getTag().equals("Item")) {
                                    throw new IllegalStateException("Not an Item");
                                } else if (node3 == null) {
                                    node3 = node5;
                                } else {
                                    node4 = node5;
                                }
                            }
                        }
                        if (node3 == null || node4 == null) {
                            throw new IllegalStateException("Missing Item in Entry");
                        }
                        String attribute2 = node3.getAttribute(AgooConstants.MESSAGE_ID);
                        String attribute3 = node4.getAttribute(AgooConstants.MESSAGE_ID);
                        Object value = value(obj, node3);
                        Object value2 = value(obj, node4);
                        hashMap.put(value, value2);
                        if (attribute2 != null) {
                            this._configuration.getIdMap().put(attribute2, value);
                        }
                        if (attribute3 != null) {
                            this._configuration.getIdMap().put(attribute3, value2);
                        }
                    } else {
                        throw new IllegalStateException("Not an Entry");
                    }
                }
            }
            return hashMap;
        }

        private Object propertyObj(XmlParser.Node node) throws Exception {
            String attribute = node.getAttribute(AgooConstants.MESSAGE_ID);
            String attribute2 = node.getAttribute(Constant.NAME);
            String attribute3 = node.getAttribute("default");
            Map<String, String> properties = this._configuration.getProperties();
            if (properties != null && properties.containsKey(attribute2)) {
                attribute3 = properties.get(attribute2);
            }
            if (attribute != null) {
                this._configuration.getIdMap().put(attribute, attribute3);
            }
            if (attribute3 != null) {
                configure(attribute3, node, 0);
            }
            return attribute3;
        }

        private Object value(Object obj, XmlParser.Node node) throws Exception {
            Object obj2;
            int i;
            String attribute = node.getAttribute("type");
            String attribute2 = node.getAttribute("ref");
            if (attribute2 != null) {
                obj2 = this._configuration.getIdMap().get(attribute2);
            } else if (node.size() != 0) {
                int size = node.size() - 1;
                if (attribute == null || !"String".equals(attribute)) {
                    i = 0;
                    while (i <= size) {
                        Object obj3 = node.get(i);
                        if (!(obj3 instanceof String) || ((String) obj3).trim().length() > 0) {
                            break;
                        }
                        i++;
                    }
                    while (i < size) {
                        Object obj4 = node.get(size);
                        if (!(obj4 instanceof String) || ((String) obj4).trim().length() > 0) {
                            break;
                        }
                        size--;
                    }
                    if (i > size) {
                        return null;
                    }
                } else {
                    i = 0;
                }
                if (i == size) {
                    obj2 = itemValue(obj, node.get(i));
                } else {
                    StringBuilder sb = new StringBuilder();
                    while (i <= size) {
                        sb.append(itemValue(obj, node.get(i)));
                        i++;
                    }
                    obj2 = sb.toString();
                }
            } else if ("String".equals(attribute)) {
                return "";
            } else {
                return null;
            }
            if (obj2 == null) {
                if ("String".equals(attribute)) {
                    return "";
                }
                return null;
            } else if (attribute == null) {
                return obj2 instanceof String ? ((String) obj2).trim() : obj2;
            } else {
                if (isTypeMatchingClass(attribute, String.class)) {
                    return obj2.toString();
                }
                Class<?> fromName = TypeUtil.fromName(attribute);
                if (fromName != null) {
                    return TypeUtil.valueOf(fromName, obj2.toString());
                }
                if (isTypeMatchingClass(attribute, URL.class)) {
                    if (obj2 instanceof URL) {
                        return obj2;
                    }
                    try {
                        return new URL(obj2.toString());
                    } catch (MalformedURLException e) {
                        throw new InvocationTargetException(e);
                    }
                } else if (!isTypeMatchingClass(attribute, InetAddress.class)) {
                    for (Class cls : XmlConfiguration.__supportedCollections) {
                        if (isTypeMatchingClass(attribute, cls)) {
                            return convertArrayToCollection(obj2, cls);
                        }
                    }
                    throw new IllegalStateException("Unknown type " + attribute);
                } else if (obj2 instanceof InetAddress) {
                    return obj2;
                } else {
                    try {
                        return InetAddress.getByName(obj2.toString());
                    } catch (UnknownHostException e2) {
                        throw new InvocationTargetException(e2);
                    }
                }
            }
        }

        private static boolean isTypeMatchingClass(String str, Class<?> cls) {
            return cls.getSimpleName().equalsIgnoreCase(str) || cls.getName().equals(str);
        }

        private Object itemValue(Object obj, Object obj2) throws Exception {
            if (obj2 instanceof String) {
                return obj2;
            }
            XmlParser.Node node = (XmlParser.Node) obj2;
            String tag = node.getTag();
            if ("Call".equals(tag)) {
                return call(obj, node);
            }
            if ("Get".equals(tag)) {
                return get(obj, node);
            }
            if ("New".equals(tag)) {
                return newObj(obj, node);
            }
            if ("Ref".equals(tag)) {
                return refObj(obj, node);
            }
            if ("Array".equals(tag)) {
                return newArray(obj, node);
            }
            if ("Map".equals(tag)) {
                return newMap(obj, node);
            }
            if ("Property".equals(tag)) {
                return propertyObj(node);
            }
            if ("SystemProperty".equals(tag)) {
                return System.getProperty(node.getAttribute(Constant.NAME), node.getAttribute("default"));
            }
            if ("Env".equals(tag)) {
                String attribute = node.getAttribute(Constant.NAME);
                String attribute2 = node.getAttribute("default");
                String str = System.getenv(attribute);
                return str == null ? attribute2 : str;
            }
            Logger access$100 = XmlConfiguration.LOG;
            access$100.warn("Unknown value tag: " + node, new Throwable());
            return null;
        }
    }

    public static void main(final String[] strArr) throws Exception {
        final AtomicReference atomicReference = new AtomicReference();
        AccessController.doPrivileged(new PrivilegedAction<Object>() {
            /* JADX WARNING: Removed duplicated region for block: B:19:0x0052 A[Catch:{ Exception -> 0x0119 }] */
            /* JADX WARNING: Removed duplicated region for block: B:26:0x007f A[Catch:{ Exception -> 0x0119 }] */
            /* JADX WARNING: Removed duplicated region for block: B:43:0x0103 A[Catch:{ Exception -> 0x0119 }] */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public java.lang.Object run() {
                /*
                    r10 = this;
                    r0 = 0
                    r1 = 0
                    java.lang.Class<org.eclipse.jetty.xml.XmlConfiguration> r2 = org.eclipse.jetty.xml.XmlConfiguration.class
                    java.lang.ClassLoader r2 = r2.getClassLoader()     // Catch:{ NoClassDefFoundError -> 0x0047, ClassNotFoundException -> 0x003d, Exception -> 0x0033 }
                    java.lang.String r3 = "org.eclipse.jetty.start.Config"
                    java.lang.Class r2 = r2.loadClass(r3)     // Catch:{ NoClassDefFoundError -> 0x0047, ClassNotFoundException -> 0x003d, Exception -> 0x0033 }
                    java.lang.String r3 = "getProperties"
                    java.lang.Class[] r4 = new java.lang.Class[r1]     // Catch:{ NoClassDefFoundError -> 0x0047, ClassNotFoundException -> 0x003d, Exception -> 0x0033 }
                    java.lang.reflect.Method r2 = r2.getMethod(r3, r4)     // Catch:{ NoClassDefFoundError -> 0x0047, ClassNotFoundException -> 0x003d, Exception -> 0x0033 }
                    java.lang.Object[] r3 = new java.lang.Object[r1]     // Catch:{ NoClassDefFoundError -> 0x0047, ClassNotFoundException -> 0x003d, Exception -> 0x0033 }
                    java.lang.Object r2 = r2.invoke(r0, r3)     // Catch:{ NoClassDefFoundError -> 0x0047, ClassNotFoundException -> 0x003d, Exception -> 0x0033 }
                    java.util.Properties r2 = (java.util.Properties) r2     // Catch:{ NoClassDefFoundError -> 0x0047, ClassNotFoundException -> 0x003d, Exception -> 0x0033 }
                    org.eclipse.jetty.util.log.Logger r3 = org.eclipse.jetty.xml.XmlConfiguration.LOG     // Catch:{ NoClassDefFoundError -> 0x0031, ClassNotFoundException -> 0x002f, Exception -> 0x002d }
                    java.lang.String r4 = "org.eclipse.jetty.start.Config properties = {}"
                    r5 = 1
                    java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ NoClassDefFoundError -> 0x0031, ClassNotFoundException -> 0x002f, Exception -> 0x002d }
                    r5[r1] = r2     // Catch:{ NoClassDefFoundError -> 0x0031, ClassNotFoundException -> 0x002f, Exception -> 0x002d }
                    r3.debug((java.lang.String) r4, (java.lang.Object[]) r5)     // Catch:{ NoClassDefFoundError -> 0x0031, ClassNotFoundException -> 0x002f, Exception -> 0x002d }
                    goto L_0x0050
                L_0x002d:
                    r3 = move-exception
                    goto L_0x0035
                L_0x002f:
                    r3 = move-exception
                    goto L_0x003f
                L_0x0031:
                    r3 = move-exception
                    goto L_0x0049
                L_0x0033:
                    r3 = move-exception
                    r2 = r0
                L_0x0035:
                    org.eclipse.jetty.util.log.Logger r4 = org.eclipse.jetty.xml.XmlConfiguration.LOG     // Catch:{ Exception -> 0x0119 }
                    r4.warn(r3)     // Catch:{ Exception -> 0x0119 }
                    goto L_0x0050
                L_0x003d:
                    r3 = move-exception
                    r2 = r0
                L_0x003f:
                    org.eclipse.jetty.util.log.Logger r4 = org.eclipse.jetty.xml.XmlConfiguration.LOG     // Catch:{ Exception -> 0x0119 }
                    r4.ignore(r3)     // Catch:{ Exception -> 0x0119 }
                    goto L_0x0050
                L_0x0047:
                    r3 = move-exception
                    r2 = r0
                L_0x0049:
                    org.eclipse.jetty.util.log.Logger r4 = org.eclipse.jetty.xml.XmlConfiguration.LOG     // Catch:{ Exception -> 0x0119 }
                    r4.ignore(r3)     // Catch:{ Exception -> 0x0119 }
                L_0x0050:
                    if (r2 != 0) goto L_0x0073
                    java.util.Properties r2 = new java.util.Properties     // Catch:{ Exception -> 0x0119 }
                    r2.<init>()     // Catch:{ Exception -> 0x0119 }
                    java.util.Properties r3 = java.lang.System.getProperties()     // Catch:{ Exception -> 0x0119 }
                    java.util.Enumeration r3 = r3.propertyNames()     // Catch:{ Exception -> 0x0119 }
                L_0x005f:
                    boolean r4 = r3.hasMoreElements()     // Catch:{ Exception -> 0x0119 }
                    if (r4 == 0) goto L_0x0073
                    java.lang.Object r4 = r3.nextElement()     // Catch:{ Exception -> 0x0119 }
                    java.lang.String r4 = (java.lang.String) r4     // Catch:{ Exception -> 0x0119 }
                    java.lang.String r5 = java.lang.System.getProperty(r4)     // Catch:{ Exception -> 0x0119 }
                    r2.put(r4, r5)     // Catch:{ Exception -> 0x0119 }
                    goto L_0x005f
                L_0x0073:
                    java.lang.String[] r3 = r2     // Catch:{ Exception -> 0x0119 }
                    int r3 = r3.length     // Catch:{ Exception -> 0x0119 }
                    java.lang.Object[] r3 = new java.lang.Object[r3]     // Catch:{ Exception -> 0x0119 }
                    r5 = r0
                    r4 = 0
                L_0x007a:
                    java.lang.String[] r6 = r2     // Catch:{ Exception -> 0x0119 }
                    int r6 = r6.length     // Catch:{ Exception -> 0x0119 }
                    if (r4 >= r6) goto L_0x00fe
                    java.lang.String[] r6 = r2     // Catch:{ Exception -> 0x0119 }
                    r6 = r6[r4]     // Catch:{ Exception -> 0x0119 }
                    java.util.Locale r7 = java.util.Locale.ENGLISH     // Catch:{ Exception -> 0x0119 }
                    java.lang.String r6 = r6.toLowerCase(r7)     // Catch:{ Exception -> 0x0119 }
                    java.lang.String r7 = ".properties"
                    boolean r6 = r6.endsWith(r7)     // Catch:{ Exception -> 0x0119 }
                    if (r6 == 0) goto L_0x00a1
                    java.lang.String[] r6 = r2     // Catch:{ Exception -> 0x0119 }
                    r6 = r6[r4]     // Catch:{ Exception -> 0x0119 }
                    org.eclipse.jetty.util.resource.Resource r6 = org.eclipse.jetty.util.resource.Resource.newResource((java.lang.String) r6)     // Catch:{ Exception -> 0x0119 }
                    java.io.InputStream r6 = r6.getInputStream()     // Catch:{ Exception -> 0x0119 }
                    r2.load(r6)     // Catch:{ Exception -> 0x0119 }
                    goto L_0x00fa
                L_0x00a1:
                    org.eclipse.jetty.xml.XmlConfiguration r6 = new org.eclipse.jetty.xml.XmlConfiguration     // Catch:{ Exception -> 0x0119 }
                    java.lang.String[] r7 = r2     // Catch:{ Exception -> 0x0119 }
                    r7 = r7[r4]     // Catch:{ Exception -> 0x0119 }
                    org.eclipse.jetty.util.resource.Resource r7 = org.eclipse.jetty.util.resource.Resource.newResource((java.lang.String) r7)     // Catch:{ Exception -> 0x0119 }
                    java.net.URL r7 = r7.getURL()     // Catch:{ Exception -> 0x0119 }
                    r6.<init>((java.net.URL) r7)     // Catch:{ Exception -> 0x0119 }
                    if (r5 == 0) goto L_0x00bf
                    java.util.Map r7 = r6.getIdMap()     // Catch:{ Exception -> 0x0119 }
                    java.util.Map r5 = r5.getIdMap()     // Catch:{ Exception -> 0x0119 }
                    r7.putAll(r5)     // Catch:{ Exception -> 0x0119 }
                L_0x00bf:
                    int r5 = r2.size()     // Catch:{ Exception -> 0x0119 }
                    if (r5 <= 0) goto L_0x00f3
                    java.util.HashMap r5 = new java.util.HashMap     // Catch:{ Exception -> 0x0119 }
                    r5.<init>()     // Catch:{ Exception -> 0x0119 }
                    java.util.Set r7 = r2.keySet()     // Catch:{ Exception -> 0x0119 }
                    java.util.Iterator r7 = r7.iterator()     // Catch:{ Exception -> 0x0119 }
                L_0x00d2:
                    boolean r8 = r7.hasNext()     // Catch:{ Exception -> 0x0119 }
                    if (r8 == 0) goto L_0x00ec
                    java.lang.Object r8 = r7.next()     // Catch:{ Exception -> 0x0119 }
                    java.lang.String r9 = r8.toString()     // Catch:{ Exception -> 0x0119 }
                    java.lang.Object r8 = r2.get(r8)     // Catch:{ Exception -> 0x0119 }
                    java.lang.String r8 = java.lang.String.valueOf(r8)     // Catch:{ Exception -> 0x0119 }
                    r5.put(r9, r8)     // Catch:{ Exception -> 0x0119 }
                    goto L_0x00d2
                L_0x00ec:
                    java.util.Map r7 = r6.getProperties()     // Catch:{ Exception -> 0x0119 }
                    r7.putAll(r5)     // Catch:{ Exception -> 0x0119 }
                L_0x00f3:
                    java.lang.Object r5 = r6.configure()     // Catch:{ Exception -> 0x0119 }
                    r3[r4] = r5     // Catch:{ Exception -> 0x0119 }
                    r5 = r6
                L_0x00fa:
                    int r4 = r4 + 1
                    goto L_0x007a
                L_0x00fe:
                    java.lang.String[] r2 = r2     // Catch:{ Exception -> 0x0119 }
                    int r2 = r2.length     // Catch:{ Exception -> 0x0119 }
                    if (r1 >= r2) goto L_0x0128
                    r2 = r3[r1]     // Catch:{ Exception -> 0x0119 }
                    boolean r2 = r2 instanceof org.eclipse.jetty.util.component.LifeCycle     // Catch:{ Exception -> 0x0119 }
                    if (r2 == 0) goto L_0x0116
                    r2 = r3[r1]     // Catch:{ Exception -> 0x0119 }
                    org.eclipse.jetty.util.component.LifeCycle r2 = (org.eclipse.jetty.util.component.LifeCycle) r2     // Catch:{ Exception -> 0x0119 }
                    boolean r4 = r2.isRunning()     // Catch:{ Exception -> 0x0119 }
                    if (r4 != 0) goto L_0x0116
                    r2.start()     // Catch:{ Exception -> 0x0119 }
                L_0x0116:
                    int r1 = r1 + 1
                    goto L_0x00fe
                L_0x0119:
                    r1 = move-exception
                    org.eclipse.jetty.util.log.Logger r2 = org.eclipse.jetty.xml.XmlConfiguration.LOG
                    java.lang.String r3 = "EXCEPTION "
                    r2.debug((java.lang.String) r3, (java.lang.Throwable) r1)
                    java.util.concurrent.atomic.AtomicReference r2 = r0
                    r2.set(r1)
                L_0x0128:
                    return r0
                */
                throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.xml.XmlConfiguration.C24761.run():java.lang.Object");
            }
        });
        Throwable th = (Throwable) atomicReference.get();
        if (th == null) {
            return;
        }
        if (th instanceof RuntimeException) {
            throw ((RuntimeException) th);
        } else if (th instanceof Exception) {
            throw ((Exception) th);
        } else if (th instanceof Error) {
            throw ((Error) th);
        } else {
            throw new Error(th);
        }
    }
}
