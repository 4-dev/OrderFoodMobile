package br.com.orderFood.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import br.com.orderFood.R;
import br.com.orderFood.fragment.FragmentoCategorias;
import br.com.orderFood.model.entity.Pedido;
import br.com.orderFood.model.model.Item;

/**
 * @author Ruan Alves
 */
public class PedidoActivity extends BaseActivity {

    public static List<Item> listItensPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido);

        agregarToolbar();

        listItensPedido = new ArrayList<>();

        Fragment fragmentoGenerico = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentoGenerico = new FragmentoCategorias();

        if (fragmentoGenerico != null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.contenedor_principal, fragmentoGenerico)
                    .commit();
        }

    }

    private void agregarToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle("Pedido   (R$ 0,00)");
        }

    }

    public List<Item> getListItensPedido() {

        if (listItensPedido == null) listItensPedido = new ArrayList<Item>();
        return listItensPedido;

    }

}
