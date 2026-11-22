package Modelos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class Modelo_Admin {

    // SECCIÓN 1: GESTIÓN DE TÉCNICOS (Compatible 100% con Tecnico.java y Almacen)

    private static class NodoTecnico {
        String[] datos; // 11 columnas exactas definidas en Tecnico.java
        NodoTecnico siguiente;

        NodoTecnico(String[] datos) {
            this.datos = datos;
            this.siguiente = null;
        }
    }

    private NodoTecnico cabezaTecnicos;

    public void cargarTecnicosDesdeTxt() throws IOException {
        Almacen.iniciar();
        cabezaTecnicos = null;
        for (String[] fila : Almacen.leer(Almacen.TECNICOS)) {
            if (fila.length == 11) {
                insertarTecnicoEnLista(fila);
            }
        }
    }

    private void insertarTecnicoEnLista(String[] fila) {
        NodoTecnico nuevo = new NodoTecnico(fila);
        if (cabezaTecnicos == null) {
            cabezaTecnicos = nuevo;
        } else {
            NodoTecnico actual = cabezaTecnicos;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
    }

    private void guardarTecnicosEnTxt() throws IOException {
        List<String[]> lista = new ArrayList<>();
        NodoTecnico actual = cabezaTecnicos;
        while (actual != null) {
            lista.add(actual.datos);
            actual = actual.siguiente;
        }
        Almacen.reescribir(Almacen.TECNICOS, lista);
    }

    public DefaultTableModel listarTecnicos() throws IOException {
        cargarTecnicosDesdeTxt();
        String[] columnas = {
            "ID", "N Documento", "Nombre", "Telefono", "Nivel", "Correo", "Direccion"
        };
        DefaultTableModel modelo = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        NodoTecnico actual = cabezaTecnicos;
        while (actual != null) {
            String[] d = actual.datos;
            modelo.addRow(new Object[]{
                d[Tecnico.ID],
                d[Tecnico.DOCUMENTO],
                d[Tecnico.NOMBRE],
                d[Tecnico.TELEFONO],
                d[Tecnico.ESPECIALIDAD],
                d[Tecnico.CORREO],
                d[Tecnico.CIUDAD]
            });
            actual = actual.siguiente;
        }
        return modelo;
    }

    public String[] buscarTecnicoPorDocumento(String numDoc) throws IOException {
        cargarTecnicosDesdeTxt();
        NodoTecnico actual = cabezaTecnicos;
        while (actual != null) {
            if (actual.datos[Tecnico.DOCUMENTO].trim().equalsIgnoreCase(numDoc.trim())) {
                return actual.datos;
            }
            actual = actual.siguiente;
        }
        return null;
    }

    public void registrarTecnico(String nombreCompleto, String correo, String password,
            String telefono, String direccion, String tipoDoc, String numDoc, String nivel) throws IOException {
        
        Almacen.iniciar();
        cargarTecnicosDesdeTxt();
        String correoNormal = Modelo_registro.normalizar(correo);

        if (buscarTecnicoPorDocumento(numDoc) != null) {
            throw new IllegalArgumentException("Ya existe un técnico registrado con ese número de documento.");
        }

        Modelo_registro validadorCorreo = new Modelo_registro();
        if (validadorCorreo.existeCorreo(correoNormal)) {
            throw new IllegalArgumentException("El correo ya está registrado por otro usuario.");
        }

        String nuevoIdUsuario = String.valueOf(Almacen.siguienteIdUsuario());
        String nuevoIdTecnico = Almacen.formatoIdTecnico(Almacen.siguienteNumeroTecnico());

        Almacen.agregar(Almacen.USUARIOS,
                nuevoIdUsuario,
                nombreCompleto.trim(),
                correoNormal,
                Seguridad.protegerClave(password),
                Modelo_registro.ROL_TECNICO);

        String[] nuevoTecnico = new String[]{
            nuevoIdTecnico,        // 0: ID
            nuevoIdUsuario,        // 1: ID_USUARIO
            nombreCompleto.trim(), // 2: NOMBRE
            correoNormal,          // 3: CORREO
            telefono.trim(),       // 4: TELEFONO
            direccion.trim(),      // 5: CIUDAD / DIRECCION
            "",                    // 6: FECHA_NACIMIENTO
            numDoc.trim(),         // 7: DOCUMENTO (solo números)
            nivel.trim(),          // 8: ESPECIALIDAD / NIVEL
            "",                    // 9: ANOS_EXPERIENCIA
            tipoDoc.trim()         // 10: DESCRIPCION (guarda "CC", "CE", etc.)
        };

        insertarTecnicoEnLista(nuevoTecnico);
        guardarTecnicosEnTxt();
    }

    public boolean actualizarTecnico(String docBuscado, String nuevoNombre, String nuevosApellidos,
            String nuevoTipoDoc, String nuevoNumDoc, String telefono, String direccion, 
            String correo, String nuevoNivel, String nuevaPass) throws IOException {
        
        Almacen.iniciar();
        cargarTecnicosDesdeTxt();

        NodoTecnico actual = cabezaTecnicos;
        while (actual != null) {
            if (actual.datos[Tecnico.DOCUMENTO].trim().equalsIgnoreCase(docBuscado.trim())) {
                String[] d = actual.datos;
                String idUsuario = d[Tecnico.ID_USUARIO].trim();

                if (!nuevoNombre.trim().isEmpty() || !nuevosApellidos.trim().isEmpty()) {
                    String nombreArmado = (nuevoNombre.trim() + " " + nuevosApellidos.trim()).trim();
                    d[Tecnico.NOMBRE] = nombreArmado;
                }
                if (!nuevoTipoDoc.equals("Selecciona una opcion")) {
                    d[Tecnico.DESCRIPCION] = nuevoTipoDoc.trim();
                }
                if (!nuevoNumDoc.trim().isEmpty()) d[Tecnico.DOCUMENTO] = nuevoNumDoc.trim();
                if (!telefono.trim().isEmpty()) d[Tecnico.TELEFONO] = telefono.trim();
                if (!direccion.trim().isEmpty()) d[Tecnico.CIUDAD] = direccion.trim();
                if (!nuevoNivel.equals("Selecciona una opcion") && !nuevoNivel.equals("Seleccione una opcion")) {
                    d[Tecnico.ESPECIALIDAD] = nuevoNivel.trim();
                }

                String correoNormal = d[Tecnico.CORREO];
                if (!correo.trim().isEmpty()) {
                    correoNormal = Modelo_registro.normalizar(correo);
                    Tecnico helper = new Tecnico();
                    if (helper.correoEnUsoPorOtro(correoNormal, Integer.parseInt(idUsuario))) {
                        throw new IllegalArgumentException("Ese correo ya está en uso por otra cuenta.");
                    }
                    d[Tecnico.CORREO] = correoNormal;
                }

                List<String[]> usuarios = Almacen.leer(Almacen.USUARIOS);
                for (String[] u : usuarios) {
                    if (u.length >= 5 && u[0].trim().equals(idUsuario)) {
                        u[1] = d[Tecnico.NOMBRE];
                        u[2] = correoNormal;
                        if (!nuevaPass.isEmpty()) {
                            u[3] = Seguridad.protegerClave(nuevaPass);
                        }
                    }
                }

                Almacen.reescribir(Almacen.USUARIOS, usuarios);
                guardarTecnicosEnTxt();
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    public boolean eliminarTecnicoPorDocumento(String numDoc) throws IOException {
        Almacen.iniciar();
        cargarTecnicosDesdeTxt();
        if (cabezaTecnicos == null) return false;

        String idUsuarioEliminar = null;
        if (cabezaTecnicos.datos[Tecnico.DOCUMENTO].trim().equalsIgnoreCase(numDoc.trim())) {
            idUsuarioEliminar = cabezaTecnicos.datos[Tecnico.ID_USUARIO].trim();
            cabezaTecnicos = cabezaTecnicos.siguiente;
        } else {
            NodoTecnico actual = cabezaTecnicos;
            while (actual.siguiente != null) {
                if (actual.siguiente.datos[Tecnico.DOCUMENTO].trim().equalsIgnoreCase(numDoc.trim())) {
                    idUsuarioEliminar = actual.siguiente.datos[Tecnico.ID_USUARIO].trim();
                    actual.siguiente = actual.siguiente.siguiente;
                    break;
                }
                actual = actual.siguiente;
            }
        }

        if (idUsuarioEliminar != null) {
            final String idBorrar = idUsuarioEliminar;
            List<String[]> usuarios = Almacen.leer(Almacen.USUARIOS);
            usuarios.removeIf(u -> u.length >= 1 && u[0].trim().equals(idBorrar));
            Almacen.reescribir(Almacen.USUARIOS, usuarios);
            guardarTecnicosEnTxt();
            return true;
        }
        return false;
    }

    // =========================================================================
    // SECCIÓN 2: GESTIÓN DE SERVICIOS (10 Columnas)
    // =========================================================================

    public static final int SRV_NOMBRE_CLI = 0;
    public static final int SRV_APELLIDO_CLI = 1;
    public static final int SRV_TIPO_DOC = 2;
    public static final int SRV_NUM_DOC = 3;
    public static final int SRV_TELEFONO = 4;
    public static final int SRV_DIRECCION = 5;
    public static final int SRV_FECHA = 6;
    public static final int SRV_TECNICO = 7;
    public static final int SRV_TIPO = 8;
    public static final int SRV_DESCRIPCION = 9;

    private static final String ARCHIVO_SERVICIOS = "servicios.txt";

    private static class NodoServicio {
        String[] datos; // 10 columnas
        NodoServicio siguiente;

        NodoServicio(String[] datos) {
            this.datos = datos;
            this.siguiente = null;
        }
    }

    private NodoServicio cabezaServicios;

    public void cargarServiciosDesdeTxt() throws IOException {
        Almacen.iniciar();
        cabezaServicios = null;
        for (String[] fila : Almacen.leer(ARCHIVO_SERVICIOS)) {
            if (fila.length == 10) {
                insertarServicioEnLista(fila);
            }
        }
    }

    private void insertarServicioEnLista(String[] fila) {
        NodoServicio nuevo = new NodoServicio(fila);
        if (cabezaServicios == null) {
            cabezaServicios = nuevo;
        } else {
            NodoServicio actual = cabezaServicios;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
        }
    }

    private void guardarServiciosEnTxt() throws IOException {
        List<String[]> lista = new ArrayList<>();
        NodoServicio actual = cabezaServicios;
        while (actual != null) {
            lista.add(actual.datos);
            actual = actual.siguiente;
        }
        Almacen.reescribir(ARCHIVO_SERVICIOS, lista);
    }

    public void asignarServicio(String nombreCli, String apellidoCli, String tipoDoc, String numDoc,
            String telefono, String direccion, String fecha, String tecnico,
            String tipoServicio, String descripcion) throws IOException {
        
        cargarServiciosDesdeTxt();
        String[] nuevo = new String[]{
            nombreCli.trim(), apellidoCli.trim(), tipoDoc.trim(), numDoc.trim(),
            telefono.trim(), direccion.trim(), fecha.trim(), tecnico.trim(),
            tipoServicio.trim(), descripcion.trim().replace("\n", " ")
        };
        insertarServicioEnLista(nuevo);
        guardarServiciosEnTxt();
    }

    public String[] buscarServicio(String numDoc) throws IOException {
        cargarServiciosDesdeTxt();
        NodoServicio actual = cabezaServicios;
        while (actual != null) {
            if (actual.datos[SRV_NUM_DOC].trim().equalsIgnoreCase(numDoc.trim())) {
                return actual.datos;
            }
            actual = actual.siguiente;
        }
        return null;
    }

    public DefaultTableModel listarServicios() throws IOException {
        cargarServiciosDesdeTxt();
        String[] columnas = {
            "Tipo de Servicio", "Direccion cliente", "Nombre cliente", "Nombre Tecnico", "Fecha"
        };
        DefaultTableModel modelo = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        NodoServicio actual = cabezaServicios;
        while (actual != null) {
            String[] d = actual.datos;
            modelo.addRow(new Object[]{
                d[SRV_TIPO],
                d[SRV_DIRECCION],
                d[SRV_NOMBRE_CLI] + " " + d[SRV_APELLIDO_CLI],
                d[SRV_TECNICO],
                d[SRV_FECHA]
            });
            actual = actual.siguiente;
        }
        return modelo;
    }

    public boolean actualizarServicio(String docBuscado, String nombreCli, String apellidoCli,
            String tipoDoc, String nuevoNumDoc, String telefono, String direccion,
            String fecha, String tecnico, String tipoServicio, String descripcion) throws IOException {
        
        cargarServiciosDesdeTxt();
        NodoServicio actual = cabezaServicios;
        while (actual != null) {
            if (actual.datos[SRV_NUM_DOC].trim().equalsIgnoreCase(docBuscado.trim())) {
                String[] d = actual.datos;
                if (!nombreCli.trim().isEmpty()) d[SRV_NOMBRE_CLI] = nombreCli.trim();
                if (!apellidoCli.trim().isEmpty()) d[SRV_APELLIDO_CLI] = apellidoCli.trim();
                if (!tipoDoc.equals("Selecciona una opcion")) d[SRV_TIPO_DOC] = tipoDoc.trim();
                if (!nuevoNumDoc.trim().isEmpty()) d[SRV_NUM_DOC] = nuevoNumDoc.trim();
                if (!telefono.trim().isEmpty()) d[SRV_TELEFONO] = telefono.trim();
                if (!direccion.trim().isEmpty()) d[SRV_DIRECCION] = direccion.trim();
                if (!fecha.trim().isEmpty()) d[SRV_FECHA] = fecha.trim();
                if (!tecnico.trim().isEmpty()) d[SRV_TECNICO] = tecnico.trim();
                if (!tipoServicio.equals("Selecciona una opcion")) d[SRV_TIPO] = tipoServicio.trim();
                if (!descripcion.trim().isEmpty()) d[SRV_DESCRIPCION] = descripcion.trim().replace("\n", " ");

                guardarServiciosEnTxt();
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    public boolean eliminarServicio(String numDoc) throws IOException {
        cargarServiciosDesdeTxt();
        if (cabezaServicios == null) return false;

        if (cabezaServicios.datos[SRV_NUM_DOC].trim().equalsIgnoreCase(numDoc.trim())) {
            cabezaServicios = cabezaServicios.siguiente;
            guardarServiciosEnTxt();
            return true;
        }

        NodoServicio actual = cabezaServicios;
        while (actual.siguiente != null) {
            if (actual.siguiente.datos[SRV_NUM_DOC].trim().equalsIgnoreCase(numDoc.trim())) {
                actual.siguiente = actual.siguiente.siguiente;
                guardarServiciosEnTxt();
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    // =========================================================================
    // SECCIÓN 3: GESTIÓN DE USUARIOS / CLIENTES (4 columnas en Almacen.CLIENTES)
    // =========================================================================

    private static class NodoCliente {
        String[] datos; // [0] Nombre, [1] Correo, [2] Telefono, [3] Ciudad
        NodoCliente siguiente;

        NodoCliente(String[] datos) {
            this.datos = datos;
            this.siguiente = null;
        }
    }

    private NodoCliente cabezaClientes;

    public void cargarClientesDesdeTxt() throws IOException {
        Almacen.iniciar();
        cabezaClientes = null;
        for (String[] fila : Almacen.leer(Almacen.CLIENTES)) {
            if (fila.length >= 4) {
                NodoCliente nuevo = new NodoCliente(fila);
                if (cabezaClientes == null) {
                    cabezaClientes = nuevo;
                } else {
                    NodoCliente actual = cabezaClientes;
                    while (actual.siguiente != null) {
                        actual = actual.siguiente;
                    }
                    actual.siguiente = nuevo;
                }
            }
        }
    }

    public DefaultTableModel listarClientes() throws IOException {
        cargarClientesDesdeTxt();
        String[] columnas = {"Nombre", "Correo", "Telefono", "Ciudad"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        NodoCliente actual = cabezaClientes;
        while (actual != null) {
            modelo.addRow(new Object[]{
                actual.datos[0], actual.datos[1], actual.datos[2], actual.datos[3]
            });
            actual = actual.siguiente;
        }
        return modelo;
    }
}