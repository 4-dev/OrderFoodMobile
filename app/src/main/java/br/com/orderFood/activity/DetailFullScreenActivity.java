package br.com.orderFood.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import br.com.orderFood.R;
import br.com.orderFood.dialog.FullScreen_ProdutosDialog;

/**
 * @author Ruan Alves
 */
public class DetailFullScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            crearFullScreenDialog();
        }

    }

    private void crearFullScreenDialog() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FullScreen_ProdutosDialog newFragment = new FullScreen_ProdutosDialog();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(android.R.id.content, newFragment, "FullScreenFragment").commit();

    }

}
