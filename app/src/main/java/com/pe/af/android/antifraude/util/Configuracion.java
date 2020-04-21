package com.pe.af.android.antifraude.util;

public class Configuracion {

    public static String CONSPROPERTYFILE = "params.properties";
    public static final int CONSIDCAMARA = 1337;
    public static final String IDENTIFICADOR = "IDENTIFICADOR";
    public final static String PUSH_BUNDLE_TOPICNAME = "bunTopicName";
    public final static String PUSH_BUNDLE_MENSAJE = "bunMensaje";
    public final static String PUSH_BUNDLE_CODMENSAJE = "bunCodMensaje";


    public static enum EnumRolPreferencia {
        /**
         * Rol de Administrador. Puede modificar las preferencias
         */
        ADMINISTRADOR,
        /**
         * Rol de Usuario. Puede visualizar las preferencias
         */
        USUARIO
    };
}
