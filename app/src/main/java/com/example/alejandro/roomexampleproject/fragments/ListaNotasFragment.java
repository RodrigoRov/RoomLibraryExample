package com.example.alejandro.roomexampleproject.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.alejandro.roomexampleproject.Adapters.ListaNotasAdapter;
import com.example.alejandro.roomexampleproject.R;
import com.example.alejandro.roomexampleproject.activities.AddNoteActivity;
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
    FloatingActionButton button;
    public static final int NEW_ADD_NOTE_ACTIVITY = 2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lista_notas_fragment,container,false);
        recyclerView = v.findViewById(R.id.lista_notas_recycler);
        button = v.findViewById(R.id.lista_notas_add);

        new setAdapter(database).execute(user);
        LinearLayoutManager  linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddNoteActivity.class);
                intent.putExtra("UserId",user.getId());
                startActivityForResult(intent,NEW_ADD_NOTE_ACTIVITY);
            }
        });

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
        ArrayList<String> nomMaterias = new ArrayList<>();

        setAdapter(AppDatabase db){
            noteDao = db.noteDao();
            materiaDao = db.materiaDao();
        }

        @Override
        protected Void doInBackground(User... users) {
            List<Materia> materias =  materiaDao.getMaterias(users[0].getId());
            notes=new ArrayList<>();
            //int i = 0;
            for(Materia m:materias){
                notes.addAll(noteDao.findNotas(m.getIdMateria()));
                /*nomMaterias.add(m.getNombre_Materia());
                Log.d("Nombre Materia",m.getNombre_Materia());*/
                //Log.d("Id Nota",String.valueOf(notes.get(i).getId()));
                //i++;
            }
            //i=0;
            for(Note n:notes){
                nomMaterias.add(materiaDao.findMateriaByID(n.getMateriaId()).getNombre_Materia());
                //Log.d("Nombre Materia",nomMaterias.get(i));
                //i++;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter = new ListaNotasAdapter(getContext(),notes,nomMaterias);
            adapter.setNotas(true);
            recyclerView.setAdapter(adapter);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NEW_ADD_NOTE_ACTIVITY && resultCode == 2){
            new setAdapter(database).execute(user);
            Toast.makeText(getContext(),data.getStringExtra("Respuesta"),Toast.LENGTH_SHORT).show();
        }
    }
}
