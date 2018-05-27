package com.example.alejandro.roomexampleproject.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alejandro.roomexampleproject.R;
import com.example.alejandro.roomexampleproject.models.Materia;

import java.util.ArrayList;
import java.util.List;

public class ListaMateriaAdapter extends RecyclerView.Adapter<ListaMateriaAdapter.ViewHolder> {
    List<Materia> listamaterias;
    Context mCtx;

    public ListaMateriaAdapter (Context context, List<Materia> listamaterias){
        this.listamaterias = listamaterias;
        mCtx = context;
    }


    @NonNull
    @Override
    public ListaMateriaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mCtx).inflate(R.layout.card_materias,parent,false);
        return new ListaMateriaAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaMateriaAdapter.ViewHolder holder, int position) {
        Materia materia = listamaterias.get(position);

        holder.nombreCatedratico.setText(materia.getCatedratico());
        holder.nombreMateria.setText(materia.getNombre_Materia());

    }

    @Override
    public int getItemCount() {
        return listamaterias.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombreMateria,nombreCatedratico;

        public ViewHolder(View itemView) {
            super(itemView);
            nombreMateria = itemView.findViewById(R.id.nombre_materia);
            nombreCatedratico = itemView.findViewById(R.id.nombre_catedratico);
        }
    }
}
