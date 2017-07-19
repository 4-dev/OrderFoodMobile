package br.com.orderFood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import br.com.orderFood.R;
import br.com.orderFood.adapter.TabsMainPrincipalAdapter;
import br.com.orderFood.enumerador.TipoCategoria;
import br.com.orderFood.fragment.PedidosFragment;
import br.com.orderFood.fragment.ResumoFragment;
import br.com.orderFood.model.bo.ParametroBO;
import br.com.orderFood.model.bo.ProdutoBO;
import br.com.orderFood.model.entity.Parametro;
import br.com.orderFood.model.entity.Produto;

public class MainActivity extends BaseActivity {

    private Toolbar mToolbar;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setmToolbar();
        tabs();
        // setarProdutos();

    }

    /*private void setarProdutos() {

        try {

            ProdutoBO bo = new ProdutoBO(this);
            if (!bo.isProdutos()) {

                List<Produto> list = new ArrayList<>();
                list.add(new Produto(1, TipoCategoria.PRATOS, 5, "Camarão Tismados", 5));
                list.add(new Produto(2, TipoCategoria.PRATOS, 3.2, "Rosca Herbárea", 5));
                list.add(new Produto(3, TipoCategoria.PRATOS, 12, "Sushi Extremo", 5));
                list.add(new Produto(4, TipoCategoria.PRATOS, 9, "Sandwich Deli", 5));
                list.add(new Produto(5, TipoCategoria.PRATOS, 34, "Lomo De Cerdo Austral", 5));

                list.add(new Produto(6, TipoCategoria.BEBIDAS, 5, "Taza de Café", 5));
                list.add(new Produto(7, TipoCategoria.BEBIDAS, 3.2, "Coctel Tronchatoro", 5));
                list.add(new Produto(8, TipoCategoria.BEBIDAS, 12, "Jugo Natural", 5));
                list.add(new Produto(9, TipoCategoria.BEBIDAS, 9, "Coctel Jordano", 5));
                list.add(new Produto(10, TipoCategoria.BEBIDAS, 34, "Botella Vino Tinto Darius", 5));

                list.add(new Produto(11, TipoCategoria.SOBREMESAS, 5, "Postre De Vainilla", 5));
                list.add(new Produto(12, TipoCategoria.SOBREMESAS, 3.2, "Flan Celestial", 5));
                list.add(new Produto(13, TipoCategoria.SOBREMESAS, 12, "Cupcake Festival", 5));
                list.add(new Produto(14, TipoCategoria.SOBREMESAS, 9, "Pastel De Fresa", 5));
                list.add(new Produto(15, TipoCategoria.SOBREMESAS, 34, "Muffin Amoroso", 5));

                for (Produto p : list) bo.salvar(p);

            }

        } catch (Exception e){
            showAlert("OPS! Ocorreu um erro ao salvar dados iniciais...");
            e.printStackTrace();
        }

    }*/

    private void setmToolbar() {

        mToolbar = (Toolbar) findViewById(R.id.tb_main);
        mToolbar.setTitle("Order Food");
        setSupportActionBar(mToolbar);

    }

    private void tabs() {

        mViewPager = (ViewPager) findViewById(R.id.vp_principal);

        final TabsMainPrincipalAdapter adapter = new TabsMainPrincipalAdapter(getSupportFragmentManager(), MainActivity.this);
        adapter.addFrag(new ResumoFragment(ContextCompat.getColor(this, R.color.accent_material_light)), "Resumo");
        adapter.addFrag(new PedidosFragment(ContextCompat.getColor(this, R.color.ripple_material_light)), "Pedidos");
        mViewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_principal);
        tabLayout.setupWithViewPager(mViewPager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == 0) {
                    ResumoFragment fragment = (ResumoFragment) adapter.instantiateItem(mViewPager, tab.getPosition());
                    if (fragment != null) fragment.onResume();
                } else if (tab.getPosition() == 1) {
                    PedidosFragment fragment2 = (PedidosFragment) adapter.instantiateItem(mViewPager, tab.getPosition());
                    if (fragment2 != null) fragment2.onResume();
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.action_carrito) {

            Intent form = new Intent(getApplicationContext(), PedidoActivity.class);
            startActivity(form);

            ParametroBO parametroBO = new ParametroBO(this);
            Parametro parametro = parametroBO.getParametro();

            /*Parametro parametro = new Parametro();
            parametro.setCodigo(1);
            parametro.setCodEmpresa(1);
            parametro.setCodMesa(2);
            parametro.setStatus("Em Atendimento");*/

            EventBus.getDefault().postSticky(parametro);

        }

        return true;

    }

}

