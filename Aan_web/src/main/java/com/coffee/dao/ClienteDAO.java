/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coffee.dao;

import com.coffee.config.Conexion;
import com.coffee.model.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO que implementa el patrón de persistencia.
 * Maneja las operaciones CRUD sobre la tabla 'cliente'.
 */
public class ClienteDAO {

    public List<Cliente> listar() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id_cliente"));
                c.setIdentificacion(rs.getString("identificacion"));
                c.setNombres(rs.getString("nombres"));
                c.setDireccion(rs.getString("direccion"));
                c.setCiudad(rs.getString("ciudad"));
                c.setTelefono(rs.getString("telefono"));
                c.setEmail(rs.getString("email"));
                lista.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Error en listar: " + e.getMessage());
        }
        return lista;
    }

    public boolean insertar(Cliente c) {
        // SQL con los 6 campos requeridos
        String sql = "INSERT INTO cliente (identificacion, nombres, email, direccion, ciudad, telefono) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection con = Conexion.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, c.getIdentificacion());
            ps.setString(2, c.getNombres());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getDireccion());
            ps.setString(5, c.getCiudad());
            ps.setString(6, c.getTelefono());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Error en insertar DAO: " + e.getMessage());
            return false;
        }
    }
}