package com.github.barteksc.pdfviewer.source;

import android.content.Context;
import android.os.ParcelFileDescriptor;
import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;
import java.io.File;
import java.io.IOException;
import org.eclipse.paho.client.mqttv3.internal.ClientDefaults;

public class FileSource implements DocumentSource {
    private File file;

    public FileSource(File file2) {
        this.file = file2;
    }

    public PdfDocument createDocument(Context context, PdfiumCore pdfiumCore, String str) throws IOException {
        return pdfiumCore.newDocument(ParcelFileDescriptor.open(this.file, ClientDefaults.MAX_MSG_SIZE), str);
    }
}
