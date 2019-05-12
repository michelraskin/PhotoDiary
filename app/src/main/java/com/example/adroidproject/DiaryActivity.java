package com.example.adroidproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class DiaryActivity extends Activity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        final File file = new File("file.txt");

        Intent intent = getIntent();
        final String date = intent.getStringExtra("date");
        final TextView textview = findViewById(R.id.legend);
        final TextView textView2 = findViewById(R.id.location);
        final TextView textView3 = findViewById(R.id.date);
        try {
            BufferedReader bf = new BufferedReader(new FileReader(file));

            String line;
            while ((line = bf.readLine()) != null) {
                String[] things = line.split(",");

                if (things[0].equals(date)) {

                    displayImage(things[1]);
                    textView3.setText(things[0]);
                    textview.setText(things[3]);
                    textView2.setText(things[2]);

                }
            }

            bf.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final EditText editText = findViewById(R.id.editLegend);

        editText.setOnKeyListener(new View.OnKeyListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {

                    String text = String.valueOf(editText.getText());

                    textview.setText(text);
                    File file2 = new File("file2.txt");
                    try {
                        BufferedReader bf = new BufferedReader(new FileReader(file));
                        PrintWriter pw = new PrintWriter(new FileWriter(file2));
                        PrintWriter pw2 = new PrintWriter(new FileWriter(file));
                        BufferedReader bf2 = new BufferedReader(new FileReader(file2));
                        String line;
                        while ((line = bf.readLine()) != null) {
                            String[] splitted = line.split(",");
                            if (splitted[0].equals(date)) {
                                pw.println(splitted[0] + "," + splitted[1] + "," + splitted[2] + "," + text);
                            } else {
                                pw.println(line);
                            }
                        }
                        Files.newBufferedWriter(Paths.get("file.txt"), StandardOpenOption.TRUNCATE_EXISTING);
                        String line2;
                        while ((line2 = bf2.readLine()) != null) {
                            pw2.println(line2);
                        }

                        Files.newBufferedWriter(Paths.get("file2.txt"), StandardOpenOption.TRUNCATE_EXISTING);

                        bf.close();
                        pw.close();
                        pw2.close();
                        bf2.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return true;
                }
                return false;
            }
        });

    }

    private void displayImage(String path) {
        ImageView imageView = findViewById(R.id.imageView);
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        imageView.setImageBitmap(bitmap);
    }

    public void toLocation(View view) {
        TextView textView = findViewById(R.id.location);
        String adress = textView.getText().toString();

        Uri uri = Uri.parse("geo:0,0?q="+adress);

        Intent map_intent = new Intent(Intent.ACTION_VIEW, uri);

        map_intent.setPackage("com.google.android.apps.maps");

        startActivity(map_intent);
    }

    public void back(View view) {
        Intent intent = new Intent(DiaryActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
