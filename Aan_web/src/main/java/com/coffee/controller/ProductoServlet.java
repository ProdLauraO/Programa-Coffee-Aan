/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.coffee.controller;

import com.coffee.dao.ProductoDAO;
import com.coffee.model.Producto;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ProductoServlet")
public class ProductoServlet extends HttpServlet {

    private ProductoDAO dao = new ProductoDAO();
    private Gson gson = new Gson();

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        List<Producto> lista = dao.listar();
        response.getWriter().write(this.gson.toJson(lista));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        String idString = request.getParameter("id");
        if ("delete".equals(action) && idString != null) {
            dao.eliminar(Integer.parseInt(idString));
            response.getWriter().write("{\"mensaje\": \"Producto eliminado\"}");
            return;
        }
        String nombre = request.getParameter("nombre");
        String precioStr = request.getParameter("precioUnitario");
        String stockStr = request.getParameter("stock");
        if (nombre != null && precioStr != null) {
            Producto p = new Producto();
            p.setNombre(nombre);
            p.setPrecioUnitario(Double.parseDouble(precioStr));
            p.setStock(Integer.parseInt(stockStr));
            if (idString != null && !idString.isEmpty() && !idString.equals("0")) {
                p.setId(Integer.parseInt(idString));
                dao.actualizar(p);
                response.getWriter().write("{\"mensaje\": \"Producto actualizado\"}");
            } else {
                dao.insertar(p);
                response.setStatus(HttpServletResponse.SC_CREATED);
                response.getWriter().write("{\"mensaje\": \"Producto guardado\"}");
            }
        }
    }
}
