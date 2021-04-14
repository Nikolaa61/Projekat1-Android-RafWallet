package com.example.rafwallet.models;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

public class TransactionDiffItemCallback  extends DiffUtil.ItemCallback<Transaction>{
    @Override
    public boolean areItemsTheSame(@NonNull Transaction oldItem, @NonNull Transaction newItem) {
        return oldItem.getId() == newItem.getId();
    }

    @Override
    public boolean areContentsTheSame(@NonNull Transaction oldItem, @NonNull Transaction newItem) {

        if (oldItem.getFilePath() == null || newItem.getFilePath() == null){
            return oldItem.getNaslov().equals(newItem.getNaslov())
                    && oldItem.getKolicina() == newItem.getKolicina()
                    && oldItem.getOpis().equals(newItem.getOpis());
        }else{
            return oldItem.getNaslov().equals(newItem.getNaslov())
                    && oldItem.getKolicina() == newItem.getKolicina()
                    && oldItem.getFilePath().equals(newItem.getFilePath());
        }

    }
}
