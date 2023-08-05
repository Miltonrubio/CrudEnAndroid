package com.example.myapplication;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import java.util.List;

public class Adapter extends ArrayAdapter<Usuarios> {

    private  Context context;


    public Adapter(@NonNull Context context,  List<Usuarios> usuarios) {
        super(context, R.layout.list_usuario, usuarios);
        this.context = context;
    }

    List<Usuarios> usuariosList;


    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_usuario, parent, false);
        }

        ImageView imageViewFoto = view.findViewById(R.id.imageViewFoto);
        TextView tvCorreo = view.findViewById(R.id.textEmail);
        TextView tvNombre = view.findViewById(R.id.textNombre);
        TextView tvCel = view.findViewById(R.id.textCelular);

        Usuarios usuario = getItem(position);
        if (usuario != null) {
            tvCorreo.setText(usuario.getEmail());
            tvNombre.setText(usuario.getNombre());

            String id=usuario.getId();
            tvCel.setText(usuario.getPhone());


            String resource = usuario.getFoto();

            if (!TextUtils.isEmpty(resource)) { // Verifica si se encuentra el recurso con el nombre proporcionado
                Glide.with(context)
                        .load(R.drawable.a)
                        .into(imageViewFoto);
                tvNombre.setText(usuario.getFoto());
            } else {

                Glide.with(context)
                        .load(R.drawable.mob) // R.drawable.imagen_predeterminada sería tu imagen predeterminada en res/drawable
                        .into(imageViewFoto);
            }

        }

        return view;
    }


}
