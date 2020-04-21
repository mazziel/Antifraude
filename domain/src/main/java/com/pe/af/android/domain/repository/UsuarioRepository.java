package com.pe.af.android.domain.repository;


import com.pe.af.android.domain.entity.Usuario;
import com.pe.af.android.domain.entity.request.UsuarioRequest;
import com.pe.af.android.domain.exception.IErrorBundle;

public interface UsuarioRepository {

    void guardarUsuario(Usuario usuario);

    Usuario obtenerUsuario();

    void eliminarUsuario();

    void validarUsuario(UsuarioRequest usuarioRequest, ValidarUsuarioCallback validarUsuarioCallback);

    interface ValidarUsuarioCallback {
        void onValidado(String mensaje, Usuario usuario);

        void onError(IErrorBundle errorBundle);
    }
}
