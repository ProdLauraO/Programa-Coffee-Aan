/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coffee.model;

/**
 * Entidad que representa a un Cliente.
 * Cumple con el estándar de encapsulamiento y anotaciones de Java.
 */
public class Cliente {
    private int id;
    private String identificacion;
    private String nombres;
    private String email;
    private String direccion;
    private String ciudad;
    private String telefono;

    // Constructor para inserción (sin ID porque es Auto-increment)
    public Cliente(String identificacion, String nombres, String email, String direccion, String ciudad, String telefono) {
        this.identificacion = identificacion;
        this.nombres = nombres;
        this.email = email;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.telefono = telefono;
    }

    // Constructor vacío para el listado
    public Cliente() {}

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getIdentificacion() { return identificacion; }
    public void setIdentificacion(String identificacion) { this.identificacion = identificacion; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}
