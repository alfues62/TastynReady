package com.example.afusesc.tastynready;

public class UsuarioInfo {
    private String nombre;
    private String email;
    private String uid;

    public UsuarioInfo(String nombre, String email, String uid) {
        this.nombre = nombre;
        this.email = email;
        this.uid = uid;
    }

    // Agrega getters según sea necesario
    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getUid() {
        return uid;
    }
}

