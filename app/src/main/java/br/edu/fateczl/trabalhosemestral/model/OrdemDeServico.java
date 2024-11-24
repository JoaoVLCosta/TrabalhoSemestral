package br.edu.fateczl.trabalhosemestral.model;

import java.time.LocalDate;

public abstract class OrdemDeServico implements IObjetoArmazenavel{
    /*
     *@author:<JOÃO VITOR LIMA COSTA>
     */

    private int codigo;
    private String descricao;
    private float valor;
    private LocalDate dataRealizacao;
    private Cliente cliente;

    public abstract float calcularValor();

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public LocalDate getDataRealizacao() {
        return dataRealizacao;
    }

    public void setDataRealizacao(LocalDate dataRealizacao) {
        this.dataRealizacao = dataRealizacao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
