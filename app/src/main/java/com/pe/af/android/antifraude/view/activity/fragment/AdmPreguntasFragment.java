package com.pe.af.android.antifraude.view.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.pe.af.android.antifraude.R;
import com.pe.af.android.antifraude.model.AdmModeloModel;
import com.pe.af.android.antifraude.model.AdmPreguntaModel;
import com.pe.af.android.antifraude.model.UsuarioModel;
import com.pe.af.android.antifraude.view.activity.MenuActivity;
import com.pe.af.android.antifraude.view.adapter.AdmPreguntaAdapter;
import com.pe.af.android.antifraude.view.adapter.ListaLayoutManager;
import com.pe.af.android.data.exception.NetworkConnectionException;
import com.pe.af.android.data.repository.AdmModeloDataRepository;
import com.pe.af.android.data.repository.AdmPreguntaDataRepository;
import com.pe.af.android.data.repository.UsuarioDataRepository;
import com.pe.af.android.domain.entity.request.AdmPreguntaRequest;
import com.pe.af.android.domain.exception.IErrorBundle;
import com.pe.af.android.domain.repository.AdmModeloRepository;
import com.pe.af.android.domain.repository.AdmPreguntaRepository;
import com.pe.af.android.domain.repository.UsuarioRepository;
import com.pe.af.android.domain.usecase.AdmModeloUseCase;
import com.pe.af.android.domain.usecase.AdmPreguntaUseCase;
import com.pe.af.android.domain.usecase.IAdmModeloUseCase;
import com.pe.af.android.domain.usecase.IAdmPreguntaUseCase;
import com.pe.af.android.domain.usecase.IUsuarioUseCase;
import com.pe.af.android.domain.usecase.UsuarioUseCase;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class AdmPreguntasFragment extends BaseFragment implements View.OnClickListener {

    private Context context;
    private AdmModeloRepository admModeloRepository;
    private AdmPreguntaRepository admPreguntaRepository;
    private UsuarioRepository usuarioRepository;
    private ListaLayoutManager listaLayoutManager;
    private AdmPreguntaAdapter admPreguntaAdapter;
    private LinearLayout llNoResultados;
    private RecyclerView rc_pregunta;
    private Button btn_registrar_modelo;
    private List<AdmPreguntaModel> listaPregunta;
    private AdmModeloModel admModeloModel;
    private int cont;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_adm_preguntas, container, false);
        setHasOptionsMenu(true);

        context = ((MenuActivity) getActivity());
        admModeloRepository = new AdmModeloDataRepository(context);
        admPreguntaRepository = new AdmPreguntaDataRepository(context);
        usuarioRepository = new UsuarioDataRepository(context);

        ((MenuActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.adm_pregunta_title));
        ((MenuActivity) getActivity()).getSupportActionBar().getThemedContext();


        rc_pregunta = (RecyclerView) view.findViewById(R.id.rc_pregunta);
        btn_registrar_modelo = (Button) view.findViewById(R.id.btn_registrar_modelo);
        llNoResultados = view.findViewById(R.id.ll_noresultados);

        btn_registrar_modelo.setOnClickListener(this);

        getViews();
        inicializarVista();
        return view;
    }

    private void getViews() {
        listaLayoutManager = new ListaLayoutManager(context);
        if (rc_pregunta != null)
            rc_pregunta.setLayoutManager(listaLayoutManager);
    }

    private void inicializarVista() {
        btn_registrar_modelo.setEnabled(false);
        btn_registrar_modelo.setBackground(getResources().getDrawable(R.drawable.button_round_corners_grey));

        IAdmModeloUseCase admModeloUseCase = new AdmModeloUseCase(admModeloRepository);
        IAdmPreguntaUseCase admPreguntaUseCase = new AdmPreguntaUseCase(admPreguntaRepository);
        ModelMapper modelMapper = new ModelMapper();

        Type typeModelo = new TypeToken<AdmModeloModel>() {
        }.getType();
        Type typePregunta = new TypeToken<List<AdmPreguntaModel>>() {
        }.getType();
        admModeloModel = modelMapper.map(admModeloUseCase.obtenerAdmModelo(), typeModelo);
        listaPregunta = modelMapper.map(admPreguntaUseCase.obtenerListAdmPregunta(), typePregunta);

        actualizarList(listaPregunta);
    }

    public void actualizarList(List<AdmPreguntaModel> lista) {
        if (!lista.isEmpty()) {
            if (rc_pregunta != null) {
                if (admPreguntaAdapter == null) {
                    admPreguntaAdapter = new AdmPreguntaAdapter(context, lista);
                } else {
                    admPreguntaAdapter.setAdmPreguntaList(lista);
                }
                admPreguntaAdapter.setOnItemClickListener(new AdmPreguntaAdapter.OnItemClickListener() {
                    @Override
                    public void onAdmPreguntaItemClicked(int position, boolean state) {
                        listaPregunta.get(position).setSeleccionada(state);
                        refrescarList();
                    }
                });
                rc_pregunta.setAdapter(admPreguntaAdapter);
                refrescarList();
            }
        } else {
            llNoResultados.setVisibility(View.VISIBLE);
        }
    }

    private void refrescarList() {
        cont = 0;

        for (AdmPreguntaModel admPreguntaModel : listaPregunta) {
            if (admPreguntaModel.isSeleccionada()) {
                cont++;
            }
        }
        if (cont < admModeloModel.getCantidadPreguntas()) {
            btn_registrar_modelo.setEnabled(false);
            btn_registrar_modelo.setBackground(getResources().getDrawable(R.drawable.button_round_corners_grey));

            showInfoFragment(getResources().getString(R.string.modelo_info_cant_preguntas));
            return;
        }
        btn_registrar_modelo.setEnabled(true);
        btn_registrar_modelo.setBackground(getResources().getDrawable(R.drawable.button_round_corners_blue));
    }


    @Override
    public void onClick(View v) {
        AdmPreguntaRequest admPreguntaRequest = new AdmPreguntaRequest();
        int[] nroPregunta = new int[cont];
        int count = 0;
        for (AdmPreguntaModel admPreguntaModel: listaPregunta){
            if (admPreguntaModel.isSeleccionada()){
                nroPregunta[count] = admPreguntaModel.getNroPregunta();
                count++;
            }
        }
        admPreguntaRequest.setPreguntasSeleccionadas(nroPregunta);
        guardarAdmPregunta(admPreguntaRequest);

    }

    public void guardarAdmPregunta(final AdmPreguntaRequest admPreguntaRequest) {
        IAdmPreguntaUseCase admPreguntaUseCase = new AdmPreguntaUseCase(admPreguntaRepository);
        IUsuarioUseCase usuarioUseCase = new UsuarioUseCase(usuarioRepository);

        ModelMapper modelMapper = new ModelMapper();

        Type typeUsuario = new TypeToken<UsuarioModel>() {
        }.getType();
        UsuarioModel usuario = modelMapper.map(usuarioUseCase.obtenerUsuario(), typeUsuario);

        showLoadingFragment(getResources().getString(R.string.text_guardando_adm_pregunta));

        admPreguntaUseCase.guardarAdmPregunta(usuario.getNombre(), admPreguntaRequest, new AdmPreguntaUseCase.Callback() {
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
