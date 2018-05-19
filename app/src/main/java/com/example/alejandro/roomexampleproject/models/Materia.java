package com.example.alejandro.roomexampleproject.models;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity


public class Materia {
    @PrimaryKey(autoGenerate = true)
    private int idMateria;

    @ColumnInfo(name = "nombre_Materia")
    private String nombre_Materia;

    @ColumnInfo(name = "nota_materia")
    private int nota;
}
