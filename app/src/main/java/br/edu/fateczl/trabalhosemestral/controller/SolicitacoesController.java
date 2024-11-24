package br.edu.fateczl.trabalhosemestral.controller;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.trabalhosemestral.model.ConteinerDTO;
import br.edu.fateczl.trabalhosemestral.persistence.OperacoesDao;

public class SolicitacoesController implements ISolicitacoes {
    /*
     *@author:<JOÃO VITOR LIMA COSTA>
     */

    OperacoesDao oDao;

    public SolicitacoesController(){
        oDao = new OperacoesDao();
    }

    @Override
    public void inserir(ConteinerDTO conteiner) throws SQLException {

        oDao.insert(conteiner);

    }

    @Override
    public void modificar(ConteinerDTO conteiner) throws SQLException{

        oDao.update(conteiner);

    }

    @Override
    public void excluir(ConteinerDTO conteiner) throws SQLException{

        oDao.delete(conteiner);

    }

    @Override
    public ConteinerDTO buscar(ConteinerDTO conteiner) throws SQLException{

        return oDao.findOne(conteiner);

    }

    @Override
    public List<ConteinerDTO> listar(ConteinerDTO conteiner) throws SQLException{

        return oDao.findAll(conteiner);

    }
}
