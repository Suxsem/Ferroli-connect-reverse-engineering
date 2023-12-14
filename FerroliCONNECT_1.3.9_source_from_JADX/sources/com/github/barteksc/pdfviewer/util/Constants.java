package com.github.barteksc.pdfviewer.util;

public interface Constants {
    public static final boolean DEBUG_MODE = false;
    public static final int GRID_SIZE = 10;
    public static final float PART_SIZE = 256.0f;
    public static final int PRELOAD_COUNT = 7;
    public static final float THUMBNAIL_RATIO = 0.3f;

    public interface Cache {
        public static final int CACHE_SIZE = ((int) Math.pow(10.0d, 2.0d));
        public static final int THUMBNAILS_CACHE_SIZE = 6;
    }

    public interface Pinch {
        public static final float MAXIMUM_ZOOM = 10.0f;
        public static final float MINIMUM_ZOOM = 1.0f;
    }
}
