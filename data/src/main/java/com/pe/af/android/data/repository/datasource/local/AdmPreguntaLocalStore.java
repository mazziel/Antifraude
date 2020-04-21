package com.pe.af.android.data.repository.datasource.local;


import com.pe.af.android.data.dao.AdmPreguntaDao;

import java.util.List;

public class AdmPreguntaLocalStore {

    public void guardarAdmPregunta(AdmPreguntaDao admPreguntaDao){
        admPreguntaDao.save();
    }

    public List<AdmPreguntaDao> listarAdmPregunta(){
        return AdmPreguntaDao.listAll(AdmPreguntaDao.class);
    }

    public void eliminarAdmPregunta(){
        AdmPreguntaDao.deleteAll(AdmPreguntaDao.class);
    }

}
