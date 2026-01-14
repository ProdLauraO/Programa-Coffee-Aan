<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Coffee Aan - Registro Profesional</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    </head>
    <body class="container mt-4">
        <h2 class="mb-4">Registro de Clientes</h2>
        <form action="ClienteServlet" method="POST" class="card p-4 shadow">
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label>Identificación:</label>
                    <input type="text" name="identificacion" class="form-control" pattern="[0-9]+" title="Solo números" required>
                </div>
                <div class="col-md-6 mb-3">
                    <label>Nombre Completo:</label>
                    <input type="text" name="nombres" class="form-control" minlength="3" required>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6 mb-3">
                    <label>Email:</label>
                    <input type="email" name="email" class="form-control" required>
                </div>
                <div class="col-md-6 mb-3">
                    <label>Teléfono:</label>
                    <input type="text" name="telefono" class="form-control" pattern="[0-9]+" required>
                </div>
            </div>
            <div class="mb-3">
                <label>Dirección:</label>
                <input type="text" name="direccion" class="form-control" required>
            </div>
            <div class="mb-3">
                <label>Ciudad:</label>
                <input type="text" name="ciudad" class="form-control" required>
            </div>
            <button type="submit" class="btn btn-success w-100">Guardar Cliente</button>
        </form>
        <div class="mt-3">
            <a href="ClienteServlet" class="btn btn-outline-primary">Ir a Lista de Clientes</a>
        </div>
    </body>
</html>
