package com.google.firebase.components;

import com.google.firebase.inject.Provider;
import java.util.Set;

/* compiled from: com.google.firebase:firebase-common@@16.1.0 */
final /* synthetic */ class ComponentRuntime$$Lambda$4 implements Provider {
    private final Set arg$1;

    private ComponentRuntime$$Lambda$4(Set set) {
        this.arg$1 = set;
    }

    public static Provider lambdaFactory$(Set set) {
        return new ComponentRuntime$$Lambda$4(set);
    }

    public Object get() {
        return ComponentRuntime.lambda$processSetComponents$1(this.arg$1);
    }
}
