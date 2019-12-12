package org.izv.pgc.posizv1920;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.izv.pgc.posizv1920.model.data.User;

import java.util.regex.Pattern;

public class AlertLoginActivity extends AppCompatActivity {

    private TextInputLayout etLUser, etLPassword;
    TextInputEditText etUser, etPassword;
    CardView btLogin;
    private User user;
    private String iduser, login, clave;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_login);
        initBlundeReciver();
        initComponents();
        initEvents();
    }

    private void initBlundeReciver() {
        Bundle userFecht = getIntent().getExtras();
        user = new User();
        if (userFecht != null) {
            user = (User) userFecht.getParcelable("user");
            iduser = "" + user.getId();
            login = user.getLogin();
            clave = user.getClave();

        }
    }

    private void initComponents() {
        etLUser = findViewById(R.id.etLUser);
        etLPassword = findViewById(R.id.etLPassword);
        etUser = findViewById(R.id.etUser);
        etPassword = findViewById(R.id.etPassword);
        btLogin = findViewById(R.id.boton_aceptar);

    }


    private void initEvents() {
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validarDatos();
            }
        });
        etUser.setText(login);
        etUser.addTextChangedListener(new AlertLoginActivity.ValidationTextWatcher(etUser));
        etPassword.addTextChangedListener(new AlertLoginActivity.ValidationTextWatcher(etPassword));

    }

    private void validarDatos() {
        String user = etLUser.getEditText().getText().toString();
        String password = etLPassword.getEditText().getText().toString();

        boolean a = esUserValido(user);
        boolean b = esPasswordValido(password);

        if (a && b) {
            if (Integer.parseInt(etPassword.getText().toString()) != Integer.parseInt(clave)) {
                Toast.makeText(this, "Existen errores", Toast.LENGTH_LONG).show();
            } else {

               sharedPreferences = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
               editor = sharedPreferences.edit();
               editor.putString("idempleadoactivo", iduser);
               editor.commit();

                //Toast.makeText(this, "Se guarda el registro", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        } else {
            Toast.makeText(this, "Existen errores", Toast.LENGTH_LONG).show();

        }


    }

    private boolean esUserValido(String user) {
        Pattern patron = Pattern.compile("^[a-zA-Z0-9]+$");
        if (!patron.matcher(user).matches()) {
            etLUser.setError("Nombre de Usuario inválido");
            return false;
        } else {
            etLUser.setError(null);
        }

        return true;
    }

    private boolean esPasswordValido(String pass) {
        Pattern patron = Pattern.compile("^[a-zA-Z0-9]+$");
        if (!patron.matcher(pass).matches() || pass.length() > 4) {
            etLPassword.setError("Contraseña de Usuario inválido");
            return false;
        } else {
            etLPassword.setError(null);
        }

        return true;
    }

    private class ValidationTextWatcher implements TextWatcher {

        private View view;

        private ValidationTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            switch (view.getId()) {
                case R.id.etPassword:
                    esPasswordValido("" + charSequence);
                    break;
                case R.id.etUser:
                    esUserValido("" + charSequence);
                    break;
            }
        }

        public void afterTextChanged(Editable editable) {

        }
    }
}
