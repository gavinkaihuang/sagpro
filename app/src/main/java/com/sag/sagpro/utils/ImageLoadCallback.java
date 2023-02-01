package com.sag.sagpro.utils;

import android.graphics.Bitmap;

public interface ImageLoadCallback {

    public Bitmap loadImageSucceed(Bitmap bitmap);
    public Exception loadImageFailed(Exception exception);
}
