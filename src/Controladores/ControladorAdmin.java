package Controladores;

import Modelos.Modelo_Admin;
import Modelos.Modelo_login;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class ControladorAdmin {

    private final Modelo_Admin modelo = new Modelo_Admin();


    // SECCIÓN 1: CONTROLADOR DE TÉCNICOS (CRUD Completo con Tipo Doc y Nivel)

    public boolean procesarRegistroTecnico(String nombre, String apellidos, String tipoDoc,
            String numDoc, String telefono, String direccion, String correo,
            String pass, String repetirPass, String nivel) {

        String nombreCompleto = (nombre.trim() + " " + apellidos.trim()).trim();
        numDoc = numDoc.trim();
        telefono = telefono.trim();
        direccion = direccion.trim();
        correo = correo.trim();

        // 1. Validar campos obligatorios (*)
        if (nombre.trim().isEmpty() || apellidos.trim().isEmpty() || numDoc.isEmpty()
                || telefono.isEmpty() || direccion.isEmpty() || correo.isEmpty()
                || pass.isEmpty() || repetirPass.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos marcados con (*) son obligatorios.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (tipoDoc.equals("Selecciona una opcion") || nivel.equals("Seleccione una opcion") || nivel.equals("Selecciona una opcion")) {
            JOptionPane.showMessageDialog(null, "Debes seleccionar el tipo de documento y el nivel del técnico.", "Selección requerida", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // 2. Validar formatos con la clase Validaciones del proyecto
        if (!Validaciones.correoValido(correo)) {
            JOptionPane.showMessageDialog(null, Validaciones.MENSAJE_CORREO, "Correo inválido", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (!Validaciones.telefonoValido(telefono)) {
            JOptionPane.showMessageDialog(null, Validaciones.MENSAJE_TELEFONO, "Teléfono inválido", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (!Validaciones.soloNumeros(numDoc, 5, 12)) {
            JOptionPane.showMessageDialog(null, "El documento debe tener solo números (entre 5 y 12 dígitos).", "Documento inválido", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        // 3. Validar contraseña
        if (pass.length() < 8) {
            JOptionPane.showMessageDialog(null, "La contraseña debe tener mínimo 8 caracteres.", "Contraseña inválida", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (!pass.equals(repetirPass)) {
            JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden.", "Error de contraseña", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // 4. Registrar en ModeloAdmin
        try {
            modelo.registrarTecnico(nombreCompleto, correo, pass, telefono, direccion, tipoDoc, numDoc, nivel);
            JOptionPane.showMessageDialog(null, "Técnico registrado y guardado correctamente.", "Registro exitoso", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar en archivo: " + e.getMessage(), "Error de archivo", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public void cargarTablaTecnicos(JTable tabla) {
        try {
            tabla.setModel(modelo.listarTecnicos());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar los técnicos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String[] procesarBuscarTecnico(String numDoc) {
        if (numDoc.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingresa el N° de Documento del técnico a buscar.", "Campo obligatorio", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        try {
            String[] tecnico = modelo.buscarTecnicoPorDocumento(numDoc);
            if (tecnico == null) {
                JOptionPane.showMessageDialog(null, "No se encontró ningún técnico con ese documento.", "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
            }
            return tecnico;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar técnico: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public boolean procesarActualizarTecnico(String docBuscado, String nombre, String apellidos,
            String nuevoTipoDoc, String nuevoNumDoc, String telefono, String direccion,
            String correo, String nuevoNivel, String pass, String repetirPass) {

        if (docBuscado.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Primero debes buscar el técnico por N° de Documento.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (!correo.trim().isEmpty() && !Validaciones.correoValido(correo.trim())) {
            JOptionPane.showMessageDialog(null, Validaciones.MENSAJE_CORREO, "Correo inválido", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (!telefono.trim().isEmpty() && !Validaciones.telefonoValido(telefono.trim())) {
            JOptionPane.showMessageDialog(null, Validaciones.MENSAJE_TELEFONO, "Teléfono inválido", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (!nuevoNumDoc.trim().isEmpty() && !Validaciones.soloNumeros(nuevoNumDoc.trim(), 5, 12)) {
            JOptionPane.showMessageDialog(null, "El documento debe tener solo números (5 a 12 dígitos).", "Documento inválido", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (!pass.isEmpty() || !repetirPass.isEmpty()) {
            if (pass.length() < 8) {
                JOptionPane.showMessageDialog(null, "La nueva contraseña debe tener mínimo 8 caracteres.", "Contraseña inválida", JOptionPane.WARNING_MESSAGE);
                return false;
            }
            if (!pass.equals(repetirPass)) {
                JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }

        try {
            boolean ok = modelo.actualizarTecnico(docBuscado, nombre, apellidos, nuevoTipoDoc, nuevoNumDoc, telefono, direccion, correo, nuevoNivel, pass);
            if (ok) {
                JOptionPane.showMessageDialog(null, "Datos del técnico actualizados correctamente.", "Actualizado", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el técnico a actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Validación", JOptionPane.WARNING_MESSAGE);
            return false;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar técnico: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean procesarEliminarTecnico(String numDoc) {
        if (numDoc.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Primero busca el técnico que deseas eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        int confirm = JOptionPane.showConfirmDialog(null,
                "¿Estás seguro de eliminar este técnico? También se borrará su cuenta de inicio de sesión.",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm != JOptionPane.YES_OPTION) {
            return false;
        }

        try {
            boolean eliminado = modelo.eliminarTecnicoPorDocumento(numDoc);
            if (eliminado) {
                JOptionPane.showMessageDialog(null, "Técnico eliminado correctamente.", "Eliminado", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el técnico a eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar técnico: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // =========================================================================
    // SECCIÓN 2: CONTROLADOR DE SERVICIOS (CRUD Completo)
    // =========================================================================

    public boolean procesarAsignarServicio(String nombreCli, String apellidoCli, String tipoDoc,
            String numDoc, String telefono, String direccion, String fecha,
            String tecnico, String tipoServicio, String descripcion) {

        if (nombreCli.trim().isEmpty() || apellidoCli.trim().isEmpty() || numDoc.trim().isEmpty()
                || telefono.trim().isEmpty() || direccion.trim().isEmpty() || fecha.trim().isEmpty()
                || tecnico.trim().isEmpty() || tipoDoc.equals("Selecciona una opcion")
                || tipoServicio.equals("Selecciona una opcion")) {
            JOptionPane.showMessageDialog(null, "Por favor completa todos los datos del servicio y selecciona las opciones correspondientes.", "Campos incompletos", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (!Validaciones.soloNumeros(numDoc.trim(), 5, 12)) {
            JOptionPane.showMessageDialog(null, "El documento del cliente debe tener solo números (5 a 12 dígitos).", "Documento inválido", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (!Validaciones.telefonoValido(telefono.trim())) {
            JOptionPane.showMessageDialog(null, Validaciones.MENSAJE_TELEFONO, "Teléfono inválido", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            modelo.asignarServicio(nombreCli, apellidoCli, tipoDoc, numDoc, telefono, direccion, fecha, tecnico, tipoServicio, descripcion);
            JOptionPane.showMessageDialog(null, "Servicio asignado y guardado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar el servicio: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public String[] procesarBuscarServicio(String numDoc) {
        if (numDoc.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ingresa el N° de Documento para buscar.", "Campo obligatorio", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        try {
            String[] resultado = modelo.buscarServicio(numDoc);
            if (resultado == null) {
                JOptionPane.showMessageDialog(null, "No se encontró ningún servicio registrado con ese documento.", "Sin resultados", JOptionPane.INFORMATION_MESSAGE);
            }
            return resultado;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al leer los servicios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
public void cargarTablaServicios(JTable tabla) {
        try {
            javax.swing.table.DefaultTableModel modeloTabla = modelo.listarServicios();
            tabla.setModel(modeloTabla);

            if (modeloTabla.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "No hay ningún servicio registrado todavía.", "Sin registros", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al listar servicios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public boolean procesarActualizarServicio(String docBuscado, String nombreCli, String apellidoCli,
            String tipoDoc, String nuevoNumDoc, String telefono, String direccion,
            String fecha, String tecnico, String tipoServicio, String descripcion) {

        if (docBuscado.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Primero debes buscar el servicio por N° de Documento.", "Error", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            boolean actualizado = modelo.actualizarServicio(docBuscado, nombreCli, apellidoCli, tipoDoc, nuevoNumDoc, telefono, direccion, fecha, tecnico, tipoServicio, descripcion);
            if (actualizado) {
                JOptionPane.showMessageDialog(null, "Servicio actualizado correctamente.", "Actualizado", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el servicio a actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar archivo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean procesarEliminarServicio(String numDoc) {
        if (numDoc.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Primero busca el servicio que deseas eliminar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        int confirm = JOptionPane.showConfirmDialog(null,
                "¿Estás seguro de eliminar este servicio permanentemente?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (confirm != JOptionPane.YES_OPTION) {
            return false;
        }

        try {
            boolean eliminado = modelo.eliminarServicio(numDoc);
            if (eliminado) {
                JOptionPane.showMessageDialog(null, "Servicio eliminado correctamente.", "Eliminado", JOptionPane.INFORMATION_MESSAGE);
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró ningún servicio con ese documento.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar del archivo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // =========================================================================
    // SECCIÓN 3: CONTROLADOR DE USUARIOS / CLIENTES
    // =========================================================================

    public void cargarTablaClientes(JTable tabla) {
        try {
            tabla.setModel(modelo.listarClientes());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar los clientes: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // =========================================================================
    // SECCIÓN 4: CIERRE DE SESIÓN
    // =========================================================================

    public void cerrarSesion() {
        Modelo_login.cerrarSesion();
    }
}
