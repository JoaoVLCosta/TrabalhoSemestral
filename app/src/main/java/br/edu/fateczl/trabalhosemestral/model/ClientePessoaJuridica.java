package br.edu.fateczl.trabalhosemestral.model;

public class ClientePessoaJuridica extends Cliente {
    /*
     *@author:<JOÃO VITOR LIMA COSTA>
     */

    private static final String NOME_TABELA = "clientesJ";

    private String cnpj;
    private String razaoSocial;

    public ClientePessoaJuridica() {
        super();
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
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
        setCnpj(dados.getDadoByColuna("identificacao") != null ? (String) dados.getDadoByColuna("identificacao") : "");
        setCep(dados.getDadoByColuna("cep") != null ? (String) dados.getDadoByColuna("cep") : "");
        setRazaoSocial(dados.getDadoByColuna("nome") != null ? (String) dados.getDadoByColuna("nome") : "");
        setNumero(dados.getDadoByColuna("numero") != null ? (String) dados.getDadoByColuna("numero") : "");
        setTipo(NOME_TABELA);
    }


    @Override
    public String toString() {
        return getCodigo()  + " - " + getRazaoSocial();
    }
}
