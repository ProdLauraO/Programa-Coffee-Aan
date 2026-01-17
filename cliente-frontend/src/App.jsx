import React, { useEffect, useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';

function App() {
  // --- ESTADOS PARA AUTENTICACIÓN ---
  const [usuario, setUsuario] = useState('');
  const [password, setPassword] = useState('');
  const [autenticado, setAutenticado] = useState(false);

  // --- ESTADOS PARA GESTIÓN DE CLIENTES ---
  const [clientes, setClientes] = useState([]);
  const [formData, setFormData] = useState({
    identificacion: '', nombres: '', email: '', telefono: '', direccion: '', ciudad: ''
  });

  // URLs de tus Servlets en NetBeans
  const URL_CLIENTES = 'http://localhost:8080/Aan_web/ClienteServlet';
  const URL_LOGIN = 'http://localhost:8080/Aan_web/LoginServlet';

  // --- 1. FUNCIÓN DE LOGIN (POST a LoginServlet) ---
  const manejarLogin = (e) => {
    e.preventDefault();
    const params = new URLSearchParams();
    params.append('usuario', usuario);
    params.append('password', password);

    fetch(URL_LOGIN, {
      method: 'POST',
      body: params,
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
    })
    .then(res => {
      if (!res.ok) throw new Error("Credenciales inválidas");
      return res.json();
    })
    .then(data => {
      alert(data.message); // "Autenticación satisfactoria"
      setAutenticado(true);
    })
    .catch(err => alert("Error: Usuario o contraseña incorrectos"));
  };

  // --- 2. FUNCIÓN PARA LISTAR CLIENTES (GET) ---
  const cargarClientes = () => {
    fetch(URL_CLIENTES)
      .then(res => res.json())
      .then(data => setClientes(data))
      .catch(err => console.error("Error al cargar clientes:", err));
  };

  // Cargar datos solo cuando el usuario se autentique
  useEffect(() => {
    if (autenticado) cargarClientes();
  }, [autenticado]);

  // --- 3. FUNCIÓN PARA GUARDAR CLIENTE (POST) ---
  const manejarGuardado = (e) => {
    e.preventDefault();
    const params = new URLSearchParams(formData);

    fetch(URL_CLIENTES, {
      method: 'POST',
      body: params,
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' }
    })
    .then(res => {
      if (res.ok) {
        alert("Cliente guardado con éxito");
        setFormData({ identificacion: '', nombres: '', email: '', telefono: '', direccion: '', ciudad: '' });
        cargarClientes();
      }
    })
    .catch(err => console.error("Error al guardar:", err));
  };

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  // --- VISTA 1: FORMULARIO DE INICIO DE SESIÓN ---
  if (!autenticado) {
    return (
      <div className="container mt-5 d-flex justify-content-center">
        <div className="card p-4 shadow-lg" style={{ width: '400px' }}>
          <div className="text-center mb-4">
            <h2 className="fw-bold text-primary">Coffee Aan</h2>
            <p className="text-muted">Inicie sesión para continuar</p>
          </div>
          <form onSubmit={manejarLogin}>
            <div className="mb-3">
              <label className="form-label fw-bold">Usuario</label>
              <input type="text" className="form-control" 
                onChange={(e) => setUsuario(e.target.value)} required />
            </div>
            <div className="mb-4">
              <label className="form-label fw-bold">Contraseña</label>
              <input type="password" className="form-control" 
                onChange={(e) => setPassword(e.target.value)} required />
            </div>
            <button type="submit" className="btn btn-primary w-100 py-2 shadow-sm">
              Ingresar al Sistema
            </button>
          </form>
        </div>
      </div>
    );
  }

  // --- VISTA 2: PANEL DE CONTROL (REGISTRO Y TABLA) ---
  return (
    <div className="container mt-4 mb-5">
      <div className="d-flex justify-content-between align-items-center mb-4 pb-2 border-bottom">
        <h1 className="h3 text-primary fw-bold">Panel de Gestión - Coffee Aan</h1>
        <button className="btn btn-outline-danger btn-sm" onClick={() => setAutenticado(false)}>
          Cerrar Sesión
        </button>
      </div>

      <div className="row">
        {/* Formulario de Registro */}
        <div className="col-md-5 mb-4">
          <div className="card p-4 shadow-sm border-0 bg-light">
            <h4 className="mb-3">Registro de Clientes</h4>
            <form onSubmit={manejarGuardado}>
              <div className="row mb-3">
                <div className="col">
                  <label className="form-label">Identificación</label>
                  <input type="text" name="identificacion" value={formData.identificacion} 
                    onChange={handleChange} className="form-control" required />
                </div>
                <div className="col">
                  <label className="form-label">Nombre</label>
                  <input type="text" name="nombres" value={formData.nombres} 
                    onChange={handleChange} className="form-control" required />
                </div>
              </div>
              <div className="mb-3">
                <label className="form-label">Email</label>
                <input type="email" name="email" value={formData.email} 
                  onChange={handleChange} className="form-control" required />
              </div>
              <div className="row mb-3">
                <div className="col">
                  <label className="form-label">Ciudad</label>
                  <input type="text" name="ciudad" value={formData.ciudad} 
                    onChange={handleChange} className="form-control" required />
                </div>
                <div className="col">
                  <label className="form-label">Teléfono</label>
                  <input type="text" name="telefono" value={formData.telefono} 
                    onChange={handleChange} className="form-control" required />
                </div>
              </div>
              <button type="submit" className="btn btn-success w-100">Guardar Datos</button>
            </form>
          </div>
        </div>

        {/* Tabla de Resultados */}
        <div className="col-md-7">
          <h4 className="mb-3">Listado General</h4>
          <div className="table-responsive shadow-sm rounded">
            <table className="table table-hover bg-white border">
              <thead className="table-dark">
                <tr>
                  <th>ID</th>
                  <th>Nombre</th>
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