package br.edu.fateczl.trabalhosemestral.model;

public class EquipDeOS implements IObjetoArmazenavel{
    /*
     *@author:<JOÃO VITOR LIMA COSTA>
     */

    private static final String NOME_TABELA = "equip_os";

    private int cod_equip;
    private int cod_os;
    private int quantidade;

    public int getCod_equip() {
        return cod_equip;
    }

    public void setCod_equip(int cod_equip) {
        this.cod_equip = cod_equip;
    }

    public int getCod_os() {
        return cod_os;
    }

    public void setCod_os(int cod_os) {
        this.cod_os = cod_os;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return getCod_os() + " - " + getQuantidade();
    }

    @Override
    public String getNomeTabela() {
        return NOME_TABELA;
    }

    @Override
    public String getChavePrimaria() {
        return "cod_equip = " + getCod_equip() + " AND cod_os = " + getCod_os();
    }

    @Override
    public void organizarDados(ConteinerDTO dados) {

        setCod_equip(dados.getDadoByColuna("cod_equip") != null ?
                Integer.parseInt(dados.getDadoByColuna("cod_equip").toString()) : 0);

        setCod_os(dados.getDadoByColuna("cod_os") != null ?
                Integer.parseInt(dados.getDadoByColuna("cod_os").toString()) : 0);

        setQuantidade(dados.getDadoByColuna("quantidade") != null ?
                Integer.parseInt(dados.getDadoByColuna("quantidade").toString()) : 0);

    }
}
