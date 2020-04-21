package com.pe.af.android.antifraude.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.pe.af.android.antifraude.presenter.BaseActivityPresenter;
import com.pe.af.android.antifraude.util.UtilitarioApp;
import com.pe.af.android.antifraude.view.BaseView;
import com.pe.af.android.antifraude.view.widget.ToastCustom;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {


    BaseActivityPresenter presenter;
    ProgressDialog progressDialog;
    public final String TAG = getClass().getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG,"Inicio Actividad");
        super.onCreate(savedInstanceState);
        presenter = new BaseActivityPresenter(this);
        presenter.iniciar();
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public Intent getIntent() {
        return super.getIntent();
    }

    @Override
    public void finalizar() {
        finish();
    }

    @Override
    public void showloading(String mensaje) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(mensaje);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void hideloading() {
        progressDialog.dismiss();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    @Override
    public void showError(String message) {
        UtilitarioApp.mostrarToast(this, message, ToastCustom.TYPE_ERROR);
    }

    @Override
    public void showCorrect(String message) {
        UtilitarioApp.mostrarToast(this, message, ToastCustom.TYPE_CORRECTO);
    }

    @Override
    public void showInfo(String message) {
        UtilitarioApp.mostrarToast(this, message, ToastCustom.TYPE_INFO);
    }
}
