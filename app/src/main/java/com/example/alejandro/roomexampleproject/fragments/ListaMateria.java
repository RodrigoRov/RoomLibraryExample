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

import com.example.alejandro.roomexampleproject.Adapters.ListaMateriaAdapter;
import com.example.alejandro.roomexampleproject.R;
import com.example.alejandro.roomexampleproject.database.AppDatabase;
import com.example.alejandro.roomexampleproject.database.daos.MateriaDao;
import com.example.alejandro.roomexampleproject.models.Materia;
import com.example.alejandro.roomexampleproject.models.User;

import java.util.ArrayList;
import java.util.List;

public class ListaMateria extends Fragment {
    RecyclerView recyclerView;
    ListaMateriaAdapter adapter;
    User user;
    AppDatabase database;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lista_materias,container,false);
        /*for(Materia m:database.materiaDao().getAll()){
            m.setUserId(user.getId());
        }*/

        recyclerView = v.findViewById(R.id.lista_materias_recycler);

        new setAdapter(database).execute(user);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        return v;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AppDatabase getDatabase() {
        return database;
    }

    public void setDatabase(AppDatabase database) {
        this.database = database;
    }

    private class setAdapter extends AsyncTask<User,Void,Void>{
        MateriaDao materiaDao;
        List<Materia> materias;

        setAdapter(AppDatabase db){
            materiaDao = db.materiaDao();
        }

        @Override
        protected Void doInBackground(User... user) {
            materias=materiaDao.getMaterias(user[0].getId());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter = new ListaMateriaAdapter(getContext(),materias);
            recyclerView.setAdapter(adapter);

        }
    }
}
