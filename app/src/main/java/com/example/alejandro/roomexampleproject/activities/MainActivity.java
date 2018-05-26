package com.example.alejandro.roomexampleproject.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.alejandro.roomexampleproject.R;
import com.example.alejandro.roomexampleproject.database.AppDatabase;
import com.example.alejandro.roomexampleproject.database.daos.MateriaDao;
import com.example.alejandro.roomexampleproject.database.daos.NoteDao;
import com.example.alejandro.roomexampleproject.database.daos.UserDao;
import com.example.alejandro.roomexampleproject.fragments.ListaMateria;
import com.example.alejandro.roomexampleproject.fragments.ListaNotas;
import com.example.alejandro.roomexampleproject.fragments.UserInfoFragment;
import com.example.alejandro.roomexampleproject.models.Materia;
import com.example.alejandro.roomexampleproject.models.User;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;
    AppDatabase database;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //database
        database = AppDatabase.getInstance(getApplicationContext());
        new FillInitialDbAsync(database).execute();

        //setting up the toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        //setting up drawerlayout
        drawerLayout = findViewById(R.id.drawerLayout);

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();

                fragmentManager = getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();

                switch (item.getItemId()){
                    case R.id.notas:
                        new StartNotas(database).execute();
                        break;

                    case R.id.materias:
                        new StartMaterias(database).execute();
                        break;

                    case R.id.perfil:
                        new GetUsersAsync(database).execute();
                        break;
                }
                return true;
            }
        });
    }

    private class GetUsersAsync extends AsyncTask<Void, User, User>{
        private final UserDao userdao;

        private GetUsersAsync(AppDatabase db) {
            this.userdao = db.userDao();
        }

        @Override
        protected User doInBackground(Void... voids) {
            return userdao.getAll().get(0);
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            UserInfoFragment fragment = new UserInfoFragment();
            fragment.setUser(user);
            fragment.setDatabase(database);

            fragmentTransaction.replace(R.id.contentFrame, fragment);
            fragmentTransaction.commit();
        }
    }

    private class FillInitialDbAsync extends AsyncTask<Void, Void, Void>{
        private final UserDao userdao;
        private final NoteDao notedao;
        private final MateriaDao materiaDao;

        private FillInitialDbAsync(AppDatabase db) {
            this.userdao = db.userDao();
            this.notedao = db.noteDao();
            this.materiaDao = db.materiaDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {


            return null;
        }
    }

    private class StartMaterias extends AsyncTask<Void,User,User>{
        UserDao userDao;

        private StartMaterias(AppDatabase db){
            userDao = db.userDao();
        }
        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            ListaMateria fragment = new ListaMateria();
            fragment.setUser(user);
            fragment.setDatabase(database);

            fragmentTransaction.replace(R.id.contentFrame, fragment);
            fragmentTransaction.commit();
        }

        @Override
        protected User doInBackground(Void... voids) {

            return userDao.getAll().get(0);
        }
    }

    private class StartNotas extends AsyncTask<Void,User,User>{

        UserDao userDao;

        private StartNotas(AppDatabase db){userDao = db.userDao();}
        @Override
        protected User doInBackground(Void... voids) {

            return userDao.getAll().get(0);
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            super.onPostExecute(user);
            ListaNotas fragment = new ListaNotas();
            fragment.setUser(user);
            fragment.setDatabase(database);

            fragmentTransaction.replace(R.id.contentFrame, fragment);
            fragmentTransaction.commit();
        }
    }
 }
