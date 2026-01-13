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

@WebServlet("/ClienteServlet")
public class ClienteServlet extends HttpServlet {

    private ClienteDAO dao = new ClienteDAO();

    // doGet: Se activa al pedir la página o por enlaces (GET)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("misClientes", dao.listar());
        request.getRequestDispatcher("listaClientes.jsp").forward(request, response);
    }

    // doPost: Se activa al enviar el formulario (POST)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String iden = request.getParameter("identificacion");
        String nom = request.getParameter("nombres");
        String mail = request.getParameter("email");
        String dir = request.getParameter("direccion");

        Cliente nuevoCliente = new Cliente(iden, nom, mail, dir);
        dao.insertar(nuevoCliente);

        // Redirige al GET para mostrar la lista actualizada
        response.sendRedirect("ClienteServlet");
    }
}
