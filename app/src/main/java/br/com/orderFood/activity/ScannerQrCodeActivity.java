package br.com.orderFood.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import br.com.orderFood.R;
import br.com.orderFood.dto.ObjectSync;
import br.com.orderFood.interfaces.APIServiceConection;
import br.com.orderFood.model.bo.ParametroBO;
import br.com.orderFood.model.bo.RetornoQrCodeBO;
import br.com.orderFood.retrofit.ObjectSyncGson;
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

                    String SCAN_RESULT_CODMESA = data.getStringExtra("SCAN_RESULT"); // Deyvid Costa
                    String formatName = data.getStringExtra("SCAN_RESULT_FORMAT"); // Deyvid Costa

                    EventBus.getDefault().postSticky(SCAN_RESULT_CODMESA);

                    verificarMesaQRCode(Integer.parseInt(SCAN_RESULT_CODMESA));

                    /*//verificarMesaQRCode(2);

                    Intent form = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(form);*/

                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }

        }
    }

    private void verificarMesaQRCode(final int SCAN_RESULT_CODMESA) {

        final Activity activity = this;
        showProgressDialog(getString(R.string.mensagem_progress));
        String URL = "https://orderfood.cfapps.io/mesa/";
//        String URL = "http://192.168.6.134:9090/mesa/";
       // String URL = "http://10.0.0.195:9090/mesa/";
        Gson gson = new GsonBuilder().registerTypeAdapter(ObjectSync.class, new ObjectSyncGson()).create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        APIServiceConection serviceConection = retrofit.create(APIServiceConection.class);
        final Call<ObjectSync> requester = serviceConection.verificarmesa(SCAN_RESULT_CODMESA);

        new Thread() {

            public void run() {

                try {

                    ParametroBO parametroBO = new ParametroBO(activity);
                    parametroBO.limparTabelas();
                    parametroBO = null;

                    ObjectSync objectSync = requester.execute().body();

                    if (objectSync != null) {

                        if (!objectSync.getMensagem().equalsIgnoreCase("Mesa ocupada")) {

                            RetornoQrCodeBO mCodeBO = new RetornoQrCodeBO(activity);
                            objectSync.setCodmesa(SCAN_RESULT_CODMESA);
                            if (mCodeBO.salvar(objectSync)) {

                                Intent form = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(form);
                                finish();

                            } else {
                                mTextToast = "Ops! Ocorreu um erro inesperado";
                            }

                        } else {
                            mTextToast = "Mesa ocupada";
                            runOnUiThread(changeMessageToastALERT);
                        }

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

