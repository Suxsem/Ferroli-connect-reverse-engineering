package com.google.firebase;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.annotation.VisibleForTesting;
import android.support.p000v4.content.ContextCompat;
import android.support.p000v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.annotation.KeepForSdk;
import com.google.android.gms.common.api.internal.BackgroundDetector;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.common.util.PlatformVersion;
import com.google.android.gms.common.util.ProcessUtils;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.annotations.PublicApi;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.components.Component;
import com.google.firebase.components.ComponentDiscovery;
import com.google.firebase.components.ComponentRegistrar;
import com.google.firebase.components.ComponentRuntime;
import com.google.firebase.events.Event;
import com.google.firebase.events.Publisher;
import com.google.firebase.internal.DefaultIdTokenListenersCountChangedListener;
import com.google.firebase.internal.InternalTokenProvider;
import com.google.firebase.internal.InternalTokenResult;
import com.google.firebase.platforminfo.DefaultUserAgentPublisher;
import com.google.firebase.platforminfo.LibraryVersionComponent;
import com.p107tb.appyunsdk.Constant;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.concurrent.GuardedBy;
import org.eclipse.paho.client.mqttv3.MqttTopic;

@PublicApi
/* compiled from: com.google.firebase:firebase-common@@16.1.0 */
public class FirebaseApp {
    private static final List<String> API_INITIALIZERS = Arrays.asList(new String[]{AUTH_CLASSNAME, IID_CLASSNAME});
    private static final String AUTH_CLASSNAME = "com.google.firebase.auth.FirebaseAuth";
    private static final Set<String> CORE_CLASSES = Collections.emptySet();
    private static final String CRASH_CLASSNAME = "com.google.firebase.crash.FirebaseCrash";
    @VisibleForTesting
    static final String DATA_COLLECTION_DEFAULT_ENABLED = "firebase_data_collection_default_enabled";
    private static final List<String> DEFAULT_APP_API_INITITALIZERS = Collections.singletonList(CRASH_CLASSNAME);
    public static final String DEFAULT_APP_NAME = "[DEFAULT]";
    private static final List<String> DEFAULT_CONTEXT_API_INITITALIZERS = Arrays.asList(new String[]{MEASUREMENT_CLASSNAME});
    private static final List<String> DIRECT_BOOT_COMPATIBLE_API_INITIALIZERS = Arrays.asList(new String[0]);
    private static final String FIREBASE_ANDROID = "fire-android";
    private static final String FIREBASE_APP_PREFS = "com.google.firebase.common.prefs:";
    private static final String FIREBASE_COMMON = "fire-core";
    private static final String IID_CLASSNAME = "com.google.firebase.iid.FirebaseInstanceId";
    @GuardedBy("LOCK")
    static final Map<String, FirebaseApp> INSTANCES = new ArrayMap();
    /* access modifiers changed from: private */
    public static final Object LOCK = new Object();
    private static final String LOG_TAG = "FirebaseApp";
    private static final String MEASUREMENT_CLASSNAME = "com.google.android.gms.measurement.AppMeasurement";
    private static final Executor UI_EXECUTOR = new UiExecutor();
    private final Context applicationContext;
    /* access modifiers changed from: private */
    public final AtomicBoolean automaticResourceManagementEnabled = new AtomicBoolean(false);
    private final List<BackgroundStateChangeListener> backgroundStateChangeListeners = new CopyOnWriteArrayList();
    private final ComponentRuntime componentRuntime;
    private final AtomicBoolean dataCollectionDefaultEnabled;
    private final AtomicBoolean deleted = new AtomicBoolean();
    private final List<IdTokenListener> idTokenListeners = new CopyOnWriteArrayList();
    private IdTokenListenersCountChangedListener idTokenListenersCountChangedListener;
    private final List<FirebaseAppLifecycleListener> lifecycleListeners = new CopyOnWriteArrayList();
    private final String name;
    private final FirebaseOptions options;
    private final Publisher publisher;
    private final SharedPreferences sharedPreferences;
    private InternalTokenProvider tokenProvider;

    @KeepForSdk
    /* compiled from: com.google.firebase:firebase-common@@16.1.0 */
    public interface BackgroundStateChangeListener {
        @KeepForSdk
        void onBackgroundStateChanged(boolean z);
    }

    @KeepForSdk
    @Deprecated
    /* compiled from: com.google.firebase:firebase-common@@16.1.0 */
    public interface IdTokenListener {
        @KeepForSdk
        void onIdTokenChanged(@NonNull InternalTokenResult internalTokenResult);
    }

    @KeepForSdk
    @Deprecated
    /* compiled from: com.google.firebase:firebase-common@@16.1.0 */
    public interface IdTokenListenersCountChangedListener {
        @KeepForSdk
        void onListenerCountChanged(int i);
    }

    @PublicApi
    @NonNull
    public Context getApplicationContext() {
        checkNotDeleted();
        return this.applicationContext;
    }

    @PublicApi
    @NonNull
    public String getName() {
        checkNotDeleted();
        return this.name;
    }

    @PublicApi
    @NonNull
    public FirebaseOptions getOptions() {
        checkNotDeleted();
        return this.options;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof FirebaseApp)) {
            return false;
        }
        return this.name.equals(((FirebaseApp) obj).getName());
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public String toString() {
        return Objects.toStringHelper(this).add(Constant.NAME, this.name).add("options", this.options).toString();
    }

    @PublicApi
    public static List<FirebaseApp> getApps(Context context) {
        ArrayList arrayList;
        synchronized (LOCK) {
            arrayList = new ArrayList(INSTANCES.values());
        }
        return arrayList;
    }

    @PublicApi
    @NonNull
    public static FirebaseApp getInstance() {
        FirebaseApp firebaseApp;
        synchronized (LOCK) {
            firebaseApp = INSTANCES.get(DEFAULT_APP_NAME);
            if (firebaseApp == null) {
                throw new IllegalStateException("Default FirebaseApp is not initialized in this process " + ProcessUtils.getMyProcessName() + ". Make sure to call FirebaseApp.initializeApp(Context) first.");
            }
        }
        return firebaseApp;
    }

    @PublicApi
    @NonNull
    public static FirebaseApp getInstance(@NonNull String str) {
        FirebaseApp firebaseApp;
        String str2;
        synchronized (LOCK) {
            firebaseApp = INSTANCES.get(normalize(str));
            if (firebaseApp == null) {
                List<String> allAppNames = getAllAppNames();
                if (allAppNames.isEmpty()) {
                    str2 = "";
                } else {
                    str2 = "Available app names: " + TextUtils.join(", ", allAppNames);
                }
                throw new IllegalStateException(String.format("FirebaseApp with name %s doesn't exist. %s", new Object[]{str, str2}));
            }
        }
        return firebaseApp;
    }

    @Nullable
    @PublicApi
    public static FirebaseApp initializeApp(@NonNull Context context) {
        synchronized (LOCK) {
            if (INSTANCES.containsKey(DEFAULT_APP_NAME)) {
                FirebaseApp instance = getInstance();
                return instance;
            }
            FirebaseOptions fromResource = FirebaseOptions.fromResource(context);
            if (fromResource == null) {
                Log.d(LOG_TAG, "Default FirebaseApp failed to initialize because no default options were found. This usually means that com.google.gms:google-services was not applied to your gradle project.");
                return null;
            }
            FirebaseApp initializeApp = initializeApp(context, fromResource);
            return initializeApp;
        }
    }

    @PublicApi
    @NonNull
    public static FirebaseApp initializeApp(@NonNull Context context, @NonNull FirebaseOptions firebaseOptions) {
        return initializeApp(context, firebaseOptions, DEFAULT_APP_NAME);
    }

    @PublicApi
    @NonNull
    public static FirebaseApp initializeApp(@NonNull Context context, @NonNull FirebaseOptions firebaseOptions, @NonNull String str) {
        FirebaseApp firebaseApp;
        GlobalBackgroundStateListener.ensureBackgroundStateListenerRegistered(context);
        String normalize = normalize(str);
        if (context.getApplicationContext() != null) {
            context = context.getApplicationContext();
        }
        synchronized (LOCK) {
            boolean z = !INSTANCES.containsKey(normalize);
            Preconditions.checkState(z, "FirebaseApp name " + normalize + " already exists!");
            Preconditions.checkNotNull(context, "Application context cannot be null.");
            firebaseApp = new FirebaseApp(context, normalize, firebaseOptions);
            INSTANCES.put(normalize, firebaseApp);
        }
        firebaseApp.initializeAllApis();
        return firebaseApp;
    }

    @KeepForSdk
    @Deprecated
    public void setTokenProvider(@NonNull InternalTokenProvider internalTokenProvider) {
        this.tokenProvider = (InternalTokenProvider) Preconditions.checkNotNull(internalTokenProvider);
    }

    @KeepForSdk
    @Deprecated
    public void setIdTokenListenersCountChangedListener(@NonNull IdTokenListenersCountChangedListener idTokenListenersCountChangedListener2) {
        this.idTokenListenersCountChangedListener = (IdTokenListenersCountChangedListener) Preconditions.checkNotNull(idTokenListenersCountChangedListener2);
        this.idTokenListenersCountChangedListener.onListenerCountChanged(this.idTokenListeners.size());
    }

    @Nullable
    @KeepForSdk
    @Deprecated
    public String getUid() throws FirebaseApiNotAvailableException {
        checkNotDeleted();
        InternalTokenProvider internalTokenProvider = this.tokenProvider;
        if (internalTokenProvider != null) {
            return internalTokenProvider.getUid();
        }
        throw new FirebaseApiNotAvailableException("firebase-auth is not linked, please fall back to unauthenticated mode.");
    }

    @NonNull
    @KeepForSdk
    @Deprecated
    public Task<GetTokenResult> getToken(boolean z) {
        checkNotDeleted();
        InternalTokenProvider internalTokenProvider = this.tokenProvider;
        if (internalTokenProvider == null) {
            return Tasks.forException(new FirebaseApiNotAvailableException("firebase-auth is not linked, please fall back to unauthenticated mode."));
        }
        return internalTokenProvider.getAccessToken(z);
    }

    @PublicApi
    public void delete() {
        if (this.deleted.compareAndSet(false, true)) {
            synchronized (LOCK) {
                INSTANCES.remove(this.name);
            }
            notifyOnAppDeleted();
        }
    }

    @KeepForSdk
    public <T> T get(Class<T> cls) {
        checkNotDeleted();
        return this.componentRuntime.get(cls);
    }

    @PublicApi
    public void setAutomaticResourceManagementEnabled(boolean z) {
        checkNotDeleted();
        if (this.automaticResourceManagementEnabled.compareAndSet(!z, z)) {
            boolean isInBackground = BackgroundDetector.getInstance().isInBackground();
            if (z && isInBackground) {
                notifyBackgroundStateChangeListeners(true);
            } else if (!z && isInBackground) {
                notifyBackgroundStateChangeListeners(false);
            }
        }
    }

    @KeepForSdk
    public boolean isDataCollectionDefaultEnabled() {
        checkNotDeleted();
        return this.dataCollectionDefaultEnabled.get();
    }

    @KeepForSdk
    public void setDataCollectionDefaultEnabled(boolean z) {
        checkNotDeleted();
        if (this.dataCollectionDefaultEnabled.compareAndSet(!z, z)) {
            this.sharedPreferences.edit().putBoolean(DATA_COLLECTION_DEFAULT_ENABLED, z).commit();
            this.publisher.publish(new Event(DataCollectionDefaultChange.class, new DataCollectionDefaultChange(z)));
        }
    }

    protected FirebaseApp(Context context, String str, FirebaseOptions firebaseOptions) {
        this.applicationContext = (Context) Preconditions.checkNotNull(context);
        this.name = Preconditions.checkNotEmpty(str);
        this.options = (FirebaseOptions) Preconditions.checkNotNull(firebaseOptions);
        this.idTokenListenersCountChangedListener = new DefaultIdTokenListenersCountChangedListener();
        this.sharedPreferences = context.getSharedPreferences(getSharedPrefsName(str), 0);
        this.dataCollectionDefaultEnabled = new AtomicBoolean(readAutoDataCollectionEnabled());
        List<ComponentRegistrar> discover = ComponentDiscovery.forContext(context).discover();
        this.componentRuntime = new ComponentRuntime(UI_EXECUTOR, discover, Component.m1156of(context, Context.class, new Class[0]), Component.m1156of(this, FirebaseApp.class, new Class[0]), Component.m1156of(firebaseOptions, FirebaseOptions.class, new Class[0]), LibraryVersionComponent.create(FIREBASE_ANDROID, ""), LibraryVersionComponent.create(FIREBASE_COMMON, BuildConfig.VERSION_NAME), DefaultUserAgentPublisher.component());
        this.publisher = (Publisher) this.componentRuntime.get(Publisher.class);
    }

    private static String getSharedPrefsName(String str) {
        return FIREBASE_APP_PREFS + str;
    }

    private boolean readAutoDataCollectionEnabled() {
        ApplicationInfo applicationInfo;
        if (this.sharedPreferences.contains(DATA_COLLECTION_DEFAULT_ENABLED)) {
            return this.sharedPreferences.getBoolean(DATA_COLLECTION_DEFAULT_ENABLED, true);
        }
        try {
            PackageManager packageManager = this.applicationContext.getPackageManager();
            if (!(packageManager == null || (applicationInfo = packageManager.getApplicationInfo(this.applicationContext.getPackageName(), 128)) == null || applicationInfo.metaData == null || !applicationInfo.metaData.containsKey(DATA_COLLECTION_DEFAULT_ENABLED))) {
                return applicationInfo.metaData.getBoolean(DATA_COLLECTION_DEFAULT_ENABLED);
            }
        } catch (PackageManager.NameNotFoundException unused) {
        }
        return true;
    }

    private void checkNotDeleted() {
        Preconditions.checkState(!this.deleted.get(), "FirebaseApp was deleted");
    }

    @KeepForSdk
    @Deprecated
    public List<IdTokenListener> getListeners() {
        checkNotDeleted();
        return this.idTokenListeners;
    }

    @VisibleForTesting
    @KeepForSdk
    public boolean isDefaultApp() {
        return DEFAULT_APP_NAME.equals(getName());
    }

    @UiThread
    @KeepForSdk
    @Deprecated
    public void notifyIdTokenListeners(@NonNull InternalTokenResult internalTokenResult) {
        Log.d(LOG_TAG, "Notifying auth state listeners.");
        int i = 0;
        for (IdTokenListener onIdTokenChanged : this.idTokenListeners) {
            onIdTokenChanged.onIdTokenChanged(internalTokenResult);
            i++;
        }
        Log.d(LOG_TAG, String.format("Notified %d auth state listeners.", new Object[]{Integer.valueOf(i)}));
    }

    /* access modifiers changed from: private */
    public void notifyBackgroundStateChangeListeners(boolean z) {
        Log.d(LOG_TAG, "Notifying background state change listeners.");
        for (BackgroundStateChangeListener onBackgroundStateChanged : this.backgroundStateChangeListeners) {
            onBackgroundStateChanged.onBackgroundStateChanged(z);
        }
    }

    @KeepForSdk
    @Deprecated
    public void addIdTokenListener(@NonNull IdTokenListener idTokenListener) {
        checkNotDeleted();
        Preconditions.checkNotNull(idTokenListener);
        this.idTokenListeners.add(idTokenListener);
        this.idTokenListenersCountChangedListener.onListenerCountChanged(this.idTokenListeners.size());
    }

    @KeepForSdk
    @Deprecated
    public void removeIdTokenListener(@NonNull IdTokenListener idTokenListener) {
        checkNotDeleted();
        Preconditions.checkNotNull(idTokenListener);
        this.idTokenListeners.remove(idTokenListener);
        this.idTokenListenersCountChangedListener.onListenerCountChanged(this.idTokenListeners.size());
    }

    @KeepForSdk
    public void addBackgroundStateChangeListener(BackgroundStateChangeListener backgroundStateChangeListener) {
        checkNotDeleted();
        if (this.automaticResourceManagementEnabled.get() && BackgroundDetector.getInstance().isInBackground()) {
            backgroundStateChangeListener.onBackgroundStateChanged(true);
        }
        this.backgroundStateChangeListeners.add(backgroundStateChangeListener);
    }

    @KeepForSdk
    public void removeBackgroundStateChangeListener(BackgroundStateChangeListener backgroundStateChangeListener) {
        checkNotDeleted();
        this.backgroundStateChangeListeners.remove(backgroundStateChangeListener);
    }

    @KeepForSdk
    public String getPersistenceKey() {
        return Base64Utils.encodeUrlSafeNoPadding(getName().getBytes(Charset.defaultCharset())) + MqttTopic.SINGLE_LEVEL_WILDCARD + Base64Utils.encodeUrlSafeNoPadding(getOptions().getApplicationId().getBytes(Charset.defaultCharset()));
    }

    @KeepForSdk
    public void addLifecycleEventListener(@NonNull FirebaseAppLifecycleListener firebaseAppLifecycleListener) {
        checkNotDeleted();
        Preconditions.checkNotNull(firebaseAppLifecycleListener);
        this.lifecycleListeners.add(firebaseAppLifecycleListener);
    }

    @KeepForSdk
    public void removeLifecycleEventListener(@NonNull FirebaseAppLifecycleListener firebaseAppLifecycleListener) {
        checkNotDeleted();
        Preconditions.checkNotNull(firebaseAppLifecycleListener);
        this.lifecycleListeners.remove(firebaseAppLifecycleListener);
    }

    private void notifyOnAppDeleted() {
        for (FirebaseAppLifecycleListener onDeleted : this.lifecycleListeners) {
            onDeleted.onDeleted(this.name, this.options);
        }
    }

    @VisibleForTesting
    public static void clearInstancesForTest() {
        synchronized (LOCK) {
            INSTANCES.clear();
        }
    }

    @KeepForSdk
    public static String getPersistenceKey(String str, FirebaseOptions firebaseOptions) {
        return Base64Utils.encodeUrlSafeNoPadding(str.getBytes(Charset.defaultCharset())) + MqttTopic.SINGLE_LEVEL_WILDCARD + Base64Utils.encodeUrlSafeNoPadding(firebaseOptions.getApplicationId().getBytes(Charset.defaultCharset()));
    }

    private static List<String> getAllAppNames() {
        ArrayList arrayList = new ArrayList();
        synchronized (LOCK) {
            for (FirebaseApp name2 : INSTANCES.values()) {
                arrayList.add(name2.getName());
            }
        }
        Collections.sort(arrayList);
        return arrayList;
    }

    /* access modifiers changed from: private */
    public void initializeAllApis() {
        boolean isDeviceProtectedStorage = ContextCompat.isDeviceProtectedStorage(this.applicationContext);
        if (isDeviceProtectedStorage) {
            UserUnlockReceiver.ensureReceiverRegistered(this.applicationContext);
        } else {
            this.componentRuntime.initializeEagerComponents(isDefaultApp());
        }
        initializeApis(FirebaseApp.class, this, API_INITIALIZERS, isDeviceProtectedStorage);
        if (isDefaultApp()) {
            initializeApis(FirebaseApp.class, this, DEFAULT_APP_API_INITITALIZERS, isDeviceProtectedStorage);
            initializeApis(Context.class, this.applicationContext, DEFAULT_CONTEXT_API_INITITALIZERS, isDeviceProtectedStorage);
        }
    }

    private <T> void initializeApis(Class<T> cls, T t, Iterable<String> iterable, boolean z) {
        for (String next : iterable) {
            if (z) {
                try {
                    if (!DIRECT_BOOT_COMPATIBLE_API_INITIALIZERS.contains(next)) {
                    }
                } catch (ClassNotFoundException unused) {
                    if (!CORE_CLASSES.contains(next)) {
                        Log.d(LOG_TAG, next + " is not linked. Skipping initialization.");
                    } else {
                        throw new IllegalStateException(next + " is missing, but is required. Check if it has been removed by Proguard.");
                    }
                } catch (NoSuchMethodException unused2) {
                    throw new IllegalStateException(next + "#getInstance has been removed by Proguard. Add keep rule to prevent it.");
                } catch (InvocationTargetException e) {
                    Log.wtf(LOG_TAG, "Firebase API initialization failure.", e);
                } catch (IllegalAccessException e2) {
                    Log.wtf(LOG_TAG, "Failed to initialize " + next, e2);
                }
            }
            Method method = Class.forName(next).getMethod("getInstance", new Class[]{cls});
            int modifiers = method.getModifiers();
            if (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers)) {
                method.invoke((Object) null, new Object[]{t});
            }
        }
    }

    private static String normalize(@NonNull String str) {
        return str.trim();
    }

    @TargetApi(24)
    /* compiled from: com.google.firebase:firebase-common@@16.1.0 */
    private static class UserUnlockReceiver extends BroadcastReceiver {
        private static AtomicReference<UserUnlockReceiver> INSTANCE = new AtomicReference<>();
        private final Context applicationContext;

        public UserUnlockReceiver(Context context) {
            this.applicationContext = context;
        }

        /* access modifiers changed from: private */
        public static void ensureReceiverRegistered(Context context) {
            if (INSTANCE.get() == null) {
                UserUnlockReceiver userUnlockReceiver = new UserUnlockReceiver(context);
                if (INSTANCE.compareAndSet((Object) null, userUnlockReceiver)) {
                    context.registerReceiver(userUnlockReceiver, new IntentFilter("android.intent.action.USER_UNLOCKED"));
                }
            }
        }

        public void onReceive(Context context, Intent intent) {
            synchronized (FirebaseApp.LOCK) {
                for (FirebaseApp access$400 : FirebaseApp.INSTANCES.values()) {
                    access$400.initializeAllApis();
                }
            }
            unregister();
        }

        public void unregister() {
            this.applicationContext.unregisterReceiver(this);
        }
    }

    @TargetApi(14)
    /* compiled from: com.google.firebase:firebase-common@@16.1.0 */
    private static class GlobalBackgroundStateListener implements BackgroundDetector.BackgroundStateChangeListener {
        private static AtomicReference<GlobalBackgroundStateListener> INSTANCE = new AtomicReference<>();

        private GlobalBackgroundStateListener() {
        }

        /* access modifiers changed from: private */
        public static void ensureBackgroundStateListenerRegistered(Context context) {
            if (PlatformVersion.isAtLeastIceCreamSandwich() && (context.getApplicationContext() instanceof Application)) {
                Application application = (Application) context.getApplicationContext();
                if (INSTANCE.get() == null) {
                    GlobalBackgroundStateListener globalBackgroundStateListener = new GlobalBackgroundStateListener();
                    if (INSTANCE.compareAndSet((Object) null, globalBackgroundStateListener)) {
                        BackgroundDetector.initialize(application);
                        BackgroundDetector.getInstance().addListener(globalBackgroundStateListener);
                    }
                }
            }
        }

        public void onBackgroundStateChanged(boolean z) {
            synchronized (FirebaseApp.LOCK) {
                Iterator it = new ArrayList(FirebaseApp.INSTANCES.values()).iterator();
                while (it.hasNext()) {
                    FirebaseApp firebaseApp = (FirebaseApp) it.next();
                    if (firebaseApp.automaticResourceManagementEnabled.get()) {
                        firebaseApp.notifyBackgroundStateChangeListeners(z);
                    }
                }
            }
        }
    }

    /* compiled from: com.google.firebase:firebase-common@@16.1.0 */
    private static class UiExecutor implements Executor {
        private static final Handler HANDLER = new Handler(Looper.getMainLooper());

        private UiExecutor() {
        }

        public void execute(@NonNull Runnable runnable) {
            HANDLER.post(runnable);
        }
    }
}
