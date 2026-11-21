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
            
            
            return "Algun Campo esta vacio";
        }
        
        if(!titular.trim().matches("[\\p{L} .'-]+")){
            
           return "El nombre del titular solo puede tener letras"; 
        }
        if(!numero.replace("","").matches("\\d{16}")){
            
            return "El numero de tarjeta debe tener 16 digitos numericos";
        }
        if(!cvv.trim().matches("\\d{3,4}")){
            
            return "El CVV debe tener 3 o 4 digitos numericos";
        }
        try{
            
           DateTimeFormatter formato = DateTimeFormatter.ofPattern("MM/yy");
           YearMonth fechaTarjeta = YearMonth.parse(vence,formato);
            if(fechaTarjeta.isBefore(YearMonth.now())){
                
               return"La tarjeta esta vencida";
            }
        }catch(DateTimeException e){
            
            return "La fecha debe tener formato MM/AA (ej: 08/28";
            
           
        }
        return null;
    }
     // DEUVELVE NULL SI SE AGREGO, O EL MENSAJE DE ERROR //
    public String agregarTarjeta(String titular, String numero, String vence, String cvv) {
        String error = validarTarjeta(titular, numero, vence, cvv);
        if (error != null){
            return error;
        }
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
    
    public Pago comprar(String cliente, String servicio, String metodo, boolean terminos) throws IllegalAccessException{
        
        
        if(!terminos){
            
           throw new IllegalAccessException("Debes aceptar los terminos y Condiciones");
            
        }
        
       if(cliente == null || cliente.isBlank()){
           throw new IllegalAccessException("Escribe tu nombre completo");
       }
       
       double precio = precioDe(servicio);
       if(precio <= 0){
           throw new IllegalAccessException("Seleccione un servicio");
           
       }
       
      if(!TARJETA.equals(metodo) && !EFECTIVO.equals(metodo)){
          throw new IllegalAccessException("Seleccione un metodo de pago");
      }
      
      if(TARJETA.equals(metodo) && tarjetas.estaVacia()){
          throw new IllegalAccessException("Agrega una tarjeta antes de pagar con tarjeta");
      }
      
     
      
    Pago p = new Pago(consecutivo++,cliente.trim(),servicio,metodo,precio);
    historial.agregarFinal(p);
    return p;
    
    }
    
    public ListaDoble<Pago> getHistorial(){
        return historial;
    }
    public void eliminarHistorial() {
    if (historial != null) { 
        historial.limpiar();
    }
}

}
