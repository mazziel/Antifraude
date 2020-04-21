package com.pe.af.android.antifraude.presenter;

import android.os.Handler;

import com.pe.af.android.antifraude.view.SplashView;


public class SplashPresenter {

    private final SplashView view;

    public SplashPresenter(SplashView view) {
        this.view = view;
    }

    public void iniciar() {
        int secondsDelayed = 3;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                view.irLogin();
            }
        }, secondsDelayed * 1000);
    }
}
