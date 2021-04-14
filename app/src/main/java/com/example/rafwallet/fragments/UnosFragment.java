package com.example.rafwallet.fragments;

import android.media.MediaRecorder;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.rafwallet.R;
import com.example.rafwallet.activities.GlavniActivity;
import com.example.rafwallet.models.MojViewModel;
import com.example.rafwallet.models.Transaction;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import timber.log.Timber;

public class UnosFragment extends Fragment {
    private Spinner spinner;
    private CheckBox checkBox;
    private Button btnDodajUListu;
    private MojViewModel mojViewModel;
    private EditText etNaslov;
    private EditText etKolicina;
    private EditText etOpis;
    private Fragment editTextFragment;
    private Fragment microphoneFragment;
    private MediaRecorder mediaRecorder;
    private File folder;
    private ImageView btnRecording;

    public static File file;

    private final String SECOND_FRAGMENT_TAG = "secondFragment";
    private final String FIRST_FRAGMENT_TAG = "firstFragment";

    public UnosFragment() {
        super(R.layout.fragment_unos);
        System.out.println("KONSTRUKTOR FRAGMENTA");

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        System.out.println("onViewCreated unosFragmenta");
        spinner = view.findViewById(R.id.dropDownSpiner);
        checkBox = view.findViewById(R.id.audioCheckBox);
        btnDodajUListu = view.findViewById(R.id.btnDodajUListu);
        mojViewModel = new ViewModelProvider(getActivity()).get(MojViewModel.class);
        etNaslov = view.findViewById(R.id.unesiNaslovEeditText);
        etKolicina = view.findViewById(R.id.unesiKolicinuEeditText);
        checkBox.setSelected(false);
        etNaslov.setText("   ");

        List<String> stanja = new ArrayList<>();
        stanja.add(0, "Prihod");
        stanja.add("Rashod");
        //footballPlayers.add("Lionel Messi");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, stanja);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction(); // getActivity().getSupportFragmentManager().beginTransaction();
        editTextFragment = new EditTextFragment();
        transaction.add(R.id.unesiOpisFcv, editTextFragment);
        transaction.commit();
        if  (((EditTextFragment)editTextFragment).getOpis() != null)
          ((EditTextFragment)editTextFragment).setOpis(); // restartuje opis

        final int[] brojac = {0};
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction =getChildFragmentManager().beginTransaction();
                if(((CompoundButton) view).isChecked()){
                    System.out.println("Checked");
                    microphoneFragment = new MicrophoneFragment();
                    transaction.replace(R.id.unesiOpisFcv, microphoneFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                } else {
                    brojac[0]++;
                    System.out.println("Un-Checked");
                    editTextFragment = new EditTextFragment();
                    transaction.replace(R.id.unesiOpisFcv, editTextFragment);
                    transaction.commit();
                }
            }
        });

        getChildFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener()
        {
            public void onBackStackChanged()
            {
                Fragment someFragment = getActivity().getSupportFragmentManager().findFragmentById(R.id.fragmentMicrophone);

                if (someFragment == null) {
                    if (checkBox.isChecked() && brojac[0] % 2 !=0){
                        checkBox.toggle();
                    }
                }
                brojac[0]++;
            }
        });

        btnDodajUListu.setOnClickListener(e->{
            String naslov = etNaslov.getText().toString();
            String regex = "[0-9]+";

            // Compile the ReGex
            Pattern p = Pattern.compile(regex);

            if (naslov != null && !naslov.isEmpty() && !etKolicina.getText().toString().isEmpty()
            && p.matcher(etKolicina.getText().toString()).matches()){
                int kolicina = Integer.parseInt(etKolicina.getText().toString());
                boolean prihod = false;
                if (spinner.getSelectedItem().toString().equalsIgnoreCase("Prihod")){
                    prihod = true;
                }

                if (checkBox.isChecked()){
                    ImageView btnRecording = ((MicrophoneFragment)microphoneFragment).getButtonRecording();
                    // AUDIO
                    // PRAVIM PUTANJU DO SNIMLJENOG FAJLA
                    if (btnRecording.getVisibility() != View.VISIBLE){
                        String path = getActivity().getFilesDir().getAbsolutePath()+"/sounds/"+"record" + GlavniActivity.brojacFajlova++ + ".3gp";
                        System.out.println("PUTANJA: " + path);
                        Transaction t = new Transaction(MojViewModel.ID++, naslov, kolicina, prihod, path);
                        if (prihod){
                            mojViewModel.getPrihodiList().add(t);
                            ArrayList<Transaction> listToSubmit = new ArrayList<>(mojViewModel.getPrihodiList());
                            mojViewModel.getPrihodi().setValue(listToSubmit);
                        }else{
                            mojViewModel.getRashodiList().add(t);
                            ArrayList<Transaction> listToSubmit = new ArrayList<>(mojViewModel.getRashodiList());
                            mojViewModel.getRashodi().setValue(listToSubmit);
                        }
                        System.out.println("USPESNO DODAT UNOS U LISTU !");
                    }

                }else{
                    // TEXT
                    String opis = ((EditTextFragment)editTextFragment).getOpis();
                    if (opis != null && !opis.isEmpty()){
                        Transaction t = new Transaction(MojViewModel.ID++, naslov, kolicina, opis, prihod);

                        if (prihod){
                            mojViewModel.getPrihodiList().add(t);
                            ArrayList<Transaction> listToSubmit = new ArrayList<>(mojViewModel.getPrihodiList());
                            mojViewModel.getPrihodi().setValue(listToSubmit);
                        }else{
                            mojViewModel.getRashodiList().add(t);
                            ArrayList<Transaction> listToSubmit = new ArrayList<>(mojViewModel.getRashodiList());
                            mojViewModel.getRashodi().setValue(listToSubmit);
                        }
                        System.out.println("USPESNO DODAT UNOS U LISTU !");
                    }else{
                        Toast.makeText(getActivity(), "Sva polja moraju da budu popunjena !", Toast.LENGTH_LONG).show();
                    }

                }
            }else{
                Toast.makeText(getActivity(), "Sva polja moraju da budu popunjena !", Toast.LENGTH_LONG).show();
            }



        });


    }
    @Override
    public void onDestroyView() {
        System.out.println("ON DESTROY VIEW");
        super.onDestroyView();
        if (checkBox.isChecked()){
            checkBox.toggle();
        }

        etKolicina.setText("");

    }
}
