package com.example.rafwallet.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.rafwallet.R;
import com.example.rafwallet.models.MojViewModel;
import com.example.rafwallet.models.Transaction;

public class StanjeFragment extends Fragment {

    private MojViewModel mojViewModel;
    private TextView tvPrihod;
    private TextView tvRashod;
    private TextView tvRazlika;

    public StanjeFragment(){super(R.layout.fragment_stanje);}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvPrihod = view.findViewById( R.id.prihodBroj);
        tvRashod = view.findViewById(R.id.rashodBroj);
        tvRazlika = view.findViewById(R.id.razlikaBroj);

        mojViewModel = new ViewModelProvider(getActivity()).get(MojViewModel.class);
        int prihod = 0;
        for(Transaction t : mojViewModel.getPrihodiList()){
            prihod += t.getKolicina();
        }

        int rashod = 0;
        for(Transaction t : mojViewModel.getRashodiList()){
            rashod += t.getKolicina();
        }

        tvPrihod.setText(String.valueOf(prihod));
        tvRashod.setText(String.valueOf(rashod));

        tvRazlika.setText(String.valueOf(Math.abs(prihod- rashod)));

        if (prihod > rashod){
            tvRazlika.setTextColor(Color.GREEN);
        }else{
            tvRazlika.setTextColor(Color.RED);
        }


    }
}
