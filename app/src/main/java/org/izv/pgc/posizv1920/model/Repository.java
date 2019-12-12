package org.izv.pgc.posizv1920.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.izv.pgc.posizv1920.contract.WaitResponse;
import org.izv.pgc.posizv1920.model.data.Bill;
import org.izv.pgc.posizv1920.model.data.Command;
import org.izv.pgc.posizv1920.model.data.Product;
import org.izv.pgc.posizv1920.model.data.User;
import org.izv.pgc.posizv1920.model.rest.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.izv.pgc.posizv1920.SplashActivity.TAG;

public class Repository {

    private ApiClient apiClient;
    private String url = "informatica.ieszaidinvergeles.org:8061";
    private int idFactura;

    public Repository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + url + "/posweb/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //.baseUrl("http://" + url + "/web/posweb/public/api/")
        //.baseUrl("http://izv.webestudios.net/api/")

        apiClient = retrofit.create(ApiClient.class);
        // LA LANZO EN CONSTRUCTOR POR QUE LA QUIERO ANTES DE QUE SE INICIE LA ACTIVITY PARA RELLENARLA
        //setUrl();
        /*fetchUserList();
        fetchProductList();
        fetchCommandList();
        fetchBillList();*/
        fetchCommandList();
}



    ///////////////// EMPLEADOS USUARIOS ///////////////////////////////////

    private List<User> userList = new ArrayList<>();
    private MutableLiveData<List<User>> liveUserList = new MutableLiveData();

    public void fetchUserList() {
        Call<ArrayList<User>> call = apiClient.getUsers();
        call.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                Log.v(TAG, response.body().toString());
                userList = response.body();
                liveUserList.setValue(response.body());
            }
            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                Log.v(TAG, t.getLocalizedMessage());
                userList = new ArrayList<>();
            }
        });
    }

    public List<User> getUserList() {
        return userList;

    }

    public LiveData<List<User>> getLiveUserList(){
        fetchUserList();
        return liveUserList;
    }

    public void addUser(User user) {
        Call<Long> call = apiClient.postUser(user);
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                Log.v(TAG, response.body().toString());
                long resultado = response.body();
                if(resultado > 0){
                    fetchUserList();
                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.v(TAG, t.getLocalizedMessage());

            }
        });
    }


    ///////////////// Commands ///////////////////////////////////

    private List<Command> commandList = new ArrayList<>();
    private MutableLiveData<List<Command>> liveCommandList = new MutableLiveData();

    public void fetchCommandList() {
        Call<ArrayList<Command>> call = apiClient.getCommands();
        call.enqueue(new Callback<ArrayList<Command>>() {
            @Override
            public void onResponse(Call<ArrayList<Command>> call, Response<ArrayList<Command>> response) {
                Log.v(TAG, "onResponse:" + response.body().toString());
                commandList = response.body();
                liveCommandList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Command>> call, Throwable t) {
                Log.v(TAG, "fetch: " + t.getLocalizedMessage());
                commandList = new ArrayList<>();
            }
        });
    }

    public MutableLiveData<List<Command>> fetchComandas() {
        fetchCommandList();
        return liveCommandList;
    }

    public LiveData<List<Command>> getLiveCommandListBill(int i) {
        Call<ArrayList<Command>> call = apiClient.getCommandsBill(i);
        call.enqueue(new Callback<ArrayList<Command>>() {
            @Override
            public void onResponse(Call<ArrayList<Command>> call, Response<ArrayList<Command>> response) {
                Log.v(TAG, "onResponse:" + response.body().toString());
                commandList = response.body();
                liveCommandList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Command>> call, Throwable t) {
                Log.v(TAG, "fetch: " + t.getLocalizedMessage());
                commandList = new ArrayList<>();
            }
        });
        return liveCommandList;
    }

    public List<Command> getCommandList() {
        return commandList;
    }

    public LiveData<List<Command>> getLiveCommandList() {
        return liveCommandList;
    }

    public void addCommand(Command command) {
        Call<Boolean> call = apiClient.postCommand(command);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Boolean resultado = response.body();
                Log.v("xyz", "hola "+resultado);
                if (resultado) {
                    fetchCommandList();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.v(TAG, t.getLocalizedMessage());
            }
        });
    }

    public MutableLiveData<Integer> deleteLiveData = new MutableLiveData<>();
    public void deleteCommand(Command command){
        Log.v("jeje", "delete");
        int idComanda = command.getId();
        Log.v("jeje", ""+idComanda);
        int idProducto = command.getIdproducto();
        //Call<Integer> call = apiClient.deleteCommand(idFactura, idProducto);
        Call<Integer> call = apiClient.deleteCommand(idComanda);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                deleteLiveData.setValue(response.body());
                Log.v("delete", "SUCCESS");
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                deleteLiveData = new MutableLiveData<>();
                Log.v("delete", t.getLocalizedMessage());
            }
        });
    }

    public void deleteCommandWithId(long id){
        //Call<Integer> call = apiClient.deleteCommand(idFactura, idProducto);
        Call<Integer> call = apiClient.deleteCommand(id);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                deleteLiveData.setValue(response.body());
                Log.v("respuesta", "SUCCESS");
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                deleteLiveData = new MutableLiveData<>();
                Log.v("respuesta", t.getLocalizedMessage());
            }
        });
    }

    public void deleteComanda(Command comanda) {
        deleteCommand(comanda);
    }

    public void editComanda(Command command){
        int idComanda = command.getId();
        Call<Boolean> call = apiClient.putCommand(idComanda, command);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                Log.v("edit", "SUCCESS");
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.v("edit", t.getLocalizedMessage());
            }
        });
    }

    public void editarComanda(Command comanda){
        editComanda(comanda);
    }

    //////////////////////// PRODUCTOS ////////////////////////////
    private List<Product> productList = new ArrayList<>();
    private MutableLiveData<List<Product>> liveProductList = new MutableLiveData();

    public void fetchProductList() {
        Call<ArrayList<Product>> call = apiClient.getProducts();
        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                Log.v(TAG, "onResponse:" + response.body().toString());
                productList = response.body();
                liveProductList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                Log.v(TAG, "fetch: " + t.getLocalizedMessage());
                productList = new ArrayList<>();
            }
        });
    }

    public void getLiveProductListCategory(int i) {
        Call<ArrayList<Product>> call = apiClient.getProductsCategory(i);
        call.enqueue(new Callback<ArrayList<Product>>() {
            @Override
            public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {
                Log.v(TAG, "onResponse:" + response.body().toString());
                productList = response.body();
                liveProductList.setValue(response.body());
                Log.d("PRODUCTOSCAT",""+liveProductList.toString());
            }

            @Override
            public void onFailure(Call<ArrayList<Product>> call, Throwable t) {
                Log.v(TAG, "fetch: " + t.getLocalizedMessage());
                productList = new ArrayList<>();
                Log.d("PRODUCTOSCAT",""+liveProductList.toString());
            }
        });
    }

    public List<Product> getProductList() {
        return productList;
    }

    public LiveData<List<Product>> getLiveProductList() {
        fetchProductList();
        return liveProductList;
    }

    public void addProduct(Product product) {
        Call<Long> call = apiClient.postProduct(product);
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                Log.v(TAG, response.body().toString());
                long resultado = response.body();
                if (resultado > 0) {
                    fetchProductList();
                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.v(TAG, t.getLocalizedMessage());
            }
        });

    }

    //////////////////////// FACTURAS ////////////////////////////
    private List<Bill> billList = new ArrayList<>();
    private MutableLiveData<List<Bill>> liveBillList = new MutableLiveData();

    public void fetchBillList() {
        Call<ArrayList<Bill>> call = apiClient.getBills();
        call.enqueue(new Callback<ArrayList<Bill>>() {
            @Override
            public void onResponse(Call<ArrayList<Bill>> call, Response<ArrayList<Bill>> response) {
                Log.v(TAG, "onResponse:" + response.body().toString());
                billList = response.body();
                liveBillList.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Bill>> call, Throwable t) {
                Log.v(TAG, "fetch: " + t.getLocalizedMessage());
                billList = new ArrayList<>();
            }
        });
    }

    public List<Bill> getBillList() {
        return billList;
    }

    public LiveData<List<Bill>> getLiveBillList() {
        fetchBillList();
        return liveBillList;
    }

    public void addBill(Bill bill) {
        Log.v("repository", bill.toString());
        Call<Long> call = apiClient.postBill(bill);
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                Log.v(TAG, response.body().toString());
                long resultado = response.body();
                if (resultado > 0) {
                    // idFactura = resultado;
                    fetchBillList();
                }
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.v(TAG, t.getLocalizedMessage());
            }
        });

    }
/*
    public Integer getIdFactura(int mesa) {
        Log.v("rnmummesa", "rnmesa"+mesa);
        Call<Bill> call = apiClient.getBillMesa(mesa);
        call.enqueue(new Callback<Bill>() {
            @Override
            public void onResponse(Call<Bill> call, Response<Bill> response) {
                Log.v("response", "r3spons32");
            }

            @Override
            public void onFailure(Call<Bill> call, Throwable t) {
                Log.v("failure", "failure");
            }
        });
        //Log.e("laura",""+idFactura);
        idFactura = 0;
        return idFactura;
    }*/

    public void deleteBill(Bill bill) {
        deleteBill(bill);
    }

    public void editBill(Bill bill){
        int idBill = bill.getId();
        Call<Integer> call = apiClient.putBill(idBill, bill);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                Log.v("delete", "SUCCESS");
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.v("delete", t.getLocalizedMessage());
            }
        });
    }

    public void editarBill(Bill bill){
        editBill(bill);
    }


    /////////////////   SET URL SERVER //////////////////////////

    public void setUrl(String url) {
        retrieveApiClient(url);
    }

    private void retrieveApiClient(String url) {
        this.url = url;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://" + url + "/posweb/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //.baseUrl("http://" + url + "/web/posweb/public/api/")
        //.baseUrl("http://izv.webestudios.net/api/")

        apiClient = retrofit.create(ApiClient.class);

    }

    public int getIdBillByMesa(Integer numMesa, final WaitResponse waitResponse) {
        Call<Integer> call = apiClient.getBillMesa(numMesa);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.body() == null){
                    waitResponse.doTheJob(false,0);
                }else{
                    idFactura = response.body();
                    waitResponse.doTheJob(true, idFactura);
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                idFactura = 0;
            }
        });
        return idFactura;
    }

    public MutableLiveData<Integer> deleteComanda(long id) {
        deleteCommandWithId(id);
        return deleteLiveData;

    }
}
