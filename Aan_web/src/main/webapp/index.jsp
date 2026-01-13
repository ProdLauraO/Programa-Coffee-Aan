<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Coffee Ann - Registro</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    </head>
    <body class="container mt-5">
        <h2>Registro de Clientes</h2>
        <form action="ClienteServlet" method="POST" class="card p-4">
            <div class="mb-3">
                <label>Identificación:</label>
                <input type="text" name="identificacion" class="form-control" required>
            </div>
            <div class="mb-3">
                <label>Nombre Completo:</label>
                <input type="text" name="nombres" class="form-control" required>
            </div>
            <div class="mb-3">
                <label>Email:</label>
                <input type="email" name="email" class="form-control" required>
            </div>
            <div class="mb-3">
                <label>Dirección:</label>
                <input type="direccion" name="direccion" class="form-control" required>
            </div>
            <button type="submit" class="btn btn-primary">Guardar Cliente</button>
        </form>
        
        <br>
        <a href="ClienteServlet" class="btn btn-secondary">Ver Lista de Clientes (GET)</a>
    </body>
</html>
