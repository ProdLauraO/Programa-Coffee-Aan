/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coffee.model;
import java.util.Date;

public class Factura {
    private int id;
    private int clienteId; // Relación con id_cliente
    private String numeroFactura;
    private Date fecha;
    private double totalBruto;
    private double totalImpuestos;
    private double totalNeto;
    private String estado;

    public Factura() {}

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getClienteId() { return clienteId; }
    public void setClienteId(int clienteId) { this.clienteId = clienteId; }
    public String getNumeroFactura() { return numeroFactura; }
    public void setNumeroFactura(String numeroFactura) { this.numeroFactura = numeroFactura; }
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    public double getTotalNeto() { return totalNeto; }
    public void setTotalNeto(double totalNeto) { this.totalNeto = totalNeto; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}