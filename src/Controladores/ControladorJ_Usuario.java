/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import Estructuras.ListaDoble;
import Modelos.UsuarioJ;
import javax.swing.JOptionPane;

/**
 *
 * @author ACER1
 */
public class ControladorJ_Usuario {
    
   private static ControladorJ_Usuario instancia;
   
   private final ListaDoble<UsuarioJ> usuarios = new ListaDoble <>();
   
   private UsuarioJ usuarioActual;
   
   private ControladorJ_Usuario(){
       
   }
   
   public static ControladorJ_Usuario getInstancia(){
       if(instancia == null) instancia = new ControladorJ_Usuario();
       return instancia;
   }
   
   
   public UsuarioJ buscarPorCorreo(String correo){
       for(UsuarioJ u : usuarios )
           if(u.getCorreo().equalsIgnoreCase(correo.trim())) return u;
       return null;
   }
   
    public UsuarioJ autenticar(String correo, String clave) {
        UsuarioJ u = buscarPorCorreo(correo);
        if (u != null && u.getClave().equals(clave)) { usuarioActual = u; return u; }
        return null;
    }
           
   public UsuarioJ getUsuarioActual(){ 
       return usuarioActual; 
   }
    public void cerrarSesion(){ 
        usuarioActual = null;
    }
    public String registrar(String nombre, String correo, String clave, String rol){
       if(nombre.isBlank() || correo.isBlank() || clave.isBlank())
           JOptionPane.showMessageDialog(null, "Alguno de los campos esta vacio", "Campo vacio", JOptionPane.WARNING_MESSAGE);
           
    
           if(!correo.trim().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")){
               
            return"El formato del correo es invalido";
           }


           if(clave.length()< 4){
               
           
           
           return "la contraseña debe tener al menos 4 caracteres";
           }
           
           if(buscarPorCorreo(correo) != null){
               
               
           
           return "Ese Correo ya esta registrado";
           }

           
           UsuarioJ u = new UsuarioJ(nombre.trim(),correo.trim(),clave, rol);
           usuarios.agregarFinal(u);
           usuarioActual = u;
           return null;
    }
   
    
    }
 
                   
   
   
   
    


