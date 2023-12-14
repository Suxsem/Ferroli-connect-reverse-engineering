package org.eclipse.jetty.util.ajax;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.eclipse.jetty.util.ajax.JSON;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.paho.client.mqttv3.MqttTopic;

public class JSONPojoConvertor implements JSON.Convertor {
    public static final NumberType DOUBLE = new NumberType() {
        public Object getActualValue(Number number) {
            return number instanceof Double ? number : new Double(number.doubleValue());
        }
    };
    public static final NumberType FLOAT = new NumberType() {
        public Object getActualValue(Number number) {
            return new Float(number.floatValue());
        }
    };
    public static final Object[] GETTER_ARG = new Object[0];
    public static final NumberType INTEGER = new NumberType() {
        public Object getActualValue(Number number) {
            return new Integer(number.intValue());
        }
    };
    /* access modifiers changed from: private */
    public static final Logger LOG = Log.getLogger((Class<?>) JSONPojoConvertor.class);
    public static final NumberType LONG = new NumberType() {
        public Object getActualValue(Number number) {
            return number instanceof Long ? number : new Long(number.longValue());
        }
    };
    public static final Object[] NULL_ARG = {null};
    public static final NumberType SHORT = new NumberType() {
        public Object getActualValue(Number number) {
            return new Short(number.shortValue());
        }
    };
    /* access modifiers changed from: private */
    public static final Map<Class<?>, NumberType> __numberTypes = new HashMap();
    protected Set<String> _excluded;
    protected boolean _fromJSON;
    protected Map<String, Method> _getters;
    protected Class<?> _pojoClass;
    protected Map<String, Setter> _setters;

    public interface NumberType {
        Object getActualValue(Number number);
    }

    static {
        __numberTypes.put(Short.class, SHORT);
        __numberTypes.put(Short.TYPE, SHORT);
        __numberTypes.put(Integer.class, INTEGER);
        __numberTypes.put(Integer.TYPE, INTEGER);
        __numberTypes.put(Long.class, LONG);
        __numberTypes.put(Long.TYPE, LONG);
        __numberTypes.put(Float.class, FLOAT);
        __numberTypes.put(Float.TYPE, FLOAT);
        __numberTypes.put(Double.class, DOUBLE);
        __numberTypes.put(Double.TYPE, DOUBLE);
    }

    public static NumberType getNumberType(Class<?> cls) {
        return __numberTypes.get(cls);
    }

    public JSONPojoConvertor(Class<?> cls) {
        this(cls, (Set<String>) null, true);
    }

    public JSONPojoConvertor(Class<?> cls, String[] strArr) {
        this(cls, new HashSet(Arrays.asList(strArr)), true);
    }

    public JSONPojoConvertor(Class<?> cls, Set<String> set) {
        this(cls, set, true);
    }

    public JSONPojoConvertor(Class<?> cls, Set<String> set, boolean z) {
        this._getters = new HashMap();
        this._setters = new HashMap();
        this._pojoClass = cls;
        this._excluded = set;
        this._fromJSON = z;
        init();
    }

    public JSONPojoConvertor(Class<?> cls, boolean z) {
        this(cls, (Set<String>) null, z);
    }

    /* access modifiers changed from: protected */
    public void init() {
        String str;
        Method[] methods = this._pojoClass.getMethods();
        for (Method method : methods) {
            if (!Modifier.isStatic(method.getModifiers()) && method.getDeclaringClass() != Object.class) {
                String name = method.getName();
                int length = method.getParameterTypes().length;
                if (length != 0) {
                    if (length == 1 && name.startsWith("set") && name.length() > 3) {
                        String str2 = name.substring(3, 4).toLowerCase(Locale.ENGLISH) + name.substring(4);
                        if (includeField(str2, method)) {
                            addSetter(str2, method);
                        }
                    }
                } else if (method.getReturnType() != null) {
                    if (name.startsWith("is") && name.length() > 2) {
                        str = name.substring(2, 3).toLowerCase(Locale.ENGLISH) + name.substring(3);
                    } else if (name.startsWith("get") && name.length() > 3) {
                        str = name.substring(3, 4).toLowerCase(Locale.ENGLISH) + name.substring(4);
                    }
                    if (includeField(str, method)) {
                        addGetter(str, method);
                    }
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void addGetter(String str, Method method) {
        this._getters.put(str, method);
    }

    /* access modifiers changed from: protected */
    public void addSetter(String str, Method method) {
        this._setters.put(str, new Setter(str, method));
    }

    /* access modifiers changed from: protected */
    public Setter getSetter(String str) {
        return this._setters.get(str);
    }

    /* access modifiers changed from: protected */
    public boolean includeField(String str, Method method) {
        Set<String> set = this._excluded;
        return set == null || !set.contains(str);
    }

    /* access modifiers changed from: protected */
    public int getExcludedCount() {
        Set<String> set = this._excluded;
        if (set == null) {
            return 0;
        }
        return set.size();
    }

    public Object fromJSON(Map map) {
        try {
            Object newInstance = this._pojoClass.newInstance();
            setProps(newInstance, map);
            return newInstance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public int setProps(Object obj, Map<?, ?> map) {
        int i = 0;
        for (Map.Entry next : map.entrySet()) {
            Setter setter = getSetter((String) next.getKey());
            if (setter != null) {
                try {
                    setter.invoke(obj, next.getValue());
                    i++;
                } catch (Exception e) {
                    Logger logger = LOG;
                    logger.warn(this._pojoClass.getName() + MqttTopic.MULTI_LEVEL_WILDCARD + setter.getPropertyName() + " not set from " + next.getValue().getClass().getName() + "=" + next.getValue().toString(), new Object[0]);
                    log(e);
                }
            }
        }
        return i;
    }

    public void toJSON(Object obj, JSON.Output output) {
        if (this._fromJSON) {
            output.addClass(this._pojoClass);
        }
        for (Map.Entry next : this._getters.entrySet()) {
            try {
                output.add((String) next.getKey(), ((Method) next.getValue()).invoke(obj, GETTER_ARG));
            } catch (Exception e) {
                LOG.warn("{} property '{}' excluded. (errors)", this._pojoClass.getName(), next.getKey());
                log(e);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void log(Throwable th) {
        LOG.ignore(th);
    }

    public static class Setter {
        protected Class<?> _componentType;
        protected NumberType _numberType = ((NumberType) JSONPojoConvertor.__numberTypes.get(this._type));
        protected String _propertyName;
        protected Method _setter;
        protected Class<?> _type;

        public Setter(String str, Method method) {
            this._propertyName = str;
            this._setter = method;
            this._type = method.getParameterTypes()[0];
            if (this._numberType == null && this._type.isArray()) {
                this._componentType = this._type.getComponentType();
                this._numberType = (NumberType) JSONPojoConvertor.__numberTypes.get(this._componentType);
            }
        }

        public String getPropertyName() {
            return this._propertyName;
        }

        public Method getMethod() {
            return this._setter;
        }

        public NumberType getNumberType() {
            return this._numberType;
        }

        public Class<?> getType() {
            return this._type;
        }

        public Class<?> getComponentType() {
            return this._componentType;
        }

        public boolean isPropertyNumber() {
            return this._numberType != null;
        }

        public void invoke(Object obj, Object obj2) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
            if (obj2 == null) {
                this._setter.invoke(obj, JSONPojoConvertor.NULL_ARG);
            } else {
                invokeObject(obj, obj2);
            }
        }

        /* access modifiers changed from: protected */
        public void invokeObject(Object obj, Object obj2) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
            if (!this._type.isEnum()) {
                NumberType numberType = this._numberType;
                if (numberType != null && (obj2 instanceof Number)) {
                    this._setter.invoke(obj, new Object[]{numberType.getActualValue((Number) obj2)});
                } else if (Character.TYPE.equals(this._type) || Character.class.equals(this._type)) {
                    this._setter.invoke(obj, new Object[]{Character.valueOf(String.valueOf(obj2).charAt(0))});
                } else if (this._componentType == null || !obj2.getClass().isArray()) {
                    this._setter.invoke(obj, new Object[]{obj2});
                } else if (this._numberType == null) {
                    int length = Array.getLength(obj2);
                    Object newInstance = Array.newInstance(this._componentType, length);
                    try {
                        System.arraycopy(obj2, 0, newInstance, 0, length);
                        this._setter.invoke(obj, new Object[]{newInstance});
                    } catch (Exception e) {
                        JSONPojoConvertor.LOG.ignore(e);
                        this._setter.invoke(obj, new Object[]{obj2});
                    }
                } else {
                    Object[] objArr = (Object[]) obj2;
                    Object newInstance2 = Array.newInstance(this._componentType, objArr.length);
                    int i = 0;
                    while (i < objArr.length) {
                        try {
                            Array.set(newInstance2, i, this._numberType.getActualValue((Number) objArr[i]));
                            i++;
                        } catch (Exception e2) {
                            JSONPojoConvertor.LOG.ignore(e2);
                            this._setter.invoke(obj, new Object[]{obj2});
                            return;
                        }
                    }
                    this._setter.invoke(obj, new Object[]{newInstance2});
                }
            } else if (obj2 instanceof Enum) {
                this._setter.invoke(obj, new Object[]{obj2});
            } else {
                this._setter.invoke(obj, new Object[]{Enum.valueOf(this._type, obj2.toString())});
            }
        }
    }
}
