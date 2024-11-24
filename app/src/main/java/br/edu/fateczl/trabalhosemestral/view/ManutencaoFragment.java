package br.edu.fateczl.trabalhosemestral.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import br.edu.fateczl.trabalhosemestral.R;
import br.edu.fateczl.trabalhosemestral.controller.ISolicitacoes;
import br.edu.fateczl.trabalhosemestral.controller.ManutencaoController;
import br.edu.fateczl.trabalhosemestral.controller.SolicitacoesController;
import br.edu.fateczl.trabalhosemestral.model.ConteinerDTO;
import br.edu.fateczl.trabalhosemestral.model.Equipamento;

public class ManutencaoFragment extends Fragment {
    /*
     *@author:<JOÃO VITOR LIMA COSTA>
     */

    private EditText etCodigoM, etDataM, etDescM;

    private Spinner spClienteM, spEquip;

    private Button btVoltarM, btInserirM, btCalcularM, btAdicionar;

    private TextView tvEquip, tvTotalM;

    private String TABELA = "ordens_manutencao";

    private View view;

    private List<Equipamento> listaEquipamentos = new ArrayList<>();

    private ManutencaoController manutencao;

    public ManutencaoFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_manutencao, container, false);

        manutencao = new ManutencaoController();

        etCodigoM = view.findViewById(R.id.etCodigoM);
        etDataM = view.findViewById(R.id.etDataM);
        etDescM = view.findViewById(R.id.etDescM);

        etDataM.addTextChangedListener(new TextWatcher() {
            private String currentText = "";
            private String dateFormat = "DDMMYYYY";
            private int maxLength = 8;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(currentText)) {
                    String cleanText = s.toString().replaceAll("[^\\d]", "");

                    if (cleanText.length() > maxLength) {
                        cleanText = cleanText.substring(0, maxLength);
                    }

                    StringBuilder formattedText = new StringBuilder();
                    int cursorPosition = 0;

                    for (int i = 0; i < cleanText.length(); i++) {
                        if (i == 2 || i == 4) {
                            formattedText.append('/');
                        }
                        formattedText.append(cleanText.charAt(i));
                        cursorPosition = formattedText.length();
                    }

                    currentText = formattedText.toString();
                    etDataM.removeTextChangedListener(this);
                    etDataM.setText(currentText);
                    etDataM.setSelection(cursorPosition);
                    etDataM.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        spClienteM = view.findViewById(R.id.spClienteM);
        spEquip = view.findViewById(R.id.spEquip);
        preencheSpinner();

        btVoltarM = view.findViewById(R.id.btVoltarM);
        btInserirM = view.findViewById(R.id.btInserirM);
        btCalcularM = view.findViewById(R.id.btCalcularM);
        btAdicionar = view.findViewById(R.id.btAdicionar);

        btVoltarM.setOnClickListener(op -> voltar((IAlternadorFragment) view.getContext()));
        btAdicionar.setOnClickListener(op -> adicionar());
        btCalcularM.setOnClickListener(op -> calcular());
        btInserirM.setOnClickListener(op -> acaoInserir());

        tvEquip = view.findViewById(R.id.tvEquip);
        tvTotalM = view.findViewById(R.id.tvTotalM);

        tvEquip.setMovementMethod(new ScrollingMovementMethod());

        return view;
    }

    private void acaoInserir() {
        try {

            if( !tvTotalM.getText().toString().isEmpty() ){

                if(validarCampos()){

                    ConteinerDTO conteiner = montaObjeto();

                    if(conteiner != null){
                        manutencao.inserir(conteiner);

                        exibirToast("Ordem de Serviço Inserida com Sucesso");

                        limpaCampos();
                    }

                } else {
                    exibirToast("Preencha todos os campos");
                }

            } else {
                exibirToast("Calcule o Total");
            }

        } catch (SQLException e) {
            exibirToast(e.getMessage());
        }
    }


    private void calcular() {

        ConteinerDTO conteiner;

        if(spClienteM.getSelectedItemPosition() > 0){

            if(validarCampos()){

                if( !tvEquip.getText().toString().isEmpty() ){

                    conteiner = montaObjeto();

                    if(conteiner != null){
                        float custoTotal = conteiner.calcularValor();

                        String texto = "Total: R$" + custoTotal;

                        tvTotalM.setText( texto );
                    }

                } else {
                    exibirToast("Adicione Equipamentos");
                }

            } else {
                exibirToast("Preencha todos os campos");
            }

        } else {
            exibirToast("Selecione um Cliente");
        }
    }

    private ConteinerDTO montaObjeto(){

        ConteinerDTO conteiner = new ConteinerDTO(TABELA, view.getContext());

        int codigo = Integer.parseInt(etCodigoM.getText().toString());
        String data = etDataM.getText().toString();
        String descricao = etDescM.getText().toString();

        conteiner.addDado("cod_os", codigo);
        conteiner.addDado("data_realizacao", data);
        conteiner.addDado("descricao", descricao);

        conteiner.addDado("cliente", ( (ConteinerDTO) spClienteM.getSelectedItem() ).getArmazenavel() );

        conteiner.addDado("equipamentos", listaEquipamentos);

        try {

            conteiner.organizarDados();

        } catch (DateTimeParseException e) {
            exibirToast("Formato de data inválido");
            etDataM.setText("");
            return null;
        }

        return conteiner;
    }

    private void adicionar(){

        int sPos = spEquip.getSelectedItemPosition();

        if(sPos > 0){

            ConteinerDTO selecionado = (ConteinerDTO) spEquip.getSelectedItem();
            listaEquipamentos.add( (Equipamento) selecionado.getArmazenavel() );

            String texto = "";
            for(Equipamento equip : listaEquipamentos){
                texto = texto + equip.toString() + "\n";
            }

            tvEquip.setText(texto);

        } else {
            exibirToast("Selecione um Equipamento válido");
        }


    }

    private void voltar(IAlternadorFragment alternador) {
        alternador.trocarFragment(new OSFragment());
    }

    private void preencheSpinner() {

        ISolicitacoes solicitacoes = new SolicitacoesController();

        ConteinerDTO conteiner = new ConteinerDTO("clientesF");
        conteiner.addDado("cod_cli", 0);
        conteiner.addDado("nome", "Escolha um Cliente");
        conteiner.organizarDados();

        ConteinerDTO equipamento_placeholder = new ConteinerDTO("clientesF");
        equipamento_placeholder.addDado("cod_cli", 0);
        equipamento_placeholder.addDado("nome", "Escolha um Equipamento");
        equipamento_placeholder.organizarDados();

        try {
            List<ConteinerDTO> conteineres = solicitacoes.listar(new ConteinerDTO("clientesF", view.getContext()));
            conteineres.add(0, conteiner);

            List<ConteinerDTO> conteineresJ = solicitacoes.listar(new ConteinerDTO("clientesJ", view.getContext()));

            List<ConteinerDTO> equipamentos = solicitacoes.listar(new ConteinerDTO("equipamentos", view.getContext()));
            equipamentos.add(0, equipamento_placeholder);

            for(ConteinerDTO c : conteineresJ){
                conteineres.add(c);
            }

            colocarNoSpinner(conteineres, spClienteM);

            colocarNoSpinner(equipamentos, spEquip);

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void colocarNoSpinner(List<ConteinerDTO> lista, Spinner spinner){
        ArrayAdapter ad = new ArrayAdapter<>(view.getContext(),
                android.R.layout.simple_spinner_item,
                lista);

        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);
    }

    private boolean validarCampos(){
        return validacao(etCodigoM, etDataM, etDescM);
    }

    private boolean validacao(EditText... campos){
        for(EditText campo : campos){
            if(campo.getText().toString().isEmpty()){
                return false;
            }
        }
        return true;
    }

    private void exibirToast(String mensagem){
        Toast.makeText(view.getContext(), mensagem, Toast.LENGTH_LONG).show();
    }

    private void limpaCampos(){
        etCodigoM.setText("");
        etDataM.setText("");
        etDescM.setText("");
        tvEquip.setText("");
        tvTotalM.setText("");
    }

}