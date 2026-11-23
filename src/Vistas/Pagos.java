/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Vistas;
import Controladores.ControladorJ_Usuario;
import Controladores.ControladorPagos;
import Modelos.Pago;
import Modelos.Tarjeta;
import Modelos.UsuarioJ;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Pagos extends javax.swing.JFrame {

private final ControladorPagos ctrl = ControladorPagos.getInstancia();
private final NumberFormat moneda = NumberFormat.getInstance(Locale.forLanguageTag("es-CO"));
private String nombreOriginal = "";
private String correoOriginal = "";
private String telefonoOriginal = "";
private String idOriginal ="";
private String direccionOriginal = "";
private int contadorTransacciones = 0;
private double totalPagadoAcumulado = 0.0;

    public void mostrarMetodosPago(){
        jTabbedPane1.setSelectedIndex(0);
        jTabbedPane2.setSelectedIndex(0);
        
        this.setVisible(true);

    }
    
    
    public Pagos() {
        initComponents();
        setLocationRelativeTo(null);

    jcombo_servicios.setModel(new DefaultComboBoxModel<>(ctrl.nombresServicios()));
    jcombo_metodopago.setModel(new DefaultComboBoxModel<>(new String[]{
     ControladorPagos.SIN_METODO, ControladorPagos.TARJETA, ControladorPagos.EFECTIVO}));

    jcombo_servicios.addActionListener(e -> actualizarResumen());
    jcombo_metodopago.addActionListener(e -> actualizarResumen());
    btn_PagoEfectivo.addActionListener(e -> pagarEnEfectivo());
    btn_ListarTarjeta.addActionListener(e -> listarTarjetas());
    btn_cancelar.addActionListener(e -> cancelarPago());

    cargarUsuarioActual();
    actualizarResumen();
    refrescarHistorial();
    }
    // Lo llama Usuarios cuando el cliente elige un plan
public void seleccionarServicio(String nombre) {
    jcombo_servicios.setSelectedItem(nombre);   // dispara actualizarResumen()
    mostrarMetodosPago();                        
}
    // Lo llaman AgregarTarjeta y EditarTarjeta al terminar bien
public void tarjetaGuardada() {
    jcombo_metodopago.setSelectedItem(ControladorPagos.TARJETA);
    jTabbedPane1.setSelectedIndex(0);
    jTabbedPane2.setSelectedIndex(1);            
    this.setVisible(true);
}
private void pagarEnEfectivo() {
    jcombo_metodopago.setSelectedItem(ControladorPagos.EFECTIVO);
    jTabbedPane2.setSelectedIndex(1);
}
private void actualizarResumen() {
    String servicio = (String) jcombo_servicios.getSelectedItem();
    String metodo   = (String) jcombo_metodopago.getSelectedItem();
    double precio   = ctrl.precioDe(servicio);

    jlabel_mostarservicio.setText(precio > 0 ? servicio : "-");
    Jlabe_mostarmetodopago.setText(ControladorPagos.SIN_METODO.equals(metodo) ? "-" : metodo);
    jlabel_TotalPago.setText("$ " + moneda.format(precio));
}
private void cargarUsuarioActual() {
    UsuarioJ u = ControladorJ_Usuario.getInstancia().getUsuarioActual();
    if (u == null) return;
    txt_nombrecompleto.setText(u.getNombre());
    txt_correo.setText(u.getCorreo());
    txt_mostrarNombre.setText(u.getNombre());
    txt_mostrarcorreo.setText(u.getCorreo());
}

public void abrirpestaña(int indice){
    jTabbedPane1.setSelectedIndex(indice);
}

private void cancelarPago() {
    int r = JOptionPane.showConfirmDialog(this, "¿Seguro que deseas cancelar el pago?",
            "Cancelar pago", JOptionPane.YES_NO_OPTION);
    if (r == JOptionPane.YES_OPTION) {
        limpiarPago();
        jTabbedPane2.setSelectedIndex(0);
    }
}
private void limpiarPago() {
    jcombo_servicios.setSelectedIndex(0);
    jcombo_metodopago.setSelectedIndex(0);
    Jcheck_terminosycondiciones.setSelected(false);
    actualizarResumen();
}
private void refrescarHistorial() {
    DefaultTableModel m = (DefaultTableModel) HistorialUsuario.getModel();
    m.setRowCount(0);   // borra las 4 filas vacias del diseñador
    for (Pago p : ctrl.getHistorial()) {
        m.addRow(new Object[]{ p.getNumeroFactura(), p.getCliente(),
                               p.getServicio(), "$ " + moneda.format(p.getTotal()) });
    }
}
private void listarTarjetas() {
    if (ctrl.getTarjetas().estaVacia()) {
        JOptionPane.showMessageDialog(this, "Aun no has agregado ninguna tarjeta.");
        return;
    }
    DefaultTableModel m = new DefaultTableModel(new String[]{"#", "Titular", "N° de tarjeta", "Vence"}, 0);
    int i = 1;
    for (Tarjeta t : ctrl.getTarjetas()) {
        m.addRow(new Object[]{ i++, t.getNombretitular(),
                               ctrl.enmascarar(t.getNumerotarjeta()), t.getFechavencimiento() });
    }
    JScrollPane sp = new JScrollPane(new JTable(m));
    sp.setPreferredSize(new java.awt.Dimension(450, 120));
    JOptionPane.showMessageDialog(this, sp, "Mis tarjetas", JOptionPane.PLAIN_MESSAGE);
}
 

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel5 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btn_AgregarTarjeta = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        btn_EditarTarjeta = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        btn_ListarTarjeta = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        btn_PagoEfectivo = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        txt_nombrecompleto = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        txt_correo = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        txt_telefono = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        txt_documento = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        txt_departamento = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jSeparator6 = new javax.swing.JSeparator();
        txt_municipio = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        txt_direccioncalle = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jcombo_metodopago = new javax.swing.JComboBox<>();
        jLabel40 = new javax.swing.JLabel();
        jcombo_servicios = new javax.swing.JComboBox<>();
        jPanel15 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jlabel_mostarservicio = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        Jlabe_mostarmetodopago = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel46 = new javax.swing.JLabel();
        jlabel_TotalPago = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        Jcheck_terminosycondiciones = new javax.swing.JCheckBox();
        btn_comprar = new javax.swing.JButton();
        btn_cancelar = new javax.swing.JButton();
        jLabel51 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jTotalpagado = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jPagosrealizados = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jLabel62 = new javax.swing.JLabel();
        jLabel63 = new javax.swing.JLabel();
        jlabel_direccionEnvio = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        HistorialUsuario = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        txt_mostrarNombre = new javax.swing.JTextField();
        jLabel72 = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        txt_mostrarcorreo = new javax.swing.JTextField();
        jLabel73 = new javax.swing.JLabel();
        jSeparator11 = new javax.swing.JSeparator();
        txt_mostrarid = new javax.swing.JTextField();
        jLabel74 = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        txt_mostrartel = new javax.swing.JTextField();
        jLabel75 = new javax.swing.JLabel();
        jSeparator13 = new javax.swing.JSeparator();
        txt_mostrardireccion = new javax.swing.JTextField();
        jPanel22 = new javax.swing.JPanel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jPanel23 = new javax.swing.JPanel();
        btn_guardarcambios = new javax.swing.JButton();
        btn_cancelarcambios = new javax.swing.JButton();
        jLabel76 = new javax.swing.JLabel();
        jLabel77 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel52 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jLabel64 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jLabel85 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel88 = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        jSeparator15 = new javax.swing.JSeparator();
        jLabel90 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        jLabel92 = new javax.swing.JLabel();
        jSeparator16 = new javax.swing.JSeparator();
        jLabel93 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        jSeparator17 = new javax.swing.JSeparator();
        jPanel26 = new javax.swing.JPanel();
        jLabel96 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel55 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(51, 255, 153), null));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Logo Airservice Pro con copo de nieve azul (1) (1) (1).png"))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 153));
        jLabel1.setText("PAGOS");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Realice sus pagos de forma segura y rapida ");

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/transferencia-movil.png"))); // NOI18N

        jTabbedPane2.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane2.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(255, 255, 255), null));
        jTabbedPane2.setForeground(new java.awt.Color(0, 204, 153));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 204, 153), null));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/pago-en-linea.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 204, 153));
        jLabel6.setText("Metodo de pago");

        jLabel7.setText("Agrege su tarjeta para ");

        jLabel8.setText("Pagar mas rapido");

        btn_AgregarTarjeta.setText("Agregar");
        btn_AgregarTarjeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_AgregarTarjetaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_AgregarTarjeta)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_AgregarTarjeta)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 204, 153), null));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/tarjeta-de-credito.png"))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 204, 153));
        jLabel10.setText("Editar informacion");

        jLabel11.setText("Actualice su informacion ");

        jLabel12.setText("de su tarjeta.");

        btn_EditarTarjeta.setText("Editar");
        btn_EditarTarjeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EditarTarjetaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_EditarTarjeta)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10)
                    .addComponent(jLabel12))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel9)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(btn_EditarTarjeta)
                .addContainerGap())
        );

        jPanel5.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, 230, 130));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 204, 153), null));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/listar.png"))); // NOI18N

        btn_ListarTarjeta.setText("Listar ");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 204, 153));
        jLabel14.setText("Listar Pagos");

        jLabel15.setText("Liste su tarjeta de preferencia");

        jLabel16.setText("y verifique de manera segura ");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addComponent(jLabel15)))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(jLabel14)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_ListarTarjeta)
                .addGap(80, 80, 80))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel16)))
                .addGap(18, 18, 18)
                .addComponent(btn_ListarTarjeta)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 20, 260, 130));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/escudo-seguro.png"))); // NOI18N
        jLabel17.setText("PAGOS RAPIDOS ");
        jPanel5.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, -1, -1));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 204, 153));
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/agente-de-servicio-al-cliente (1).png"))); // NOI18N
        jLabel18.setText("ATENCION 24/7");
        jPanel5.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 270, -1, 60));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setText("No dude en contactarnos");
        jPanel5.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 310, -1, -1));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 204, 153));
        jLabel20.setText("Y 100% SEGUROS");
        jPanel5.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 300, -1, -1));

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(51, 255, 0), null));
        jPanel20.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_PagoEfectivo.setBackground(new java.awt.Color(153, 204, 0));
        btn_PagoEfectivo.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        btn_PagoEfectivo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/boton-agregarEfectivo.png"))); // NOI18N
        btn_PagoEfectivo.setText("Pago en efectivo");
        btn_PagoEfectivo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 255, 51)));
        jPanel20.add(btn_PagoEfectivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 160, 40));

        jPanel5.add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 260, 210, 60));

        jTabbedPane2.addTab("Metodos de pago", jPanel5);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));
        jPanel11.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 204, 153), null));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/32.png"))); // NOI18N
        jPanel11.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, -1, -1));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(0, 204, 255));
        jLabel22.setText("Datos del usuario");
        jPanel11.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, -1, -1));

        jLabel23.setText("Ingrese sus datos para continuar el pago");
        jPanel11.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 220, -1));

        jLabel24.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel24.setText("Nombre completo");
        jPanel11.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        jPanel11.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 140, 10));

        txt_nombrecompleto.setBorder(null);
        txt_nombrecompleto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nombrecompletoActionPerformed(evt);
            }
        });
        txt_nombrecompleto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nombrecompletoKeyTyped(evt);
            }
        });
        jPanel11.add(txt_nombrecompleto, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 140, 20));

        jLabel25.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel25.setText("Correo Electronico");
        jPanel11.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        jLabel26.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel26.setText("Telefono");
        jPanel11.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, -1, -1));

        jLabel27.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel27.setText("Documento");
        jPanel11.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 200, -1, -1));

        txt_correo.setBorder(null);
        txt_correo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_correoActionPerformed(evt);
            }
        });
        jPanel11.add(txt_correo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 140, 20));

        jSeparator2.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));
        jPanel11.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 140, 10));

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));
        jPanel11.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 240, 120, 10));

        txt_telefono.setBorder(null);
        txt_telefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_telefonoKeyTyped(evt);
            }
        });
        jPanel11.add(txt_telefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 120, -1));

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));
        jPanel11.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 240, 100, 10));

        txt_documento.setBorder(null);
        txt_documento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_documentoActionPerformed(evt);
            }
        });
        txt_documento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_documentoKeyTyped(evt);
            }
        });
        jPanel11.add(txt_documento, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 220, 100, 20));

        jPanel12.setBackground(new java.awt.Color(226, 238, 249));
        jPanel12.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 255, 204), null));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/informacion.png"))); // NOI18N
        jPanel12.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 30, 30));

        jLabel28.setForeground(new java.awt.Color(26, 46, 244));
        jLabel28.setText("La informacion sera usada para confirmar ");
        jPanel12.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, -1, 20));

        jLabel30.setForeground(new java.awt.Color(26, 46, 244));
        jLabel30.setText("confirmar el pago");
        jPanel12.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 16, -1, -1));

        jPanel11.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 260, 40));

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 255, 153), null));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(0, 204, 255));
        jLabel32.setText("Direccion del servicio");
        jPanel13.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 0, -1, 20));

        jLabel33.setFont(new java.awt.Font("SansSerif", 0, 10)); // NOI18N
        jLabel33.setText("complete los campos requeridos");
        jPanel13.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 30, 150, 10));

        jLabel34.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/clavo (1).png"))); // NOI18N
        jPanel13.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, -1, -1));

        jLabel31.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel31.setText("Departamento");
        jPanel13.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jLabel35.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel35.setText("Municipio");
        jPanel13.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 70, -1, -1));

        jLabel36.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel36.setText("Direccion (# calle, etc)");
        jPanel13.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 70, -1, -1));

        txt_departamento.setBorder(null);
        txt_departamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_departamentoActionPerformed(evt);
            }
        });
        txt_departamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_departamentoKeyTyped(evt);
            }
        });
        jPanel13.add(txt_departamento, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 80, 20));

        jSeparator5.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));
        jPanel13.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 80, 10));

        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));
        jPanel13.add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 110, 70, 10));

        txt_municipio.setBorder(null);
        txt_municipio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_municipioKeyTyped(evt);
            }
        });
        jPanel13.add(txt_municipio, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 70, -1));

        jSeparator7.setForeground(new java.awt.Color(0, 0, 0));
        jPanel13.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 110, 120, 10));

        txt_direccioncalle.setBorder(null);
        jPanel13.add(txt_direccioncalle, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 90, 120, 20));

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(51, 255, 204), null));
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel37.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(0, 204, 255));
        jLabel37.setText("Finalizar Compra");
        jPanel14.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 160, -1));

        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/carrito-de-compras.png"))); // NOI18N
        jPanel14.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, -1, -1));

        jLabel39.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel39.setText("Selecciona un metodo de pago");
        jPanel14.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, -1, -1));

        jcombo_metodopago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Metodo de pago", " " }));
        jPanel14.add(jcombo_metodopago, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 170, -1));

        jLabel40.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel40.setText("Seleccione un servicio");
        jPanel14.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));

        jcombo_servicios.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Servicio seleccionado" }));
        jPanel14.add(jcombo_servicios, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 170, -1));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(51, 255, 153), null));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel41.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(0, 204, 255));
        jLabel41.setText("Resumen de compra");
        jPanel15.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, -1, -1));

        jLabel42.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/examen.png"))); // NOI18N
        jPanel15.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 30, -1));

        jLabel43.setText("Revisa los detalles de tu compra");
        jPanel15.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, -1, -1));

        jLabel44.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel44.setText("Servicio seleccionado :");
        jPanel15.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jlabel_mostarservicio.setText("XXXX");
        jPanel15.add(jlabel_mostarservicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 70, -1, -1));

        jLabel45.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jLabel45.setText("Metodo de pago :");
        jPanel15.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        Jlabe_mostarmetodopago.setText("XXXX");
        jPanel15.add(Jlabe_mostarmetodopago, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 90, -1, -1));

        jSeparator8.setForeground(new java.awt.Color(204, 204, 204));
        jPanel15.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 230, 10));

        jLabel46.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel46.setText("Total a Pagar :");
        jPanel15.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, -1, -1));

        jlabel_TotalPago.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jlabel_TotalPago.setText("XXXXXX");
        jPanel15.add(jlabel_TotalPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 120, -1, -1));

        jLabel47.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(0, 204, 153));
        jLabel47.setText("/COP");
        jPanel15.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 120, -1, -1));

        jPanel16.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(102, 0, 102), null));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel48.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(102, 0, 102));
        jLabel48.setText("Confirmar Compra");
        jPanel16.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, -1, -1));

        jLabel49.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/confirmar (1).png"))); // NOI18N
        jPanel16.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, -1, -1));

        jLabel50.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel50.setText("finalice su compra ");
        jPanel16.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 40, -1, -1));

        Jcheck_terminosycondiciones.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        Jcheck_terminosycondiciones.setText("Acepto");
        Jcheck_terminosycondiciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Jcheck_terminosycondicionesActionPerformed(evt);
            }
        });
        jPanel16.add(Jcheck_terminosycondiciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 110, -1, -1));

        btn_comprar.setBackground(new java.awt.Color(204, 255, 0));
        btn_comprar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_comprar.setForeground(new java.awt.Color(255, 255, 255));
        btn_comprar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/controlar.png"))); // NOI18N
        btn_comprar.setText("Comprar");
        btn_comprar.setToolTipText("");
        btn_comprar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(102, 255, 102), new java.awt.Color(102, 255, 102), new java.awt.Color(102, 255, 153), new java.awt.Color(102, 255, 153)));
        btn_comprar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_comprarActionPerformed(evt);
            }
        });
        jPanel16.add(btn_comprar, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, 90, -1));

        btn_cancelar.setBackground(new java.awt.Color(255, 0, 0));
        btn_cancelar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_cancelar.setForeground(new java.awt.Color(255, 255, 255));
        btn_cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cruzar.png"))); // NOI18N
        btn_cancelar.setText("Cancelar");
        btn_cancelar.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(153, 0, 51), new java.awt.Color(153, 0, 102), new java.awt.Color(255, 102, 0), new java.awt.Color(255, 204, 51)));
        btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActionPerformed(evt);
            }
        });
        jPanel16.add(btn_cancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 70, 90, -1));

        jLabel51.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(26, 96, 244));
        jLabel51.setText("Terminos y Condiciones");
        jPanel16.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 110, -1, 20));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(56, 56, 56)
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 16, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Pagar", jPanel6);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 255, 153), new java.awt.Color(51, 255, 153)));
        jPanel17.setForeground(new java.awt.Color(255, 255, 255));

        jLabel53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/examen.png"))); // NOI18N

        jLabel54.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(51, 255, 153));
        jLabel54.setText("Total Pagado");

        jTotalpagado.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jTotalpagado.setText("XXX");

        jLabel56.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(0, 204, 153));
        jLabel56.setText("/COP");

        jLabel57.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(0, 255, 153));
        jLabel57.setText("En sus servicios");

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel53)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jTotalpagado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel56))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel54))))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jLabel54)
                        .addGap(12, 12, 12)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel56)
                            .addComponent(jTotalpagado)))
                    .addComponent(jLabel53))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel57, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 255, 153), new java.awt.Color(51, 255, 153)));

        jLabel58.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/transferencia-movil.png"))); // NOI18N

        jLabel59.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(51, 255, 153));
        jLabel59.setText("Transacciones");

        jPagosrealizados.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jPagosrealizados.setText("1");

        jLabel61.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(51, 255, 153));
        jLabel61.setText("Pagos realizados");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel59)))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addComponent(jPagosrealizados, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel58)
                    .addComponent(jLabel59))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPagosrealizados)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel61)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 255, 153), new java.awt.Color(51, 255, 153)));

        jLabel62.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/estado-financiero.png"))); // NOI18N

        jLabel63.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel63.setForeground(new java.awt.Color(51, 255, 153));
        jLabel63.setText("Estado general");

        jlabel_direccionEnvio.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jlabel_direccionEnvio.setForeground(new java.awt.Color(51, 255, 0));
        jlabel_direccionEnvio.setText("XXX");

        jLabel65.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel65.setText("Direccion del envio:");

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addComponent(jLabel62)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel65)
                            .addComponent(jLabel63))
                        .addGap(0, 34, Short.MAX_VALUE))
                    .addComponent(jlabel_direccionEnvio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel63)
                    .addComponent(jLabel62))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel65)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlabel_direccionEnvio)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        HistorialUsuario.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(51, 255, 153), new java.awt.Color(51, 255, 153)));
        HistorialUsuario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "N° Factura", "Cliente", "Servicio Contratado", "Total"
            }
        ));
        jScrollPane1.setViewportView(HistorialUsuario);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 172, Short.MAX_VALUE)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(117, 117, 117)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57))
            .addComponent(jScrollPane1)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel18, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(54, 54, 54)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Historial de pago", jPanel7);

        jButton2.setBackground(new java.awt.Color(0, 102, 153));
        jButton2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Regresar Inicio");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(354, 354, 354)
                        .addComponent(jLabel2))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(441, 441, 441)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(22, 22, 22))
            .addComponent(jTabbedPane2)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(50, 50, 50)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(42, 42, 42)
                .addComponent(jTabbedPane2))
        );

        jTabbedPane1.addTab("PAGOS", jPanel2);

        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel21.setBackground(new java.awt.Color(1, 10, 28));
        jPanel21.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 51, 255), null));
        jPanel21.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel68.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/usuario (1)AAA.png"))); // NOI18N
        jPanel21.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, -10, 70, 90));

        jLabel69.setFont(new java.awt.Font("Yu Gothic UI", 1, 24)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(255, 255, 255));
        jLabel69.setText("Datos del Usuario");
        jPanel21.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 10, -1, -1));

        jLabel70.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(255, 255, 255));
        jLabel70.setText("Nombre Completo");
        jPanel21.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, -1));

        jLabel71.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(51, 255, 0));
        jLabel71.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/boton.png"))); // NOI18N
        jLabel71.setText("Rol usuario");
        jPanel21.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 130, 40));

        jSeparator9.setBackground(new java.awt.Color(0, 255, 153));
        jSeparator9.setForeground(new java.awt.Color(0, 204, 153));
        jPanel21.add(jSeparator9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 171, -1));

        txt_mostrarNombre.setBackground(new java.awt.Color(1, 10, 28));
        txt_mostrarNombre.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txt_mostrarNombre.setForeground(new java.awt.Color(255, 255, 255));
        txt_mostrarNombre.setBorder(null);
        txt_mostrarNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_mostrarNombreKeyTyped(evt);
            }
        });
        jPanel21.add(txt_mostrarNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, 171, 20));

        jLabel72.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel72.setForeground(new java.awt.Color(255, 255, 255));
        jLabel72.setText("Correo Electronico");
        jPanel21.add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(332, 122, -1, -1));

        jSeparator10.setForeground(new java.awt.Color(0, 255, 153));
        jPanel21.add(jSeparator10, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 180, 160, 10));

        txt_mostrarcorreo.setBackground(new java.awt.Color(1, 10, 28));
        txt_mostrarcorreo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txt_mostrarcorreo.setForeground(new java.awt.Color(255, 255, 255));
        txt_mostrarcorreo.setBorder(null);
        jPanel21.add(txt_mostrarcorreo, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 160, 160, 20));

        jLabel73.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(255, 255, 255));
        jLabel73.setText("Numero de identificacion");
        jPanel21.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, -1, -1));

        jSeparator11.setForeground(new java.awt.Color(0, 255, 153));
        jPanel21.add(jSeparator11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 170, -1));

        txt_mostrarid.setBackground(new java.awt.Color(1, 10, 28));
        txt_mostrarid.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txt_mostrarid.setForeground(new java.awt.Color(255, 255, 255));
        txt_mostrarid.setBorder(null);
        txt_mostrarid.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_mostraridKeyTyped(evt);
            }
        });
        jPanel21.add(txt_mostrarid, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 262, 171, 20));

        jLabel74.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel74.setForeground(new java.awt.Color(255, 255, 255));
        jLabel74.setText("Telefono");
        jPanel21.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 220, 71, -1));

        jSeparator12.setForeground(new java.awt.Color(0, 255, 153));
        jPanel21.add(jSeparator12, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 270, 160, -1));

        txt_mostrartel.setBackground(new java.awt.Color(1, 10, 28));
        txt_mostrartel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txt_mostrartel.setForeground(new java.awt.Color(255, 255, 255));
        txt_mostrartel.setBorder(null);
        txt_mostrartel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_mostrartelKeyTyped(evt);
            }
        });
        jPanel21.add(txt_mostrartel, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 250, 160, 20));

        jLabel75.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(255, 255, 255));
        jLabel75.setText("Direccion");
        jPanel21.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 74, -1));

        jSeparator13.setForeground(new java.awt.Color(0, 255, 153));
        jPanel21.add(jSeparator13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 347, 310, -1));

        txt_mostrardireccion.setBackground(new java.awt.Color(1, 10, 28));
        txt_mostrardireccion.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txt_mostrardireccion.setForeground(new java.awt.Color(255, 255, 255));
        txt_mostrardireccion.setBorder(null);
        jPanel21.add(txt_mostrardireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, 310, -1));

        jPanel3.add(jPanel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 550, 370));

        jPanel22.setBackground(new java.awt.Color(0, 10, 58));
        jPanel22.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 51, 204), null));

        jLabel66.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel66.setForeground(new java.awt.Color(255, 255, 255));
        jLabel66.setText("Perfil");

        jLabel67.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        jLabel67.setForeground(new java.awt.Color(255, 255, 255));
        jLabel67.setText("Revise y actualice su perfil de manera segura ");

        jButton3.setBackground(new java.awt.Color(0, 102, 153));
        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Regresar Inicio");

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel67)
                    .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 571, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(23, 23, 23))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addComponent(jLabel66)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel67))
                    .addGroup(jPanel22Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 1050, 60));

        jPanel23.setBackground(new java.awt.Color(0, 10, 28));
        jPanel23.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 1, 255), null));

        btn_guardarcambios.setBackground(new java.awt.Color(0, 102, 153));
        btn_guardarcambios.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_guardarcambios.setForeground(new java.awt.Color(255, 255, 255));
        btn_guardarcambios.setText("Guardar Cambios");
        btn_guardarcambios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardarcambiosActionPerformed(evt);
            }
        });

        btn_cancelarcambios.setBackground(new java.awt.Color(255, 0, 0));
        btn_cancelarcambios.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btn_cancelarcambios.setForeground(new java.awt.Color(255, 255, 255));
        btn_cancelarcambios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cruzar.png"))); // NOI18N
        btn_cancelarcambios.setText("Cancelar");
        btn_cancelarcambios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarcambiosActionPerformed(evt);
            }
        });

        jLabel76.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/usuario (1)AAA.png"))); // NOI18N

        jLabel77.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel77.setForeground(new java.awt.Color(255, 255, 255));
        jLabel77.setText("Podra editar los campos si lo desea pero debe ");

        jLabel79.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel79.setForeground(new java.awt.Color(255, 255, 255));
        jLabel79.setText("confirmarlos, si no quiere lo puede cancelar ");

        jLabel78.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(255, 255, 255));
        jLabel78.setText("para dejarlo igual.");

        jLabel80.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(255, 255, 255));
        jLabel80.setText("Querido usuario");

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(btn_guardarcambios)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_cancelarcambios))
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel23Layout.createSequentialGroup()
                                .addGap(121, 121, 121)
                                .addComponent(jLabel76))
                            .addGroup(jPanel23Layout.createSequentialGroup()
                                .addGap(76, 76, 76)
                                .addComponent(jLabel80, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel23Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel78)
                                    .addComponent(jLabel79))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel77)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel23Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel76)
                .addGap(4, 4, 4)
                .addComponent(jLabel80)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel77)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel79)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel78, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 148, Short.MAX_VALUE)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_guardarcambios)
                    .addComponent(btn_cancelarcambios, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel3.add(jPanel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 160, 320, 370));

        jButton1.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 0, 0));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/usuario_eliminado.png"))); // NOI18N
        jButton1.setText("Eliminar Usuario");
        jButton1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 51, 51), new java.awt.Color(255, 51, 51), new java.awt.Color(153, 0, 0), new java.awt.Color(153, 0, 0)));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 580, 150, 30));

        jLabel52.setForeground(new java.awt.Color(51, 153, 255));
        jLabel52.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Fondo tecnológico oscuro AIRSERVICE PRO (1).png"))); // NOI18N
        jPanel3.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1060, 620));

        jTabbedPane1.addTab("MI PERFIL", jPanel3);

        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel24.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel64.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/agente-de-servicio-al-cliente (2).png"))); // NOI18N
        jPanel24.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 140, -1, -1));

        jLabel81.setFont(new java.awt.Font("Yu Gothic UI", 1, 24)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(255, 255, 255));
        jLabel81.setText("Contacto");
        jPanel24.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 140, -1, -1));

        jLabel82.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel82.setForeground(new java.awt.Color(255, 255, 255));
        jLabel82.setText("Estamos aqui para ayudarte,");
        jPanel24.add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 210, 180, -1));

        jLabel83.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel83.setForeground(new java.awt.Color(255, 255, 255));
        jLabel83.setText("Si tienes alguna duda o necesitas soporte, ");
        jPanel24.add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 230, -1, -1));

        jLabel84.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel84.setForeground(new java.awt.Color(255, 255, 255));
        jLabel84.setText("contactenos");
        jPanel24.add(jLabel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 250, -1, -1));

        jLabel60.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Fondo tecnológico de aire acondicionado helado (1)rescalado.png"))); // NOI18N
        jPanel24.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 110, 800, 180));

        jPanel25.setBackground(new java.awt.Color(0, 5, 36));
        jPanel25.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 204, 204), new java.awt.Color(0, 204, 204)));
        jPanel25.setForeground(new java.awt.Color(0, 5, 36));
        jPanel25.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel85.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel85.setForeground(new java.awt.Color(255, 255, 255));
        jLabel85.setText("Informacion de contacto");
        jPanel25.add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 0, -1, -1));

        jLabel86.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/informacion (2).png"))); // NOI18N
        jPanel25.add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 2, -1, -1));

        jLabel87.setForeground(new java.awt.Color(255, 255, 255));
        jLabel87.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/llamada.png"))); // NOI18N
        jPanel25.add(jLabel87, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 49, -1, -1));

        jSeparator14.setForeground(new java.awt.Color(51, 255, 255));
        jPanel25.add(jSeparator14, new org.netbeans.lib.awtextra.AbsoluteConstraints(66, 23, 190, 10));

        jLabel88.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel88.setForeground(new java.awt.Color(0, 255, 153));
        jLabel88.setText("Telefono");
        jPanel25.add(jLabel88, new org.netbeans.lib.awtextra.AbsoluteConstraints(52, 49, -1, -1));

        jLabel89.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel89.setForeground(new java.awt.Color(255, 255, 255));
        jLabel89.setText("+57 01 8000 8888");
        jPanel25.add(jLabel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        jSeparator15.setForeground(new java.awt.Color(102, 255, 255));
        jPanel25.add(jSeparator15, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 97, 285, 10));

        jLabel90.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/correo-electronico.png"))); // NOI18N
        jPanel25.add(jLabel90, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 113, 43, -1));

        jLabel91.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel91.setForeground(new java.awt.Color(51, 255, 204));
        jLabel91.setText("Correo Electronico");
        jPanel25.add(jLabel91, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, -1, -1));

        jLabel92.setFont(new java.awt.Font("Yu Gothic UI", 0, 12)); // NOI18N
        jLabel92.setForeground(new java.awt.Color(0, 102, 153));
        jLabel92.setText("Soporte@ariservice.com");
        jPanel25.add(jLabel92, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 130, -1, -1));

        jSeparator16.setForeground(new java.awt.Color(0, 204, 255));
        jPanel25.add(jSeparator16, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 167, 320, 10));

        jLabel93.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/marcador-de-posicion.png"))); // NOI18N
        jPanel25.add(jLabel93, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 180, -1, -1));

        jLabel94.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel94.setForeground(new java.awt.Color(0, 255, 153));
        jLabel94.setText("Nuestra Direccion");
        jPanel25.add(jLabel94, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, 132, -1));

        jLabel95.setForeground(new java.awt.Color(255, 255, 255));
        jLabel95.setText("Av. PedroHeredia Calle #23");
        jPanel25.add(jLabel95, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, 150, -1));
        jPanel25.add(jSeparator17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 290, 10));

        jPanel24.add(jPanel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 330, 280));

        jPanel26.setBackground(new java.awt.Color(0, 5, 36));
        jPanel26.setBorder(javax.swing.BorderFactory.createEtchedBorder(new java.awt.Color(0, 204, 204), new java.awt.Color(0, 204, 204)));

        jLabel96.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel96.setForeground(new java.awt.Color(255, 255, 255));
        jLabel96.setText("Gracias por preferir Air Service Pro !! ");

        jLabel97.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel97.setForeground(new java.awt.Color(255, 255, 255));
        jLabel97.setText("Deje su reseña");

        jLabel98.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/clasificacion.png"))); // NOI18N

        jLabel99.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/resena-del-producto.png"))); // NOI18N

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addGap(165, 165, 165)
                        .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel98)
                            .addComponent(jLabel97, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(jLabel96))
                    .addGroup(jPanel26Layout.createSequentialGroup()
                        .addGap(202, 202, 202)
                        .addComponent(jLabel99)))
                .addContainerGap(78, Short.MAX_VALUE))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel97)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel98, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel96)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel99)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jPanel24.add(jPanel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 330, 460, 200));

        jButton4.setBackground(new java.awt.Color(0, 102, 153));
        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Regresar Inicio");
        jPanel24.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 40, 170, 40));

        jLabel55.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/Fondo tecnológico oscuro AIRSERVICE PRO (1).png"))); // NOI18N
        jPanel24.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1060, 610));

        jPanel4.add(jPanel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1060, 610));

        jTabbedPane1.addTab("CONTACTO", jPanel4);

        jPanel1.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1060, 650));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_nombrecompletoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nombrecompletoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nombrecompletoActionPerformed

    private void txt_correoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_correoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_correoActionPerformed

    private void txt_documentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_documentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_documentoActionPerformed

    private void Jcheck_terminosycondicionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Jcheck_terminosycondicionesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Jcheck_terminosycondicionesActionPerformed

    private void btn_AgregarTarjetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_AgregarTarjetaActionPerformed
        // TODO add your handling code here:
        AgregarTarjeta ventana = new AgregarTarjeta(this);
        ventana.setVisible(true);
        this.setVisible(false);
        
    }//GEN-LAST:event_btn_AgregarTarjetaActionPerformed

    private void btn_EditarTarjetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EditarTarjetaActionPerformed
        // TODO add your handling code here:
        int indice = ctrl.indiceTarjetaActiva();
        if(indice < 0){
            JOptionPane.showMessageDialog(this, "no hay ninguna tarjeta registrada para editar");
            return;
        }
        EditarTarjeta vent = new EditarTarjeta(this);
        vent.setVisible(true);
        this.setVisible(false);
        
    }//GEN-LAST:event_btn_EditarTarjetaActionPerformed

    private void btn_comprarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_comprarActionPerformed
        // TODO add your handling code here:
   
     if (txt_nombrecompleto.getText().trim().isEmpty() ||
        txt_correo.getText().trim().isEmpty() ||
        txt_telefono.getText().trim().isEmpty() ||
        txt_documento.getText().trim().isEmpty() ||
        txt_departamento.getText().trim().isEmpty() ||
        txt_municipio.getText().trim().isEmpty() ||
        txt_direccioncalle.getText().trim().isEmpty()) {
        
        javax.swing.JOptionPane.showMessageDialog(this, 
            "Por favor, complete todos los datos del usuario y la dirección de envío.", 
            "Campos Incompletos", 
            javax.swing.JOptionPane.WARNING_MESSAGE);
        return; 
    }
     String correo = txt_correo.getText().trim();
     String regexCorreo = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
     
     if (!correo.matches(regexCorreo)) {
    javax.swing.JOptionPane.showMessageDialog(this, 
        "El formato del correo electrónico no es válido.\nEjemplo: usuario@dominio.com", 
        "Correo Inválido", 
        javax.swing.JOptionPane.WARNING_MESSAGE);
    txt_correo.requestFocus(); 
    return;       
}
      if (txt_telefono.getText().trim().length() < 7 || txt_documento.getText().trim().length() < 6) {
    javax.swing.JOptionPane.showMessageDialog(this, 
        "El teléfono debe tener al menos 7 dígitos y el documento al menos 6.", 
        "Datos Inválidos", 
        javax.swing.JOptionPane.WARNING_MESSAGE);
    return;
}
       

        
        try {
            Pago p = ctrl.comprar(txt_nombrecompleto.getText(), (String)jcombo_servicios.getSelectedItem(),(String)jcombo_metodopago.getSelectedItem(),Jcheck_terminosycondiciones.isSelected());
            refrescarHistorial();
            String textoPrecio = jlabel_TotalPago.getText();
            String textoLimpio = textoPrecio.replace("$", "")
                                .replace("COP", "")
                                .replace(" ", "")
                                .replace(".", "");
            double montoActual = Double.parseDouble(textoLimpio);
            totalPagadoAcumulado += montoActual;
            contadorTransacciones++;
            jTotalpagado.setText("$" + String.format(java.util.Locale.US,"%,.0f", totalPagadoAcumulado));
            jPagosrealizados.setText(String.valueOf(contadorTransacciones));
            String direccionEnvio = txt_departamento.getText() + "," + txt_municipio.getText() + "," + txt_direccioncalle.getText();
            jlabel_direccionEnvio.setText(direccionEnvio);
            
            JOptionPane.showMessageDialog(this,
            "Compra completada exitosamente.\nFactura: " + p.getNumeroFactura()
            + "\nTotal: $ " + moneda.format(p.getTotal()) + " COP",
            "Pago exitoso", JOptionPane.INFORMATION_MESSAGE);
            
            jTabbedPane2.setSelectedIndex(2);
            //Pasar datos al perfil
    txt_mostrarNombre.setText(txt_nombrecompleto.getText());
    txt_mostrarcorreo.setText(txt_correo.getText());
    txt_mostrarid.setText(txt_documento.getText());
    txt_mostrartel.setText(txt_telefono.getText());
    String direccionCompleta = txt_direccioncalle.getText() + ", " + txt_municipio.getText() + ", " + txt_departamento.getText();
    txt_mostrardireccion.setText(direccionCompleta);
    //guarda valores originales
    nombreOriginal = txt_mostrarNombre.getText();
    correoOriginal = txt_mostrarcorreo.getText();
    idOriginal = txt_mostrarid.getText();
    telefonoOriginal = txt_mostrartel.getText();
    direccionOriginal = txt_mostrardireccion.getText();
    
    
    limpiarPago();
            
            
        
        }catch(IllegalAccessException ex){
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "No se pudo completar la compra",JOptionPane.INFORMATION_MESSAGE);
        }
        
        
    }//GEN-LAST:event_btn_comprarActionPerformed

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_cancelarActionPerformed

    private void btn_guardarcambiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarcambiosActionPerformed
        // TODO add your handling code here:
     if (txt_telefono.getText().trim().length() < 7 || txt_documento.getText().trim().length() < 6) {
    javax.swing.JOptionPane.showMessageDialog(this, 
        "El teléfono debe tener al menos 7 dígitos y el documento al menos 6.", 
        "Datos Inválidos", 
        javax.swing.JOptionPane.WARNING_MESSAGE);
    return;
}
        
    nombreOriginal = txt_mostrarNombre.getText();
    correoOriginal = txt_mostrarcorreo.getText();
    idOriginal = txt_mostrarid.getText();
    telefonoOriginal = txt_mostrartel.getText();
    direccionOriginal = txt_mostrardireccion.getText();
    
    javax.swing.JOptionPane.showMessageDialog(this, "Tus datos de perfil han sido actualizados.");
        
    }//GEN-LAST:event_btn_guardarcambiosActionPerformed

    private void btn_cancelarcambiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarcambiosActionPerformed
        // TODO add your handling code here:
     txt_mostrarNombre.setText(nombreOriginal);
    txt_mostrarcorreo.setText(correoOriginal);
    txt_mostrarid.setText(idOriginal);
    txt_mostrartel.setText(telefonoOriginal);
    txt_mostrardireccion.setText(direccionOriginal);
    
    javax.swing.JOptionPane.showMessageDialog(this, "Edición cancelada. Se han restaurado tus datos.");
    }//GEN-LAST:event_btn_cancelarcambiosActionPerformed

    private void txt_nombrecompletoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombrecompletoKeyTyped
        // TODO add your handling code here:
        validarSoloLetras(evt);
    }//GEN-LAST:event_txt_nombrecompletoKeyTyped

    private void txt_telefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_telefonoKeyTyped
        // TODO add your handling code here:
        validarSoloNumeros(evt, txt_telefono, 10);
        

    }//GEN-LAST:event_txt_telefonoKeyTyped

    private void txt_documentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_documentoKeyTyped
        // TODO add your handling code here:
        validarSoloNumeros(evt, txt_documento, 10);
       
    }//GEN-LAST:event_txt_documentoKeyTyped

    private void txt_departamentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_departamentoKeyTyped
        // TODO add your handling code here:
        validarSinCaracteresEspeciales(evt);
        validarSoloLetras(evt);
    }//GEN-LAST:event_txt_departamentoKeyTyped

    private void txt_municipioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_municipioKeyTyped
        // TODO add your handling code here:
        validarSinCaracteresEspeciales(evt);
        
        validarSoloLetras(evt);
    }//GEN-LAST:event_txt_municipioKeyTyped

    private void txt_mostrarNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_mostrarNombreKeyTyped
        // TODO add your handling code here:
        validarSoloLetras(evt);
    }//GEN-LAST:event_txt_mostrarNombreKeyTyped

    private void txt_mostraridKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_mostraridKeyTyped
        // TODO add your handling code here:
        validarSoloNumeros(evt, txt_mostrarid, 10);
    }//GEN-LAST:event_txt_mostraridKeyTyped

    private void txt_mostrartelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_mostrartelKeyTyped
        // TODO add your handling code here:
        validarSoloNumeros(evt, txt_correo, 10);
    }//GEN-LAST:event_txt_mostrartelKeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        // Valida que exista un usuario activo
    if (txt_mostrarNombre.getText().trim().isEmpty()) {
        javax.swing.JOptionPane.showMessageDialog(this, 
            "No hay ningún perfil activo para eliminar.", 
            "Aviso", 
            javax.swing.JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    //  PRIMERA ADVERTENCIA
    int primeraAdvertencia = javax.swing.JOptionPane.showConfirmDialog(this, 
            "¿Estás seguro que deseas eliminar permanentemente tus datos?", 
            "Advertencia de Eliminación", 
            javax.swing.JOptionPane.YES_NO_OPTION, 
            javax.swing.JOptionPane.WARNING_MESSAGE);
            
    if (primeraAdvertencia == javax.swing.JOptionPane.YES_OPTION) {
        
        // SEGUNDA ADVERTENCIA (Crítica)
        int segundaAdvertencia = javax.swing.JOptionPane.showConfirmDialog(this, 
                "¡ATENCIÓN! Al eliminar tu usuario se borrará también todo tu historial de pagos y los datos que has ingresado.\n¿Deseas continuar de todas formas?", 
                "Peligro - Eliminación Definitiva", 
                javax.swing.JOptionPane.YES_NO_OPTION, 
                javax.swing.JOptionPane.ERROR_MESSAGE);
                
        if (segundaAdvertencia == javax.swing.JOptionPane.YES_OPTION) {
            
            // A. Borrar el historial de pagos del controlador 
            if (ctrl != null) {
                ctrl.eliminarHistorial();
            }
            
            
            javax.swing.JOptionPane.showMessageDialog(this, 
                "Tu usuario, datos personales e historial de pagos han sido eliminados permanentemente.", 
                "Cuenta Eliminada", 
                javax.swing.JOptionPane.INFORMATION_MESSAGE);
                
           
            Login vistaLogin = new Login();
            vistaLogin.setVisible(true);
            vistaLogin.setLocationRelativeTo(null); 
            this.dispose(); 
        }
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txt_departamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_departamentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_departamentoActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Pagos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Pagos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Pagos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Pagos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Pagos().setVisible(true);
            }
        });
    }
    // Permite solo letras y espacios
private void validarSoloLetras(java.awt.event.KeyEvent evt) {
    char c = evt.getKeyChar();
    if (!Character.isLetter(c) && !Character.isWhitespace(c) && c != java.awt.event.KeyEvent.VK_BACK_SPACE) {
        evt.consume();
    }
}

// Permite solo números y limita la cantidad máxima de dígitos
private void validarSoloNumeros(java.awt.event.KeyEvent evt, javax.swing.JTextField campo, int limite) {
    char c = evt.getKeyChar();
    // Si no es número o ya alcanzó el límite de caracteres, bloquea la tecla
    if (!Character.isDigit(c) || campo.getText().length() >= limite) {
        evt.consume();
    }
}

// Bloquea caracteres especiales (permite letras, números y espacios)
private void validarSinCaracteresEspeciales(java.awt.event.KeyEvent evt) {
    char c = evt.getKeyChar();
    if (!Character.isLetterOrDigit(c) && !Character.isWhitespace(c) && c != java.awt.event.KeyEvent.VK_BACK_SPACE) {
        evt.consume();
    }
}
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable HistorialUsuario;
    private javax.swing.JCheckBox Jcheck_terminosycondiciones;
    private javax.swing.JLabel Jlabe_mostarmetodopago;
    private javax.swing.JButton btn_AgregarTarjeta;
    private javax.swing.JButton btn_EditarTarjeta;
    private javax.swing.JButton btn_ListarTarjeta;
    private javax.swing.JButton btn_PagoEfectivo;
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_cancelarcambios;
    private javax.swing.JButton btn_comprar;
    private javax.swing.JButton btn_guardarcambios;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JLabel jPagosrealizados;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel jTotalpagado;
    private javax.swing.JComboBox<String> jcombo_metodopago;
    private javax.swing.JComboBox<String> jcombo_servicios;
    private javax.swing.JLabel jlabel_TotalPago;
    private javax.swing.JLabel jlabel_direccionEnvio;
    private javax.swing.JLabel jlabel_mostarservicio;
    private javax.swing.JTextField txt_correo;
    private javax.swing.JTextField txt_departamento;
    private javax.swing.JTextField txt_direccioncalle;
    private javax.swing.JTextField txt_documento;
    private javax.swing.JTextField txt_mostrarNombre;
    private javax.swing.JTextField txt_mostrarcorreo;
    private javax.swing.JTextField txt_mostrardireccion;
    private javax.swing.JTextField txt_mostrarid;
    private javax.swing.JTextField txt_mostrartel;
    private javax.swing.JTextField txt_municipio;
    private javax.swing.JTextField txt_nombrecompleto;
    private javax.swing.JTextField txt_telefono;
    // End of variables declaration//GEN-END:variables
}
