package com.pe.af.android.domain.usecase;

import com.pe.af.android.domain.entity.Identidad;
import com.pe.af.android.domain.entity.Pregunta;
import com.pe.af.android.domain.entity.request.IdentidadRequest;
import com.pe.af.android.domain.exception.IErrorBundle;

import java.util.List;

public interface IIdentidadUseCase {

    Identidad obtenerIdentidad();

    List<Pregunta> obtenerListPregunta();

    void ejecutar(String usuario, IdentidadRequest identidadRequest, Callback callback);

    interface Callback {
        void onValidado(String mensaje);

        void onError(IErrorBundle errorBundle);
    }

}
