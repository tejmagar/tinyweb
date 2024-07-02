package com.tinyweb.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.tinyweb.lib.Server;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Server server = new Server(8080);
        try {
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}