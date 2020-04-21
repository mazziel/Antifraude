package com.pe.af.android.antifraude.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.pe.af.android.domain.exception.ErrorBundle;

public interface BaseFragmentView {
    Context getContextFragment();
    void showLoadingFragment(String mensaje);
    void hideLoadingFragment();
    void showErrorFragment(String message);
    void showInfoFragment(String message);
    void showCorrectFragment(String message);
    AlertDialog showDialogOptionAceptarFragment(String titulo, String mensaje, DialogInterface.OnClickListener aceptar);
    AlertDialog showDialogOptionAceptarCancelarFragment(String titulo, String mensaje, DialogInterface.OnClickListener aceptar, DialogInterface.OnClickListener cancelar);
    AlertDialog showErrorDialogFragment(ErrorBundle error, DialogInterface.OnClickListener cancelar);
    AlertDialog showErrorDialogFragment(String error, DialogInterface.OnClickListener cancelar);
}
