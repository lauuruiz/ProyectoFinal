package org.izv.pgc.posizv1920;


import android.content.Intent;
import android.os.Bundle;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.izv.pgc.posizv1920.model.data.Command;
import org.izv.pgc.posizv1920.model.data.Product;
import org.izv.pgc.posizv1920.view.CommandViewAdapter;
import org.izv.pgc.posizv1920.view.CommandViewModel;
import org.izv.pgc.posizv1920.view.ProductViewModel;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CommandActivity extends AppCompatActivity {

    private CommandViewModel viewModel;
    private RecyclerView rvList;
    private List<Command> listCommandAux = new ArrayList<>();
    private CommandViewAdapter adapter;
    private ProductViewModel productViewModel;

    private FloatingActionButton fabFacturas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        String titleToolbar = intent.getStringExtra("mesa");

        final int idFactura = Integer.parseInt(intent.getStringExtra("idfactura"));
        Log.v("COMMANDACTIVITY", "mesa numero: "+titleToolbar);
        Log.v("COMMANDACTIVITY", "FACTURA numero: "+idFactura);

        fabFacturas = findViewById(R.id.fabFacturas);
        fabFacturas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommandActivity.this, BillsActivity.class);
                intent.putExtra("idfactura", ""+ idFactura);
                startActivity(intent);
            }
        });

        viewModel = ViewModelProviders.of(this).get(CommandViewModel.class);
        adapter = new CommandViewAdapter(this, viewModel, this);

        viewModel.getLiveCommandList().observe(this, new Observer<List<Command>>() {
            @Override
            public void onChanged(@Nullable final List<Command> commands) {

                for (int i = 0; i < commands.size(); i++) {
                    if (commands.get(i).getIdfactura() == idFactura) {
                        if (commands.get(i).getEntregada() == 0) {
                            listCommandAux.add(commands.get(i));
                        }
                    }
                }

                viewModelProductos();

                Log.v("productolista", listCommandAux.toString());
            }
        });

        rvList = findViewById(R.id.rvCommandList);
        rvList.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tvNombreProducto = findViewById(R.id.tvNombreProducto);
                TextView tvCantidad = findViewById(R.id.valueTextView);

                int id = Integer.parseInt(tvNombreProducto.getText().toString());
                int cantidad = Integer.parseInt(tvCantidad.getText().toString());

                tvNombreProducto.setText(id);
                tvCantidad.setText(cantidad);
            }
        });
        
        Log.v("jeje", "gggg");
        adapter.notifyDataSetChanged();
        rvList.setAdapter(adapter);
    }

    private void viewModelProductos(){
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.getLiveProductList().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable final List<Product> products) {
                ArrayList<String> nombreProductos = new ArrayList<>();
                for (int i = 0; i < products.size(); i++) {
                    for (int j = 0; j < listCommandAux.size(); j++) {
                        if (products.get(i).getId() == listCommandAux.get(j).getIdproducto()) {
                            nombreProductos.add(products.get(i).getNombre());
                        }
                    }
                }

                Log.v("productolista", listCommandAux.toString());
                adapter.setData(listCommandAux, nombreProductos);
            }
        });
    }

}
