package com.pe.af.android.data.net;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.pe.af.android.data.R;
import com.pe.af.android.data.util.UtilitarioData;

import java.util.Properties;

public class Url {

    public static String obtenerBase(Context poContext) {
        Properties mProperties;
        mProperties = UtilitarioData.readPropertiesFromData(poContext, "params.properties");
        String ipServer = mProperties.getProperty("IP_SERVER");

        String lsUrl = "";
        SharedPreferences loSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(poContext);

        String lsRuta = "https://"
                + loSharedPreferences.getString(
                poContext.getString(R.string.keypre_url),
                ipServer) + "/";


        return lsRuta;
    }


}
