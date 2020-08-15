
package pruebazfk;

import com.sun.awt.AWTUtilities;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author manuel.vargas
 */
public class Modificacion extends javax.swing.JFrame {

 private String nombre;
 private String ID;
 private Image usuario;
 private boolean cambioFoto = false;
 private FileInputStream Nfoto;
 
 int xE;
 int yE;
 
 public boolean getCambioFoto(){
 return cambioFoto;
 }
 
 public FileInputStream getNfoto(){
 return this.Nfoto;
 }
 
 public void setCambioFoto(boolean cambioFoto){
 this.cambioFoto = cambioFoto;
 }
 
 public void setNfoto(FileInputStream Nfoto){
 this.Nfoto = Nfoto;
 }
 
    public Modificacion(String nombre, String ID, Image usuario) {
        initComponents();
        this.nombre = nombre;
        this.ID = ID;
        this.usuario = usuario;
        panel.setOpaque(false);
        this.getContentPane().setBackground(Color.WHITE);
        guardar.setEnabled(false);
      
        /*LOS TXTFIELDS*/
        txtID.setText(ID);
        txtID.setEditable(false);
        //txtID.setBackground(Color.PINK);
        txtname.setText(nombre);
        txtname.setEditable(false);
        
        /*Imagen*/
        colocarFoto(usuario);
        
     AWTUtilities.setWindowOpaque(this, false);
     this.setLocationRelativeTo(null);
    }
    
    public void colocarFoto(Image usuario){
    FOTO.setIcon(new ImageIcon(usuario));
    }

    private Modificacion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void cambiarFoto() throws FileNotFoundException, IOException{
    int resp = JOptionPane.showConfirmDialog(rootPane, "¿Desea cambiar de foto?","CAMBIO DE FOTO",JOptionPane.INFORMATION_MESSAGE);
    
   if(resp == JOptionPane.YES_OPTION){//Si resp es igual a si:
   Registro re = new Registro();//Crear nuevo objeto de registro
   re.seleccionarFoto();//Función seleccionar foto en la clase registro
   String ruta = re.getRuta();//Almacenamos la ruta que nos retorna el getter setRuta en el string ruta
   setNfoto(new FileInputStream(ruta));//Mandamos como parametro el fileinputstream que tiene como parametro ruta al setter setNfoto
   System.out.println(ruta);
   colocarFoto(ImageIO.read(new File(ruta)));//Mandamos como parametro la función ImageIo.read que convierte a objeto file nuestra imagen a la función: colocarFoto
//FOTO.setIcon(new ImageIcon(ImageIO.read(new File(ruta))));
    }
    
    }
    
    public void habilitarModificacion(boolean seguir){
    
        if(seguir){
        txtname.setEditable(true);
        txtname.requestFocus();
        //txtname.setBorder(null);
        guardar.setEnabled(true);
        setCambioFoto(true);
        Cerrado.setIcon(new javax.swing.ImageIcon("C:\\Users\\manuel.vargas\\Documents\\NetBeansProjects\\PruebaZFK\\img\\abierto.png"));
        } else{
        txtname.setEditable(false);
        txtname.requestFocus();
        //txtname.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        guardar.setEnabled(false);
        setCambioFoto(false);
        Cerrado.setIcon(new javax.swing.ImageIcon("C:\\Users\\manuel.vargas\\Documents\\NetBeansProjects\\PruebaZFK\\img\\cerrado.png"));
        }
        }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        txtname = new javax.swing.JTextField();
        txtID = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        FOTO = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        guardar = new javax.swing.JLabel();
        MODIFICAR = new javax.swing.JLabel();
        REGRESAR = new javax.swing.JLabel();
        Cerrado = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        cerrar = new javax.swing.JLabel();
        ARRASTRAR = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        panel.setBackground(new java.awt.Color(246, 246, 246));

        txtname.setFont(new java.awt.Font("Microsoft JhengHei", 1, 14)); // NOI18N
        txtname.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtname.setBorder(null);

        txtID.setFont(new java.awt.Font("Microsoft JhengHei", 1, 14)); // NOI18N
        txtID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtID.setBorder(null);

        jLabel2.setFont(new java.awt.Font("Microsoft JhengHei", 1, 12)); // NOI18N
        jLabel2.setText("CLAVE DE EMPLEADO");

        FOTO.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        FOTO.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        FOTO.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FOTOMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Microsoft JhengHei", 1, 12)); // NOI18N
        jLabel1.setText("NOMBRE");

        guardar.setBackground(new java.awt.Color(99, 174, 215));
        guardar.setFont(new java.awt.Font("Microsoft JhengHei UI Light", 1, 12)); // NOI18N
        guardar.setForeground(new java.awt.Color(246, 246, 246));
        guardar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        guardar.setText("GUARDAR");
        guardar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(66, 116, 143)));
        guardar.setOpaque(true);
        guardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                guardarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                guardarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                guardarMouseExited(evt);
            }
        });

        MODIFICAR.setBackground(new java.awt.Color(99, 174, 215));
        MODIFICAR.setFont(new java.awt.Font("Microsoft JhengHei UI Light", 1, 12)); // NOI18N
        MODIFICAR.setForeground(new java.awt.Color(246, 246, 246));
        MODIFICAR.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MODIFICAR.setText("MODIFICAR");
        MODIFICAR.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(66, 116, 143)));
        MODIFICAR.setOpaque(true);
        MODIFICAR.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MODIFICARMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                MODIFICARMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                MODIFICARMouseExited(evt);
            }
        });

        REGRESAR.setBackground(new java.awt.Color(99, 174, 215));
        REGRESAR.setFont(new java.awt.Font("Microsoft JhengHei UI Light", 1, 12)); // NOI18N
        REGRESAR.setForeground(new java.awt.Color(246, 246, 246));
        REGRESAR.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        REGRESAR.setText("REGRESAR");
        REGRESAR.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(66, 116, 143)));
        REGRESAR.setOpaque(true);
        REGRESAR.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                REGRESARMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                REGRESARMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                REGRESARMouseExited(evt);
            }
        });

        Cerrado.setIcon(new javax.swing.ImageIcon("C:\\Users\\manuel.vargas\\Documents\\NetBeansProjects\\PruebaZFK\\img\\cerrado.png")); // NOI18N

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(MODIFICAR, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelLayout.createSequentialGroup()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(136, 136, 136)
                        .addComponent(jLabel1))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                        .addComponent(FOTO, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(127, 127, 127))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                        .addComponent(REGRESAR, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(109, 109, 109))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                        .addComponent(txtname)
                        .addGap(18, 18, 18)
                        .addComponent(Cerrado)
                        .addGap(31, 31, 31))))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(txtname, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(FOTO, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(MODIFICAR, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(REGRESAR, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Cerrado))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(246, 246, 246));

        cerrar.setIcon(new javax.swing.ImageIcon("C:\\Users\\manuel.vargas\\Documents\\NetBeansProjects\\PruebaZFK\\img\\CERRAR.png")); // NOI18N
        cerrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cerrarMouseClicked(evt);
            }
        });

        ARRASTRAR.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ARRASTRARMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ARRASTRARMousePressed(evt);
            }
        });
        ARRASTRAR.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                ARRASTRARMouseDragged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ARRASTRAR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cerrar))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(cerrar)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(ARRASTRAR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void MODIFICARMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MODIFICARMouseExited
        this.MODIFICAR.setForeground(Color.white);
    }//GEN-LAST:event_MODIFICARMouseExited

    private void MODIFICARMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MODIFICARMouseEntered
       this.MODIFICAR.setForeground(Color.red);
    }//GEN-LAST:event_MODIFICARMouseEntered

    private void MODIFICARMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MODIFICARMouseClicked
        habilitarModificacion(true);
        JOptionPane.showMessageDialog(rootPane, "Toque la foto si desea cambiarla","CAMBIAR FOTO",JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_MODIFICARMouseClicked

    private void guardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_guardarMouseExited
        this.guardar.setForeground(Color.white);
    }//GEN-LAST:event_guardarMouseExited

    private void guardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_guardarMouseEntered
        this.guardar.setForeground(Color.red);
    }//GEN-LAST:event_guardarMouseEntered

    private void guardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_guardarMouseClicked
        if(cambioFoto){
        modificar mf = new modificar(txtname.getText(),ID,getNfoto());
        try {
            mf.update();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Modificacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Modificacion.class.getName()).log(Level.SEVERE, null, ex);
        }

        habilitarModificacion(false);
    }//GEN-LAST:event_guardarMouseClicked
    }
    private void FOTOMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FOTOMouseClicked
        if(getCambioFoto()){
            try {
                cambiarFoto();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Modificacion.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Modificacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_FOTOMouseClicked

    private void REGRESARMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_REGRESARMouseClicked
 borrar dg = null;
        try{
            dg = new borrar();
        }catch(Exception e){
            Logger.getLogger(Modificacion.class.getName()).log(Level.SEVERE,null,e);
        }
        dg.setLocationRelativeTo(null);
        dg.setVisible(true);
        this.setVisible(false);        
    }//GEN-LAST:event_REGRESARMouseClicked

    private void REGRESARMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_REGRESARMouseEntered
        this.REGRESAR.setForeground(Color.red);
    }//GEN-LAST:event_REGRESARMouseEntered

    private void REGRESARMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_REGRESARMouseExited
     this.REGRESAR.setForeground(Color.white);
    }//GEN-LAST:event_REGRESARMouseExited

    private void ARRASTRARMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ARRASTRARMouseClicked
        
    }//GEN-LAST:event_ARRASTRARMouseClicked

    private void ARRASTRARMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ARRASTRARMousePressed
         xE = evt.getX();
         yE = evt.getY();
    }//GEN-LAST:event_ARRASTRARMousePressed

    private void ARRASTRARMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ARRASTRARMouseDragged
        this.setLocation(this.getLocation().x + evt.getX() - xE, this.getLocation().y + evt.getY() - yE);
    }//GEN-LAST:event_ARRASTRARMouseDragged

    private void cerrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cerrarMouseClicked
        this.setVisible(false);
        this.dispose();       
    }//GEN-LAST:event_cerrarMouseClicked

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
            java.util.logging.Logger.getLogger(Modificacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Modificacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Modificacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Modificacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Modificacion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ARRASTRAR;
    private javax.swing.JLabel Cerrado;
    private javax.swing.JLabel FOTO;
    private javax.swing.JLabel MODIFICAR;
    private javax.swing.JLabel REGRESAR;
    private javax.swing.JLabel cerrar;
    private javax.swing.JLabel guardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JPanel panel;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtname;
    // End of variables declaration//GEN-END:variables
}
