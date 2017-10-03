package com.demo.nomad.nomad5s.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.demo.nomad.nomad5s.Model.Auditoria;
import com.demo.nomad.nomad5s.Model.Campania;
import com.demo.nomad.nomad5s.Model.Criterio;

import java.util.ArrayList;
import java.util.List;



public class DAOCriterios extends DatabaseHelper {

   public static final String IDCRITERIO = "IDCRITERIO";
   public static final String OPCION1 = "OPCION1";
   public static final String OPCION2 = "OPCION2";
   public static final String OPCION3 = "OPCION3";
   public static final String OPCION4 = "OPCION4";
   public static final String OPCION5 = "OPCION5";
   public static final String TEXTOCRITERIO = "TEXTOCRITERIO";

   public static final String TABLE_CRITERIO="TABLE_CRITERIO";

    public DAOCriterios(Context context) {
        super(context);
    }


    //ESTA ES UNA TABLA ESTATICA, SOLO EL MANAGER PUEDE EDITAR LOS CRITERIOS

    public void addCriterio (Criterio unaCriterio){

        if(!checkIfExist(unaCriterio.getIdCriterio())) {

            SQLiteDatabase database = getWritableDatabase();

            //CREO LA FILA Y LE CARGO LOS DATOS
            ContentValues row = new ContentValues();
            row.put(IDCRITERIO, unaCriterio.getIdCriterio());
            row.put(TEXTOCRITERIO, unaCriterio.getTextoCriterio());
            row.put(OPCION1, unaCriterio.getOpcion1());
            row.put(OPCION2, unaCriterio.getOpcion2());
            row.put(OPCION3, unaCriterio.getOpcion3());
            row.put(OPCION4, unaCriterio.getOpcion4());
            row.put(OPCION5, unaCriterio.getOpcion5());
            //LE DIGO A LA BD QUE CARGUE LA FILA EN LA TABLA
            database.insert(TABLE_CRITERIO, null, row);

            database.close();
        }
    }


    public List<Criterio> getAllCriterios(){

        List<Criterio> allCriterios  = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        String select = "SELECT * FROM " + TABLE_CRITERIO;

        Cursor cursor = database.rawQuery(select, null);
        while(cursor.moveToNext()){

            //LEER CADA FILA DE LA TABLA RESULTADO
            Criterio unCriterio = new Criterio();
            unCriterio.setIdCriterio(cursor.getString(cursor.getColumnIndex(IDCRITERIO)));
            unCriterio.setTextoCriterio(cursor.getString(cursor.getColumnIndex(TEXTOCRITERIO)));
            unCriterio.setOpcion1(cursor.getString(cursor.getColumnIndex(OPCION1)));
            unCriterio.setOpcion2(cursor.getString(cursor.getColumnIndex(OPCION2)));
            unCriterio.setOpcion3(cursor.getString(cursor.getColumnIndex(OPCION3)));
            unCriterio.setOpcion4(cursor.getString(cursor.getColumnIndex(OPCION4)));
            unCriterio.setOpcion5(cursor.getString(cursor.getColumnIndex(OPCION5)));
            allCriterios.add(unCriterio);
        }
        //CERRAR
        cursor.close();
        database.close();

        return allCriterios;
    }



    public Criterio getCriterio(String idCriterio){

        SQLiteDatabase database = getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_CRITERIO +
                        " WHERE IDCRITERIO=" + idCriterio;

        Cursor cursor = database.rawQuery(query, null);
        Criterio unCriterio = null;
        if(cursor.moveToNext()){

            //LEER CADA FILA DE LA TABLA RESULTADO

            unCriterio = new Criterio();
            unCriterio.setIdCriterio(cursor.getString(cursor.getColumnIndex(IDCRITERIO)));
            unCriterio.setTextoCriterio(cursor.getString(cursor.getColumnIndex(TEXTOCRITERIO)));
            unCriterio.setOpcion1(cursor.getString(cursor.getColumnIndex(OPCION1)));
            unCriterio.setOpcion2(cursor.getString(cursor.getColumnIndex(OPCION2)));
            unCriterio.setOpcion3(cursor.getString(cursor.getColumnIndex(OPCION3)));
            unCriterio.setOpcion4(cursor.getString(cursor.getColumnIndex(OPCION4)));
            unCriterio.setOpcion5(cursor.getString(cursor.getColumnIndex(OPCION5)));

        }

        cursor.close();
        database.close();

        return unCriterio;
    }

    public Boolean checkIfExist(String id){
        Criterio unCriterio = getCriterio(id);
        return !(unCriterio == null);
    }

}
