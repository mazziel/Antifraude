package com.pe.af.android.antifraude.view.activity;

import android.os.Bundle;

import com.pe.af.android.antifraude.R;
import com.pe.af.android.antifraude.navigation.Navigator;
import com.pe.af.android.antifraude.presenter.SplashPresenter;
import com.pe.af.android.antifraude.view.SplashView;

public class SplashActivity extends BaseActivity implements SplashView {

    SplashPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        presenter = new SplashPresenter(this);
        setTheme(R.style.ThemeSplash);
    }

    @Override
    public void irLogin() {
        Navigator.navigateToLogin(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.iniciar();
    }
}