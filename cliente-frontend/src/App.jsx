import React, { useState, useEffect } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';
import {
  FaCoffee, FaUser, FaBox, FaFileInvoiceDollar,
  FaChartLine, FaSignOutAlt, FaEdit, FaTrash, FaPlus, FaSave
} from 'react-icons/fa';

const API_BASE = 'http://localhost:8080/Aan_web';

const App = () => {
  const [activeTab, setActiveTab] = useState('facturacion');
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [user, setUser] = useState({ name: 'Admin Coffee', role: 'Administrador', email: 'admin@coffee.com' });
  const [credentials, setCredentials] = useState({ username: '', password: '' });

  // Estados de Datos
  const [productos, setProductos] = useState([]);
  const [clientes, setClientes] = useState([]);
  const [carrito, setCarrito] = useState([]);
  const [reporte, setReporte] = useState({ conteo: 0, ingresos: 0 });

  // Estados para Selección y Formularios
  const [clienteSel, setClienteSel] = useState('');
  const [metodoPago, setMetodoPago] = useState('Efectivo');
  const [clienteForm, setClienteForm] = useState({ id: '', identificacion: '', nombres: '', email: '', direccion: '', ciudad: '', telefono: '' });
  const [productoForm, setProductoForm] = useState({ id: '', nombre: '', precioUnitario: '', stock: '' });

  // --- LÓGICA DE AUTENTICACION ---
  const handleLogin = (e) => {
    e.preventDefault();
    // Simulación de validación
    if (credentials.username === 'admin' && credentials.password === '1234') {
      setIsLoggedIn(true);
      setCredentials({ username: '', password: '' });
    } else {
      alert("Credenciales incorrectas (Pista: admin / 1234)");
    }
  };

  const handleLogout = () => {
    if (window.confirm("¿Desea cerrar la sesión?")) {
      setIsLoggedIn(false);
      setActiveTab('facturacion');
    }
  };

  // --- CARGA DE DATOS (Optimizado con validación) ---
  const cargarTodo = async () => {
    console.log("--- Iniciando Carga de Datos ---");
    try {
      console.log(`Petición: GET ${API_BASE}/ProductoServlet`);
      const resP = await fetch(`${API_BASE}/ProductoServlet`);
      if (resP.ok) {
        const dataP = await resP.json();
        console.log("Productos cargados:", dataP);
        setProductos(dataP);
      } else {
        console.error("Error cargando productos:", resP.status, resP.statusText);
      }

      console.log(`Petición: GET ${API_BASE}/ClienteServlet`);
      const resC = await fetch(`${API_BASE}/ClienteServlet`);
      if (resC.ok) {
        const dataC = await resC.json();
        console.log("Clientes cargados:", dataC);
        setClientes(dataC);
      } else {
        console.error("Error cargando clientes:", resC.status, resC.statusText);
      }

      console.log(`Petición: GET ${API_BASE}/ReporteServlet`);
      const resR = await fetch(`${API_BASE}/ReporteServlet`);
      if (resR.ok) {
        const dataR = await resR.json();
        console.log("Reportes cargados:", dataR);
        setReporte(dataR);
      } else {
        console.error("Error cargando reportes:", resR.status, resR.statusText);
      }
    } catch (error) {
      console.error("Error crítico conectando al servidor:", error);
      alert(`Error de conexión: ${error.message}. Verifique que el servidor backend esté corriendo en ${API_BASE}`);
    }
  };

  useEffect(() => { if (isLoggedIn) cargarTodo(); }, [isLoggedIn]);

  // --- LÓGICA DE FACTURACIÓN ---
  const agregarAlCarrito = (e) => {
    e.preventDefault();
    const idProd = e.target.producto.value;
    const cant = parseInt(e.target.unidades.value);
    const prod = productos.find(p => (p.id || p.id_productos) === parseInt(idProd));
    if (prod && cant > 0) {
      setCarrito([...carrito, { ...prod, cantidad: cant, subtotal: (prod.precioUnitario || prod.precio_unitario) * cant }]);
    }
  };

  const finalizarVenta = () => {
    if (!clienteSel || carrito.length === 0) return alert("Complete los datos de la venta");
    const ventaDTO = {
      idCliente: clienteSel,
      total: carrito.reduce((a, b) => a + b.subtotal, 0),
      metodoPago: metodoPago,
      items: carrito.map(i => ({
        idProducto: i.id || i.id_productos,
        cantidad: i.cantidad,
        precio: i.precioUnitario || i.precio_unitario
      }))
    };

    console.log("--- Finalizando Venta ---");
    console.log("Enviando JSON a FacturaServlet:", ventaDTO);

    fetch(`${API_BASE}/FacturaServlet`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(ventaDTO)
    })
      .then(async res => {
        console.log(`Respuesta FacturaServlet: ${res.status}`);
        if (res.ok) {
          alert("¡Venta Exitosa!");
          setCarrito([]);
          cargarTodo();
        } else {
          const errorText = await res.text();
          console.error("Error en FacturaServlet:", errorText);
          alert(`Error al finalizar venta: ${res.status} - ${errorText}`);
        }
      })
      .catch(err => {
        console.error("Error de red en FacturaServlet:", err);
        alert(`Error de red al facturar: ${err.message}`);
      });
  };

  // --- CRUD CLIENTES ---
  const guardarCliente = (e) => {
    e.preventDefault();
    const params = new URLSearchParams();
    // No enviar ID si es una creación nueva (id vacío o 0)
    if (clienteForm.id && clienteForm.id !== '0') {
      params.append('id', clienteForm.id);
    }
    params.append('identificacion', clienteForm.identificacion);
    params.append('nombres', clienteForm.nombres);
    params.append('email', clienteForm.email);
    params.append('direccion', clienteForm.direccion);
    params.append('ciudad', clienteForm.ciudad);
    params.append('telefono', clienteForm.telefono);

    console.log("--- Guardando Cliente ---");
    console.log("Parametros enviados a ClienteServlet:", params.toString());

    fetch(`${API_BASE}/ClienteServlet`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      body: params
    })
      .then(async res => {
        console.log(`Respuesta ClienteServlet: ${res.status}`);
        if (res.ok) {
          alert("Operación con Cliente Exitosa");
          setClienteForm({ id: '', identificacion: '', nombres: '', email: '', ciudad: '', telefono: '' });
          cargarTodo();
        } else {
          const errorText = await res.text();
          console.error("Error en ClienteServlet:", errorText);
          alert(`Error al guardar cliente: ${res.status} - ${errorText}`);
        }
      })
      .catch(err => {
        console.error("Error de red en ClienteServlet:", err);
        alert(`Error de red: ${err.message}`);
      });
  };



  // --- CRUD PRODUCTOS ---
  const guardarProducto = (e) => {
    e.preventDefault();
    const params = new URLSearchParams(productoForm);
    console.log("--- Guardando Producto ---");
    console.log("Parametros enviados a ProductoServlet:", params.toString());

    fetch(`${API_BASE}/ProductoServlet`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      body: params
    })
      .then(async res => {
        console.log(`Respuesta ProductoServlet: ${res.status}`);
        if (res.ok) {
          alert("Producto guardado correctamente");
          setProductoForm({ id: '', nombre: '', precioUnitario: '', stock: '' });
          cargarTodo();
        } else {
          const errorText = await res.text();
          console.error("Error en ProductoServlet:", errorText);
          alert(`Error al guardar producto: ${res.status} - ${errorText}`);
        }
      })
      .catch(err => {
        console.error("Error de red en ProductoServlet:", err);
        alert(`Error de red: ${err.message}`);
      });
  };

  const eliminarProducto = (id) => {
    if (window.confirm("¿Desea eliminar este producto?")) {
      const params = new URLSearchParams({ id: id, action: 'delete' });
      console.log("--- Eliminando Producto ---");
      console.log("Enviando delete a ProductoServlet con ID:", id);

      fetch(`${API_BASE}/ProductoServlet`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: params
      })
        .then(async res => {
          console.log(`Respuesta ProductoServlet (Delete): ${res.status}`);
          if (res.ok) {
            alert("Producto eliminado");
            cargarTodo();
          } else {
            const errorText = await res.text();
            console.error("Error eliminando producto:", errorText);
            alert(`Error al eliminar: ${res.status} - ${errorText}`);
          }
        })
        .catch(err => {
          console.error("Error de red al eliminar producto:", err);
          alert(`Error de red: ${err.message}`);
        });
    }
  };

  const eliminarCliente = (id) => {
    if (window.confirm("¿Desea eliminar este cliente?")) {
      const params = new URLSearchParams({ id: id, action: 'delete' });
      console.log("--- Eliminando Cliente ---");
      console.log("Enviando delete a ClienteServlet con ID:", id);

      fetch(`${API_BASE}/ClienteServlet`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
        body: params
      })
        .then(async res => {
          console.log(`Respuesta ClienteServlet (Delete): ${res.status}`);
          if (res.ok) {
            alert("Cliente eliminado");
            cargarTodo();
          } else {
            const errorText = await res.text();
            console.error("Error eliminando cliente:", errorText);
            alert(`Error al eliminar cliente: ${res.status} - ${errorText}`);
          }
        })
        .catch(err => {
          console.error("Error de red al eliminar cliente:", err);
          alert(`Error de red: ${err.message}`);
        });
    }
  };

  const quitarDelCarrito = (index) => {
    const nuevoCarrito = [...carrito];
    nuevoCarrito.splice(index, 1);
    setCarrito(nuevoCarrito);
  };

  if (!isLoggedIn) return (
    <div className="min-vh-100 d-flex align-items-center justify-content-center bg-dark p-3">
      <div className="card shadow-lg p-4 w-100" style={{ maxWidth: '400px' }}>
        <div className="text-center mb-4">
          <div className="bg-primary bg-opacity-10 p-4 rounded-circle d-inline-block mb-3">
            <FaCoffee size={50} className="text-primary" />
          </div>
          <h2 className="fw-bold">AAN WEB</h2>
          <p className="text-muted">Inicie sesión para continuar</p>
        </div>
        <form onSubmit={handleLogin}>
          <div className="mb-3">
            <label className="form-label fw-bold small">USUARIO</label>
            <input
              type="text"
              className="form-control form-control-lg"
              placeholder="Ingrese usuario"
              value={credentials.username}
              onChange={e => setCredentials({ ...credentials, username: e.target.value })}
              required
            />
          </div>
          <div className="mb-3">
            <label className="form-label fw-bold small">CONTRASEÑA</label>
            <input
              type="password"
              className="form-control form-control-lg"
              placeholder="********"
              value={credentials.password}
              onChange={e => setCredentials({ ...credentials, password: e.target.value })}
              required
            />
          </div>
          <button type="submit" className="btn btn-primary btn-lg w-100 mt-3 shadow-sm">INGRESAR</button>
        </form>
      </div>
    </div>
  );

  return (
    <div className="min-vh-100 bg-light">
      <nav className="navbar navbar-expand-lg navbar-dark bg-dark shadow sticky-top">
        <div className="container-fluid">
          <span className="navbar-brand fw-bold d-flex align-items-center">
            <FaCoffee className="me-2 text-warning" /> ANN WEB
          </span>
          <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span className="navbar-toggler-icon"></span>
          </button>
          <div className="collapse navbar-collapse" id="navbarNav">
            <ul className="navbar-nav me-auto">
              {['facturacion', 'inventario', 'clientes', 'reportes', 'perfil'].map(tab => (
                <li className="nav-item" key={tab}>
                  <button className={`nav-link btn border-0 text-capitalize ${activeTab === tab ? 'active text-warning' : ''}`} onClick={() => setActiveTab(tab)}>{tab}</button>
                </li>
              ))}
            </ul>
            <div className="d-flex gap-2 align-items-center">
              <span className="text-white me-2 d-none d-md-block small">{user.name}</span>
              <button className="btn btn-sm btn-danger shadow-sm" onClick={handleLogout}><FaSignOutAlt className="me-1" /> Salir</button>
            </div>
          </div>
        </div>
      </nav>

      <div className="container py-4">
        {/* FACTURACIÓN */}
        {activeTab === 'facturacion' && (
          <div className="row g-4">
            <div className="col-12 col-lg-7">
              <div className="card shadow-sm p-4 border-0">
                <h4 className="fw-bold mb-4 border-bottom pb-2">NUEVA VENTA</h4>
                <form onSubmit={agregarAlCarrito} className="row g-3">
                  <div className="col-12">
                    <label className="form-label small fw-bold">CLIENTE</label>
                    <select className="form-select" onChange={(e) => setClienteSel(e.target.value)} required>
                      <option value="">Seleccione...</option>
                      {clientes.map(c => <option key={c.id} value={c.id}>{c.nombres}</option>)}
                    </select>
                  </div>
                  <div className="col-md-6">
                    <label className="form-label small fw-bold">PRODUCTO</label>
                    <select name="producto" className="form-select">
                      {productos.map(p => <option key={p.id || p.id_productos} value={p.id || p.id_productos}>{p.nombre} (${p.precioUnitario || p.precio_unitario})</option>)}
                    </select>
                  </div>
                  <div className="col-md-3">
                    <label className="form-label small fw-bold">CANTIDAD</label>
                    <input name="unidades" type="number" className="form-control" defaultValue="1" min="1" />
                  </div>
                  <div className="col-md-3 d-flex align-items-end">
                    <button type="submit" className="btn btn-dark w-100"><FaPlus /> AÑADIR</button>
                  </div>
                </form>
                <div className="table-responsive mt-4 border rounded bg-white">
                  <table className="table table-hover mb-0">
                    <thead className="table-dark"><tr><th>Item</th><th>Cant</th><th>Subtotal</th><th></th></tr></thead>
                    <tbody>
                      {carrito.map((item, i) => (
                        <tr key={i} className="align-middle">
                          <td>{item.nombre}</td>
                          <td>{item.cantidad}</td>
                          <td>${item.subtotal}</td>
                          <td className="text-end">
                            <button className="btn btn-sm btn-outline-danger border-0" onClick={() => quitarDelCarrito(i)}><FaTrash /></button>
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
            <div className="col-12 col-lg-5">
              <div className="card shadow-sm p-4 border-0">
                <h5>Resumen</h5>
                <select className="form-select mb-3" onChange={(e) => setMetodoPago(e.target.value)}>
                  <option value="Efectivo">Efectivo</option>
                  <option value="Tarjeta">Tarjeta</option>
                </select>
                <div className="selling-price mb-4">
                  ${carrito.reduce((a, b) => a + b.subtotal, 0)}
                </div>
                <button
                  className="btn btn-primary w-100 btn-lg fw-bold shadow-sm"
                  onClick={finalizarVenta}
                  disabled={carrito.length === 0}
                >
                  <FaFileInvoiceDollar className="me-2" /> FINALIZAR VENTA
                </button>
              </div>
            </div>
          </div>
        )}

        {/* INVENTARIO (CRUD PRODUCTOS) */}
        {activeTab === 'inventario' && (
          <div className="card shadow p-4 border-0">
            <div className="d-flex justify-content-between align-items-center mb-4">
              <h3 className="fw-bold m-0 text-primary">Inventario de Productos</h3>
              <button className="btn btn-primary shadow-sm" data-bs-toggle="modal" data-bs-target="#modalProd" onClick={() => setProductoForm({ id: '', nombre: '', precioUnitario: '', stock: '' })}><FaPlus className="me-2" /> Nuevo Producto</button>
            </div>
            <div className="table-responsive">
              <table className="table table-hover align-middle">
                <thead><tr><th>Nombre</th><th>Precio</th><th>Stock</th><th className="text-end">Acciones</th></tr></thead>
                <tbody>
                  {productos.map(p => (
                    <tr key={p.id || p.id_productos}>
                      <td className="fw-semibold">{p.nombre}</td>
                      <td>${p.precioUnitario || p.precio_unitario}</td>
                      <td><span className={`badge ${p.stock < 5 ? 'bg-danger' : 'bg-success shadow-sm'}`}>{p.stock} Unid.</span></td>
                      <td className="text-end">
                        <button className="btn btn-sm btn-outline-primary me-2 border-0" onClick={() => { setProductoForm(p) }} data-bs-toggle="modal" data-bs-target="#modalProd"><FaEdit /></button>
                        <button className="btn btn-sm btn-outline-danger border-0" onClick={() => eliminarProducto(p.id || p.id_productos)}><FaTrash /></button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        )}

        {/* CLIENTES (CRUD CLIENTES) */}
        {activeTab === 'clientes' && (
          <div className="card shadow p-4 border-0">
            <div className="d-flex justify-content-between align-items-center mb-4">
              <h3 className="fw-bold m-0 text-primary">Gestión de Clientes</h3>
              <button className="btn btn-primary shadow-sm" data-bs-toggle="modal" data-bs-target="#modalCli" onClick={() => setClienteForm({ id: '', identificacion: '', nombres: '', email: '', direccion: '', ciudad: '', telefono: '' })}><FaPlus className="me-2" /> Registrar Cliente</button>
            </div>
            <div className="table-responsive">
              <table className="table table-hover align-middle">
                <thead><tr><th>Identificación</th><th>Nombre</th><th>Email</th><th className="text-end">Acciones</th></tr></thead>
                <tbody>
                  {clientes.map(c => (
                    <tr key={c.id}>
                      <td className="fw-bold">{c.identificacion}</td>
                      <td>{c.nombres}</td>
                      <td><a href={`mailto:${c.email}`} className="text-decoration-none">{c.email}</a></td>
                      <td className="text-end">
                        <button className="btn btn-sm btn-outline-primary me-2 border-0" onClick={() => { setClienteForm(c) }} data-bs-toggle="modal" data-bs-target="#modalCli"><FaEdit /></button>
                        <button className="btn btn-sm btn-outline-danger border-0" onClick={() => eliminarCliente(c.id)}><FaTrash /></button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        )}

        {/* REPORTES */}
        {activeTab === 'reportes' && (
          <div className="row g-4 animation-fadeIn">
            <div className="col-md-6">
              <div className="card shadow p-4 border-0 text-center">
                <div className="bg-primary bg-opacity-10 p-3 rounded-circle d-inline-block mb-3 mx-auto">
                  <FaFileInvoiceDollar size={40} className="text-primary" />
                </div>
                <p className="mb-1 text-gray fw-semibold">Ventas Registradas Hoy</p>
                <h2 className="fw-bold display-5">{reporte.conteo}</h2>
              </div>
            </div>
            <div className="col-md-6">
              <div className="card shadow p-4 border-0 text-center">
                <div className="bg-success bg-opacity-10 p-3 rounded-circle d-inline-block mb-3 mx-auto">
                  <FaChartLine size={40} className="text-success" />
                </div>
                <p className="mb-1 text-gray fw-semibold">Ingresos Totales Hoy</p>
                <h2 className="fw-bold text-success display-5">${reporte.ingresos}</h2>
              </div>
            </div>
          </div>
        )}

        {/* PERFIL */}
        {activeTab === 'perfil' && (
          <div className="row justify-content-center">
            <div className="col-12 col-md-8 col-lg-6">
              <div className="card shadow p-5 border-0 text-center">
                <div className="mx-auto mb-4 bg-primary bg-opacity-10 p-4 rounded-circle" style={{ width: 'fit-content' }}>
                  <FaUser size={60} className="text-primary" />
                </div>
                <h3 className="fw-bold">{user.name}</h3>
                <p className="badge bg-primary px-3 py-2 mb-4">{user.role}</p>
                <hr className="my-4" />
                <div className="text-start mb-4">
                  <div className="mb-3">
                    <label className="text-muted small fw-bold">EMAIL</label>
                    <p className="h6">{user.email}</p>
                  </div>
                  <div className="mb-3">
                    <label className="text-muted small fw-bold">CARGO</label>
                    <p className="h6">{user.role}</p>
                  </div>
                </div>
                <button className="btn btn-outline-primary" onClick={() => alert("Función de edición en desarrollo")}>
                  <FaEdit className="me-2" /> EDITAR PERFIL
                </button>
              </div>
            </div>
          </div>
        )}
      </div>

      {/* MODAL PRODUCTO */}
      <div className="modal fade" id="modalProd" tabIndex="-1">
        <div className="modal-dialog">
          <form className="modal-content" onSubmit={guardarProducto}>
            <div className="modal-header bg-dark text-white"><h5>Gestión de Producto</h5></div>
            <div className="modal-body">
              <input type="text" className="form-control mb-2" placeholder="Nombre" value={productoForm.nombre} required onChange={e => setProductoForm({ ...productoForm, nombre: e.target.value })} />
              <input type="number" className="form-control mb-2" placeholder="Precio" value={productoForm.precioUnitario || productoForm.precio_unitario || ''} required onChange={e => setProductoForm({ ...productoForm, precioUnitario: e.target.value })} />
              <input type="number" className="form-control mb-2" placeholder="Stock" value={productoForm.stock} required onChange={e => setProductoForm({ ...productoForm, stock: e.target.value })} />
            </div>
            <div className="modal-footer"><button className="btn btn-primary w-100" data-bs-dismiss="modal">Guardar</button></div>
          </form>
        </div>
      </div>

      {/* MODAL CLIENTE */}
      <div className="modal fade" id="modalCli" tabIndex="-1">
        <div className="modal-dialog">
          <form className="modal-content" onSubmit={guardarCliente}>
            <div className="modal-header bg-primary text-white"><h5>Datos Cliente</h5></div>
            <div className="modal-body">
              <input type="text" className="form-control mb-2" placeholder="Número de Identificación" value={clienteForm.identificacion} required onChange={e => setClienteForm({ ...clienteForm, identificacion: e.target.value })} />
              <input type="text" className="form-control mb-2" placeholder="Nombres" value={clienteForm.nombres} required onChange={e => setClienteForm({ ...clienteForm, nombres: e.target.value })} />
              <input type="email" className="form-control mb-2" placeholder="Email" value={clienteForm.email} onChange={e => setClienteForm({ ...clienteForm, email: e.target.value })} />
              <input type="text" className="form-control mb-2" placeholder="Dirección" value={clienteForm.direccion} onChange={e => setClienteForm({ ...clienteForm, direccion: e.target.value })} />
              <input type="text" className="form-control mb-2" placeholder="Ciudad" value={clienteForm.ciudad} onChange={e => setClienteForm({ ...clienteForm, ciudad: e.target.value })} />
              <input type="text" className="form-control mb-2" placeholder="Teléfono" value={clienteForm.telefono} onChange={e => setClienteForm({ ...clienteForm, telefono: e.target.value })} />
            </div>
            <div className="modal-footer"><button className="btn btn-success w-100" data-bs-dismiss="modal">Confirmar</button></div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default App;