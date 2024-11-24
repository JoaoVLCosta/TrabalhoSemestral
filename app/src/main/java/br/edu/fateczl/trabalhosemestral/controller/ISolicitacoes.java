package br.edu.fateczl.trabalhosemestral.controller;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.trabalhosemestral.model.ConteinerDTO;

public interface ISolicitacoes {
    /*
     *@author:<JOÃO VITOR LIMA COSTA>
     */

    void inserir(ConteinerDTO conteiner)throws SQLException;
    void modificar(ConteinerDTO conteiner)throws SQLException;
    void excluir(ConteinerDTO conteiner)throws SQLException;
    ConteinerDTO buscar(ConteinerDTO conteiner)throws SQLException;
    List<ConteinerDTO> listar(ConteinerDTO conteiner)throws SQLException;

}
