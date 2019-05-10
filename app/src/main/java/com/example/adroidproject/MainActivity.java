package com.example.adroidproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toPhoto(View view) {
        //sends the user to the photo activity
    }

    public void toDiary(View view) {
        //sends the user to the view activity with the correct date and time selected if none is selected show try again option
    }


}
