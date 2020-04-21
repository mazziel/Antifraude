package com.pe.af.android.antifraude.view.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.EditText;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.pe.af.android.antifraude.R;
import com.pe.af.android.antifraude.navigation.Navigator;
import com.pe.af.android.antifraude.presenter.LoginPresenter;
import com.pe.af.android.antifraude.view.LoginView;
import com.pe.af.android.domain.entity.request.UsuarioRequest;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginView {

    LoginPresenter presenter;

    @InjectView(R.id.login_usuario)
    EditText txtUsuario;
    @InjectView(R.id.login_clave)
    EditText txtClave;
    private final int REQUEST_WRITE_STORAGE_REQUEST_CODE = 201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        presenter = new LoginPresenter(this);
        presenter.iniciar();
    }

    public void requestAppPermissions() {
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }

        if (hasReadPermissions() && hasWritePermissions()) {
            return;
        }

        ActivityCompat.requestPermissions(this,
                new String[] {
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, REQUEST_WRITE_STORAGE_REQUEST_CODE); // your request code
    }

    private boolean hasReadPermissions() {
        return (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasWritePermissions() {
        return (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    @OnClick(R.id.login_btn_ingresar)
    public void validarUsuario() {
        UsuarioRequest usuarioRequest = new UsuarioRequest();
        usuarioRequest.setUsuario(txtUsuario.getText().toString());
        usuarioRequest.setContrasena(txtClave.getText().toString());

        presenter.validarUsuario(usuarioRequest);
    }

    @Override
    public void irMenu() {
        Navigator.navigateToMenu(this);
    }

    @Override
    public void actualizarUsuario(String codigo, String clave) {
        txtUsuario.setText(codigo);
        txtClave.setText(clave);
    }

    @Override
    public void iniciarSesionSinSenal(String codigo, String clave) {
        if(txtUsuario.getText().toString().equals(codigo) &&
                txtClave.getText().toString().equals(clave)){
            showInfo(getResources().getString(R.string.text_iniciando_session_fuera_de_cobertura));
            presenter.irMenuSinSenal();
        }
    }

    public static Intent getCallingIntent(Context context){
        return new Intent(context,LoginActivity.class);
    }
}
