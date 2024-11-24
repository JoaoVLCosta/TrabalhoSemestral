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
import br.edu.fateczl.trabalhosemestral.controller.ConsultasManutencaoController;
import br.edu.fateczl.trabalhosemestral.model.ConteinerDTO;

public class ConsultasManutencaoFragment extends Fragment {
    /*
     *@author:<JOÃO VITOR LIMA COSTA>
     */

    private EditText etCodigoCM;

    private Button btBuscarCM, btListarCM, btExcluirCM, btVoltarCM;

    private TextView tvRetornoCM;

    private View view;

    private String nomeTabela = "ordens_manutencao";

    private ConsultasManutencaoController conCont;

    public ConsultasManutencaoFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_consultas_manutencao, container, false);

        conCont = new ConsultasManutencaoController();

        etCodigoCM = view.findViewById(R.id.etCodigoCM);

        btBuscarCM = view.findViewById(R.id.btBuscarCM);
        btListarCM = view.findViewById(R.id.btListarCM);
        btExcluirCM = view.findViewById(R.id.btExcluirCM);
        btVoltarCM = view.findViewById(R.id.btVoltarCM);

        tvRetornoCM = view.findViewById(R.id.tvRetornoCM);

        tvRetornoCM.setMovementMethod(new ScrollingMovementMethod());

        btBuscarCM.setOnClickListener(op -> acaoBuscar());
        btListarCM.setOnClickListener(op -> acaoListar());
        btExcluirCM.setOnClickListener(op -> acaoExcluir());

        btVoltarCM.setOnClickListener(op -> voltar((IAlternadorFragment) view.getContext()));

        return view;
    }

    private void acaoExcluir(){
        try {

            String cPrimaria = etCodigoCM.getText().toString();

            if(!cPrimaria.isEmpty()) {

                ConteinerDTO conteiner = new ConteinerDTO(nomeTabela, view.getContext());

                conteiner.addDado("cod_os", cPrimaria);
                conteiner.organizarDados();

                conCont.excluir(conteiner);

                Toast.makeText(view.getContext(), "Ordem Excluida com Sucesso", Toast.LENGTH_LONG).show();

            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        etCodigoCM.setText("");
    }

    private void acaoBuscar(){
        try {

            String cPrimaria = etCodigoCM.getText().toString();

            if(!cPrimaria.isEmpty()){

                ConteinerDTO conteiner = new ConteinerDTO(nomeTabela, view.getContext());

                conteiner.addDado("cod_os", cPrimaria);
                conteiner.organizarDados();

                ConteinerDTO retorno = conCont.buscar(conteiner);

                StringBuffer buffer = new StringBuffer();

                for(String chave : retorno){
                    if(chave.equals("equipamentos")){
                        continue;
                    } else {
                        buffer.append(chave).append(" - ").append(retorno.getDadoByColuna(chave)).append("\n");
                    }
                }

                buffer.append("\nEQUIPAMENTOS:\n");

                List<ConteinerDTO> equipamentos = (List<ConteinerDTO>) retorno.getDadoByColuna("equipamentos");

                for(ConteinerDTO equip : equipamentos){
                    buffer.append(equip).append(" - Quantidade: ").append(equip.getDadoByColuna("quantidade")).append("\n");
                }

                tvRetornoCM.setText(buffer.toString());
            }
        } catch (SQLException e) {
            Toast.makeText(view.getContext(), "Ordem de Serviço não Encontrada", Toast.LENGTH_LONG).show();
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

            tvRetornoCM.setText(buffer.toString());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void voltar(IAlternadorFragment alternador) {
        alternador.trocarFragment(new OSFragment());
    }

}