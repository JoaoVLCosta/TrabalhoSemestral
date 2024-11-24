package br.edu.fateczl.trabalhosemestral.persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.fateczl.trabalhosemestral.model.ConteinerDTO;

public class OperacoesDao implements IOperacoesDao {
    /*
     *@author:<JOÃO VITOR LIMA COSTA>
     */

    private GenericDao gDao;
    private SQLiteDatabase database;

    public OperacoesDao() {
        super();
    }

    private OperacoesDao open(Context context) throws SQLException {
        gDao = new GenericDao(context);

        database = gDao.getWritableDatabase();

        return this;
    }

    private void close() {
        gDao.close();
    }

    @Override
    public void insert(ConteinerDTO conteiner) throws SQLException {

        verificarDao( (Context) conteiner.getAdicional() );

        database.insert(conteiner.getNomeTabela(), null, getContentValues(conteiner));

        this.close();
    }

    @Override
    public int update(ConteinerDTO conteiner) throws SQLException {

        verificarDao( (Context) conteiner.getAdicional() );

        int retorno = database.update(conteiner.getNomeTabela(),
                getContentValues(conteiner),
                conteiner.getChavePrimaria(),
                null);

        this.close();

        return retorno;
    }

    @Override
    public void delete(ConteinerDTO conteiner) throws SQLException {

        verificarDao( (Context) conteiner.getAdicional() );

        database.delete(conteiner.getNomeTabela(),
                conteiner.getChavePrimaria(),
                null);

        close();
    }

    @Override
    public ConteinerDTO findOne(ConteinerDTO conteiner) throws SQLException {

        verificarDao( (Context) conteiner.getAdicional() );

        String sql = "SELECT * FROM " +
                conteiner.getNomeTabela() +
                        " WHERE " + conteiner.getChavePrimaria();

        Cursor cursor = database.rawQuery(sql, null);

        ConteinerDTO conteinerRetorno = new ConteinerDTO();

        if(cursor != null){
            Buscador buscador = new Buscador(cursor, conteiner.getNomeTabela());
            conteinerRetorno = buscador.buscarUm();
        }

        cursor.close();

        close();

        conteinerRetorno.setNomeTabela(conteiner.getNomeTabela());

        return conteinerRetorno;
    }

    @Override
    public List<ConteinerDTO> findAll(ConteinerDTO conteiner) throws SQLException {

        verificarDao( (Context) conteiner.getAdicional() );

        String sql = "SELECT * FROM " + conteiner.getNomeTabela();

        Cursor cursor = database.rawQuery(sql, null);

        List<ConteinerDTO> conteineres = new ArrayList<>();

        if(cursor != null){
            Buscador buscador = new Buscador(cursor, conteiner.getNomeTabela());
            conteineres = buscador.listarTodos();
        }
        cursor.close();

        close();

        return conteineres;
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
