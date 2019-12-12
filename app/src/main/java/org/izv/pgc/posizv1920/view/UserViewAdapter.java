package org.izv.pgc.posizv1920.view;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.izv.pgc.posizv1920.AlertLoginActivity;
import org.izv.pgc.posizv1920.R;
import org.izv.pgc.posizv1920.model.data.User;

import java.util.List;

public class UserViewAdapter extends RecyclerView.Adapter<UserViewAdapter.ItemHolder> {

    private LayoutInflater inflater;
    private List<User> userList;
    private Context context;

    public UserViewAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }


    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.myrecycler_user_item, parent, false);
        return new ItemHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        if (userList != null) {
            final User current = userList.get(position);
            holder.nombreUser.setText(current.getLogin());
            holder.imgUser.setImageResource(R.drawable.man_waiter);

            holder.imgUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, AlertLoginActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("user", current);
                    intent.putExtras(bundle);
                    context.startActivity(intent);


                }
            });

        } else {
            holder.nombreUser.setText("No Users available");
        }

    }

    @Override
    public int getItemCount() {
        int elements = 0;
        if(userList != null){
            elements = userList.size();
        }
        return elements;
    }

    public void setData(List<User> userList){
        this.userList = userList;
        notifyDataSetChanged(); //actualizar la listas
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        private final TextView nombreUser;
        private final ImageView imgUser;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            nombreUser = itemView.findViewById(R.id.nMesa);
            imgUser = itemView.findViewById(R.id.imgUser);
        }
    }
}