package com.pe.af.android.antifraude.view;

import com.pe.af.android.antifraude.model.UsuarioModel;

public interface MenuView extends BaseView {
    void actualizarDatos(UsuarioModel usuarioModel);
    void abrirDialogoSalir();
}
