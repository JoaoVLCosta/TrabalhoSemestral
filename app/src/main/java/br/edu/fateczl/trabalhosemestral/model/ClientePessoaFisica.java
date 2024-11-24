package br.edu.fateczl.trabalhosemestral.model;

public class ClientePessoaFisica extends Cliente {
    /*
     *@author:<JOÃO VITOR LIMA COSTA>
     */

    private static final String NOME_TABELA = "clientesF";

    String cpf;
    String nome;

    public ClientePessoaFisica() {
        super();
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String getNomeTabela() {
        return NOME_TABELA;
    }

    @Override
    public String getChavePrimaria() {
        return "cod_cli = " + getCodigo();
    }

    @Override
    public void organizarDados(ConteinerDTO dados) {
        setCodigo(dados.getDadoByColuna("cod_cli") != null ? Integer.parseInt(dados.getDadoByColuna("cod_cli").toString()) : 0);
        setCpf(dados.getDadoByColuna("identificacao") != null ? (String) dados.getDadoByColuna("identificacao") : "");
        setCep(dados.getDadoByColuna("cep") != null ? (String) dados.getDadoByColuna("cep") : "");
        setNome(dados.getDadoByColuna("nome") != null ? (String) dados.getDadoByColuna("nome") : "");
        setNumero(dados.getDadoByColuna("numero") != null ? (String) dados.getDadoByColuna("numero") : "");
        setTipo(NOME_TABELA);
    }


    @Override
    public String toString() {
        return getCodigo() + " - " + getNome();
    }
}
