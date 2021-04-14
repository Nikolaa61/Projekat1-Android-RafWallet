package com.example.rafwallet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rafwallet.R;
import com.example.rafwallet.models.User;
import com.google.gson.Gson;

public class LoginActivity extends AppCompatActivity {
    private EditText etIme;
    private EditText etPrezime;
    private EditText etImeBanke;
    private EditText etSifra;
    private Button btnPrijava;
    private String lozinka = "rafovac123";

    public static final String PREF_MESSAGE_KEY = "prefMessageKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnPrijava = findViewById(R.id.btnPrijava);
        etIme = findViewById(R.id.ime);
        etPrezime = findViewById(R.id.prezime);
        etImeBanke = findViewById(R.id.imeBanke);
        etSifra = findViewById(R.id.sifra);

        initListeners();

    }

    private void initListeners() {
        btnPrijava.setOnClickListener(v -> {
            if (etIme.getText().toString().isEmpty() || etPrezime.getText().toString().isEmpty() ||
                    etImeBanke.getText().toString().isEmpty()) {
                Toast.makeText(this, "Sva polja moraju da budu popunjena", Toast.LENGTH_LONG).show();
            } else {
                if (etSifra.getText().toString().length() < 5) {
                    Toast.makeText(this, "Lozinka mora da sadrzi bar 5 karaktera", Toast.LENGTH_LONG).show();
                } else if(!etSifra.getText().toString().equals(lozinka)){
                    Toast.makeText(this, "Pogresna lozinka", Toast.LENGTH_LONG).show();
                } else {
                    SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
                    User user = new User(etIme.getText().toString(), etPrezime.getText().toString(), etImeBanke.getText().toString(), etSifra.getText().toString());
                    Gson gson = new Gson();
                    String json = gson.toJson(user);

                    sharedPreferences
                            .edit()
                            .putString(PREF_MESSAGE_KEY, json)
                            .apply();
                    Toast.makeText(this, "Uspesan login", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(this, GlavniActivity.class);
                    intent.putExtra(GlavniActivity.GLAVNI_KEY, user);
                    startActivity(intent);
                    finish();
                }
            }


        });
    }


}