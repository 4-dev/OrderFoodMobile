package br.com.orderFood.utils;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author Ruan Alves
 * Class que verifica se há conexão com a internet
 */
public class UtilTCM {
    public static boolean verifyConnection(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean verifyGPS(Context context) {
        LocationManager service = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);

        // Verifica se o GPS está ativo
        boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);

        return enabled;
    }
}
