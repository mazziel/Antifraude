package com.pe.af.android.data.repository.datasource.cloud;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.pe.af.android.data.exception.NetworkConnectionException;
import com.pe.af.android.data.net.Url;
import com.pe.af.android.data.net.message.Response;
import com.pe.af.android.data.util.UnsafeOkHttpClient;
import com.pe.af.android.data.util.UtilitarioData;
import com.pe.af.android.domain.entity.request.AdmModeloRequest;
import com.pe.af.android.domain.entity.response.AdmModeloResponse;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public class AdmModeloCloudStore {
    final Context context;
    final OkHttpClient okHttpClient;
    final Retrofit retrofit;

    public AdmModeloCloudStore(Context context) {
        this.context = context;
        okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();
        retrofit = new Retrofit.Builder()
                .baseUrl(Url.obtenerBase(context))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void guardarAdmModelo(String usuario, AdmModeloRequest request, final GuardarAdmModeloCallback callback) {

        if (UtilitarioData.isThereInternetConnection(context)) {

            GuardarAdmModeloRetroService service = retrofit.create(GuardarAdmModeloRetroService.class);

            Call<Response<String>> repos = service.guardarAdmModelo(usuario, request);

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

    public void obtenerAdmModelo(String usuario, final ObtenerAdmModeloCallback callback) {

        if (UtilitarioData.isThereInternetConnection(context)) {

            ObtenerAdmModeloRetroService service = retrofit.create(ObtenerAdmModeloRetroService.class);

            Call<Response<AdmModeloResponse>> repos = service.obtenerAdmModelo(usuario);

            repos.enqueue(new Callback<Response<AdmModeloResponse>>() {
                @Override
                public void onResponse(Call<Response<AdmModeloResponse>> call, retrofit2.Response<Response<AdmModeloResponse>> response) {
                    if (response.code() == 200 && response.message() != null) {
                        Response<AdmModeloResponse> respuesta;
                        try {
                            respuesta = response.body();
                            Log.v("RESPONSE", new Gson().toJson(respuesta));
                            if (respuesta.getCode() == Response.CODE_OK) {
                                callback.onEnviado(respuesta.getMessage(), respuesta.getPayLoad());
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
                public void onFailure(Call<Response<AdmModeloResponse>> call, Throwable t) {
                    callback.onError(new Exception(t.getMessage()));
                }
            });
        } else {
            callback.onError(new NetworkConnectionException("Fuera de cobertura"));
        }
    }

    public interface GuardarAdmModeloRetroService {
        @POST("administracion/modelo")
        Call<Response<String>> guardarAdmModelo(@Header("username") String usuario,
                                                @Body AdmModeloRequest request);
    }

    public interface ObtenerAdmModeloRetroService {
        @GET("administracion/modelo")
        Call<Response<AdmModeloResponse>> obtenerAdmModelo(@Header("username") String usuario);
    }

    public interface GuardarAdmModeloCallback {
        void onEnviado(String mensaje);

        void onError(Exception e);
    }

    public interface ObtenerAdmModeloCallback {
        void onEnviado(String mensaje, AdmModeloResponse item);

        void onError(Exception e);
    }

}
