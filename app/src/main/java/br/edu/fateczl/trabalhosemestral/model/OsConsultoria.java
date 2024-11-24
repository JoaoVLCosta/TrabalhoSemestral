package br.edu.fateczl.trabalhosemestral.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class OsConsultoria extends OrdemDeServico{
    /*
     *@author:<JOÃO VITOR LIMA COSTA>
     */

    private static final String NOME_TABELA = "ordens_consultoria";

    private String consultor;
    private float custoHora;
    private int horas;

    public OsConsultoria() {
        super();
    }

    @Override
    public float calcularValor() {
        float total = getCustoHora() * getHoras();
        setValor(total);
        return total;
    }

    public String getConsultor() {
        return consultor;
    }

    public void setConsultor(String consultor) {
        this.consultor = consultor;
    }

    public float getCustoHora() {
        return custoHora;
    }

    public void setCustoHora(float custoHora) {
        this.custoHora = custoHora;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }

    @Override
    public String toString(){
        return "codigo: " + getCodigo() + " - cliente: " + getCliente().toString() + " - valor: " + getValor();
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

        setConsultor(dados.getDadoByColuna("consultor") != null ?
                (String) dados.getDadoByColuna("consultor") : "");

        setCustoHora(dados.getDadoByColuna("custo") != null ?
                Float.parseFloat(dados.getDadoByColuna("custo").toString()) : 0.0f);

        setHoras(dados.getDadoByColuna("horas") != null ?
                Integer.parseInt(dados.getDadoByColuna("horas").toString()) : 0);

        setDescricao(dados.getDadoByColuna("descricao") != null ?
                (String) dados.getDadoByColuna("descricao") : "");

        setValor(dados.getDadoByColuna("valor") != null ?
                Float.parseFloat(dados.getDadoByColuna("valor").toString()) : 0.0f);

    }
}
