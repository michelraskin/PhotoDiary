package com.example.adroidproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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
import java.io.IOException;

public class MainActivity extends Activity {
    private String text = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText = findViewById(R.id.dateTime);
        final TextView textView = findViewById(R.id.timeOpt);
        final TextView textView1 = findViewById(R.id.Choices);
        final TextView textView2 = findViewById(R.id.diaryData);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.O)
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {

                    text = "";

                    String line;
                    text = String.valueOf(editText.getText());
                    textView2.setText(text);
                    File sdcard = Environment.getExternalStorageDirectory();
                    File file = new File(sdcard,"file.txt");
                    try {
                        BufferedReader bf = new BufferedReader(new FileReader(file));
                        while ((line = bf.readLine()) != null) {
                            String[] things = line.split(",");
                            String[] date = things[0].split("_");

                            if (date[0].equals(text)) {
                                textView.append(date[1] + "\n");

                            }
                        }
                        editText.setText("Press a when time is selected");
                        bf.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return true;
                } else if (keyCode == KeyEvent.KEYCODE_A) {
                    text = text + String.valueOf(editText.getText());
                    textView1.setText(text);
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
