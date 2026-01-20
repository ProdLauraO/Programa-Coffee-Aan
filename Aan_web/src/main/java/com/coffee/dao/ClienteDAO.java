/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coffee.dao;

import com.coffee.model.Cliente;
import com.coffee.config.Conexion; // Ajusta esto a tu clase de conexión
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public List<Cliente> listar() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id_cliente"));
                c.setIdentificacion(rs.getString("identificacion"));
                c.setNombres(rs.getString("nombres"));
                c.setEmail(rs.getString("email"));
                c.setDireccion(rs.getString("direccion"));
                c.setCiudad(rs.getString("ciudad"));
                c.setTelefono(rs.getString("telefono"));
                lista.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean insertar(Cliente c) {
        String sql = "INSERT INTO cliente (identificacion, nombres, email, direccion, ciudad, telefono) VALUES (?,?,?,?,?,?)";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getIdentificacion());
            ps.setString(2, c.getNombres());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getDireccion());
            ps.setString(5, c.getCiudad());
            ps.setString(6, c.getTelefono());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizar(Cliente c) {
        String sql = "UPDATE cliente SET identificacion=?, nombres=?, email=?, direccion=?, ciudad=?, telefono=? WHERE id_cliente=?";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getIdentificacion());
            ps.setString(2, c.getNombres());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getDireccion());
            ps.setString(5, c.getCiudad());
            ps.setString(6, c.getTelefono());
            ps.setInt(7, c.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM cliente WHERE id_cliente=?";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
