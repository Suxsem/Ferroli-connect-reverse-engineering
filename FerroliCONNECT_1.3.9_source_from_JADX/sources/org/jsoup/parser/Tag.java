package org.jsoup.parser;

import android.support.p000v4.app.NotificationCompat;
import anetwork.channel.util.RequestConstant;
import com.szacs.ferroliconnect.util.LanguageUtil;
import com.taobao.accs.common.Constants;
import java.util.HashMap;
import java.util.Map;
import org.android.agoo.common.AgooConstants;
import org.jsoup.helper.Validate;

public class Tag {
    private static final String[] blockTags = {"html", "head", AgooConstants.MESSAGE_BODY, "frameset", "script", "noscript", "style", "meta", "link", "title", "frame", "noframes", "section", "nav", "aside", "hgroup", "header", "footer", "p", "h1", "h2", "h3", "h4", "h5", "h6", "ul", "ol", RequestConstant.ENV_PRE, "div", "blockquote", "hr", "address", "figure", "figcaption", "form", "fieldset", "ins", "del", "s", "dl", "dt", "dd", "li", "table", "caption", "thead", "tfoot", "tbody", "colgroup", "col", LanguageUtil.LOGOGRAM_TR, "th", "td", "video", "audio", "canvas", "details", "menu", "plaintext", "template", "article", "main", "svg", "math"};
    private static final String[] emptyTags = {"meta", "link", "base", "frame", "img", "br", "wbr", "embed", "hr", "input", "keygen", "col", "command", "device", "area", "basefont", "bgsound", "menuitem", "param", "source", "track"};
    private static final String[] formListedTags = {"button", "fieldset", "input", "keygen", "object", "output", "select", "textarea"};
    private static final String[] formSubmitTags = {"input", "keygen", "object", "select", "textarea"};
    private static final String[] formatAsInlineTags = {"title", "a", "p", "h1", "h2", "h3", "h4", "h5", "h6", RequestConstant.ENV_PRE, "address", "li", "th", "td", "script", "style", "ins", "del", "s"};
    private static final String[] inlineTags = {"object", "base", "font", "tt", "i", "b", "u", "big", "small", "em", "strong", "dfn", "code", "samp", "kbd", "var", "cite", "abbr", AgooConstants.MESSAGE_TIME, "acronym", "mark", "ruby", "rt", "rp", "a", "img", "br", "wbr", "map", "q", "sub", "sup", "bdo", "iframe", "embed", "span", "input", "select", "textarea", "label", "button", "optgroup", "option", "legend", "datalist", "keygen", "output", NotificationCompat.CATEGORY_PROGRESS, "meter", "area", "param", "source", "track", "summary", "command", "device", "area", "basefont", "bgsound", "menuitem", "param", "source", "track", Constants.KEY_DATA, "bdi"};
    private static final String[] preserveWhitespaceTags = {RequestConstant.ENV_PRE, "plaintext", "title", "textarea"};
    private static final Map<String, Tag> tags = new HashMap();
    private boolean canContainBlock = true;
    private boolean canContainInline = true;
    private boolean empty = false;
    private boolean formList = false;
    private boolean formSubmit = false;
    private boolean formatAsBlock = true;
    private boolean isBlock = true;
    private boolean preserveWhitespace = false;
    private boolean selfClosing = false;
    private String tagName;

    static {
        for (String tag : blockTags) {
            register(new Tag(tag));
        }
        for (String tag2 : inlineTags) {
            Tag tag3 = new Tag(tag2);
            tag3.isBlock = false;
            tag3.canContainBlock = false;
            tag3.formatAsBlock = false;
            register(tag3);
        }
        for (String str : emptyTags) {
            Tag tag4 = tags.get(str);
            Validate.notNull(tag4);
            tag4.canContainBlock = false;
            tag4.canContainInline = false;
            tag4.empty = true;
        }
        for (String str2 : formatAsInlineTags) {
            Tag tag5 = tags.get(str2);
            Validate.notNull(tag5);
            tag5.formatAsBlock = false;
        }
        for (String str3 : preserveWhitespaceTags) {
            Tag tag6 = tags.get(str3);
            Validate.notNull(tag6);
            tag6.preserveWhitespace = true;
        }
        for (String str4 : formListedTags) {
            Tag tag7 = tags.get(str4);
            Validate.notNull(tag7);
            tag7.formList = true;
        }
        for (String str5 : formSubmitTags) {
            Tag tag8 = tags.get(str5);
            Validate.notNull(tag8);
            tag8.formSubmit = true;
        }
    }

    private Tag(String str) {
        this.tagName = str;
    }

    public String getName() {
        return this.tagName;
    }

    public static Tag valueOf(String str, ParseSettings parseSettings) {
        Validate.notNull(str);
        Tag tag = tags.get(str);
        if (tag != null) {
            return tag;
        }
        String normalizeTag = parseSettings.normalizeTag(str);
        Validate.notEmpty(normalizeTag);
        Tag tag2 = tags.get(normalizeTag);
        if (tag2 != null) {
            return tag2;
        }
        Tag tag3 = new Tag(normalizeTag);
        tag3.isBlock = false;
        tag3.canContainBlock = true;
        return tag3;
    }

    public static Tag valueOf(String str) {
        return valueOf(str, ParseSettings.preserveCase);
    }

    public boolean isBlock() {
        return this.isBlock;
    }

    public boolean formatAsBlock() {
        return this.formatAsBlock;
    }

    public boolean canContainBlock() {
        return this.canContainBlock;
    }

    public boolean isInline() {
        return !this.isBlock;
    }

    public boolean isData() {
        return !this.canContainInline && !isEmpty();
    }

    public boolean isEmpty() {
        return this.empty;
    }

    public boolean isSelfClosing() {
        return this.empty || this.selfClosing;
    }

    public boolean isKnownTag() {
        return tags.containsKey(this.tagName);
    }

    public static boolean isKnownTag(String str) {
        return tags.containsKey(str);
    }

    public boolean preserveWhitespace() {
        return this.preserveWhitespace;
    }

    public boolean isFormListed() {
        return this.formList;
    }

    public boolean isFormSubmittable() {
        return this.formSubmit;
    }

    /* access modifiers changed from: package-private */
    public Tag setSelfClosing() {
        this.selfClosing = true;
        return this;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Tag)) {
            return false;
        }
        Tag tag = (Tag) obj;
        if (this.tagName.equals(tag.tagName) && this.canContainBlock == tag.canContainBlock && this.canContainInline == tag.canContainInline && this.empty == tag.empty && this.formatAsBlock == tag.formatAsBlock && this.isBlock == tag.isBlock && this.preserveWhitespace == tag.preserveWhitespace && this.selfClosing == tag.selfClosing && this.formList == tag.formList && this.formSubmit == tag.formSubmit) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((((((((((((((((this.tagName.hashCode() * 31) + (this.isBlock ? 1 : 0)) * 31) + (this.formatAsBlock ? 1 : 0)) * 31) + (this.canContainBlock ? 1 : 0)) * 31) + (this.canContainInline ? 1 : 0)) * 31) + (this.empty ? 1 : 0)) * 31) + (this.selfClosing ? 1 : 0)) * 31) + (this.preserveWhitespace ? 1 : 0)) * 31) + (this.formList ? 1 : 0)) * 31) + (this.formSubmit ? 1 : 0);
    }

    public String toString() {
        return this.tagName;
    }

    private static void register(Tag tag) {
        tags.put(tag.tagName, tag);
    }
}
