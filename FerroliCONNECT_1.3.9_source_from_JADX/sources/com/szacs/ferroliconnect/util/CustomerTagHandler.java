package com.szacs.ferroliconnect.util;

import android.content.Context;
import android.text.Editable;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StrikethroughSpan;
import com.szacs.ferroliconnect.MainApplication;
import com.szacs.ferroliconnect.util.HtmlParser;
import java.util.Stack;
import org.xml.sax.Attributes;

public class CustomerTagHandler implements HtmlParser.TagHandler {
    private Stack<String> propertyValue;
    private Stack<Integer> startIndex;

    public boolean handleTag(boolean z, String str, Editable editable, Attributes attributes) {
        if (z) {
            handlerStartTAG(str, editable, attributes);
        } else {
            handlerEndTAG(str, editable, attributes);
        }
        return handlerBYDefault(str);
    }

    private void handlerStartTAG(String str, Editable editable, Attributes attributes) {
        if (str.equalsIgnoreCase("font")) {
            handlerStartFONT(editable, attributes);
        } else if (str.equalsIgnoreCase("del")) {
            handlerStartDEL(editable);
        }
    }

    private void handlerEndTAG(String str, Editable editable, Attributes attributes) {
        if (str.equalsIgnoreCase("font")) {
            handlerEndFONT(editable);
        } else if (str.equalsIgnoreCase("del")) {
            handlerEndDEL(editable);
        }
    }

    private void handlerStartFONT(Editable editable, Attributes attributes) {
        if (this.startIndex == null) {
            this.startIndex = new Stack<>();
        }
        this.startIndex.push(Integer.valueOf(editable.length()));
        if (this.propertyValue == null) {
            this.propertyValue = new Stack<>();
        }
        this.propertyValue.push(HtmlParser.getValue(attributes, "size"));
    }

    private void handlerEndFONT(Editable editable) {
        Stack<String> stack = this.propertyValue;
        if (stack != null && !stack.empty()) {
            try {
                editable.setSpan(new AbsoluteSizeSpan(sp2px(MainApplication.getInstance(), (float) Integer.parseInt(this.propertyValue.pop()))), this.startIndex.pop().intValue(), editable.length(), 33);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private int sp2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().scaledDensity) + 0.5f);
    }

    private void handlerStartDEL(Editable editable) {
        if (this.startIndex == null) {
            this.startIndex = new Stack<>();
        }
        this.startIndex.push(Integer.valueOf(editable.length()));
    }

    private void handlerEndDEL(Editable editable) {
        editable.setSpan(new StrikethroughSpan(), this.startIndex.pop().intValue(), editable.length(), 33);
    }

    private boolean handlerBYDefault(String str) {
        return str.equalsIgnoreCase("del");
    }
}
