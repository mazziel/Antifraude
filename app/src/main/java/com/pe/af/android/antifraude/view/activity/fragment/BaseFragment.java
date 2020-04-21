package com.pe.af.android.antifraude.view.activity.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.pe.af.android.antifraude.R;
import com.pe.af.android.antifraude.presenter.BaseFragmentPresenter;
import com.pe.af.android.antifraude.util.UtilitarioApp;
import com.pe.af.android.antifraude.view.BaseFragmentView;
import com.pe.af.android.antifraude.view.widget.ToastCustom;
import com.pe.af.android.data.exception.BaseException;
import com.pe.af.android.domain.exception.ErrorBundle;

public abstract class BaseFragment extends Fragment implements BaseFragmentView {
    BaseFragmentPresenter fragmentPresenter;
    ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentPresenter = new BaseFragmentPresenter(this);
    }

    @Override
    public Context getContextFragment() {
        return getContext();
    }

    @Override
    public void showLoadingFragment(String mensaje) {
        Activity a = getActivity();
        if(a!= null) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
            progressDialog = new ProgressDialog(getContext(), R.style.AppCompatAlertDialogStyle);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage(mensaje);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    @Override
    public void hideLoadingFragment() {
        progressDialog.dismiss();
        Activity a = getActivity();
        if(a!= null)
            a.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    @Override
    public void showErrorFragment(String message) {
        UtilitarioApp.mostrarToast(getContext(), message, ToastCustom.TYPE_ERROR);
    }

    @Override
    public void showInfoFragment(String message) {
        UtilitarioApp.mostrarToast(getContext(), message, ToastCustom.TYPE_INFO);
    }

    @Override
    public void showCorrectFragment(String message) {
        UtilitarioApp.mostrarToast(getContext(), message, ToastCustom.TYPE_CORRECTO);
    }

    @Override
    public AlertDialog showDialogOptionAceptarFragment(String titulo, String mensaje, DialogInterface.OnClickListener aceptar) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.mensajeAlerta);
        if (titulo != null)
            builder.setTitle(titulo);

        builder.setMessage(mensaje + "\n");
        builder.setCancelable(false);
        builder.setPositiveButton("ACEPTAR", aceptar);
        final AlertDialog dialogo = builder.create();
        dialogo.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button si = dialogo.getButton(DialogInterface.BUTTON_POSITIVE);
                si.setTextColor(getResources().getColor(R.color.color_orange));
                //si.setBackgroundResource(R.drawable.boton_mensaje);
            }
        });
        dialogo.show();
        return dialogo;
    }

    @Override
    public AlertDialog showDialogOptionAceptarCancelarFragment(String titulo, String mensaje, DialogInterface.OnClickListener aceptar, DialogInterface.OnClickListener cancelar) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.mensajeAlerta);
        if (titulo != null)
            builder.setTitle(titulo);

        builder.setMessage(mensaje + "\n");
        builder.setCancelable(false);
        builder.setPositiveButton("ACEPTAR", aceptar);
        builder.setNegativeButton("CANCELAR", cancelar);
        final AlertDialog dialogo = builder.create();
        dialogo.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button si = dialogo.getButton(DialogInterface.BUTTON_POSITIVE);
                si.setTextColor(getResources().getColor(R.color.color_orange));
                //si.setBackgroundResource(R.drawable.boton_mensaje);
                Button no = dialogo.getButton(DialogInterface.BUTTON_NEGATIVE);
                no.setTextColor(getResources().getColor(R.color.color_grey));
            }
        });
        dialogo.show();
        return dialogo;
    }

    @Override
    public AlertDialog showErrorDialogFragment(ErrorBundle error, DialogInterface.OnClickListener cancelar) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.mensajeAlerta);
        if (((BaseException)error.getException()).getCodeError() != null) {
            builder.setTitle("Error: " + ((BaseException)error.getException()).getCodeError());
        } else {
            builder.setTitle("Error");
        }

        builder.setMessage(error.getErrorMessage() + "\n");
        builder.setCancelable(false);
        builder.setNegativeButton("CANCELAR", cancelar);
        final AlertDialog dialogo = builder.create();
        dialogo.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button no = dialogo.getButton(DialogInterface.BUTTON_NEGATIVE);
                no.setTextColor(getResources().getColor(R.color.color_grey));
            }
        });
        dialogo.show();
        return dialogo;
    }

    @Override
    public AlertDialog showErrorDialogFragment(String error, DialogInterface.OnClickListener cancelar) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.mensajeAlerta);
        builder.setTitle("Error");
        builder.setMessage(error + "\n");
        builder.setCancelable(false);
        builder.setNegativeButton("CANCELAR", cancelar);
        final AlertDialog dialogo = builder.create();
        dialogo.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Button no = dialogo.getButton(DialogInterface.BUTTON_NEGATIVE);
                no.setTextColor(getResources().getColor(R.color.color_grey));
            }
        });
        dialogo.show();
        return dialogo;
    }
}
