package com.example.vbps;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up OnClickListener for NewPassButton
        findViewById(R.id.newPassButton).setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, NewPassActivity.class)));

        // Set up OnClickListener for RechargePassButton
        findViewById(R.id.rechargePassButton).setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, RechargePassActivity.class)));

        // Set up OnClickListener for ViewPassButton
        findViewById(R.id.viewPassButton).setOnClickListener(view ->
                startActivity(new Intent(MainActivity.this, ViewPassActivity.class)));
    }
}
