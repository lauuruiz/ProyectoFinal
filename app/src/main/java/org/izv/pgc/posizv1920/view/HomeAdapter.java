package org.izv.pgc.posizv1920.view;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.izv.pgc.posizv1920.BillsActivity;
import org.izv.pgc.posizv1920.ProductActivity;
import org.izv.pgc.posizv1920.R;
import org.izv.pgc.posizv1920.model.data.Bill;

import java.sql.Timestamp;
import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private onItemClickListener listener;
    private int contador;
    private int nmesas;
    private String mesasKo;


    public interface onItemClickListener{
        public void onItemClick(Integer i);
    }

    public HomeAdapter(Context context, onItemClickListener listener, String mesasKo) {
        inflater = LayoutInflater.from(context);
        this.listener = listener;
        this.context = context;
        this.mesasKo = mesasKo;
    }

    @NonNull
    @Override
    public HomeAdapter.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.myrecycler_home_item, parent, false);
        return new HomeViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.HomeViewHolder holder, final int position) {
        contador = 1 + position;
        holder.tvItem.setText("" + contador);
        holder.tvItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(view.getContext(), "has pulsado "+position,Toast.LENGTH_LONG).show();
                listener.onItemClick(position+1);
            }
        });

        if (mesasKo != null) {
            String[] mesasOcupadas = mesasKo.split(",");

            for (int i = 0; i < mesasOcupadas.length; i++) {
                System.out.println(mesasKo.split(",").toString());
                if (Integer.parseInt(mesasOcupadas[i]) == contador) {
                    holder.tvItem.setTextColor(Color.RED);
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        Log.v("xyzyx", "getItemCount: " + nmesas);
        int elementos = 0;
            elementos = nmesas;
        return elementos;
    }

    public void setMesas(Integer nmesas) {
        this.nmesas = nmesas;
        notifyDataSetChanged();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvItem;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.nMesa);
        }
    }
}
