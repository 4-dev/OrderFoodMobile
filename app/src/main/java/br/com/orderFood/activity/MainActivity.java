package br.com.orderFood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import br.com.orderFood.R;
import br.com.orderFood.adapter.TabsMainPrincipalAdapter;
import br.com.orderFood.fragment.PedidosFragment;
import br.com.orderFood.fragment.ResumoFragment;

public class MainActivity extends BaseActivity {

    private Toolbar mToolbar;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setmToolbar();
        tabs();

    }

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
        }

        return true;

    }

}

