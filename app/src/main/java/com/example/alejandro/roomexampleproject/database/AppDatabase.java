package com.example.alejandro.roomexampleproject.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.alejandro.roomexampleproject.database.daos.MateriaDao;
import com.example.alejandro.roomexampleproject.database.daos.NoteDao;
import com.example.alejandro.roomexampleproject.database.daos.UserDao;
import com.example.alejandro.roomexampleproject.models.Materia;
import com.example.alejandro.roomexampleproject.models.Note;
import com.example.alejandro.roomexampleproject.models.User;

@Database(entities = {User.class, Note.class, Materia.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase{
    private static final String DB_NAME = "notesDatabase.db";
    private static volatile AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context){
        if (instance == null){
            instance = create(context);
        }

        return instance;
    }

    private static AppDatabase create(Context context) {
        return Room.databaseBuilder(
                context,
                AppDatabase.class,
                DB_NAME
        ).addCallback(sRoomDatabaseCallback).build();
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(instance);
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void,Void,Void>{

        private final UserDao userDao;
        private final MateriaDao materiaDao;
        private final NoteDao noteDao;

        PopulateDbAsync(AppDatabase db){
            userDao = db.userDao();
            materiaDao = db.materiaDao();
            noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userDao.deleteAll();
            materiaDao.deleteAll();
            userDao.insert(new User(1,"Alejandro", "Velasco", "22577777","00357215@uca.edu.sv","PUSSY DESTROYER"),
                    new User(2,"Enrique", "Palacios", "22577777","00008415@uca.edu.sv","DICK DESTROYER"));
            Materia materia1 = new Materia("Programacion Moviles",8,"Varela-chan",1);
            Materia materia2 = new Materia("Programacion Java",6,"Nestor-chan",1);
            materiaDao.insert(materia1);
            materiaDao.insert(materia2);
            noteDao.insert(new Note("Ella no me ama",materia1.getIdMateria()));
            noteDao.insert(new Note("Ella si me ama",materia2.getIdMateria()));
            return null;
        }
    }



    public abstract UserDao userDao();
    public abstract NoteDao noteDao();
    public abstract MateriaDao materiaDao();
}
