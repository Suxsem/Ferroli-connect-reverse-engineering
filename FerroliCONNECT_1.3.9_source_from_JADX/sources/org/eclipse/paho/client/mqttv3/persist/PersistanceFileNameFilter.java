package org.eclipse.paho.client.mqttv3.persist;

import java.io.File;
import java.io.FilenameFilter;

public class PersistanceFileNameFilter implements FilenameFilter {
    private final String fileExtension;

    public PersistanceFileNameFilter(String str) {
        this.fileExtension = str;
    }

    public boolean accept(File file, String str) {
        return str.endsWith(this.fileExtension);
    }
}
