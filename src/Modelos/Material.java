/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

public class Material {
    private String codigo;
    private String nombre;
    private double precioUnitario;
    private int stockDisponible;

    public Material(String codigo, String nombre, double precioUnitario, int stockDisponible) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precioUnitario = precioUnitario;
        this.stockDisponible = stockDisponible;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public double getPrecioUnitario() { return precioUnitario; }
    public int getStockDisponible() { return stockDisponible; }
    public void setStockDisponible(int stockDisponible) { this.stockDisponible = stockDisponible; }

    public String getEstadoAlmacen() {
        if (stockDisponible == 0) return "AGOTADO";
        if (stockDisponible <= 5) return "BAJO STOCK";
        return "DISPONIBLE";
    }
}