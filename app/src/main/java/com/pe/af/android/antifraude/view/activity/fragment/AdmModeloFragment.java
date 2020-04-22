package com.pe.af.android.antifraude.view.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.pe.af.android.antifraude.R;
import com.pe.af.android.antifraude.model.AdmModeloModel;
import com.pe.af.android.antifraude.model.UsuarioModel;
import com.pe.af.android.antifraude.view.activity.MenuActivity;
import com.pe.af.android.data.exception.NetworkConnectionException;
import com.pe.af.android.data.repository.AdmModeloDataRepository;
import com.pe.af.android.data.repository.UsuarioDataRepository;
import com.pe.af.android.domain.entity.AdmModelo;
import com.pe.af.android.domain.entity.request.AdmModeloRequest;
import com.pe.af.android.domain.exception.IErrorBundle;
import com.pe.af.android.domain.repository.AdmModeloRepository;
import com.pe.af.android.domain.repository.UsuarioRepository;
import com.pe.af.android.domain.usecase.AdmModeloUseCase;
import com.pe.af.android.domain.usecase.IAdmModeloUseCase;
import com.pe.af.android.domain.usecase.IUsuarioUseCase;
import com.pe.af.android.domain.usecase.UsuarioUseCase;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;

public class AdmModeloFragment extends BaseFragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private Context context;
    private AdmModeloRepository admModeloRepository;
    private UsuarioRepository usuarioRepository;
    private EditText edt_cant_preguntas;
    private CheckBox ckbBloqueo;
    private LinearLayout lyt_seccion_bloqueo;
    private TextInputEditText edt_limite_desaprobaciones;
    private TextInputEditText edt_sin_repuestas;
    private TextInputEditText edt_cant_horas;
    private Button btn_registrar_modelo;
    private AdmModeloModel admModeloModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_adm_modelo, container, false);
        setHasOptionsMenu(true);

        context = ((MenuActivity) getActivity());
        admModeloRepository = new AdmModeloDataRepository(context);
        usuarioRepository = new UsuarioDataRepository(context);

        ((MenuActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.modelo_title));
        ((MenuActivity) getActivity()).getSupportActionBar().getThemedContext();


        edt_cant_preguntas = (EditText) view.findViewById(R.id.edt_cant_preguntas);
        ckbBloqueo = (CheckBox) view.findViewById(R.id.ckbBloqueo);
        lyt_seccion_bloqueo = (LinearLayout) view.findViewById(R.id.lyt_seccion_bloqueo);
        edt_limite_desaprobaciones = (TextInputEditText) view.findViewById(R.id.edt_limite_desaprobaciones);
        edt_sin_repuestas = (TextInputEditText) view.findViewById(R.id.edt_sin_repuestas);
        edt_cant_horas = (TextInputEditText) view.findViewById(R.id.edt_cant_horas);
        btn_registrar_modelo = (Button) view.findViewById(R.id.btn_registrar_modelo);

        ckbBloqueo.setOnCheckedChangeListener(this);
        btn_registrar_modelo.setOnClickListener(this);

        getViews();
        return view;
    }

    private void getViews() {
        IAdmModeloUseCase admModeloUseCase = new AdmModeloUseCase(admModeloRepository);
        ModelMapper modelMapper = new ModelMapper();

        Type type = new TypeToken<AdmModeloModel>() {
        }.getType();
        admModeloModel = modelMapper.map(admModeloUseCase.obtenerAdmModelo(), type);

        edt_cant_preguntas.setText(String.valueOf(admModeloModel.getCantidadPreguntas()));
        ckbBloqueo.setChecked(admModeloModel.isBloqueoHabilitado());
        edt_limite_desaprobaciones.setText(String.valueOf(admModeloModel.getLimiteDesaprobaciones()));
        edt_sin_repuestas.setText(String.valueOf(admModeloModel.getLimiteSinRespuestas()));
        edt_cant_horas.setText(String.valueOf(admModeloModel.getHorasHabilitacion()));
        checkBloqueo();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        checkBloqueo();
    }

    public void checkBloqueo() {
        lyt_seccion_bloqueo.setVisibility(View.GONE);
        if (ckbBloqueo.isChecked()) {
            lyt_seccion_bloqueo.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        String mensaje = validar();
        if (mensaje.length() > 0) {
            showErrorFragment(mensaje);
            return;
        }
        AdmModeloRequest admModeloRequest = new AdmModeloRequest();
        admModeloRequest.setCantidadPreguntas(Integer.parseInt(edt_cant_preguntas.getText().toString()));
        admModeloRequest.setBloqueoHabilitado(ckbBloqueo.isChecked());
        admModeloRequest.setLimiteDesaprobaciones(Integer.parseInt(edt_limite_desaprobaciones.getText().toString()));
        admModeloRequest.setLimiteSinRespuestas(Integer.parseInt(edt_sin_repuestas.getText().toString()));
        admModeloRequest.setHorasHabilitacion(Integer.parseInt(edt_cant_horas.getText().toString()));
        guardarAdmModelo(admModeloRequest);
    }

    private String validar() {
        String result = "";
        if (edt_cant_preguntas.getText().toString() == null ||
                edt_cant_preguntas.getText().toString().length() == 0) {
            result = "El campo de Cantidad preguntas no puede estar vacío.";
            return result;
        }
        if (Integer.parseInt(edt_cant_preguntas.getText().toString()) > 8 ||
                Integer.parseInt(edt_cant_preguntas.getText().toString()) < 1) {
            result = "El campo de Cantidad preguntas es incorrecto.";
            return result;
        }
        if (ckbBloqueo.isChecked()) {
            if (edt_limite_desaprobaciones.getText().toString() == null ||
                    edt_limite_desaprobaciones.getText().toString().length() == 0) {
                result = "El campo de Límite de preguntas desaprobadas no puede estar vacío.";
                return result;
            }
            if (Integer.parseInt(edt_limite_desaprobaciones.getText().toString()) > 8 ||
                    Integer.parseInt(edt_limite_desaprobaciones.getText().toString()) < 1) {
                result = "El campo de Límite de preguntas desaprobadas es incorrecto.";
                return result;
            }
            if (edt_sin_repuestas.getText().toString() == null ||
                    edt_sin_repuestas.getText().toString().length() == 0) {
                result = "El campo de Límite de preguntas sin respuesta no puede estar vacío.";
                return result;
            }
            if (Integer.parseInt(edt_sin_repuestas.getText().toString()) > 8 ||
                    Integer.parseInt(edt_sin_repuestas.getText().toString()) < 1) {
                result += "El campo de Límite de preguntas sin respuesta es incorrecto.";
                return result;
            }
            if (edt_cant_horas.getText().toString() == null ||
                    edt_cant_horas.getText().toString().length() == 0) {
                result = "El campo de de la Cantidad de horas para habilitar a una persona no puede estar vacío.";
                return result;
            }
            if (Integer.parseInt(edt_cant_horas.getText().toString()) > 8 ||
                    Integer.parseInt(edt_cant_horas.getText().toString()) < 1) {
                result += "El campo de la Cantidad de horas para habilitar a una persona es incorrecto.";
                return result;
            }
        }
        return result;
    }


    public void guardarAdmModelo(final AdmModeloRequest admModeloRequest) {
        IAdmModeloUseCase admModeloUseCase = new AdmModeloUseCase(admModeloRepository);
        IUsuarioUseCase usuarioUseCase = new UsuarioUseCase(usuarioRepository);

        ModelMapper modelMapper = new ModelMapper();

        Type typeUsuario = new TypeToken<UsuarioModel>() {
        }.getType();
        //UsuarioModel usuario = modelMapper.map(usuarioUseCase.obtenerUsuario(), typeUsuario);

        showLoadingFragment(getResources().getString(R.string.text_guardando_adm_modelo));

        admModeloUseCase.guardarAdmModelo(/*usuario.getUsuario()*/"pvicente", admModeloRequest, new AdmModeloUseCase.Callback() {
            @Override
            public void onEnviar(String mensaje) {
                hideLoadingFragment();
                showCorrectFragment(mensaje);
            }

            @Override
            public void onError(IErrorBundle errorBundle) {
                String mensaje = errorBundle.getErrorMessage();

                if (mensaje == null || mensaje.equals("")) {
                    mensaje = errorBundle.getException().getClass().getName();
                    if (errorBundle.getException().getClass().isInstance(new NetworkConnectionException())) {
                        mensaje = getResources().getString(R.string.text_fuera_de_cobertura);
                    }
                }
                hideLoadingFragment();
                showErrorFragment(mensaje);
            }
        });
    }
}
