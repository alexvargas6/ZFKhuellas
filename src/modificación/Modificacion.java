package modificación;

import tablaBorrar.borrar;
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
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import registro.Registro;
import modificación.modificar;

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

    public boolean getCambioFoto() {
        return cambioFoto;
    }

    public FileInputStream getNfoto() {
        return this.Nfoto;
    }

    public void setCambioFoto(boolean cambioFoto) {
        this.cambioFoto = cambioFoto;
    }

    public void setNfoto(FileInputStream Nfoto) {
        this.Nfoto = Nfoto;
    }

    public Modificacion(String nombre, String ID, Image usuario) {
        initComponents();
        this.nombre = nombre;
        this.ID = ID;
        this.usuario = usuario;
        //trans.setOpaque(false);
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

        this.setTitle("MODIFICAR");
    }

    public void colocarFoto(Image usuario) {
        FOTO.setIcon(new ImageIcon(usuario));
    }

    private Modificacion() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void cambiarFoto() throws FileNotFoundException, IOException {
        int resp = JOptionPane.showConfirmDialog(rootPane, "¿Desea cambiar de foto?", "CAMBIO DE FOTO", JOptionPane.INFORMATION_MESSAGE);

        if (resp == JOptionPane.YES_OPTION) {//Si resp es igual a si:
            Registro re = new Registro();//Crear nuevo objeto de registro
            re.seleccionarFoto();//Función seleccionar foto en la clase registro
            String ruta = re.getRuta();//Almacenamos la ruta que nos retorna el getter setRuta en el string ruta
            setNfoto(new FileInputStream(ruta));//Mandamos como parametro el fileinputstream que tiene como parametro ruta al setter setNfoto
            System.out.println(ruta);
            colocarFoto(ImageIO.read(new File(ruta)));//Mandamos como parametro la función ImageIo.read que convierte a objeto file nuestra imagen a la función: colocarFoto
            //FOTO.setIcon(new ImageIcon(ImageIO.read(new File(ruta))));
        }

    }

    public void habilitarModificacion(boolean seguir) {

        if (seguir) {
            txtname.setEditable(true);
            txtname.requestFocus();
            //txtname.setBorder(null);
            guardar.setEnabled(true);
            setCambioFoto(true);
            Cerrado.setIcon(new javax.swing.ImageIcon("C:\\Users\\manuel.vargas\\Documents\\NetBeansProjects\\PruebaZFK\\img\\abierto.png"));
        } else {
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

        trans = new javax.swing.JPanel();
        cerrar = new javax.swing.JLabel();
        ARRASTRAR = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtID = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        txtname = new javax.swing.JTextField();
        Cerrado = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        FOTO = new javax.swing.JLabel();
        MODIFICAR = new javax.swing.JLabel();
        guardar = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        REGRESAR = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        trans.setBackground(new java.awt.Color(246, 246, 246));

        cerrar.setIcon(new javax.swing.ImageIcon("C:\\Users\\manuel.vargas\\Documents\\NetBeansProjects\\PruebaZFK\\img\\CERRAR.png")); // NOI18N
        cerrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cerrarMouseClicked(evt);
            }
        });

        ARRASTRAR.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
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

        jLabel2.setFont(new java.awt.Font("Microsoft JhengHei", 1, 12)); // NOI18N
        jLabel2.setText("CLAVE DE EMPLEADO");

        txtID.setBackground(new java.awt.Color(246, 246, 246));
        txtID.setFont(new java.awt.Font("Microsoft JhengHei", 1, 14)); // NOI18N
        txtID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtID.setBorder(null);

        jLabel1.setFont(new java.awt.Font("Microsoft JhengHei", 1, 12)); // NOI18N
        jLabel1.setText("NOMBRE");

        txtname.setBackground(new java.awt.Color(246, 246, 246));
        txtname.setFont(new java.awt.Font("Microsoft JhengHei", 1, 14)); // NOI18N
        txtname.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtname.setBorder(null);

        Cerrado.setIcon(new javax.swing.ImageIcon("C:\\Users\\manuel.vargas\\Documents\\NetBeansProjects\\PruebaZFK\\img\\cerrado.png")); // NOI18N

        FOTO.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        FOTO.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        FOTO.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                FOTOMouseClicked(evt);
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

        javax.swing.GroupLayout transLayout = new javax.swing.GroupLayout(trans);
        trans.setLayout(transLayout);
        transLayout.setHorizontalGroup(
            transLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ARRASTRAR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(44, 44, 44))
            .addGroup(transLayout.createSequentialGroup()
                .addGroup(transLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(transLayout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addComponent(jLabel1))
                    .addGroup(transLayout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(transLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(transLayout.createSequentialGroup()
                                .addComponent(MODIFICAR, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(transLayout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addComponent(REGRESAR, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, transLayout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addGroup(transLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, transLayout.createSequentialGroup()
                        .addGroup(transLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(transLayout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(66, 66, 66)
                                .addComponent(cerrar)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, transLayout.createSequentialGroup()
                        .addGroup(transLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(transLayout.createSequentialGroup()
                                .addComponent(txtname, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Cerrado)))
                        .addGap(32, 32, 32))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, transLayout.createSequentialGroup()
                        .addComponent(FOTO, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(127, 127, 127))))
        );
        transLayout.setVerticalGroup(
            transLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(transLayout.createSequentialGroup()
                .addGroup(transLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cerrar)
                    .addGroup(transLayout.createSequentialGroup()
                        .addComponent(ARRASTRAR, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(transLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(transLayout.createSequentialGroup()
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 7, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtname, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Cerrado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(FOTO, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(transLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(MODIFICAR, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(guardar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(REGRESAR, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(trans, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(trans, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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
        JOptionPane.showMessageDialog(rootPane, "Toque la foto si desea cambiarla", "CAMBIAR FOTO", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_MODIFICARMouseClicked

    private void guardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_guardarMouseExited
        this.guardar.setForeground(Color.white);
    }//GEN-LAST:event_guardarMouseExited

    private void guardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_guardarMouseEntered
        this.guardar.setForeground(Color.red);
    }//GEN-LAST:event_guardarMouseEntered

    private void guardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_guardarMouseClicked
        if (cambioFoto) {
            modificar mf = new modificar(txtname.getText(), ID, getNfoto());
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
        if (getCambioFoto()) {
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
        try {
            dg = new borrar();
        } catch (Exception e) {
            Logger.getLogger(Modificacion.class.getName()).log(Level.SEVERE, null, e);
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
        System.exit(0);
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
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JPanel trans;
    private javax.swing.JTextField txtID;
    private javax.swing.JTextField txtname;
    // End of variables declaration//GEN-END:variables
}
