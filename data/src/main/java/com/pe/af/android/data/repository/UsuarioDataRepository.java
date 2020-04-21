package com.pe.af.android.data.repository;

import android.content.Context;

import com.pe.af.android.data.dao.UsuarioDao;
import com.pe.af.android.data.exception.RepositoryErrorBundle;
import com.pe.af.android.data.repository.datasource.cloud.UsuarioCloudStore;
import com.pe.af.android.data.repository.datasource.local.UsuarioLocalStore;
import com.pe.af.android.domain.entity.Usuario;
import com.pe.af.android.domain.entity.request.UsuarioRequest;
import com.pe.af.android.domain.repository.UsuarioRepository;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class UsuarioDataRepository implements UsuarioRepository {

    final Context context;

    public UsuarioDataRepository(Context context) {
        this.context = context;
    }

    @Override
    public void validarUsuario(UsuarioRequest usuarioRequest, final UsuarioRepository.ValidarUsuarioCallback validarUsuarioCallback) {

        UsuarioCloudStore usuarioCloudStore = new UsuarioCloudStore(context);
        usuarioCloudStore.validarUsuario(usuarioRequest, new UsuarioCloudStore.ValidarUsuarioCallback() {
            @Override
            public void onValidar(String mensaje, Usuario usuario) {
                validarUsuarioCallback.onValidado(mensaje, usuario);
            }

            @Override
            public void onError(Exception e) {
                validarUsuarioCallback.onError(new RepositoryErrorBundle(e));
            }
        });

    }

    @Override
    public void guardarUsuario(Usuario usuario) {
        UsuarioLocalStore usuarioLocalStore = new UsuarioLocalStore();
        ModelMapper modelMapper = new ModelMapper();
        usuarioLocalStore.guardarUsuario(modelMapper.map(usuario, UsuarioDao.class));
    }

    @Override
    public Usuario obtenerUsuario() {
        Usuario usuario;
        UsuarioLocalStore usuarioLocalStore = new UsuarioLocalStore();
        ModelMapper modelMapper = new ModelMapper();

        Type type = new TypeToken<List<Usuario>>() {
        }.getType();

        try {
            List<Usuario> list = modelMapper.map(usuarioLocalStore.listarUsuario(), type);
            if (list.size()>0) {
                usuario = list.get(0);
                return usuario;
            }
            usuario = null;
        } catch (Exception e) {
            usuario = null;
        }
        return usuario;
    }

    @Override
    public void eliminarUsuario() {
        UsuarioLocalStore usuarioLocalStore = new UsuarioLocalStore();
        usuarioLocalStore.eliminarUsuario();
    }

}
