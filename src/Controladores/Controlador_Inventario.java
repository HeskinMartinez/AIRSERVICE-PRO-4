
package Controladores;
import Modelos.Material;
import java.util.ArrayList;

public class Controlador_Inventario {
    private ArrayList<Material> listaMateriales;

    public Controlador_Inventario() {
        listaMateriales = new ArrayList<>();
        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        listaMateriales.add(new Material("MAT-001", "Kit de tubería", 100000, 8));
        listaMateriales.add(new Material("MAT-002", "1 kilo de gas", 60000, 12));
        listaMateriales.add(new Material("MAT-003", "Metro de cable #12", 3700, 50));
        listaMateriales.add(new Material("MAT-004", "1 cuña de 20A", 35000, 4));
        listaMateriales.add(new Material("MAT-005", "Pie amigo para aire", 36000, 10));
        listaMateriales.add(new Material("MAT-006", "Rubate", 6000, 25));
        listaMateriales.add(new Material("MAT-007", "Cinta de vinilo", 10000, 15));
        listaMateriales.add(new Material("MAT-008", "Tubería de 1/4 por metro", 9500, 3));
        listaMateriales.add(new Material("MAT-009", "Tubería de 1/2 por metro", 7500, 0));
    }

    public ArrayList<Material> obtenerMateriales() {
        return listaMateriales;
    }

    public double calcularCostoSolicitud(String nombreMaterial, int cantidad) {
        for (Material m : listaMateriales) {
            if (m.getNombre().equals(nombreMaterial)) {
                return m.getPrecioUnitario() * cantidad;
            }
        }
        return 0;
    }
}

