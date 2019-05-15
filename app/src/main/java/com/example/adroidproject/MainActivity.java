package com.example.adroidproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
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

public class MainActivity extends Activity {
    private String text = "";
    private int option = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText = findViewById(R.id.dateTime);
        final TextView textView = findViewById(R.id.timeOpt);
        final TextView textView2 = findViewById(R.id.diaryData);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.O)
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && option == 1) {
                    option = 2;
                    text = "";

                    String line;
                    String text3 = String.valueOf(editText.getText());
                    text = text3;
                    String text2 = text;
                    try {
                        File file = new File(getFilesDir(), "file.txt");

                        BufferedReader bf = new BufferedReader(new FileReader(file));
                        textView.setText("");
                        while ((line = bf.readLine()) != null) {
                            String[] things = line.split(",");
                            String[] date = things[0].split("_");

                            if (date[0].equals(text)) {
                                textView.append(date[1] + "    ");

                            }
                        }
                        bf.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return true;
                } else if (keyCode == KeyEvent.KEYCODE_ENTER && option == 2) {
                    String text3 = String.valueOf(editText.getText());
                    text = text3;
                    String text2 = text;
                    option = 1;
                    return true;
                }
                return false;
            }
        });
    }

    public void toPhoto(View view) {
        //sends the user to the photo activity
        Intent intent = new Intent(MainActivity.this, PhotoActivity.class);
        startActivity(intent);
    }

    public void toDiary(View view) {
        //sends the user to the view activity with the correct date
        // and time selected if none is selected show try again option

        Intent intent = new Intent(MainActivity.this, DiaryActivity.class);



        intent.putExtra("date", text);

        startActivity(intent);
    }
}
