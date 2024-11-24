package br.edu.fateczl.trabalhosemestral.persistence;

import java.sql.SQLException;

import br.edu.fateczl.trabalhosemestral.model.ConteinerDTO;

public interface IManutencaoDAO {
    /*
     *@author:<JOÃO VITOR LIMA COSTA>
     */

    public void insert(ConteinerDTO conteiner) throws SQLException;
}
