package com.example.alejandro.roomexampleproject.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.alejandro.roomexampleproject.R;
import com.example.alejandro.roomexampleproject.database.AppDatabase;
import com.example.alejandro.roomexampleproject.models.User;

public class LoginFragment extends Fragment{
    Button button;
    TextView first,last;
    AppDatabase database;
    boolean encontro=false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_fragment,container,false);

        first = v.findViewById(R.id.Login_first_name);
        last = v.findViewById(R.id.Login_last_name);
        button = v.findViewById(R.id.Login_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    User user = database.userDao().findByFullName(first.getText().toString(),last.getText().toString());
                    encontro = true;
                }catch (NullPointerException e){
                    encontro = false;
                }

            }
        });

        return v;
    }

    public boolean isEncontro() {
        return encontro;
    }

    public void setDatabase(AppDatabase database) {
        this.database = database;
    }
}
