package com.pe.af.android.antifraude.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.pe.af.android.antifraude.view.activity.LoginActivity;
import com.pe.af.android.antifraude.view.activity.MenuActivity;

public final class Navigator {

    public static void navigateToLogin(Context context){
        if(context != null){
            Intent intent = LoginActivity.getCallingIntent(context);
            context.startActivity(intent);
        }
    }

    public static void navigateToMenu(Context context){
        if(context != null){
            Intent intent = MenuActivity.getCallingIntent(context);
            ((Activity)context).finish();
            context.startActivity(intent);
        }
    }
}
