package com.example.alejandro.roomexampleproject.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alejandro.roomexampleproject.R;
import com.example.alejandro.roomexampleproject.models.Note;

import java.util.ArrayList;
import java.util.List;

public class ListaNotasAdapter extends RecyclerView.Adapter<ListaNotasAdapter.ViewHolder> {
    List<Note> noteList;
    Context mCtxt;
    ArrayList<String> nomMaterias;
    private onItemClicked onClick;
    boolean isNotas = true;


    public interface onItemClicked{
        void onItemClick(int position);
    }

    public ListaNotasAdapter(Context context, List<Note> noteList,ArrayList<String> nomMaterias){
        this.noteList = noteList;
        mCtxt = context;
        this.nomMaterias = nomMaterias;
    }

    @NonNull
    @Override
    public ListaNotasAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mCtxt).inflate(R.layout.card_notas,parent,false);
        return new ListaNotasAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaNotasAdapter.ViewHolder holder, final int position) {
        Note note = noteList.get(position);
        holder.desc.setText(note.getData());
        holder.nombreMateria.setText(nomMaterias.get(position));

        if(!isNotas) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClick.onItemClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nombreMateria,desc;
        public ViewHolder(View view){
            super(view);
            nombreMateria = view.findViewById(R.id.lista_notas_materia);
            desc = view.findViewById(R.id.lista_notas_descripcion);
        }
    }

    public void setOnClick(onItemClicked onClick) {
        this.onClick = onClick;
    }

    public void setNotas(boolean notas) {
        isNotas = notas;
    }
}
