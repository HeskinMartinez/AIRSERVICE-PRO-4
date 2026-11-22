/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;


import Modelos.Modelo_login;
import Modelos.Modelo_registro;
import Vistas.Login;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author Heskin Martinez
 */
public class Controlador_login {
    
    private final Login vista;
    private final Modelo_login modelo = new Modelo_login();

    public Controlador_login(Login vista) {
        this.vista = vista;
    }

    /**
     * @param rolElegido "Tecnico", "Cliente" o null si no marco ninguna casilla.
     *                   Solo el admin puede entrar sin elegir rol.
     */
    public void iniciarSesion(String correo, String clave, String rolElegido) {
        correo = correo == null ? "" : correo.trim();
        if (correo.isEmpty() || clave == null || clave.isEmpty()) {
            aviso("Escribe tu correo y tu contrasena.", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if (!modelo.autenticar(correo, clave)) {
                aviso("Correo o contrasena incorrectos.", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (IOException e) {
            aviso("No se pudieron leer los datos: " + e.getMessage(), JOptionPane.ERROR_MESSAGE);
            return;
        }

        String rol = Modelo_login.getRolActual();

        // El admin entra sin elegir rol. Tecnico y Cliente deben marcar el suyo.
        if (!Modelo_registro.ROL_ADMIN.equals(rol)) {
            if (rolElegido == null) {
                Modelo_login.cerrarSesion();
                aviso("Selecciona si eres Tecnico o Cliente.", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!rolElegido.equals(rol)) {
                Modelo_login.cerrarSesion();
                aviso("Esta cuenta no es de tipo " + rolElegido + ".", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        if (!Modelo_registro.ROL_TECNICO.equals(rol)
                && !Modelo_registro.ROL_CLIENTE.equals(rol)
                && !Modelo_registro.ROL_ADMIN.equals(rol)) {
            Modelo_login.cerrarSesion();
            aviso("El rol de esta cuenta no es valido: " + rol, JOptionPane.ERROR_MESSAGE);
            return;
        }

        java.awt.EventQueue.invokeLater(() -> {
            if (Modelo_registro.ROL_TECNICO.equals(rol)) {
                new Vistas.Tecnico().setVisible(true);
            } else if (Modelo_registro.ROL_CLIENTE.equals(rol)) {
                new Vistas.Usuarios().setVisible(true);
            } else {
                new Vistas.Admin().setVisible(true);
            }
        });
        vista.dispose();
    }

    private void aviso(String mensaje, int tipo) {
        JOptionPane.showMessageDialog(vista, mensaje, "Iniciar sesion", tipo);
    }
}
