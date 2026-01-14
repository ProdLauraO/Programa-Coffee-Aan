/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.coffee.controller;

import com.coffee.dao.ClienteDAO;
import com.coffee.model.Cliente;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * Servlet que actúa como controlador del flujo web.
 */
@WebServlet("/ClienteServlet")
public class ClienteServlet extends HttpServlet {

    private ClienteDAO dao = new ClienteDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtiene la lista y la envía a la vista JSP
        request.setAttribute("misClientes", dao.listar());
        request.getRequestDispatcher("listaClientes.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Captura de parámetros desde el formulario
        String iden = request.getParameter("identificacion");
        String nom = request.getParameter("nombres");
        String mail = request.getParameter("email");
        String dir = request.getParameter("direccion");
        String ciu = request.getParameter("ciudad");
        String tel = request.getParameter("telefono");

        // Creación del objeto y persistencia
        Cliente nuevoCliente = new Cliente(iden, nom, mail, dir, ciu, tel);
        dao.insertar(nuevoCliente);

        // Redirección para evitar duplicidad de datos al refrescar
        response.sendRedirect("ClienteServlet");
    }
}
