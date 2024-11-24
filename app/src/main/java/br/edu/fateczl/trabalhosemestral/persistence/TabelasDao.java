package br.edu.fateczl.trabalhosemestral.persistence;

import java.util.ArrayList;
import java.util.List;

public class TabelasDao {
    /*
     *@author:<JOÃO VITOR LIMA COSTA>
     */

    private static final String DATABASE = "PRESTADOR.DB";
    private static final int DATA_VER = 8;



    private static final String EQUIPAMENTOS = "equipamentos";
    private static final String CREATE_TABLE_EQUIP = "CREATE TABLE " + EQUIPAMENTOS
            + " ( " +
            "cod_equip INT NOT NULL PRIMARY KEY, " +
            "nome VARCHAR(100) NOT NULL, " +
            "custo NUMERIC(4,2))";


    private static final String CLIENTESF = "clientesF";
    private static final String CREATE_TABLE_CLIENTEF = "CREATE TABLE " + CLIENTESF
            + " ( " +
            "cod_cli INT NOT NULL PRIMARY KEY, " +
            "identificacao VARCHAR(100) NOT NULL, " +
            "cep VARCHAR(100) NOT NULL, " +
            "nome VARCHAR(100) NOT NULL, " +
            "numero VARCHAR(100) NOT NULL, " +
            "tipo VARCHAR(100))";


    private static final String CLIENTESJ = "clientesJ";
    private static final String CREATE_TABLE_CLIENTEJ = "CREATE TABLE " + CLIENTESJ
            + " ( " +
            "cod_cli INT NOT NULL PRIMARY KEY, " +
            "identificacao VARCHAR(100) NOT NULL, " +
            "cep VARCHAR(100) NOT NULL, " +
            "nome VARCHAR(100) NOT NULL, " +
            "numero VARCHAR(100) NOT NULL, " +
            "tipo VARCHAR(100))";


    private static final String OS_CON = "ordens_consultoria";
    private static final String CREATE_TABLE_OS_CON = "CREATE TABLE " + OS_CON
            + " ( " +
            "cod_os INT NOT NULL PRIMARY KEY, " +
            "data_realizacao VARCHAR(100) NOT NULL, " +
            "consultor VARCHAR(100) NOT NULL, " +
            "custo NUMERIC(4,2), " +
            "horas INT, " +
            "descricao VARCHAR(255) NOT NULL, " +
            "codigo_cliente INT NOT NULL, " +
            "valor NUMERIC(4,2), " +
            "tipo VARCHAR(100))";


    private static final String OS_MAN = "ordens_manutencao";
    private static final String CREATE_TABLE_OS_MAN = "CREATE TABLE " + OS_MAN
            + " ( " +
            "cod_os INT NOT NULL PRIMARY KEY, " +
            "data_realizacao VARCHAR(100) NOT NULL, " +
            "descricao VARCHAR(255) NOT NULL, " +
            "codigo_cliente INT NOT NULL, " +
            "valor NUMERIC(4,2), " +
            "tipo VARCHAR(100))";


    private static final String EQUIP_OS = "equip_os";
    private static final String CREATE_TABLE_EQUIP_OS = "CREATE TABLE " + EQUIP_OS
            + " ( " +
            "cod_os INT NOT NULL, " +
            "cod_equip INT NOT NULL, " +
            "quantidade INT, " +
            "PRIMARY KEY (cod_os, cod_equip))";


    public static List<String> getTabelas(){
        List<String> tabelas = new ArrayList<>();

        tabelas.add(CREATE_TABLE_EQUIP);
        tabelas.add(CREATE_TABLE_CLIENTEF);
        tabelas.add(CREATE_TABLE_CLIENTEJ);
        tabelas.add(CREATE_TABLE_OS_CON);
        tabelas.add(CREATE_TABLE_OS_MAN);
        tabelas.add(CREATE_TABLE_EQUIP_OS);

        return tabelas;
    }

    public static List<String> getNomesTabelas(){
        List<String> nomes = new ArrayList<>();

        nomes.add(EQUIPAMENTOS);
        nomes.add(CLIENTESF);
        nomes.add(CLIENTESJ);
        nomes.add(OS_CON);
        nomes.add(OS_MAN);
        nomes.add(EQUIP_OS);

        return nomes;
    }

    public static String getDatabase(){
        return DATABASE;
    }

    public static int getVersion(){
        return DATA_VER;
    }

}
