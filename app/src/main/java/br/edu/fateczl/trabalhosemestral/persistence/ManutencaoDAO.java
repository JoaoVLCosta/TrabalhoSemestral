package br.edu.fateczl.trabalhosemestral.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

import br.edu.fateczl.trabalhosemestral.model.ConteinerDTO;

public class ManutencaoDAO implements IManutencaoDAO {
    /*
     *@author:<JOÃO VITOR LIMA COSTA>
     */

    private GenericDao mDao;
    private SQLiteDatabase database;

    public ManutencaoDAO() {
        super();
    }

    @Override
    public void insert(ConteinerDTO conteiner) throws SQLException{
        verificarDao( (Context) conteiner.getAdicional() );

        database.insert(conteiner.getNomeTabela(), null, getContentValues(conteiner));

        this.close();
    }

    private ManutencaoDAO open(Context context) throws SQLException {
        mDao = new GenericDao(context);

        database = mDao.getWritableDatabase();

        return this;
    }

    private void close() {
        mDao.close();
    }

    private void verificarDao(Context context) throws SQLException{
        if(open(context) == null) open(context);
    }

    private static ContentValues getContentValues(ConteinerDTO conteiner){

        ContentValues contentValues = new ContentValues();

        for(String coluna : conteiner){

            Object valor = conteiner.getDadoByColuna(coluna);

            if (valor instanceof Integer) {

                contentValues.put(coluna, (Integer) valor);

            } else if (valor instanceof Float) {

                contentValues.put(coluna, (Float) valor);

            } else {

                contentValues.put(coluna, (String) valor);

            }
        }
        return contentValues;
    }

}
