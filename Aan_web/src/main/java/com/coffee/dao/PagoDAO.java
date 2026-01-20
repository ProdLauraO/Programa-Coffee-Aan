/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coffee.dao;

import com.coffee.config.Conexion;
import com.coffee.model.Pago;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PagoDAO {
    // Consultar todos los pagos realizados (Reporte de ingresos)
    public List<Pago> listarTodos() {
        List<Pago> lista = new ArrayList<>();
        String sql = "SELECT * FROM pago ORDER BY fecha DESC";
        
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Pago p = new Pago();
                p.setId(rs.getInt("id_pago"));
                p.setFacturaId(rs.getInt("id_factura"));
                p.setMonto(rs.getDouble("monto"));
                p.setMetodoPago(rs.getString("metodo_pago"));
                lista.add(p);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return lista;
    }
}