package br.edu.fateczl.trabalhosemestral.controller;

import android.content.Context;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.fateczl.trabalhosemestral.model.Cliente;
import br.edu.fateczl.trabalhosemestral.model.ClientePessoaFisica;
import br.edu.fateczl.trabalhosemestral.model.ClientePessoaJuridica;
import br.edu.fateczl.trabalhosemestral.model.ConteinerDTO;
import br.edu.fateczl.trabalhosemestral.model.EquipDeOS;
import br.edu.fateczl.trabalhosemestral.model.Equipamento;
import br.edu.fateczl.trabalhosemestral.model.OsManutencao;
import br.edu.fateczl.trabalhosemestral.persistence.ManutencaoDAO;

public class ManutencaoController implements IOSController{
    /*
     *@author:<JOÃO VITOR LIMA COSTA>
     */

    ManutencaoDAO mDao;

    public ManutencaoController(){
        mDao = new ManutencaoDAO();
    }

    @Override
    public void inserir(ConteinerDTO conteiner) throws SQLException {

        Object objetoCliente = conteiner.getDadoByColuna("cliente");

        Cliente cliente = objetoCliente instanceof ClientePessoaFisica ?
                (ClientePessoaFisica) objetoCliente : (ClientePessoaJuridica) objetoCliente;

        OsManutencao manutencao = (OsManutencao) conteiner.getArmazenavel();

        manutencao.setCliente(cliente);

        String tipo = cliente.getTipo();
        int codigo_cliente = cliente.getCodigo();

        manutencao.calcularValor();
        float valor = manutencao.getValor();

        List<Equipamento> equipamentos = (List<Equipamento>) conteiner.getDadoByColuna("equipamentos");
        conteiner.removeDado("equipamentos");

        conteiner.removeDado("cliente");

        conteiner.addDado("tipo", tipo);
        conteiner.addDado("codigo_cliente", codigo_cliente);
        conteiner.addDado("valor", valor);

        mDao.insert(conteiner);

        inserirEquipamentos(equipamentos, manutencao.getCodigo(), (Context) conteiner.getAdicional());

    }

    private void inserirEquipamentos(List<Equipamento> equipamentos, int chaveOS, Context context) throws SQLException{

        List<EquipDeOS> listaEquipOS = new ArrayList<>();

        for(Equipamento equip : equipamentos){

            EquipDeOS eqOS = new EquipDeOS();

            eqOS.setCod_equip(equip.getCodigo());

            eqOS.setCod_os(chaveOS);

            listaEquipOS.add(eqOS);

        }

        listaEquipOS = mesclarEquipDeOS(listaEquipOS);

        ConteinerDTO conteiner = new ConteinerDTO("equip_os", context);

        conteiner.addDado("cod_os", chaveOS);

        for(EquipDeOS equip : listaEquipOS){

            conteiner.addDado("cod_equip", equip.getCod_equip());

            conteiner.addDado("quantidade", equip.getQuantidade());

            conteiner.organizarDados();

            mDao.insert(conteiner);

        }

    }

    private List<EquipDeOS> mesclarEquipDeOS(List<EquipDeOS> equipamentos) {

        Map<Integer, EquipDeOS> mapaEquipDeOS = new HashMap<>();

        for (EquipDeOS equipamentoDaOS : equipamentos) {

            int codigo = equipamentoDaOS.getCod_equip();

            if (mapaEquipDeOS.containsKey(codigo)) {

                EquipDeOS equipamentoExistente = mapaEquipDeOS.get(codigo);

                equipamentoExistente.setQuantidade(equipamentoExistente.getQuantidade() + 1);

            } else {

                equipamentoDaOS.setQuantidade(1);

                mapaEquipDeOS.put(codigo, equipamentoDaOS);

            }
        }

        return new ArrayList<>(mapaEquipDeOS.values());
    }
}
