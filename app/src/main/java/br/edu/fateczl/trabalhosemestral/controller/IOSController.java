package br.edu.fateczl.trabalhosemestral.controller;

import java.sql.SQLException;

import br.edu.fateczl.trabalhosemestral.model.ConteinerDTO;

public interface IOSController {
    /*
     *@author:<JOÃO VITOR LIMA COSTA>
     */

    public void inserir(ConteinerDTO conteiner) throws SQLException;
}
