package com.example.rafwallet.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.rafwallet.R;
import com.example.rafwallet.activities.GlavniActivity;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class MicrophoneFragment extends Fragment {
    private MediaRecorder mediaRecorder;
    private File folder;
    private ImageView btnRecording;

    private final int PERMISSION_ALL = 1;
    private final String[] PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public MicrophoneFragment(){
        super(R.layout.fragment_microphone);
    }
    private CheckBox checkBox;
    public static File file;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(hasPermissions(getContext(), PERMISSIONS)) {
            init();
        }else {
            // Ukoliko nije, trazimo ih
            requestPermissions(PERMISSIONS, PERMISSION_ALL);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListeners(view);
        checkBox = view.findViewById(R.id.audioCheckBox);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String @NotNull [] permissionsList, int @NotNull [] grantResults) {
        // Ovde dobijamo odgovor na requestPermissions
        if (requestCode == PERMISSION_ALL) {
            if (grantResults.length > 0) {
                StringBuilder permissionsDenied = new StringBuilder();
                for(int i=0;i<grantResults.length;i++){
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        permissionsDenied.append("\n").append(permissionsList[i]);
                    }
                }

                if (permissionsDenied.toString().length() == 0) {
                    // Ukoliko nema odbijenih dozvola, nastavljamo dalje
                    System.out.println("POZIVAMO INIT");
                    init();
                }else {
                    Toast.makeText(getActivity(), "Missing permissions! " + permissionsDenied.toString(), Toast.LENGTH_LONG).show();
                    // Ukoliko ima odbijenih dozvola ispisujemo poruku i zatvaramo activity

                    getParentFragmentManager().popBackStack();

                }
            }
        }
    }
    private boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void init() {
        folder = new File(getActivity().getFilesDir(), "sounds");
        if(!folder.exists()) folder.mkdir();
        file = new File(folder, "record" + GlavniActivity.brojacFajlova + ".3gp");
    }

    private void initListeners(View view) {
        ImageView btnMic = view.findViewById(R.id.btnMic);
        btnRecording = view.findViewById(R.id.btnRecording);

        btnMic.setOnClickListener(v -> {
            file = new File(folder, "record" + GlavniActivity.brojacFajlova + ".3gp");
            try {
                btnMic.setVisibility(View.GONE);
                btnRecording.setVisibility(View.VISIBLE);
                initMediaRecorder(file);
                // Pokrecemo snimanje
                mediaRecorder.prepare();
                mediaRecorder.start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        btnRecording.setOnClickListener(v -> {
            btnMic.setVisibility(View.VISIBLE);
            btnRecording.setVisibility(View.GONE);
            // Zaustavljamo snimanje i oslobadjamo resurse
            // Metodom stop() se snimljeni resurs cuva u fajlu koji smo prosledili pri inicijalizaciji mediaRecorder-a
            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;
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

    public ImageView getButtonRecording(){
        return btnRecording;
    }
}
