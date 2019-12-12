package org.izv.pgc.posizv1920.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.izv.pgc.posizv1920.R;
import org.izv.pgc.posizv1920.model.data.Command;
import org.izv.pgc.posizv1920.model.data.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductViewAdapter extends RecyclerView.Adapter<ProductViewAdapter.ProductHolder> implements Filterable {

    private LayoutInflater inflater;
    private List<Product> productList, listaAux;
    private Context context;
    private CommandViewModel viewModel;
    private String idFactura;
    private String idEmpleado;

    public ProductViewAdapter(Context context, CommandViewModel viewModel, String idFactura, String idEmpleado) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.viewModel = viewModel;
        this.idFactura = idFactura;
        this.idEmpleado = idEmpleado;
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.myrecycler_product_item, parent, false);
        return new ProductHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        if (productList != null) {
            final Product current = productList.get(position);
            holder.tvNombreProducto.setText("" + current.getNombre());
            //holder.imgProducto.setImageBitmap();

            holder.lc.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "" + current.getNombre(), Toast.LENGTH_SHORT).show();
                    Command command = new Command();
                    command.setIdfactura(Integer.parseInt(idFactura));
                    command.setIdproducto(Integer.parseInt(""+current.getId()));
                    command.setIdempleado(Integer.parseInt(idEmpleado));
                    command.setUnidades(1);
                    command.setPrecio(current.getPrecio());
                    // command.setEntregada(0);
                    Log.v("comandatostring: ", command.toString());
                    viewModel.addCommand(command);
                }
            });


        } else {
            holder.tvNombreProducto.setText("No products available");
        }
    }

    private Filter productFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Product> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(listaAux);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(Product product : listaAux){
                    if (product.getNombre().toLowerCase().contains(filterPattern)){
                        filteredList.add(product);
                    }
                }
            }
            FilterResults results =  new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            productList.clear();
            productList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    @Override
    public Filter getFilter() {
        return productFilter;
    }

    @Override
    public int getItemCount() {
        int elements = 0;
        if (productList != null) {
            elements = productList.size();
        }
        return elements;
    }

    public void setData(List<Product> productList) {
        this.productList = productList;
        listaAux = new ArrayList<>(productList);
        notifyDataSetChanged();
    }

    public class ProductHolder extends RecyclerView.ViewHolder {
        private final LinearLayout lc;
        private final ImageView imgProducto;
        private final TextView tvNombreProducto;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);
            lc = itemView.findViewById(R.id.lc);
            imgProducto = itemView.findViewById(R.id.imageItem);
            tvNombreProducto = itemView.findViewById(R.id.nameItem);

        }
    }
}

