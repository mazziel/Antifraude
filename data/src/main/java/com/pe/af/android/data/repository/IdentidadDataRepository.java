package com.pe.af.android.data.repository;

import android.content.Context;

import com.google.gson.Gson;
import com.pe.af.android.data.dao.IdentidadDao;
import com.pe.af.android.data.exception.RepositoryErrorBundle;
import com.pe.af.android.data.repository.datasource.cloud.IdentidadCloudStore;
import com.pe.af.android.data.repository.datasource.generic.PreferenceStore;
import com.pe.af.android.data.repository.datasource.local.IdentidadLocalStore;
import com.pe.af.android.domain.entity.Identidad;
import com.pe.af.android.domain.entity.Pregunta;
import com.pe.af.android.domain.entity.request.IdentidadRequest;
import com.pe.af.android.domain.repository.IdentidadRepository;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class IdentidadDataRepository implements IdentidadRepository {

    final Context context;

    public IdentidadDataRepository(Context context) {
        this.context = context;
    }

    @Override
    public void validarIdentidad(String usuario, IdentidadRequest identidadRequest, final ValidarIdentidadCallback validarIdentidadCallback) {

        IdentidadCloudStore identidadCloudStore = new IdentidadCloudStore(context);
        identidadCloudStore.validarIdentidad(usuario, identidadRequest, new IdentidadCloudStore.ValidarIdentidadCallback() {
            @Override
            public void onValidar(String mensaje, Identidad identidad, List<Pregunta> list) {
                validarIdentidadCallback.onValidado(mensaje, identidad, list);
            }

            @Override
            public void onError(Exception e) {
                validarIdentidadCallback.onError(new RepositoryErrorBundle(e));
            }
        });

    }

    @Override
    public void guardarIdentidad(Identidad identidad) {
        IdentidadLocalStore identidadLocalStore = new IdentidadLocalStore();
        ModelMapper modelMapper = new ModelMapper();
        identidadLocalStore.guardarIdentidad(modelMapper.map(identidad, IdentidadDao.class));
    }

    @Override
    public void guardarListPregunta(List<Pregunta> list) {
        PreferenceStore<List<Pregunta>> preferenceStore = new PreferenceStore<>(context);
        preferenceStore.saveOnSharePreferencesObject("pregunta", list);
    }

    @Override
    public Identidad obtenerIdentidad() {
        Identidad identidad;
        IdentidadLocalStore identidadLocalStore = new IdentidadLocalStore();
        ModelMapper modelMapper = new ModelMapper();

        Type type = new TypeToken<List<Identidad>>() {
        }.getType();

        try {
            List<Identidad> list = modelMapper.map(identidadLocalStore.listarIdentidad(), type);
            if (list.size() > 0) {
                identidad = list.get(0);
                return identidad;
            }
            identidad = null;
        } catch (Exception e) {
            identidad = null;
        }
        return identidad;
    }

    @Override
    public List<Pregunta> obtenerListPregunta() {
        List<Pregunta> preguntaList;
        PreferenceStore<List<Pregunta>> preferenceStore = new PreferenceStore<>(context);

        //convert to string using gson
        Gson gson = new Gson();

        java.lang.reflect.Type type = new TypeToken<List<Pregunta>>() {
        }.getType();

        try {
            String stored = preferenceStore.getPreferenceObject("pregunta");
            preguntaList = gson.fromJson(stored, type);
        } catch (Exception e) {
            preguntaList = null;
        }
        return preguntaList;
    }

    @Override
    public void eliminarIdentidad() {
        IdentidadLocalStore identidadLocalStore = new IdentidadLocalStore();
        identidadLocalStore.eliminarIdentidad();
    }

    @Override
    public void eliminarListPregunta() {
        PreferenceStore<List<Pregunta>> preferenceStore = new PreferenceStore<>(context);
        preferenceStore.removePreferenceObject("pregunta");
    }

}
