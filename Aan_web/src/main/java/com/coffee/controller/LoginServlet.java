/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.coffee.controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;

// Definición del endpoint para el servicio de autenticación
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Configuración de cabeceras para respuesta JSON y soporte de CORS
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        // Captura de credenciales enviadas desde el cliente
        String user = request.getParameter("usuario");
        String pass = request.getParameter("password");

        // Objeto para construir la respuesta estructurada
        Map<String, String> respuesta = new HashMap<>();

        // Lógica de validación (En un entorno real, aquí se consultaría a la Base de Datos)
        // Ejemplo simplificado: usuario 'admin' y clave '1234'
        if ("admin".equals(user) && "1234".equals(pass)) {
            response.setStatus(HttpServletResponse.SC_OK); // Estado 200
            respuesta.put("status", "success");
            respuesta.put("message", "Autenticación satisfactoria");
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Estado 401
            respuesta.put("status", "error");
            respuesta.put("message", "Error en la autenticación");
        }

        // Conversión del mapa de respuesta a formato JSON y envío al cliente
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(respuesta));
        out.flush();
    }
}