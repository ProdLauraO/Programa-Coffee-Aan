package com.mycompany.coffee_ann;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Coffee_Ann {

    public static void main(String[] args) {
        String usuario = "root";
        String password = "";
        String url = "jdbc:mysql://localhost:3306/annweb?serverTimezone=America/Bogota";

        System.out.println("Intentando conectar a la base de datos...");

        try (Connection conexion = DriverManager.getConnection(url, usuario, password); Statement statement = conexion.createStatement()) {

            System.out.println("¡Conexión exitosa!");

            // 1. LISTAR INICIAL
            System.out.println("--- Lista Inicial de Clientes ---");
            try (ResultSet rs = statement.executeQuery("SELECT * FROM cliente")) {
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("id_cliente") + " | Nombre: " + rs.getString("nombres"));
                }
            }

            // 2. INSERTAR DATOS (Usando NOW() para la fecha)
            System.out.println("\nInsertando nuevo cliente...");
            statement.execute("""
                INSERT INTO `cliente` (`identificacion`, `nombres`, `direccion`, `ciudad`, `codigo_postal`, `telefono`, `email`, `fecha`) 
                VALUES ('654168', 'juan gomez', 'calle 321', 'medellin', '464', '3165421585', 'jgomez@mail.com', NOW());
                """);

            // 3. ACTUALIZAR DATOS 
            System.out.println("Actualizando cliente con ID 2...");
            statement.execute("UPDATE `cliente` SET `nombres` = 'pepito gomez' WHERE `id_cliente` = 2");

            // 4. BORRAR DATOS 
            System.out.println("Borrando cliente..");
            statement.execute("DELETE FROM `annweb`.`cliente` WHERE  `id_cliente`=8");

            // 5. LISTAR FINAL
            System.out.println("\n--- Lista Final de Clientes ---");
            try (ResultSet rsFinal = statement.executeQuery("SELECT * FROM cliente")) {
                while (rsFinal.next()) {
                    System.out.println("Nombre: " + rsFinal.getString("nombres"));
                }
            }

        } catch (SQLException ex) {
            System.out.println("Error en la operación de base de datos.");
            Logger.getLogger(Coffee_Ann.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
