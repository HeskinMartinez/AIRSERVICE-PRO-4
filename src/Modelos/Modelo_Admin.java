package Modelos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class Modelo_Admin {

    private final String ARCHIVO_USUARIOS = "usuarios.txt";
    private final String ARCHIVO_TECNICOS = "tecnicos.txt";
    private final String ARCHIVO_SERVICIOS = "servicios.txt";
    private final String ARCHIVO_CLIENTES = "clientes.txt";

    // =========================================================================
    // SECCIÓN 1: CRUD DE TÉCNICOS
    // Formato tecnicos.txt: nombreCompleto;tipoDoc;numDoc;telefono;direccion;correo;nivel;clave
    // =========================================================================

    public void registrarTecnico(String nombreCompleto, String correo, String clave,
            String telefono, String direccion, String tipoDoc, String numDoc, String nivel) throws IOException {

        File archivoTec = new File(ARCHIVO_TECNICOS);
        if (archivoTec.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(archivoTec))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] d = linea.split(";");
                    if (d.length >= 6) {
                        if (d[2].trim().equals(numDoc.trim())) {
                            throw new IllegalArgumentException("Ya existe un técnico registrado con ese N° de Documento.");
                        }
                        if (d[5].trim().equalsIgnoreCase(correo.trim())) {
                            throw new IllegalArgumentException("Ya existe un técnico registrado con ese Correo Electrónico.");
                        }
                    }
                }
            }
        }

        // 1. Guardar datos completos del técnico
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoTec, true))) {
            bw.write(nombreCompleto.trim() + ";" + tipoDoc.trim() + ";" + numDoc.trim() + ";"
                    + telefono.trim() + ";" + direccion.trim() + ";" + correo.trim() + ";"
                    + nivel.trim() + ";" + clave.trim());
            bw.newLine();
        }

        // 2. Guardar credenciales en usuarios.txt para iniciar sesión en el Login
        try (BufferedWriter bwLogin = new BufferedWriter(new FileWriter(ARCHIVO_USUARIOS, true))) {
            bwLogin.write(nombreCompleto.trim() + ";" + correo.trim() + ";" + clave.trim() + ";Tecnico");
            bwLogin.newLine();
        }
    }

    public DefaultTableModel listarTecnicos() throws IOException {
        DefaultTableModel tabla = new DefaultTableModel();
        tabla.addColumn("Nombre");
        tabla.addColumn("Tipo Doc");
        tabla.addColumn("Documento");
        tabla.addColumn("Teléfono");
        tabla.addColumn("Dirección");
        tabla.addColumn("Correo");
        tabla.addColumn("Nivel");

        File archivo = new File(ARCHIVO_TECNICOS);
        if (!archivo.exists()) {
            return tabla;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] d = linea.split(";");
                if (d.length >= 7) {
                    tabla.addRow(new Object[]{
                        d[0].trim(), d[1].trim(), d[2].trim(),
                        d[3].trim(), d[4].trim(), d[5].trim(), d[6].trim()
                    });
                }
            }
        }
        return tabla;
    }

    public String[] buscarTecnicoPorDocumento(String numDoc) throws IOException {
        File archivo = new File(ARCHIVO_TECNICOS);
        if (!archivo.exists()) return null;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(";");
                if (d.length >= 8 && d[2].trim().equals(numDoc.trim())) {
                    return d;
                }
            }
        }
        return null;
    }

    public boolean actualizarTecnico(String docBuscado, String nombre, String apellidos,
            String nuevoTipoDoc, String nuevoNumDoc, String telefono, String direccion,
            String correo, String nuevoNivel, String nuevaClave) throws IOException {

        File archivo = new File(ARCHIVO_TECNICOS);
        if (!archivo.exists()) return false;

        List<String> lineas = new ArrayList<>();
        boolean actualizado = false;
        String correoAnterior = null;
        String nombreFinal = "", correoFinal = "", claveFinal = "";

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(";");
                if (d.length >= 8 && d[2].trim().equals(docBuscado.trim())) {
                    actualizado = true;
                    correoAnterior = d[5].trim();

                    String nuevoNombreCompleto = (nombre.trim() + " " + apellidos.trim()).trim();
                    nombreFinal = nuevoNombreCompleto.isEmpty() ? d[0].trim() : nuevoNombreCompleto;
                    String tipoDocFinal = (nuevoTipoDoc == null || nuevoTipoDoc.equals("Selecciona una opcion")) ? d[1].trim() : nuevoTipoDoc.trim();
                    String numDocFinal = nuevoNumDoc.trim().isEmpty() ? d[2].trim() : nuevoNumDoc.trim();
                    String telFinal = telefono.trim().isEmpty() ? d[3].trim() : telefono.trim();
                    String dirFinal = direccion.trim().isEmpty() ? d[4].trim() : direccion.trim();
                    correoFinal = correo.trim().isEmpty() ? d[5].trim() : correo.trim();
                    String nivelFinal = (nuevoNivel == null || nuevoNivel.equals("Selecciona una opcion") || nuevoNivel.equals("Seleccione una opcion")) ? d[6].trim() : nuevoNivel.trim();
                    claveFinal = nuevaClave.isEmpty() ? d[7].trim() : nuevaClave.trim();

                    lineas.add(nombreFinal + ";" + tipoDocFinal + ";" + numDocFinal + ";"
                            + telFinal + ";" + dirFinal + ";" + correoFinal + ";"
                            + nivelFinal + ";" + claveFinal);
                } else {
                    lineas.add(linea);
                }
            }
        }

        if (actualizado) {
            escribirLineas(archivo, lineas);
            actualizarCredencialesLogin(correoAnterior, nombreFinal, correoFinal, claveFinal);
        }
        return actualizado;
    }

    public boolean eliminarTecnicoPorDocumento(String numDoc) throws IOException {
        File archivo = new File(ARCHIVO_TECNICOS);
        if (!archivo.exists()) return false;

        List<String> lineas = new ArrayList<>();
        boolean eliminado = false;
        String correoAEliminar = null;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(";");
                if (d.length >= 6 && d[2].trim().equals(numDoc.trim())) {
                    eliminado = true;
                    correoAEliminar = d[5].trim();
                } else {
                    lineas.add(linea);
                }
            }
        }

        if (eliminado) {
            escribirLineas(archivo, lineas);
            eliminarCredencialesLogin(correoAEliminar);
        }
        return eliminado;
    }

    // =========================================================================
    // SECCIÓN 2: CRUD DE SERVICIOS
    // Formato servicios.txt: nombreCli;apellidoCli;tipoDoc;numDoc;telefono;direccion;fecha;tecnico;tipoServicio;descripcion
    // =========================================================================

    public void asignarServicio(String nombreCli, String apellidoCli, String tipoDoc,
            String numDoc, String telefono, String direccion, String fecha,
            String tecnico, String tipoServicio, String descripcion) throws IOException {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_SERVICIOS, true))) {
            bw.write(nombreCli.trim() + ";" + apellidoCli.trim() + ";" + tipoDoc.trim() + ";"
                    + numDoc.trim() + ";" + telefono.trim() + ";" + direccion.trim() + ";"
                    + fecha.trim() + ";" + tecnico.trim() + ";" + tipoServicio.trim() + ";"
                    + descripcion.trim());
            bw.newLine();
        }
    }

    public DefaultTableModel listarServicios() throws IOException {
        DefaultTableModel tabla = new DefaultTableModel();
        tabla.addColumn("Nombre");
        tabla.addColumn("Apellido");
        tabla.addColumn("Tipo Doc");
        tabla.addColumn("Documento");
        tabla.addColumn("Teléfono");
        tabla.addColumn("Dirección");
        tabla.addColumn("Fecha");
        tabla.addColumn("Técnico");
        tabla.addColumn("Tipo Servicio");
        tabla.addColumn("Descripción");

        File archivo = new File(ARCHIVO_SERVICIOS);
        if (!archivo.exists()) {
            return tabla;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] d = linea.split(";");
                if (d.length >= 10) {
                    tabla.addRow(new Object[]{
                        d[0].trim(), d[1].trim(), d[2].trim(), d[3].trim(), d[4].trim(),
                        d[5].trim(), d[6].trim(), d[7].trim(), d[8].trim(), d[9].trim()
                    });
                }
            }
        }
        return tabla;
    }

    public String[] buscarServicio(String numDoc) throws IOException {
        File archivo = new File(ARCHIVO_SERVICIOS);
        if (!archivo.exists()) return null;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(";");
                if (d.length >= 10 && d[3].trim().equals(numDoc.trim())) {
                    return d;
                }
            }
        }
        return null;
    }

    public boolean actualizarServicio(String docBuscado, String nombreCli, String apellidoCli,
            String tipoDoc, String nuevoNumDoc, String telefono, String direccion,
            String fecha, String tecnico, String tipoServicio, String descripcion) throws IOException {

        File archivo = new File(ARCHIVO_SERVICIOS);
        if (!archivo.exists()) return false;

        List<String> lineas = new ArrayList<>();
        boolean actualizado = false;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(";");
                if (d.length >= 10 && d[3].trim().equals(docBuscado.trim())) {
                    actualizado = true;
                    String nomF = nombreCli.trim().isEmpty() ? d[0].trim() : nombreCli.trim();
                    String apeF = apellidoCli.trim().isEmpty() ? d[1].trim() : apellidoCli.trim();
                    String tDocF = (tipoDoc == null || tipoDoc.equals("Selecciona una opcion")) ? d[2].trim() : tipoDoc.trim();
                    String nDocF = nuevoNumDoc.trim().isEmpty() ? d[3].trim() : nuevoNumDoc.trim();
                    String telF = telefono.trim().isEmpty() ? d[4].trim() : telefono.trim();
                    String dirF = direccion.trim().isEmpty() ? d[5].trim() : direccion.trim();
                    String fecF = fecha.trim().isEmpty() ? d[6].trim() : fecha.trim();
                    String tecF = tecnico.trim().isEmpty() ? d[7].trim() : tecnico.trim();
                    String tSerF = (tipoServicio == null || tipoServicio.equals("Selecciona una opcion")) ? d[8].trim() : tipoServicio.trim();
                    String descF = descripcion.trim().isEmpty() ? d[9].trim() : descripcion.trim();

                    lineas.add(nomF + ";" + apeF + ";" + tDocF + ";" + nDocF + ";" + telF + ";"
                            + dirF + ";" + fecF + ";" + tecF + ";" + tSerF + ";" + descF);
                } else {
                    lineas.add(linea);
                }
            }
        }

        if (actualizado) {
            escribirLineas(archivo, lineas);
        }
        return actualizado;
    }

    public boolean eliminarServicio(String numDoc) throws IOException {
        File archivo = new File(ARCHIVO_SERVICIOS);
        if (!archivo.exists()) return false;

        List<String> lineas = new ArrayList<>();
        boolean eliminado = false;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(";");
                if (d.length >= 4 && d[3].trim().equals(numDoc.trim())) {
                    eliminado = true;
                } else {
                    lineas.add(linea);
                }
            }
        }

        if (eliminado) {
            escribirLineas(archivo, lineas);
        }
        return eliminado;
    }

    // =========================================================================
    // SECCIÓN 3: CRUD DE USUARIOS / CLIENTES (Compatible con UsuarioJ)
    // Formato clientes.txt: nombre;apellidos;tipoDoc;numDoc;telefono;direccion;correo;clave
    // =========================================================================

    public void registrarCliente(String nombre, String apellidos, String tipoDoc,
            String numDoc, String telefono, String direccion, String correo, String clave) throws IOException {

        File archivoClientes = new File(ARCHIVO_CLIENTES);
        if (archivoClientes.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(archivoClientes))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] d = linea.split(";");
                    if (d.length >= 7) {
                        if (d[3].trim().equals(numDoc.trim())) {
                            throw new IllegalArgumentException("Ya existe un usuario con ese N° de Documento.");
                        }
                        if (d[6].trim().equalsIgnoreCase(correo.trim())) {
                            throw new IllegalArgumentException("Ya existe un usuario con ese Correo Electrónico.");
                        }
                    }
                }
            }
        }

        // 1. Guarda los 8 datos completos en clientes.txt
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivoClientes, true))) {
            bw.write(nombre.trim() + ";" + apellidos.trim() + ";" + tipoDoc.trim() + ";"
                    + numDoc.trim() + ";" + telefono.trim() + ";" + direccion.trim() + ";"
                    + correo.trim() + ";" + clave.trim());
            bw.newLine();
        }

        // 2. Guarda los 4 campos de UsuarioJ en usuarios.txt para iniciar sesión en el Login
        String nombreCompleto = (nombre.trim() + " " + apellidos.trim()).trim();
        try (BufferedWriter bwLogin = new BufferedWriter(new FileWriter(ARCHIVO_USUARIOS, true))) {
            bwLogin.write(nombreCompleto + ";" + correo.trim() + ";" + clave.trim() + ";Usuario");
            bwLogin.newLine();
        }
    }

    public DefaultTableModel listarClientes() throws IOException {
        DefaultTableModel tabla = new DefaultTableModel();
        tabla.addColumn("ID");
        tabla.addColumn("Telefono");
        tabla.addColumn("N Documento");
        tabla.addColumn("Nombre");
        tabla.addColumn("Apellido");
        tabla.addColumn("Direccion");

        File archivo = new File(ARCHIVO_CLIENTES);
        if (!archivo.exists()) {
            return tabla;
        }

        int id = 1;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;
                String[] d = linea.split(";");
                if (d.length >= 6) {
                    tabla.addRow(new Object[]{
                        id++,        // ID
                        d[4].trim(), // Telefono
                        d[3].trim(), // N Documento
                        d[0].trim(), // Nombre
                        d[1].trim(), // Apellido
                        d[5].trim()  // Direccion
                    });
                }
            }
        }
        return tabla;
    }

    public String[] buscarClientePorDocumento(String numDoc) throws IOException {
        File archivo = new File(ARCHIVO_CLIENTES);
        if (!archivo.exists()) return null;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(";");
                if (d.length >= 8 && d[3].trim().equals(numDoc.trim())) {
                    return d;
                }
            }
        }
        return null;
    }

    public boolean actualizarCliente(String docBuscado, String nombre, String apellidos,
            String nuevoTipoDoc, String nuevoNumDoc, String telefono, String direccion,
            String correo, String nuevaClave) throws IOException {

        File archivo = new File(ARCHIVO_CLIENTES);
        if (!archivo.exists()) return false;

        List<String> lineas = new ArrayList<>();
        boolean actualizado = false;
        String correoAnterior = null;
        String nombreFinal = "", apellidosFinal = "", correoFinal = "", claveFinal = "";

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(";");
                if (d.length >= 8 && d[3].trim().equals(docBuscado.trim())) {
                    actualizado = true;
                    correoAnterior = d[6].trim();

                    nombreFinal = nombre.trim().isEmpty() ? d[0].trim() : nombre.trim();
                    apellidosFinal = apellidos.trim().isEmpty() ? d[1].trim() : apellidos.trim();
                    String tipoDocFinal = (nuevoTipoDoc == null || nuevoTipoDoc.equals("Selecciona una opcion")) ? d[2].trim() : nuevoTipoDoc.trim();
                    String numDocFinal = nuevoNumDoc.trim().isEmpty() ? d[3].trim() : nuevoNumDoc.trim();
                    String telFinal = telefono.trim().isEmpty() ? d[4].trim() : telefono.trim();
                    String dirFinal = direccion.trim().isEmpty() ? d[5].trim() : direccion.trim();
                    correoFinal = correo.trim().isEmpty() ? d[6].trim() : correo.trim();
                    claveFinal = nuevaClave.isEmpty() ? d[7].trim() : nuevaClave.trim();

                    lineas.add(nombreFinal + ";" + apellidosFinal + ";" + tipoDocFinal + ";"
                            + numDocFinal + ";" + telFinal + ";" + dirFinal + ";"
                            + correoFinal + ";" + claveFinal);
                } else {
                    lineas.add(linea);
                }
            }
        }

        if (actualizado) {
            escribirLineas(archivo, lineas);
            actualizarCredencialesLogin(correoAnterior, (nombreFinal + " " + apellidosFinal).trim(), correoFinal, claveFinal);
        }
        return actualizado;
    }

    public boolean eliminarClientePorDocumento(String numDoc) throws IOException {
        File archivo = new File(ARCHIVO_CLIENTES);
        if (!archivo.exists()) return false;

        List<String> lineas = new ArrayList<>();
        boolean eliminado = false;
        String correoAEliminar = null;

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] d = linea.split(";");
                if (d.length >= 7 && d[3].trim().equals(numDoc.trim())) {
                    eliminado = true;
                    correoAEliminar = d[6].trim();
                } else {
                    lineas.add(linea);
                }
            }
        }

        if (eliminado) {
            escribirLineas(archivo, lineas);
            eliminarCredencialesLogin(correoAEliminar);
        }
        return eliminado;
    }

    // =========================================================================
    // MÉTODOS AUXILIARES PARA SINCRONIZAR CON usuarios.txt (LOGIN)
    // =========================================================================

    private void escribirLineas(File archivo, List<String> lineas) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo))) {
            for (String l : lineas) {
                bw.write(l);
                bw.newLine();
            }
        }
    }

    private void actualizarCredencialesLogin(String correoAnterior, String nombreCompleto, String correoNuevo, String claveNueva) throws IOException {
        File archivoLogin = new File(ARCHIVO_USUARIOS);
        if (!archivoLogin.exists() || correoAnterior == null) return;

        List<String> lineasLogin = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivoLogin))) {
            String l;
            while ((l = br.readLine()) != null) {
                String[] dl = l.split(";");
                if (dl.length >= 4 && dl[1].trim().equalsIgnoreCase(correoAnterior)) {
                    lineasLogin.add(nombreCompleto + ";" + correoNuevo + ";" + claveNueva + ";" + dl[3].trim());
                } else {
                    lineasLogin.add(l);
                }
            }
        }
        escribirLineas(archivoLogin, lineasLogin);
    }

    private void eliminarCredencialesLogin(String correoAEliminar) throws IOException {
        File archivoLogin = new File(ARCHIVO_USUARIOS);
        if (!archivoLogin.exists() || correoAEliminar == null) return;

        List<String> lineasLogin = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivoLogin))) {
            String l;
            while ((l = br.readLine()) != null) {
                String[] dl = l.split(";");
                if (!(dl.length >= 2 && dl[1].trim().equalsIgnoreCase(correoAEliminar))) {
                    lineasLogin.add(l);
                }
            }
        }
        escribirLineas(archivoLogin, lineasLogin);
    }
}