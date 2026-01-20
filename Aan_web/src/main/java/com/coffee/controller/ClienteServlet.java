/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.coffee.controller;

import com.coffee.dao.ClienteDAO;
import com.coffee.model.Cliente;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.google.gson.Gson;

@WebServlet("/ClienteServlet")
public class ClienteServlet extends HttpServlet {

    private ClienteDAO dao = new ClienteDAO();
    private Gson gson = new Gson();

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Esto le dice al navegador que el Servlet acepta conexiones desde afuera
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Permite que React se conecte
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Obtiene la lista y la convierte a JSON
        List<Cliente> lista = dao.listar();
        String jsonResultado = this.gson.toJson(lista);

        response.getWriter().write(jsonResultado);
    }

    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setCharacterEncoding("UTF-8");
    String action = request.getParameter("action");
    String idString = request.getParameter("id");
    // Lógica para ELIMINAR
    if ("delete".equals(action) && idString != null) {
        dao.eliminar(Integer.parseInt(idString));
        response.getWriter().write("{\"mensaje\": \"Cliente eliminado\"}");
        return;
    }
    // Parámetros para GUARDAR/ACTUALIZAR
    String iden = request.getParameter("identificacion");
    String nom = request.getParameter("nombres");
    String mail = request.getParameter("email");
    String dir = request.getParameter("direccion");
    String ciu = request.getParameter("ciudad");
    String tel = request.getParameter("telefono");
    if (iden != null && nom != null) {
        Cliente c = new Cliente(iden, nom, mail, dir, ciu, tel);
        if (idString != null && !idString.isEmpty() && !idString.equals("0")) {
            c.setId(Integer.parseInt(idString));
            dao.actualizar(c); 
            response.getWriter().write("{\"mensaje\": \"Cliente actualizado\"}");
        } else {
            dao.insertar(c);
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().write("{\"mensaje\": \"Cliente guardado\"}");
        }
    }
}

    @Override
    public void destroy() {
        try {
            com.mysql.cj.jdbc.AbandonedConnectionCleanupThread.checkedShutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
