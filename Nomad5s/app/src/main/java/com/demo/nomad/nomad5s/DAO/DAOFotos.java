package com.demo.nomad.nomad5s.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.demo.nomad.nomad5s.Model.Auditoria;
import com.demo.nomad.nomad5s.Model.Campania;
import com.demo.nomad.nomad5s.Model.Foto;
import com.demo.nomad.nomad5s.Model.SubItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by digitalhouse on 10/06/17.
 */

public class DAOFotos extends DatabaseHelper {

   public static final String IDFOTO = "IDFOTO";
   public static final String RUTAFOTO = "RUTAFOTO";
   public static final String COMENTARIO="COMENTARIO";

   public static final String TABLE_FOTO="TABLE_FOTO";

    public DAOFotos(Context context) {
        super(context);
    }
    public void addFoto (Foto unaFoto){

        if(!checkIfExist(unaFoto.getIdFoto())) {

            SQLiteDatabase database = getWritableDatabase();
            
            //CREO LA FILA Y LE CARGO LOS DATOS
            ContentValues row = new ContentValues();
            row.put(IDFOTO, unaFoto.getIdFoto());
            row.put(RUTAFOTO, unaFoto.getRutaFoto());
            row.put(COMENTARIO, unaFoto.getComentario());
            //LE DIGO A LA BD QUE CARGUE LA FILA EN LA TABLA
            database.insert(TABLE_FOTO, null, row);
            database.close();
        }
    }

    public List<Foto> getAllFotos(SubItem unSubitem){

        List<Foto> allFotos  = new ArrayList<>();
        if (unSubitem.getListaFotos().size()>0){
            for (Foto unaFoto:unSubitem.getListaFotos()
                 ) {
                //ABRO LA BASE GENERO UNA FOTO Y CIERRO PARA CADA IDFOTO EN EL SUBITEM
                SQLiteDatabase database = getReadableDatabase();
                String select = "SELECT * FROM " + TABLE_FOTO + " WHERE IDFOTO="+unaFoto.getIdFoto();

                Cursor cursor = database.rawQuery(select, null);
                while(cursor.moveToNext()){

                    //LEER CADA FILA DE LA TABLA RESULTADO
                    Foto laFoto = new Foto();
                    laFoto.setIdFoto(cursor.getString(cursor.getColumnIndex(IDFOTO)));
                    laFoto.setRutaFoto(cursor.getString(cursor.getColumnIndex(RUTAFOTO)));
                    laFoto.setComentario(cursor.getString(cursor.getColumnIndex(COMENTARIO)));
                    
                    allFotos.add(laFoto);
                }
                cursor.close();
                database.close();
            }
        }
        return allFotos;
    }

    public Foto getFoto(String fotoId){

        SQLiteDatabase database = getReadableDatabase();
        Foto laFoto= null;

        String query = "SELECT * FROM " + TABLE_FOTO +
                        " WHERE IDFOTO=" + fotoId;

        Cursor cursor = database.rawQuery(query, null);
        if(cursor.moveToNext()){
            //LEER CADA FILA DE LA TABLA RESULTADO

            laFoto = new Foto();
            laFoto.setIdFoto(cursor.getString(cursor.getColumnIndex(IDFOTO)));
            laFoto.setRutaFoto(cursor.getString(cursor.getColumnIndex(RUTAFOTO)));
            laFoto.setComentario(cursor.getString(cursor.getColumnIndex(COMENTARIO)));
        }
        cursor.close();
        database.close();

        return laFoto;
    }

    public Boolean checkIfExist(String id){
        Foto unFoto = getFoto(id);
        return !(unFoto == null);
    }
}
