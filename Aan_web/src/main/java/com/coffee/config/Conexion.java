/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coffee.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    
    // Configuración de la base de datos
    private static final String DATABASE = "annweb";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DATABASE + "?serverTimezone=America/Bogota";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Cambia si tienes contraseña en MySQL
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    public static Connection conectar() {
        Connection conexion = null;
        try {
            // Cargar el driver en memoria
            Class.forName(DRIVER);
            // Establecer el puente
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa a la base de datos: " + DATABASE);
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontró el Driver de MySQL.");
        } catch (SQLException e) {
            System.out.println("Error: No se pudo conectar a la base de datos.");
        }
        return conexion;
    }
}