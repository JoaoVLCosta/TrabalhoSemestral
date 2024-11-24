package br.edu.fateczl.trabalhosemestral.controller;

import java.sql.SQLException;
import java.util.List;

import br.edu.fateczl.trabalhosemestral.model.ClientePessoaFisica;
import br.edu.fateczl.trabalhosemestral.model.ClientePessoaJuridica;
import br.edu.fateczl.trabalhosemestral.model.ConteinerDTO;
import br.edu.fateczl.trabalhosemestral.model.OsManutencao;
import br.edu.fateczl.trabalhosemestral.persistence.MConsultaDAO;
import br.edu.fateczl.trabalhosemestral.persistence.OperacoesDao;

public class ConsultasManutencaoController implements IConsultasController{
    /*
     *@author:<JOÃO VITOR LIMA COSTA>
     */

    private MConsultaDAO mDao;

    public ConsultasManutencaoController() {
        this.mDao = new MConsultaDAO();
    }
    @Override
    public void excluir(ConteinerDTO conteiner) throws SQLException {

        mDao.delete(conteiner);

    }

    @Override
    public ConteinerDTO buscar(ConteinerDTO conteiner) throws SQLException {

        return mDao.findOne(conteiner);

    }

    @Override
    public List<ConteinerDTO> listar(ConteinerDTO conteiner) throws SQLException {

        OperacoesDao oDao = new OperacoesDao();

        List<ConteinerDTO> conteineres = oDao.findAll(conteiner);

        for (ConteinerDTO c : conteineres){

            OsManutencao manutencao = (OsManutencao) c.getArmazenavel();

            int codigo_cliente = Integer.parseInt( c.getDadoByColuna("codigo_cliente").toString() );

            String tipo = c.getDadoByColuna("tipo").toString();

            ConteinerDTO subBusca = new ConteinerDTO(tipo, conteiner.getAdicional());

            subBusca.addDado("cod_cli", codigo_cliente);

            subBusca.organizarDados();;

            ConteinerDTO retornoCliente = oDao.findOne(subBusca);

            manutencao.setCliente( retornoCliente.getArmazenavel() instanceof ClientePessoaFisica ?
                    (ClientePessoaFisica) retornoCliente.getArmazenavel() :
                    (ClientePessoaJuridica) retornoCliente.getArmazenavel() );

            c.setArmazenavel( manutencao );

        }

        return conteineres;
    }
}
