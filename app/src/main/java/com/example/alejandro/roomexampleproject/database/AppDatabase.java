package com.example.alejandro.roomexampleproject.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.alejandro.roomexampleproject.database.daos.MateriaDao;
import com.example.alejandro.roomexampleproject.database.daos.NoteDao;
import com.example.alejandro.roomexampleproject.database.daos.UserDao;
import com.example.alejandro.roomexampleproject.models.Materia;
import com.example.alejandro.roomexampleproject.models.Note;
import com.example.alejandro.roomexampleproject.models.User;

@Database(entities = {User.class, Note.class, Materia.class}, version = 4, exportSchema = false)
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
        ).addMigrations(MIGRATION_2_3).addMigrations(MIGRATION_3_4).addCallback(sRoomDatabaseCallback).build();
    }

    static final Migration MIGRATION_2_3 = new Migration(2,3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };
    static final Migration MIGRATION_3_4 = new Migration(3,4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("DROP TABLE User");
            database.execSQL("CREATE TABLE User(" +
                    "id INT PRIMARY KEY AUTOINCREMENT," +
                    "first_name VARCHAR(30)," +
                    "last_name VARCHAR(30)," +
                    "email VARCHAR(40)," +
                    "facebook_user VARCHAR(20)," +
                    "phone_number VARCHAR(10));");
        }
    };

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(instance).execute();
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
            User user = new User("Alejandro", "Velasco", "22577777","00357215@uca.edu.sv","PUSSY DESTROYER");
            user.setId(1);
            User user2 = new User("Enrique", "Palacios", "22577777","00008415@uca.edu.sv","DICK DESTROYER");
            userDao.insert(user);
            userDao.insert(user2);
            Log.d("User [0] ID",String.valueOf(user.getId()));
            Log.d("User [1] ID",String.valueOf(user2.getId()));
            Materia materia1 = new Materia("Programacion Moviles",8,"Varela-chan",user.getId());
            Materia materia2 = new Materia("Programacion Java",6,"Nestor-chan",user.getId());
            materia1.setIdMateria(1);
            materia2.setIdMateria(2);
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
