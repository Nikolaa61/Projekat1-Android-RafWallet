package com.example.rafwallet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.rafwallet.models.User;
import com.google.gson.Gson;

public class SplashActivity extends AppCompatActivity {

    public static final int PREFERENCE_WRITE_REQUEST_CODE = 222;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("USAO");
        Toast.makeText(this, "USAOOOO", Toast.LENGTH_LONG);
        SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
        String message = sharedPreferences.getString(LoginActivity.PREF_MESSAGE_KEY, null);

        if(message == null) {
            // Nista jos uvek nije upisano, pokreni PreferenceActivity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, PREFERENCE_WRITE_REQUEST_CODE);
        }else {
            Intent intent = new Intent(this, GlavniActivity.class);
            Gson gson = new Gson();
            User user = gson.fromJson(message, User.class);
            intent.putExtra(GlavniActivity.GLAVNI_KEY, user);
            startActivity(intent);

        }

    }
}