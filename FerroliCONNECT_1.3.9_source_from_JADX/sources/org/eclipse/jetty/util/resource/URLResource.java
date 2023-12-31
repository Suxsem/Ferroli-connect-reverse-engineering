package org.eclipse.jetty.util.resource;

import java.io.File;
import java.io.FilePermission;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.Permission;
import org.eclipse.jetty.util.URIUtil;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

public class URLResource extends Resource {
    private static final Logger LOG = Log.getLogger((Class<?>) URLResource.class);
    protected URLConnection _connection;
    protected InputStream _in;
    protected URL _url;
    protected String _urlString;
    transient boolean _useCaches;

    public boolean isContainedIn(Resource resource) throws MalformedURLException {
        return false;
    }

    public String[] list() {
        return null;
    }

    protected URLResource(URL url, URLConnection uRLConnection) {
        this._in = null;
        this._useCaches = Resource.__defaultUseCaches;
        this._url = url;
        this._urlString = this._url.toString();
        this._connection = uRLConnection;
    }

    protected URLResource(URL url, URLConnection uRLConnection, boolean z) {
        this(url, uRLConnection);
        this._useCaches = z;
    }

    /* access modifiers changed from: protected */
    public synchronized boolean checkConnection() {
        if (this._connection == null) {
            try {
                this._connection = this._url.openConnection();
                this._connection.setUseCaches(this._useCaches);
            } catch (IOException e) {
                LOG.ignore(e);
            }
        }
        return this._connection != null;
    }

    public synchronized void release() {
        if (this._in != null) {
            try {
                this._in.close();
            } catch (IOException e) {
                LOG.ignore(e);
            }
            this._in = null;
        }
        if (this._connection != null) {
            this._connection = null;
        }
    }

    public boolean exists() {
        try {
            synchronized (this) {
                if (checkConnection() && this._in == null) {
                    this._in = this._connection.getInputStream();
                }
            }
        } catch (IOException e) {
            LOG.ignore(e);
        }
        return this._in != null;
    }

    public boolean isDirectory() {
        return exists() && this._url.toString().endsWith("/");
    }

    public long lastModified() {
        if (checkConnection()) {
            return this._connection.getLastModified();
        }
        return -1;
    }

    public long length() {
        if (checkConnection()) {
            return (long) this._connection.getContentLength();
        }
        return -1;
    }

    public URL getURL() {
        return this._url;
    }

    public File getFile() throws IOException {
        if (checkConnection()) {
            Permission permission = this._connection.getPermission();
            if (permission instanceof FilePermission) {
                return new File(permission.getName());
            }
        }
        try {
            return new File(this._url.getFile());
        } catch (Exception e) {
            LOG.ignore(e);
            return null;
        }
    }

    public String getName() {
        return this._url.toExternalForm();
    }

    public synchronized InputStream getInputStream() throws IOException {
        if (checkConnection()) {
            try {
                if (this._in != null) {
                    InputStream inputStream = this._in;
                    this._in = null;
                    return inputStream;
                }
                InputStream inputStream2 = this._connection.getInputStream();
                this._connection = null;
                return inputStream2;
            } finally {
                this._connection = null;
            }
        } else {
            throw new IOException("Invalid resource");
        }
    }

    public OutputStream getOutputStream() throws IOException, SecurityException {
        throw new IOException("Output not supported");
    }

    public boolean delete() throws SecurityException {
        throw new SecurityException("Delete not supported");
    }

    public boolean renameTo(Resource resource) throws SecurityException {
        throw new SecurityException("RenameTo not supported");
    }

    public Resource addPath(String str) throws IOException, MalformedURLException {
        if (str == null) {
            return null;
        }
        return newResource(URIUtil.addPaths(this._url.toExternalForm(), URIUtil.canonicalPath(str)));
    }

    public String toString() {
        return this._urlString;
    }

    public int hashCode() {
        return this._urlString.hashCode();
    }

    public boolean equals(Object obj) {
        return (obj instanceof URLResource) && this._urlString.equals(((URLResource) obj)._urlString);
    }

    public boolean getUseCaches() {
        return this._useCaches;
    }
}
