package com.pe.af.android.data.repository.datasource.local;

import com.pe.af.android.data.dao.UsuarioDao;

import java.util.List;

public class UsuarioLocalStore {

    public void guardarUsuario(UsuarioDao usuarioDao){
        usuarioDao.save();
    }

    public List<UsuarioDao> listarUsuario(){
        return UsuarioDao.listAll(UsuarioDao.class);
    }

    public void eliminarUsuario(){
        UsuarioDao.deleteAll(UsuarioDao.class);
    }
}
