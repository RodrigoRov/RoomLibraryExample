package com.example.alejandro.roomexampleproject.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alejandro.roomexampleproject.R;
import com.example.alejandro.roomexampleproject.database.AppDatabase;
import com.example.alejandro.roomexampleproject.database.daos.UserDao;
import com.example.alejandro.roomexampleproject.models.User;

public class LoginFragment extends Fragment{
    Button button;
    EditText first,last;
    TextView primero,ultimo,login;
    AppDatabase database;
    onButtonClicked onButtonClicked;

    public interface onButtonClicked{
        void onButtonClick();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_fragment,container,false);

        primero = v.findViewById(R.id.Login_usernametxt);
        ultimo = v.findViewById(R.id.Login_passwordtxt);
        login = v.findViewById(R.id.Login_login);
        first = v.findViewById(R.id.Login_first_name);
        last = v.findViewById(R.id.Login_last_name);
        button = v.findViewById(R.id.Login_button);
        button = v.findViewById(R.id.Login_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClicked.onButtonClick();
            }
        });

        return v;
    }
    public void setDatabase(AppDatabase database) {
        this.database = database;
    }


    public void setOnClick(onButtonClicked onClick) {
        onButtonClicked = onClick;
    }

    public EditText getFirst() {
        return first;
    }

    public EditText getLast() {
        return last;
    }

    public Button getButton() {
        return button;
    }

    public TextView getPrimero() {
        return primero;
    }

    public TextView getUltimo() {
        return ultimo;
    }

    public TextView getLogin() {
        return login;
    }
}
