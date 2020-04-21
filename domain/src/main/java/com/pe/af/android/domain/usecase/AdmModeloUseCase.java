package com.pe.af.android.domain.usecase;

import com.pe.af.android.domain.entity.AdmModelo;
import com.pe.af.android.domain.entity.request.AdmModeloRequest;
import com.pe.af.android.domain.entity.response.AdmModeloResponse;
import com.pe.af.android.domain.exception.IErrorBundle;
import com.pe.af.android.domain.repository.AdmModeloRepository;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;

public class AdmModeloUseCase implements IAdmModeloUseCase {

    final AdmModeloRepository admModeloRepository;

    public AdmModeloUseCase(AdmModeloRepository admModeloRepository) {
        this.admModeloRepository = admModeloRepository;
    }

    @Override
    public void guardarAdmModelo(AdmModelo admModelo) {
        admModeloRepository.guardarAdmModelo(admModelo);
    }

    @Override
    public AdmModelo obtenerAdmModelo() {
        return admModeloRepository.obtenerAdmModelo();
    }

    @Override
    public void eliminarAdmModelo() {
        admModeloRepository.eliminarAdmModelo();
    }

    @Override
    public void obtenerAdmModelo(String usuario, final Callback callback) {
        admModeloRepository.obtenerAdmModelo(usuario, new AdmModeloRepository.ObtenerAdmModeloCallback() {
            @Override
            public void onEnviado(String mensaje, AdmModeloResponse item) {
                admModeloRepository.eliminarAdmModelo();
                ModelMapper modelMapper = new ModelMapper();
                Type type = new TypeToken<AdmModelo>() {
                }.getType();
                AdmModelo admModelo = modelMapper.map(item, type);
                admModeloRepository.guardarAdmModelo(admModelo);
                callback.onEnviar(mensaje);
            }

            @Override
            public void onError(IErrorBundle errorBundle) {
                callback.onError(errorBundle);
            }
        });
    }

    @Override
    public void guardarAdmModelo(String usuario, final AdmModeloRequest request, final Callback callback) {
        admModeloRepository.guardarAdmModelo(usuario, request, new AdmModeloRepository.GuardarAdmModeloCallback() {
            @Override
            public void onEnviado(String mensaje) {
                ModelMapper modelMapper = new ModelMapper();
                Type type = new TypeToken<AdmModelo>() {
                }.getType();
                AdmModelo admModelo = modelMapper.map(request, type);
                admModeloRepository.guardarAdmModelo(admModelo);
                callback.onEnviar(mensaje);
            }

            @Override
            public void onError(IErrorBundle errorBundle) {
                callback.onError(errorBundle);
            }
        });
    }
}
