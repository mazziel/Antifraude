package com.pe.af.android.domain.repository;

import com.pe.af.android.domain.entity.AdmPregunta;
import com.pe.af.android.domain.entity.request.AdmPreguntaRequest;
import com.pe.af.android.domain.exception.IErrorBundle;

import java.util.List;

public interface AdmPreguntaRepository {

    void guardarAdmPregunta(AdmPregunta admPregunta);

    List<AdmPregunta> obtenerListAdmPregunta();

    void eliminarAdmPregunta();

    void obtenerAdmPregunta(String usuario, ObtenerAdmPreguntaCallback callback);

    void guardarAdmPregunta(String usuario, AdmPreguntaRequest request, GuardarAdmPreguntaCallback callback);

    interface ObtenerAdmPreguntaCallback {
        void onEnviado(String mensaje, List<AdmPregunta> list);

        void onError(IErrorBundle errorBundle);
    }

    interface GuardarAdmPreguntaCallback {
        void onEnviado(String mensaje);

        void onError(IErrorBundle errorBundle);
    }
}
