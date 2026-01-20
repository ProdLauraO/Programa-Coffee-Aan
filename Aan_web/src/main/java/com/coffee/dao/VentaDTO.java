/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coffee.model;
import java.util.List;

public class VentaDTO {
    private String idCliente;
    private double total;
    private String metodoPago;
    private List<ItemCarrito> items;

    // Getters y Setters necesarios para GSON
    public String getIdCliente() { return idCliente; }
    public double getTotal() { return total; }
    public String getMetodoPago() { return metodoPago; }
    public List<ItemCarrito> getItems() { return items; }

    public static class ItemCarrito {
        public int idProducto;
        public int cantidad;
        public double precio;
    }
}