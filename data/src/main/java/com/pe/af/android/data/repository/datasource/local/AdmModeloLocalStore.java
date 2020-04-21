package com.pe.af.android.data.repository.datasource.local;


import com.pe.af.android.data.dao.AdmModeloDao;

import java.util.List;

public class AdmModeloLocalStore {

    public void guardarAdmModelo(AdmModeloDao admModeloDao){
        admModeloDao.save();
    }

    public List<AdmModeloDao> listarAdmModelo(){
        return AdmModeloDao.listAll(AdmModeloDao.class);
    }

    public void eliminarAdmModelo(){
        AdmModeloDao.deleteAll(AdmModeloDao.class);
    }

}
