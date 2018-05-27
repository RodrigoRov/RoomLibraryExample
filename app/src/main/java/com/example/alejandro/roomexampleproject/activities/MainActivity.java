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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.alejandro.roomexampleproject.R;
import com.example.alejandro.roomexampleproject.database.AppDatabase;
import com.example.alejandro.roomexampleproject.database.daos.MateriaDao;
import com.example.alejandro.roomexampleproject.database.daos.NoteDao;
import com.example.alejandro.roomexampleproject.database.daos.UserDao;
import com.example.alejandro.roomexampleproject.fragments.ListaMateriaFragment;
import com.example.alejandro.roomexampleproject.fragments.ListaNotasFragment;
import com.example.alejandro.roomexampleproject.fragments.LoginFragment;
import com.example.alejandro.roomexampleproject.fragments.UserInfoFragment;
import com.example.alejandro.roomexampleproject.models.Materia;
import com.example.alejandro.roomexampleproject.models.User;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBar actionBar;
    AppDatabase database;
    FragmentManager fragmentManager;
    final LoginFragment loginFragment = new LoginFragment();
    String nombres [] = new String [2];

    boolean Allowed;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.log_out:
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                loginFragment.setDatabase(database);
                loginFragment.getFirst().setText("");
                loginFragment.getLast().setText("");
                loginFragment.getPrimero().setVisibility(View.VISIBLE);
                loginFragment.getUltimo().setVisibility(View.VISIBLE);
                loginFragment.getLast().setVisibility(View.VISIBLE);
                loginFragment.getFirst().setVisibility(View.VISIBLE);
                loginFragment.getButton().setVisibility(View.VISIBLE);
                loginFragment.getButton().setEnabled(true);
                loginFragment.getFirst().setEnabled(true);
                loginFragment.getLast().setEnabled(true);
                loginFragment.getLogin().setText(R.string.login);
                Allowed = false;
                nombres = new String[2];
                fragmentTransaction.replace(R.id.contentFrame,loginFragment);
                fragmentTransaction.commit();
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

        //setting up the toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);


        //setting up drawerlayout
        drawerLayout = findViewById(R.id.drawerLayout);


        loginFragment.setDatabase(database);

        loginFragment.setOnClick(new LoginFragment.onButtonClicked() {
            @Override
            public void onButtonClick() {
                nombres[0]=loginFragment.getFirst().getText().toString();
                nombres[1]=loginFragment.getLast().getText().toString();
                new SearchUser(database).execute(nombres[0],nombres[1]);

            }
        });
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


        fragmentTransaction.add(R.id.contentFrame,loginFragment);
        fragmentTransaction.commit();
        Allowed = false;


        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                drawerLayout.closeDrawers();

                switch (item.getItemId()){
                    case R.id.notas:
                        if(Allowed)
                            new StartNotas(database).execute(nombres);
                        break;

                    case R.id.materias:
                        if(Allowed)
                            new StartMaterias(database).execute(nombres);
                        break;

                    case R.id.perfil:
                        if(Allowed)
                            new GetUsersAsync(database).execute(nombres);
                        break;
                    case R.id.extra:
                        if(Allowed)
                            new StartExtras().execute();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    private class GetUsersAsync extends AsyncTask<String, Void, User>{
        private final UserDao userdao;

        private GetUsersAsync(AppDatabase db) {
            this.userdao = db.userDao();
        }

        @Override
        protected User doInBackground(String... users) {

            return userdao.findByFullName(users[0],users[1]);
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            UserInfoFragment fragment = new UserInfoFragment();
            fragment.setUser(user);
            fragment.setDatabase(database);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contentFrame, fragment);
            fragmentTransaction.commit();
        }
    }

    private class StartMaterias extends AsyncTask<String,User,User>{
        UserDao userDao;

        private StartMaterias(AppDatabase db){
            userDao = db.userDao();
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            ListaMateriaFragment fragment = new ListaMateriaFragment();
            fragment.setUser(user);
            fragment.setDatabase(database);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contentFrame, fragment);
            fragmentTransaction.commit();
        }

        @Override
        protected User doInBackground(String... names) {

            return userDao.findByFullName(names[0],names[1]);
        }
    }

    private class StartNotas extends AsyncTask<String,Void,User>{

        UserDao userDao;

        private StartNotas(AppDatabase db){userDao = db.userDao();}
        @Override
        protected User doInBackground(String... names) {

            return userDao.findByFullName(names[0],names[1]);
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            ListaNotasFragment fragment = new ListaNotasFragment();
            fragment.setUser(user);
            fragment.setDatabase(database);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contentFrame, fragment);
            fragmentTransaction.commit();
        }
    }

    private class StartExtras extends AsyncTask<Void,User,User>{
        @Override
        protected User doInBackground(Void... voids) {
            return null;
        }
    }

    private class SearchUser extends AsyncTask<String,Void,Boolean>{

        UserDao userDao;
        SearchUser(AppDatabase db){
            userDao = db.userDao();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            User user = userDao.findByFullName(strings[0],strings[1]);
            if(user == null){
                userDao.insert(new User(strings[0],strings[1],"","",""));
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            Allowed = aBoolean;
            loginFragment.getButton().setVisibility(View.INVISIBLE);
            loginFragment.getButton().setEnabled(false);
            loginFragment.getLast().setVisibility(View.INVISIBLE);
            loginFragment.getLast().setEnabled(false);
            loginFragment.getFirst().setVisibility(View.INVISIBLE);
            loginFragment.getFirst().setEnabled(false);
            loginFragment.getLogin().setText(R.string.login_welcome);
            loginFragment.getUltimo().setVisibility(View.INVISIBLE);
            loginFragment.getPrimero().setVisibility(View.INVISIBLE);
        }
    }

 }
