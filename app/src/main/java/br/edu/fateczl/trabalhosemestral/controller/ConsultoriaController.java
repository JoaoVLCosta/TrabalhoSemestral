package br.edu.fateczl.trabalhosemestral.controller;

import java.sql.SQLException;

import br.edu.fateczl.trabalhosemestral.model.Cliente;
import br.edu.fateczl.trabalhosemestral.model.ClientePessoaFisica;
import br.edu.fateczl.trabalhosemestral.model.ClientePessoaJuridica;
import br.edu.fateczl.trabalhosemestral.model.ConteinerDTO;
import br.edu.fateczl.trabalhosemestral.model.OsConsultoria;
import br.edu.fateczl.trabalhosemestral.persistence.ConsultoriaDAO;

public class ConsultoriaController implements IOSController{
    /*
     *@author:<JOÃO VITOR LIMA COSTA>
     */

    ConsultoriaDAO cDao;

    public ConsultoriaController(){
        cDao = new ConsultoriaDAO();
    }

    @Override
    public void inserir(ConteinerDTO conteiner) throws SQLException {

        Object objetoCliente = conteiner.getDadoByColuna("cliente");

        Cliente cliente = objetoCliente instanceof ClientePessoaFisica ?
                (ClientePessoaFisica) objetoCliente : (ClientePessoaJuridica) objetoCliente;

        OsConsultoria consultoria = (OsConsultoria) conteiner.getArmazenavel();

        consultoria.setCliente(cliente);

        String tipo = cliente.getTipo();
        int codigo_cliente = cliente.getCodigo();

        consultoria.calcularValor();
        float valor = consultoria.getValor();

        conteiner.removeDado("cliente");

        conteiner.addDado("tipo", tipo);
        conteiner.addDado("codigo_cliente", codigo_cliente);
        conteiner.addDado("valor", valor);

        cDao.insert(conteiner);

    }
}
