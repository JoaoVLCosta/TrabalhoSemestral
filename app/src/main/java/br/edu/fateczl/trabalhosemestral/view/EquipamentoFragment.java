package br.edu.fateczl.trabalhosemestral.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.fateczl.trabalhosemestral.R;
import br.edu.fateczl.trabalhosemestral.controller.SolicitacoesController;
import br.edu.fateczl.trabalhosemestral.model.ConteinerDTO;

public class EquipamentoFragment extends Fragment {
    /*
     *@author:<JOÃO VITOR LIMA COSTA>
     */

    private Button btInserirE, btBuscarE, btModificarE, btListarE, btExcluirE;

    private EditText etCodigoE, etNomeE, etCustoE;

    private TextView tvListaE;

    private View view;

    private SolicitacoesController solicitacoes;

    private final String TABELA = "equipamentos";

    public EquipamentoFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_equipamento, container, false);

        btInserirE = view.findViewById(R.id.btInserirE);
        btBuscarE = view.findViewById(R.id.btBuscarE);
        btModificarE = view.findViewById(R.id.btModificarE);
        btListarE = view.findViewById(R.id.btListarE);
        btExcluirE = view.findViewById(R.id.btExcluirE);

        etCodigoE = view.findViewById(R.id.etCodigoE);
        etNomeE = view.findViewById(R.id.etNomeE);
        etCustoE = view.findViewById(R.id.etCustoE);

        tvListaE = view.findViewById(R.id.tvListaE);

        solicitacoes = new SolicitacoesController();

        btInserirE.setOnClickListener(op -> acaoInserir());
        btBuscarE.setOnClickListener(op -> acaoBuscar());
        btModificarE.setOnClickListener(op -> acaoModificar());
        btListarE.setOnClickListener(op -> acaoListar());
        btExcluirE.setOnClickListener(op -> acaoExcluir());

        return view;
    }

    private void acaoInserir() {
        try {
            if(validarCampos()){
                solicitacoes.inserir(montaObjeto());
                Toast.makeText(view.getContext(), "Equipamento Inserido com Sucesso", Toast.LENGTH_LONG).show();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();
    }

    private void acaoBuscar(){
        try {

            String cPrimaria = etCodigoE.getText().toString();

            if(!cPrimaria.isEmpty()){

                ConteinerDTO conteiner = new ConteinerDTO(TABELA, view.getContext());

                conteiner.addDado("cod_equip", cPrimaria);
                conteiner.organizarDados();

                ConteinerDTO retorno = solicitacoes.buscar(conteiner);

                preencheCampos(retorno);
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), "Equipamento não Encontrado", Toast.LENGTH_LONG).show();
            limpaCampos();
        }
    }

    private void acaoExcluir(){
        try {

            String cPrimaria = etCodigoE.getText().toString();

            if(!cPrimaria.isEmpty()){

                ConteinerDTO conteiner = new ConteinerDTO(TABELA, view.getContext());

                conteiner.addDado("cod_equip", cPrimaria);
                conteiner.organizarDados();

                solicitacoes.excluir(conteiner);

                Toast.makeText(view.getContext(), "Equipamento Excluido com Sucesso", Toast.LENGTH_LONG).show();

            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();
    }

    private void acaoModificar(){
        try {
            if(validarCampos()){
                solicitacoes.modificar(montaObjeto());
                Toast.makeText(view.getContext(), "Equipamento Modificado com Sucesso", Toast.LENGTH_LONG).show();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();
    }

    private void acaoListar(){
        try {

            List<ConteinerDTO> conteineres = new ArrayList<>();

            conteineres = solicitacoes.listar( new ConteinerDTO( TABELA, view.getContext() ) );

            StringBuffer buffer = new StringBuffer();

            for(ConteinerDTO dado : conteineres){
                buffer.append(dado);
                buffer.append("\n");
            }

            tvListaE.setText(buffer.toString());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void limpaCampos(){
        etCodigoE.setText("");
        etNomeE.setText("");
        etCustoE.setText("");
    }

    private boolean validarCampos(){
        return validacao(etCodigoE, etNomeE, etCustoE);
    }

    private boolean validacao(EditText... campos){
        for(EditText campo : campos){
            if(campo.getText().toString().isEmpty()){
                return false;
            }
        }
        return true;
    }

    private ConteinerDTO montaObjeto(){

        ConteinerDTO conteiner = new ConteinerDTO(TABELA, view.getContext());

        int codigo = Integer.parseInt(etCodigoE.getText().toString());
        String nome = etNomeE.getText().toString();
        float custo = Float.parseFloat(etCustoE.getText().toString());

        conteiner.addDado("cod_equip", codigo);
        conteiner.addDado("nome", nome);
        conteiner.addDado("custo", custo);

        conteiner.organizarDados();

        return conteiner;
    }

    private void preencheCampos(ConteinerDTO conteiner){

        String codigo = conteiner.getDadoByColuna("cod_equip").toString();
        String nome = conteiner.getDadoByColuna("nome").toString();
        String custo = conteiner.getDadoByColuna("custo").toString();

        etCodigoE.setText(codigo);
        etNomeE.setText(nome);
        etCustoE.setText(custo);
    }
}