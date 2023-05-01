package org.tinyweb.demo;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.tinyweb.TinyWebWS;
import org.tinyweb.urls.Path;
import org.tinyweb.urls.Routes;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Routes routes = new Routes();
        routes.addRoute(new Path("/", new HomeView()));

        Routes wsRoutes = new Routes();
        wsRoutes.addRoute(new Path("/ws/", new MyWsView()));

        TinyWebWS tinyWeb = new TinyWebWS(8000);
        tinyWeb.setRoutes(routes);
        tinyWeb.setWsRoutes(wsRoutes);

        try {
            tinyWeb.start();
            Toast.makeText(getApplicationContext(), "Server is running", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
