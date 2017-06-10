/*
 * BaseActivity.java
 * Copyright (c) SN SISTEMAS.
 *
 * Este software é confidencial e propriedade da SN SISTEMAS.
 * Não é permitida sua distribuição ou divulgação do seu conteúdo sem expressa autorização da SN SISTEMAS.
 * Este arquivo contém informações proprietárias.
 */
package br.com.orderFood.activity;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;

import br.com.orderFood.R;

/**
 * Created by Ruan Alves
 */

public class BaseActivity extends AppCompatActivity {

    MaterialDialog mProgressDialog;
    String mTextToast = "";

    public void showAlert(String message){
        Snackbar.make(findViewById(android.R.id.content),message,Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }

    public void showAlertRede(){
        Snackbar snackbar = Snackbar
                .make(findViewById(android.R.id.content), "Sem conexão com Internet. Por favor, verifique sey WiFi ou 3G.", Snackbar.LENGTH_LONG)
                .setAction("Habilitar", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent it = new Intent(Settings.ACTION_WIFI_SETTINGS);
                        startActivity(it);
                    }
                });
        snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.coloLink));
        snackbar.show();
    }

    public void showFixedBottom(String message){
        Snackbar.make(findViewById(android.R.id.content),message,Snackbar.LENGTH_INDEFINITE).setAction("Action", null).show();
    }

    public void hideKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void setStringFromEdit(int id, String text){
        EditText input = (EditText) findViewById(id);
        input.setText(text);
    }

    public void showProgressDialog(String message){
        mProgressDialog = new MaterialDialog.Builder(this)
                .content(message)
                .progress(true,0)
                .widgetColor((ContextCompat.getColor(this, R.color.buttonDialog)))
                    .show();
    }

    public void dismissProgressDialog(){
        if(mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
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
