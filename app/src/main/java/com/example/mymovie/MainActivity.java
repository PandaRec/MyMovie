package com.example.mymovie;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.mymovie.utils.NetworkUtils;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JSONObject tempJson= null;

        tempJson = NetworkUtils.getJSONFromNetwork(NetworkUtils.POPULARITY,1);
        if(tempJson==null)
            Toast.makeText(this, "fail", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();

    }
}