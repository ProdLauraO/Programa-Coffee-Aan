/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.coffee.main;

import com.coffee.dao.ClienteDAO;
import com.coffee.model.Cliente;
import java.util.List;
import java.util.Scanner;

/**
 * Aplicación de consola para la gestión de clientes (Módulo Stand-alone).
 * Permite interactuar con la base de datos sin usar un navegador web.
 */
public class MainApp {

    public static void main(String[] args) {
        // Inicialización de componentes
        ClienteDAO dao = new ClienteDAO();
        Scanner leer = new Scanner(System.in);
        int opcion = 0;

        do {
            System.out.println("\n========================================");
            System.out.println("   SISTEMA COFFEE AAN - MÓDULO CONSOLA  ");
            System.out.println("========================================");
            System.out.println("1. Registrar nuevo cliente");
            System.out.println("2. Listar todos los clientes");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            
            try {
                opcion = Integer.parseInt(leer.nextLine());

                switch (opcion) {
                    case 1 -> {
                        System.out.println("\n--- REGISTRO DE NUEVO CLIENTE ---");
                        System.out.print("Identificación: ");
                        String id = leer.nextLine();
                        System.out.print("Nombre Completo: ");
                        String nom = leer.nextLine();
                        System.out.print("Email: ");
                        String mail = leer.nextLine();
                        System.out.print("Dirección: ");
                        String dir = leer.nextLine();
                        System.out.print("Ciudad: ");
                        String ciu = leer.nextLine();
                        System.out.print("Teléfono: ");
                        String tel = leer.nextLine();

                        // Creación del objeto con los 6 parámetros
                        Cliente nuevo = new Cliente(id, nom, mail, dir, ciu, tel);
                        
                        // Intento de inserción
                        if (dao.insertar(nuevo)) {
                            System.out.println("\n[ÉXITO] Cliente guardado correctamente en la base de datos.");
                        } else {
                            System.out.println("\n[ERROR] No se pudo registrar el cliente. Revise la conexión.");
                        }
                    }

                    case 2 -> {
                        System.out.println("\n--- LISTADO DE CLIENTES REGISTRADOS ---");
                        List<Cliente> lista = dao.listar();
                        
                        if (lista.isEmpty()) {
                            System.out.println("No hay clientes en la base de datos.");
                        } else {
                            // Cabecera de tabla simple
                            System.out.printf("%-5s | %-15s | %-20s | %-15s | %-10s\n", "ID", "IDENTIFICACIÓN", "NOMBRE", "CIUDAD", "TELÉFONO");
                            System.out.println("--------------------------------------------------------------------------------");
                            for (Cliente c : lista) {
                                System.out.printf("%-5d | %-15s | %-20s | %-15s | %-10s\n",
                                        c.getId(), c.getIdentificacion(), c.getNombres(), c.getCiudad(), c.getTelefono());
                            }
                        }
                    }

                    case 3 -> System.out.println("Cerrando el sistema... ¡Gracias!");

                    default -> System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor, ingrese un número válido.");
            }

        } while (opcion != 3);
        
        leer.close();
    }
}
