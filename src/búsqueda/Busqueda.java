package búsqueda;

import tablaBorrar.setters;
import vista.prueba;
import com.zkteco.biometric.FingerprintSensorEx;
import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static vista.prueba.connect;

/**
 * @author manuel.vargas
 */
public class Busqueda {

    private int idEnMemoria;
    private String id = null;
    private String nombreUS;
    private String clave;
    private Blob foto1;
    private Blob bytesImagenHuella;

    public void busqueda(int[] idDevuelta, JButton btnFoto, int[] score, JLabel lblfoto, JLabel lblIDMostrar,
            JLabel lblNombre, JLabel lblNombreMostrar) throws IOException, SQLException, ClassNotFoundException, InterruptedException {
        prueba pru = new prueba();
        int mandar = idDevuelta[0];//Pasamos el ID del usuario al int mandar
        try {
            Connection con = connect();//Establecemos conexión

            PreparedStatement st = con.prepareStatement("SELECT * FROM usuariosconhuellas WHERE idusuariosConHuellas LIKE '%" + mandar + "%'");
            ResultSet rs = st.executeQuery();

            if (rs.next()) {

                setId(rs.getString("idusuariosConHuellas"));
                setNombreUS(rs.getString("Nombre"));
                setFoto1(rs.getBlob("Foto"));
                Image foto = javax.imageio.ImageIO.read(getFoto1().getBinaryStream());/*Función que lee la foto que se recibe de la base de datos,
 la recibe como blob*/

                //MUESTRA LOS RESULTADOS EN PANTALLA:
                btnFoto.setIcon(new ImageIcon(foto));//Muestra la foto que esta dentro de la variable foto en el botón.

                mostrarlbl(lblfoto, lblIDMostrar, lblNombre, lblNombreMostrar);

                lblfoto.setText("ID:");
                lblIDMostrar.setText(getId());
                lblNombre.setText("NOMBRE:");
                lblNombreMostrar.setText(getNombreUS());

                setIdEnMemoria(0);
            }

            JOptionPane.showMessageDialog(null, "Identificación completa, el usuario: " + nombreUS /*fid[0]
                                Fid es un array en donde se almacena el número del usuario que fue registrado*/ + "\n Con el ID: " + id + " obtuvo un puntaje de: " + score[0] + " En identificación."/*
                        En score se almacena el puntuaje que obtuvo el lector de huellas.*/);
        } catch (Exception e) {
            System.out.println("Problema al tratar de identificar al usuario:\n" + e);
        } finally {
            try {
                pru.closeCon();
            } catch (Exception e) {
                System.out.println("Error al tratar de cerrar la conexión:\n" + e);
            }
        }

    }

    public void buscar(Long mhDB, byte[] template) throws ClassNotFoundException, SQLException, IOException {
        prueba prueba = new prueba();
        Connection con = connect();
        int ret;

        Image rpta = null;

        setClave(JOptionPane.showInputDialog("Ingresa la clave de usuario: "));
        if (getClave() != null || getClave() == "") {
            try {
                int clav = Integer.parseInt(getClave());
                if (clav == getIdEnMemoria()) {
                    JOptionPane.showMessageDialog(null, "¡El usuario con el ID: '" + getClave() + "' ya se encuentra en la memoria!", "ERROR", JOptionPane.INFORMATION_MESSAGE);
                } else {

                    try {
                        PreparedStatement st = con.prepareStatement("SELECT * FROM usuariosconhuellas WHERE idusuariosConHuellas LIKE '%" + getClave() + "%'");
                        ResultSet rs = st.executeQuery();
                        if (rs.next()) {

                            setIdEnMemoria(rs.getInt("idusuariosConHuellas"));//udEnMemoria es una variable instanciada.
                            setBytesImagenHuella(rs.getBlob("Huella"));
                            rpta = javax.imageio.ImageIO.read(getBytesImagenHuella().getBinaryStream());
                            //Creamos la imagen en disco:
                            ImageIO.write((RenderedImage) rpta, "bmp", new File("C:\\Users\\manuel.vargas\\Documents\\NetBeansProjects\\PruebaZFK\\copia_Huella.bmp"));

                            int[] TamañoPlantilla = new int[1];
                            TamañoPlantilla[0] = 2048;
                            byte[] plantillaDevuelta = new byte[2048];

                            //Establecemos la ruta en donde creamos la imagen en disco:
                            String ruta = "C:\\Users\\manuel.vargas\\Documents\\NetBeansProjects\\PruebaZFK\\copia_Huella.bmp";
                            ret = FingerprintSensorEx.ExtractFromImage(mhDB, ruta, 500, plantillaDevuelta, TamañoPlantilla);//Registramos la imagen 
                            if (ret == 0) {//Si ret=0 significa que todo es correcto, entonces procedemos a registrar en memoria

                                ret = FingerprintSensorEx.DBAdd(mhDB, getIdEnMemoria(), plantillaDevuelta);//Guardamos la plantilla en memoria
                            }
                            //DBCount va sumando usuarios, dependiendo del número en el que se encuentre!
                            ret = FingerprintSensorEx.DBCount(mhDB);//DbCount nos dice cuantos usuarios existen en memoria.
                            if (ret >= 0) {
                                JOptionPane.showMessageDialog(null, ret + " Usuarios en meoria");//Aquí nos muestra cuantos usuarios tenemos en memoria por medio de un mensaje en pantalla

                            } else {//Si ret es menor a 0 significa que algo salio mal, y lo mostramos en el siguiente mensaje:
                                JOptionPane.showMessageDialog(null, "Ocurrio un error al contar los usuarios en memoria: " + ret);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "USUARIO NO ENCONTRADO!", "ERROR DE OPERACIÓN", JOptionPane.ERROR_MESSAGE);
                            //borrarTodo(mhDB);

                            ret = FingerprintSensorEx.DBCount(mhDB);
                            if (ret > 0) {
                                prueba.Borrar(getIdEnMemoria(), mhDB);
                            }
                        }

                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, e);
                    }
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e);
            } finally {
                try {
                    prueba.closeCon();
                } catch (Exception e) {
                    System.out.println("Problemas al tratar de cerrar la conexión\n" + e);
                }
            }
        }
    }

    public void mostrarlbl(JLabel lblfoto, JLabel lblIDmostrar, JLabel lblNombre, JLabel lblNombreMostrar) {
        lblfoto.setVisible(true);
        lblIDmostrar.setVisible(true);
        lblNombre.setVisible(true);
        lblNombreMostrar.setVisible(true);
    }

    public Image user(String ID, String name) throws ClassNotFoundException, SQLException {
        //Creamos el objeto de la clase prueba
        prueba pru = new prueba();
        Image us = null;//Nuestra imagén en donde almacenaremos nuestro blob convertido en imagén
        String sql = "SELECT * FROM usuariosconhuellas WHERE idusuariosConHuellas = " + ID /*+ " AND Nombre = " + name*/;
        Blob IU;
        Connection con = connect();
        try {
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                IU = rs.getBlob("Foto");//Recibimos nuestra imagén en un dato blob    
                us = javax.imageio.ImageIO.read(IU.getBinaryStream());//convertimos nuestro blob en imagén y lo almacenamos en us
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                pru.closeCon();//Cerramos la conexión después del finally
            } catch (Exception e) {
                System.out.println("Error al tratar de cerrar la conexión:\n" + e);
            }
        }//Retornamos nuestra imagen almacenada en us
        return us;
    }

    public ArrayList<setters> Lista_Usuarios() throws ClassNotFoundException, SQLException {
        ArrayList<setters> list = new ArrayList<setters>();
        Connection con = connect();
        String sql = "SELECT * FROM usuariosconhuellas";
        try {
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                setters se = new setters();//Nuevo objeto con la clase setters
                se.setIdUsuariosConHuellas(rs.getInt(1));//Añadimos los id al setter id usuarios con huellas
                se.setNombre(rs.getString(2));//Añadimos el nombre a setNombre
                se.setFoto(rs.getBytes(3));//Tomamos los bytes y los añadimos a setFoto
                list.add(se);//Añadimos el objeto se al array list
            }
        } catch (SQLException e) {
            System.out.println("Ocurrio un problema al tratar de llenar la lista de usuarios:\n" + e);
        } catch (Exception e) {
            System.out.println("Ocurrio un problema al intentar llenar la lista de usuarios:\n" + e);
        } finally {
            try {
                prueba pru = new prueba();
                pru.closeCon();//Cerramos la conexión
            } catch (Exception e) {
                System.out.println("Imposible cerrar la conexión: " + e);
            }
        }
        return list;//Devolvemos el array list
    }

    public void setIdEnMemoria(int idEnMemoria) {
        this.idEnMemoria = idEnMemoria;
    }

    public int getIdEnMemoria() {
        return idEnMemoria;
    }

    public void setFoto1(Blob foto1) {
        this.foto1 = foto1;
    }

    public Blob getFoto1() {
        return foto1;
    }

    public void setBytesImagenHuella(Blob bytesImageHuella) {
        this.bytesImagenHuella = bytesImageHuella;
    }

    public Blob getBytesImagenHuella() {
        return bytesImagenHuella;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setNombreUS(String nombreUS) {
        this.nombreUS = nombreUS;
    }

    public String getNombreUS() {
        return nombreUS;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getClave() {
        return clave;
    }
}
