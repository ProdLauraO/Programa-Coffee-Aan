/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.coffee.dao;

import com.coffee.config.Conexion;
import com.coffee.model.VentaDTO;
import java.sql.*;

public class FacturaDAO {
    public boolean procesarVentaCompleta(VentaDTO venta) {
        Connection con = null;
        try {
            con = Conexion.getConexion();
            con.setAutoCommit(false); // Iniciamos Transacción

            // 1. Insertar Cabecera de Factura
            String sqlFactura = "INSERT INTO factura (cliente_id_cliente, numero_factura, fecha, total_neto, estado_factura) VALUES (?, ?, NOW(), ?, 'PAGADA')";
            PreparedStatement psF = con.prepareStatement(sqlFactura, Statement.RETURN_GENERATED_KEYS);
            psF.setInt(1, Integer.parseInt(venta.getIdCliente()));
            psF.setString(2, "FAC-" + System.currentTimeMillis()); // Genera un número único
            psF.setDouble(3, venta.getTotal());
            psF.executeUpdate();

            ResultSet rs = psF.getGeneratedKeys();
            int idFactura = rs.next() ? rs.getInt(1) : 0;

            // 2. Insertar Detalle de Productos
            String sqlDetalle = "INSERT INTO detalle (id_factura, id_producto, cantidad, precio_unitario_venta, subtotal_linea) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement psD = con.prepareStatement(sqlDetalle);
            for (VentaDTO.ItemCarrito item : venta.getItems()) {
                psD.setInt(1, idFactura);
                psD.setInt(2, item.idProducto);
                psD.setInt(3, item.cantidad);
                psD.setDouble(4, item.precio);
                psD.setDouble(5, item.cantidad * item.precio);
                psD.addBatch();
            }
            psD.executeBatch();

            // 3. Registrar el Pago
            String sqlPago = "INSERT INTO pago (id_factura, fecha, monto, metodo_pago) VALUES (?, NOW(), ?, ?)";
            PreparedStatement psP = con.prepareStatement(sqlPago);
            psP.setInt(1, idFactura);
            psP.setDouble(2, venta.getTotal());
            psP.setString(3, venta.getMetodoPago());
            psP.executeUpdate();

            con.commit(); // Éxito: Guardar todo
            return true;
        } catch (SQLException e) {
            try { if (con != null) con.rollback(); } catch (SQLException ex) {} // Error: Revertir todo
            e.printStackTrace();
            return false;
        } finally {
            try { if (con != null) con.close(); } catch (SQLException e) {}
        }
    }
}