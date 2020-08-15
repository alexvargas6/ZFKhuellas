
package pruebazfk;

import com.sun.awt.AWTUtilities;
import com.zkteco.biometric.FingerprintSensorEx;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import static pruebazfk.prueba.connect;

/**
 *
 * @author manuel.vargas
 */
public class borrar extends javax.swing.JFrame {

    public void setUsuario(Image usuario){
    this.usuario = usuario;
    }
    
    public void setNombre(String Nombre){
    this.nombre = Nombre;
    }
    
    public void setID(String ID){
    this.ID = ID;
    }
    
    public String getNombre() {
        return nombre;
    }

    public String getID() {
        return ID;
    }
    public Image getUsuario(){
    return usuario;
    }
 Busqueda busq = null;
 prueba pru = null;
 private String nombre;
 private ImageIcon imgi;
 private String ID;
 private Image usuario;
 
 int xE;
 int yE;
    
public borrar() throws ClassNotFoundException, SQLException, IOException {
        
    initComponents();
    MostrarTable(tabla);
    this.fotoBtn.setBackground(Color.white);
    this.txtNombre.setEditable(false);
    this.txtNombre.setBackground(Color.pink);
    this.setTitle("BORRAR");
    AWTUtilities.setWindowOpaque(this, false);
     this.setLocationRelativeTo(null);
 
    }

public void MostrarTable(JTable tabla) throws ClassNotFoundException, SQLException, IOException{
tabla.setDefaultRenderer(Object.class, new Render());/*Establece un
renderizado de celda*/
DefaultTableModel dt;
     dt = new DefaultTableModel(){
         
         @Override
         public boolean isCellEditable(int row, int column){
             return false;
         }
     };
dt.addColumn("ID");/*Establecmos los nombres de nuesras columnas:*/
dt.addColumn("Nombre");
dt.addColumn("Foto");

busq = new Busqueda();
setters set = new setters();
ArrayList<setters> list = busq.Lista_Usuarios();/*Llamamos a la función Lista_Usuarios dentro
De la clase Busqueda*/

if(list.size()>0){ /*List.size representa el Número de usuarios dentro del array*/
    System.out.println("Número de usuarios registrados: " + list.size());
for(int i=0; i<list.size(); i++){
    Object fila[] = new Object[3];/*Creamos un nuevo objeto fila por cada ciclo*/
    set = list.get(i);/*i representa el número del usuario, list.get() devuelve el
    elemento de un indice*/
    fila[0] = set.getIdUsuariosConHuellas();/*Añadimos en la poscisión 0 el id de los usuarios*/
    fila[1] = set.getNombre();/*En la poscisión 1 del array añadimos el nombre*/
    try{
    byte[] bi = set.getFoto();/*getFoto recibe los bytes de cada imagén, por lo que es 
    necesario pasarlos a la variable bi*/
    BufferedImage image = null;/*Declaramos la imagen*/
    InputStream in = new ByteArrayInputStream(bi);/*Leemos los datos de la matriz de bytes almacenados en memoria*/
    image = ImageIO.read(in);/*los bayts de in los leemos y almacenamos en image*/
     imgi = new ImageIcon(image.getScaledInstance(60, 60, 0));//declaramos los valores de alto y ancho como 60 x 60
    fila[2] = new JLabel(imgi);/*añadimos en la pocisión 2 un label que contiene la imagen*/
    
    }catch(Exception ex){
    fila[2] = new JLabel("NO IMAGEN");/*En caso de que no se pueda leer ninguna imagen
    saldra el texto NO IMAGEN en el label de esa columna*/
    }
    dt.addRow(fila);/*addRow (Agregar filas) añadimos el objecto fila que contiene 
                        nuestros usuarios regisstrados al modelo dt*/
}
tabla.setModel(dt);/*Añadimos el modelo a nuestra tabla*/
tabla.setRowHeight(60);//Asignamos un valor a la altura de nuestra fila
}
}

public void eliminarUsuario() throws ClassNotFoundException, SQLException{
    pru = new prueba();
    /*en el int usuariosSeleccionado indicamos el usuario seleccionado en la tabla
    con la función getSelectedRow*/
int usuarioSeleccionado = tabla.getSelectedRow();/*Indica la posición en la que
se encuentra de arriba hacía abajo*/

if(usuarioSeleccionado < 0){
JOptionPane.showMessageDialog(rootPane, "No se ha seleccionado ningún usuario");
}else{

DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
/*En el string usuario almacenamos el ID de nuestro usuarios seleccionado*/

String usuario = String.valueOf(modelo.getValueAt/*GetValuetAt devuelve
        el valor de la celda con los parametros columna y fila
        */(/*Devuelve el indice de la fila seleccionada*/tabla.getSelectedRow(),/*0 indica la
        posición en la fila en la que se encuentra nuestro ID*/0));

Connection con = connect();
String sql ="DELETE FROM usuariosconhuellas WHERE idusuariosConHuellas = " + usuario;
try{
Statement st = con.createStatement();
int executeUpdate = st.executeUpdate(sql);

if(executeUpdate!=0){
    JOptionPane.showMessageDialog(rootPane, "El usuario: '" + this.nombre + "' Ha sido borrado con exito");

MostrarTable(this.tabla);
}else{
JOptionPane.showMessageDialog(rootPane, "Ocurrio un error al intentar borrar al usuario: " + this.nombre);
}
}
catch(Exception e){
System.out.println("Error al tratar de borrar usuario:\n"+e);
} finally{
try{
pru.closeCon();
}catch(Exception e){
System.out.println("Error al cerrar conexión:\n" +e);
}}}}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        txtNombre = new javax.swing.JTextField();
        lblUsuario = new javax.swing.JLabel();
        fotoBtn = new javax.swing.JButton();
        delete = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        menu = new javax.swing.JLabel();
        move = new javax.swing.JLabel();
        CERRAR = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(205, 231, 254));
        setUndecorated(true);

        jPanel2.setBackground(new java.awt.Color(205, 231, 254));

        jPanel1.setBackground(new java.awt.Color(221, 239, 254));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblUsuario.setFont(new java.awt.Font("Arial Unicode MS", 0, 14)); // NOI18N
        lblUsuario.setText("USUARIO SELECCIONADO");

        fotoBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fotoBtnActionPerformed(evt);
            }
        });

        delete.setIcon(new javax.swing.ImageIcon("C:\\Users\\manuel.vargas\\Documents\\NetBeansProjects\\PruebaZFK\\img\\delete.png")); // NOI18N
        delete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        delete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                deleteMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                deleteMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                deleteMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(txtNombre))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(57, 57, 57)
                                .addComponent(fotoBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(lblUsuario)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(delete)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(delete)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(fotoBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        tabla.setFont(new java.awt.Font("Dubai", 0, 14)); // NOI18N
        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabla.setGridColor(new java.awt.Color(0, 153, 0));
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabla);

        menu.setIcon(new javax.swing.ImageIcon("C:\\Users\\manuel.vargas\\Documents\\NetBeansProjects\\PruebaZFK\\img\\MENU.png")); // NOI18N
        menu.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                menuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                menuMouseExited(evt);
            }
        });

        move.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                moveMousePressed(evt);
            }
        });
        move.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                moveMouseDragged(evt);
            }
        });

        CERRAR.setIcon(new javax.swing.ImageIcon("C:\\Users\\manuel.vargas\\Documents\\NetBeansProjects\\PruebaZFK\\img\\CERRAR.png")); // NOI18N
        CERRAR.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        CERRAR.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CERRARMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(94, 94, 94)
                                .addComponent(menu))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 20, Short.MAX_VALUE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(move, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(CERRAR)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(CERRAR, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(move, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(menu)))
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Evento que se acciona cuando se hace click en la tabla:
    private void tablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMouseClicked
      int clic = tabla.rowAtPoint(evt.getPoint());/*rowAtPoint devuelve el indice de la fila
      en la que se encuentra jeje*/
      System.out.println(clic);/*El int clic almacena el número de fila en el que estamos 
      dando clic*/
     setNombre( ""+tabla.getValueAt(clic, 1));
     setID(""+tabla.getValueAt(clic, 0));
    
     try {
        setUsuario(busq.user(getID(), getNombre()));
         this.fotoBtn.setIcon(new ImageIcon (getUsuario()));
     } catch (ClassNotFoundException ex) {
         Logger.getLogger(borrar.class.getName()).log(Level.SEVERE, null, ex);
         System.out.println("Problema al intentar colocar la imagen de usuario: "+ex);
     } catch (SQLException ex) {
         Logger.getLogger(borrar.class.getName()).log(Level.SEVERE, null, ex);
         System.out.println("Problema al intentar colocar la imagen de usuario: "+ex);
     }
     this.txtNombre.setText(nombre);
     
    }//GEN-LAST:event_tablaMouseClicked

    private void fotoBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fotoBtnActionPerformed
try{
        if(nombre!=null){
    
        Modificacion dg = null;
        try{
        dg = new Modificacion(getNombre(),getID(),getUsuario());
        }catch(Exception e){
        Logger.getLogger(borrar.class.getName()).log(Level.SEVERE,null,e);
        System.out.println(e);
        }
        dg.setLocationRelativeTo(null);
        dg.setVisible(true);
        this.setVisible(false);
}
}catch(Exception e){
System.out.println("ERROR: " + e);
}
    }//GEN-LAST:event_fotoBtnActionPerformed

    private void menuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuMouseClicked
         prueba dg = null;
       try{
           dg = new prueba();
       }catch(Exception e){
       Logger.getLogger(borrar.class.getName()).log(Level.SEVERE, null, e);
       }
       dg.setLocationRelativeTo(null);
       dg.setVisible(true);
       this.setVisible(false);
       
      FingerprintSensorEx.Terminate();/*Liberamos recursos para evitar errores al
      regresar al menú*/
    }//GEN-LAST:event_menuMouseClicked

    private void menuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuMouseEntered
     menu.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(66, 116, 143)));
    }//GEN-LAST:event_menuMouseEntered

    private void menuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuMouseExited
        menu.setBorder(null);
    }//GEN-LAST:event_menuMouseExited

    private void deleteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteMouseClicked
       if(nombre==null){
        JOptionPane.showMessageDialog(rootPane, "Por favor selecciona un usuario de la tabla.","Ningún usuario seleccionado.",
                JOptionPane.ERROR_MESSAGE);
        }else{
        
        int resp = JOptionPane.showConfirmDialog(rootPane, "Esta segura de querer borrar a: " + nombre + "?","WARNING",JOptionPane.WARNING_MESSAGE);
if(resp == JOptionPane.YES_OPTION){
        try {
            eliminarUsuario();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(borrar.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(borrar.class.getName()).log(Level.SEVERE, null, ex);
        }
}
        }
    }//GEN-LAST:event_deleteMouseClicked

    private void deleteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteMouseEntered
        delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(66, 116, 143)));
    }//GEN-LAST:event_deleteMouseEntered

    private void deleteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deleteMouseExited
       delete.setBorder(null);
    }//GEN-LAST:event_deleteMouseExited

    private void moveMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_moveMousePressed
         xE = evt.getX();
         yE = evt.getY();
    }//GEN-LAST:event_moveMousePressed

    private void moveMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_moveMouseDragged
        this.setLocation(this.getLocation().x + evt.getX() - xE, this.getLocation().y + evt.getY() - yE);
    }//GEN-LAST:event_moveMouseDragged

    private void CERRARMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CERRARMouseClicked
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_CERRARMouseClicked

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
            java.util.logging.Logger.getLogger(borrar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(borrar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(borrar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(borrar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new borrar().setVisible(true);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(borrar.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(borrar.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(borrar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CERRAR;
    private javax.swing.JLabel delete;
    private javax.swing.JButton fotoBtn;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JLabel menu;
    private javax.swing.JLabel move;
    private javax.swing.JTable tabla;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
