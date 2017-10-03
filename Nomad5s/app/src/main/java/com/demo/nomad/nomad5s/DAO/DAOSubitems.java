package com.demo.nomad.nomad5s.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.demo.nomad.nomad5s.Model.Auditoria;
import com.demo.nomad.nomad5s.Model.Foto;
import com.demo.nomad.nomad5s.Model.SubItem;


import java.util.ArrayList;
import java.util.List;



public class DAOSubitems extends DatabaseHelper {

   public static final String IDSUBITEM = "IDSUBITEM";
   public static final String PUNTAJE = "PUNTAJE";
   public static final String IDCRITERIO = "IDCRITERIO";
   public static final String IDFOTO = "IDFOTO";
   public static final String TABLE_SUBITEMS="TABLE_SUBITEMS";

    private Context context;






    public DAOSubitems(Context context) {
        super(context);
        this.context=context;
    }


    public void addSubItem (SubItem unSubItem){

            SQLiteDatabase database = getWritableDatabase();

            //CREO LA FILA Y LE CARGO LOS DATOS


            //SI EL SUBITEM TIENE UNA O MAS FOTOS HACE UN FOR EACH, CREA UN REGISTRO POR CADA FOTO.
            if (unSubItem.getListaFotos().size()>=1){
                for (Foto unaFoto:unSubItem.getListaFotos()
                     ) {
                    ContentValues row = new ContentValues();
                    row.put(IDSUBITEM, unSubItem.getIdSubitem());
                    row.put(PUNTAJE, unSubItem.getPuntuacion());
                    row.put(IDCRITERIO, unSubItem.getCriteriosSubitem().getIdCriterio());
                    row.put(IDFOTO,unaFoto.getIdFoto());
                    database.insert(TABLE_SUBITEMS, null, row);
                }

            }
            //SI EL SUBITEM NO TIENE FOTOS, COPIA SOLO LA INFO QUE EXISTE
            else{
                ContentValues row = new ContentValues();
                row.put(IDSUBITEM, unSubItem.getIdSubitem());
                row.put(PUNTAJE, unSubItem.getPuntuacion());
                row.put(IDCRITERIO, unSubItem.getCriteriosSubitem().getIdCriterio());
                database.insert(TABLE_SUBITEMS, null, row);
            }

            //CIERRO LA DB
            database.close();

    }


    public List<SubItem> getAllSubItems(Auditoria unaAuditoria){

        List<SubItem> allSubitems  = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();

        String select = "SELECT A.IDAUDITORIA, B.IDSUBITEM, B.IDFOTO " +
                        "FROM TABLE_AUDITORIAS AS A, TABLE_SUBITEMS AS B "+
                        "WHERE A.IDAUDITORIA="+unaAuditoria.getIdAuditoria()+
                        " AND B.IDSUBITEM = A.IDSUBITEM ";

        Cursor cursor = database.rawQuery(select, null);
        SubItem unSubItem = new SubItem();
        List<Foto>unaListaFotos=new ArrayList<>();
        unSubItem.setListaFotos(unaListaFotos);

        while(cursor.moveToNext()){
            //LEER CADA FILA DE LA TABLA RESULTADO

            //si el idsubitem que estoy leyendo es distinto al anterior agrego un nuevo subitem, sino agrego fotos
            if (!cursor.getString(cursor.getColumnIndex(IDSUBITEM)).equals(unSubItem.getIdSubitem())){

                if (unSubItem.getIdSubitem()!=null&&!unSubItem.getIdSubitem().isEmpty()){
                    allSubitems.add(unSubItem);
                    unSubItem = new SubItem();
                    unaListaFotos=new ArrayList<>();
                    unSubItem.setListaFotos(unaListaFotos);
                }
                unSubItem.setIdSubitem(cursor.getString(cursor.getColumnIndex(IDSUBITEM)));
                unSubItem.setPuntuacion(cursor.getInt(cursor.getColumnIndex(PUNTAJE)));

                //AGREGA LOS OBJETOS CRITERIO A LOS SUBITEMS
                DAOCriterios daoCriterios = new DAOCriterios(context);
                unSubItem.setCriteriosSubitem(daoCriterios.getCriterio(cursor.getString(cursor.getColumnIndex(IDCRITERIO))));

                //SI EL SUBITEM TIENE FOTO LA AGREGA COMO OBJETO
                if (cursor.getString(cursor.getColumnIndex(IDFOTO))!=null){
                    DAOFotos daoFotos= new DAOFotos(context);
                    unSubItem.agregarFoto(daoFotos.getFoto(cursor.getString(cursor.getColumnIndex(IDFOTO))));
                }

            }
            //si el idsubitem actual es igual al anterior, agrego foto, si la hay.
            else{
                if (cursor.getString(cursor.getColumnIndex(IDFOTO))!=null){
                    DAOFotos daoFotos= new DAOFotos(context);
                    unSubItem.agregarFoto(daoFotos.getFoto(cursor.getString(cursor.getColumnIndex(IDFOTO))));
                }
            }

        }
        //cuando el cursor termina veo si me quedo el ultimo subitem sin guardar
        if (unSubItem.getIdSubitem()!=null&&!unSubItem.getIdSubitem().isEmpty()){
            allSubitems.add(unSubItem);
        }
        //CERRAR
        cursor.close();
        database.close();
        return allSubitems;
    }


}
