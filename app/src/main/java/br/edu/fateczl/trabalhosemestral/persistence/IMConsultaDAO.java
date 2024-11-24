package br.edu.fateczl.trabalhosemestral.persistence;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.trabalhosemestral.model.ConteinerDTO;

public interface IMConsultaDAO {
    /*
     *@author:<JOÃO VITOR LIMA COSTA>
     */

    public void delete(ConteinerDTO conteiner) throws SQLException;
    public ConteinerDTO findOne(ConteinerDTO conteiner) throws SQLException;
    public List<ConteinerDTO> findAll(ConteinerDTO conteiner) throws SQLException;

}
