package com.szacs.ferroliconnect.util;

import android.text.Editable;
import android.text.Html;
import android.text.style.AbsoluteSizeSpan;
import org.xml.sax.XMLReader;

public class SizeLabel implements Html.TagHandler {
    private int size;

    public SizeLabel(int i) {
        this.size = i;
    }

    public void handleTag(boolean z, String str, Editable editable, XMLReader xMLReader) {
        if (str.toLowerCase().equals("size") && !z) {
            editable.setSpan(new AbsoluteSizeSpan(this.size), 0, editable.length(), 33);
        }
    }
}
