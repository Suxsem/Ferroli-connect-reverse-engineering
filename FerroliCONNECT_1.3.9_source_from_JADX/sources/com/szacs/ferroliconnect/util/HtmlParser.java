package com.szacs.ferroliconnect.util;

import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import java.util.ArrayDeque;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class HtmlParser implements Html.TagHandler, ContentHandler {
    private final TagHandler handler;
    private ArrayDeque<Boolean> tagStatus = new ArrayDeque<>();
    private Editable text;
    private ContentHandler wrapped;

    public interface TagHandler {
        boolean handleTag(boolean z, String str, Editable editable, Attributes attributes);
    }

    public static Spanned buildSpannedText(String str, TagHandler tagHandler) {
        return Html.fromHtml("<inject/>" + str, (Html.ImageGetter) null, new HtmlParser(tagHandler));
    }

    public static String getValue(Attributes attributes, String str) {
        int length = attributes.getLength();
        for (int i = 0; i < length; i++) {
            if (str.equals(attributes.getLocalName(i))) {
                return attributes.getValue(i);
            }
        }
        return null;
    }

    private HtmlParser(TagHandler tagHandler) {
        this.handler = tagHandler;
    }

    public void handleTag(boolean z, String str, Editable editable, XMLReader xMLReader) {
        if (this.wrapped == null) {
            this.text = editable;
            this.wrapped = xMLReader.getContentHandler();
            xMLReader.setContentHandler(this);
            this.tagStatus.addLast(Boolean.FALSE);
        }
    }

    public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
        boolean handleTag = this.handler.handleTag(true, str2, this.text, attributes);
        this.tagStatus.addLast(Boolean.valueOf(handleTag));
        if (!handleTag) {
            this.wrapped.startElement(str, str2, str3, attributes);
        }
    }

    public void endElement(String str, String str2, String str3) throws SAXException {
        if (!this.tagStatus.removeLast().booleanValue()) {
            this.wrapped.endElement(str, str2, str3);
        }
        this.handler.handleTag(false, str2, this.text, (Attributes) null);
    }

    public void setDocumentLocator(Locator locator) {
        this.wrapped.setDocumentLocator(locator);
    }

    public void startDocument() throws SAXException {
        this.wrapped.startDocument();
    }

    public void endDocument() throws SAXException {
        this.wrapped.endDocument();
    }

    public void startPrefixMapping(String str, String str2) throws SAXException {
        this.wrapped.startPrefixMapping(str, str2);
    }

    public void endPrefixMapping(String str) throws SAXException {
        this.wrapped.endPrefixMapping(str);
    }

    public void characters(char[] cArr, int i, int i2) throws SAXException {
        this.wrapped.characters(cArr, i, i2);
    }

    public void ignorableWhitespace(char[] cArr, int i, int i2) throws SAXException {
        this.wrapped.ignorableWhitespace(cArr, i, i2);
    }

    public void processingInstruction(String str, String str2) throws SAXException {
        this.wrapped.processingInstruction(str, str2);
    }

    public void skippedEntity(String str) throws SAXException {
        this.wrapped.skippedEntity(str);
    }
}
