<%-- 
    Document   : lista
    Created on : 12/01/2026, 7:12:43 p. m.
    Author     : USUARIO
--%>

<%@page import="java.util.List"%>
<%@page import="com.coffee.model.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Lista de Clientes</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="container mt-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Clientes Registrados</h2>
        <a href="index.jsp" class="btn btn-primary">+ Nuevo Cliente</a>
    </div>
    <table class="table table-hover border shadow-sm">
        <thead class="table-dark">
            <tr>
                <th>ID</th>
                <th>Identificación</th>
                <th>Nombre</th>
                <th>Ciudad</th>
                <th>Teléfono</th>
                <th>Email</th>
            </tr>
        </thead>
        <tbody>
            <% 
                List<Cliente> lista = (List<Cliente>) request.getAttribute("misClientes");
                if (lista != null && !lista.isEmpty()) {
                    for (Cliente c : lista) {
            %>
            <tr>
                <td><%= c.getId() %></td>
                <td><%= c.getIdentificacion() %></td>
                <td><%= c.getNombres() %></td>
                <td><%= c.getCiudad() %></td>
                <td><%= c.getTelefono() %></td>
                <td><%= c.getEmail() %></td>
            </tr>
            <% 
                    }
                } else {
            %>
            <tr><td colspan="6" class="text-center">No hay clientes registrados.</td></tr>
            <% } %>
        </tbody>
    </table>
</body>
</html>