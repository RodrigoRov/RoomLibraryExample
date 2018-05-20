package com.example.alejandro.roomexampleproject.models;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.util.StringJoiner;

@Entity(foreignKeys = @ForeignKey(entity = User.class, parentColumns = "id",childColumns = "user_id", onDelete = ForeignKey.CASCADE),indices = {@Index("idMateria")})


public class Materia {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idMateria")
    private int idMateria;

    @ColumnInfo(name = "nombre_Materia")
    private String nombre_Materia;

    @ColumnInfo(name = "nota_materia")
    private int nota;

    @ColumnInfo(name = "catedratico")
    private String catedratico;

    @ColumnInfo(name = "user_id")
    private int userId;

    public Materia(String nombre_Materia, int nota, String catedratico,int userId){
        this.nombre_Materia = nombre_Materia;
        this.nota = nota;
        this.catedratico = catedratico;
        this.userId = userId;
    }

    public String getCatedratico() {
        return catedratico;
    }

    public void setCatedratico(String catedratico) {
        this.catedratico = catedratico;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public String getNombre_Materia() {
        return nombre_Materia;
    }

    public void setNombre_Materia(String nombre_Materia) {
        this.nombre_Materia = nombre_Materia;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
