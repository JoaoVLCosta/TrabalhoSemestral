package br.edu.fateczl.trabalhosemestral.persistence;

import android.database.Cursor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.fateczl.trabalhosemestral.model.ConteinerDTO;

public class Buscador {
    /*
     *@author:<JOÃO VITOR LIMA COSTA>
     */

    private final String TABELA;

    private final Cursor CURSOR;

    public Buscador(Cursor cursor, String tabela){
        this.TABELA = tabela;
        this.CURSOR = cursor;
    }

    public ConteinerDTO buscarUm() throws SQLException {

        ConteinerDTO conteiner = new ConteinerDTO(TABELA);

        CURSOR.moveToFirst();

        if(!CURSOR.isAfterLast()){

            int colunasCont = CURSOR.getColumnCount();

            for(int i = 0; i < colunasCont; i++){

                int colunaTipo = CURSOR.getType(i);

                ler(i, colunaTipo, conteiner);
            }
        }
        conteiner.organizarDados();
        return conteiner;
    }

    public List<ConteinerDTO> listarTodos() throws SQLException{

        CURSOR.moveToFirst();

        List<ConteinerDTO> conteineres = new ArrayList<>();

        int colunasCont = CURSOR.getColumnCount();

        while(!CURSOR.isAfterLast()){

            ConteinerDTO conteiner = new ConteinerDTO(TABELA);

            for(int i = 0; i < colunasCont; i++){

                int colunaTipo = CURSOR.getType(i);

                ler(i, colunaTipo, conteiner);
            }

            conteiner.organizarDados();
            conteineres.add(conteiner);
            CURSOR.moveToNext();

        }
        return conteineres;
    }

    private void ler(int posicao, int colunaTipo, ConteinerDTO conteiner){

        switch (colunaTipo) {

            case Cursor.FIELD_TYPE_INTEGER:

                conteiner.addDado(CURSOR.getColumnName(posicao), CURSOR.getInt(posicao));

                break;

            case Cursor.FIELD_TYPE_STRING:

                conteiner.addDado(CURSOR.getColumnName(posicao), CURSOR.getString(posicao));

                break;

            case Cursor.FIELD_TYPE_FLOAT:

                conteiner.addDado(CURSOR.getColumnName(posicao), CURSOR.getFloat(posicao));

                break;

            case Cursor.FIELD_TYPE_NULL:

                break;

        }

    }
}

