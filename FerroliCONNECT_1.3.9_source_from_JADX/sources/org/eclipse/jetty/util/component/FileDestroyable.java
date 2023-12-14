package org.eclipse.jetty.util.component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.eclipse.jetty.util.C2439IO;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.eclipse.jetty.util.resource.Resource;

public class FileDestroyable implements Destroyable {
    private static final Logger LOG = Log.getLogger((Class<?>) FileDestroyable.class);
    final List<File> _files = new ArrayList();

    public FileDestroyable() {
    }

    public FileDestroyable(String str) throws IOException {
        this._files.add(Resource.newResource(str).getFile());
    }

    public FileDestroyable(File file) {
        this._files.add(file);
    }

    public void addFile(String str) throws IOException {
        this._files.add(Resource.newResource(str).getFile());
    }

    public void addFile(File file) {
        this._files.add(file);
    }

    public void addFiles(Collection<File> collection) {
        this._files.addAll(collection);
    }

    public void removeFile(String str) throws IOException {
        this._files.remove(Resource.newResource(str).getFile());
    }

    public void removeFile(File file) {
        this._files.remove(file);
    }

    public void destroy() {
        for (File next : this._files) {
            if (next.exists()) {
                LOG.debug("Destroy {}", next);
                C2439IO.delete(next);
            }
        }
    }
}
