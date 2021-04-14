package com.example.rafwallet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.rafwallet.R;
import com.example.rafwallet.fragments.PrihodiFragment;
import com.example.rafwallet.models.Transaction;
import com.example.rafwallet.models.User;

import java.io.File;
import java.io.IOException;

public class IzmenaActivity extends AppCompatActivity {
    private Transaction transaction;
    public static final String IZMENA_KEY = "izmenaKey";

    private EditText etNaslov;
    private EditText etKolicina;
    private EditText etOpis;
    private ImageView btnMic;
    private ImageView btnRecording;
    private Button btnOdustani;
    private Button btnIzmeni;

    private MediaRecorder mediaRecorder;
    private File folder;
    public static File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_izmena);

        Intent intent = getIntent();
        if (intent.getExtras() != null){
            this.transaction = (Transaction) intent.getExtras().getSerializable(IZMENA_KEY);
        }
        etNaslov = findViewById(R.id.izmeniNaslovEditText);
        etKolicina = findViewById(R.id.izmeniKolicinaEditText);
        etOpis = findViewById(R.id.izmeniOpisTextView);
        btnMic = findViewById(R.id.izmeniBtnMic);
        btnOdustani = findViewById(R.id.btnOdustani);
        btnIzmeni = findViewById(R.id.btnIzmeni);
        btnRecording = findViewById(R.id.izmeniBtnRecording);


        etNaslov.setText(transaction.getNaslov());
        etKolicina.setText(String.valueOf(transaction.getKolicina()));
        if (transaction.getFilePath() == null){
            // opisna
            etOpis.setText(transaction.getOpis());
        }else{
            btnMic.setVisibility(View.VISIBLE);
            etOpis.setVisibility(View.GONE);
        }



        btnOdustani.setOnClickListener(e->{
            if (etOpis.getVisibility() == View.GONE){
                if (mediaRecorder != null){
                    mediaRecorder.reset();
                    mediaRecorder.release();
                    mediaRecorder = null;
                }
            }
            // MOZDA TREBA DA SE OSVEZAVA U VIEW MODELU DA LI CE GA DIFITEMCALLBACK PREPOZNATI ILI ADAPTER?
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        });

        btnIzmeni.setOnClickListener(e->{
            transaction.setNaslov(etNaslov.getText().toString());
            transaction.setKolicina(Integer.parseInt(etKolicina.getText().toString()));
            if (etOpis.getVisibility() == View.VISIBLE){
                transaction.setOpis(etOpis.getText().toString());
            }else{
                mediaRecorder.stop();
                mediaRecorder.reset();
                mediaRecorder.release();
                mediaRecorder = null;
            }
            // MOZDA TREBA DA SE MANUELNO APDEJTUJE LISTA U VIEW MODELU
            Intent returnIntent = new Intent();
            returnIntent.putExtra(PrihodiFragment.TRANSACTION_RECEIVED_MESSAGE, transaction);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();

        });

        btnMic.setOnClickListener(e->{
            file = new File(transaction.getFilePath());
            try {
                btnMic.setVisibility(View.GONE);
                btnRecording.setVisibility(View.VISIBLE);
                initMediaRecorder(file);
                // Pokrecemo snimanje
                mediaRecorder.prepare();
                mediaRecorder.start();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        btnRecording.setOnClickListener(e->{
            btnMic.setVisibility(View.VISIBLE);
            btnRecording.setVisibility(View.GONE);
            // Zaustavljamo snimanje i oslobadjamo resurse
            // Metodom stop() se snimljeni resurs cuva u fajlu koji smo prosledili pri inicijalizaciji mediaRecorder-a
            mediaRecorder.pause();
        });





    }

    private void initMediaRecorder(File file) {
        mediaRecorder = new MediaRecorder();
        // Postavljanje parametara za mediaRecorder
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(file);
    }
}