package com.stratpoint.ayalamuseum.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.stratpoint.ayalamuseum.MainActivity;
import com.stratpoint.ayalamuseum.R;
import com.stratpoint.ayalamuseum.databinding.FragmentCameraFilterBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class CameraFilterFragment extends BaseFragment {
    private FragmentCameraFilterBinding fragmentCameraFilterBinding;
    private CameraSource cameraSource;
    private final int RC_CAMERA_PERMISSION = 1;

    public CameraFilterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentCameraFilterBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_camera_filter, container, false);
        return fragmentCameraFilterBinding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentCameraFilterBinding.setEventHandler(new EventHandler(mainActivity));

        int rc = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);

        if (rc == PackageManager.PERMISSION_GRANTED) {
            createCameraSource();
        } else {
            requestCameraPermission();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (requestCode != RC_CAMERA_PERMISSION) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            createCameraSource();
            return;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        fragmentCameraFilterBinding.cameraPreview.start(cameraSource);
    }

    @Override
    public void onPause() {
        super.onPause();
        fragmentCameraFilterBinding.cameraPreview.stop();
    }

    public void onDestroy() {
        super.onDestroy();
        fragmentCameraFilterBinding.cameraPreview.release();
    }

    /**
     * Instantiate FaceDetector and CameraSource
     */
    private void createCameraSource() {
        FaceDetector faceDetector = new FaceDetector.Builder(getContext())
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();

        faceDetector.setProcessor(new MultiProcessor.Builder<>(new FaceTrackerFactory()).build());

        if (!faceDetector.isOperational()) {
            AlertDialog alert = new AlertDialog.Builder(getContext())
                    .setTitle("Error")
                    .setMessage("Face detector is not operational")
                    .create();
            alert.show();
        }

        cameraSource = new CameraSource.Builder(getContext(), faceDetector)
                .setRequestedPreviewSize(640, 500)
                .setFacing(CameraSource.CAMERA_FACING_FRONT)
                .setRequestedFps(30.0f)
                .build();
    }

    /**
     * Support for Marshmallow. Requesting the camera permission.
     */
    private void requestCameraPermission() {
        final String[] permissions = { Manifest.permission.CAMERA };

        if (!ActivityCompat.shouldShowRequestPermissionRationale(
                getActivity(), Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(getActivity(), permissions, RC_CAMERA_PERMISSION);
            return;
        }

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(getActivity(), permissions, RC_CAMERA_PERMISSION);
            }
        };

        Snackbar.make(getView(), R.string.camera_permission_required, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.ok, listener)
                .show();
    }

    /**
     * Factory pattern to create the processor use in FaceDetector.
     */
    private class FaceTrackerFactory implements MultiProcessor.Factory<Face> {

        @Override
        public Tracker<Face> create(Face face) {
            return new FaceTracker();
        }
    }

    private class FaceTracker extends Tracker<Face> {

        @Override
        public void onUpdate(FaceDetector.Detections<Face> detectionResults, Face face) {
            
        }

        @Override
        public void onMissing(FaceDetector.Detections<Face> detectionResults) {

        }

        @Override
        public void onDone() {

        }
    }

    public static class EventHandler {
        private MainActivity mainActivity;

        public EventHandler(MainActivity mainActivity) {
            this.mainActivity = mainActivity;
        }

        public void onCameraFilterBoxClick(View view) {
           mainActivity.switchFragment(new FilterList());
        }
    }
}
