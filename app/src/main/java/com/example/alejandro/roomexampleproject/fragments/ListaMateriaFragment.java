package com.example.alejandro.roomexampleproject.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.alejandro.roomexampleproject.Adapters.ListaMateriaAdapter;
import com.example.alejandro.roomexampleproject.R;
import com.example.alejandro.roomexampleproject.database.AppDatabase;
import com.example.alejandro.roomexampleproject.models.Materia;
import com.example.alejandro.roomexampleproject.models.User;

public class ListaMateriaFragment extends Fragment {
    RecyclerView recyclerView;
    ListaMateriaAdapter adapter;
    User user;
    AppDatabase database;
    FloatingActionButton floatingActionButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.lista_materias_fragment,container,false);
        /*for(Materia m:database.materiaDao().getAll()){
            m.setUserId(user.getId());
        }*/

        recyclerView = v.findViewById(R.id.lista_materias_recycler);
        adapter = new ListaMateriaAdapter(this.getContext(),database.materiaDao().getMaterias(user.getId()));
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        floatingActionButton = v.findViewById(R.id.lista_materias_add);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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

    /*public class NewMateria extends AsyncTask<Materia,> {

    }*/
}
