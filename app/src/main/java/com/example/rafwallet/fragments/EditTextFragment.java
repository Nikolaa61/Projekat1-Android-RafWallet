package com.example.rafwallet.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rafwallet.R;

public class EditTextFragment extends Fragment {
    private EditText etUnesiOpis;

    public EditTextFragment(){
        super(R.layout.fragment_edittext);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etUnesiOpis = view.findViewById(R.id.unesiOpis);
    }

    public String getOpis(){
        if (etUnesiOpis == null)
            return null;
        return etUnesiOpis.getText().toString();
    }
    public void setOpis(){ etUnesiOpis.setText(" ");}
}
