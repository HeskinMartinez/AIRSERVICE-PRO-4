/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Estructuras.ListaDoble;
import Modelos.Tarjeta;
import Modelos.Pago;
import Modelos.ServicioJ;
import java.time.DateTimeException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import javax.swing.JOptionPane;

/**
 *
 * @author ACER1
 */
public class ControladorPagos {
    public static final String MANTENIMIENTO = "Mantenimiento Preventivo";
    public static final String REPARACION = "Reparacion de Equipos";
    public static final String LIMPIEZA = "Limpieza profunda";
    public static final String INSTALACIONES = "Instalaciones";
    
    public static final String SIN_SERVICIO = "Seleccione un servicio";
    public static final String SIN_METODO = "Seleccione un metodo";
    public static final String TARJETA      = "Tarjeta";
    public static final String EFECTIVO     = "Efectivo";

    private static ControladorPagos instancia;
    
    private final ListaDoble<ServicioJ> servicios = new ListaDoble<>();
    private final ListaDoble<Tarjeta>  tarjetas  = new ListaDoble<>();
    private final ListaDoble<Pago>  historial = new ListaDoble<>();
    private int consecutivo = 1;
    
    private ControladorPagos() {
        
        servicios.agregarFinal(new ServicioJ(MANTENIMIENTO, 120000));
        servicios.agregarFinal(new ServicioJ(REPARACION,    250000));
        servicios.agregarFinal(new ServicioJ(LIMPIEZA,      180000));
        servicios.agregarFinal(new ServicioJ(INSTALACIONES, 350000));
    }
    
    public static ControladorPagos getInstancia() {
        if (instancia == null) instancia = new ControladorPagos();
        return instancia;
    }
    //Servicios//
    public String[] nombresServicios() {
        String[] r = new String[servicios.size() + 1];
        r[0] = SIN_SERVICIO;
        int i = 1;
        for (ServicioJ s : servicios) r[i++] = s.getNombre();
        return r;
    }
    
    public double precioDe(String nombre) {
        for (ServicioJ s : servicios)
            if (s.getNombre().equals(nombre)) return s.getPrecio();
        return 0;
    }
    
    // tarjeta // 
    
    private String validarTarjeta(String titular, String numero, String vence, String cvv){
        if(titular.isBlank() || numero.isBlank() || vence.isBlank() || cvv.isBlank()){
            
            JOptionPane.showConfirmDialog(null, "Algun Campo esta vacio","Campo Vacio",JOptionPane.WARNING_MESSAGE);
            return null;
        }
        
        if(!titular.trim().matches("[\\p{L} .'-]+")){
            JOptionPane.showMessageDialog(null, "El nombre del tituar solo puede tener letras","SOLO LETRAS",JOptionPane.WARNING_MESSAGE);
           return null; 
        }
        if(!numero.replace("","").matches("\\d{16}")){
            JOptionPane.showMessageDialog(null, "El numero de tarjeta debe tener 16 digitos numericos", "SON 16 DIGITOS",JOptionPane.WARNING_MESSAGE);
            return null;
        }
        if(!cvv.trim().matches("\\d{3,4}")){
            JOptionPane.showConfirmDialog(null, "El CVV debe tener 3 o 4 digitos numericos","SOLO 3 0 4 DIGITOS", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        try{
            
            YearMonth ym =YearMonth.parse(vence.trim(),DateTimeFormatter.ofPattern("MM//YY"));
            if(ym.isBefore(YearMonth.now())){
                
                JOptionPane.showMessageDialog(null, "La tarjeta esta vencida", "VENCIDA",JOptionPane.WARNING_MESSAGE); 
            }
        }catch(DateTimeException e){
            JOptionPane.showMessageDialog(null, "La fecha debe tener formato MM//AA (ej: 08/28)", "FORMATO INCORRECTO", JOptionPane.WARNING_MESSAGE);
        }
        return null;
    }
     // DEUVELVE NULL SI SE AGREGO, O EL MENSAJE DE ERROR //
    public String agregarTarjeta(String titular, String numero, String vence, String cvv) {
        String error = validarTarjeta(titular, numero, vence, cvv);
        if (error != null) return error;
        tarjetas.agregarFinal(new Tarjeta(titular.trim(), numero.replace(" ", ""), vence.trim(), cvv.trim()));
        return null;
    }
    public String editarTarjeta(int indice, String titular, String numero, String vence, String cvv) {
        String error = validarTarjeta(titular, numero, vence, cvv);
        if (error != null) return error;
        tarjetas.reemplazar(indice, new Tarjeta(titular.trim(), numero.replace(" ", ""), vence.trim(), cvv.trim()));
        return null;
    }
    
    public ListaDoble<Tarjeta> getTarjetas(){
        return tarjetas;
    }
    
    public int indiceTarjetaActiva(){
        return tarjetas.size()-1;
    }
    public Tarjeta getTarjetaActiva(){
        return tarjetas.estaVacia() ? null : tarjetas.obtener(tarjetas.size()-1);
   }
    
    public String enmascarar(String numero){
        return numero.length() < 4 ? numero : "**** **** **** " + numero.substring(numero.length() - 4);
    }
    
    // COMPRA // 
    
    public Pago comprar(String cliente, String servicio, String metodo, boolean terminos){
        if(!terminos){
            JOptionPane.showMessageDialog(null, "Debes aceptar los terminos y condiciones", "ACEPTE LOS TERMINOS Y CONDICIONES",JOptionPane.WARNING_MESSAGE);
        }
        
       if(cliente == null || cliente.isBlank()){
           JOptionPane.showMessageDialog(null, "Escribe tu nombre completo", "CASILLAS VACIAS",JOptionPane.WARNING_MESSAGE);
       }
       
       double precio = precioDe(servicio);
       if(precio <= 0){
           JOptionPane.showMessageDialog(null, "Seleccione un servicio", "CASILLA VACIA",JOptionPane.WARNING_MESSAGE);
           
       }
       
      if(!TARJETA.equals(metodo) && !EFECTIVO.equals(metodo)){
          JOptionPane.showMessageDialog(null, "Seleccione un metodo de pago", "METODO DE PAGO VACIO",JOptionPane.WARNING_MESSAGE);
      }
      
      if(TARJETA.equals(metodo) && tarjetas.estaVacia()){
          JOptionPane.showMessageDialog(null, "Agrega una tarjeta antes de pagar", "AGREGE TARJETA",JOptionPane.WARNING_MESSAGE);
      }
      
     
      
    Pago p = new Pago(consecutivo++,cliente.trim(),servicio,metodo,precio);
    historial.agregarFinal(p);
    return p;
    
    }
    
    public ListaDoble<Pago> getHistorial(){
        return historial;
    }
    
}
