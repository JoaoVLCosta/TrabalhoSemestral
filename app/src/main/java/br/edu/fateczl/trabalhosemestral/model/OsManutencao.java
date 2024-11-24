package br.edu.fateczl.trabalhosemestral.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class OsManutencao extends OrdemDeServico {
    /*
     *@author:<JOÃO VITOR LIMA COSTA>
     */

    private static final String NOME_TABELA = "ordens_manutencao";

    private List<Equipamento> equipamentos = new ArrayList<>();

    @Override
    public float calcularValor() {
        float total = 0;
        for (Equipamento e : equipamentos) {
            total += e.getCusto();
        }
        setValor(total);
        return total;
    }

    public void addEquipamento(Equipamento equipamento) {
        this.equipamentos.add(equipamento);
    }

    public List<Equipamento> getEquipamentos() {
        return equipamentos;
    }

    public void setEquipamentos(List<Equipamento> equipamentos) {
        this.equipamentos = equipamentos;
    }

    @Override
    public String toString(){
        return "codigo: " + getCodigo() + " - cliente: " + getCliente() + " - valor: " + getValor();
    }

    @Override
    public String getNomeTabela() {
        return NOME_TABELA;
    }

    @Override
    public String getChavePrimaria() {
        return "cod_os = " + getCodigo();
    }

    @Override
    public void organizarDados(ConteinerDTO dados) throws DateTimeParseException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        setCodigo(dados.getDadoByColuna("cod_os") != null ?
                Integer.parseInt(dados.getDadoByColuna("cod_os").toString()) : 0);

        setDataRealizacao(dados.getDadoByColuna("data_realizacao") != null ?
                LocalDate.parse(dados.getDadoByColuna("data_realizacao").toString(), formatter) : LocalDate.MIN);

        setDescricao(dados.getDadoByColuna("descricao") != null ?
                (String) dados.getDadoByColuna("descricao") : "");

        setValor(dados.getDadoByColuna("valor") != null ?
                Float.parseFloat(dados.getDadoByColuna("valor").toString()) : 0.0f);

        setEquipamentos( (dados.getDadoByColuna("equipamentos") != null) ?
                ( (List<Equipamento>) dados.getDadoByColuna("equipamentos") ) : new ArrayList<>());

    }
}
