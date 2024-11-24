package br.edu.fateczl.trabalhosemestral.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import br.edu.fateczl.trabalhosemestral.R;

public class OSFragment extends Fragment {
    /*
     *@author:<JOÃO VITOR LIMA COSTA>
     */

    private IAlternadorFragment alternador;

    private RadioGroup rgOS;

    private RadioButton rbConsultoria, rbManutencao;

    private Button btConsultar, btGerar;

    private View view;

    private Fragment fragmentAlvoGerar = new ConsultoriaFragment();

    private Fragment fragmentAlvoConsultar = new ConsultoriaFragment();

    public OSFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_os, container, false);
        alternador = (IAlternadorFragment) this.getContext();

        btConsultar = view.findViewById(R.id.btConsultar);
        btGerar = view.findViewById(R.id.btGerar);

        rgOS = view.findViewById(R.id.rgOS);
        rbConsultoria = view.findViewById(R.id.rbConsultoria);
        rbManutencao = view.findViewById(R.id.rbManutencao);

        rgOS.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbConsultoria) {
                fragmentAlvoGerar = new ConsultoriaFragment();
                fragmentAlvoConsultar = new ConsultasConsultoriaFragment();
            } else {
                fragmentAlvoGerar = new ManutencaoFragment();
                fragmentAlvoConsultar = new ConsultasManutencaoFragment();
            }
        });

        rbConsultoria.setChecked(true);

        btConsultar.setOnClickListener(op -> {
            alternador.trocarFragment(fragmentAlvoConsultar);
        });

        btGerar.setOnClickListener(op -> {
            alternador.trocarFragment(fragmentAlvoGerar);
        });

        return view;
    }
}