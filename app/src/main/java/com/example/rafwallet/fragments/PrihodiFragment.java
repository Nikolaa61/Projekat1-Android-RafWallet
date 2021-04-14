package com.example.rafwallet.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rafwallet.R;
import com.example.rafwallet.activities.IzmenaActivity;
import com.example.rafwallet.activities.IzmeniKorisnikaActivity;
import com.example.rafwallet.activities.PodaciTransakcijaActivity;
import com.example.rafwallet.models.MojViewModel;
import com.example.rafwallet.models.Transaction;
import com.example.rafwallet.models.TransactionAdapter;
import com.example.rafwallet.models.TransactionDiffItemCallback;

import timber.log.Timber;

public class PrihodiFragment extends Fragment {
    private MojViewModel recyclerViewModel;
    private TransactionAdapter transactionAdapter;
    private RecyclerView recyclerView;

    public static final int TRANSACTION_RECEIVED_REQUEST_CODE = 222;
    public static final String TRANSACTION_RECEIVED_MESSAGE = "transactionReceivedKey";

    public PrihodiFragment(){
        super(R.layout.fragment_prihodi);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.listRvPrihodi);

        recyclerViewModel = new ViewModelProvider(getActivity()).get(MojViewModel.class);

        transactionAdapter = new TransactionAdapter(new TransactionDiffItemCallback(), transaction ->{
            //  Toast.makeText(this, contact.getId()+"", Toast.LENGTH_SHORT).show();
            // ovde uklanjas kontakta.
            recyclerViewModel.removeTransaction(transaction);
            return null;
        }, transaction2->{
            // onEdit
            Intent intent = new Intent(getActivity(), IzmenaActivity.class);
            intent.putExtra(IzmenaActivity.IZMENA_KEY, transaction2);
            startActivityForResult(intent, TRANSACTION_RECEIVED_REQUEST_CODE);
            return null;
        }, transaction3->{
            // onTransaction
            Intent intent = new Intent(getActivity(), PodaciTransakcijaActivity.class);
            intent.putExtra(PodaciTransakcijaActivity.PODACI_KEY , transaction3);
            startActivity(intent);
            return null;
        });



        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(transactionAdapter);



        recyclerViewModel.getPrihodi().observe(getViewLifecycleOwner(), prihodi -> {
            transactionAdapter.submitList(prihodi);
        });



    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == TRANSACTION_RECEIVED_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                Transaction t = (Transaction) data.getSerializableExtra(TRANSACTION_RECEIVED_MESSAGE);
                recyclerViewModel.updateTransactionPrihod(t);
            }
        }
    }
}
