/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Modelos.Modelo_registro;
import Vistas.Login;
import Vistas.Registro;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author Heskin Martinez
 */
public class Controlador_registro {
    
    private final Registro vista;
    private final Modelo_registro modelo = new Modelo_registro();

    public Controlador_registro(Registro vista) {
        this.vista = vista;
    }

    public void registrar(String nombre, String correo, String clave,
            String rol, String telefono, String ciudad) {

        nombre = nombre == null ? "" : nombre.trim();
        correo = correo == null ? "" : correo.trim();
        clave = clave == null ? "" : clave;
        telefono = telefono == null ? "" : telefono.trim();
        ciudad = ciudad == null ? "" : ciudad.trim();

        String error = validar(nombre, correo, clave, rol, telefono, ciudad);
        if (error != null) {
            aviso(error, JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if (modelo.existeCorreo(correo)) {
                aviso("Ya existe una cuenta con ese correo.", JOptionPane.WARNING_MESSAGE);
                return;
            }
            modelo.registrar(nombre, correo, clave, rol, telefono, ciudad);
            aviso("Cuenta creada. Ya puedes iniciar sesion.", JOptionPane.INFORMATION_MESSAGE);
            volverAlLogin();
        } catch (IOException e) {
            aviso("No se pudo guardar la cuenta: " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    private String validar(String nombre, String correo, String clave,
            String rol, String telefono, String ciudad) {
        if (nombre.length() < 3) {
            return "Escribe tu nombre completo.";
        }
        if (!Validaciones.correoValido(correo)) {
            return Validaciones.MENSAJE_CORREO;
        }
        if (clave.length() < 6) {
            return "La contrasena debe tener al menos 6 caracteres.";
        }
        if (!Validaciones.telefonoValido(telefono)) {
            return Validaciones.MENSAJE_TELEFONO;
        }
        if (ciudad.isEmpty()) {
            return "Escribe tu ciudad.";
        }
        if (!Modelo_registro.ROL_TECNICO.equals(rol) && !Modelo_registro.ROL_CLIENTE.equals(rol)) {
            return "Selecciona un rol.";
        }
        return null;
    }

    public void volverAlLogin() {
        java.awt.EventQueue.invokeLater(() -> new Login().setVisible(true));
        vista.dispose();
    }

    private void aviso(String mensaje, int tipo) {
        JOptionPane.showMessageDialog(vista, mensaje, "Registro", tipo);
    }
}
