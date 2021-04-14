package com.example.rafwallet.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.rafwallet.R;
import com.example.rafwallet.activities.GlavniActivity;
import com.example.rafwallet.activities.IzmeniKorisnikaActivity;
import com.example.rafwallet.activities.LoginActivity;
import com.example.rafwallet.models.MojViewModel;
import com.example.rafwallet.models.User;
import com.google.gson.Gson;

public class ProfilFragment extends Fragment {
    public static final int TRANSACTION_RECEIVED_REQUEST_CODE = 111;

    private TextView tfIme;
    private TextView tfPrezime;
    private TextView tfBanka;
    private MojViewModel mojViewModel;
    private Button btnIzmeni;
    private Button btnOdjava;

    public ProfilFragment(){
        super(R.layout.fragment_profil);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        System.out.println("onViewCreatedProfilFragmenta");
        tfIme = view.findViewById(R.id.profilTextViewImeKorisnika);
        tfPrezime = view.findViewById(R.id.profilTextViewPrezimeKorisnika);
        tfBanka = view.findViewById(R.id.profilTextViewBankaKorisnika);
        btnOdjava = view.findViewById(R.id.profilBtnOdjava);
        btnIzmeni = view.findViewById(R.id.profilBtnIzmeni);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
        String message = sharedPreferences.getString(LoginActivity.PREF_MESSAGE_KEY, null);

        Gson gson = new Gson();
        User user = gson.fromJson(message, User.class);

        tfIme.setText(user.getIme());
        tfPrezime.setText(user.getPrezime());
        tfBanka.setText(user.getImeBanke());

        btnOdjava.setOnClickListener( e -> {
            SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences(getActivity().getPackageName(), Context.MODE_PRIVATE);
            sharedPreferences1
                    .edit()
                    .putString(LoginActivity.PREF_MESSAGE_KEY, null)
                    .apply();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        });

        btnIzmeni.setOnClickListener(e ->{
            Intent intent = new Intent(getActivity(), IzmeniKorisnikaActivity.class);
            intent.putExtra(IzmeniKorisnikaActivity.IZMENA_KORISNIKA_KEY, user);
            startActivityForResult(intent, TRANSACTION_RECEIVED_REQUEST_CODE);
        });
    }
}
