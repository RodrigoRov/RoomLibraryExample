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
import android.widget.Toast;

import com.example.alejandro.roomexampleproject.Adapters.ListaMateriaAdapter;
import com.example.alejandro.roomexampleproject.R;
import com.example.alejandro.roomexampleproject.activities.AddMateriaActivity;
import com.example.alejandro.roomexampleproject.activities.MainActivity;
import com.example.alejandro.roomexampleproject.database.AppDatabase;
import com.example.alejandro.roomexampleproject.database.daos.MateriaDao;
import com.example.alejandro.roomexampleproject.models.Materia;
import com.example.alejandro.roomexampleproject.models.User;

import java.util.ArrayList;
import java.util.List;

public class ListaMateriaFragment extends Fragment {
    public static final int NEW_ADDMATERIA_ACTIVITY_REQUEST_CODE = 1;
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

        new setAdapter(database).execute(user);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        floatingActionButton = v.findViewById(R.id.lista_materias_add);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddMateriaActivity.class);
                intent.putExtra("UserId",user.getId());
                startActivityForResult(intent, NEW_ADDMATERIA_ACTIVITY_REQUEST_CODE);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == NEW_ADDMATERIA_ACTIVITY_REQUEST_CODE){
            new setAdapter(database).execute(user);
            Toast.makeText(getContext(),data.getStringExtra("Respuesta"),Toast.LENGTH_SHORT).show();
        }
    }
}
