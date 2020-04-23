package com.pe.af.android.data.repository.datasource.cloud;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.pe.af.android.data.exception.NetworkConnectionException;
import com.pe.af.android.data.net.Url;
import com.pe.af.android.data.net.message.Response;
import com.pe.af.android.data.util.UtilitarioData;
import com.pe.af.android.domain.entity.Usuario;
import com.pe.af.android.domain.entity.request.UsuarioRequest;
import com.pe.af.android.domain.entity.response.UsuarioResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class UsuarioCloudStore {

    final Context context;
    final Retrofit retrofit;

    public UsuarioCloudStore(Context context) {
        this.context = context;
        retrofit = new Retrofit.Builder()
                .baseUrl(Url.obtenerBase(context))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public void validarUsuario(UsuarioRequest usuarioRequest, final ValidarUsuarioCallback validarUsuarioCallback){
        if(UtilitarioData.isThereInternetConnection(context)){
            UsuarioRetroService service = retrofit.create(UsuarioRetroService.class);

            Call<Response<UsuarioResponse>> repos = service.validarUsuario(usuarioRequest.getUsuario(), usuarioRequest.getContrasena());
            Log.v("URL Obtenida",repos.request().url().toString());

            repos.enqueue(new Callback<Response<UsuarioResponse>>() {
                @Override
                public void onResponse(Call<Response<UsuarioResponse>> call, retrofit2.Response<Response<UsuarioResponse>> response) {

                    if (response.code() == 200 && response.message() != null) {

                        Response<UsuarioResponse> respuesta;
                        try {
                            respuesta = response.body();
                            Log.v("RESPONSE", new Gson().toJson(respuesta));

                            if (respuesta.getCode() == Response.CODE_OK) {
                                Usuario usuario = new Usuario();
                                usuario.setIdUsuario(respuesta.getPayLoad().getIdUsuario());
                                usuario.setNombre(respuesta.getPayLoad().getNombre());
                                usuario.setClave(respuesta.getPayLoad().getClave());
                                /*usuario.setNombres(respuesta.getPayLoad().getNombres());
                                usuario.setApellidos(respuesta.getPayLoad().getApellidos());
                                usuario.setCorreo(respuesta.getPayLoad().getCorreo());*/
                                validarUsuarioCallback.onValidar(respuesta.getMessage(), usuario);
                            } else if (respuesta.getCode() == Response.CODE_ERROR) {
                                validarUsuarioCallback.onError(new Exception(respuesta.getMessage()));
                            }

                        } catch (Exception e) {
                            validarUsuarioCallback.onError(e);
                        }

                    }else{
                        validarUsuarioCallback.onError(new Exception(response.message()));
                    }
                }
                @Override
                public void onFailure(Call<Response<UsuarioResponse>> call, Throwable t) {
                    validarUsuarioCallback.onError(new Exception(t.getMessage()));
                }
            });


        }else{
            validarUsuarioCallback.onError(new NetworkConnectionException("Fuera de cobertura"));
        }
    }

    public interface UsuarioRetroService {
        @POST("seguridad/autenticacion")
        Call<Response<UsuarioResponse>> validarUsuario(@Header("username") String usuario, @Header("password") String clave);
    }

    public interface ValidarUsuarioCallback{
        void onValidar(String mensaje, Usuario usuario);
        void onError(Exception e);



    }
}
