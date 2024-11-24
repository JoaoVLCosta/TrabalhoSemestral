package br.edu.fateczl.trabalhosemestral.model;

public abstract class Cliente implements IObjetoArmazenavel{
    /*
     *@author:<JOÃO VITOR LIMA COSTA>
     */

    private int codigo;
    private String numero;
    private String tipo;
    private String cep;

    public Cliente() {
        super();
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
