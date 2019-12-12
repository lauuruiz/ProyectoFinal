package org.izv.pgc.posizv1920;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.izv.pgc.posizv1920.contract.WaitResponse;
import org.izv.pgc.posizv1920.model.data.Bill;
import org.izv.pgc.posizv1920.view.BillViewAdapter;
import org.izv.pgc.posizv1920.view.BillViewModel;
import org.izv.pgc.posizv1920.view.HomeAdapter;
import org.izv.pgc.posizv1920.view.ProductViewModel;
import org.izv.pgc.posizv1920.view.UserViewModel;
import org.izv.pgc.posizv1920.view.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private BillViewModel viewModel;
    private HomeAdapter adapter;
    private int nmesas;
    private String mesasKo;
    private String idempleadoactivo;
    private RecyclerView rvList;
    private int idFactura;
    private ArrayList<Bill> listaBills;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        pref =  getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        editor = pref.edit();
        editor.putInt("mesas", 10);
        editor.commit();
        mesasKo = pref.getString("mesasocupadas", null);
        Log.v("mesasocupadas", "mesasocupadas: " + mesasKo);

        nmesas =  pref.getInt("mesas", 1);
        idempleadoactivo = pref.getString("idempleadoactivo", "1");


        //Drawer con ActionBarDrawer Toogle
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //evento Navigation
        NavigationView navigationView = findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                //AQUI RECOGER LOS ITEM Y LANZAR LOS INTENT
                switch (menuItem.getItemId()){
                    case R.id.menuLogOut:
                        cerrarSesion();
                        return true;
                }
                return true;
            }
        });


        rvList = findViewById(R.id.rvMesasList);
        rvList.setLayoutManager(new GridLayoutManager(this, 3));

        viewModel = ViewModelProviders.of(this).get(BillViewModel.class);
        adapter = new HomeAdapter(this, new HomeAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Integer numMesa) {
                lanzarActividad(numMesa);
            }
        }, mesasKo);

        rvList.setAdapter(adapter);
                adapter.setMesas(nmesas);



    }

    private void lanzarActividad(final Integer numMesa) {
        boolean ocupada = false;
        //Toast.makeText(getApplicationContext(), "hola "+i, Toast.LENGTH_SHORT).show();
                /*
                CREAR LA FACTURA Y SETEA EL INTENT
                */

        if (mesasKo != null) {
            String[] mesasOcupadas = mesasKo.split(",");

            for (int i = 0; i < mesasOcupadas.length; i++) {
                //for (int j = 0; j < nmesas; j++) {
                    if (Integer.parseInt(mesasOcupadas[i]) == numMesa) {

                        ocupada = true;
                    }
                //}
            }
        }


        //Log.v("mesashome bill", bill.toString()+" ocupada: "+ocupada);
        if (!ocupada) {
            Bill bill = new Bill();
            bill.setIdempleadoinicio(Integer.parseInt(idempleadoactivo));
            bill.setMesa(numMesa);

            viewModel.addBill(bill);
            if (mesasKo != null) {

                String[] mesasOcupadas = mesasKo.split(",");

                    List<String> list = Arrays.asList(mesasOcupadas);

                    if(!list.contains(Integer.toString(numMesa))){
                        mesasKo = mesasKo + "," + numMesa;
                    }

            } else {
                mesasKo = "" + numMesa;
            }
            editor.putString("mesasocupadas", "" + mesasKo);
            editor.commit();
        }

        /*try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        listaBills = new ArrayList<Bill>();
        listaBills = (ArrayList<Bill>) viewModel.getBillList();*/
        //Log.v("jaja", ""+listaBills.get(0).toString());

        /*viewModel.getLiveBillList().observe(this, new Observer<List<Bill>>() {
            @Override
            public void onChanged(List<Bill> bills) {
                for (int i = 0; i < bills.size(); i++) {
                    if (bills.get(i).getMesa() == numMesa) {
                        Log.v("ss", bills.get(i).toString());
                        idFactura = bills.get(i).getId();
                    }
                }

            }

        });*/
        final Intent intent = new Intent(HomeActivity.this, ProductActivity.class);

        viewModel.getIdBillByMesa(numMesa, new WaitResponse() {
            @Override
            public void doTheJob(Boolean success, int idFactura) {
                //como recupero la id cuando hace el add el insert

                if (success){
                    Log.v("idfacturahome", "dddddddd"+idFactura);
                    intent.putExtra("mesa", "" + numMesa);
                    intent.putExtra("idfactura", ""+ idFactura);
                    startActivity(intent);
                }

            }
        });

        Log.v("idfacturahome", ""+idFactura);

        Log.v("nummesa", "nummesa "+numMesa);
        Log.v("idFactura", "idFactura "+idFactura);
        //viewModel.getIdFactura(numMesa);


        //idFactura = 152;


    }

    @Override
    protected void onResume() {
        super.onResume();

        adapter = new HomeAdapter(this, new HomeAdapter.onItemClickListener() {
            @Override
            public void onItemClick(Integer numMesa) {
                lanzarActividad(numMesa);
            }
        },mesasKo);

        rvList.setAdapter(adapter);
        adapter.setMesas(nmesas);

    }

    private void showSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_settings:
                showSettings();
                return true;
            case R.id.action_log_out:
                cerrarSesion();
                return true;
            case R.id.action_authors:
                showAuthor();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAuthor() {
        Intent intent = new Intent(this, AuthorActivity.class);
        startActivity(intent);
    }

    private void cerrarSesion() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
