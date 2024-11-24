package br.edu.fateczl.trabalhosemestral.persistence;

import java.sql.SQLException;

import br.edu.fateczl.trabalhosemestral.model.ConteinerDTO;

public interface IConsultoriaDAO {
    /*
     *@author:<JOÃO VITOR LIMA COSTA>
     */

    public void insert(ConteinerDTO conteiner) throws SQLException;
}
