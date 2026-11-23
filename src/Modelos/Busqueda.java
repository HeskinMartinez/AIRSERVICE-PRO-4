/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

/**
 * Ordenamiento y busquedas sobre la lista de materiales:
 * insercion para ordenar por codigo, busqueda binaria por codigo y
 * busqueda lineal por nombre.
 */

/**
 *
 * @author Heskin Martinez
 */
public class Busqueda {
    /** Copia de la lista ordenada por codigo (ordenamiento por insercion). */
    public static List<Material> ordenarPorCodigo(List<Material> materiales) {
        List<Material> lista = new ArrayList<>(materiales);
        for (int i = 1; i < lista.size(); i++) {
            Material actual = lista.get(i);
            int j = i - 1;
            while (j >= 0 && lista.get(j).getCodigo().compareToIgnoreCase(actual.getCodigo()) > 0) {
                lista.set(j + 1, lista.get(j));
                j--;
            }
            lista.set(j + 1, actual);
        }
        return lista;
    }

    /** Busqueda binaria por codigo exacto. La lista debe venir ordenada por codigo. */
    public static Material buscarPorCodigo(List<Material> ordenadaPorCodigo, String codigo) {
        int bajo = 0;
        int alto = ordenadaPorCodigo.size() - 1;
        while (bajo <= alto) {
            int medio = (bajo + alto) / 2;
            Material m = ordenadaPorCodigo.get(medio);
            int comparacion = codigo.compareToIgnoreCase(m.getCodigo());
            if (comparacion == 0) {
                return m;
            }
            if (comparacion < 0) {
                alto = medio - 1;
            } else {
                bajo = medio + 1;
            }
        }
        return null;
    }

    /** Busqueda lineal: materiales cuyo nombre contiene el texto (sin importar mayusculas ni tildes). */
    public static List<Material> buscarPorNombre(List<Material> materiales, String texto) {
        String buscado = sinTildes(texto);
        List<Material> encontrados = new ArrayList<>();
        for (Material m : materiales) {
            if (sinTildes(m.getNombre()).contains(buscado)) {
                encontrados.add(m);
            }
        }
        return encontrados;
    }

    private static String sinTildes(String texto) {
        return Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .toLowerCase().trim();
    }
}
