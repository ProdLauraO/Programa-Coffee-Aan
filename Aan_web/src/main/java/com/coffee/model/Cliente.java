/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coffee.model;

public class Cliente {
    private int id;
    private String identificacion;
    private String nombres;
    private String email;
    private String direccion;

    // Constructor vacío
    public Cliente() {}

    // Constructor para insertar (sin ID)
    public Cliente(String identificacion, String nombres, String email, String direccion) {
        this.identificacion = identificacion;
        this.nombres = nombres;
        this.email = email;
        this.direccion = direccion;
    }

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
}
