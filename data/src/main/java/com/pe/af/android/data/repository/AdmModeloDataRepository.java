package com.pe.af.android.data.repository;

import android.content.Context;

import com.pe.af.android.data.dao.AdmModeloDao;
import com.pe.af.android.data.exception.RepositoryErrorBundle;
import com.pe.af.android.data.repository.datasource.cloud.AdmModeloCloudStore;
import com.pe.af.android.data.repository.datasource.local.AdmModeloLocalStore;
import com.pe.af.android.domain.entity.AdmModelo;
import com.pe.af.android.domain.entity.request.AdmModeloRequest;
import com.pe.af.android.domain.entity.response.AdmModeloResponse;
import com.pe.af.android.domain.repository.AdmModeloRepository;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class AdmModeloDataRepository implements AdmModeloRepository {

    final Context context;

    public AdmModeloDataRepository(Context context) {
        this.context = context;
    }

    @Override
    public void guardarAdmModelo(AdmModelo admModelo) {
        AdmModeloLocalStore admModeloLocalStore = new AdmModeloLocalStore();
        ModelMapper modelMapper = new ModelMapper();
        admModeloLocalStore.guardarAdmModelo(modelMapper.map(admModelo, AdmModeloDao.class));
    }

    @Override
    public AdmModelo obtenerAdmModelo() {
        AdmModelo admModelo;
        AdmModeloLocalStore admModeloLocalStore = new AdmModeloLocalStore();
        ModelMapper modelMapper = new ModelMapper();

        Type type = new TypeToken<List<AdmModelo>>() {
        }.getType();

        try {
            List<AdmModelo> list = modelMapper.map(admModeloLocalStore.listarAdmModelo(), type);
            if (list.size()>0) {
                admModelo = list.get(0);
                return admModelo;
            }
            admModelo = null;
        } catch (Exception e) {
            admModelo = null;
        }
        return admModelo;
    }

    @Override
    public void eliminarAdmModelo() {
        AdmModeloLocalStore store = new AdmModeloLocalStore();
        store.eliminarAdmModelo();
    }

    @Override
    public void guardarAdmModelo(final String usuario, final AdmModeloRequest request, final GuardarAdmModeloCallback callback) {

        AdmModeloCloudStore admModeloCloudStore = new AdmModeloCloudStore(context);

        admModeloCloudStore.guardarAdmModelo(usuario, request, new AdmModeloCloudStore.GuardarAdmModeloCallback() {
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
    public void obtenerAdmModelo(String usuario, final ObtenerAdmModeloCallback callback) {

        AdmModeloCloudStore admModeloCloudStore = new AdmModeloCloudStore(context);

        admModeloCloudStore.obtenerAdmModelo(usuario, new AdmModeloCloudStore.ObtenerAdmModeloCallback() {
            @Override
            public void onEnviado(String mensaje, AdmModeloResponse item ) {
                callback.onEnviado(mensaje, item);
            }

            @Override
            public void onError(Exception e) {
                callback.onError(new RepositoryErrorBundle(e));
            }
        });
    }
}
