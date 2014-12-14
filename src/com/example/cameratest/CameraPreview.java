package com.example.cameratest;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

@SuppressWarnings("deprecation")
public class CameraPreview extends SurfaceView implements Callback {

    private Camera mCamera;

    public CameraPreview(Context context, Camera camera) {
        super(context);
        mCamera = camera;

        // register callback so we receive notifcations on surface
        // creation/destruction
        SurfaceHolder mHolder = getHolder();
        mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.
        if (holder.getSurface() == null) {
            return;
        }
        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            return;
        }
        // make preview changes here
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (Exception e) {
            Log.d(CameraActivity.TAG, "Error with camera preview: ", e);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // surface has been created, tell camera where to draw
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.d(CameraActivity.TAG, "Error setting camera preview: ", e);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // no-op
    }

}
