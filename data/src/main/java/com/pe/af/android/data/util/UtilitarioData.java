package com.pe.af.android.data.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.pe.af.android.data.R;

import java.io.InputStream;
import java.util.Properties;

public class UtilitarioData {

    public static Properties readPropertiesFromData(Context ctx, String arc) {
        Properties prop = null;
        try {
            AssetManager am = ctx.getAssets();
            InputStream is = am.open(arc);
            prop = new Properties();
            prop.load(is);
        } catch (Exception ex) {
            Log.e("readPropertiesFromData","Error",ex);
        }
        return prop;
    }

    public static String parseOrigen(Location poLocation) {
        String origen = "F";
        if (poLocation != null) {
            String psProvider = poLocation.getProvider();
            Log.v("UtilitarioData",psProvider);
            if ((poLocation.getLatitude() != 0 && poLocation.getLatitude() != 0)
                    && psProvider != null) {
                psProvider = psProvider.toUpperCase();
                if (psProvider.startsWith("G")) {
                    origen = "S";
                } else if (psProvider.startsWith("N")) {
                    origen = "C";
                }
            }
        }
        return origen;
    }

    public static String getRaiz(Context context){
        return context.getString(R.string.app_name);
    }

    public static boolean isThereInternetConnection(Context context) {
        boolean isConnected;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }
}
