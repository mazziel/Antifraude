package com.pe.af.android.domain.usecase;


import com.pe.af.android.domain.entity.Usuario;
import com.pe.af.android.domain.entity.request.UsuarioRequest;
import com.pe.af.android.domain.exception.ErrorBundle;
import com.pe.af.android.domain.exception.IErrorBundle;
import com.pe.af.android.domain.repository.UsuarioRepository;

public class UsuarioUseCase implements IUsuarioUseCase {

    final UsuarioRepository usuarioRepository;

    public UsuarioUseCase(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Usuario obtenerUsuario() {
        return usuarioRepository.obtenerUsuario();
    }

    @Override
    public void cerrarSesion() {
        usuarioRepository.eliminarUsuario();
    }

    @Override
    public void ejecutar(final UsuarioRequest usuarioRequest, final Callback callback) {

        if(usuarioRequest.getUsuario().equals("")||usuarioRequest.getContrasena().equals("")){
            Exception exception = new Exception("Ingresa usuario y clave");
            IErrorBundle errorBundle = new ErrorBundle(exception);
            callback.onError(errorBundle);
        }else{
            usuarioRepository.validarUsuario(usuarioRequest, new UsuarioRepository.ValidarUsuarioCallback() {
                @Override
                public void onValidado(String mensaje, Usuario usuario) {
                    usuarioRepository.eliminarUsuario();
                    /*usuario.setNombre(usuarioRequest.getUsuario());
                    usuario.setClave(usuarioRequest.getContrasena());*/
                    usuarioRepository.guardarUsuario(usuario);
                    callback.onValidado(mensaje);
                }

                @Override
                public void onError(IErrorBundle errorBundle) {
                    callback.onError(errorBundle);
                }
            });


        }

    }
}
