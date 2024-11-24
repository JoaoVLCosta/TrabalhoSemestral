package br.edu.fateczl.trabalhosemestral.controller;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.trabalhosemestral.model.ClientePessoaFisica;
import br.edu.fateczl.trabalhosemestral.model.ClientePessoaJuridica;
import br.edu.fateczl.trabalhosemestral.model.ConteinerDTO;
import br.edu.fateczl.trabalhosemestral.model.OsConsultoria;
import br.edu.fateczl.trabalhosemestral.persistence.OperacoesDao;

public class ConsultasConsultoriaController implements IConsultasController{
    /*
     *@author:<JOÃO VITOR LIMA COSTA>
     */

    private OperacoesDao oDao;

    public ConsultasConsultoriaController() {
        this.oDao = new OperacoesDao();
    }

    @Override
    public void excluir(ConteinerDTO conteiner) throws SQLException {

        oDao.delete(conteiner);

    }

    @Override
    public ConteinerDTO buscar(ConteinerDTO conteiner) throws SQLException {

        return oDao.findOne(conteiner);

    }

    @Override
    public List<ConteinerDTO> listar(ConteinerDTO conteiner) throws SQLException{

        List<ConteinerDTO> conteineres = oDao.findAll(conteiner);

        for (ConteinerDTO c : conteineres){

            OsConsultoria consultoria = (OsConsultoria) c.getArmazenavel();

            int codigo_cliente = Integer.parseInt( c.getDadoByColuna("codigo_cliente").toString() );

            String tipo = c.getDadoByColuna("tipo").toString();

            ConteinerDTO subBusca = new ConteinerDTO(tipo, conteiner.getAdicional());

            subBusca.addDado("cod_cli", codigo_cliente);

            subBusca.organizarDados();;

            ConteinerDTO retornoCliente = oDao.findOne(subBusca);

            consultoria.setCliente( retornoCliente.getArmazenavel() instanceof ClientePessoaFisica ?
                    (ClientePessoaFisica) retornoCliente.getArmazenavel() :
                    (ClientePessoaJuridica) retornoCliente.getArmazenavel() );

            c.setArmazenavel( consultoria );

        }

        return conteineres;
    }
}