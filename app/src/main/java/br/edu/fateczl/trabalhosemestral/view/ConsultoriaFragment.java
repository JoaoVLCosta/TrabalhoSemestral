package br.edu.fateczl.trabalhosemestral.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
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
import java.util.List;

import br.edu.fateczl.trabalhosemestral.R;
import br.edu.fateczl.trabalhosemestral.controller.ConsultoriaController;
import br.edu.fateczl.trabalhosemestral.controller.ISolicitacoes;
import br.edu.fateczl.trabalhosemestral.controller.SolicitacoesController;
import br.edu.fateczl.trabalhosemestral.model.ConteinerDTO;

public class ConsultoriaFragment extends Fragment {
    /*
     *@author:<JOÃO VITOR LIMA COSTA>
     */

    private EditText etCodigo, etData, etConsultor, etCusto, etDesc, etHoras;

    private Spinner spCliente;

    private TextView tvTotal;

    private Button btVoltar, btInserir, btCalcular;

    private View view;

    private ConsultoriaController consultoria;

    private String TABELA = "ordens_consultoria";

    public ConsultoriaFragment() {
        super();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_consultoria, container, false);

        consultoria = new ConsultoriaController();

        etCodigo = view.findViewById(R.id.etCodigoCC);
        etData = view.findViewById(R.id.etData);
        etConsultor = view.findViewById(R.id.etConsultor);
        etCusto = view.findViewById(R.id.etCusto);
        etDesc = view.findViewById(R.id.etDesc);
        etHoras = view.findViewById(R.id.etHoras);

        etData.addTextChangedListener(new TextWatcher() {
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
                    etData.removeTextChangedListener(this);
                    etData.setText(currentText);
                    etData.setSelection(cursorPosition);
                    etData.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        spCliente = view.findViewById(R.id.spCliente);
        preencheSpinner();

        tvTotal = view.findViewById(R.id.tvTotal);

        btInserir = view.findViewById(R.id.btInserir);
        btCalcular = view.findViewById(R.id.btCalcular);
        btVoltar = view.findViewById(R.id.btVoltar);

        btInserir.setOnClickListener(op -> acaoInserir());
        btCalcular.setOnClickListener( op -> calcular());
        btVoltar.setOnClickListener(op -> voltar((IAlternadorFragment) view.getContext()));

        return view;
    }

    private void acaoInserir() {
        try {

            if( !tvTotal.getText().toString().isEmpty() ){

                if(validarCampos()){

                    ConteinerDTO conteiner = montaObjeto();

                    if(conteiner != null){
                        consultoria.inserir(conteiner);

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

        int sPos = spCliente.getSelectedItemPosition();

        if(sPos > 0){

            if(validarCampos()){

                conteiner = montaObjeto();

                if(conteiner != null){
                    float custoTotal = conteiner.calcularValor();

                    String texto = "Total: R$" + custoTotal;

                    tvTotal.setText( texto );
                }

            } else {
                exibirToast("Preencha todos os campos");
            }

        } else {
            exibirToast("Selecione um Cliente");
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

        try {
            List<ConteinerDTO> conteineres = solicitacoes.listar(new ConteinerDTO("clientesF", view.getContext()));
            conteineres.add(0, conteiner);

            List<ConteinerDTO> conteineresJ = solicitacoes.listar(new ConteinerDTO("clientesJ", view.getContext()));

            for(ConteinerDTO c : conteineresJ){
                conteineres.add(c);
            }

            ArrayAdapter ad = new ArrayAdapter<>(view.getContext(),
                    android.R.layout.simple_spinner_item,
                    conteineres);

            ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spCliente.setAdapter(ad);

        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private boolean validarCampos(){
        return validacao(etCodigo, etData, etConsultor, etCusto, etDesc, etHoras);
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

        int codigo = Integer.parseInt(etCodigo.getText().toString());
        String data = etData.getText().toString();
        String consultor = etConsultor.getText().toString();
        float custo = Float.parseFloat(etCusto.getText().toString());
        int horas = Integer.parseInt(etHoras.getText().toString());
        String descricao = etDesc.getText().toString();

        conteiner.addDado("cod_os", codigo);
        conteiner.addDado("data_realizacao", data);
        conteiner.addDado("consultor", consultor);
        conteiner.addDado("custo", custo);
        conteiner.addDado("horas", horas);
        conteiner.addDado("descricao", descricao);

        conteiner.addDado("cliente", ( (ConteinerDTO) spCliente.getSelectedItem() ).getArmazenavel() );

        try {

            conteiner.organizarDados();

        } catch (DateTimeParseException e) {
            exibirToast("Formato de data inválido");
            etData.setText("");
            return null;
        }


        return conteiner;
    }

    private void exibirToast(String mensagem){
        Toast.makeText(view.getContext(), mensagem, Toast.LENGTH_LONG).show();
    }

    private void limpaCampos(){
        etCodigo.setText("");
        etData.setText("");
        etConsultor.setText("");
        etCusto.setText("");
        etDesc.setText("");
        etHoras.setText("");
        tvTotal.setText("");
    }
}