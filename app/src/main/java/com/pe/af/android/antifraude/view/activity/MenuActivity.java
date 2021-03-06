package com.pe.af.android.antifraude.view.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;
import com.pe.af.android.antifraude.R;
import com.pe.af.android.antifraude.model.UsuarioModel;
import com.pe.af.android.antifraude.presenter.MenuPresenter;
import com.pe.af.android.antifraude.view.MenuView;
import com.pe.af.android.antifraude.view.activity.fragment.ValidacionIdentidadFragment;
import com.pe.af.android.antifraude.view.activity.fragment.ValidacionPreguntaFragment;
import com.pe.af.android.antifraude.view.adapter.PreguntaSubItemAdapter;
import com.pe.af.android.domain.entity.request.IdentidadRequest;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MenuActivity extends BaseActivity implements MenuView, ValidacionIdentidadFragment.OnItemClickListener, ValidacionPreguntaFragment.OnItemClickListener {

    MenuPresenter presenter;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;

    @InjectView(R.id.navigation_drawer_layout)
    DrawerLayout drawerLayout;

    @InjectView(R.id.navigation_view)
    NavigationView navigationView;

    ActionBar actionBar;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.inject(this);
        presenter = new MenuPresenter(this);
        presenter.iniciar();
        getViews();
    }

    private void getViews() {
        configToolBar();
        fragmentManager = getSupportFragmentManager();

        if (navigationView != null) {
            presenter.setupNavigationDrawerContent(navigationView, drawerLayout, fragmentManager);
        }
        presenter.setupNavigationDrawerContent(navigationView, drawerLayout, fragmentManager);

        //First start (Menu Fragment)
        presenter.buscarAdmModelo(fragmentManager, 0);
    }

    private void configToolBar() {
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white);
        actionBar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.menu_titulo));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void actualizarDatos(UsuarioModel usuarioModel) {
        View headerView = navigationView.getHeaderView(0);

        TextView name =  headerView.findViewById(R.id.name);
        name.setText(usuarioModel.getUsuario());
        TextView lastname = headerView.findViewById(R.id.lastname);
        lastname.setText(usuarioModel.getNombreUsuario());
        TextView email = headerView.findViewById(R.id.email);
        email.setText(usuarioModel.getEmail());
    }

    @Override
    public void abrirDialogoSalir() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        presenter.cerrarSesion();
                        finish();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.alert_dialog_cerrar_sesion_title)).setPositiveButton("Si", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    @Override
    public void onBackPressed() {

    }

    public static Intent getCallingIntent(Context context){
        return new Intent(context,MenuActivity.class);
    }

    @Override
    public void onClickValidacionIdentidad(IdentidadRequest identidadRequest) {
        presenter.validarIdentidad(fragmentManager, 3, identidadRequest);
    }

    @Override
    public void onClickNuevaConsulta(int position) {
        presenter.irValidacionIdentidad(fragmentManager, position);
    }
}
