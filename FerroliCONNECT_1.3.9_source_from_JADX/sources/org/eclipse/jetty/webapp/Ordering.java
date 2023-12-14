package org.eclipse.jetty.webapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.webapp.FragmentDescriptor;

public interface Ordering {
    boolean hasOther();

    boolean isAbsolute();

    List<Resource> order(List<Resource> list);

    public static class AbsoluteOrdering implements Ordering {
        public static final String OTHER = "@@-OTHER-@@";
        protected boolean _hasOther = false;
        protected MetaData _metaData;
        protected List<String> _order = new ArrayList();

        public boolean isAbsolute() {
            return true;
        }

        public AbsoluteOrdering(MetaData metaData) {
            this._metaData = metaData;
        }

        public List<Resource> order(List<Resource> list) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList(list);
            HashMap hashMap = new HashMap(this._metaData.getNamedFragments());
            int i = -1;
            for (String next : this._order) {
                if (next.equals(OTHER)) {
                    i = arrayList.size();
                } else if (((FragmentDescriptor) hashMap.remove(next)) != null) {
                    Resource jarForFragment = this._metaData.getJarForFragment(next);
                    arrayList.add(jarForFragment);
                    arrayList2.remove(jarForFragment);
                }
            }
            if (this._hasOther) {
                if (i < 0) {
                    i = 0;
                }
                arrayList.addAll(i, arrayList2);
            }
            return arrayList;
        }

        public void add(String str) {
            this._order.add(str);
        }

        public void addOthers() {
            if (!this._hasOther) {
                this._hasOther = true;
                this._order.add(OTHER);
                return;
            }
            throw new IllegalStateException("Duplicate <other> element in absolute ordering");
        }

        public boolean hasOther() {
            return this._hasOther;
        }
    }

    public static class RelativeOrdering implements Ordering {
        protected LinkedList<Resource> _afterOthers = new LinkedList<>();
        protected LinkedList<Resource> _beforeOthers = new LinkedList<>();
        protected MetaData _metaData;
        protected LinkedList<Resource> _noOthers = new LinkedList<>();

        public boolean isAbsolute() {
            return false;
        }

        public RelativeOrdering(MetaData metaData) {
            this._metaData = metaData;
        }

        /* JADX WARNING: Removed duplicated region for block: B:25:0x0086  */
        /* JADX WARNING: Removed duplicated region for block: B:38:0x00c9  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.util.List<org.eclipse.jetty.util.resource.Resource> order(java.util.List<org.eclipse.jetty.util.resource.Resource> r6) {
            /*
                r5 = this;
                java.util.Iterator r6 = r6.iterator()
            L_0x0004:
                boolean r0 = r6.hasNext()
                r1 = 1
                r2 = 2
                if (r0 == 0) goto L_0x005e
                java.lang.Object r0 = r6.next()
                org.eclipse.jetty.util.resource.Resource r0 = (org.eclipse.jetty.util.resource.Resource) r0
                org.eclipse.jetty.webapp.MetaData r3 = r5._metaData
                org.eclipse.jetty.webapp.FragmentDescriptor r3 = r3.getFragment((org.eclipse.jetty.util.resource.Resource) r0)
                if (r3 == 0) goto L_0x0052
                int[] r4 = org.eclipse.jetty.webapp.Ordering.C24631.$SwitchMap$org$eclipse$jetty$webapp$FragmentDescriptor$OtherType
                org.eclipse.jetty.webapp.FragmentDescriptor$OtherType r3 = r3.getOtherType()
                int r3 = r3.ordinal()
                r3 = r4[r3]
                if (r3 == r1) goto L_0x0046
                if (r3 == r2) goto L_0x003a
                r1 = 3
                if (r3 == r1) goto L_0x002e
                goto L_0x0004
            L_0x002e:
                org.eclipse.jetty.webapp.MetaData r1 = r5._metaData
                org.eclipse.jetty.webapp.Ordering r1 = r1.getOrdering()
                org.eclipse.jetty.webapp.Ordering$RelativeOrdering r1 = (org.eclipse.jetty.webapp.Ordering.RelativeOrdering) r1
                r1.addAfterOthers(r0)
                goto L_0x0004
            L_0x003a:
                org.eclipse.jetty.webapp.MetaData r1 = r5._metaData
                org.eclipse.jetty.webapp.Ordering r1 = r1.getOrdering()
                org.eclipse.jetty.webapp.Ordering$RelativeOrdering r1 = (org.eclipse.jetty.webapp.Ordering.RelativeOrdering) r1
                r1.addBeforeOthers(r0)
                goto L_0x0004
            L_0x0046:
                org.eclipse.jetty.webapp.MetaData r1 = r5._metaData
                org.eclipse.jetty.webapp.Ordering r1 = r1.getOrdering()
                org.eclipse.jetty.webapp.Ordering$RelativeOrdering r1 = (org.eclipse.jetty.webapp.Ordering.RelativeOrdering) r1
                r1.addNoOthers(r0)
                goto L_0x0004
            L_0x0052:
                org.eclipse.jetty.webapp.MetaData r1 = r5._metaData
                org.eclipse.jetty.webapp.Ordering r1 = r1.getOrdering()
                org.eclipse.jetty.webapp.Ordering$RelativeOrdering r1 = (org.eclipse.jetty.webapp.Ordering.RelativeOrdering) r1
                r1.addNoOthers(r0)
                goto L_0x0004
            L_0x005e:
                java.util.ArrayList r6 = new java.util.ArrayList
                r6.<init>()
            L_0x0063:
                java.util.LinkedList<org.eclipse.jetty.util.resource.Resource> r0 = r5._beforeOthers
                boolean r0 = r5.orderList(r0)
                java.util.LinkedList<org.eclipse.jetty.util.resource.Resource> r3 = r5._afterOthers
                boolean r3 = r5.orderList(r3)
                java.util.LinkedList<org.eclipse.jetty.util.resource.Resource> r4 = r5._noOthers
                boolean r4 = r5.orderList(r4)
                if (r0 != 0) goto L_0x007d
                if (r3 != 0) goto L_0x007d
                if (r4 != 0) goto L_0x007d
                r0 = 1
                goto L_0x007e
            L_0x007d:
                r0 = 0
            L_0x007e:
                if (r0 != 0) goto L_0x0084
                int r2 = r2 + -1
                if (r2 > 0) goto L_0x0063
            L_0x0084:
                if (r0 == 0) goto L_0x00c9
                java.util.LinkedList<org.eclipse.jetty.util.resource.Resource> r0 = r5._beforeOthers
                java.util.Iterator r0 = r0.iterator()
            L_0x008c:
                boolean r1 = r0.hasNext()
                if (r1 == 0) goto L_0x009c
                java.lang.Object r1 = r0.next()
                org.eclipse.jetty.util.resource.Resource r1 = (org.eclipse.jetty.util.resource.Resource) r1
                r6.add(r1)
                goto L_0x008c
            L_0x009c:
                java.util.LinkedList<org.eclipse.jetty.util.resource.Resource> r0 = r5._noOthers
                java.util.Iterator r0 = r0.iterator()
            L_0x00a2:
                boolean r1 = r0.hasNext()
                if (r1 == 0) goto L_0x00b2
                java.lang.Object r1 = r0.next()
                org.eclipse.jetty.util.resource.Resource r1 = (org.eclipse.jetty.util.resource.Resource) r1
                r6.add(r1)
                goto L_0x00a2
            L_0x00b2:
                java.util.LinkedList<org.eclipse.jetty.util.resource.Resource> r0 = r5._afterOthers
                java.util.Iterator r0 = r0.iterator()
            L_0x00b8:
                boolean r1 = r0.hasNext()
                if (r1 == 0) goto L_0x00c8
                java.lang.Object r1 = r0.next()
                org.eclipse.jetty.util.resource.Resource r1 = (org.eclipse.jetty.util.resource.Resource) r1
                r6.add(r1)
                goto L_0x00b8
            L_0x00c8:
                return r6
            L_0x00c9:
                java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                java.lang.String r0 = "Circular references for fragments"
                r6.<init>(r0)
                goto L_0x00d2
            L_0x00d1:
                throw r6
            L_0x00d2:
                goto L_0x00d1
            */
            throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.webapp.Ordering.RelativeOrdering.order(java.util.List):java.util.List");
        }

        public boolean hasOther() {
            return !this._beforeOthers.isEmpty() || !this._afterOthers.isEmpty();
        }

        public void addBeforeOthers(Resource resource) {
            this._beforeOthers.addLast(resource);
        }

        public void addAfterOthers(Resource resource) {
            this._afterOthers.addLast(resource);
        }

        public void addNoOthers(Resource resource) {
            this._noOthers.addLast(resource);
        }

        /* access modifiers changed from: protected */
        public boolean orderList(LinkedList<Resource> linkedList) {
            boolean z = false;
            for (Resource fragment : new ArrayList(linkedList)) {
                FragmentDescriptor fragment2 = this._metaData.getFragment(fragment);
                if (fragment2 != null) {
                    List<String> befores = fragment2.getBefores();
                    if (befores != null && !befores.isEmpty()) {
                        for (String next : befores) {
                            if (!isBefore(linkedList, fragment2.getName(), next)) {
                                int indexOf = getIndexOf(linkedList, fragment2.getName());
                                int indexOf2 = getIndexOf(linkedList, next);
                                if (indexOf2 < 0) {
                                    Resource jarForFragment = this._metaData.getJarForFragment(next);
                                    if (jarForFragment != null && this._noOthers.remove(jarForFragment)) {
                                        insert((List<Resource>) linkedList, indexOf + 1, next);
                                    }
                                } else {
                                    linkedList.remove(indexOf);
                                    insert((List<Resource>) linkedList, indexOf2, fragment2.getName());
                                }
                                z = true;
                            }
                        }
                    }
                    List<String> afters = fragment2.getAfters();
                    if (afters != null && !afters.isEmpty()) {
                        for (String next2 : afters) {
                            if (!isAfter(linkedList, fragment2.getName(), next2)) {
                                int indexOf3 = getIndexOf(linkedList, fragment2.getName());
                                int indexOf4 = getIndexOf(linkedList, next2);
                                if (indexOf4 < 0) {
                                    Resource jarForFragment2 = this._metaData.getJarForFragment(next2);
                                    if (jarForFragment2 != null && this._noOthers.remove(jarForFragment2)) {
                                        insert((List<Resource>) linkedList, indexOf3, jarForFragment2);
                                    }
                                } else {
                                    linkedList.remove(indexOf4);
                                    insert((List<Resource>) linkedList, indexOf3, next2);
                                }
                                z = true;
                            }
                        }
                    }
                }
            }
            return z;
        }

        /* access modifiers changed from: protected */
        public boolean isBefore(List<Resource> list, String str, String str2) {
            LinkedList<Resource> linkedList;
            int indexOf = getIndexOf(list, str);
            int indexOf2 = getIndexOf(list, str2);
            if (indexOf2 >= 0 && indexOf2 < indexOf) {
                return false;
            }
            if (indexOf2 >= 0 || list == (linkedList = this._beforeOthers) || list != this._afterOthers) {
                return true;
            }
            if (!linkedList.contains(str2)) {
                return false;
            }
            throw new IllegalStateException("Incorrect relationship: " + str + " before " + str2);
        }

        /* access modifiers changed from: protected */
        public boolean isAfter(List<Resource> list, String str, String str2) {
            LinkedList<Resource> linkedList;
            int indexOf = getIndexOf(list, str);
            int indexOf2 = getIndexOf(list, str2);
            if (indexOf2 >= 0 && indexOf < indexOf2) {
                return false;
            }
            if (indexOf2 >= 0 || list == (linkedList = this._afterOthers) || list != this._beforeOthers) {
                return true;
            }
            if (!linkedList.contains(str2)) {
                return false;
            }
            throw new IllegalStateException("Incorrect relationship: " + str2 + " after " + str);
        }

        /* access modifiers changed from: protected */
        public void insert(List<Resource> list, int i, String str) {
            Resource jarForFragment = this._metaData.getJarForFragment(str);
            if (jarForFragment != null) {
                insert(list, i, jarForFragment);
                return;
            }
            throw new IllegalStateException("No jar for insertion");
        }

        /* access modifiers changed from: protected */
        public void insert(List<Resource> list, int i, Resource resource) {
            if (list == null) {
                throw new IllegalStateException("List is null for insertion");
            } else if (i > list.size()) {
                list.add(resource);
            } else {
                list.add(i, resource);
            }
        }

        /* access modifiers changed from: protected */
        public void remove(List<Resource> list, Resource resource) {
            if (list != null) {
                list.remove(resource);
            }
        }

        /* access modifiers changed from: protected */
        public int getIndexOf(List<Resource> list, String str) {
            Resource jarForFragment;
            if (this._metaData.getFragment(str) == null || (jarForFragment = this._metaData.getJarForFragment(str)) == null) {
                return -1;
            }
            return list.indexOf(jarForFragment);
        }
    }

    /* renamed from: org.eclipse.jetty.webapp.Ordering$1 */
    static /* synthetic */ class C24631 {
        static final /* synthetic */ int[] $SwitchMap$org$eclipse$jetty$webapp$FragmentDescriptor$OtherType = new int[FragmentDescriptor.OtherType.values().length];

        /* JADX WARNING: Can't wrap try/catch for region: R(8:0|1|2|3|4|5|6|8) */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:3:0x0014 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:5:0x001f */
        static {
            /*
                org.eclipse.jetty.webapp.FragmentDescriptor$OtherType[] r0 = org.eclipse.jetty.webapp.FragmentDescriptor.OtherType.values()
                int r0 = r0.length
                int[] r0 = new int[r0]
                $SwitchMap$org$eclipse$jetty$webapp$FragmentDescriptor$OtherType = r0
                int[] r0 = $SwitchMap$org$eclipse$jetty$webapp$FragmentDescriptor$OtherType     // Catch:{ NoSuchFieldError -> 0x0014 }
                org.eclipse.jetty.webapp.FragmentDescriptor$OtherType r1 = org.eclipse.jetty.webapp.FragmentDescriptor.OtherType.None     // Catch:{ NoSuchFieldError -> 0x0014 }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x0014 }
                r2 = 1
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x0014 }
            L_0x0014:
                int[] r0 = $SwitchMap$org$eclipse$jetty$webapp$FragmentDescriptor$OtherType     // Catch:{ NoSuchFieldError -> 0x001f }
                org.eclipse.jetty.webapp.FragmentDescriptor$OtherType r1 = org.eclipse.jetty.webapp.FragmentDescriptor.OtherType.Before     // Catch:{ NoSuchFieldError -> 0x001f }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x001f }
                r2 = 2
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x001f }
            L_0x001f:
                int[] r0 = $SwitchMap$org$eclipse$jetty$webapp$FragmentDescriptor$OtherType     // Catch:{ NoSuchFieldError -> 0x002a }
                org.eclipse.jetty.webapp.FragmentDescriptor$OtherType r1 = org.eclipse.jetty.webapp.FragmentDescriptor.OtherType.After     // Catch:{ NoSuchFieldError -> 0x002a }
                int r1 = r1.ordinal()     // Catch:{ NoSuchFieldError -> 0x002a }
                r2 = 3
                r0[r1] = r2     // Catch:{ NoSuchFieldError -> 0x002a }
            L_0x002a:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: org.eclipse.jetty.webapp.Ordering.C24631.<clinit>():void");
        }
    }
}
