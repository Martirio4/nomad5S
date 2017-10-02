package com.demo.nomad.nomad5s.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by digitalhouse on 10/06/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String FORMATOS_DB = "formatosDB";
    public static final Integer VERSION_DB = 1;


    private Context context;

    //DEFINE LA BASE DE DATOS CON EL NOMBRE personDB y con version 1
    public DatabaseHelper(Context context) {
        super(context, FORMATOS_DB, null, VERSION_DB);
    }

    //ACA ADENTRO DEFINIMOS LA ESTRUCTURA QUE VA A TENER MI BD EN VERSION 1
    @Override
    public void onCreate(SQLiteDatabase db) {

        //CREO TABLA DE CAMPANIAS
        String query =  "CREATE TABLE " + DAOAuditorias.TABLE_FORMATOS  + "(" +
                DAOAuditorias.TITLE + " TEXT, " +
                DAOAuditorias.RELEASE_DATE + " TEXT, " +
                DAOAuditorias.OVERVIEW + " TEXT, " +
                DAOAuditorias.VOTE_AVERAGE + " FLOAT, " +
                DAOAuditorias.TIPO_FORMATO + " TEXT, " +
                DAOAuditorias.POSTER_PATH + " TEXT, " +
                DAOAuditorias.ID + " INTEGER PRIMARY KEY, " +
                DAOAuditorias.BACKDROP_PATH + " TEXT, " +
                DAOAuditorias.NAME + " TEXT, " +
                DAOAuditorias.FIRST_AIR_DATE + " TEXT, " +
                DAOAuditorias.TAGLINE + " TEXT, " +
                DAOAuditorias.NUMBER_OF_SEASONS + " INTEGER, " +
                DAOAuditorias.NUMBER_OF_EPISODES + " INTEGER, " +
                DAOAuditorias.BUDGET + " INTEGER, " +
                DAOAuditorias.REVENUE + " INTEGER )";
        db.execSQL(query);

        //CREO TABLA DE USUARIOS
        String query2 =  "CREATE TABLE " + DAOUsuarioDatabase.TABLE_USUARIOS  + "(" +
                DAOUsuarioDatabase.USER + " TEXT, " +
                DAOUsuarioDatabase.PASS + " TEXT, " +
                DAOUsuarioDatabase.PAIS + " TEXT, " +
                DAOUsuarioDatabase.MAIL + " TEXT, " +
                DAOUsuarioDatabase.IDIOMA + " TEXT, " +
                DAOUsuarioDatabase.IDUSUARIO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DAOUsuarioDatabase.FECHANACIMIENTO + " TEXT )";
        db.execSQL(query2);

        //CREAR TABLA DE FAVORITOS
        String query3 =  "CREATE TABLE " + DAOFavoritosDatabase.TABLE_FAVORITOS  + "(" +
                DAOFavoritosDatabase.TITLE + " TEXT, " +
                DAOFavoritosDatabase.MAIL + " TEXT, " +
                DAOFavoritosDatabase.RELEASE_DATE + " TEXT, " +
                DAOFavoritosDatabase.OVERVIEW + " TEXT, " +
                DAOFavoritosDatabase.VOTE_AVERAGE + " FLOAT, " +
                DAOFavoritosDatabase.TIPO_FORMATO + " TEXT, " +
                DAOFavoritosDatabase.POSTER_PATH + " TEXT, " +
                DAOFavoritosDatabase.ID + " INTEGER PRIMARY KEY, " +
                DAOFavoritosDatabase.BACKDROP_PATH + " TEXT, " +
                DAOFavoritosDatabase.NAME + " TEXT, " +
                DAOFavoritosDatabase.FIRST_AIR_DATE + " TEXT, " +
                DAOFavoritosDatabase.TAGLINE + " TEXT, " +
                DAOFavoritosDatabase.NUMBER_OF_SEASONS + " INTEGER, " +
                DAOFavoritosDatabase.NUMBER_OF_EPISODES + " INTEGER, " +
                DAOFavoritosDatabase.BUDGET + " INTEGER, " +
                DAOFavoritosDatabase.REVENUE + " INTEGER )";
        db.execSQL(query3);




    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }







}
