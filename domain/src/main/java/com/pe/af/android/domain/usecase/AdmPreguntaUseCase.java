package com.pe.af.android.domain.usecase;

import com.pe.af.android.domain.entity.AdmPregunta;
import com.pe.af.android.domain.entity.request.AdmPreguntaRequest;
import com.pe.af.android.domain.entity.response.AdmPreguntaResponse;
import com.pe.af.android.domain.exception.IErrorBundle;
import com.pe.af.android.domain.repository.AdmPreguntaRepository;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class AdmPreguntaUseCase implements IAdmPreguntaUseCase {

    final AdmPreguntaRepository admPreguntaRepository;

    public AdmPreguntaUseCase(AdmPreguntaRepository admPreguntaRepository) {
        this.admPreguntaRepository = admPreguntaRepository;
    }

    @Override
    public void guardarAdmPregunta(AdmPregunta admPregunta) {
        admPreguntaRepository.guardarAdmPregunta(admPregunta);
    }

    @Override
    public List<AdmPregunta> obtenerListAdmPregunta() {
        return admPreguntaRepository.obtenerListAdmPregunta();
    }

    @Override
    public void eliminarAdmPregunta() {
        admPreguntaRepository.eliminarAdmPregunta();
    }

    @Override
    public void obtenerAdmPregunta(String usuario, final Callback callback) {
        admPreguntaRepository.obtenerAdmPregunta(usuario, new AdmPreguntaRepository.ObtenerAdmPreguntaCallback() {
            @Override
            public void onEnviado(String mensaje, List<AdmPregunta> list) {
                admPreguntaRepository.eliminarAdmPregunta();
                for (AdmPregunta admPregunta:list) {
                    admPreguntaRepository.guardarAdmPregunta(admPregunta);
                }
                callback.onEnviar(mensaje);
            }

            @Override
            public void onError(IErrorBundle errorBundle) {
                callback.onError(errorBundle);
            }
        });
    }

    @Override
    public void guardarAdmPregunta(String usuario, final AdmPreguntaRequest request, final Callback callback) {
        admPreguntaRepository.guardarAdmPregunta(usuario, request, new AdmPreguntaRepository.GuardarAdmPreguntaCallback() {
            @Override
            public void onEnviado(String mensaje) {
                List<AdmPregunta> list = obtenerListAdmPregunta();
                admPreguntaRepository.eliminarAdmPregunta();
                for (AdmPregunta itemm: list) {
                    itemm.setSeleccionada(false);
                    for (int id: request.getPreguntasSeleccionadas()) {
                        if (itemm.getNroPregunta() == id){
                            itemm.setSeleccionada(true);
                            continue;
                        }
                    }
                    admPreguntaRepository.guardarAdmPregunta(itemm);
                }
                callback.onEnviar(mensaje);
            }

            @Override
            public void onError(IErrorBundle errorBundle) {
                callback.onError(errorBundle);
            }
        });
    }
}
