package com.pe.af.android.data.dao;

import com.orm.SugarRecord;

public class AdmModeloDao extends SugarRecord {
    private int cantidadPreguntas;
    private boolean bloqueoHabilitado;
    private int limiteDesaprobaciones;
    private int limiteSinRespuestas;
    private int horasHabilitacion;

    public int getCantidadPreguntas() {
        return cantidadPreguntas;
    }

    public void setCantidadPreguntas(int cantidadPreguntas) {
        this.cantidadPreguntas = cantidadPreguntas;
    }

    public boolean isBloqueoHabilitado() {
        return bloqueoHabilitado;
    }

    public void setBloqueoHabilitado(boolean bloqueoHabilitado) {
        this.bloqueoHabilitado = bloqueoHabilitado;
    }

    public int getLimiteDesaprobaciones() {
        return limiteDesaprobaciones;
    }

    public void setLimiteDesaprobaciones(int limiteDesaprobaciones) {
        this.limiteDesaprobaciones = limiteDesaprobaciones;
    }

    public int getLimiteSinRespuestas() {
        return limiteSinRespuestas;
    }

    public void setLimiteSinRespuestas(int limiteSinRespuestas) {
        this.limiteSinRespuestas = limiteSinRespuestas;
    }

    public int getHorasHabilitacion() {
        return horasHabilitacion;
    }

    public void setHorasHabilitacion(int horasHabilitacion) {
        this.horasHabilitacion = horasHabilitacion;
    }
}
