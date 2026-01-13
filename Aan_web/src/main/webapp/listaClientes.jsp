<%-- 
    Document   : lista
    Created on : 12/01/2026, 7:12:43 p. m.
    Author     : USUARIO
--%>

<%@page import="java.sql.ResultSet"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Coffee Ann - Listado</title>
        <style>
            table {
                width: 50%;
                border-collapse: collapse;
            }
            th, td {
                border: 1px solid black;
                padding: 8px;
                text-align: left;
            }
        </style>
    </head>
    <body>
        <h2>Lista de Clientes Registrados</h2>
        <table>
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Email</th>
            </tr>
            <%
                // Obtenemos el ResultSet que envió el Servlet
                ResultSet rs = (ResultSet) request.getAttribute("resultadoSQL");
                while (rs != null && rs.next()) {
            %>
            <tr>
                <td><%= rs.getInt("id_cliente")%></td>
                <td><%= rs.getString("nombres")%></td>
                <td><%= rs.getString("email")%></td
                <td><%= rs.getString("direccion")%></td>
            </tr>
            <% }%>
        </table>
        <br>
        <a href="index.jsp">Volver al Formulario</a>
    </body>
</html>
