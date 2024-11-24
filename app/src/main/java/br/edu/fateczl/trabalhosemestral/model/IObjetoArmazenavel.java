package br.edu.fateczl.trabalhosemestral.model;

public interface IObjetoArmazenavel {
    /*
     *@author:<JOÃO VITOR LIMA COSTA>
     */

    String getNomeTabela();
    String getChavePrimaria();
    void organizarDados(ConteinerDTO dados);

}
