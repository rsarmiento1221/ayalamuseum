package com.stratpoint.ayalamuseum.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.widget.FrameLayout;

import com.google.android.gms.common.images.Size;
import com.google.android.gms.vision.CameraSource;

import java.io.IOException;

/**
 * Created by raymondsarmiento on 7/29/16.
 */
public class CameraPreview extends FrameLayout {
    private SurfaceView surfaceView;
    private CameraSource cameraSource;
    private Context context;
    private boolean surfaceIsReady;

    public CameraPreview(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        surfaceView = new SurfaceView(context);
        surfaceView.getHolder().addCallback(new SurfaceCallback());
        addView(surfaceView);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        int heightPixels = getResources().getDisplayMetrics().heightPixels;

        if (surfaceView != null) {
            surfaceView.layout(0, 0, widthPixels, heightPixels);
        }
    }

    private boolean isPortraitMode() {
        int orientation = context.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return false;
        }
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            return true;
        }

        return false;
    }

    public void start(CameraSource cameraSource) {
        this.cameraSource = cameraSource;
        startIfReady();
    }

    private void startIfReady() {
        if (cameraSource != null && surfaceIsReady) {
            try {
                cameraSource.start(surfaceView.getHolder());
            } catch (IOException e) {
                cameraSource.release();
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        if (cameraSource != null) {
            cameraSource.stop();
        }
    }

    public void release() {
        if (cameraSource != null) {
            cameraSource.release();
        }
    }

    private class SurfaceCallback implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            surfaceIsReady = true;
            startIfReady();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    }
}
