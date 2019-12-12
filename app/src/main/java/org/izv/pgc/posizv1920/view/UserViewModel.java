package org.izv.pgc.posizv1920.view;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.izv.pgc.posizv1920.model.data.User;
import org.izv.pgc.posizv1920.model.Repository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private Repository repository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
    }

    private LiveData<List<User>> users;

    public List<User> getUserList(){
        return repository.getUserList();
    }

    public LiveData<List<User>> getLiveUserList(){
        return repository.getLiveUserList();
    }

    public void addUser(User user) {
        repository.addUser(user);
    }

    public void setUrl(String url) {
        repository.setUrl(url);
    }
}


