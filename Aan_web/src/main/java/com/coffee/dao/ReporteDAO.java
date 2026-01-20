/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coffee.dao;

import com.coffee.config.Conexion;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ReporteDAO {
    public Map<String, Object> obtenerResumenHoy() {
        Map<String, Object> data = new HashMap<>();
        String sql = "SELECT COUNT(*) as total_ventas, SUM(total_neto) as recaudado " +
                     "FROM factura WHERE DATE(fecha) = CURDATE()";
        
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                data.put("conteo", rs.getInt("total_ventas"));
                data.put("ingresos", rs.getDouble("recaudado"));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return data;
    }
}