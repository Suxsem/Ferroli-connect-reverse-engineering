package org.eclipse.jetty.webapp;

import java.net.URL;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.xml.XmlParser;

public abstract class Descriptor {
    protected XmlParser _parser;
    protected XmlParser.Node _root;
    protected boolean _validating;
    protected Resource _xml;

    public abstract void ensureParser() throws ClassNotFoundException;

    public abstract XmlParser newParser() throws ClassNotFoundException;

    public Descriptor(Resource resource) {
        this._xml = resource;
    }

    /* access modifiers changed from: protected */
    public void redirect(XmlParser xmlParser, String str, URL url) {
        if (url != null) {
            xmlParser.redirectEntity(str, url);
        }
    }

    public void setValidating(boolean z) {
        this._validating = z;
    }

    public void parse() throws Exception {
        if (this._parser == null) {
            ensureParser();
        }
        if (this._root == null) {
            try {
                this._root = this._parser.parse(this._xml.getInputStream());
            } finally {
                this._xml.release();
            }
        }
    }

    public Resource getResource() {
        return this._xml;
    }

    public XmlParser.Node getRoot() {
        return this._root;
    }

    public String toString() {
        return getClass().getSimpleName() + "(" + this._xml + ")";
    }
}
