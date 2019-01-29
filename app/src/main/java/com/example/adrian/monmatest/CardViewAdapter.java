package com.example.adrian.monmatest;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {

    private static Context myContext;
    private View view;
    private ClipData.Item currentItem;


    //se crean las variables necesarias que se mostraran en el cardview
// Clase interna:
// Se implementa el ViewHolder que se encargara
// de almacenar la vista del elemento y sus datos
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView enunciado, categoria;
        private ImageView foto;

        //constructor de la clase
        public ViewHolder(View v) {

            super(v);
            enunciado = (TextView) v.findViewById(R.id.enunciado);
            categoria = (TextView) v.findViewById(R.id.categoria);
            foto = (ImageView) v.findViewById(R.id.foto);

        }

    }

    //almacena todos los datos mostrados en cada cardview
    public List<Pregunta> preguntas;

    //constructor del adaptador que recibe como parametro la lista creada
    public CardViewAdapter(List<Pregunta> preguntas) {

        this.preguntas = preguntas;

    }

    //es el encargado de inflar el contenido de un nuevo componente para la lista
    //"inflar" quiere decir que un layout se usara dentro de otro layout
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.cardview, parent, false);

        ViewHolder vh = new ViewHolder(view);

        return vh;

    }

    //aqui se hacen las modificaciones del contenido para cada componente
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.enunciado.setText(preguntas.get(position).getEnunciado());
        holder.categoria.setText(preguntas.get(position).getCategoria());

    }

    //permite al adaptador saber la cantidad de elementos que se crean.
    @Override
    public int getItemCount() {
        return preguntas.size();
    }


}
