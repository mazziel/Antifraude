package com.pe.af.android.data.repository;

import android.content.Context;

import com.pe.af.android.data.dao.AdmPreguntaDao;
import com.pe.af.android.data.exception.RepositoryErrorBundle;
import com.pe.af.android.data.repository.datasource.cloud.AdmPreguntaCloudStore;
import com.pe.af.android.data.repository.datasource.local.AdmPreguntaLocalStore;
import com.pe.af.android.domain.entity.AdmPregunta;
import com.pe.af.android.domain.entity.request.AdmPreguntaRequest;
import com.pe.af.android.domain.entity.response.AdmPreguntaResponse;
import com.pe.af.android.domain.repository.AdmPreguntaRepository;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AdmPreguntaDataRepository implements AdmPreguntaRepository {

    final Context context;

    public AdmPreguntaDataRepository(Context context) {
        this.context = context;
    }

    @Override
    public void guardarAdmPregunta(AdmPregunta admPregunta) {
        AdmPreguntaLocalStore admPreguntaLocalStore = new AdmPreguntaLocalStore();
        ModelMapper modelMapper = new ModelMapper();
        admPreguntaLocalStore.guardarAdmPregunta(modelMapper.map(admPregunta, AdmPreguntaDao.class));
    }

    @Override
    public List<AdmPregunta> obtenerListAdmPregunta() {
        List<AdmPregunta> list;
        AdmPreguntaLocalStore admPreguntaLocalStore = new AdmPreguntaLocalStore();
        ModelMapper modelMapper = new ModelMapper();

        Type type = new TypeToken<List<AdmPregunta>>() {
        }.getType();

        try {
            list = modelMapper.map(admPreguntaLocalStore.listarAdmPregunta(), type);
        } catch (Exception e) {
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    public void eliminarAdmPregunta() {
        AdmPreguntaLocalStore store = new AdmPreguntaLocalStore();
        store.eliminarAdmPregunta();
    }

    @Override
    public void guardarAdmPregunta(final String usuario, final AdmPreguntaRequest request, final GuardarAdmPreguntaCallback callback) {
        
        AdmPreguntaCloudStore admPreguntaCloudStore = new AdmPreguntaCloudStore(context);

        admPreguntaCloudStore.guardarAdmPregunta(usuario, request, new AdmPreguntaCloudStore.GuardarAdmPreguntaCallback() {
            @Override
            public void onEnviado(String mensaje) {
                callback.onEnviado(mensaje);
            }

            @Override
            public void onError(Exception e) {
                callback.onError(new RepositoryErrorBundle(e));
            }
        });
    }

    @Override
    public void obtenerAdmPregunta(String usuario, final ObtenerAdmPreguntaCallback callback) {

        AdmPreguntaCloudStore admPreguntaCloudStore = new AdmPreguntaCloudStore(context);

        admPreguntaCloudStore.obtenerAdmPregunta(usuario, new AdmPreguntaCloudStore.ObtenerAdmPreguntaCallback() {
            @Override
            public void onEnviado(String mensaje, List<AdmPregunta> list ) {
                callback.onEnviado(mensaje, list);
            }

            @Override
            public void onError(Exception e) {
                callback.onError(new RepositoryErrorBundle(e));
            }
        });
    }
}
