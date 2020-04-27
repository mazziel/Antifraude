package com.pe.af.android.domain.usecase;


import com.pe.af.android.domain.entity.Identidad;
import com.pe.af.android.domain.entity.Pregunta;
import com.pe.af.android.domain.entity.request.IdentidadRequest;
import com.pe.af.android.domain.exception.ErrorBundle;
import com.pe.af.android.domain.exception.IErrorBundle;
import com.pe.af.android.domain.repository.IdentidadRepository;

import java.util.List;

public class IdentidadUseCase implements IIdentidadUseCase {

    final IdentidadRepository identidadRepository;

    public IdentidadUseCase(IdentidadRepository identidadRepository) {
        this.identidadRepository = identidadRepository;
    }

    @Override
    public Identidad obtenerIdentidad() {
        return identidadRepository.obtenerIdentidad();
    }

    @Override
    public List<Pregunta> obtenerListPregunta() {
        return identidadRepository.obtenerListPregunta();
    }

    @Override
    public void ejecutar(String usuario, final IdentidadRequest identidadRequest, final Callback callback) {

        if(identidadRequest.getTipoDocumento().equals("")||identidadRequest.getNroDocumento().equals("")){
            Exception exception = new Exception("Ingresa tipo de documento y número de documento");
            IErrorBundle errorBundle = new ErrorBundle(exception);
            callback.onError(errorBundle);
        }else{
            identidadRepository.validarIdentidad(usuario, identidadRequest, new IdentidadRepository.ValidarIdentidadCallback() {
                @Override
                public void onValidado(String mensaje, Identidad identidad,  List<Pregunta> list) {
                    identidadRepository.eliminarIdentidad();
                    identidadRepository.eliminarListPregunta();
                    identidad.setNroDocumento(identidadRequest.getNroDocumento());
                    identidadRepository.guardarIdentidad(identidad);
                    identidadRepository.guardarListPregunta(list);
                    callback.onValidado(mensaje);
                }

                @Override
                public void onError(IErrorBundle errorBundle) {
                    callback.onError(errorBundle);
                }
            });


        }

    }
}
