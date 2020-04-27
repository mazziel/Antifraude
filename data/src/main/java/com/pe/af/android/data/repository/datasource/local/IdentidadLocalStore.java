package com.pe.af.android.data.repository.datasource.local;

import com.pe.af.android.data.dao.IdentidadDao;

import java.util.List;

public class IdentidadLocalStore {

    public void guardarIdentidad(IdentidadDao identidadDao){
        identidadDao.save();
    }

    public List<IdentidadDao> listarIdentidad(){
        return IdentidadDao.listAll(IdentidadDao.class);
    }

    public void eliminarIdentidad(){
        IdentidadDao.deleteAll(IdentidadDao.class);
    }
}
