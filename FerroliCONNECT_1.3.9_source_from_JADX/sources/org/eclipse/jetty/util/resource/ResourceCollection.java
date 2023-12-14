package org.eclipse.jetty.util.resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.StringTokenizer;

public class ResourceCollection extends Resource {
    private Resource[] _resources;

    public boolean isContainedIn(Resource resource) throws MalformedURLException {
        return false;
    }

    public long length() {
        return -1;
    }

    public ResourceCollection() {
        this._resources = new Resource[0];
    }

    public ResourceCollection(Resource... resourceArr) {
        ArrayList arrayList = new ArrayList();
        for (ResourceCollection resourceCollection : resourceArr) {
            if (resourceCollection != null) {
                if (resourceCollection instanceof ResourceCollection) {
                    for (Resource add : resourceCollection.getResources()) {
                        arrayList.add(add);
                    }
                } else {
                    arrayList.add(resourceCollection);
                }
            }
        }
        this._resources = (Resource[]) arrayList.toArray(new Resource[arrayList.size()]);
        for (Resource resource : this._resources) {
            if (!resource.exists() || !resource.isDirectory()) {
                throw new IllegalArgumentException(resource + " is not an existing directory.");
            }
        }
    }

    public ResourceCollection(String[] strArr) {
        this._resources = new Resource[strArr.length];
        int i = 0;
        while (i < strArr.length) {
            try {
                this._resources[i] = Resource.newResource(strArr[i]);
                if (!this._resources[i].exists() || !this._resources[i].isDirectory()) {
                    throw new IllegalArgumentException(this._resources[i] + " is not an existing directory.");
                }
                i++;
            } catch (IllegalArgumentException e) {
                throw e;
            } catch (Exception e2) {
                throw new RuntimeException(e2);
            }
        }
    }

    public ResourceCollection(String str) {
        setResourcesAsCSV(str);
    }

    public Resource[] getResources() {
        return this._resources;
    }

    public void setResources(Resource[] resourceArr) {
        if (resourceArr == null) {
            resourceArr = new Resource[0];
        }
        this._resources = resourceArr;
    }

    public void setResourcesAsCSV(String str) {
        StringTokenizer stringTokenizer = new StringTokenizer(str, ",;");
        int countTokens = stringTokenizer.countTokens();
        if (countTokens != 0) {
            this._resources = new Resource[countTokens];
            int i = 0;
            while (stringTokenizer.hasMoreTokens()) {
                try {
                    this._resources[i] = Resource.newResource(stringTokenizer.nextToken().trim());
                    if (!this._resources[i].exists() || !this._resources[i].isDirectory()) {
                        throw new IllegalArgumentException(this._resources[i] + " is not an existing directory.");
                    }
                    i++;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            return;
        }
        throw new IllegalArgumentException("ResourceCollection@setResourcesAsCSV(String)  argument must be a string containing one or more comma-separated resource strings.");
    }

    public Resource addPath(String str) throws IOException, MalformedURLException {
        if (this._resources == null) {
            throw new IllegalStateException("*resources* not set.");
        } else if (str == null) {
            throw new MalformedURLException();
        } else if (str.length() == 0 || "/".equals(str)) {
            return this;
        } else {
            int i = 0;
            Resource resource = null;
            while (true) {
                Resource[] resourceArr = this._resources;
                if (i >= resourceArr.length) {
                    break;
                }
                resource = resourceArr[i].addPath(str);
                if (!resource.exists()) {
                    i++;
                } else if (!resource.isDirectory()) {
                    return resource;
                }
            }
            int i2 = i + 1;
            ArrayList arrayList = null;
            while (true) {
                Resource[] resourceArr2 = this._resources;
                if (i2 >= resourceArr2.length) {
                    break;
                }
                Resource addPath = resourceArr2[i2].addPath(str);
                if (addPath.exists() && addPath.isDirectory()) {
                    if (resource != null) {
                        arrayList = new ArrayList();
                        arrayList.add(resource);
                        resource = null;
                    }
                    arrayList.add(addPath);
                }
                i2++;
            }
            if (resource != null) {
                return resource;
            }
            if (arrayList != null) {
                return new ResourceCollection((Resource[]) arrayList.toArray(new Resource[arrayList.size()]));
            }
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public Object findResource(String str) throws IOException, MalformedURLException {
        int i = 0;
        Resource resource = null;
        while (true) {
            Resource[] resourceArr = this._resources;
            if (i >= resourceArr.length) {
                break;
            }
            resource = resourceArr[i].addPath(str);
            if (!resource.exists()) {
                i++;
            } else if (!resource.isDirectory()) {
                return resource;
            }
        }
        int i2 = i + 1;
        ArrayList arrayList = null;
        while (true) {
            Resource[] resourceArr2 = this._resources;
            if (i2 >= resourceArr2.length) {
                break;
            }
            Resource addPath = resourceArr2[i2].addPath(str);
            if (addPath.exists() && addPath.isDirectory()) {
                if (resource != null) {
                    arrayList = new ArrayList();
                    arrayList.add(resource);
                }
                arrayList.add(addPath);
            }
            i2++;
        }
        if (resource != null) {
            return resource;
        }
        if (arrayList != null) {
            return arrayList;
        }
        return null;
    }

    public boolean delete() throws SecurityException {
        throw new UnsupportedOperationException();
    }

    public boolean exists() {
        if (this._resources != null) {
            return true;
        }
        throw new IllegalStateException("*resources* not set.");
    }

    public File getFile() throws IOException {
        Resource[] resourceArr = this._resources;
        if (resourceArr != null) {
            for (Resource file : resourceArr) {
                File file2 = file.getFile();
                if (file2 != null) {
                    return file2;
                }
            }
            return null;
        }
        throw new IllegalStateException("*resources* not set.");
    }

    public InputStream getInputStream() throws IOException {
        Resource[] resourceArr = this._resources;
        if (resourceArr != null) {
            for (Resource inputStream : resourceArr) {
                InputStream inputStream2 = inputStream.getInputStream();
                if (inputStream2 != null) {
                    return inputStream2;
                }
            }
            return null;
        }
        throw new IllegalStateException("*resources* not set.");
    }

    public String getName() {
        Resource[] resourceArr = this._resources;
        if (resourceArr != null) {
            for (Resource name : resourceArr) {
                String name2 = name.getName();
                if (name2 != null) {
                    return name2;
                }
            }
            return null;
        }
        throw new IllegalStateException("*resources* not set.");
    }

    public OutputStream getOutputStream() throws IOException, SecurityException {
        Resource[] resourceArr = this._resources;
        if (resourceArr != null) {
            for (Resource outputStream : resourceArr) {
                OutputStream outputStream2 = outputStream.getOutputStream();
                if (outputStream2 != null) {
                    return outputStream2;
                }
            }
            return null;
        }
        throw new IllegalStateException("*resources* not set.");
    }

    public URL getURL() {
        Resource[] resourceArr = this._resources;
        if (resourceArr != null) {
            for (Resource url : resourceArr) {
                URL url2 = url.getURL();
                if (url2 != null) {
                    return url2;
                }
            }
            return null;
        }
        throw new IllegalStateException("*resources* not set.");
    }

    public boolean isDirectory() {
        if (this._resources != null) {
            return true;
        }
        throw new IllegalStateException("*resources* not set.");
    }

    public long lastModified() {
        Resource[] resourceArr = this._resources;
        if (resourceArr != null) {
            for (Resource lastModified : resourceArr) {
                long lastModified2 = lastModified.lastModified();
                if (lastModified2 != -1) {
                    return lastModified2;
                }
            }
            return -1;
        }
        throw new IllegalStateException("*resources* not set.");
    }

    public String[] list() {
        if (this._resources != null) {
            HashSet hashSet = new HashSet();
            for (Resource list : this._resources) {
                for (String add : list.list()) {
                    hashSet.add(add);
                }
            }
            String[] strArr = (String[]) hashSet.toArray(new String[hashSet.size()]);
            Arrays.sort(strArr);
            return strArr;
        }
        throw new IllegalStateException("*resources* not set.");
    }

    public void release() {
        Resource[] resourceArr = this._resources;
        if (resourceArr != null) {
            for (Resource release : resourceArr) {
                release.release();
            }
            return;
        }
        throw new IllegalStateException("*resources* not set.");
    }

    public boolean renameTo(Resource resource) throws SecurityException {
        throw new UnsupportedOperationException();
    }

    public void copyTo(File file) throws IOException {
        int length = this._resources.length;
        while (true) {
            int i = length - 1;
            if (length > 0) {
                this._resources[i].copyTo(file);
                length = i;
            } else {
                return;
            }
        }
    }

    public String toString() {
        Resource[] resourceArr = this._resources;
        if (resourceArr == null) {
            return "[]";
        }
        return String.valueOf(Arrays.asList(resourceArr));
    }
}
