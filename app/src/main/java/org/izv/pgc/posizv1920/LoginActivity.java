package org.izv.pgc.posizv1920;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.izv.pgc.posizv1920.model.data.User;
import org.izv.pgc.posizv1920.view.UserViewAdapter;
import org.izv.pgc.posizv1920.view.UserViewModel;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static UserViewModel viewModel;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            // 1 LANZAR EL PROGRESSBAR
            RecyclerView rvList = findViewById(R.id.rvUserList);
            rvList.setLayoutManager(new GridLayoutManager(this, 4));

            final UserViewAdapter adapter = new UserViewAdapter(this);
            rvList.setAdapter(adapter);

            //viewModel = new ViewModelProvider(this).get(UserViewModel.class);
            viewModel = ViewModelProviders.of(this).get(UserViewModel.class);
            viewModel.getLiveUserList().observe(this, new Observer<List<User>>() {
                @Override
                public void onChanged(@Nullable final List<User> users) {
                    adapter.setData(users);
                    // 2 DESHABILITAR EL PROGRESSBAR
                }
            });

        }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(this /* Activity context */);
        String url = sharedPreferences.getString("url", "");
        viewModel.setUrl(url);
    }






    }
