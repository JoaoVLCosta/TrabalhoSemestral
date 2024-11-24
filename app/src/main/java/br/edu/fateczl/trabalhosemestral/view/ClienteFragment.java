package br.edu.fateczl.trabalhosemestral.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.edu.fateczl.trabalhosemestral.R;
import br.edu.fateczl.trabalhosemestral.controller.SolicitacoesController;
import br.edu.fateczl.trabalhosemestral.model.ConteinerDTO;

public class ClienteFragment extends Fragment {
    /*
     *@author:<JOÃO VITOR LIMA COSTA>
     */

    private RadioGroup rgTipoC;

    private RadioButton rbtFisica, rbtJuridica;

    private Button btInserirC, btBuscarC, btModificarC, btListarC, btExcluirC;

    private EditText etCodigoC, etCPFouCNPJ, etCepC, etNomeouRZ, etNumeroC;

    private TextView tvListaC;

    private View view;

    private SolicitacoesController solicitacoes;

    private String nomeTabela = "clientesF";

    public ClienteFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cliente, container, false);

        btInserirC = view.findViewById(R.id.btInserirC);
        btBuscarC = view.findViewById(R.id.btBuscarC);
        btModificarC = view.findViewById(R.id.btModificarC);
        btListarC = view.findViewById(R.id.btListarC);
        btExcluirC = view.findViewById(R.id.btExcluirC);

        rgTipoC = view.findViewById(R.id.rgTipoC);

        rbtFisica = view.findViewById(R.id.rbtFisica);
        rbtJuridica = view.findViewById(R.id.rbtJuridica);

        etCodigoC = view.findViewById(R.id.etCodigoC);
        etCPFouCNPJ = view.findViewById(R.id.etCPFouCNPJ);
        etCepC = view.findViewById(R.id.etCepC);
        etNomeouRZ = view.findViewById(R.id.etNomeouRZ);
        etNumeroC = view.findViewById(R.id.etNumeroC);

        tvListaC = view.findViewById(R.id.tvListaC);

        rgTipoC.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbtFisica) {
                etCPFouCNPJ.setHint(R.string.et_cpf);
                etNomeouRZ.setHint(R.string.et_nome);
                this.nomeTabela = "clientesF";
            } else {
                etCPFouCNPJ.setHint(R.string.et_cnpj);
                etNomeouRZ.setHint(R.string.et_razao_social);
                this.nomeTabela = "clientesJ";
            }
        });

        rbtFisica.setChecked(true);

        solicitacoes = new SolicitacoesController();

        btInserirC.setOnClickListener(op -> acaoInserir());
        btBuscarC.setOnClickListener(op -> acaoBuscar());
        btModificarC.setOnClickListener(op -> acaoModificar());
        btListarC.setOnClickListener(op -> acaoListar());
        btExcluirC.setOnClickListener(op -> acaoExcluir());

        return view;
    }

    private void acaoInserir() {
        try {
            if(validarCampos()){
                solicitacoes.inserir(montaObjeto());
                Toast.makeText(view.getContext(), "Cliente Inserido com Sucesso", Toast.LENGTH_LONG).show();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();
    }

    private void acaoBuscar(){
        try {

            String cPrimaria = etCodigoC.getText().toString();

            if(!cPrimaria.isEmpty()){

                ConteinerDTO conteiner = new ConteinerDTO(nomeTabela, view.getContext());

                conteiner.addDado("cod_cli", cPrimaria);
                conteiner.organizarDados();

                ConteinerDTO retorno = solicitacoes.buscar(conteiner);

                preencheCampos(retorno);
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), "Cliente não Encontrado", Toast.LENGTH_LONG).show();
            limpaCampos();
        }
    }

    private void acaoExcluir(){
        try {

            String cPrimaria = etCodigoC.getText().toString();

            if(!cPrimaria.isEmpty()){

                ConteinerDTO conteiner = new ConteinerDTO(nomeTabela, view.getContext());

                conteiner.addDado("cod_cli", cPrimaria);
                conteiner.organizarDados();

                solicitacoes.excluir(conteiner);

                Toast.makeText(view.getContext(), "Cliente Excluido com Sucesso", Toast.LENGTH_LONG).show();

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
                Toast.makeText(view.getContext(), "Cliente Modificado com Sucesso", Toast.LENGTH_LONG).show();
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        limpaCampos();
    }

    private void acaoListar(){
        try {

            List<ConteinerDTO> conteineres = new ArrayList<>();

            conteineres = solicitacoes.listar( new ConteinerDTO( nomeTabela, view.getContext() ) );

            StringBuffer buffer = new StringBuffer();

            for(ConteinerDTO dado : conteineres){
                buffer.append(dado);
                buffer.append("\n");
            }

            tvListaC.setText(buffer.toString());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void limpaCampos(){
        etCodigoC.setText("");
        etCPFouCNPJ.setText("");
        etCepC.setText("");
        etNomeouRZ.setText("");
        etNumeroC.setText("");
    }

    private boolean validarCampos(){
        return validacao(etCodigoC, etCPFouCNPJ, etCepC, etNomeouRZ, etNumeroC);
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

        ConteinerDTO conteiner = new ConteinerDTO(nomeTabela, view.getContext());

        int codigo = Integer.parseInt(etCodigoC.getText().toString());
        String identificacao = etCPFouCNPJ.getText().toString();
        String cep = etCepC.getText().toString();
        String nome = etNomeouRZ.getText().toString();
        String numero = etNumeroC.getText().toString();

        conteiner.addDado("cod_cli", codigo);
        conteiner.addDado("identificacao", identificacao);
        conteiner.addDado("cep", cep);
        conteiner.addDado("nome", nome);
        conteiner.addDado("numero", numero);
        conteiner.addDado("tipo", nomeTabela);

        conteiner.organizarDados();

        return conteiner;
    }

    private void preencheCampos(ConteinerDTO conteiner){

        String codigo = conteiner.getDadoByColuna("cod_cli").toString();
        String identificacao = conteiner.getDadoByColuna("identificacao").toString();
        String cep = conteiner.getDadoByColuna("cep").toString();
        String nome = conteiner.getDadoByColuna("nome").toString();
        String numero = conteiner.getDadoByColuna("numero").toString();

        etCodigoC.setText(codigo);
        etCPFouCNPJ.setText(identificacao);
        etCepC.setText(cep);
        etNomeouRZ.setText(nome);
        etNumeroC.setText(numero);
    }
}