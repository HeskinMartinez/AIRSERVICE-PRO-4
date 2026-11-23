/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

/**
 *
 * @author ACER1
 */
public class Pago {
    
    private int numero;
    private String cliente,servicio,metodo;
    private double total;

    public Pago(int numero, String cliente, String servicio, String metodo, double total) {
        this.numero = numero;
        this.cliente = cliente;
        this.servicio = servicio;
        this.metodo = metodo;
        this.total = total;
    }

    public String getCliente() {
        return cliente;
    }

    public String getServicio() {
        return servicio;
    }

    public String getMetodo() {
        return metodo;
    }

    public double getTotal() {
        return total;
    }

  public String getNumeroFactura(){
      return String.format("FAC-%04d", numero);
  }
    
    
    
}
