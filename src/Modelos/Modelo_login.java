/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.io.IOException;

/**
 *
 * @author Heskin Martinez
 */
public class Modelo_login {
    
    private static int idUsuarioActual;
    private static String nombreActual;
    private static String correoActual;
    private static String rolActual;

    /** Devuelve true si las credenciales son correctas y deja la sesion iniciada. */
    public boolean autenticar(String correo, String clave) throws IOException {
        Almacen.iniciar();
        String buscado = Modelo_registro.normalizar(correo);
        for (String[] fila : Almacen.leer(Almacen.USUARIOS)) {
            if (fila.length >= 5 && fila[2].equalsIgnoreCase(buscado)) {
                if (Seguridad.verificarClave(clave, fila[3])) {
                    try {
                        idUsuarioActual = Integer.parseInt(fila[0].trim());
                    } catch (NumberFormatException e) {
                        idUsuarioActual = 0;
                    }
                    nombreActual = fila[1];
                    correoActual = fila[2];
                    rolActual = fila[4];
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public static int getIdUsuarioActual() { return idUsuarioActual; }
    public static String getNombreActual() { return nombreActual; }
    public static String getCorreoActual() { return correoActual; }
    public static String getRolActual() { return rolActual; }

    /** Para cuando el usuario cambia su nombre o correo estando dentro. */
    public static void actualizarSesion(String nombre, String correo) {
        nombreActual = nombre;
        correoActual = correo;
    }

    public static void cerrarSesion() {
        idUsuarioActual = 0;
        nombreActual = null;
        correoActual = null;
        rolActual = null;
    }
}
