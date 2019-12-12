package org.izv.pgc.posizv1920.view;


import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.izv.pgc.posizv1920.model.data.Command;
import org.izv.pgc.posizv1920.model.Repository;

import java.util.List;

public class CommandViewModel extends AndroidViewModel {

    private Repository repository;

    public CommandViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository();
        commands = repository.getLiveCommandList();
    }

    private LiveData<List<Command>> commands;

    public List<Command> getCommandList() {
        return repository.getCommandList();
    }

    public LiveData<List<Command>> returnComandas () {
        return repository.fetchComandas();
    }

    public LiveData<List<Command>> getLiveCommandList() {
        return repository.getLiveCommandList();
    }

    public LiveData<List<Command>> getLiveCommandListBill(int i) {
        return repository.getLiveCommandListBill(i);
    }


    public void addCommand(Command command) {
        repository.addCommand(command);
    }

    public void deleteCommand(Command command){
        repository.deleteComanda(command);
    }

    public void editCommand(Command command){
        repository.editarComanda(command);

    }

    public void setUrl(String url) {
        repository.setUrl(url);
    }

    public void getLiveCommandListCategory(int idFactura) {
    }

    public MutableLiveData<Integer> deleteCommand(long id) {
        return repository.deleteComanda(id);
    }
}