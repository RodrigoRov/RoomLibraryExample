package com.example.alejandro.roomexampleproject.fragments;

import android.arch.persistence.room.Update;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alejandro.roomexampleproject.R;
import com.example.alejandro.roomexampleproject.database.AppDatabase;
import com.example.alejandro.roomexampleproject.database.daos.UserDao;
import com.example.alejandro.roomexampleproject.models.Note;
import com.example.alejandro.roomexampleproject.models.User;

import java.util.List;

public class UserInfoFragment extends Fragment{
    User user;
    List<Note> userNotes;
    EditText firstName, lastName,telefono,email,facebook;
    AppCompatButton guardar,cancelar;
    AppDatabase database;
    boolean editar=true;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.user_info, container, false);

        firstName = v.findViewById(R.id.firstName);
        lastName = v.findViewById(R.id.lastName);
        telefono = v.findViewById(R.id.telefono);
        email = v.findViewById(R.id.email);
        facebook = v.findViewById(R.id.facebook);
        guardar =  v.findViewById(R.id.guardar_cambios_perfil);
        cancelar = v.findViewById(R.id.cancelar_cambios_perfil);

        firstName.setText(user.getFirstName());
        lastName.setText(user.getLastName());
        telefono.setText(user.getPhone());
        email.setText(user.getEmail());
        facebook.setText(user.getFacebook_user());

        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User nuevoUsu = new User(user.getId(),firstName.getText().toString(),
                        lastName.getText().toString(),
                        telefono.getText().toString(),
                        email.getText().toString(),
                        facebook.getText().toString());

                new UpdateUser(database,nuevoUsu);
                user = nuevoUsu;
                Log.d("User",user.getFirstName());

                firstName.setText(user.getFirstName());
                lastName.setText(user.getLastName());
                telefono.setText(user.getPhone());
                email.setText(user.getEmail());
                facebook.setText(user.getFacebook_user());

                firstName.setEnabled(false);
                lastName.setEnabled(false);
                telefono.setEnabled(false);
                email.setEnabled(false);
                facebook.setEnabled(false);
                cancelar.setText(R.string.boton_editar);
                guardar.setEnabled(false);
                editar = true;
                Toast.makeText(getContext(),"Se han actualizado los datos",Toast.LENGTH_SHORT).show();

            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editar){
                    firstName.setEnabled(editar);
                    lastName.setEnabled(editar);
                    telefono.setEnabled(editar);
                    email.setEnabled(editar);
                    facebook.setEnabled(editar);
                    guardar.setEnabled(editar);
                    editar = !editar;
                    cancelar.setText(R.string.boton_cancelar);
                }
                else{
                    firstName.setText(user.getFirstName());
                    lastName.setText(user.getLastName());
                    telefono.setText(user.getPhone());
                    email.setText(user.getEmail());
                    facebook.setText(user.getFacebook_user());
                    firstName.setEnabled(editar);
                    lastName.setEnabled(editar);
                    telefono.setEnabled(editar);
                    email.setEnabled(editar);
                    facebook.setEnabled(editar);
                    guardar.setEnabled(false);
                    editar = !editar;
                    cancelar.setText(R.string.boton_editar);
                }
            }
        });

        return v;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUserNotes(List<Note> userNotes){
        this.userNotes = userNotes;
    }

    public void setDatabase(AppDatabase database) {
        this.database = database;
    }

    private class UpdateUser extends AsyncTask<Void,User,User>{
        UserDao userDao;
        User usuario;

        private UpdateUser(AppDatabase db,User user){
            this.userDao = db.userDao();
            this.usuario = user;
        }

        @Override
        protected User doInBackground(Void... voids) {
            userDao.update(usuario);
            return null;
        }

    }
}
