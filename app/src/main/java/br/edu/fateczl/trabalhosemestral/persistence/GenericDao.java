package br.edu.fateczl.trabalhosemestral.persistence;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

public class GenericDao extends SQLiteOpenHelper {
    /*
     *@author:<JOÃO VITOR LIMA COSTA>
     */

    public GenericDao(Context context){
        super(context, TabelasDao.getDatabase(), null, TabelasDao.getVersion());
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        List<String> tabelas = TabelasDao.getTabelas();

        for(String tabela : tabelas){
            sqLiteDatabase.execSQL(tabela);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int antigaVersao, int novaVersao) {
        if(novaVersao > antigaVersao){

            List<String> nomesTabelas = TabelasDao.getNomesTabelas();

            for(String nome : nomesTabelas){
                sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + nome);
            }

            onCreate(sqLiteDatabase);
        }
    }
}
