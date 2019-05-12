package com.example.adroidproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText = findViewById(R.id.dateTime);

        editText.setOnKeyListener(new View.OnKeyListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    try {
                        BufferedReader bf = new BufferedReader(new FileReader(new File("file.txt")));
                        String line;
                        String text = editText.getText();
                        while ((line = bf.readLine()) != null) {
                            String[] things = line.split(",");
                            String[] date = things[0].split("_");

                            if (date[0].equals())
                        }
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

    public void toPhoto(View view) {
        //sends the user to the photo activity
        Intent intent = new Intent(MainActivity.this, PhotoActivity.class);
        startActivity(intent);
    }

    public void toDiary(View view) {
        //sends the user to the view activity with the correct date
        // and time selected if none is selected show try again option

        Intent intent = new Intent(MainActivity.this, DiaryActivity.class);



        intent.putExtra("date", "");

        startActivity(intent);
    }
}
