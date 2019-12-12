package org.izv.pgc.posizv1920.view;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.izv.pgc.posizv1920.model.Repository;
import org.izv.pgc.posizv1920.model.data.Product;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {

    private Repository repository;

    public ProductViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    private LiveData<List<Product>> products;

    public List<Product> getUserList() {
        return repository.getProductList();
    }

    public LiveData<List<Product>> getLiveProductList() {
        return repository.getLiveProductList();
    }

    public void getLiveProductListCategory(int i) {
        repository.getLiveProductListCategory(i);
    }

    public void addProduct(Product product) {
        repository.addProduct(product);
    }

    public void setUrl(String url) {
        repository.setUrl(url);
    }



}


