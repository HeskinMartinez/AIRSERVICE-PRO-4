/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
/**
 *
 * @author Heskin Martinez
 */
public class Seguridad {
    
    private static final SecureRandom ALEATORIO = new SecureRandom();

    public static String protegerClave(String clave) {
        byte[] sal = new byte[16];
        ALEATORIO.nextBytes(sal);
        String salHex = aHex(sal);
        return salHex + ":" + sha256(salHex + clave);
    }

    public static boolean verificarClave(String clave, String almacenada) {
        if (clave == null || almacenada == null) {
            return false;
        }
        int separador = almacenada.indexOf(':');
        if (separador < 0) {
            return false;
        }
        String salHex = almacenada.substring(0, separador);
        String esperado = almacenada.substring(separador + 1);
        return MessageDigest.isEqual(
                sha256(salHex + clave).getBytes(StandardCharsets.UTF_8),
                esperado.getBytes(StandardCharsets.UTF_8));
    }

    private static String sha256(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return aHex(md.digest(texto.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 no disponible", e);
        }
    }

    private static String aHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
