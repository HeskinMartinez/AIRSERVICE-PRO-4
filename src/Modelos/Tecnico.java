/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Heskin Martinez
 */
public class Tecnico {
    
    // Posicion de cada dato en una fila de tecnicos.txt
    public static final int ID = 0;
    public static final int ID_USUARIO = 1;
    public static final int NOMBRE = 2;
    public static final int CORREO = 3;
    public static final int TELEFONO = 4;
    public static final int CIUDAD = 5;
    public static final int FECHA_NACIMIENTO = 6;
    public static final int DOCUMENTO = 7;
    public static final int ESPECIALIDAD = 8;
    public static final int ANOS_EXPERIENCIA = 9;
    public static final int DESCRIPCION = 10;

    /**
     * Clientes guardados desde el registro, ordenados por nombre.
     * Cada fila: nombre, correo, telefono, ciudad.
     */
    public List<String[]> listarClientes() throws IOException {
        Almacen.iniciar();
        List<String[]> clientes = new ArrayList<>();
        for (String[] fila : Almacen.leer(Almacen.CLIENTES)) {
            if (fila.length >= 4) {
                clientes.add(new String[]{fila[0], fila[1], fila[2], fila[3]});
            }
        }
        clientes.sort((a, b) -> String.CASE_INSENSITIVE_ORDER.compare(a[0], b[0]));
        return clientes;
    }

    // Estados y prioridades de una solicitud
    public static final String ESTADO_PENDIENTE = "Pendiente";

    /**
     * Guarda una solicitud de servicio nueva (SOL-001...) y se la asigna al
     * tecnico que tenga menos solicitudes pendientes. Si todavia no hay
     * tecnicos queda sin asignar y la ven todos. Devuelve el ID creado.
     */
    public String registrarSolicitud(String cliente, String servicio) throws IOException {
        Almacen.iniciar();
        String id = Almacen.formatoIdSolicitud(Almacen.siguienteNumeroSolicitud());
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        Almacen.agregar(Almacen.SOLICITUDES, id, cliente, servicio, fecha, ESTADO_PENDIENTE,
                prioridadDe(servicio), tecnicoConMenosCarga());
        return id;
    }

    /**
     * Solicitudes de un tecnico (y las que aun no tienen tecnico), en el orden
     * en que llegaron. Cada fila: id, cliente, servicio, fecha, estado, prioridad.
     */
    public List<String[]> listarSolicitudes(String idTecnico) throws IOException {
        Almacen.iniciar();
        List<String[]> solicitudes = new ArrayList<>();
        for (String[] fila : Almacen.leer(Almacen.SOLICITUDES)) {
            if (fila.length >= 7 && (fila[6].trim().isEmpty() || fila[6].trim().equals(idTecnico))) {
                solicitudes.add(new String[]{fila[0], fila[1], fila[2], fila[3], fila[4], fila[5]});
            }
        }
        return solicitudes;
    }

    /** Reparacion = Alta, Instalaciones = Media, el resto = Baja. */
    private String prioridadDe(String servicio) {
        String s = servicio == null ? "" : servicio.toLowerCase();
        if (s.contains("reparacion") || s.contains("reparación")) {
            return "Alta";
        }
        if (s.contains("instalacion") || s.contains("instalación")) {
            return "Media";
        }
        return "Baja";
    }

    /** ID del tecnico con menos solicitudes pendientes ("" si no hay tecnicos). */
    private String tecnicoConMenosCarga() throws IOException {
        Map<String, Integer> pendientes = new HashMap<>();
        for (String[] fila : Almacen.leer(Almacen.SOLICITUDES)) {
            if (fila.length >= 7 && ESTADO_PENDIENTE.equals(fila[4].trim())) {
                pendientes.merge(fila[6].trim(), 1, Integer::sum);
            }
        }
        String elegido = "";
        int menor = Integer.MAX_VALUE;
        for (String[] fila : Almacen.leer(Almacen.TECNICOS)) {
            if (fila.length == 11 && !fila[ID].trim().isEmpty()) {
                int carga = pendientes.getOrDefault(fila[ID].trim(), 0);
                if (carga < menor) {
                    menor = carga;
                    elegido = fila[ID].trim();
                }
            }
        }
        return elegido;
    }

    /** Guarda en materiales_usados.txt cuanto uso el tecnico de un material. */
    public void registrarUsoMaterial(String idTecnico, String codigo, String material, int cantidad)
            throws IOException {
        Almacen.iniciar();
        String fecha = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        Almacen.agregar(Almacen.MATERIALES_USADOS, fecha, idTecnico, codigo, material,
                String.valueOf(cantidad));
    }

    /** Total usado de cada material (codigo -> cantidad), sumando todo lo registrado. */
    public Map<String, Integer> materialesUsadosPorCodigo() throws IOException {
        Almacen.iniciar();
        Map<String, Integer> totales = new HashMap<>();
        for (String[] fila : Almacen.leer(Almacen.MATERIALES_USADOS)) {
            if (fila.length >= 5) {
                try {
                    totales.merge(fila[2].trim(), Integer.parseInt(fila[4].trim()), Integer::sum);
                } catch (NumberFormatException e) {
                    // linea danada: se ignora
                }
            }
        }
        return totales;
    }

    /** Fila completa del perfil de ese usuario (11 datos) o null si no existe. */
    public String[] obtenerPerfil(int idUsuario) throws IOException {
        Almacen.iniciar();
        for (String[] fila : Almacen.leer(Almacen.TECNICOS)) {
            if (fila.length == 11 && fila[ID_USUARIO].trim().equals(String.valueOf(idUsuario))) {
                return fila;
            }
        }
        return null;
    }

    /** True si otro usuario (distinto a este) ya usa ese correo. */
    public boolean correoEnUsoPorOtro(String correo, int idUsuario) throws IOException {
        Almacen.iniciar();
        String buscado = Modelo_registro.normalizar(correo);
        for (String[] u : Almacen.leer(Almacen.USUARIOS)) {
            if (u.length >= 3 && u[2].equalsIgnoreCase(buscado)
                    && !u[0].trim().equals(String.valueOf(idUsuario))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Guarda el perfil en tecnicos.txt y, como nombre y correo tambien estan
     * en usuarios.txt, los actualiza ahi tambien. El ID del tecnico no cambia.
     */
    public void guardarPerfil(int idUsuario, String nombre, String correo, String telefono,
            String ciudad, String fechaNacimiento, String documento, String especialidad,
            String anosExperiencia, String descripcion) throws IOException {

        Almacen.iniciar();
        String id = String.valueOf(idUsuario);
        String correoNormal = Modelo_registro.normalizar(correo);

        List<String[]> tecnicos = Almacen.leer(Almacen.TECNICOS);
        boolean encontrado = false;
        for (int i = 0; i < tecnicos.size(); i++) {
            String[] fila = tecnicos.get(i);
            if (fila.length == 11 && fila[ID_USUARIO].trim().equals(id)) {
                tecnicos.set(i, new String[]{fila[ID], id, nombre, correoNormal, telefono, ciudad,
                    fechaNacimiento, documento, especialidad, anosExperiencia, descripcion});
                encontrado = true;
            }
        }
        if (!encontrado) {
            tecnicos.add(new String[]{Almacen.formatoIdTecnico(Almacen.siguienteNumeroTecnico()), id,
                nombre, correoNormal, telefono, ciudad, fechaNacimiento, documento, especialidad,
                anosExperiencia, descripcion});
        }

        List<String[]> usuarios = Almacen.leer(Almacen.USUARIOS);
        for (String[] u : usuarios) {
            if (u.length >= 5 && u[0].trim().equals(id)) {
                u[1] = nombre;
                u[2] = correoNormal;
            }
        }

        Almacen.reescribir(Almacen.USUARIOS, usuarios);
        Almacen.reescribir(Almacen.TECNICOS, tecnicos);
        Modelo_login.actualizarSesion(nombre.trim(), correoNormal);
    }
}
