package org.eclipse.jetty.webapp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.Ordering;

public class MetaData {
    private static final Logger LOG = Log.getLogger((Class<?>) MetaData.class);
    public static final String ORDERED_LIBS = "javax.servlet.context.orderedLibs";
    protected final List<DiscoveredAnnotation> _annotations = new ArrayList();
    protected final List<DescriptorProcessor> _descriptorProcessors = new ArrayList();
    protected boolean _metaDataComplete;
    protected final List<Resource> _orderedContainerJars = new ArrayList();
    protected final List<Resource> _orderedWebInfJars = new ArrayList();
    protected Ordering _ordering;
    protected Map<String, OriginInfo> _origins = new HashMap();
    protected WebDescriptor _webDefaultsRoot;
    protected final Map<Resource, List<DiscoveredAnnotation>> _webFragmentAnnotations = new HashMap();
    protected final Map<String, FragmentDescriptor> _webFragmentNameMap = new HashMap();
    protected final Map<Resource, FragmentDescriptor> _webFragmentResourceMap = new HashMap();
    protected final List<FragmentDescriptor> _webFragmentRoots = new ArrayList();
    protected final List<Resource> _webInfJars = new ArrayList();
    protected final List<WebDescriptor> _webOverrideRoots = new ArrayList();
    protected WebDescriptor _webXmlRoot;
    protected boolean allowDuplicateFragmentNames = false;

    public static class OriginInfo {
        protected Descriptor descriptor;
        protected String name;
        protected Origin origin;

        public OriginInfo(String str, Descriptor descriptor2) {
            this.name = str;
            this.descriptor = descriptor2;
            if (descriptor2 == null) {
                throw new IllegalArgumentException("No descriptor");
            } else if (descriptor2 instanceof FragmentDescriptor) {
                this.origin = Origin.WebFragment;
            } else if (descriptor2 instanceof OverrideDescriptor) {
                this.origin = Origin.WebOverride;
            } else if (descriptor2 instanceof DefaultsDescriptor) {
                this.origin = Origin.WebDefaults;
            } else {
                this.origin = Origin.WebXml;
            }
        }

        public OriginInfo(String str) {
            this.name = str;
            this.origin = Origin.Annotation;
        }

        public OriginInfo(String str, Origin origin2) {
            this.name = str;
            this.origin = origin2;
        }

        public String getName() {
            return this.name;
        }

        public Origin getOriginType() {
            return this.origin;
        }

        public Descriptor getDescriptor() {
            return this.descriptor;
        }
    }

    public void clear() {
        this._webDefaultsRoot = null;
        this._origins.clear();
        this._webXmlRoot = null;
        this._webOverrideRoots.clear();
        this._metaDataComplete = false;
        this._annotations.clear();
        this._descriptorProcessors.clear();
        this._webFragmentRoots.clear();
        this._webFragmentNameMap.clear();
        this._webFragmentResourceMap.clear();
        this._webFragmentAnnotations.clear();
        this._webInfJars.clear();
        this._orderedWebInfJars.clear();
        this._orderedContainerJars.clear();
        this._ordering = null;
        this.allowDuplicateFragmentNames = false;
    }

    public void setDefaults(Resource resource) throws Exception {
        this._webDefaultsRoot = new DefaultsDescriptor(resource);
        this._webDefaultsRoot.parse();
        if (this._webDefaultsRoot.isOrdered()) {
            if (this._ordering == null) {
                this._ordering = new Ordering.AbsoluteOrdering(this);
            }
            for (String next : this._webDefaultsRoot.getOrdering()) {
                if (next.equalsIgnoreCase("others")) {
                    ((Ordering.AbsoluteOrdering) this._ordering).addOthers();
                } else {
                    ((Ordering.AbsoluteOrdering) this._ordering).add(next);
                }
            }
        }
    }

    public void setWebXml(Resource resource) throws Exception {
        this._webXmlRoot = new WebDescriptor(resource);
        this._webXmlRoot.parse();
        this._metaDataComplete = this._webXmlRoot.getMetaDataComplete() == MetaDataComplete.True;
        if (this._webXmlRoot.isOrdered()) {
            if (this._ordering == null) {
                this._ordering = new Ordering.AbsoluteOrdering(this);
            }
            for (String next : this._webXmlRoot.getOrdering()) {
                if (next.equalsIgnoreCase("others")) {
                    ((Ordering.AbsoluteOrdering) this._ordering).addOthers();
                } else {
                    ((Ordering.AbsoluteOrdering) this._ordering).add(next);
                }
            }
        }
    }

    public void addOverride(Resource resource) throws Exception {
        OverrideDescriptor overrideDescriptor = new OverrideDescriptor(resource);
        overrideDescriptor.setValidating(false);
        overrideDescriptor.parse();
        int i = C24611.$SwitchMap$org$eclipse$jetty$webapp$MetaDataComplete[overrideDescriptor.getMetaDataComplete().ordinal()];
        if (i == 1) {
            this._metaDataComplete = true;
        } else if (i == 2) {
            this._metaDataComplete = false;
        }
        if (overrideDescriptor.isOrdered()) {
            if (this._ordering == null) {
                this._ordering = new Ordering.AbsoluteOrdering(this);
            }
            for (String next : overrideDescriptor.getOrdering()) {
                if (next.equalsIgnoreCase("others")) {
                    ((Ordering.AbsoluteOrdering) this._ordering).addOthers();
                } else {
                    ((Ordering.AbsoluteOrdering) this._ordering).add(next);
                }
            }
        }
        this._webOverrideRoots.add(overrideDescriptor);
    }

    /* renamed from: org.eclipse.jetty.webapp.MetaData$1 */
    static /* synthetic */ class C24611 {
        static final /* synthetic */ int[] $SwitchMap$org$eclipse$jetty$webapp$MetaDataComplete = new int[MetaDataComplete.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|8) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        static {
            /*
                org.eclipse.jetty.webapp.MetaDataComplete[] r0 = org.eclipse.jetty.webapp.MetaDataComplete.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$org$eclipse$jetty$webapp$MetaDataComplete = r0
                int[] r0 = $SwitchMap$org$eclipse$jetty$webapp$MetaDataComplete     // Catch:{ NoSuchFieldError -> 0x0014 }
                org.eclipse.jetty.webapp.MetaDataComplete r1 = org.eclipse.jetty.webapp.MetaDataComplete.True     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$org$eclipse$jetty$webapp$MetaDataComplete     // Catch:{ NoSuchFieldError -> 0x001f }
                org.eclipse.jetty.webapp.MetaDataComplete r1 = org.eclipse.jetty.webapp.MetaDataComplete.False     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$org$eclipse$jetty$webapp$MetaDataComplete     // Catch:{ NoSuchFieldError -> 0x002a }
                org.eclipse.jetty.webapp.MetaDataComplete r1 = org.eclipse.jetty.webapp.MetaDataComplete.NotSet     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.webapp.MetaData.C24611.<clinit>():void");
        }
    }

    public void addFragment(Resource resource, Resource resource2) throws Exception {
        if (!this._metaDataComplete) {
            FragmentDescriptor fragmentDescriptor = new FragmentDescriptor(resource2);
            this._webFragmentResourceMap.put(resource, fragmentDescriptor);
            this._webFragmentRoots.add(fragmentDescriptor);
            fragmentDescriptor.parse();
            if (fragmentDescriptor.getName() != null) {
                Descriptor descriptor = this._webFragmentNameMap.get(fragmentDescriptor.getName());
                if (descriptor == null || isAllowDuplicateFragmentNames()) {
                    this._webFragmentNameMap.put(fragmentDescriptor.getName(), fragmentDescriptor);
                } else {
                    throw new IllegalStateException("Duplicate fragment name: " + fragmentDescriptor.getName() + " for " + descriptor.getResource() + " and " + fragmentDescriptor.getResource());
                }
            }
            Ordering ordering = this._ordering;
            if ((ordering == null || !ordering.isAbsolute()) && this._ordering == null && fragmentDescriptor.isOrdered()) {
                this._ordering = new Ordering.RelativeOrdering(this);
            }
        }
    }

    public void addDiscoveredAnnotations(List<DiscoveredAnnotation> list) {
        if (list != null) {
            for (DiscoveredAnnotation next : list) {
                Resource resource = next.getResource();
                if (resource == null || !this._webInfJars.contains(resource)) {
                    this._annotations.add(next);
                } else {
                    addDiscoveredAnnotation(next.getResource(), next);
                }
            }
        }
    }

    public void addDiscoveredAnnotation(Resource resource, DiscoveredAnnotation discoveredAnnotation) {
        List list = this._webFragmentAnnotations.get(resource);
        if (list == null) {
            list = new ArrayList();
            this._webFragmentAnnotations.put(resource, list);
        }
        list.add(discoveredAnnotation);
    }

    public void addDiscoveredAnnotations(Resource resource, List<DiscoveredAnnotation> list) {
        List list2 = this._webFragmentAnnotations.get(resource);
        if (list2 == null) {
            list2 = new ArrayList();
            this._webFragmentAnnotations.put(resource, list2);
        }
        list2.addAll(list);
    }

    public void addDescriptorProcessor(DescriptorProcessor descriptorProcessor) {
        this._descriptorProcessors.add(descriptorProcessor);
    }

    public void orderFragments() {
        if (this._orderedWebInfJars.size() != this._webInfJars.size()) {
            Ordering ordering = this._ordering;
            if (ordering != null) {
                this._orderedWebInfJars.addAll(ordering.order(this._webInfJars));
            } else {
                this._orderedWebInfJars.addAll(this._webInfJars);
            }
        }
    }

    public void resolve(WebAppContext webAppContext) throws Exception {
        LOG.debug("metadata resolve {}", webAppContext);
        this._origins.clear();
        if (this._ordering != null) {
            ArrayList arrayList = new ArrayList();
            for (Resource name : this._orderedWebInfJars) {
                String name2 = name.getName();
                int indexOf = name2.indexOf(".jar");
                arrayList.add(name2.substring(name2.lastIndexOf("/", indexOf) + 1, indexOf + 4));
            }
            webAppContext.setAttribute("javax.servlet.context.orderedLibs", arrayList);
        }
        if (this._webXmlRoot != null) {
            webAppContext.getServletContext().setEffectiveMajorVersion(this._webXmlRoot.getMajorVersion());
            webAppContext.getServletContext().setEffectiveMinorVersion(this._webXmlRoot.getMinorVersion());
        }
        for (DescriptorProcessor next : this._descriptorProcessors) {
            next.process(webAppContext, getWebDefault());
            next.process(webAppContext, getWebXml());
            for (WebDescriptor next2 : getOverrideWebs()) {
                LOG.debug("process {} {}", webAppContext, next2);
                next.process(webAppContext, next2);
            }
        }
        for (DiscoveredAnnotation next3 : this._annotations) {
            LOG.debug("apply {}", next3);
            next3.apply();
        }
        for (Resource next4 : getOrderedWebInfJars()) {
            FragmentDescriptor fragmentDescriptor = this._webFragmentResourceMap.get(next4);
            if (fragmentDescriptor != null) {
                for (DescriptorProcessor process : this._descriptorProcessors) {
                    LOG.debug("process {} {}", webAppContext, fragmentDescriptor);
                    process.process(webAppContext, fragmentDescriptor);
                }
            }
            List<DiscoveredAnnotation> list = this._webFragmentAnnotations.get(next4);
            if (list != null) {
                for (DiscoveredAnnotation discoveredAnnotation : list) {
                    LOG.debug("apply {}", discoveredAnnotation);
                    discoveredAnnotation.apply();
                }
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x000c, code lost:
        r0 = r6._webXmlRoot;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean isDistributable() {
        /*
            r6 = this;
            org.eclipse.jetty.webapp.WebDescriptor r0 = r6._webDefaultsRoot
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x000c
            boolean r0 = r0.isDistributable()
            if (r0 != 0) goto L_0x0016
        L_0x000c:
            org.eclipse.jetty.webapp.WebDescriptor r0 = r6._webXmlRoot
            if (r0 == 0) goto L_0x0018
            boolean r0 = r0.isDistributable()
            if (r0 == 0) goto L_0x0018
        L_0x0016:
            r0 = 1
            goto L_0x0019
        L_0x0018:
            r0 = 0
        L_0x0019:
            java.util.List<org.eclipse.jetty.webapp.WebDescriptor> r3 = r6._webOverrideRoots
            java.util.Iterator r3 = r3.iterator()
        L_0x001f:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x0031
            java.lang.Object r4 = r3.next()
            org.eclipse.jetty.webapp.WebDescriptor r4 = (org.eclipse.jetty.webapp.WebDescriptor) r4
            boolean r4 = r4.isDistributable()
            r0 = r0 & r4
            goto L_0x001f
        L_0x0031:
            java.util.List r3 = r6.getOrderedWebInfJars()
            java.util.Iterator r3 = r3.iterator()
        L_0x0039:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x005b
            java.lang.Object r4 = r3.next()
            org.eclipse.jetty.util.resource.Resource r4 = (org.eclipse.jetty.util.resource.Resource) r4
            java.util.Map<org.eclipse.jetty.util.resource.Resource, org.eclipse.jetty.webapp.FragmentDescriptor> r5 = r6._webFragmentResourceMap
            java.lang.Object r4 = r5.get(r4)
            org.eclipse.jetty.webapp.FragmentDescriptor r4 = (org.eclipse.jetty.webapp.FragmentDescriptor) r4
            if (r4 == 0) goto L_0x0039
            if (r0 == 0) goto L_0x0059
            boolean r0 = r4.isDistributable()
            if (r0 == 0) goto L_0x0059
            r0 = 1
            goto L_0x0039
        L_0x0059:
            r0 = 0
            goto L_0x0039
        L_0x005b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.webapp.MetaData.isDistributable():boolean");
    }

    public WebDescriptor getWebXml() {
        return this._webXmlRoot;
    }

    public List<WebDescriptor> getOverrideWebs() {
        return this._webOverrideRoots;
    }

    public WebDescriptor getWebDefault() {
        return this._webDefaultsRoot;
    }

    public List<FragmentDescriptor> getFragments() {
        return this._webFragmentRoots;
    }

    public List<Resource> getOrderedWebInfJars() {
        List<Resource> list = this._orderedWebInfJars;
        return list == null ? new ArrayList() : list;
    }

    public List<FragmentDescriptor> getOrderedFragments() {
        ArrayList arrayList = new ArrayList();
        List<Resource> list = this._orderedWebInfJars;
        if (list == null) {
            return arrayList;
        }
        for (Resource resource : list) {
            FragmentDescriptor fragmentDescriptor = this._webFragmentResourceMap.get(resource);
            if (fragmentDescriptor != null) {
                arrayList.add(fragmentDescriptor);
            }
        }
        return arrayList;
    }

    public Ordering getOrdering() {
        return this._ordering;
    }

    public void setOrdering(Ordering ordering) {
        this._ordering = ordering;
    }

    public FragmentDescriptor getFragment(Resource resource) {
        return this._webFragmentResourceMap.get(resource);
    }

    public FragmentDescriptor getFragment(String str) {
        return this._webFragmentNameMap.get(str);
    }

    public Resource getJarForFragment(String str) {
        FragmentDescriptor fragment = getFragment(str);
        Resource resource = null;
        if (fragment == null) {
            return null;
        }
        for (Resource next : this._webFragmentResourceMap.keySet()) {
            if (this._webFragmentResourceMap.get(next).equals(fragment)) {
                resource = next;
            }
        }
        return resource;
    }

    public Map<String, FragmentDescriptor> getNamedFragments() {
        return Collections.unmodifiableMap(this._webFragmentNameMap);
    }

    public Origin getOrigin(String str) {
        OriginInfo originInfo = this._origins.get(str);
        if (originInfo == null) {
            return Origin.NotSet;
        }
        return originInfo.getOriginType();
    }

    public Descriptor getOriginDescriptor(String str) {
        OriginInfo originInfo = this._origins.get(str);
        if (originInfo == null) {
            return null;
        }
        return originInfo.getDescriptor();
    }

    public void setOrigin(String str, Descriptor descriptor) {
        this._origins.put(str, new OriginInfo(str, descriptor));
    }

    public void setOrigin(String str) {
        if (str != null) {
            this._origins.put(str, new OriginInfo(str, Origin.Annotation));
        }
    }

    public void setOrigin(String str, Origin origin) {
        if (str != null) {
            this._origins.put(str, new OriginInfo(str, origin));
        }
    }

    public boolean isMetaDataComplete() {
        return this._metaDataComplete;
    }

    public void addWebInfJar(Resource resource) {
        this._webInfJars.add(resource);
    }

    public List<Resource> getWebInfJars() {
        return Collections.unmodifiableList(this._webInfJars);
    }

    public List<Resource> getOrderedContainerJars() {
        return this._orderedContainerJars;
    }

    public void addContainerJar(Resource resource) {
        this._orderedContainerJars.add(resource);
    }

    public boolean isAllowDuplicateFragmentNames() {
        return this.allowDuplicateFragmentNames;
    }

    public void setAllowDuplicateFragmentNames(boolean z) {
        this.allowDuplicateFragmentNames = z;
    }
}
