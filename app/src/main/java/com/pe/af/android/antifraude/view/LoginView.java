package com.pe.af.android.antifraude.view;

public interface LoginView extends BaseView{
    void requestAppPermissions();
    void irMenu();
    void actualizarUsuario(String codigo, String clave);
    void iniciarSesionSinSenal(String codigo, String clave);
}
