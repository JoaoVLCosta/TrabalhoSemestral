package br.edu.fateczl.trabalhosemestral.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
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
import br.edu.fateczl.trabalhosemestral.controller.ConsultasConsultoriaController;
import br.edu.fateczl.trabalhosemestral.model.ConteinerDTO;

public class ConsultasConsultoriaFragment extends Fragment {
    /*
     *@author:<JOÃO VITOR LIMA COSTA>
     */

    private EditText etCodigoCC;

    private Button btBuscarCC, btListarCC, btExcluirCC, btVoltarCC;

    private TextView tvRetornoCC;

    private View view;

    private String nomeTabela = "ordens_consultoria";

    private ConsultasConsultoriaController conCont;

    public ConsultasConsultoriaFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_consultas_consultoria, container, false);

        conCont = new ConsultasConsultoriaController();

        etCodigoCC = view.findViewById(R.id.etCodigoCC);

        btBuscarCC = view.findViewById(R.id.btBuscarCC);
        btListarCC = view.findViewById(R.id.btListarCC);
        btExcluirCC = view.findViewById(R.id.btExcluirCC);
        btVoltarCC = view.findViewById(R.id.btVoltarCC);

        tvRetornoCC = view.findViewById(R.id.tvRetornoCC);

        tvRetornoCC.setMovementMethod(new ScrollingMovementMethod());

        btListarCC.setOnClickListener(op -> acaoListar());
        btBuscarCC.setOnClickListener(op -> acaoBuscar());
        btExcluirCC.setOnClickListener(op -> acaoExcluir());

        btVoltarCC.setOnClickListener(op -> voltar((IAlternadorFragment) view.getContext()));

        return view;
    }

    private void acaoExcluir(){
        try {

            String cPrimaria = etCodigoCC.getText().toString();

            if(!cPrimaria.isEmpty()) {

                ConteinerDTO conteiner = new ConteinerDTO(nomeTabela, view.getContext());

                conteiner.addDado("cod_os", cPrimaria);
                conteiner.organizarDados();

                conCont.excluir(conteiner);

                Toast.makeText(view.getContext(), "Cliente Excluido com Sucesso", Toast.LENGTH_LONG).show();

            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        etCodigoCC.setText("");
    }

    private void acaoBuscar(){
        try {

            String cPrimaria = etCodigoCC.getText().toString();

            if(!cPrimaria.isEmpty()){

                ConteinerDTO conteiner = new ConteinerDTO(nomeTabela, view.getContext());

                conteiner.addDado("cod_os", cPrimaria);
                conteiner.organizarDados();

                ConteinerDTO retorno = conCont.buscar(conteiner);

                StringBuffer buffer = new StringBuffer();

                for(String chave : retorno){
                    buffer.append(chave).append(" - ").append(retorno.getDadoByColuna(chave)).append("\n");
                }

                tvRetornoCC.setText(buffer.toString());

            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), "Cliente não Encontrado", Toast.LENGTH_LONG).show();
        }
    }

    private void acaoListar(){

        try {

            List<ConteinerDTO> conteineres = new ArrayList<>();

            conteineres = conCont.listar( new ConteinerDTO( nomeTabela, view.getContext() ) );

            StringBuffer buffer = new StringBuffer();

            for(ConteinerDTO dado : conteineres){
                buffer.append(dado);
                buffer.append("\n");
            }

            tvRetornoCC.setText(buffer.toString());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void voltar(IAlternadorFragment alternador) {
        alternador.trocarFragment(new OSFragment());
    }

}