package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    Adapter adapter;


    public static ArrayList<Usuarios> usuariosArrayList = new ArrayList<>();

    Usuarios usuarios;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listViewDatos);
        adapter = new Adapter(this, usuariosArrayList);

        listView.setAdapter(adapter);
        ListarDatos();

    }

    public void agregar(View view) {
        Intent intent = new Intent(MainActivity.this, ActivityInsertar.class);
        startActivity(intent);
    }
    public void ListarDatos() {

        StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.1.107/android/mostrar.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        usuariosArrayList.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String exito = jsonObject.getString("exito");
                            JSONArray jsonArray = jsonObject.getJSONArray("datos");

                            if (exito.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String id = object.getString("ID");
                                    String nombre = object.getString("nombre");
                                    String email = object.getString("email");
                                    String pass = object.getString("pass");
                                    String phone = object.getString("phone");
                                    String foto = object.getString("foto");

                                    usuarios = new Usuarios(id, nombre, email, pass, phone,foto);
                                    usuariosArrayList.add(usuarios);
                                    adapter.notifyDataSetChanged();
                                }
                            } else {

                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(MainActivity.this, "Error al obtener los datos", Toast.LENGTH_SHORT).show();
            }

        }) ;

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);
    }

}