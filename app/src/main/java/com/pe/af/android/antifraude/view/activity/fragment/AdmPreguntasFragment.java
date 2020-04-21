package com.pe.af.android.antifraude.view.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.pe.af.android.antifraude.R;
import com.pe.af.android.antifraude.model.AdmPreguntaModel;
import com.pe.af.android.antifraude.view.activity.MenuActivity;
import com.pe.af.android.antifraude.view.adapter.AdmPreguntaAdapter;
import com.pe.af.android.antifraude.view.adapter.ListaLayoutManager;
import com.pe.af.android.data.repository.AdmPreguntaDataRepository;
import com.pe.af.android.data.repository.UsuarioDataRepository;
import com.pe.af.android.domain.entity.AdmPregunta;
import com.pe.af.android.domain.repository.AdmPreguntaRepository;
import com.pe.af.android.domain.repository.UsuarioRepository;
import com.pe.af.android.domain.usecase.AdmPreguntaUseCase;
import com.pe.af.android.domain.usecase.IAdmPreguntaUseCase;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class AdmPreguntasFragment extends BaseFragment {

    private Context context;
    private AdmPreguntaRepository admPreguntaRepository;
    private UsuarioRepository usuarioRepository;
    private ListaLayoutManager listaLayoutManager;
    private AdmPreguntaAdapter admPreguntaAdapter;
    private LinearLayout llNoResultados;
    private RecyclerView rc_pregunta;
    List<AdmPreguntaModel> listaPregunta;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_adm_preguntas, container, false);
        setHasOptionsMenu(true);

        context = ((MenuActivity) getActivity());
        admPreguntaRepository = new AdmPreguntaDataRepository(context);
        usuarioRepository = new UsuarioDataRepository(context);

        ((MenuActivity) getActivity()).getSupportActionBar().setTitle(getResources().getString(R.string.adm_pregunta_title));
        ((MenuActivity) getActivity()).getSupportActionBar().getThemedContext();


        rc_pregunta = (RecyclerView) view.findViewById(R.id.rc_pregunta);
        llNoResultados = view.findViewById(R.id.ll_noresultados);

        getViews();
        llenarListaPreguntas();
        return view;
    }

    private void getViews() {
        listaLayoutManager = new ListaLayoutManager(context);
        if (rc_pregunta != null)
            rc_pregunta.setLayoutManager(listaLayoutManager);
    }

    private void llenarListaPreguntas() {
        IAdmPreguntaUseCase admPreguntaUseCase = new AdmPreguntaUseCase(admPreguntaRepository);
        ModelMapper modelMapper = new ModelMapper();

        Type type = new TypeToken<List<AdmPreguntaModel>>() {
        }.getType();
        listaPregunta = modelMapper.map(admPreguntaUseCase.obtenerListAdmPregunta(), type);

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
                    public void onAdmPreguntaItemClicked(List<AdmPreguntaModel> admPreguntaModels) {
                        listaPregunta = admPreguntaModels;
                    }
                });
                rc_pregunta.setAdapter(admPreguntaAdapter);
            }
        } else {
            llNoResultados.setVisibility(View.VISIBLE);
        }
    }

    /*public void getOrdenCompraDetalleList(final OrdenCompraModel ordenCompraModel) {
        showLoadingFragment(getResources().getString(R.string.text_obteniendo_detalle_orden_compra));
        ListarOrdenCompraDetalleUseCase listarOrdenCompraDetalleUseCase = new ListarOrdenCompraDetalleUseCaseImpl(ordenCompraDetalleRepository);

        ListarOrdenCompraUseCase listarOrdenCompraUseCase = new ListarOrdenCompraUseCaseImpl(ordenCompraRepository, usuarioRepository);
        listarOrdenCompraUseCase.setOrdenCompra(OrdenCompraModelMapper.revert(ordenCompraModel));

        OrdenCompraDetalleRequest ordenCompraDetalleRequest = new OrdenCompraDetalleRequest();
        ordenCompraDetalleRequest.setIdOrdenCompra(ordenCompraModel.getIdOrdenCompra());

        listarOrdenCompraDetalleUseCase.ejecutar(ordenCompraDetalleRequest, new ListarOrdenCompraDetalleUseCase.Callback() {
            @Override
            public void onEnviar(String mensaje) {
                hideLoadingFragment();
                showCorrectFragment(mensaje);
                navigateToOrdenCompraDetalle(context);
            }

            @Override
            public void onError(ErrorBundle errorBundle) {
                GetOrdenCompraDetalleUseCase getOrdenCompraDetalleUseCase = new GetOrdenCompraDetalleUseCaseImpl(ordenCompraDetalleRepository);
                List<OrdenCompraDetalleModel> ordenCompraDetalleModelList = OrdenCompraDetalleModelMapper.adapter(getOrdenCompraDetalleUseCase.getOrdenCompraDetalleListPorIdOrdenCompra(ordenCompraModel.getIdOrdenCompra()));

                hideLoadingFragment();

                String mensaje = errorBundle.getErrorMessage();

                if (mensaje == null || mensaje.equals("")) {
                    mensaje = errorBundle.getException().getClass().getName();

                    if (errorBundle.getException().getClass().isInstance(new NetworkConnectionException())) {
                        mensaje = getResources().getString(R.string.text_fuera_de_cobertura);
                    }
                }
                showErrorFragment(mensaje);

                if (ordenCompraDetalleModelList.size()==0){
                    showInfoFragment(getResources().getString(R.string.text_no_existe_detalle_orden_compra));
                }else{
                    navigateToOrdenCompraDetalle(context);
                }
            }
        });
    }*/
}
