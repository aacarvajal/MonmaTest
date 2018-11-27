package com.example.adrian.monmatest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CardViewAdapter extends RecyclerView.Adapter<CardViewAdapter.ViewHolder> {

    private static Context myContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView enunciado, categoria;

        public ViewHolder(View v) {

            super(v);
            enunciado = (TextView) v.findViewById(R.id.enunciado);
            categoria = (TextView) v.findViewById(R.id.categoria);

        }

    }

    public List<Pregunta> preguntas;

    public CardViewAdapter(List<Pregunta> preguntas) {

        this.preguntas = preguntas;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.cardview, parent, false);

        ViewHolder vh = new ViewHolder(view);

        return vh;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.enunciado.setText(preguntas.get(position).getEnunciado());
        holder.categoria.setText(preguntas.get(position).getCategoria());

    }

    @Override
    public int getItemCount() {
        return preguntas.size();
    }


}
