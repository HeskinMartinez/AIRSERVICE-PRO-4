/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author Heskin Martinez
 */
public class ConexionBD {
    
    private static final String URL = "jdbc:sqlite:airservice.db";

    public static Connection conectar() {
        Connection conexion = null;

        try {
            conexion = DriverManager.getConnection(URL);
            System.out.println("Conexion a SQLite exitosa.");

        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }

        return conexion;
    }
}
