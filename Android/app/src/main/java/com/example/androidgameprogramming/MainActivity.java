package com.example.androidgameprogramming;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    TextView  tv = findViewById(R.id.textView);
    tv.setText("Launched");
    }

    public void onBtnFirst(View v) {
        TextView tv = findViewById(R.id.textView);
        tv.setText("First Button Pressed");
    }

    public void onBtnSecond(View v) {
        TextView tv = findViewById(R.id.textView2);
        tv.setText("Second Button Pressed");
    }

    public void onBtnThird(View v) {
        TextView tv = findViewById(R.id.textView3);
        tv.setText("Third Button Pressed");
    }
}
