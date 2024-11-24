package br.edu.fateczl.trabalhosemestral.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ConteinerDTO implements Iterable<String>{
    /*
     *@author:<JOÃO VITOR LIMA COSTA>
     */

    private final HashMap<String, IObjetoArmazenavel> MAPA_ARMAZENAVEIS = new HashMap<>(Map.ofEntries(
            Map.entry("clientesF", new ClientePessoaFisica()),
            Map.entry("clientesJ", new ClientePessoaJuridica()),
            Map.entry("equipamentos", new Equipamento()),
            Map.entry("ordens_consultoria", new OsConsultoria()),
            Map.entry("ordens_manutencao", new OsManutencao()),
            Map.entry("equip_os", new EquipDeOS())
    ));

    private HashMap<String, Object> mapaDeDados = new HashMap<>();

    private Object adicional;

    private IObjetoArmazenavel armazenavel;

    public ConteinerDTO(){
        super();
    }

    public ConteinerDTO(String nomeTabela){
        setNomeTabela(nomeTabela);
    }

    public ConteinerDTO(String nomeTabela, Object adicional){
        setNomeTabela(nomeTabela);
        this.adicional = adicional;
    }

    public float calcularValor(){

        float valor = 0.0f;

        if (armazenavel instanceof OsConsultoria){

            valor = ( (OsConsultoria) armazenavel ).calcularValor();

        } else if (armazenavel instanceof OsManutencao) {

            valor =  ( (OsManutencao) armazenavel ).calcularValor();

        }
        return valor;
    }

    public void setNomeTabela(String nomeTabela){
        this.armazenavel = MAPA_ARMAZENAVEIS.get(nomeTabela);
    }

    public String getNomeTabela(){
        return armazenavel.getNomeTabela();
    }

    public Object getAdicional(){
        return adicional;
    }

    public String getChavePrimaria(){
        return armazenavel.getChavePrimaria();
    }

    public void organizarDados(){
        armazenavel.organizarDados(this);
    }

    public void addDado(String coluna, Object dado){
        mapaDeDados.put(coluna, dado);
    }

    public Object getDadoByColuna(String coluna){
        return mapaDeDados.get(coluna);
    }

    public IObjetoArmazenavel getArmazenavel() {
        return armazenavel;
    }

    public void setArmazenavel(IObjetoArmazenavel armazenavel) {
        this.armazenavel = armazenavel;
    }

    public void removeDado(String coluna){
        mapaDeDados.remove(coluna);
    }

    @Override
    public String toString(){
        return armazenavel.toString();
    }

    @Override
    public Iterator<String> iterator() {
        return mapaDeDados.keySet().iterator();
    }
}
