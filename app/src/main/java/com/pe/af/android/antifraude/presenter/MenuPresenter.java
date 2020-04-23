package com.pe.af.android.antifraude.presenter;

import android.view.MenuItem;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;
import com.pe.af.android.antifraude.R;
import com.pe.af.android.antifraude.model.AdmModeloModel;
import com.pe.af.android.antifraude.model.AdmPreguntaModel;
import com.pe.af.android.antifraude.model.UsuarioModel;
import com.pe.af.android.antifraude.view.MenuView;
import com.pe.af.android.antifraude.view.activity.fragment.AdmModeloFragment;
import com.pe.af.android.antifraude.view.activity.fragment.AdmPreguntasFragment;
import com.pe.af.android.data.exception.NetworkConnectionException;
import com.pe.af.android.data.repository.AdmModeloDataRepository;
import com.pe.af.android.data.repository.AdmPreguntaDataRepository;
import com.pe.af.android.data.repository.UsuarioDataRepository;
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

public class MenuPresenter {
    final MenuView view;
    final UsuarioRepository usuarioRepository;
    final AdmModeloRepository admModeloRepository;
    final AdmPreguntaRepository admPreguntaRepository;
    private AdmModeloModel admModeloModel;
    private List<AdmPreguntaModel> admPreguntaModelList;

    public MenuPresenter(MenuView view) {
        this.view = view;
        usuarioRepository = new UsuarioDataRepository(view.getContext());
        admModeloRepository = new AdmModeloDataRepository(view.getContext());
        admPreguntaRepository = new AdmPreguntaDataRepository(view.getContext());
    }

    public void iniciar() {
        IUsuarioUseCase usuarioUseCase = new UsuarioUseCase(usuarioRepository);
        ModelMapper modelMapper = new ModelMapper();

        Type type = new TypeToken<UsuarioModel>() {
        }.getType();
        UsuarioModel usuario = modelMapper.map(usuarioUseCase.obtenerUsuario(), type);
        view.actualizarDatos(usuario);
    }

    public void setupNavigationDrawerContent(NavigationView navigationView, final DrawerLayout drawerLayout, final FragmentManager fragmentManager) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.item_adm_modelo:
                                menuItem.setChecked(true);
                                buscarAdmModelo(fragmentManager, 0);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_adm_preguntas:
                                menuItem.setChecked(true);
                                buscarAdmPreguntas(fragmentManager, 1);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_validacion_identidad:
                                menuItem.setChecked(true);
                                irValidacionIdentidad(fragmentManager, 1);
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                            case R.id.item_salir:
                                menuItem.setChecked(true);
                                view.abrirDialogoSalir();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                return true;
                        }
                        return true;
                    }
                }
        );
    }

    public void setFragment(FragmentManager fragmentManager, int position) {
        FragmentTransaction fragmentTransaction;
        switch (position) {
            case 0:
                fragmentTransaction = fragmentManager.beginTransaction();
                AdmModeloFragment admModeloFragment = new AdmModeloFragment();
                fragmentTransaction.replace(R.id.fragment, admModeloFragment);
                fragmentTransaction.commitAllowingStateLoss();
                break;
            case 1:
                fragmentTransaction = fragmentManager.beginTransaction();
                AdmPreguntasFragment admPreguntasFragment = new AdmPreguntasFragment();
                fragmentTransaction.replace(R.id.fragment, admPreguntasFragment);
                fragmentTransaction.commitAllowingStateLoss();
                break;
        }
    }

    public void buscarAdmModelo(final FragmentManager fragmentManager, final int position) {
        obtenerAdmModelo(fragmentManager, position);
    }

    private void obtenerAdmModelo(final FragmentManager fragmentManager, final int position) {
        IAdmModeloUseCase admModeloUseCase = new AdmModeloUseCase(admModeloRepository);
        IUsuarioUseCase usuarioUseCase = new UsuarioUseCase(usuarioRepository);
        ModelMapper modelMapper = new ModelMapper();

        Type type = new TypeToken<UsuarioModel>() {
        }.getType();
        UsuarioModel usuario = modelMapper.map(usuarioUseCase.obtenerUsuario(), type);

        view.showloading(view.getContext().getResources().getString(R.string.text_obteniendo_adm_modelo));

        admModeloUseCase.obtenerAdmModelo(usuario.getNombre(), new AdmModeloUseCase.Callback() {
            @Override
            public void onEnviar(String mensaje) {
                view.hideloading();
                view.showCorrect(mensaje);
                setFragment(fragmentManager, position);
            }

            @Override
            public void onError(IErrorBundle errorBundle) {
                String mensaje = errorBundle.getErrorMessage();

                if (mensaje == null || mensaje.equals("")) {
                    mensaje = errorBundle.getException().getClass().getName();
                    if (errorBundle.getException().getClass().isInstance(new NetworkConnectionException())) {
                        mensaje = view.getContext().getResources().getString(R.string.text_fuera_de_cobertura);
                    }
                }
                view.hideloading();
                view.showError(mensaje);

                IAdmModeloUseCase admModeloUseCase = new AdmModeloUseCase(admModeloRepository);
                ModelMapper modelMapper = new ModelMapper();

                Type type = new TypeToken<AdmModeloModel>() {
                }.getType();

                if (admModeloUseCase.obtenerAdmModelo() != null) {
                    admModeloModel = modelMapper.map(admModeloUseCase.obtenerAdmModelo(), type);
                    setFragment(fragmentManager, position);
                }
            }
        });
    }

    public void buscarAdmPreguntas(final FragmentManager fragmentManager, final int position) {
        obtenerAdmPregunta(fragmentManager, position);
    }

    private void obtenerAdmPregunta(final FragmentManager fragmentManager, final int position) {
        IAdmPreguntaUseCase admPreguntaUseCase = new AdmPreguntaUseCase(admPreguntaRepository);
        IUsuarioUseCase usuarioUseCase = new UsuarioUseCase(usuarioRepository);
        ModelMapper modelMapper = new ModelMapper();

        Type type = new TypeToken<UsuarioModel>() {
        }.getType();
        UsuarioModel usuario = modelMapper.map(usuarioUseCase.obtenerUsuario(), type);

        view.showloading(view.getContext().getResources().getString(R.string.text_obteniendo_adm_pregunta));

        admPreguntaUseCase.obtenerAdmPregunta(usuario.getNombre(), new AdmPreguntaUseCase.Callback() {
            @Override
            public void onEnviar(String mensaje) {
                view.hideloading();
                view.showCorrect(mensaje);
                setFragment(fragmentManager, position);
            }

            @Override
            public void onError(IErrorBundle errorBundle) {
                String mensaje = errorBundle.getErrorMessage();

                if (mensaje == null || mensaje.equals("")) {
                    mensaje = errorBundle.getException().getClass().getName();
                    if (errorBundle.getException().getClass().isInstance(new NetworkConnectionException())) {
                        mensaje = view.getContext().getResources().getString(R.string.text_fuera_de_cobertura);
                    }
                }
                view.hideloading();
                view.showError(mensaje);

                IAdmPreguntaUseCase admPreguntaUseCase = new AdmPreguntaUseCase(admPreguntaRepository);
                ModelMapper modelMapper = new ModelMapper();

                Type type = new TypeToken<List<AdmPreguntaModel>>() {
                }.getType();

                if (admPreguntaUseCase.obtenerListAdmPregunta().size()>0) {
                    admPreguntaModelList = modelMapper.map(admPreguntaUseCase.obtenerListAdmPregunta(), type);
                    setFragment(fragmentManager, position);
                }
            }
        });
    }

    public void irValidacionIdentidad(final FragmentManager fragmentManager, final int position) {
        /*view.showloading(view.getContext().getResources().getString(R.string.text_obteniendo_ordenes_compra_pendientes));
        GetOrdenCompraUseCase getOrdenCompraUseCase = new GetOrdenCompraUseCaseImpl(ordenCompraRepository);
        List<OrdenCompraModel> ordenCompraModelList = OrdenCompraModelMapper.adapter(getOrdenCompraUseCase.getOrdenCompraList("", ""));
        view.hideloading();

        if (ordenCompraModelList.size()==0){
            view.showInfo(view.getContext().getResources().getString(R.string.text_no_existen_ordenes_compra_pendientes));
        }else{
            setFragment(fragmentManager, position);
        }*/
    }

    public void cerrarSesion() {
        IUsuarioUseCase usuarioUseCase = new UsuarioUseCase(usuarioRepository);
        IAdmModeloUseCase admModeloUseCase = new AdmModeloUseCase(admModeloRepository);
        IAdmPreguntaUseCase admPreguntaUseCase = new AdmPreguntaUseCase(admPreguntaRepository);

        usuarioUseCase.cerrarSesion();
        admModeloUseCase.eliminarAdmModelo();
        admPreguntaUseCase.eliminarAdmPregunta();
    }
}
