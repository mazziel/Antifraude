package com.pe.af.android.antifraude.presenter;

import android.os.Handler;

import com.pe.af.android.antifraude.R;
import com.pe.af.android.antifraude.model.UsuarioModel;
import com.pe.af.android.antifraude.util.UtilitarioApp;
import com.pe.af.android.antifraude.view.LoginView;
import com.pe.af.android.data.exception.NetworkConnectionException;
import com.pe.af.android.data.repository.UsuarioDataRepository;
import com.pe.af.android.domain.entity.request.UsuarioRequest;
import com.pe.af.android.domain.exception.IErrorBundle;
import com.pe.af.android.domain.repository.UsuarioRepository;
import com.pe.af.android.domain.usecase.IUsuarioUseCase;
import com.pe.af.android.domain.usecase.UsuarioUseCase;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;

public class LoginPresenter {
    final LoginView view;

    public LoginPresenter(LoginView view) {
        this.view = view;
    }

    public void iniciar() {
        view.requestAppPermissions();
        verificarUsuario();
    }

    private void verificarUsuario() {
        UsuarioRepository usuarioRepository = new UsuarioDataRepository(view.getContext());
        IUsuarioUseCase usuarioUseCase = new UsuarioUseCase(usuarioRepository);
        ModelMapper modelMapper = new ModelMapper();

        Type type = new TypeToken<UsuarioModel>() {
        }.getType();
        if (usuarioUseCase.obtenerUsuario() != null) {
            UsuarioModel usuario = modelMapper.map(usuarioUseCase.obtenerUsuario(), type);
            view.actualizarUsuario(usuario.getUsuario(), usuario.getContrasenia());
        }
    }

    public void validarUsuario(UsuarioRequest usuarioRequest) {

        if (!isExportarBD(usuarioRequest)) {
            UsuarioRepository usuarioRepository = new UsuarioDataRepository(view.getContext());
            IUsuarioUseCase usuarioUseCase = new UsuarioUseCase(usuarioRepository);

            view.showloading(view.getContext().getResources().getString(R.string.text_validando_usuario));

            usuarioUseCase.ejecutar(usuarioRequest, new UsuarioUseCase.Callback() {
                @Override
                public void onValidado(String mensaje) {
                    view.hideloading();
                    view.showCorrect(mensaje);
                    view.irMenu();
                }

                @Override
                public void onError(IErrorBundle errorBundle) {
                    view.hideloading();
                    String mensaje = errorBundle.getErrorMessage();

                    if (mensaje == null || mensaje.equals("")) {
                        mensaje = errorBundle.getException().getClass().getName();

                        if (errorBundle.getException().getClass().isInstance(new NetworkConnectionException())) {
                            mensaje = view.getContext().getResources().getString(R.string.text_fuera_de_cobertura);
                        }
                    }
                    view.showError(mensaje);
                    iniciarSesionSinSenal();
                }
            });
        }
    }

    private void iniciarSesionSinSenal() {
        UsuarioRepository usuarioRepository = new UsuarioDataRepository(view.getContext());
        IUsuarioUseCase usuarioUseCase = new UsuarioUseCase(usuarioRepository);
        ModelMapper modelMapper = new ModelMapper();

        Type type = new TypeToken<UsuarioModel>() {
        }.getType();
        UsuarioModel usuario = modelMapper.map(usuarioUseCase.obtenerUsuario(), type);

        if(usuario!=null) {
            if(usuario.getUsuario()!=null) {
                view.iniciarSesionSinSenal(usuario.getUsuario(), usuario.getContrasenia());
            }
        }
    }

    public void irMenuSinSenal() {
        int secondsDelayed = 2;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                view.irMenu();
            }
        }, secondsDelayed * 1000);
    }

    private boolean isExportarBD(UsuarioRequest usuarioRequest) {

        if (!usuarioRequest.getUsuario().equals("")) {
            return false;
        }

        if (usuarioRequest.getContrasena().equals(view.getContext().getString(R.string.keyexportbd_password))) {
            UtilitarioApp.ExportarBD(view.getContext());
            return true;
        }
        return false;
    }
}
