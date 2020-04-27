package com.pe.af.android.data.repository.datasource.cloud;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.pe.af.android.data.exception.NetworkConnectionException;
import com.pe.af.android.data.net.Url;
import com.pe.af.android.data.net.message.Response;
import com.pe.af.android.data.util.UtilitarioData;
import com.pe.af.android.domain.entity.Identidad;
import com.pe.af.android.domain.entity.Pregunta;
import com.pe.af.android.domain.entity.request.IdentidadRequest;
import com.pe.af.android.domain.entity.response.IdentidadResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public class IdentidadCloudStore {

    final Context context;
    final Retrofit retrofit;

    public IdentidadCloudStore(Context context) {
        this.context = context;
        retrofit = new Retrofit.Builder()
                .baseUrl(Url.obtenerBase(context))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void validarIdentidad(String usuario, IdentidadRequest identidadRequest, final ValidarIdentidadCallback validarIdentidadCallback) {
        if (UtilitarioData.isThereInternetConnection(context)) {
            IdentidadRetroService service = retrofit.create(IdentidadRetroService.class);

            Call<Response<IdentidadResponse>> repos = service.validarIdentidad(usuario, identidadRequest);
            Log.v("URL Obtenida", repos.request().url().toString());

            repos.enqueue(new Callback<Response<IdentidadResponse>>() {
                @Override
                public void onResponse(Call<Response<IdentidadResponse>> call, retrofit2.Response<Response<IdentidadResponse>> response) {

                    if (response.code() == 200 && response.message() != null) {

                        Response<IdentidadResponse> respuesta;
                        try {
                            respuesta = response.body();
                            Log.v("RESPONSE", new Gson().toJson(respuesta));

                            if (respuesta.getCode() == Response.CODE_OK) {
                                Identidad identidad = new Identidad();
                                identidad.setPersona(respuesta.getPayLoad().getPersona());
                                identidad.setTransaccion(respuesta.getPayLoad().getTransaccion());
                                identidad.setEstado(respuesta.getPayLoad().getEstado());
                                validarIdentidadCallback.onValidar(respuesta.getMessage(), identidad, respuesta.getPayLoad().getListaPreguntas());
                            } else if (respuesta.getCode() == Response.CODE_ERROR) {
                                validarIdentidadCallback.onError(new Exception(respuesta.getMessage()));
                            }

                        } catch (Exception e) {
                            validarIdentidadCallback.onError(e);
                        }

                    } else {
                        validarIdentidadCallback.onError(new Exception(response.message()));
                    }
                }

                @Override
                public void onFailure(Call<Response<IdentidadResponse>> call, Throwable t) {
                    validarIdentidadCallback.onError(new Exception(t.getMessage()));
                }
            });
        } else {
            validarIdentidadCallback.onError(new NetworkConnectionException("Fuera de cobertura"));
        }
    }

    public interface IdentidadRetroService {
        @POST("identidad/persona")
        Call<Response<IdentidadResponse>> validarIdentidad(@Header("username") String usuario, @Body IdentidadRequest identidadRequest);
    }

    public interface ValidarIdentidadCallback {
        void onValidar(String mensaje, Identidad identidad, List<Pregunta> list);

        void onError(Exception e);


    }
}
