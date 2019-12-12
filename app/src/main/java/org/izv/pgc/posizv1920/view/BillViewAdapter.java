package org.izv.pgc.posizv1920.view;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.izv.pgc.posizv1920.R;
import org.izv.pgc.posizv1920.model.data.Bill;
import org.izv.pgc.posizv1920.model.data.Command;
import org.izv.pgc.posizv1920.model.data.Product;

import java.util.ArrayList;
import java.util.List;

public class BillViewAdapter extends RecyclerView.Adapter<BillViewAdapter.BillHolder> {

    private LayoutInflater inflater;
    private List<Bill> billList;
    private Context context;
    private BillViewModel viewModel;

    private List<Command> commandList = new ArrayList<>(); // A esta lista le tienen que llegar las comandas de una misma factura con el entregada = 1
    private List<Product> productList = new ArrayList<>();

    public BillViewAdapter(Context context, BillViewModel viewModel) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.viewModel = viewModel;
    }

    public BillViewAdapter(Context context, List<Command> commandList, List<Product> productList) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.commandList = commandList;
        this.productList = productList;
    }

    @NonNull
    @Override
    public BillHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.myrecycler_bill_item, parent, false);
        return new BillHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BillHolder holder, int position) {
        if (commandList != null && productList != null) {
            Log.v("adaptador", "Position " + position);
            double total = commandList.get(position).getUnidades() * productList.get(position).getPrecio();
            total = total * 100;
            total = (int) total;
            total = total / 100;
            holder.tvNombreProducto.setText(productList.get(position).getNombre());
            Log.v("adaptador", "nombre producto " + position + " : " + productList.get(position).getNombre());
            holder.tvNumeroUnidades.setText(String.valueOf(commandList.get(position).getUnidades()));
            Log.v("adaptador", "unidades producto " + position + " : " + commandList.get(position).getUnidades());
            holder.tvPrecioUnidad.setText(String.valueOf(productList.get(position).getPrecio()));
            Log.v("adaptador", "precio producto " + position + " : " + productList.get(position).getPrecio());
            holder.tvPrecioFinal.setText(String.valueOf(total));
            Log.v("adaptador", "total producto " + position + " : " + total);
        } else {
            holder.tvNombreProducto.setText("No hay facturas"); ////
        }
    }

    @Override
    public int getItemCount() {
        int elements = 0;
        if (commandList != null) {
            elements = commandList.size();
        }
        Log.v("adaptador", "numero de elementos: " + elements);
        return elements;
    }

    public void setData(List<Command> commandList, List<Product> productList) {
        this.commandList = commandList;
        this.productList = productList;
        notifyDataSetChanged();
    }

    public class BillHolder extends RecyclerView.ViewHolder {
        private TextView tvNombreProducto, tvNumeroUnidades, tvPrecioUnidad, tvPrecioFinal;

        public BillHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreProducto = itemView.findViewById(R.id.tvNombreProducto);
            tvNumeroUnidades = itemView.findViewById(R.id.tvNumeroUnidades);
            tvPrecioUnidad = itemView.findViewById(R.id.tvPrecioUnidad);
            tvPrecioFinal = itemView.findViewById(R.id.tvPrecioFinal);
        }
    }
}

