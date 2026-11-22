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
public class Modelo_registro {
    
    public static final String ROL_TECNICO = "Tecnico";
    public static final String ROL_CLIENTE = "Cliente";
    public static final String ROL_ADMIN = "Admin";

    public boolean existeCorreo(String correo) throws IOException {
        Almacen.iniciar();
        String buscado = normalizar(correo);
        for (String[] fila : Almacen.leer(Almacen.USUARIOS)) {
            if (fila.length >= 3 && fila[2].equalsIgnoreCase(buscado)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Si el rol es Tecnico guarda tambien su perfil (con su ID TEC-001...) en tecnicos.txt;
     * si es Cliente, sus datos en clientes.txt.
     */
    public void registrar(String nombre, String correo, String clave,
            String rol, String telefono, String ciudad) throws IOException {

        Almacen.iniciar();
        String id = String.valueOf(Almacen.siguienteIdUsuario());
        String correoNormal = normalizar(correo);

        if (ROL_TECNICO.equals(rol)) {
            Almacen.agregar(Almacen.TECNICOS,
                    Almacen.formatoIdTecnico(Almacen.siguienteNumeroTecnico()), id,
                    nombre, correoNormal, telefono, ciudad, "", "", "", "", "");
        } else if (ROL_CLIENTE.equals(rol)) {
            Almacen.agregar(Almacen.CLIENTES, nombre, correoNormal, telefono, ciudad);
        }
        Almacen.agregar(Almacen.USUARIOS, id, nombre, correoNormal, Seguridad.protegerClave(clave), rol);
    }

    static String normalizar(String correo) {
        return correo == null ? "" : correo.trim().toLowerCase();
    }
}
