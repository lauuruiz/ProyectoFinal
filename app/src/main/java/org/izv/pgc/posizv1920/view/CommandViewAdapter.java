package org.izv.pgc.posizv1920.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myitemselector.MyItemSelector;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import org.izv.pgc.posizv1920.CommandActivity;
import org.izv.pgc.posizv1920.R;
import org.izv.pgc.posizv1920.model.data.Command;

import java.util.ArrayList;
import java.util.List;

public class CommandViewAdapter extends RecyclerView.Adapter<CommandViewAdapter.CommandHolder> {

    private LayoutInflater inflater;
    private List<Command> commandList;
    private View.OnClickListener listener;
    private CommandViewModel viewModel;
    private Context context;
    private ArrayList<String> nombreProductos;
    LifecycleOwner lifecycleOwner;

    public CommandViewAdapter(Context context, CommandViewModel viewModel, LifecycleOwner lifecycleOwner) {
        this.viewModel = viewModel;
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.lifecycleOwner = lifecycleOwner;
    }

    @NonNull
    @Override
    public CommandHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.myrecycler_command_item, parent, false);

        return new CommandHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CommandHolder holder, int position) {
        if (commandList != null) {
            final Command current = commandList.get(position);
            holder.tvNombreProducto.setText(nombreProductos.get(position));
            holder.itemSelector.setValue(current.getUnidades());
            holder.itemSelector.setMinValue(1);

            final int pos = position;

            holder.ibtQuitarComanda.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewModel.deleteCommand(current.getId()).observe(lifecycleOwner, new Observer<Integer>() {
                        @Override
                        public void onChanged(Integer integer) {
                            try {
                                if (integer == current.getId()) {
                                    viewModel.returnComandas().observe(lifecycleOwner, new Observer<List<Command>>() {
                                        @Override
                                        public void onChanged(List<Command> commands) {
                                            if (commandList != commands) {
                                                commandList.remove(current);
                                                notifyItemRemoved(pos);
                                            }
                                        }
                                    });
                                }
                            } catch (Exception e) {
                                Log.v("respuesta", current.getId() + "");
                                try {
                                    Log.v("respuesta", integer + "");
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    });

                }
            });

            holder.itemSelector.plusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.itemSelector.incrementValue();
                    //Log.v("itemselector", current.toString());
                    current.setUnidades(holder.itemSelector.getValue());

                    viewModel.editCommand(current);
                    Log.v("itemselector", current.toString());

                }
            });

            holder.itemSelector.minusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.itemSelector.decrementValue();
                    //Log.v("itemselector", current.toString());
                    current.setUnidades(holder.itemSelector.getValue());

                    viewModel.editCommand(current);
                    Log.v("itemselector", current.toString());

                }
            });

        } else {
            holder.tvNombreProducto.setText("No commands available");
        }
    }

    @Override
    public int getItemCount() {
        int elements = 0;
        if (commandList != null) {
            elements = commandList.size();
        }
        return elements;
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    public void onClick(View view){
        if(listener != null){
            listener.onClick(view);
        }
    }

    public void setData(List<Command> commandList, ArrayList<String> nombreProductos) {
        this.commandList = commandList;
        this.nombreProductos = nombreProductos;
        notifyDataSetChanged();
    }

    public class CommandHolder extends RecyclerView.ViewHolder {
        private final ImageButton ibtQuitarComanda;
        private final TextView tvNombreProducto;
        private final MyItemSelector itemSelector;

        public CommandHolder(@NonNull View itemView) {
            super(itemView);
            ibtQuitarComanda = itemView.findViewById(R.id.ibtQuitarComanda);

            itemSelector = itemView.findViewById(R.id.itemSelector);

            tvNombreProducto = itemView.findViewById(R.id.tvNombreProducto);

        }
    }
}

