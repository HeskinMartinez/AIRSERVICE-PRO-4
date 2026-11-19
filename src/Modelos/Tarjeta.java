/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

/**
 *
 * @author ACER1
 */
public class Tarjeta {
    
   private String nombretitular;
   private String numerotarjeta;
   private String fechavencimiento;
   private String codigo;

    public Tarjeta(String nombretitular, String numerotarjeta, String fechavencimiento, String codigo) {
        this.nombretitular = nombretitular;
        this.numerotarjeta = numerotarjeta;
        this.fechavencimiento = fechavencimiento;
        this.codigo = codigo;
    }

    public String getNombretitular() {
        return nombretitular;
    }

    public void setNombretitular(String nombretitular) {
        this.nombretitular = nombretitular;
    }

    public String getNumerotarjeta() {
        return numerotarjeta;
    }

    public void setNumerotarjeta(String numerotarjeta) {
        this.numerotarjeta = numerotarjeta;
    }

    public String getFechavencimiento() {
        return fechavencimiento;
    }

    public void setFechavencimiento(String fechavencimiento) {
        this.fechavencimiento = fechavencimiento;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
   
   
   
    
}
