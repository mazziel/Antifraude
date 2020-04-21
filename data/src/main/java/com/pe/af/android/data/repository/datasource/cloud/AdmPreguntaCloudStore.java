package com.pe.af.android.data.repository.datasource.cloud;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.pe.af.android.data.exception.NetworkConnectionException;
import com.pe.af.android.data.net.Url;
import com.pe.af.android.data.net.message.Response;
import com.pe.af.android.data.util.UtilitarioData;
import com.pe.af.android.domain.entity.AdmPregunta;
import com.pe.af.android.domain.entity.request.AdmPreguntaRequest;
import com.pe.af.android.domain.entity.response.AdmPreguntaResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class AdmPreguntaCloudStore {
    final Context context;
    final Retrofit retrofit;

    public AdmPreguntaCloudStore(Context context) {
        this.context = context;
        retrofit = new Retrofit.Builder()
                .baseUrl(Url.obtenerBase(context))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void guardarAdmPregunta(String usuario, AdmPreguntaRequest request, final GuardarAdmPreguntaCallback callback) {

        if (UtilitarioData.isThereInternetConnection(context)) {

            GuardarAdmPreguntaRetroService service = retrofit.create(GuardarAdmPreguntaRetroService.class);

            Call<Response<String>> repos = service.guardarAdmPregunta(usuario, request);

            repos.enqueue(new Callback<Response<String>>() {
                @Override
                public void onResponse(Call<Response<String>> call, retrofit2.Response<Response<String>> response) {
                    if (response.code() == 200 && response.message() != null) {
                        Response<String> respuesta;
                        try {
                            respuesta = response.body();
                            Log.v("RESPONSE", new Gson().toJson(respuesta));
                            if (respuesta.getCode() == Response.CODE_OK) {
                                callback.onEnviado(respuesta.getMessage());
                            } else if (respuesta.getCode() == Response.CODE_ERROR) {
                                callback.onError(new Exception(respuesta.getMessage()));
                            }
                        } catch (Exception e) {
                            callback.onError(e);
                        }
                    } else {
                        callback.onError(new Exception(response.message()));
                    }
                }

                @Override
                public void onFailure(Call<Response<String>> call, Throwable t) {
                    callback.onError(new Exception(t.getMessage()));
                }
            });
        } else {
            callback.onError(new NetworkConnectionException("Fuera de cobertura"));
        }
    }

    public void obtenerAdmPregunta(String usuario, final ObtenerAdmPreguntaCallback callback) {

        if (UtilitarioData.isThereInternetConnection(context)) {

            OrdenCompraRetroService service = retrofit.create(OrdenCompraRetroService.class);

            Call<Response<AdmPreguntaResponse>> repos = service.obtenerAdmPregunta(usuario);

            repos.enqueue(new Callback<Response<AdmPreguntaResponse>>() {
                @Override
                public void onResponse(Call<Response<AdmPreguntaResponse>> call, retrofit2.Response<Response<AdmPreguntaResponse>> response) {
                    if (response.code() == 200 && response.message() != null) {
                        Response<AdmPreguntaResponse> respuesta;
                        try {
                            respuesta = response.body();
                            Log.v("RESPONSE", new Gson().toJson(respuesta));
                            if (respuesta.getCode() == Response.CODE_OK) {
                                callback.onEnviado(respuesta.getMessage(), respuesta.getPayLoad().getListaPreguntas());
                            } else if (respuesta.getCode() == Response.CODE_ERROR) {
                                callback.onError(new Exception(respuesta.getMessage()));
                            }
                        } catch (Exception e) {
                            callback.onError(e);
                        }
                    } else {
                        callback.onError(new Exception(response.message()));
                    }
                }

                @Override
                public void onFailure(Call<Response<AdmPreguntaResponse>> call, Throwable t) {
                    callback.onError(new Exception(t.getMessage()));
                }
            });
        } else {
            callback.onError(new NetworkConnectionException("Fuera de cobertura"));
        }
    }

    public interface GuardarAdmPreguntaRetroService {
        @Headers({
                "Content-Type: application/json",
                "Accept: application/json"
        })
        @POST("preguntas")
        Call<Response<String>> guardarAdmPregunta(@Header("Authorization") String usuario,
                                                @Body AdmPreguntaRequest request);
    }

    public interface OrdenCompraRetroService {
        @Headers({
                "Content-Type: application/json",
                "Accept: application/json"
        })
        @GET("preguntas")
        Call<Response<AdmPreguntaResponse>> obtenerAdmPregunta(@Header("Authorization") String usuario);
    }

    public interface GuardarAdmPreguntaCallback {
        void onEnviado(String mensaje);

        void onError(Exception e);
    }

    public interface ObtenerAdmPreguntaCallback {
        void onEnviado(String mensaje, List<AdmPregunta> list);

        void onError(Exception e);
    }

}
