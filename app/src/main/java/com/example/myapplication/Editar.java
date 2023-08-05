package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Editar extends AppCompatActivity {

TextView textViewid;
    EditText etNombre, etEmail, etPassword, etPhone;
    Button btnEnviar;

    RequestQueue requestQueue;

private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        requestQueue = Volley.newRequestQueue(this);

        etNombre = findViewById(R.id.editNombre2);
        etEmail = findViewById(R.id.editEmail2);
        etPassword = findViewById(R.id.editPassword2);
        etPhone = findViewById(R.id.editPhone2);
        btnEnviar = findViewById(R.id.btnActualizar);

        textViewid= findViewById(R.id.textViewID);


        Intent intent =    getIntent();
        position= intent.getExtras().getInt("position");



        etNombre.setText(MainActivity.usuariosArrayList.get(position).getNombre());
        etEmail.setText(MainActivity.usuariosArrayList.get(position).getEmail());
        etPassword.setText(MainActivity.usuariosArrayList.get(position).getPass());
        etPhone.setText(MainActivity.usuariosArrayList.get(position).getPhone());


        textViewid.setText(MainActivity.usuariosArrayList.get(position).getId());

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarDatos();
            }
        });

    }


    private void editarDatos() {


String id = textViewid.getText().toString().trim();
        String nombre = etNombre.getText().toString().trim();
        String correo = etEmail.getText().toString().trim();
        String contra = etPassword.getText().toString().trim();
        String celular = etPhone.getText().toString().trim();

        if (nombre.isEmpty()) {
            Toast.makeText(this, "Ingresa el nombre", Toast.LENGTH_SHORT).show();
        } else if (correo.isEmpty()) {
            Toast.makeText(this, "Ingresa el correo", Toast.LENGTH_SHORT).show();
        } else if (contra.isEmpty()) {
            Toast.makeText(this, "Ingresa la contraseña", Toast.LENGTH_SHORT).show();
        } else if (celular.isEmpty()) {
            Toast.makeText(this, "Ingresa el celular", Toast.LENGTH_SHORT).show();
        } else {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Actualizando...");
            progressDialog.show();

            StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.1.107/android/editar.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            String trimmedResponse = response.trim();

                            if (trimmedResponse.equalsIgnoreCase("No Actualizado")) {
                                Toast.makeText(Editar.this, "Error al editar", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            } else {

                                Toast.makeText(Editar.this, "Registrado correctamente", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }
                        }
                    }, new Response.ErrorListener() {

                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(Editar.this, "Error en la solicitud: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("id", id);
                    params.put("nombre", nombre);
                    params.put("email", correo);
                    params.put("password", contra);
                    params.put("phone", celular);

                    return params;
                }
            };

            RequestQueue requestQueue1 = Volley.newRequestQueue(Editar.this);
            requestQueue1.add(request);
        }
    }


    public void onClick(View view) {

    }
}
