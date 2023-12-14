package com.github.barteksc.pdfviewer;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.github.barteksc.pdfviewer.model.PagePart;
import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;
import java.util.HashSet;
import java.util.Set;

class RenderingHandler extends Handler {
    static final int MSG_RENDER_TASK = 1;
    private final Set<Integer> openedPages = new HashSet();
    private PdfDocument pdfDocument;
    /* access modifiers changed from: private */
    public PDFView pdfView;
    private PdfiumCore pdfiumCore;
    private RectF renderBounds = new RectF();
    private Matrix renderMatrix = new Matrix();
    private Rect roundedRenderBounds = new Rect();

    RenderingHandler(Looper looper, PDFView pDFView, PdfiumCore pdfiumCore2, PdfDocument pdfDocument2) {
        super(looper);
        this.pdfView = pDFView;
        this.pdfiumCore = pdfiumCore2;
        this.pdfDocument = pdfDocument2;
    }

    /* access modifiers changed from: package-private */
    public void addRenderingTask(int i, int i2, float f, float f2, RectF rectF, boolean z, int i3, boolean z2, boolean z3) {
        sendMessage(obtainMessage(1, new RenderingTask(f, f2, rectF, i, i2, z, i3, z2, z3)));
    }

    public void handleMessage(Message message) {
        final PagePart proceed = proceed((RenderingTask) message.obj);
        if (proceed != null) {
            this.pdfView.post(new Runnable() {
                public void run() {
                    RenderingHandler.this.pdfView.onBitmapRendered(proceed);
                }
            });
        }
    }

    private PagePart proceed(RenderingTask renderingTask) {
        Bitmap bitmap;
        if (!this.openedPages.contains(Integer.valueOf(renderingTask.page))) {
            this.openedPages.add(Integer.valueOf(renderingTask.page));
            this.pdfiumCore.openPage(this.pdfDocument, renderingTask.page);
        }
        int round = Math.round(renderingTask.width);
        int round2 = Math.round(renderingTask.height);
        Bitmap createBitmap = Bitmap.createBitmap(round, round2, Bitmap.Config.ARGB_8888);
        calculateBounds(round, round2, renderingTask.bounds);
        this.pdfiumCore.renderPageBitmap(this.pdfDocument, createBitmap, renderingTask.page, this.roundedRenderBounds.left, this.roundedRenderBounds.top, this.roundedRenderBounds.width(), this.roundedRenderBounds.height(), renderingTask.annotationRendering);
        if (!renderingTask.bestQuality) {
            Bitmap copy = createBitmap.copy(Bitmap.Config.RGB_565, false);
            createBitmap.recycle();
            bitmap = copy;
        } else {
            bitmap = createBitmap;
        }
        return new PagePart(renderingTask.userPage, renderingTask.page, bitmap, renderingTask.width, renderingTask.height, renderingTask.bounds, renderingTask.thumbnail, renderingTask.cacheOrder);
    }

    private void calculateBounds(int i, int i2, RectF rectF) {
        this.renderMatrix.reset();
        float f = (float) i;
        float f2 = (float) i2;
        this.renderMatrix.postTranslate((-rectF.left) * f, (-rectF.top) * f2);
        this.renderMatrix.postScale(1.0f / rectF.width(), 1.0f / rectF.height());
        this.renderBounds.set(0.0f, 0.0f, f, f2);
        this.renderMatrix.mapRect(this.renderBounds);
        this.renderBounds.round(this.roundedRenderBounds);
    }

    private class RenderingTask {
        boolean annotationRendering;
        boolean bestQuality;
        RectF bounds;
        int cacheOrder;
        float height;
        int page;
        boolean thumbnail;
        int userPage;
        float width;

        RenderingTask(float f, float f2, RectF rectF, int i, int i2, boolean z, int i3, boolean z2, boolean z3) {
            this.page = i2;
            this.width = f;
            this.height = f2;
            this.bounds = rectF;
            this.userPage = i;
            this.thumbnail = z;
            this.cacheOrder = i3;
            this.bestQuality = z2;
            this.annotationRendering = z3;
        }
    }
}
