package com.pe.af.android.domain.usecase;

import com.pe.af.android.domain.entity.AdmModelo;
import com.pe.af.android.domain.entity.request.AdmModeloRequest;
import com.pe.af.android.domain.exception.IErrorBundle;

public interface IAdmModeloUseCase {

    void guardarAdmModelo(AdmModelo admModelo);

    AdmModelo obtenerAdmModelo();

    void eliminarAdmModelo();

    void obtenerAdmModelo(String usuario, Callback callback);

    void guardarAdmModelo(String usuario, AdmModeloRequest request, Callback callback);

    interface Callback {
        void onEnviar(String mensaje);

        void onError(IErrorBundle errorBundle);
    }

}
