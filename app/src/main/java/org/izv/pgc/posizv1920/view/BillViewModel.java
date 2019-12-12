package org.izv.pgc.posizv1920.view;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.izv.pgc.posizv1920.contract.WaitResponse;
import org.izv.pgc.posizv1920.model.Repository;
import org.izv.pgc.posizv1920.model.data.Bill;

import java.util.List;

public class BillViewModel extends AndroidViewModel {

    private Repository repository;

    public BillViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
        bills = repository.getLiveBillList();
    }

    private LiveData<List<Bill>> bills;

    public List<Bill> getBillList() {
        return repository.getBillList();
    }

    public LiveData<List<Bill>> getLiveBillList() {
        return repository.getLiveBillList();
    }

    public void addBill(Bill bill) {
       // Log.v("view model", "hola");
        //Log.v("view model", bill.toString());
        repository.addBill(bill);
    }
    /*public Integer getIdFactura(int mesa) {
        return repository.getIdFactura(mesa);
    }*/
    public void deleteBill(Bill bill){
        repository.deleteBill(bill);
    }

    public void editBill(Bill bill){
        repository.editBill(bill);

    }

    public void setUrl(String url) {
        repository.setUrl(url);
    }

    public int getIdBillByMesa(Integer numMesa, WaitResponse waitResponse) {
        return repository.getIdBillByMesa(numMesa,waitResponse);
    }
}