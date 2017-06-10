package br.com.orderFood.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;

import br.com.orderFood.R;

/**
 * Created by Ruan Alves
 */

public class BaseFragment extends Fragment {

    //MaterialDialog dialog;
    ProgressDialog mProgressDialog;
    String mTextToast = "";

    public void showAlert(String message){
        Snackbar.make(getView(), message,
                Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    public void showAlertRede(){
        Snackbar snackbar = Snackbar
                .make(getView(), "Sem conexão com Internet. Por favor, verifique sey WiFi ou 3G.", Snackbar.LENGTH_LONG)
                .setAction("Habilitar", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent it = new Intent(Settings.ACTION_WIFI_SETTINGS);
                        startActivity(it);
                    }
                });
        snackbar.show();
    }

    public void showProgressDialog(String texto) {

        mProgressDialog = new ProgressDialog(getActivity(), R.style.AppTheme_Dark_Dialog);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage(texto);
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mProgressDialog.setCancelable(true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.show();

    }

    public Runnable changeMessageToastINF = new Runnable() {
        @Override
        public void run() {
            showAlert(mTextToast);
        }
    };

    public Runnable changeMessageToastALERT = new Runnable() {
        @Override
        public void run() {
            showAlert(mTextToast);
        }
    };

    public Runnable changeMessageToastERROR = new Runnable() {
        @Override
        public void run() {
            showAlert(mTextToast);
        }
    };

}
