/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author Heskin Martinez
 */
public class Validaciones {
    public static final String MENSAJE_CORREO
            = "Correo mal escrito. Debe ser Gmail, Hotmail, Outlook o Yahoo, por ejemplo nombre@gmail.com.";
    public static final String MENSAJE_TELEFONO
            = "El telefono debe tener solo numeros (entre 7 y 10 digitos).";

    private static final Pattern CORREO = Pattern.compile("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$");

    // Unicos dominios aceptados, escritos exactamente asi.
    private static final List<String> DOMINIOS_VALIDOS
            = Arrays.asList("gmail.com", "hotmail.com", "outlook.com", "yahoo.com");

    private static final DateTimeFormatter FECHA
            = DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    public static boolean correoValido(String correo) {
        if (correo == null || !CORREO.matcher(correo).matches()) {
            return false;
        }
        String dominio = correo.substring(correo.indexOf('@') + 1).toLowerCase();
        return DOMINIOS_VALIDOS.contains(dominio);
    }

    public static boolean telefonoValido(String telefono) {
        return soloNumeros(telefono, 7, 10);
    }

    /** Solo digitos y con una cantidad de digitos entre minimo y maximo. */
    public static boolean soloNumeros(String texto, int minimo, int maximo) {
        return texto != null && texto.matches("\\d{" + minimo + "," + maximo + "}");
    }

    /** Fecha dd/mm/aaaa real, que no sea futura ni anterior a 1900. */
    public static boolean fechaNacimientoValida(String texto) {
        try {
            LocalDate fecha = LocalDate.parse(texto, FECHA);
            return !fecha.isAfter(LocalDate.now()) && fecha.getYear() >= 1900;
        } catch (DateTimeParseException | NullPointerException e) {
            return false;
        }
    }

    /** Entero de 0 a 60. */
    public static boolean anosExperienciaValidos(String texto) {
        return texto != null && texto.matches("\\d{1,2}") && Integer.parseInt(texto) <= 60;
    }
    
}
