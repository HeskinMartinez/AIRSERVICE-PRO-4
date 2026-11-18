/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

/**
 *
 * @author Heskin Martinez
 */
public class CrearTablasBD {
    
    public static void crearTablas() {

        Connection conexion = ConexionBD.conectar();

        if (conexion == null) {
            System.out.println("No se pudo conectar a la base de datos.");
            return;
        }

        try (Statement sentencia = conexion.createStatement()) {

            // ACTIVAR RELACIONES ENTRE TABLAS
            sentencia.execute("PRAGMA foreign_keys = ON");

            // 1. TABLA USUARIOS
            String usuarios = "CREATE TABLE IF NOT EXISTS usuarios ("
                    + "id_usuario INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "nombre TEXT NOT NULL,"
                    + "correo TEXT NOT NULL UNIQUE,"
                    + "contrasena TEXT NOT NULL,"
                    + "rol TEXT NOT NULL"
                    + ")";

            sentencia.executeUpdate(usuarios);

            // 2. TABLA TECNICOS
            String tecnicos = "CREATE TABLE IF NOT EXISTS tecnicos ("
                    + "id_tecnico INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "id_usuario INTEGER NOT NULL UNIQUE,"
                    + "especialidad TEXT,"
                    + "telefono TEXT,"
                    + "FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)"
                    + ")";

            sentencia.executeUpdate(tecnicos);

            // 3. TABLA CLIENTES
            String clientes = "CREATE TABLE IF NOT EXISTS clientes ("
                    + "id_cliente INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "nombre TEXT NOT NULL,"
                    + "telefono TEXT,"
                    + "correo TEXT,"
                    + "direccion TEXT"
                    + ")";

            sentencia.executeUpdate(clientes);

            // 4. TABLA SERVICIOS
            String servicios = "CREATE TABLE IF NOT EXISTS servicios ("
                    + "id_servicio INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "id_cliente INTEGER NOT NULL,"
                    + "id_tecnico INTEGER,"
                    + "descripcion TEXT NOT NULL,"
                    + "fecha TEXT,"
                    + "estado TEXT NOT NULL,"
                    + "FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente),"
                    + "FOREIGN KEY (id_tecnico) REFERENCES tecnicos(id_tecnico)"
                    + ")";

            sentencia.executeUpdate(servicios);

            // 5. TABLA MATERIALES
            String materiales = "CREATE TABLE IF NOT EXISTS materiales ("
                    + "id_material INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "nombre TEXT NOT NULL,"
                    + "categoria TEXT,"
                    + "cantidad INTEGER NOT NULL DEFAULT 0,"
                    + "precio REAL DEFAULT 0"
                    + ")";

            sentencia.executeUpdate(materiales);

            // 6. TABLA DETALLE DE MATERIALES UTILIZADOS
            String detalleMaterial = "CREATE TABLE IF NOT EXISTS detalle_material_servicio ("
                    + "id_detalle INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "id_servicio INTEGER NOT NULL,"
                    + "id_material INTEGER NOT NULL,"
                    + "cantidad_usada INTEGER NOT NULL,"
                    + "FOREIGN KEY (id_servicio) REFERENCES servicios(id_servicio),"
                    + "FOREIGN KEY (id_material) REFERENCES materiales(id_material),"
                    + "UNIQUE (id_servicio, id_material)"
                    + ")";

            sentencia.executeUpdate(detalleMaterial);

            // 7. TABLA MOVIMIENTOS FINANCIEROS
            String finanzas = "CREATE TABLE IF NOT EXISTS movimientos_financieros ("
                    + "id_movimiento INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "id_servicio INTEGER,"
                    + "tipo TEXT NOT NULL,"
                    + "valor REAL NOT NULL,"
                    + "descripcion TEXT,"
                    + "fecha TEXT,"
                    + "FOREIGN KEY (id_servicio) REFERENCES servicios(id_servicio)"
                    + ")";

            sentencia.executeUpdate(finanzas);

            // 8. TABLA HISTORIAL
            String historial = "CREATE TABLE IF NOT EXISTS historial ("
                    + "id_historial INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "id_usuario INTEGER,"
                    + "accion TEXT NOT NULL,"
                    + "fecha TEXT,"
                    + "FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)"
                    + ")";

            sentencia.executeUpdate(historial);

            System.out.println("================================");
            System.out.println("AIRSERVICE PRO");
            System.out.println("Base de datos preparada.");
            System.out.println("Las 8 tablas fueron creadas.");
            System.out.println("================================");

        } catch (SQLException e) {

            System.out.println("Error al crear las tablas: " + e.getMessage());

        } finally {

            try {
                conexion.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexion: " + e.getMessage());
            }
        }
    }
    
}
