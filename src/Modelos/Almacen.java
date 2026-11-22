/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

/**
 * Guarda y lee los datos en archivos de texto dentro de la carpeta "datos".
 * Cada linea es un registro y los campos van separados por "|".
 *
 * usuarios.txt: id|nombre|correo|contrasena|rol
 * tecnicos.txt: id_usuario|telefono|ciudad
 * clientes.txt: nombre|correo|telefono|ciudad
 */

/**
 *
 * @author Heskin Martinez
 */
public class Almacen {
    static final String CARPETA = "datos";
    static final String USUARIOS = "usuarios.txt";
    static final String TECNICOS = "tecnicos.txt";
    static final String CLIENTES = "clientes.txt";

    // Cuenta de administrador que se crea sola la primera vez.
    private static final String ADMIN_NOMBRE = "Administrador";
    private static final String ADMIN_CORREO = "admin@airservice.com";
    private static final String ADMIN_CLAVE = "admin123";

    private static boolean iniciado = false;

    /** Crea la carpeta, los archivos y el admin si todavia no existen. */
    public static synchronized void iniciar() throws IOException {
        if (iniciado) {
            return;
        }
        Files.createDirectories(Paths.get(CARPETA));
        for (String archivo : new String[]{USUARIOS, TECNICOS, CLIENTES}) {
            Path ruta = ruta(archivo);
            if (!Files.exists(ruta)) {
                Files.createFile(ruta);
            }
        }
        migrarTecnicosAntiguos();
        boolean hayAdmin = false;
        for (String[] fila : leer(USUARIOS)) {
            if (fila.length >= 5 && Modelo_registro.ROL_ADMIN.equals(fila[4])) {
                hayAdmin = true;
            }
        }
        if (!hayAdmin) {
            agregar(USUARIOS, String.valueOf(siguienteIdUsuario()), ADMIN_NOMBRE, ADMIN_CORREO,
                    Seguridad.protegerClave(ADMIN_CLAVE), Modelo_registro.ROL_ADMIN);
        }
        iniciado = true;
    }

    /**
     * Pasa tecnicos.txt de los formatos viejos (id|telefono|ciudad y
     * nombre|correo|telefono|ciudad) al formato completo, con un ID de
     * tecnico (TEC-001...). Las lineas con otro formato no se tocan.
     */
    private static void migrarTecnicosAntiguos() throws IOException {
        List<String[]> filas = leer(TECNICOS);
        boolean hayAntiguas = false;
        for (String[] fila : filas) {
            if (fila.length == 3 || fila.length == 4) {
                hayAntiguas = true;
            }
        }
        if (!hayAntiguas) {
            return;
        }
        List<String[]> usuarios = leer(USUARIOS);
        int mayor = 0;
        for (String[] fila : filas) {
            if (fila.length == 11) {
                mayor = Math.max(mayor, numeroTecnico(fila[0]));
            }
        }
        List<String[]> nuevas = new ArrayList<>();
        for (String[] fila : filas) {
            if (fila.length != 3 && fila.length != 4) {
                nuevas.add(fila);
                continue;
            }
            String idUsuario = "";
            String nombre = "";
            String correo = "";
            String telefono;
            String ciudad;
            if (fila.length == 3) { // id|telefono|ciudad
                idUsuario = fila[0].trim();
                telefono = fila[1];
                ciudad = fila[2];
                for (String[] u : usuarios) {
                    if (u.length >= 3 && u[0].trim().equals(idUsuario)) {
                        nombre = u[1];
                        correo = u[2];
                    }
                }
            } else { // nombre|correo|telefono|ciudad
                nombre = fila[0];
                correo = fila[1];
                telefono = fila[2];
                ciudad = fila[3];
                for (String[] u : usuarios) {
                    if (u.length >= 3 && u[2].equalsIgnoreCase(correo.trim())) {
                        idUsuario = u[0].trim();
                    }
                }
            }
            mayor++;
            nuevas.add(new String[]{formatoIdTecnico(mayor), idUsuario, nombre, correo,
                telefono, ciudad, "", "", "", "", ""});
        }
        reescribir(TECNICOS, nuevas);
    }

    static List<String[]> leer(String archivo) throws IOException {
        List<String[]> filas = new ArrayList<>();
        Path ruta = ruta(archivo);
        if (!Files.exists(ruta)) {
            return filas;
        }
        for (String linea : Files.readAllLines(ruta, StandardCharsets.UTF_8)) {
            if (!linea.trim().isEmpty()) {
                filas.add(linea.split("\\|", -1));
            }
        }
        return filas;
    }

    static void agregar(String archivo, String... campos) throws IOException {
        Files.write(ruta(archivo), (unir(campos) + System.lineSeparator()).getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    /** Cambia todo el contenido del archivo por estas filas. */
    static void reescribir(String archivo, List<String[]> filas) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (String[] fila : filas) {
            sb.append(unir(fila)).append(System.lineSeparator());
        }
        Files.write(ruta(archivo), sb.toString().getBytes(StandardCharsets.UTF_8));
    }

    static int siguienteIdUsuario() throws IOException {
        int mayor = 0;
        for (String[] fila : leer(USUARIOS)) {
            try {
                mayor = Math.max(mayor, Integer.parseInt(fila[0].trim()));
            } catch (NumberFormatException e) {
                // linea danada: se ignora
            }
        }
        return mayor + 1;
    }

    /** Siguiente numero libre para el ID del tecnico (TEC-001, TEC-002...). */
    static int siguienteNumeroTecnico() throws IOException {
        int mayor = 0;
        for (String[] fila : leer(TECNICOS)) {
            if (fila.length > 0) {
                mayor = Math.max(mayor, numeroTecnico(fila[0]));
            }
        }
        return mayor + 1;
    }

    static String formatoIdTecnico(int numero) {
        return String.format("TEC-%03d", numero);
    }

    private static int numeroTecnico(String id) {
        if (id != null && id.trim().startsWith("TEC-")) {
            try {
                return Integer.parseInt(id.trim().substring(4));
            } catch (NumberFormatException e) {
                return 0;
            }
        }
        return 0;
    }

    private static String unir(String[] campos) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < campos.length; i++) {
            if (i > 0) {
                sb.append('|');
            }
            sb.append(limpiar(campos[i]));
        }
        return sb.toString();
    }

    private static Path ruta(String archivo) {
        return Paths.get(CARPETA, archivo);
    }

    /** Quita el separador y los saltos de linea para no romper el archivo. */
    private static String limpiar(String texto) {
        return texto == null ? "" : texto.replace('|', ' ').replace('\r', ' ').replace('\n', ' ').trim();
    }
    
}
