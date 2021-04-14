package com.example.rafwallet.models;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;


import com.example.rafwallet.R;

import java.util.function.Function;

public class TransactionAdapter  extends ListAdapter<Transaction, TransactionAdapter.ViewHolder> {


    private final Function<Transaction, Void> onDeleteClicked;
    private final Function<Transaction, Void> onEditClicked;
    private final Function<Transaction, Void> onTransactionClicked;

    public TransactionAdapter(@NonNull DiffUtil.ItemCallback<Transaction> diffCallback, Function<Transaction, Void> onDeleteClicked, Function<Transaction, Void> onEditClicked, Function<Transaction, Void> onTransactionClicked ) {
        super(diffCallback);
        this.onDeleteClicked = onDeleteClicked;
        this.onEditClicked = onEditClicked;
        this.onTransactionClicked = onTransactionClicked;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_list_item, parent, false);
        return new ViewHolder(view, position -> {
            Transaction transaction = getItem(position);
            onDeleteClicked.apply(transaction);
            return null;
        }, position2 -> {
            Transaction transaction = getItem(position2);
            onEditClicked.apply(transaction);
            return null;
        }, position3 ->{
            Transaction transaction = getItem(position3);
            onTransactionClicked.apply(transaction);
            return null;
        });
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Transaction transaction = getItem(position);
        holder.bind(transaction);

    }



    public static class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(@NonNull View itemView, Function<Integer, Void> onDeleteClicked, Function<Integer, Void> onEditClicked, Function<Integer, Void> onItemClicked) {
            super(itemView);

            // za brisanje
            ImageView iv = itemView.findViewById(R.id.kanta);
            iv.setOnClickListener(v->{
                if (getAdapterPosition() != RecyclerView.NO_POSITION){
                    onDeleteClicked.apply(getAdapterPosition());
                }
            });

            ImageView iv2 = itemView.findViewById(R.id.olovka);
            iv2.setOnClickListener(v->{
                if (getAdapterPosition() != RecyclerView.NO_POSITION){
                    onEditClicked.apply(getAdapterPosition());
                }
            });

            itemView.setOnClickListener(v -> {
                if(getAdapterPosition() != RecyclerView.NO_POSITION) {
                    onItemClicked.apply(getAdapterPosition());
                }
            });




        }


        public void bind(Transaction transaction) {
            ImageView zeleni = itemView.findViewById(R.id.zeleniDolar);

            if (!transaction.isPrihod()){
                zeleni.setColorFilter(Color.RED);
            }else{
                zeleni.setColorFilter(Color.parseColor("#00B600"));
            }

            ((TextView)itemView.findViewById(R.id.naslovListe)).setText(transaction.getNaslov());
            ((TextView)itemView.findViewById(R.id.kolicina)).setText(String.valueOf(transaction.getKolicina()));

        }
    }

}
