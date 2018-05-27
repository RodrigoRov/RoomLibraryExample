package com.example.alejandro.roomexampleproject.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.alejandro.roomexampleproject.models.Materia;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface MateriaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Materia materia);

    @Update
    void update(Materia... materias);

    @Delete
    void delete(Materia... materias);

    @Query("DELETE FROM Materia")
    void deleteAll();

    @Query("SELECT * FROM Materia")
    List<Materia> getAll();

    @Query("SELECT * FROM Materia WHERE user_id=(:userId)")
    List<Materia> getMaterias(int userId);

    @Query("SELECT * FROM Materia WHERE nombre_Materia=(:nombre) AND user_id=(:userId)")
    Materia getMateria(String nombre,int userId);

    @Query("SELECT * FROM Materia WHERE idMateria=(:idMat)")
    Materia findMateriaByID(int idMat);


}
