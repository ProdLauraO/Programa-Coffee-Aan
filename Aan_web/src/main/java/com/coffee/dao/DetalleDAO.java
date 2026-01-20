/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coffee.dao;

import com.coffee.config.Conexion;
import com.coffee.model.Detalle;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetalleDAO {
    // Listar detalles de una factura específica
    public List<Detalle> listarPorFactura(int idFactura) {
        List<Detalle> lista = new ArrayList<>();
        String sql = "SELECT * FROM detalle WHERE id_factura = ?";
        
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idFactura);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Detalle d = new Detalle();
                    d.setId(rs.getInt("id_detalle"));
                    d.setFacturaId(rs.getInt("id_factura"));
                    d.setProductoId(rs.getInt("id_producto"));
                    d.setCantidad(rs.getInt("cantidad"));
                    d.setSubtotalLinea(rs.getDouble("subtotal_linea"));
                    lista.add(d);
                }
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }
}