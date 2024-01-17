package com.example.afusesc.tastynready.model;

import java.util.List;

public class Reserva {
    private Long Comensales;
    private String Fecha;
    private String Hora;
    private String IdUser;
    private String Sala;
    private String Usuario;
    private List<Platos> zPlatos;

    // Agrega este constructor sin argumentos
    public Reserva() {
        // Constructor vacío requerido por Firebase
    }

    public Long getComensales() {
        return Comensales;
    }

    public void setComensales(Long comensales) {
        Comensales = comensales;
    }

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        Hora = hora;
    }

    public String getIdUser() {
        return IdUser;
    }

    public void setIdUser(String idUser) {
        IdUser = idUser;
    }

    public String getSala() {
        return Sala;
    }

    public void setSala(String sala) {
        Sala = sala;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public List<Platos> getzPlatos() {
        return zPlatos;
    }

    public void setzPlatos(List<Platos> zPlatos) {
        this.zPlatos = zPlatos;
    }
}
