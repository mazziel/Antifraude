package com.pe.af.android.domain.repository;


import com.pe.af.android.domain.entity.Identidad;
import com.pe.af.android.domain.entity.Pregunta;
import com.pe.af.android.domain.entity.request.IdentidadRequest;
import com.pe.af.android.domain.exception.IErrorBundle;

import java.util.List;

public interface IdentidadRepository {

    void guardarIdentidad(Identidad identidad);

    void guardarListPregunta(List<Pregunta> list);

    Identidad obtenerIdentidad();

    List<Pregunta> obtenerListPregunta();

    void eliminarIdentidad();

    void eliminarListPregunta();

    void validarIdentidad(String usuario, IdentidadRequest identidadRequest, ValidarIdentidadCallback validarIdentidadCallback);

    interface ValidarIdentidadCallback {
        void onValidado(String mensaje, Identidad identidad, List<Pregunta> list);

        void onError(IErrorBundle errorBundle);
    }
}
