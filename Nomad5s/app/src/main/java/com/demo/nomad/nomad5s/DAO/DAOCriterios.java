package com.demo.nomad.nomad5s.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.demo.nomad.nomad5s.Model.Auditoria;
import com.demo.nomad.nomad5s.Model.Campania;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by digitalhouse on 10/06/17.
 */

public class DAOCriterios extends DatabaseHelper {

   public static final String IDCAMPANIA = "IDCAMPANIA";
   public static final String FECHA_CAMPANIA = "FECHA_CAMPANIA";
   public static final String IDAUDITORIA="IDAAUDITORIA";

   public static final String TABLE_CAMPANIA="TABLE_CAMPANIA";

    public DAOCriterios(Context context) {
        super(context);
    }

    public void addCampania (Campania unaCampania){

        if(!checkIfExist(unaCampania.getIdCampania())) {

            SQLiteDatabase database = getWritableDatabase();

            if (unaCampania.getAuditoriasCampania().size()>0) {

                for (Auditoria unAudit : unaCampania.getAuditoriasCampania()
                        ) {

                    //CREO LA FILA Y LE CARGO LOS DATOS
                    ContentValues row = new ContentValues();
                    row.put(IDCAMPANIA, unaCampania.getIdCampania());
                    row.put(FECHA_CAMPANIA, unaCampania.getFechaLimite());
                    row.put(IDAUDITORIA, unAudit.getIdAuditoria());
                    //LE DIGO A LA BD QUE CARGUE LA FILA EN LA TABLA
                    database.insert(TABLE_CAMPANIA, null, row);
                }
            }
            database.close();
        }
    }


//    public void addAuditorias(List<Auditoria> formatosList, String tipoAuditoria){
//
//        for(Auditoria unAuditoria : formatosList){
//            addAuditoria(unAuditoria, tipoAuditoria);
//        }
//    }


    public List<Auditoria> getAllCampanias(){

        List<Campania> allCampanias  = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        String select = "SELECT * FROM " + TABLE_CAMPANIA;

        Cursor cursor = database.rawQuery(select, null);
        while(cursor.moveToNext()){

            //LEER CADA FILA DE LA TABLA RESULTADO
            Auditoria unAuditoria = new Auditoria();
            unAuditoria.setIdAuditoria(cursor.getString(cursor.getColumnIndex(IDCAMPANIA)));
            unAuditoria.setPuntajeFinal(cursor.getDouble(cursor.getColumnIndex(PUNTAJE_FINAL)));
            unAuditoria.setFechaAuditoria(cursor.getString(cursor.getColumnIndex(FECHA_CAMPANIA)));
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

        String query = "SELECT * FROM " + TABLE_CAMPANIA +
                        " WHERE IDCAMPANIA=" + id;

        Cursor cursor = database.rawQuery(query, null);
        Auditoria unAuditoria = null;
        if(cursor.moveToNext()){

            //LEER CADA FILA DE LA TABLA RESULTADO

            unAuditoria = new Auditoria();
            unAuditoria.setIdAuditoria(cursor.getString(cursor.getColumnIndex(IDCAMPANIA)));
            unAuditoria.setUsuario(cursor.getString(cursor.getColumnIndex(USUARIO)));
            unAuditoria.setFechaAuditoria(cursor.getString(cursor.getColumnIndex(FECHA_CAMPANIA)));
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
