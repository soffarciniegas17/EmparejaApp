package com.sophia1.emparejaapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RegistrosBBDD extends SQLiteOpenHelper {

    private final static String NOMBRE_BBDD="registros.db";
    private final static int VERSION=1;
    private final static String TABLA_NOMBRE="CREATE TABLE REGISTROS(ID INTEGER PRIMARY KEY AUTOINCREMENT, "
    + "NOMBRE TEXT, PUNTAJE INTEGER, TIEMPO INTEGER, DIFICULTAD TEXT, MODO TEXT)";

    public RegistrosBBDD(Context context){
        super(context,NOMBRE_BBDD,null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLA_NOMBRE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS CREATE"+TABLA_NOMBRE);
        db.execSQL(TABLA_NOMBRE);
    }

    public void guardarDatos(String nombre, int puntaje, int tiempo, String dificultad, String modo){
        SQLiteDatabase db=getWritableDatabase();

        ContentValues valores=new ContentValues();

        valores.put("NOMBRE",nombre);
        valores.put("PUNTAJE",puntaje);
        valores.put("TIEMPO",tiempo);
        valores.put("DIFICULTAD",dificultad);
        valores.put("MODO",modo);

        db.insert("REGISTROS",null,valores);
    }
    public Cursor cargarDatos(String dificultad, String modo){
        SQLiteDatabase db=getReadableDatabase();
        Cursor cursor=null;

        try{
            String[] valoresRecuperar={"NOMBRE","PUNTAJE","TIEMPO"};
            String valorBuscar="DIFICULTAD" + " =? " + " AND " + "MODO"+ " =? ";
            String[] valores={dificultad,modo};
            String orderBy="PUNTAJE DESC";
            String limit="4";

            cursor=db.query("REGISTROS",valoresRecuperar,valorBuscar,valores,null,null,orderBy,limit);

        }catch (Exception e){}


        return cursor;
    }
}
