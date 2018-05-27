package com.example.alejandro.roomexampleproject.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alejandro.roomexampleproject.R;
import com.example.alejandro.roomexampleproject.database.AppDatabase;
import com.example.alejandro.roomexampleproject.database.daos.MateriaDao;
import com.example.alejandro.roomexampleproject.database.daos.NoteDao;
import com.example.alejandro.roomexampleproject.models.Materia;
import com.example.alejandro.roomexampleproject.models.Note;

import java.util.concurrent.ExecutionException;

public class AddNoteActivity extends AppCompatActivity{
    AppDatabase database;
    EditText nota,nomMateria;
    Button button,cancelar;
    Boolean anadio=false;
    int UserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnote);

        UserId = getIntent().getIntExtra("UserId",1);
        database = AppDatabase.getInstance(getApplicationContext());

        nota= findViewById(R.id.Add_note_nota);
        nomMateria = findViewById(R.id.Add_note_nombreMateria);
        button = findViewById(R.id.Add_note_add);
        cancelar = findViewById(R.id.Add_note_cancelar);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nota.getText().toString().equals("")){
                    if(!nomMateria.getText().toString().equals("")){
                        new NewNote(database).execute(nota.getText().toString(),nomMateria.getText().toString(),String.valueOf(UserId));
                    }
                    else{
                        Toast.makeText(v.getContext(),"Por favor ingresar una materia",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(v.getContext(),"Por favor ingresar una Nota",Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent = new Intent();
                replyIntent.putExtra("Respuesta","Ha cancelado ingresar nota");
                setResult(2,replyIntent);
                finish();
            }
        });
    }

    private class NewNote extends AsyncTask<String,Void,Boolean>{
        NoteDao noteDao;
        MateriaDao materiaDao;
        NewNote(AppDatabase db){
            noteDao = db.noteDao();
            materiaDao = db.materiaDao();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Boolean doInBackground(String... strings) {
            Materia materia = materiaDao.getMateria(strings[1],Integer.parseInt(strings[2]));
            if(materia == null){
                return false;
            }
            else{
                noteDao.insert(new Note(strings[0],materia.getIdMateria()));
                return true;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);

            if(!aBoolean){
                Toast.makeText(AddNoteActivity.this, "No existe esta Materia", Toast.LENGTH_SHORT).show();
            }
            else{
                Intent replyIntent = new Intent();
                replyIntent.putExtra("Respuesta","Se ha anadido una Nota");
                setResult(2,replyIntent);
                finish();
            }
        }
    }
}
