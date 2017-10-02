package com.demo.nomad.nomad5s.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



import java.util.ArrayList;
import java.util.List;

/**
 * Created by digitalhouse on 10/06/17.
 */

public class DAOAuditorias extends DatabaseHelper {

   public static final String IDAUDITORIA = "IDAUDITORIA";
   public static final String FECHA_AUDITORIA = "FECHA_AUDITORIA";
   public static final String USUARIO = "USUARIO";
   public static final String PUNTAJE_FINAL = "PUNTAJE_FINAL";
   public static final String TABLE_AUDITORIAS="TABLE_AUDITORIAS";

    public DAOAuditorias(Context context) {

        super(context);
    }

    public void addAuditoria (Auditoria unAuditoria){
        if(!checkIfExist(unAuditoria.getIdAuditoria())) {

            SQLiteDatabase database = getWritableDatabase();

            //CREO LA FILA Y LE CARGO LOS DATOS
            ContentValues row = new ContentValues();
            row.put(IDAUDITORIA, unAuditoria.getIdAuditoria());
            row.put(FECHA_AUDITORIA, unAuditoria.getFechaAuditoria());
            row.put(USUARIO, FirebaseAuth.getInstance().getCurrentUser().getUid());
            row.put(PUNTAJE_FINAL,unAuditoria.getPuntajeFinal());

            //LE DIGO A LA BD QUE CARGUE LA FILA EN LA TABLA
            database.insert(TABLE_AUDITORIAS, null, row);
            database.close();
        }
    }


//    public void addAuditorias(List<Auditoria> formatosList, String tipoAuditoria){
//
//        for(Auditoria unAuditoria : formatosList){
//            addAuditoria(unAuditoria, tipoAuditoria);
//        }
//    }


    public List<Auditoria> getAllAuditorias(){

        List<Auditoria> auditorias  = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        String select = "SELECT * FROM " + TABLE_AUDITORIAS;

        Cursor cursor = database.rawQuery(select, null);
        while(cursor.moveToNext()){

            //LEER CADA FILA DE LA TABLA RESULTADO
            Auditoria unAuditoria = new Auditoria();
            unAuditoria.setIdAuditoria(cursor.getString(cursor.getColumnIndex(IDAUDITORIA)));
            unAuditoria.setPuntajeFinal(cursor.getDouble(cursor.getColumnIndex(PUNTAJE_FINAL)));
            unAuditoria.setFechaAuditoria(cursor.getString(cursor.getColumnIndex(FECHA_AUDITORIA)));
            unAuditoria.setUsuario(cursor.getString(cursor.getColumnIndex(USUARIO)));
            auditorias.add(unAuditoria);
        }
        //CERRAR
        cursor.close();
        database.close();

        return auditorias;
    }



    public Auditoria getAuditoria(String id){

        SQLiteDatabase database = getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_AUDITORIAS +
                        " WHERE IDAUDITORIA=" + id;

        Cursor cursor = database.rawQuery(query, null);
        Auditoria unAuditoria = null;
        if(cursor.moveToNext()){

            //LEER CADA FILA DE LA TABLA RESULTADO

            unAuditoria = new Auditoria();
            unAuditoria.setIdAuditoria(cursor.getString(cursor.getColumnIndex(IDAUDITORIA)));
            unAuditoria.setUsuario(cursor.getString(cursor.getColumnIndex(USUARIO)));
            unAuditoria.setFechaAuditoria(cursor.getString(cursor.getColumnIndex(FECHA_AUDITORIA)));
            unAuditoria.setPuntajeFinal(cursor.getDouble(cursor.getColumnIndex(PUNTAJE_FINAL)));

        }

        cursor.close();
        database.close();

        return unAuditoria;
    }

    public Boolean checkIfExist(String id){
        Auditoria unAuditoria = getAuditoria(id);
        return !(unAuditoria == null);
    }

}
