/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coffee.model;

public class Producto {
    private int id;
    private String nombre;
    private String descripcion;
    private double precioUnitario;
    private double porcentajeImpuesto;
    private String tipo;
    private int stock;

    public Producto() {}

    // Constructor completo
    public Producto(int id, String nombre, double precioUnitario, int stock) {
        this.id = id;
        this.nombre = nombre;
        this.precioUnitario = precioUnitario;
        this.stock = stock;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }
    public double getPorcentajeImpuesto() { return porcentajeImpuesto; }
    public void setPorcentajeImpuesto(double porcentajeImpuesto) { this.porcentajeImpuesto = porcentajeImpuesto; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}