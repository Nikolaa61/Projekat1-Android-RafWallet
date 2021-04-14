package com.example.rafwallet.models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MojViewModel extends ViewModel {

    private final MutableLiveData<List<Transaction>> prihodi = new MutableLiveData<>();
    private final MutableLiveData<List<Transaction>> rashodi = new MutableLiveData<>();

    private final ArrayList<Transaction> prihodiList = new ArrayList<>();
    private final ArrayList<Transaction> rashodiList = new ArrayList<>();

    private User user;

    public static int ID = 0;

    public MojViewModel() {
        System.out.println("NAPRAVLJEN MOJ VIEW MODEL");
        for(int i=0; i<=50; i++) {
            Random r = new Random();
            int br = r.nextInt(10000);
            // int id, String naslov, int kolicina, String opis, boolean prihod
            Transaction transaction = new Transaction(ID, "Naslov", br, "Opis", true);
            ID++;
            prihodiList.add(transaction);
        }
        ArrayList<Transaction> listToSubmit = new ArrayList<>(prihodiList);
        prihodi.setValue(listToSubmit);


        for(int i=0; i<=50; i++) {
            Random r = new Random();
            int br = r.nextInt(10000);
            // int id, String naslov, int kolicina, String opis, boolean prihod
            Transaction transaction = new Transaction(ID, "Naslov", br, "Opis", false);
            ID++;
            rashodiList.add(transaction);
        }
        ArrayList<Transaction> listToSubmit1 = new ArrayList<>(rashodiList);
        rashodi.setValue(listToSubmit1);
        System.out.println("Zavrsio MOJVM");
    }

    public void removeTransaction(Transaction transaction) {
        if (transaction.isPrihod()) {
            prihodiList.remove(transaction);
            ArrayList<Transaction> listToSubmit = new ArrayList<>(prihodiList);
            prihodi.setValue(listToSubmit);
        }else{
            rashodiList.remove(transaction);
            ArrayList<Transaction> listToSubmit = new ArrayList<>(rashodiList);
            rashodi.setValue(listToSubmit);
        }
    }

    public MutableLiveData<List<Transaction>> getPrihodi() {
        return prihodi;
    }

    public MutableLiveData<List<Transaction>> getRashodi() {
        return rashodi;
    }

    public void addTransaction(Transaction transaction){
        if (transaction.isPrihod()){
            prihodiList.add(transaction);
            ArrayList<Transaction> listToSubmit = new ArrayList<>(prihodiList);
            prihodi.setValue(listToSubmit);
        }else{
            rashodiList.add(transaction);
            ArrayList<Transaction> listToSubmit = new ArrayList<>(rashodiList);
            rashodi.setValue(listToSubmit);
        }
    }

    public ArrayList<Transaction> getPrihodiList() {
        return prihodiList;
    }

    public ArrayList<Transaction> getRashodiList() {
        return rashodiList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void updateTransactionPrihod(Transaction t) {
        System.out.println("usao123123123123");
        Transaction tmp = null;
        for(Transaction transaction : prihodiList){
            if (transaction.getId() == t.getId()){
                System.out.println(t.getNaslov() + " " + transaction.getNaslov());
                tmp = transaction;
                System.out.println(t.getNaslov() +" " +transaction.getNaslov());
                break;
            }
        }
        removeTransaction(tmp);
        addTransaction(t);
    }

    public void updateTransactionRashod(Transaction t) {
        Transaction tmp = null;
        for(Transaction transaction : rashodiList){
            if (transaction.getId() == t.getId()){
                tmp=transaction;
                break;
            }
        }
        removeTransaction(tmp);
        addTransaction(t);
    }
}
