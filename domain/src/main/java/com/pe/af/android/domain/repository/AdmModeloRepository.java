package com.pe.af.android.domain.repository;

import com.pe.af.android.domain.entity.AdmModelo;
import com.pe.af.android.domain.entity.request.AdmModeloRequest;
import com.pe.af.android.domain.entity.response.AdmModeloResponse;
import com.pe.af.android.domain.exception.IErrorBundle;

public interface AdmModeloRepository {

    void guardarAdmModelo(AdmModelo admModelo);

    AdmModelo obtenerAdmModelo();

    void eliminarAdmModelo();

    void obtenerAdmModelo(String usuario, ObtenerAdmModeloCallback callback);

    void guardarAdmModelo(String usuario, AdmModeloRequest request, GuardarAdmModeloCallback callback);

    interface ObtenerAdmModeloCallback {
        void onEnviado(String mensaje, AdmModeloResponse item);

        void onError(IErrorBundle errorBundle);
    }

    interface GuardarAdmModeloCallback {
        void onEnviado(String mensaje);

        void onError(IErrorBundle errorBundle);
    }
}
