import React, { useEffect, useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
  // Estados para la lista y para el formulario
  const [clientes, setClientes] = useState([]);
  const [formData, setFormData] = useState({
    identificacion: '', nombres: '', email: '', telefono: '', direccion: '', ciudad: ''
  });

  const url = 'http://localhost:8080/Aan_web/ClienteServlet';

  // 1. Función para listar (GET)
  const cargarClientes = () => {
    fetch(url)
      .then(res => res.json())
      .then(data => setClientes(data))
      .catch(err => console.error("Error al cargar:", err));
  };

  useEffect(() => { cargarClientes(); }, []);

  // 2. Manejar cambios en el formulario
  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  // 3. Función para guardar (POST)
  const handleSubmit = (e) => {
    e.preventDefault();
    
    const params = new URLSearchParams(formData);

    fetch(url, {
      method: 'POST',
      body: params,
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
    })
    .then(res => {
      if (res.ok) {
        alert("¡Cliente guardado con éxito!");
        setFormData({ identificacion: '', nombres: '', email: '', telefono: '', direccion: '', ciudad: '' });
        cargarClientes(); // Recargar la tabla automáticamente
      }
    })
    .catch(err => console.error("Error al guardar:", err));
  };

  return (
    <div className="container mt-4 mb-5">
      <div className="row">
        {/* COLUMNA DEL FORMULARIO*/}
        <div className="col-md-5">
          <h2 className="mb-4">Registro de Clientes</h2>
          <form onSubmit={handleSubmit} className="card p-4 shadow border-0">
            <div className="row">
              <div className="col-md-6 mb-3">
                <label className="form-label fw-bold">Identificación:</label>
                <input type="text" name="identificacion" value={formData.identificacion} onChange={handleChange} 
                       className="form-control" pattern="[0-9]+" title="Solo números" required />
              </div>
              <div className="col-md-6 mb-3">
                <label className="form-label fw-bold">Nombre Completo:</label>
                <input type="text" name="nombres" value={formData.nombres} onChange={handleChange} 
                       className="form-control" minLength="3" required />
              </div>
            </div>

            <div className="row">
              <div className="col-md-6 mb-3">
                <label className="form-label fw-bold">Email:</label>
                <input type="email" name="email" value={formData.email} onChange={handleChange} 
                       className="form-control" required />
              </div>
              <div className="col-md-6 mb-3">
                <label className="form-label fw-bold">Teléfono:</label>
                <input type="text" name="telefono" value={formData.telefono} onChange={handleChange} 
                       className="form-control" pattern="[0-9]+" required />
              </div>
            </div>

            <div className="mb-3">
              <label className="form-label fw-bold">Dirección:</label>
              <input type="text" name="direccion" value={formData.direccion} onChange={handleChange} 
                     className="form-control" required />
            </div>

            <div className="mb-3">
              <label className="form-label fw-bold">Ciudad:</label>
              <input type="text" name="ciudad" value={formData.ciudad} onChange={handleChange} 
                     className="form-control" required />
            </div>

            <button type="submit" className="btn btn-success w-100 fw-bold shadow-sm">
               Guardar Cliente
            </button>
          </form>
        </div>

        {/* COLUMNA DE LA TABLA*/}
        <div className="col-md-7">
          <h2 className="mb-4 text-secondary">Clientes Registrados</h2>
          <div className="table-responsive shadow rounded">
            <table className="table table-hover bg-white mb-0">
              <thead className="table-dark">
                <tr>
                  <th>ID</th>
                  <th>Nombres</th>
                  <th>Email</th>
                  <th>Ciudad</th>
                </tr>
              </thead>
              <tbody>
                {clientes.map((c, i) => (
                  <tr key={i}>
                    <td>{c.identificacion}</td>
                    <td>{c.nombres}</td>
                    <td>{c.email}</td>
                    <td>{c.ciudad}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
}

export default App;