package com.example.adroidproject;

import android.content.Intent;
import android.net.Uri;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DiaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        File file = new File("our.txt");

        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        final TextView textview = findViewById(R.id.legend);
        final TextView textView2 = findViewById(R.id.location);
        try {
            BufferedReader bf = new BufferedReader(new FileReader(file));

            for (int i = 0; i < file.length(); i++) {
                String line = bf.readLine();
                String[] things = line.split(",");

                if (things[0].equals(date)) {
                    Uri uri = (Uri) things[1];
                    if (uri != null) {
                        displayImage(uri);
                    }

                    textview.setText(things[3]);
                    textView2.setText(things[2]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        final EditText editText = findViewById(R.id.editLegend);

        editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {

                    String text = String.valueOf(editText.getText());

                    textview.setText(text);

                    return true;
                }
                return false;
            }
        });

    }

    private void displayImage(Uri uri) {
        ImageView imageView = findViewById(R.id.imageView);

        imageView.setImageURI(uri);
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
