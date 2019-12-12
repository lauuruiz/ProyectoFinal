package org.izv.pgc.posizv1920.model.rest;

import org.izv.pgc.posizv1920.model.data.Bill;
import org.izv.pgc.posizv1920.model.data.Command;
import org.izv.pgc.posizv1920.model.data.Product;
import org.izv.pgc.posizv1920.model.data.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiClient {

    // OPERACIONES DE USERS

    @DELETE("empleado/{id}")
    Call<Integer> deleteUser(@Path("id") long id);

    @GET("empleado/{id}")
    Call<User> getUser(@Path("id") long id);

    @GET("empleado")
    Call<ArrayList<User>> getUsers();

    @POST("empleado")
    Call<Long> postUser(@Body User user);

    @PUT("empleado/{id}")
    Call<Integer> putUser(@Path("id") long id, @Body User user);


    //OPERACIONES DE COMMANDS

    @DELETE("comanda/{id}")
    Call<Integer> deleteCommand(@Path("id") long id);

    @DELETE("comanda/{idfactura}/{idproducto}")
    Call<Integer> deleteCommand2(@Path("idfactura") int idfactura, @Path("idproducto") int idproducto);

    @GET("comanda/{id}")
    Call<Command> getCommand(@Path("id") int id);

    @GET("comanda")
    Call<ArrayList<Command>> getCommands();

    @GET("comanda/{id}")
    Call<ArrayList<Command>> getCommandsBill(@Path("id") int id);

    @POST("comanda")
    Call<Boolean> postCommand(@Body Command command);

    @PUT("comanda/{id}")
    Call<Boolean> putCommand(@Path("id") int id, @Body Command command);


    //OPERACIONES DE PRODUCTOS

    @DELETE("producto/{id}")
    Call<Integer> deleteProduct(@Path("id") int id);

    @GET("producto/{id}")
    Call<Product> getProduct(@Path("id") int id);

    @GET("producto")
    Call<ArrayList<Product>> getProducts();

    @POST("producto")
    Call<Long> postProduct(@Body Product product);

    @PUT("producto/{id}")
    Call<Integer> putProduct(@Path("id") int id, @Body Product product);

    @GET("producto/categoria/{id}")
    Call<ArrayList<Product>> getProductsCategory(@Path("id") int id);

    //OPERACIONES DE BILLS

    @DELETE("factura/{id}")
    Call<Integer> deleteBill(@Path("id") int id);

    @GET("factura/{id}")
    Call<Bill> getBill(@Path("id") int id);

    @GET("factura")
    Call<ArrayList<Bill>> getBills();

    @POST("factura")
    Call<Long> postBill(@Body Bill bill);

    @PUT("factura/{id}")
    Call<Integer> putBill(@Path("id") int id, @Body Bill bill);

    @GET("factura/mesa/{id}")
    Call<Integer> getBillMesa(@Path("id") int id);

}
