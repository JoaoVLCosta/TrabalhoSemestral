package br.edu.fateczl.trabalhosemestral.model;

public class Equipamento implements IObjetoArmazenavel {
    /*
     *@author:<JOÃO VITOR LIMA COSTA>
     */

    private static final String NOME_TABELA = "equipamentos";

    private int codigo;
    private String nome;
    private float custo;

    public Equipamento() {
        super();
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getCusto() {
        return custo;
    }

    public void setCusto(float custo) {
        this.custo = custo;
    }

    @Override
    public String getNomeTabela(){
        return NOME_TABELA;
    }

    @Override
    public String getChavePrimaria(){
        return "cod_equip = " + getCodigo();
    }

    @Override
    public void organizarDados(ConteinerDTO dados) {
        setCodigo(dados.getDadoByColuna("cod_equip") != null ? Integer.parseInt(dados.getDadoByColuna("cod_equip").toString()) : 0);
        setNome(dados.getDadoByColuna("nome") != null ? (String) dados.getDadoByColuna("nome") : "");
        setCusto(dados.getDadoByColuna("custo") != null ? Float.parseFloat(dados.getDadoByColuna("custo").toString()) : 0.0f);
    }


    @Override
    public String toString() {
        return getCodigo() + " - " + getNome() + " - R$" + getCusto();
    }
}
