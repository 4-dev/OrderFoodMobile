package br.com.orderFood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;

import br.com.orderFood.R;
import br.com.orderFood.interfaces.APIServiceConection;
import br.com.orderFood.model.bo.ParametroBO;
import br.com.orderFood.utils.UtilTCM;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ScannerQrCodeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        getIntentIntegrator();
        buscarQrCodeManual();

    }

    private void buscarQrCodeManual() {

        ImageView imageView = (ImageView) findViewById(R.id.imagem_scanner_qrcode);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIntentIntegrator();
            }
        });

    }

    private void getIntentIntegrator() {

        if (UtilTCM.verifyConnection(getApplicationContext())) {

            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setOrientationLocked(false);
            integrator.initiateScan();

        } else {
            showAlertRede();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {


            if (result != null) {
                if (result.getContents() == null) {

                    Log.d("MainActivity", "Cancelled scan");
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();

                } else {

                    verificarMesaQRCode();

                   //Intent form = new Intent(getApplicationContext(), MainActivity.class);
                    //startActivity(form);

                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }

        }
    }

    private void verificarMesaQRCode() {

        showProgressDialog(getString(R.string.mensagem_progress));
        String URL = "https://orderfood.cfapps.io/mesa/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIServiceConection serviceConection = retrofit.create(APIServiceConection.class);
        final Call<String> requester = serviceConection.verificarColaborador(1);

        new Thread() {

            public void run() {

                try {

                    String retornoChamada = requester.execute().body();

                    if (retornoChamada != null) {

                        /*ParametroBO parametroBO = new ParametroBO(this);
                        parametroBO.limparTabelas();
                        parametroBO = null;*/

                        mTextToast = "RETORNO: " + retornoChamada;
                        runOnUiThread(changeMessageToastALERT);

                        Intent form = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(form);

                    } else {
                        mTextToast = "Sem Resultados...";
                        runOnUiThread(changeMessageToastALERT);
                    }

                    mProgressDialog.dismiss();
                    Thread.interrupted();

                } catch (IOException e) {

                    mProgressDialog.dismiss();
                    Thread.interrupted();
                    e.printStackTrace();

                    mTextToast = "Conexão não estabelecida...";
                    runOnUiThread(changeMessageToastERROR);

                } catch (Exception e) {

                    mProgressDialog.dismiss();
                    Thread.interrupted();
                    e.printStackTrace();

                    mTextToast = "Erro ao conectar ao servidor";
                    runOnUiThread(changeMessageToastERROR);

                }

            }

        }.start();

    }


}

