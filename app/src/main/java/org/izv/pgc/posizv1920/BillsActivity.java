package org.izv.pgc.posizv1920;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.izv.pgc.posizv1920.model.data.Bill;
import org.izv.pgc.posizv1920.model.data.Command;
import org.izv.pgc.posizv1920.model.data.Product;
import org.izv.pgc.posizv1920.view.BillViewAdapter;
import org.izv.pgc.posizv1920.view.BillViewModel;
import org.izv.pgc.posizv1920.view.CommandViewModel;
import org.izv.pgc.posizv1920.view.ProductViewModel;

import java.util.ArrayList;
import java.util.List;

public class BillsActivity extends AppCompatActivity {

    Button btImprimir;
    AlertDialog dialog;
    Toolbar toolbar;

    List<Product> products = new ArrayList<>();
    List<Command> commands = new ArrayList<>();

    private BillViewModel viewModel;
    private CommandViewModel commandViewModel;
    private ProductViewModel productViewModel;

    private List<Command> listCommandEntregadas = new ArrayList<>();
    private List<Command> comandasAgrupadas;
    private List<Product> listProduct = new ArrayList<>();

    private BillViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btImprimir = findViewById(R.id.btImprimir);
        btImprimir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog();
            }
        });

        // Recibo la id de la factura
        final int idFactura = Integer.parseInt(getIntent().getStringExtra("idfactura"));
        // Comprobamos que recibimos la id de la factura
        Log.v("adaptador", "idFactura Bills: " + idFactura);

        // DATOS TEMPORALES AMIGO
        // test();

        // Con las comandas puedo comprobar dentro de la factura cuales estan entregadas ya, y recoger sus datos
        commandViewModel = ViewModelProviders.of(this).get(CommandViewModel.class);
        commandViewModel.getLiveCommandList().observe(this, new Observer<List<Command>>() {
            @Override
            public void onChanged(List<Command> commandList) {
                for (int i = 0; i < commandList.size(); i++) {
                    if (commandList.get(i).getEntregada() == 1) {
                        if (commandList.get(i).getIdfactura() == idFactura) {
                            listCommandEntregadas.add(commandList.get(i));
                            Log.v("adaptador", "listCommandEntregadas: " + listCommandEntregadas.size());
                        }
                    }
                }
                viewModelProductos();

                /*for (int i = 0; i < listProduct.size(); i++) {
                    precioTotal = precioTotal + listProduct.get(i).getPrecio();
                }
                tvPrecioTotal.setText("PRECIO TOTAL: " + precioTotal + "€");*/
            }
        });



    }

    private void viewModelProductos(){
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.getLiveProductList().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable final List<Product> products) {
                Log.v("listproductos", "Lista productos: " + products.size());
                for (int i = 0; i < products.size(); i++) {
                    Log.v("listproductos", "Comandas agrupadas: " + listCommandEntregadas.size());
                    for (int j = 0; j < listCommandEntregadas.size(); j++) {
                        if (products.get(i).getId() == listCommandEntregadas.get(j).getIdproducto()) {
                            listProduct.add(products.get(i));
                            Log.v("adaptador", "listProduct: " + listProduct.size());
                        }
                    }
                }
                pintarFactura();
                adapter.setData(listCommandEntregadas, listProduct);
                adapter.notifyDataSetChanged();


                // viewModelBills();
                // listProduct.addAll(products);
            }
        });

    }

    private void pintarFactura() {
        RecyclerView rvList = findViewById(R.id.rvBills);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BillViewAdapter(this, listCommandEntregadas, listProduct);
        rvList.setAdapter(adapter);
    }


    private void viewModelBills(){
        viewModel = ViewModelProviders.of(this).get(BillViewModel.class);
        viewModel.getLiveBillList().observe(this, new Observer<List<Bill>>() {
            @Override
            public void onChanged(@Nullable final List<Bill> bills) {
                // adapter.setData(comandasAgrupadas, listProduct);
                adapter.setData(listCommandEntregadas, listProduct);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void agruparComandas() {
        comandasAgrupadas = new ArrayList<>(); // instancio el arraylist nuevo
        Command comandaAux, comandaAux2; // creo 2 objetos auxiliares de la clase comanda
        boolean commandAdded;

        for (int i = 0; i < listCommandEntregadas.size(); i++) { // recorro el array de comandas actual (sin juntar)
            comandaAux2 = listCommandEntregadas.get(i); // en el objeto comandaAux2 tengo la comanda recorrida [0, 1, 2...]
            commandAdded = false; // asigno al booleano el valor false
            if (comandasAgrupadas.size() == 0) { // compruebo si el futuro arraylist está vacio
                comandasAgrupadas.add(comandaAux2); // si está vacio añado la primera comanda
            } else { // cuando ya haya minimo 1 comanda añadida...
                for (int j = 0; j < comandasAgrupadas.size(); j++) { // recorro el arraylist bueno
                    if (comandasAgrupadas.get(j).getIdproducto() == comandaAux2.getIdproducto()) { //
                        comandaAux = comandasAgrupadas.get(j);
                        comandaAux.setUnidades(comandaAux.getUnidades() + comandaAux2.getUnidades());
                        comandasAgrupadas.set(j, comandaAux);
                        commandAdded = true;
                    }
                }
                if (!commandAdded) {
                    comandasAgrupadas.add(comandaAux2);
                }
            }
            //Log.v(TAG, "comandas.size: " + comandasAgrupadas.size());
        }

        viewModelProductos();
    }

    private void test(){
        products.add(new Product(1, "cocacola", 1.25, "Mesa"));
        products.add(new Product(2, "avioneta", 3.30, "Mesa"));
        products.add(new Product(3, "cocacola2", 4.50, "Mesa"));
        products.add(new Product(4, "cocacola3", 1.00, "Mesa"));
        //private int id, idfactura, idproducto, idempleado, unidades, entregada;
        //    private double precio;
        commands.add(new Command(1, 1, 1, 4, 5, 1, 5*1.25));
        commands.add(new Command(2, 1, 3, 4, 5, 1, 5*4.50));
        commands.add(new Command(3, 1, 4, 4, 5, 1, 5*1.00));
        commands.add(new Command(4, 1, 2, 4, 5, 1, 5*3.30));
    }

    private void alertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(BillsActivity.this);
        builder.setPositiveButton(R.string.dialogYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                intentPrintActivity();
            }
        });
        builder.setNegativeButton(R.string.dialogNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });
        builder.setTitle(R.string.dialogTitle).setMessage(R.string.dialogMessage);

        dialog = builder.create();
        dialog.show();

    }

    private void intentPrintActivity() {

        Intent intent = new Intent(this, CustomPrintActivity.class);
        // intent.putExtra("arraylistCommands", (Parcelable) commandViewModel);
        intent.putParcelableArrayListExtra("arraylistCommands", (ArrayList<? extends Parcelable>) listCommandEntregadas);
        // intent.putExtra("arraylistProducts", (Parcelable) products);
        // intent.putParcelableArrayListExtra("arraylistProducts", (ArrayList<? extends Parcelable>) listProduct);
        startActivity(intent);

    }

}
