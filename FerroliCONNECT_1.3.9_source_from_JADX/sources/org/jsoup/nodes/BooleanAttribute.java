package org.jsoup.nodes;

public class BooleanAttribute extends Attribute {
    /* access modifiers changed from: protected */
    public boolean isBooleanAttribute() {
        return true;
    }

    public BooleanAttribute(String str) {
        super(str, "");
    }
}
