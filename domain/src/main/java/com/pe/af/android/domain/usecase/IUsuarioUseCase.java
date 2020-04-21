package com.pe.af.android.domain.usecase;

import com.pe.af.android.domain.entity.Usuario;
import com.pe.af.android.domain.entity.request.UsuarioRequest;
import com.pe.af.android.domain.exception.IErrorBundle;

public interface IUsuarioUseCase {

    Usuario obtenerUsuario();

    void cerrarSesion();

    void ejecutar(UsuarioRequest usuarioRequest, Callback callback);

    interface Callback {
        void onValidado(String mensaje);

        void onError(IErrorBundle errorBundle);
    }

}
