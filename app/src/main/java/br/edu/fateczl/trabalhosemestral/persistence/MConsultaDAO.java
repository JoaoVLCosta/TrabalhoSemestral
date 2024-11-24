package br.edu.fateczl.trabalhosemestral.persistence;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.fateczl.trabalhosemestral.model.ConteinerDTO;

public class MConsultaDAO implements IMConsultaDAO{
    /*
     *@author:<JOÃO VITOR LIMA COSTA>
     */

    private GenericDao gDao;
    private SQLiteDatabase database;

    public MConsultaDAO() {
        super();
    }

    @Override
    public void delete(ConteinerDTO conteiner) throws SQLException {
        verificarDao( (Context) conteiner.getAdicional() );

        database.delete("equip_os",
                conteiner.getChavePrimaria(),
                null);

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

        String sqlEQUIPS = "SELECT equip_os.cod_equip, equipamentos.nome, equipamentos.custo, equip_os.quantidade FROM " +
                "equip_os JOIN equipamentos ON equip_os.cod_equip = equipamentos.cod_equip" +
                " WHERE " + conteiner.getChavePrimaria();

        Cursor cursor = database.rawQuery(sql, null);

        ConteinerDTO conteinerRetorno = new ConteinerDTO();

        if(cursor != null){
            Buscador buscador = new Buscador(cursor, conteiner.getNomeTabela());
            conteinerRetorno = buscador.buscarUm();
        }

        cursor.close();

        cursor = database.rawQuery(sqlEQUIPS, null);

        List<ConteinerDTO> conteineres = new ArrayList<>();

        if(cursor != null){
            Buscador buscador = new Buscador(cursor, "equipamentos");
            conteineres = buscador.listarTodos();
        }

        cursor.close();

        close();

        conteinerRetorno.setNomeTabela(conteiner.getNomeTabela());

        conteinerRetorno.addDado("equipamentos", conteineres);

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

    private MConsultaDAO open(Context context) throws SQLException {
        gDao = new GenericDao(context);

        database = gDao.getWritableDatabase();

        return this;
    }

    private void close() {
        gDao.close();
    }

    private void verificarDao(Context context) throws SQLException{
        if(open(context) == null) open(context);
    }

}
