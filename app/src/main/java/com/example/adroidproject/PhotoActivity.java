package com.example.adroidproject;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoActivity extends AppCompatActivity implements LocationListener {
    LocationManager mLocationManager;
    private String file_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
    }

    static final int TAKE_PHOTO = 1;

    private void pictureClick() {
        String time = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(new Date());
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            File photo = null;
            try {
                photo = saveImage(time);
            } catch (IOException io) {
                System.out.println("Error saving image: " + io.getMessage());
                return;
            }
            file_path = fileCreate(time, photo.getPath());

            if (file_path != null) {
                Uri uri_photo = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photo);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri_photo);
                startActivityForResult(takePictureIntent, TAKE_PHOTO);

            }
        }
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode) {
        if (requestCode == TAKE_PHOTO && resultCode == RESULT_OK) {


        }
    }
*/
    private File saveImage(String time) throws IOException {
        return File.createTempFile("PHOTO_" + time, ".jpg", getExternalFilesDir(Environment.DIRECTORY_PICTURES));
    }

    private String fileCreate(String time, String photo_path) {
        String file_name = "Diary_Picture_" + time + ".txt";
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Location location = null;
        try {
            location = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } catch (SecurityException se) {
            System.out.println("Permission not granted - Failure");
        }
        try {
            PrintWriter writer = new PrintWriter(file_name);
            writer.print(time + "," + photo_path + "," + location.getLatitude() + location.getLongitude() + ",");
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
}