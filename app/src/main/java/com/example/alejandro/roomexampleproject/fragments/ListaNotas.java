package com.example.alejandro.roomexampleproject.fragments;

import android.os.AsyncTask;
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
import com.example.alejandro.roomexampleproject.database.daos.MateriaDao;
import com.example.alejandro.roomexampleproject.database.daos.NoteDao;
import com.example.alejandro.roomexampleproject.models.Materia;
import com.example.alejandro.roomexampleproject.models.Note;
import com.example.alejandro.roomexampleproject.models.User;

import java.util.ArrayList;
import java.util.List;

public class ListaNotas extends Fragment {
    AppDatabase database;
    RecyclerView recyclerView;
    User user;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lista_notas,container,false);
        recyclerView = v.findViewById(R.id.lista_notas_recycler);

        new setAdapter(database).execute();
        LinearLayoutManager  linearLayoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(linearLayoutManager);


        return v;
    }

    public void setDatabase(AppDatabase database) {
        this.database = database;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private class setAdapter extends AsyncTask<Void,Void,Void>{
        NoteDao noteDao;
        MateriaDao materiaDao;
        List<Note> notes;
        ArrayList<String> nomMaterias = new ArrayList<>();

        setAdapter(AppDatabase db){
            noteDao = db.noteDao();
            materiaDao = db.materiaDao();
        };

        @Override
        protected Void doInBackground(Void... voids) {
            List<Materia> materias =  materiaDao.getMaterias(user.getId());
            notes = new ArrayList<>();
            for(Materia m:materias){
                notes.addAll(noteDao.findNotas(m.getIdMateria()));
                nomMaterias.add(m.getNombre_Materia());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ListaNotasAdapter adapter = new ListaNotasAdapter(getContext(),notes,nomMaterias);
            recyclerView.setAdapter(adapter);
        }
    }

}
