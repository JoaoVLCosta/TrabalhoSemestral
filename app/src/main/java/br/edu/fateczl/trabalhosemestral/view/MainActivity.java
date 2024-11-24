package br.edu.fateczl.trabalhosemestral.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.HashMap;
import java.util.Map;

import br.edu.fateczl.trabalhosemestral.R;

public class MainActivity extends AppCompatActivity implements IAlternadorFragment{
    /*
     *@author:<JOÃO VITOR LIMA COSTA>
     */

    private final HashMap<Integer, Fragment> MAPA_DE_FRAGMENTS = new HashMap<>(Map.ofEntries(
            Map.entry(R.id.item_equip, new EquipamentoFragment()),
            Map.entry(R.id.item_cliente, new ClienteFragment()),
            Map.entry(R.id.item_os, new OSFragment())
    ));

    private Fragment fragment = new InicioFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            fragment = MAPA_DE_FRAGMENTS.get(bundle.getInt("id"));
        }
        trocarFragment(fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, MainActivity.class);

        bundle.putInt("id", item.getItemId());

        intent.putExtras(bundle);
        this.startActivity(intent);
        this.finish();
        return true;
    }

    @Override
    public void trocarFragment(Fragment fragmentoAlvo){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragmentoAlvo);
        fragmentTransaction.commit();
    }
}