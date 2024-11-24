package br.edu.fateczl.trabalhosemestral.controller;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.trabalhosemestral.model.ConteinerDTO;

public interface IConsultasController {
    /*
     *@author:<JOÃO VITOR LIMA COSTA>
     */

    void excluir(ConteinerDTO conteiner) throws SQLException;
    ConteinerDTO buscar(ConteinerDTO conteiner) throws SQLException;
    List<ConteinerDTO> listar(ConteinerDTO conteiner) throws SQLException;

}
