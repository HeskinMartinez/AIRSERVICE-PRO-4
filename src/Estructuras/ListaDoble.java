/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Estructuras;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

 

public class ListaDoble<T> implements Iterable<T> {

    private static class Nodo<T> {
        T dato;
        Nodo<T> anterior, siguiente;
        Nodo(T dato) { this.dato = dato; }
    }

    private Nodo<T> cabeza, cola;
    private int tamano;


    public void agregarInicio(T dato) {
        Nodo<T> n = new Nodo<>(dato);
        if (cabeza == null) { cabeza = cola = n; }
        else { n.siguiente = cabeza; cabeza.anterior = n; cabeza = n; }
        tamano++;
    }

    public void agregarFinal(T dato) {
        Nodo<T> n = new Nodo<>(dato);
        if (cola == null) { cabeza = cola = n; }
        else { n.anterior = cola; cola.siguiente = n; cola = n; }
        tamano++;
    }

    private Nodo<T> nodoEn(int i) {
        if (i < 0 || i >= tamano) throw new IndexOutOfBoundsException("Indice: " + i);
        Nodo<T> a;
        if (i < tamano / 2) { a = cabeza; for (int k = 0; k < i; k++) a = a.siguiente; }
        else { a = cola; for (int k = tamano - 1; k > i; k--) a = a.anterior; }
        return a;
    }

    public T obtener(int i)              { return nodoEn(i).dato; }
    public void reemplazar(int i, T dato) { nodoEn(i).dato = dato; }

    public T eliminar(int i) {
        Nodo<T> n = nodoEn(i);
        if (n.anterior == null) cabeza = n.siguiente; else n.anterior.siguiente = n.siguiente;
        if (n.siguiente == null) cola = n.anterior;   else n.siguiente.anterior = n.anterior;
        tamano--;
        return n.dato;
    }

    public int size()           { return tamano; }
    public boolean estaVacia()  { return tamano == 0; }
    public void limpiar()       { cabeza = cola = null; tamano = 0; }

    /** Ordenamiento burbuja sobre la lista doble. */
    public void ordenar(Comparator < ? super T> c) {
        if (tamano < 2) return;
        boolean huboCambio;
        do {
            huboCambio = false;
            for (Nodo<T> a = cabeza; a.siguiente != null; a = a.siguiente) {
                if (c.compare(a.dato, a.siguiente.dato) > 0) {
                    T tmp = a.dato; a.dato = a.siguiente.dato; a.siguiente.dato = tmp;
                    huboCambio = true;
                }
            }
        } while (huboCambio);
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Nodo<T> actual = cabeza;
            public boolean hasNext() { return actual != null; }
            public T next() {
                if (actual == null) throw new NoSuchElementException();
                T d = actual.dato; actual = actual.siguiente; return d;
            }
        };
    }

    /** Recorrido de cola a cabeza (aprovecha el enlace "anterior"). */
    public Iterable<T> reversa() {
        return () -> new Iterator<T>() {
            Nodo<T> actual = cola;
            public boolean hasNext() { return actual != null; }
            public T next() {
                if (actual == null) throw new NoSuchElementException();
                T d = actual.dato; actual = actual.anterior; return d;
            }
        };
    }
}

