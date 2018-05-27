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
import android.widget.TextView;

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

public class ExtraFragment extends Fragment{
    AppDatabase database;
    RecyclerView recyclerView;
    TextView nombreMateria,desc,idNota;
    User user;
    ListaNotasAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.extra_fragment,container,false);

        nombreMateria = v.findViewById(R.id.extra_nombre_materia);
        desc = v.findViewById(R.id.extra_descripcion);
        idNota = v.findViewById(R.id.extra_idNota);
        idNota.setVisibility(View.INVISIBLE);

        new setAdapter(database).execute(user);
        recyclerView = v.findViewById(R.id.extra_recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        return v;
    }

    public void setDatabase(AppDatabase database) {
        this.database = database;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private class setAdapter extends AsyncTask<User,Void,Void> {
        NoteDao noteDao;
        MateriaDao materiaDao;
        List<Note> notes;
        ArrayList<String> nomMaterias = new ArrayList<>();

        setAdapter(AppDatabase db){
            noteDao = db.noteDao();
            materiaDao = db.materiaDao();
        }

        @Override
        protected Void doInBackground(User... users) {
            List<Materia> materias =  materiaDao.getMaterias(users[0].getId());
            notes=new ArrayList<>();
            for(Materia m:materias){
                notes.addAll(noteDao.findNotas(m.getIdMateria()));
            }
            for(Note n:notes){
                nomMaterias.add(materiaDao.findMateriaByID(n.getMateriaId()).getNombre_Materia());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter = new ListaNotasAdapter(getContext(),notes,nomMaterias);
            adapter.setNotas(false);
            adapter.setOnClick(new ListaNotasAdapter.onItemClicked() {
                @Override
                public void onItemClick(int position) {
                    Note note = notes.get(position);
                    desc.setText(note.getData());
                    nombreMateria.setText(nomMaterias.get(position));
                    idNota.setText(String.valueOf(note.getId()));
                    idNota.setVisibility(View.VISIBLE);
                }
            });
            recyclerView.setAdapter(adapter);
        }
    }
}
