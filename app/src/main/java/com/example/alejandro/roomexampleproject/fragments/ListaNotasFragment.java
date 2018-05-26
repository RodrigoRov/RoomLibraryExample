package com.example.alejandro.roomexampleproject.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

public class ListaNotasFragment extends Fragment {
    AppDatabase database;
    RecyclerView recyclerView;
    User user;
    ListaNotasAdapter adapter;
    List<Note> notas = new ArrayList<Note>();
    ArrayList<String> nameMaterias = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lista_notas_fragment,container,false);
        recyclerView = v.findViewById(R.id.lista_notas_recycler);

        adapter = new ListaNotasAdapter(getContext(),notas,nameMaterias);
        adapter.setNotas(true);
        recyclerView.setAdapter(adapter);
        new setAdapter(database,nameMaterias,notas).execute(user);
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

    private class setAdapter extends AsyncTask<User,Void,Void>{
        NoteDao noteDao;
        MateriaDao materiaDao;
        List<Note> notes;
        ArrayList<String> nomMaterias;

        setAdapter(AppDatabase db,ArrayList<String> materias,List<Note> noteList){
            noteDao = db.noteDao();
            materiaDao = db.materiaDao();
            notes = noteList;
            nomMaterias = materias;
        };

        @Override
        protected Void doInBackground(User... users) {
            List<Materia>todasMaterias = materiaDao.getAll();
            Log.d("ID en MATERIAS",String.valueOf(todasMaterias.get(0).getUserId()));

            List<Materia> materias =  materiaDao.getMaterias(users[0].getId());
            Log.d("Tamano materias",String.valueOf(materias.size()));
            for(Materia m:materias){
                Log.d("En for de LNF","ENtra");
                notes.addAll(noteDao.findNotas(m.getIdMateria()));
                nomMaterias.add(m.getNombre_Materia());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.notifyDataSetChanged();
            Log.d("On Post EXECUTE","Si entra");
        }
    }

}
