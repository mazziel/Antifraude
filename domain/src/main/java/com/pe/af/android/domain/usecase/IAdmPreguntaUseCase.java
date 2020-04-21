package com.pe.af.android.domain.usecase;

import com.pe.af.android.domain.entity.AdmPregunta;
import com.pe.af.android.domain.entity.request.AdmPreguntaRequest;
import com.pe.af.android.domain.exception.IErrorBundle;

import java.util.List;

public interface IAdmPreguntaUseCase {

    void guardarAdmPregunta(AdmPregunta admPregunta);

    List<AdmPregunta> obtenerListAdmPregunta();

    void eliminarAdmPregunta();

    void obtenerAdmPregunta(String usuario, Callback callback);

    void guardarAdmPregunta(String usuario, AdmPreguntaRequest request, Callback callback);

    interface Callback {
        void onEnviar(String mensaje);

        void onError(IErrorBundle errorBundle);
    }
}
