package com.pe.af.android.antifraude.model;

import com.pe.af.android.antifraude.R;

import java.util.ArrayList;
import java.util.List;


public class MenuModel {

    public final static int SALIR = 1;
    public final static int ADM_MODELO = 2;
    public final static int ADM_PREGUNTA = 3;
    public final static int VALIDACION_IDENTIDAD = 4;

    int id;
    String titulo;
    int imagen;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public static List<MenuModel> getMenus() {
        List<MenuModel> lista = new ArrayList<>();

        MenuModel model;

        model = new MenuModel();
        model.setId(ADM_MODELO);
        model.setTitulo("Administración de Modelo");
        model.setImagen(R.drawable.menuprincipal_adm_modelo);
        lista.add(model);

        model = new MenuModel();
        model.setId(ADM_PREGUNTA);
        model.setTitulo("Administración de Preguntas");
        model.setImagen(R.drawable.menuprincipal_adm_pregunta);
        lista.add(model);

        model = new MenuModel();
        model.setId(VALIDACION_IDENTIDAD);
        model.setTitulo("Validación de Identidad");
        model.setImagen(R.drawable.menuprincipal_validacion_identidad);
        lista.add(model);

        model = new MenuModel();
        model.setId(SALIR);
        model.setTitulo("Salir");
        model.setImagen(R.drawable.menuprincipal_salir);
        lista.add(model);

        return lista;
    }
}
