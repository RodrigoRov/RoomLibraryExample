package com.example.alejandro.roomexampleproject.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alejandro.roomexampleproject.Adapters.ListaNotasAdapter;
import com.example.alejandro.roomexampleproject.R;
import com.example.alejandro.roomexampleproject.database.AppDatabase;
import com.example.alejandro.roomexampleproject.models.Materia;
import com.example.alejandro.roomexampleproject.models.Note;
import com.example.alejandro.roomexampleproject.models.User;

import java.util.ArrayList;
import java.util.List;

public class ListaNotasFragment extends Fragment {
    AppDatabase database;
    RecyclerView recyclerView;
    User user;
    ArrayList<String> nomMaterias = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lista_notas_fragment,container,false);

        List<Materia> materias = database.materiaDao().getMaterias(user.getId());
        List<Note> notes = new ArrayList<>();
        for(Materia m:materias){
            notes.addAll(database.noteDao().findNotas(m.getIdMateria()));
            nomMaterias.add(m.getNombre_Materia());
        }

        recyclerView = v.findViewById(R.id.lista_notas_recycler);
        ListaNotasAdapter adapter = new ListaNotasAdapter(getContext(),notes,nomMaterias);
        adapter.setNotas(true);
        LinearLayoutManager  linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);



        return v;
    }

    public void setDatabase(AppDatabase database) {
        this.database = database;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
