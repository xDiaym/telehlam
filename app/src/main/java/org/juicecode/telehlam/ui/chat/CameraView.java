package org.juicecode.telehlam.ui.chat;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

public class CameraView extends SurfaceView implements SurfaceHolder.Callback {
    private Camera camera;
    private SurfaceHolder holder;

    public CameraView(Context context, Camera camera) {
        super(context);
        this.camera = camera;
        holder = getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Parameters parameters = camera.getParameters();
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            parameters.set("orientation", "landscape");
            camera.setDisplayOrientation(0);
            parameters.setRotation(0);
        } else {
            parameters.set("orientation", "portrait");
            camera.setDisplayOrientation(90);
            parameters.setRotation(90);
        }
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (holder.getSurface() == null) {
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            camera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();

        } catch (Exception e) {


        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        try {
            camera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }
    }
}
