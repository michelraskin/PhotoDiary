package com.example.adroidproject;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class PhotoActivity extends Activity implements LocationListener {
    LocationManager mLocationManager;
    private String file_path;
    Camera camera;
    CameraPreview cameraPreview;
    FrameLayout background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        background = findViewById(R.id.background);
        camera = Camera.open();

        cameraPreview = new CameraPreview(PhotoActivity.this, camera);
        background.addView(cameraPreview);
    }

    public void back2(View view) {
        Intent intent = new Intent(PhotoActivity.this, MainActivity.class);
        startActivity(intent);
    }

    static final int TAKE_PHOTO = 1;

    public void pictureClick(View view) {
        String time = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date());
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            File photo = null;
            try {
                photo = File.createTempFile("PHOTO_" + time, ".jpg", getExternalFilesDir(Environment.DIRECTORY_PICTURES));
            } catch (IOException io) {
                System.out.println("Error saving image: " + io.getMessage());
                return;
            }
            addImageToGallery(photo.getAbsolutePath(), this);
            //file_path = fileCreate(time, photo.getPath());

        }
    }

    public static void addImageToGallery(final String filePath, final Context context) {

        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, filePath);

        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }
    //
/*
    private void galleryAddPic(String currentPhotoPath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }
*/

     protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    private String fileCreate(String time, String photo_path) {
        String file_name = "file.txt";
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        File file = new File(getFilesDir(), file_name);
        Location location = null;
        try {
            location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } catch (SecurityException se) {
            System.out.println("Permission not granted - Failure");
        }
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(file, true));
            writer.println(time + "," + photo_path + "," + location.getLatitude() + location.getLongitude() + ",");
            writer.close();
        } catch (Exception exc) {
            System.out.println("Error writing to new image file" + exc.getMessage());
            return null;
        }
        return file_name;
    }

    //Required methods which will not be utilized
    public void onProviderDisabled(String arg0) {
    }

    public void onProviderEnabled(String arg0) {
    }

    public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
    }

    public void onLocationChanged(Location arg0){

    }

    public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
        private SurfaceHolder mHolder;
        private Camera mCamera;

        public CameraPreview(Context context, Camera camera) {
            super(context);
            mCamera = camera;

            // Install a SurfaceHolder.Callback so we get notified when the
            // underlying surface is created and destroyed.
            mHolder = getHolder();
            mHolder.addCallback(this);
        }

        public void surfaceCreated(SurfaceHolder holder) {
            // The Surface has been created, now tell the camera where to draw the preview.
            try {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            } catch (IOException e) {

            }
        }
        public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
            // If your preview can change or rotate, take care of those events here.
            // Make sure to stop the preview before resizing or reformatting it.

            if (mHolder.getSurface() == null){
                // preview surface does not exist
                return;
            }

            // stop preview before making changes
            try {
                mCamera.stopPreview();
            } catch (Exception e){
                // ignore: tried to stop a non-existent preview
            }

            // set preview size and make any resize, rotate or
            // reformatting changes here

            // start preview with new settings
            try {
                mCamera.setPreviewDisplay(mHolder);
                mCamera.startPreview();

            } catch (Exception e){
                Log.d(TAG, "Error starting camera preview: " + e.getMessage());
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            camera.stopPreview();
            camera.release();
        }
    }
}