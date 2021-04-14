package com.example.rafwallet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.rafwallet.R;
import com.example.rafwallet.fragments.PrihodiFragment;
import com.example.rafwallet.models.User;
import com.google.gson.Gson;

public class IzmeniKorisnikaActivity extends AppCompatActivity {

    EditText etIme;
    EditText etPrezime;
    EditText etBanka;
    Button btnIzmeni;
    
    User user;
    public static final String IZMENA_KORISNIKA_KEY = "izmenaKorisnikaKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_izmeni_korisnika);

        etIme = findViewById(R.id.izmeniKorisnikaIme);
        etPrezime = findViewById(R.id.izmeniKorisnikaPrezime);
        etBanka = findViewById( R.id.izmeniKorisnikaBanka);
        btnIzmeni = findViewById(R.id.izmeniKorisnikaBtnIzmeni);

        Intent intent = getIntent();
        if (intent.getExtras() != null){
            this.user = (User) intent.getExtras().getSerializable(IZMENA_KORISNIKA_KEY);
        }

        etIme.setText(user.getIme());
        etPrezime.setText(user.getPrezime());
        etBanka.setText(user.getImeBanke());

        btnIzmeni.setOnClickListener(e->{
            user.setIme(etIme.getText().toString());
            user.setPrezime(etPrezime.getText().toString());
            user.setImeBanke(etBanka.getText().toString());

            SharedPreferences sharedPreferences = getSharedPreferences(getPackageName(), Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String json = gson.toJson(user);

            sharedPreferences
                    .edit()
                    .putString(LoginActivity.PREF_MESSAGE_KEY, json)
                    .apply();
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        });
        
    }
}