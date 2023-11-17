package com.example.afusesc.tastynready.model;

public class Platos {
    String Nombre, img, Categoria, Descripcion;
    double Precio;

    public Platos(){}

    public Platos(String nombre, String img, String categoria, String descripcion, double precio) {
        Nombre = nombre;
        this.img = img;
        Categoria = categoria;
        Descripcion = descripcion;
        Precio = precio;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public double getPrecio() {
        return Precio;
    }

    public void setPrecio(double precio) {
        Precio = precio;
    }
}
