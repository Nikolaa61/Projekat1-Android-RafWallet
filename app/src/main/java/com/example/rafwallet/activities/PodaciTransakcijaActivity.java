package com.example.rafwallet.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rafwallet.R;
import com.example.rafwallet.models.Transaction;
import com.example.rafwallet.models.User;

import java.io.File;

import timber.log.Timber;

public class PodaciTransakcijaActivity extends AppCompatActivity {
    Transaction transaction;

    private EditText etNaslov;
    private EditText etKolicina;
    private TextView tvOpis;
    private ImageView imgPlay;
    private ImageView imgPause;

    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;
    private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener;
    private AudioFocusRequest audioFocusRequest;

    public static final String PODACI_KEY = "podaciKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podaci_transakcija);

        etNaslov = findViewById(R.id.podaciTransakcijaNaslovEditText);
        etKolicina = findViewById(R.id.podaciTransakcijaKolicinaEditText);
        tvOpis = findViewById(R.id.podaciTransakcijaTFOpis);
        imgPlay = findViewById(R.id.podaciTransakcijaBtnPlay);
        imgPause = findViewById(R.id.podaciTransakcijaBtnPause);

        Intent intent = getIntent();
        if (intent.getExtras() != null){
            this.transaction = (Transaction) intent.getExtras().getSerializable(PODACI_KEY);
        }


        etNaslov.setText(transaction.getNaslov());
        etKolicina.setText(String.valueOf(transaction.getKolicina()));

        if (transaction.getFilePath() != null){
            File f = new File(transaction.getFilePath());
            if(f.exists() && !f.isDirectory()){
                System.out.println("POSTOJI");
            }
            mediaPlayer = MediaPlayer.create(this, Uri.fromFile(f));
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

            imgPlay.setVisibility(View.VISIBLE);
            tvOpis.setVisibility(View.GONE);

            imgPlay.setOnClickListener(e->{
                play();
            });

            imgPause.setOnClickListener(e->{
                // Hide pause button
                imgPause.setVisibility(View.GONE);
                // Show play button
                imgPlay.setVisibility(View.VISIBLE);
                // Pause media player
                mediaPlayer.pause();
            });

            // We have to handle focus changes
            onAudioFocusChangeListener = focusChange -> {
                switch (focusChange) {
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    case AudioManager.AUDIOFOCUS_LOSS: {
                        // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a short amount of time.
                        // The AUDIOFOCUS_LOSS case means we've lost audio focus
                        Timber.e("AUDIOFOCUS_LOSS_TRANSIENT or AUDIOFOCUS_LOSS");
                        pause();
                    } break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK: {
                        // The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                        // our app is allowed to continue playing sound but at a lower volume.
                        Timber.e("AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK");
                        playerDuck(true);
                    } break;
                    case AudioManager.AUDIOFOCUS_GAIN: {
                        // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                        Timber.e("AUDIOFOCUS_GAIN");
                        playerDuck(false);
                        play();
                    } break;
                }
            };

            mediaPlayer.setOnCompletionListener(mp -> {
                // Hide pause button
                imgPause.setVisibility(View.GONE);
                // Show play button
                imgPlay.setVisibility(View.VISIBLE);
                // Set media player to initial position
                mediaPlayer.seekTo(0);
            });

            // Description of the audioFocusRequest
            audioFocusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                    .setAudioAttributes(new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build())
                    .setAcceptsDelayedFocusGain(true)
                    .setWillPauseWhenDucked(true)
                    .setOnAudioFocusChangeListener(onAudioFocusChangeListener)
                    .build();

        }else{
            tvOpis.setText(transaction.getOpis());
        }


    }

    private void play() {
        int result = audioManager.requestAudioFocus(audioFocusRequest);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            Timber.e("AUDIOFOCUS_REQUEST_GRANTED");
            // Hide play button
            imgPlay.setVisibility(View.GONE);
            // Show pause button
            imgPause.setVisibility(View.VISIBLE);
            // Start media player
            mediaPlayer.start();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null)
            releaseMediaPlayer();
    }

    private void releaseMediaPlayer() {
        // Release resources
        if(mediaPlayer != null)
            mediaPlayer.release();
        mediaPlayer = null;
        audioManager.abandonAudioFocusRequest(audioFocusRequest);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null)
            pause();
    }

    private void pause() {
        // Hide pause button
        imgPause.setVisibility(View.GONE);
        // Show play button
        imgPlay.setVisibility(View.VISIBLE);
        // Pause media player
        mediaPlayer.pause();
    }
    public synchronized void playerDuck(boolean duck) {
        if (mediaPlayer != null) {
            // Reduce the volume when ducking - otherwise play at full volume.
            mediaPlayer.setVolume(duck ? 0.2f : 1.0f, duck ? 0.2f : 1.0f);
        }
    }

}