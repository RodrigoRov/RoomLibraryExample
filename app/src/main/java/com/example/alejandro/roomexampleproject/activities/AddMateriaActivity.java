package com.example.alejandro.roomexampleproject.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.alejandro.roomexampleproject.R;
import com.example.alejandro.roomexampleproject.database.AppDatabase;
import com.example.alejandro.roomexampleproject.database.daos.MateriaDao;
import com.example.alejandro.roomexampleproject.models.Materia;

public class AddMateriaActivity extends AppCompatActivity {
    EditText nombreMat,nombreCat,notaMat;
    Button button;
    AppDatabase database;
    Toolbar toolbar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmateria);
        nombreCat = findViewById(R.id.Add_materia_nombreCatedratico);
        nombreMat = findViewById(R.id.Add_materia_nombreMateria);
        notaMat = findViewById(R.id.Add_materia_notaMateria);
        database = AppDatabase.getInstance(getApplicationContext());

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final int userId = getIntent().getIntExtra("UserId",1);

        button = findViewById(R.id.Add_materia_add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nombreCat.getText().toString().equals("")){
                    if(!nombreMat.getText().toString().equals("")){
                        if(!notaMat.getText().toString().equals("")){
                            Materia materia = new Materia(nombreMat.getText().toString(),Integer.parseInt(notaMat.getText().toString()),nombreCat.getText().toString(),userId);
                            new NewMateria(database).execute(materia);
                        }else{
                            Toast.makeText(v.getContext(),"Favor ingresar una Nota",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(v.getContext(),"Favor ingresar un Catedratico",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(v.getContext(),"Favor ingresar un Catedratico",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public class NewMateria extends AsyncTask<Materia,Void,Void>{

        MateriaDao materiaDao;
        public NewMateria(AppDatabase db){
            materiaDao = db.materiaDao();
        }

        @Override
        protected Void doInBackground(Materia... materias) {
            materiaDao.insert(materias[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent replyIntent = new Intent();
            replyIntent.putExtra("Respuesta","Se ha añadido una materia");
            setResult(1,replyIntent);
            finish();
        }
    }

}
