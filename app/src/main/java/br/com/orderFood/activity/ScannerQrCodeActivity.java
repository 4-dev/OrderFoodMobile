package br.com.orderFood.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.orderFood.R;
import br.com.orderFood.dto.ObjectSync;
import br.com.orderFood.fragment.PedidosFragment;
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

        if (permissoesHabilitadas()) {
            getIntentIntegrator();
            buscarQrCodeManual();
        }

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

                    String SCAN_RESULT = data.getStringExtra("SCAN_RESULT"); // Deyvid Costa
                    String formatName = data.getStringExtra("SCAN_RESULT_FORMAT"); // Deyvid Costa

                    verificarMesaQRCode(Integer.parseInt(SCAN_RESULT));

                    /*Intent form = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(form);*/

                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }

        }
    }

    public String getIMEI() {

        String imei = "";
        TelephonyManager telephonyManager = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);

        if(telephonyManager != null) imei = telephonyManager.getDeviceId();
        WifiManager wifiMan = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if(imei == null || imei.equals("")) {
            WifiInfo wifiInf = wifiMan.getConnectionInfo();
            imei = wifiInf.getMacAddress();
        }

        return imei;

    }

    private void verificarMesaQRCode(final int SCAN_RESULT) {

        final Activity activity = this;
        showProgressDialog(getString(R.string.mensagem_progress));
        String URL = "https://orderfood.cfapps.io/mesa/";
//        String URL = "http://192.168.15.7:9090/mesa/";
        // String URL = "http://10.0.0.195:9090/mesa/";
        Gson gson = new GsonBuilder().registerTypeAdapter(ObjectSync.class, new ObjectSyncGson()).create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        APIServiceConection serviceConection = retrofit.create(APIServiceConection.class);
        final Call<ObjectSync> requester = serviceConection.verificarmesa(SCAN_RESULT, getIMEI());

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
                            if (mCodeBO.salvar(objectSync,SCAN_RESULT)) {

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

    private boolean permissoesHabilitadas() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            Log.d("NGVL", "permissoesHabilitadas::BEGIN");
            List<String> permissoes = new ArrayList<>();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED) {
                permissoes.add(Manifest.permission.READ_PHONE_STATE);
            }

            if (!permissoes.isEmpty()) {
                String[] array = new String[permissoes.size()];
                permissoes.toArray(array);
                ActivityCompat.requestPermissions(this, array, 1);
            }

            Log.d("NGVL", "permissoesHabilitadas::END");
            return permissoes.isEmpty();

        }

        return true;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Log.d("NGVL", "onRequestPermissionsResult::BEGIN");

        for (int i = 0; i < permissions.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                break;
            } else {
                getIntentIntegrator();
                buscarQrCodeManual();
            }
        }

        Log.d("NGVL", "onRequestPermissionsResult::END");

    }

}

