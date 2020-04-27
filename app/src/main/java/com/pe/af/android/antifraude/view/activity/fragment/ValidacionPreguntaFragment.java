package com.pe.af.android.antifraude.view.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.pe.af.android.antifraude.R;
import com.pe.af.android.antifraude.model.IdentidadModel;
import com.pe.af.android.antifraude.model.OpcionModel;
import com.pe.af.android.antifraude.model.PreguntaModel;
import com.pe.af.android.antifraude.model.UsuarioModel;
import com.pe.af.android.antifraude.view.activity.MenuActivity;
import com.pe.af.android.antifraude.view.adapter.ListaLayoutManager;
import com.pe.af.android.antifraude.view.adapter.PreguntaAdapter;
import com.pe.af.android.data.exception.NetworkConnectionException;
import com.pe.af.android.data.repository.AdmModeloDataRepository;
import com.pe.af.android.data.repository.AdmPreguntaDataRepository;
import com.pe.af.android.data.repository.IdentidadDataRepository;
import com.pe.af.android.data.repository.UsuarioDataRepository;
import com.pe.af.android.domain.entity.Pregunta;
import com.pe.af.android.domain.entity.request.AdmPreguntaRequest;
import com.pe.af.android.domain.exception.IErrorBundle;
import com.pe.af.android.domain.repository.AdmModeloRepository;
import com.pe.af.android.domain.repository.AdmPreguntaRepository;
import com.pe.af.android.domain.repository.IdentidadRepository;
import com.pe.af.android.domain.repository.UsuarioRepository;
import com.pe.af.android.domain.usecase.AdmPreguntaUseCase;
import com.pe.af.android.domain.usecase.IAdmPreguntaUseCase;
import com.pe.af.android.domain.usecase.IIdentidadUseCase;
import com.pe.af.android.domain.usecase.IUsuarioUseCase;
import com.pe.af.android.domain.usecase.IdentidadUseCase;
import com.pe.af.android.domain.usecase.UsuarioUseCase;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ValidacionPreguntaFragment extends BaseFragment implements View.OnClickListener {

    private Context context;
    private AdmModeloRepository admModeloRepository;
    private IdentidadRepository identidadRepository;
    private AdmPreguntaRepository admPreguntaRepository;
    private UsuarioRepository usuarioRepository;
    private ListaLayoutManager listaLayoutManager;
    private PreguntaAdapter preguntaAdapter;
    private TextView tv_documento_consultado;
    private TextView tv_nombre_completo;
    private TextView tv_estado;
    private TextView tv_operacion;
    private LinearLayout ly_nombre_completo;
    private RecyclerView rc_pregunta;
    private Button btn_continuar;
    private List<PreguntaModel> preguntaModels;
    private IdentidadModel identidadModel;
    private OnItemClickListener onItemClickListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_validacion_pregunta, container, false);
        setHasOptionsMenu(true);

        context = ((MenuActivity) getActivity());
        admModeloRepository = new AdmModeloDataRepository(context);
        identidadRepository = new IdentidadDataRepository(context);
        admPreguntaRepository = new AdmPreguntaDataRepository(context);
        usuarioRepository = new UsuarioDataRepository(context);

        ((MenuActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.adm_pregunta_title));
        ((MenuActivity) getActivity()).getSupportActionBar().getThemedContext();


        tv_documento_consultado = (TextView) view.findViewById(R.id.tv_documento_consultado);
        tv_nombre_completo = (TextView) view.findViewById(R.id.tv_nombre_completo);
        tv_estado = (TextView) view.findViewById(R.id.tv_estado);
        tv_operacion = (TextView) view.findViewById(R.id.tv_operacion);
        rc_pregunta = (RecyclerView) view.findViewById(R.id.rc_pregunta);
        btn_continuar = (Button) view.findViewById(R.id.btn_continuar);
        ly_nombre_completo = view.findViewById(R.id.ly_nombre_completo);

        btn_continuar.setOnClickListener(this);

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
        IIdentidadUseCase identidadUseCase = new IdentidadUseCase(identidadRepository);
        ModelMapper modelMapper = new ModelMapper();

        Type typeIdentidad = new TypeToken<IdentidadModel>() {
        }.getType();
        Type typePregunta = new TypeToken<List<PreguntaModel>>() {
        }.getType();

        identidadModel = modelMapper.map(identidadUseCase.obtenerIdentidad(), typeIdentidad);
        List<Pregunta> preguntaList = identidadUseCase.obtenerListPregunta();
        if (preguntaList!=null) {
            preguntaModels = modelMapper.map(preguntaList, typePregunta);
            for (int i = 0; i < preguntaList.size(); i++) {
                Pregunta item = preguntaList.get(i);
                preguntaModels.get(i).setOpciones(item.getOpciones());

                List<OpcionModel> alternativas = new ArrayList<>();
                for (String key : item.getOpciones().keySet()) {
                    OpcionModel op = new OpcionModel();
                    op.setOpcion(key);
                    op.setEstado(false);
                    alternativas.add(op);
                }
                preguntaModels.get(i).setAlternativas(alternativas);
            }
        }

        tv_documento_consultado.setText(identidadModel.getNroDocumento());
        tv_nombre_completo.setText(identidadModel.getPersona());
        tv_estado.setText(identidadModel.getEstado());
        tv_operacion.setText(identidadModel.getTransaccion());

        actualizarList(preguntaModels);
    }

    public void actualizarList(List<PreguntaModel> lista) {
        if (lista != null && !lista.isEmpty()) {
            ly_nombre_completo.setVisibility(View.VISIBLE);
            rc_pregunta.setVisibility(View.VISIBLE);
            btn_continuar.setText(getContext().getResources().getString(R.string.btn_description_continuar));
            if (rc_pregunta != null) {
                if (preguntaAdapter == null) {
                    preguntaAdapter = new PreguntaAdapter(lista);
                } else {
                    preguntaAdapter.setPreguntaList(lista);
                }
                /*preguntaAdapter.setOnItemClickListener(new AdmPreguntaAdapter.OnItemClickListener() {
                    @Override
                    public void onAdmPreguntaItemClicked(int position, boolean state) {
                        listaPregunta.get(position).setSeleccionada(state);
                        refrescarList();
                    }
                });*/
                rc_pregunta.setAdapter(preguntaAdapter);
                //refrescarList();
            }
        } else {
            ly_nombre_completo.setVisibility(View.GONE);
            rc_pregunta.setVisibility(View.GONE);
            btn_continuar.setText(getContext().getResources().getString(R.string.btn_description_nuevo_consulta));
        }
    }

    /*private void refrescarList() {
        cont = 0;

        for (AdmPreguntaModel admPreguntaModel : listaPregunta) {
            if (admPreguntaModel.isSeleccionada()) {
                cont++;
            }
        }
        if (cont < admModeloModel.getCantidadPreguntas()) {
            btn_continuar.setEnabled(false);
            btn_continuar.setBackground(getResources().getDrawable(R.drawable.button_round_corners_grey));

            showInfoFragment(getResources().getString(R.string.modelo_info_cant_preguntas));
            return;
        }
        btn_continuar.setEnabled(true);
        btn_continuar.setBackground(getResources().getDrawable(R.drawable.button_round_corners_blue));
    }*/


    @Override
    public void onClick(View v) {
        if (preguntaModels==null){
            onItemClickListener.onClickNuevaConsulta(2);
            return;
        }
        showCorrectFragment("Procesando");
        /*AdmPreguntaRequest admPreguntaRequest = new AdmPreguntaRequest();
        int[] nroPregunta = new int[cont];
        int count = 0;
        for (AdmPreguntaModel admPreguntaModel: listaPregunta){
            if (admPreguntaModel.isSeleccionada()){
                nroPregunta[count] = admPreguntaModel.getNroPregunta();
                count++;
            }
        }
        admPreguntaRequest.setPreguntasSeleccionadas(nroPregunta);*/
        //guardarAdmPregunta(admPreguntaRequest);

    }

    public interface OnItemClickListener {
        void onClickNuevaConsulta(int position);
    }

    public void guardarAdmPregunta(final AdmPreguntaRequest admPreguntaRequest) {
        IAdmPreguntaUseCase admPreguntaUseCase = new AdmPreguntaUseCase(admPreguntaRepository);
        IUsuarioUseCase usuarioUseCase = new UsuarioUseCase(usuarioRepository);

        ModelMapper modelMapper = new ModelMapper();

        Type typeUsuario = new TypeToken<UsuarioModel>() {
        }.getType();
        UsuarioModel usuario = modelMapper.map(usuarioUseCase.obtenerUsuario(), typeUsuario);

        showLoadingFragment(getResources().getString(R.string.text_guardando_adm_pregunta));

        admPreguntaUseCase.guardarAdmPregunta(usuario.getUsuario(), admPreguntaRequest, new AdmPreguntaUseCase.Callback() {
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

    //Override this function as below to set fragmentInterfacer
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onItemClickListener = (OnItemClickListener) context;
    }
}
