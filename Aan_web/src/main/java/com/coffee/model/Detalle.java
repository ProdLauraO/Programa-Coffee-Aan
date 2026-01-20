/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coffee.model;

public class Detalle {
    private int id;
    private int facturaId;
    private int productoId;
    private int cantidad;
    private double precioUnitarioVenta;
    private double subtotalLinea;

    public Detalle() {}

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getFacturaId() { return facturaId; }
    public void setFacturaId(int facturaId) { this.facturaId = facturaId; }
    public int getProductoId() { return productoId; }
    public void setProductoId(int productoId) { this.productoId = productoId; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public double getSubtotalLinea() { return subtotalLinea; }
    public void setSubtotalLinea(double subtotalLinea) { this.subtotalLinea = subtotalLinea; }
}