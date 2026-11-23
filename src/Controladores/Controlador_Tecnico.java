/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelos.Busqueda;
import Modelos.Material;
import Modelos.Modelo_login;
import Modelos.Tecnico;
import java.awt.Component;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Heskin Martinez
 */
public class Controlador_Tecnico {
    
    private static final String NO_DISPONIBLE = "No disponible";

    private final Tecnico modelo = new Tecnico();

    // Componentes de la vista que el controlador llena (los entrega la vista una vez).
    private JLabel[] etiquetas;   // tecnico, ID, correo, telefono, nombre, fecha, direccion, documento, especialidad, anos, descripcion
    private JTextField[] campos;  // nombre, correo, fecha, documento, telefono, direccion, especialidad, anos, descripcion

    /** La vista entrega los labels del Perfil, en este orden. */
    public void enlazarPerfil(JLabel nombreTitulo, JLabel id, JLabel correo, JLabel telefono,
            JLabel nombreCompleto, JLabel fechaNacimiento, JLabel direccion, JLabel documento,
            JLabel especialidad, JLabel anosExperiencia, JLabel descripcion) {
        etiquetas = new JLabel[]{nombreTitulo, id, correo, telefono, nombreCompleto, fechaNacimiento,
            direccion, documento, especialidad, anosExperiencia, descripcion};
    }

    /** La vista entrega los campos de texto de Configuracion, en este orden. */
    public void enlazarConfiguracion(JTextField nombre, JTextField correo, JTextField fechaNacimiento,
            JTextField documento, JTextField telefono, JTextField direccion, JTextField especialidad,
            JTextField anosExperiencia, JTextField descripcion) {
        campos = new JTextField[]{nombre, correo, fechaNacimiento, documento, telefono, direccion,
            especialidad, anosExperiencia, descripcion};
    }

    /** Pone en los labels del Perfil los datos guardados del tecnico que inicio sesion. */
    public void mostrarPerfil() {
        if (etiquetas == null) {
            return;
        }
        String[] d = perfilParaMostrar();
        etiquetas[0].setText(d[1]);   // nombre (titulo)
        etiquetas[1].setText(d[0]);   // ID
        etiquetas[2].setText(d[2]);   // correo
        etiquetas[3].setText(d[3]);   // telefono
        etiquetas[4].setText(d[1]);   // nombre completo
        etiquetas[5].setText(d[5]);   // fecha de nacimiento
        etiquetas[6].setText(d[4]);   // direccion (ciudad)
        etiquetas[7].setText(d[6]);   // documento
        etiquetas[8].setText(d[7]);   // especialidad
        etiquetas[9].setText(d[8]);   // anos de experiencia
        etiquetas[10].setText(d[9]);  // descripcion
    }

    /** Pone en los campos de Configuracion lo que ya esta guardado (lo que falta queda vacio). */
    public void cargarCampos() {
        if (campos == null) {
            return;
        }
        String[] d = perfilParaEditar();
        campos[0].setText(d[1]);  // nombre
        campos[1].setText(d[2]);  // correo
        campos[2].setText(d[5]);  // fecha de nacimiento
        campos[3].setText(d[6]);  // documento
        campos[4].setText(d[3]);  // telefono
        campos[5].setText(d[4]);  // direccion (ciudad)
        campos[6].setText(d[7]);  // especialidad
        campos[7].setText(d[8]);  // anos de experiencia
        campos[8].setText(d[9]);  // descripcion
    }

    /** Lee los campos de Configuracion, los valida y los guarda. True si se guardo. */
    public boolean guardarPerfil(Component padre) {
        if (campos == null) {
            return false;
        }
        return guardar(padre, campos[0].getText(), campos[1].getText(), campos[2].getText(),
                campos[3].getText(), campos[4].getText(), campos[5].getText(), campos[6].getText(),
                campos[7].getText(), campos[8].getText());
    }

    public void cerrarSesion() {
        Modelo_login.cerrarSesion();
    }

    /** Label Almacen: abre la ventana de inventario y cierra la del tecnico. */
    public void abrirAlmacen(javax.swing.JFrame ventanaActual) {
        java.awt.EventQueue.invokeLater(() -> new Vistas.INVENTARIO().setVisible(true));
        ventanaActual.dispose();
    }

    // ---------------------------------------------------------------
    // Solicitudes de servicio (las crea el cliente al pagar)
    // ---------------------------------------------------------------

    private JTable tablaInicio;        // ID Solicitud, Cliente, Servicio, Fecha
    private JTable tablaSolicitudes;   // ID, Cliente, Servicio, Fecha Asignada, Estado, Prioridad

    /** La vista entrega la tabla de Inicio y la de Mis Solicitudes. */
    public void enlazarSolicitudes(JTable inicio, JTable misSolicitudes) {
        this.tablaInicio = inicio;
        this.tablaSolicitudes = misSolicitudes;
    }

    /**
     * Llena Mis Solicitudes con todas las del tecnico y Inicio con las
     * pendientes, en el orden en que llegaron (cola: la primera en llegar sale primero).
     */
    public void cargarSolicitudes() {
        if (tablaInicio == null || tablaSolicitudes == null) {
            return;
        }
        DefaultTableModel inicio = (DefaultTableModel) tablaInicio.getModel();
        DefaultTableModel todas = (DefaultTableModel) tablaSolicitudes.getModel();
        inicio.setRowCount(0);
        todas.setRowCount(0);
        try {
            Queue<String[]> pendientes = new ArrayDeque<>();
            for (String[] fila : modelo.listarSolicitudes(idTecnicoActual())) {
                todas.addRow(fila);
                if (Tecnico.ESTADO_PENDIENTE.equals(fila[4])) {
                    pendientes.add(fila);
                }
            }
            while (!pendientes.isEmpty()) {
                String[] fila = pendientes.poll();
                inicio.addRow(new Object[]{fila[0], fila[1], fila[2], fila[3]});
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(tablaSolicitudes,
                    "No se pudieron cargar las solicitudes: " + e.getMessage(),
                    "Solicitudes", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ---------------------------------------------------------------
    // Inventario: materiales usados y busqueda (lista de Controlador_Inventario)
    // ---------------------------------------------------------------

    private final Controlador_Inventario inventario = new Controlador_Inventario();
    private JTable tablaUsados;       // ID, Material, Disponible, Cantidad Usada
    private JTable tablaInventario;   // ID, Material, Estado, Cantidad
    private JComboBox<?> criterioBusqueda;
    private JTextField textoBusqueda;

    // Despues de buscar, el campo de texto se limpia solo y la tabla vuelve a mostrar todos los materiales.
    private int segundosLimpiarCampo = 5;
    private javax.swing.Timer temporizadorLimpiar;
    private boolean limpiandoCampo = false;

    /** La vista entrega las dos tablas, el combo "Buscar por" y el campo de texto de la busqueda. */
    public void enlazarInventario(JTable usados, JTable inventarioTabla, JComboBox<?> criterio, JTextField texto) {
        this.tablaUsados = usados;
        this.tablaInventario = inventarioTabla;
        this.criterioBusqueda = criterio;
        this.textoBusqueda = texto;
        // Enter en el campo busca; si el campo se vacia, vuelven todos los materiales.
        texto.addActionListener(e -> buscarMateriales());
        texto.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                textoCambio();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                textoCambio();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                textoCambio();
            }
        });
        // La lista de materiales no tiene "categoria"; se muestra el estado del almacen.
        inventarioTabla.getColumnModel().getColumn(2).setHeaderValue("Estado");
        inventarioTabla.getTableHeader().repaint();
    }

    /** Si el usuario escribe se cancela la limpieza automatica; si deja el campo vacio, vuelven todos los materiales. */
    private void textoCambio() {
        if (limpiandoCampo) {
            return; // es la limpieza automatica: la tabla ya se restaura aparte
        }
        if (temporizadorLimpiar != null) {
            temporizadorLimpiar.stop();
        }
        if (tablaInventario != null && textoBusqueda.getText().trim().isEmpty()) {
            llenarTablaInventario(inventario.obtenerMateriales());
        }
    }

    /** Pasados unos segundos limpia el campo de texto y vuelve a mostrar todos los materiales. */
    private void programarLimpiezaCampo() {
        if (temporizadorLimpiar != null) {
            temporizadorLimpiar.stop();
        }
        temporizadorLimpiar = new javax.swing.Timer(segundosLimpiarCampo * 1000, e -> {
            limpiandoCampo = true;
            try {
                textoBusqueda.setText("");
            } finally {
                limpiandoCampo = false;
            }
            llenarTablaInventario(inventario.obtenerMateriales());
        });
        temporizadorLimpiar.setRepeats(false);
        temporizadorLimpiar.start();
    }

    /** Cuantos segundos tarda en limpiarse el campo de busqueda despues de buscar (por defecto 5). */
    public void setSegundosLimpiarCampo(int segundos) {
        this.segundosLimpiarCampo = Math.max(1, segundos);
    }

    /** Borra el texto de la busqueda y vuelve a mostrar todos los materiales. */
    public void limpiarBusqueda() {
        if (temporizadorLimpiar != null) {
            temporizadorLimpiar.stop();
        }
        if (textoBusqueda != null) {
            textoBusqueda.setText("");
        }
        if (tablaInventario != null) {
            llenarTablaInventario(inventario.obtenerMateriales());
        }
    }

    /** Llena las dos tablas con todos los materiales y su stock actual. */
    public void cargarInventario() {
        if (tablaUsados == null || tablaInventario == null) {
            return;
        }
        llenarTablaUsados();
        llenarTablaInventario(inventario.obtenerMateriales());
    }

    /**
     * Busca por ID (busqueda binaria sobre la lista ordenada por codigo) o por
     * nombre (busqueda lineal) segun el combo, y muestra el resultado.
     * Con el campo vacio muestra todos los materiales.
     */
    public void buscarMateriales() {
        if (tablaInventario == null) {
            return;
        }
        String texto = textoBusqueda.getText().trim();
        if (texto.isEmpty()) {
            llenarTablaInventario(inventario.obtenerMateriales());
            return;
        }

        List<Material> resultado = new ArrayList<>();
        if ("ID".equals(String.valueOf(criterioBusqueda.getSelectedItem()))) {
            List<Material> ordenada = Busqueda.ordenarPorCodigo(inventario.obtenerMateriales());
            Material encontrado = Busqueda.buscarPorCodigo(ordenada, normalizarCodigo(texto));
            if (encontrado != null) {
                resultado.add(encontrado);
            }
        } else {
            resultado = Busqueda.buscarPorNombre(inventario.obtenerMateriales(), texto);
        }

        llenarTablaInventario(resultado);
        if (resultado.isEmpty()) {
            JOptionPane.showMessageDialog(tablaInventario, "No se encontraron materiales con ese dato.",
                    "Buscar material", JOptionPane.INFORMATION_MESSAGE);
        } else {
            programarLimpiezaCampo();
        }
    }

    /**
     * Toma la "Cantidad Usada" escrita en la tabla de materiales usados, la
     * valida, descuenta el stock, la guarda en materiales_usados.txt y
     * actualiza las tablas. Si algo esta mal no se guarda nada.
     */
    public boolean registrarMaterialesUsados(Component padre) {
        if (tablaUsados == null) {
            return false;
        }
        if (tablaUsados.isEditing() && !tablaUsados.getCellEditor().stopCellEditing()) {
            aviso(padre, "Corrige la cantidad usada: debe ser un numero entero.", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        List<Material> materiales = inventario.obtenerMateriales();
        List<Material> usados = new ArrayList<>();
        List<Integer> cantidades = new ArrayList<>();
        for (int fila = 0; fila < tablaUsados.getRowCount(); fila++) {
            Object valor = tablaUsados.getValueAt(fila, 3);
            String texto = valor == null ? "" : valor.toString().trim();
            if (texto.isEmpty()) {
                continue;
            }
            String codigo = String.valueOf(tablaUsados.getValueAt(fila, 0));
            Material material = null;
            for (Material m : materiales) {
                if (m.getCodigo().equals(codigo)) {
                    material = m;
                }
            }
            int cantidad;
            try {
                cantidad = Integer.parseInt(texto);
            } catch (NumberFormatException e) {
                aviso(padre, "La cantidad usada de " + codigo + " debe ser un numero entero.",
                        JOptionPane.WARNING_MESSAGE);
                return false;
            }
            if (material == null || cantidad <= 0) {
                aviso(padre, "La cantidad usada de " + codigo + " debe ser mayor a 0.", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            if (cantidad > material.getStockDisponible()) {
                aviso(padre, "No hay suficiente stock de " + material.getNombre() + ". Disponible: "
                        + material.getStockDisponible() + ".", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            usados.add(material);
            cantidades.add(cantidad);
        }

        if (usados.isEmpty()) {
            aviso(padre, "Escribe la cantidad usada en al menos un material.", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            String idTecnico = idTecnicoActual();
            for (int i = 0; i < usados.size(); i++) {
                modelo.registrarUsoMaterial(idTecnico, usados.get(i).getCodigo(),
                        usados.get(i).getNombre(), cantidades.get(i));
                usados.get(i).setStockDisponible(usados.get(i).getStockDisponible() - cantidades.get(i));
            }
        } catch (IOException e) {
            aviso(padre, "No se pudo guardar el uso de materiales: " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
            return false;
        }

        cargarInventario();
        aviso(padre, "Materiales registrados correctamente.", JOptionPane.INFORMATION_MESSAGE);
        return true;
    }

    private void llenarTablaUsados() {
        DefaultTableModel m = (DefaultTableModel) tablaUsados.getModel();
        m.setRowCount(0);
        for (Material material : inventario.obtenerMateriales()) {
            m.addRow(new Object[]{material.getCodigo(), material.getNombre(),
                material.getStockDisponible(), null});
        }
    }

    private void llenarTablaInventario(List<Material> materiales) {
        DefaultTableModel m = (DefaultTableModel) tablaInventario.getModel();
        m.setRowCount(0);
        for (Material material : materiales) {
            m.addRow(new Object[]{material.getCodigo(), material.getNombre(),
                material.getEstadoAlmacen(), material.getStockDisponible()});
        }
    }

    /** "1" o "001" se entienden como MAT-001. */
    private String normalizarCodigo(String texto) {
        String t = texto.trim().toUpperCase();
        if (t.matches("\\d{1,3}")) {
            return String.format("MAT-%03d", Integer.parseInt(t));
        }
        return t;
    }

    private String idTecnicoActual() throws IOException {
        String[] perfil = modelo.obtenerPerfil(Modelo_login.getIdUsuarioActual());
        return perfil != null ? perfil[Tecnico.ID] : "SIN-ID";
    }

    /**
     * Llena la tabla de clientes (Nombre, Correo, Telefono, Ciudad) con lo
     * que hay guardado en el archivo de clientes.
     */
    public void cargarClientes(JTable tabla) {
        DefaultTableModel tablaModelo = (DefaultTableModel) tabla.getModel();
        tablaModelo.setRowCount(0);
        try {
            for (String[] fila : modelo.listarClientes()) {
                tablaModelo.addRow(fila);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(tabla,
                    "No se pudieron cargar los clientes: " + e.getMessage(),
                    "Clientes", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Datos del tecnico que inicio sesion, listos para los labels del Perfil.
     * Orden: 0 ID, 1 nombre, 2 correo, 3 telefono, 4 ciudad (Direccion),
     * 5 fecha de nacimiento, 6 documento, 7 especialidad, 8 anos de
     * experiencia, 9 descripcion. Lo que esta vacio sale como "No disponible".
     */
    public String[] perfilParaMostrar() {
        String[] datos = leerPerfil();
        for (int i = 0; i < datos.length; i++) {
            if (datos[i].isEmpty()) {
                datos[i] = NO_DISPONIBLE;
            }
        }
        return datos;
    }

    /** Mismo orden que perfilParaMostrar, pero lo vacio queda vacio (para los campos de texto). */
    public String[] perfilParaEditar() {
        return leerPerfil();
    }

    /**
     * Valida y guarda lo que el tecnico escribio en Configuracion y actualiza
     * los labels del Perfil. Devuelve true si se guardo.
     */
    private boolean guardar(Component padre, String nombre, String correo, String fechaNacimiento,
            String documento, String telefono, String direccion, String especialidad,
            String anosExperiencia, String descripcion) {

        nombre = limpio(nombre);
        correo = limpio(correo);
        fechaNacimiento = limpio(fechaNacimiento);
        documento = limpio(documento);
        telefono = limpio(telefono);
        direccion = limpio(direccion);
        especialidad = limpio(especialidad);
        anosExperiencia = limpio(anosExperiencia);
        descripcion = limpio(descripcion);

        String error = validarPerfil(nombre, correo, fechaNacimiento, documento, telefono,
                direccion, especialidad, anosExperiencia, descripcion);
        if (error != null) {
            aviso(padre, error, JOptionPane.WARNING_MESSAGE);
            return false;
        }

        int idUsuario = Modelo_login.getIdUsuarioActual();
        if (idUsuario == 0) {
            aviso(padre, "No hay una sesion iniciada. Inicia sesion para editar tu perfil.",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            if (modelo.correoEnUsoPorOtro(correo, idUsuario)) {
                aviso(padre, "Ese correo ya lo usa otra cuenta.", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            modelo.guardarPerfil(idUsuario, nombre, correo, telefono, direccion, fechaNacimiento,
                    documento, especialidad, anosExperiencia, descripcion);
            mostrarPerfil();
            aviso(padre, "Cambios guardados.", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (IOException e) {
            aviso(padre, "No se pudieron guardar los cambios: " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    /** Nombre, correo, telefono y direccion son obligatorios; el resto es opcional pero si se escribe se revisa. */
    private String validarPerfil(String nombre, String correo, String fechaNacimiento,
            String documento, String telefono, String direccion, String especialidad,
            String anosExperiencia, String descripcion) {
        if (nombre.length() < 3) {
            return "Escribe tu nombre completo.";
        }
        if (!Validaciones.correoValido(correo)) {
            return Validaciones.MENSAJE_CORREO;
        }
        if (!Validaciones.telefonoValido(telefono)) {
            return Validaciones.MENSAJE_TELEFONO;
        }
        if (direccion.isEmpty()) {
            return "Escribe tu ciudad en el campo Direccion.";
        }
        if (!fechaNacimiento.isEmpty() && !Validaciones.fechaNacimientoValida(fechaNacimiento)) {
            return "La fecha de nacimiento debe ser dia/mes/ano, por ejemplo 25/03/1995.";
        }
        if (!documento.isEmpty() && !Validaciones.soloNumeros(documento, 5, 12)) {
            return "El documento debe tener solo numeros (entre 5 y 12 digitos).";
        }
        if (!anosExperiencia.isEmpty() && !Validaciones.anosExperienciaValidos(anosExperiencia)) {
            return "Los anos de experiencia deben ser un numero entre 0 y 60.";
        }
        if (especialidad.length() > 60) {
            return "La especialidad es muy larga (maximo 60 caracteres).";
        }
        if (descripcion.length() > 200) {
            return "La descripcion es muy larga (maximo 200 caracteres).";
        }
        return null;
    }

    private String[] leerPerfil() {
        String[] datos = new String[10];
        Arrays.fill(datos, "");
        datos[1] = limpio(Modelo_login.getNombreActual());
        datos[2] = limpio(Modelo_login.getCorreoActual());
        try {
            String[] fila = modelo.obtenerPerfil(Modelo_login.getIdUsuarioActual());
            if (fila != null) {
                datos[0] = fila[Tecnico.ID];
                datos[1] = fila[Tecnico.NOMBRE];
                datos[2] = fila[Tecnico.CORREO];
                datos[3] = fila[Tecnico.TELEFONO];
                datos[4] = fila[Tecnico.CIUDAD];
                datos[5] = fila[Tecnico.FECHA_NACIMIENTO];
                datos[6] = fila[Tecnico.DOCUMENTO];
                datos[7] = fila[Tecnico.ESPECIALIDAD];
                datos[8] = fila[Tecnico.ANOS_EXPERIENCIA];
                datos[9] = fila[Tecnico.DESCRIPCION];
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No se pudo leer el perfil: " + e.getMessage(),
                    "Perfil", JOptionPane.ERROR_MESSAGE);
        }
        return datos;
    }

    private String limpio(String texto) {
        return texto == null ? "" : texto.trim();
    }

    private void aviso(Component padre, String mensaje, int tipo) {
        JOptionPane.showMessageDialog(padre, mensaje, "Perfil", tipo);
    }
}
