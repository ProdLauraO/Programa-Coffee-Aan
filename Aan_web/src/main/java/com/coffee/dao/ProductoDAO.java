/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coffee.dao;
import com.coffee.model.Producto;
import com.coffee.config.Conexion; // Ajusta esto a tu clase de conexión
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ProductoDAO {
    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos"; // Asegúrate que el nombre de la tabla sea correcto
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id_productos")); // o id_productos según tu base de datos
                p.setNombre(rs.getString("nombre"));
                p.setPrecioUnitario(rs.getDouble("precio_unitario"));
                p.setStock(rs.getInt("stock"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    public boolean insertar(Producto p) {
        String sql = "INSERT INTO productos (nombre, precio_unitario, stock) VALUES (?,?,?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getPrecioUnitario());
            ps.setInt(3, p.getStock());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean actualizar(Producto p) {
        String sql = "UPDATE productos SET nombre=?, precio_unitario=?, stock=? WHERE id_productos=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getPrecioUnitario());
            ps.setInt(3, p.getStock());
            ps.setInt(4, p.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean eliminar(int id) {
        String sql = "DELETE FROM productos WHERE id_productos=?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}