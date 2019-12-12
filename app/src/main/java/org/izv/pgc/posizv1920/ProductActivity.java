package org.izv.pgc.posizv1920;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.SearchView;
import android.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import org.izv.pgc.posizv1920.model.data.Product;
import org.izv.pgc.posizv1920.view.CommandViewModel;
import org.izv.pgc.posizv1920.view.ProductViewAdapter;
import org.izv.pgc.posizv1920.view.ProductViewModel;
import org.izv.pgc.posizv1920.view.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    private ProductViewModel viewModel;
    private CommandViewModel commandViewModel;
    private SharedPreferences pref;
    private SearchView svProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        pref =  getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

        final Intent intent = getIntent();
        String titleToolbar = "Mesa " + intent.getStringExtra("mesa");
        final String idFactura = intent.getStringExtra("idfactura");
        String idEmpleado = pref.getString("idempleadoactivo", "1");


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(titleToolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fabFacturas);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProductActivity.this, CommandActivity.class);
                i.putExtra("mesa", intent.getStringExtra("mesa"));
                i.putExtra("idfactura", ""+ idFactura);
                startActivity(i);

            }
        });


        //Drawer con ActionBarDrawer Toogle
        DrawerLayout drawer = findViewById(R.id.drawer_product_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //evento Navigation
        NavigationView navigationView = findViewById(R.id.navigation2);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                //AQUI RECOGER LOS ITEM Y LANZAR LOS INTENT
                switch (menuItem.getItemId()){
                    case R.id.nav_drinks:
                        viewModel.getLiveProductListCategory(1);
                        return true;
                    case R.id.nav_salads:
                        viewModel.getLiveProductListCategory(2);
                        return true;
                    case R.id.nav_frisbees:
                        viewModel.getLiveProductListCategory(3);
                        return true;
                    case R.id.nav_rolls:
                        viewModel.getLiveProductListCategory(4);
                        return true;
                    case R.id.nav_kebabs:
                        viewModel.getLiveProductListCategory(5);
                        return true;
                    case R.id.nav_burgers:
                        viewModel.getLiveProductListCategory(6);
                        return true;
                    case R.id.nav_temptations:
                        viewModel.getLiveProductListCategory(7);
                        return true;
                }
                return false;
            }
        });


        RecyclerView rvList = findViewById(R.id.rvProductList);
        rvList.setLayoutManager(new GridLayoutManager(this, 4));
        commandViewModel = ViewModelProviders.of(this).get(CommandViewModel.class);
        final ProductViewAdapter adapter = new ProductViewAdapter(this, commandViewModel, idFactura, idEmpleado);
        rvList.setAdapter(adapter);

        svProducts = findViewById(R.id.svProducts);
        svProducts.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });


        viewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        viewModel.getLiveProductList().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable final List<Product> products) {
                // Update the cached copy of the words in the adapter.
                adapter.setData(products);
            }
        });
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
