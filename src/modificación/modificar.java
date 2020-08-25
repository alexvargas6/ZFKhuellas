package modificación;

import vista.prueba;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import static vista.prueba.connect;

public class modificar {

    prueba pru = new prueba();

    public modificar(String nombre, String ID, FileInputStream Nfoto) {
        this.nombre = nombre;
        this.ID = ID;
        this.Nfoto = Nfoto;
    }
    private String nombre;
    private String ID;
    private FileInputStream Nfoto;

    public String getNombre() {
        return nombre;
    }

    public String getID() {
        return ID;
    }

    public FileInputStream getNfoto() {
        return Nfoto;
    }

    public void update() throws ClassNotFoundException, SQLException {
        try {
            Connection con = connect();
            PreparedStatement pst;
            if (getNfoto() == null) {
                String SQL = "UPDATE usuariosconhuellas SET Nombre=? WHERE idusuariosConHuellas=?";

                pst = con.prepareStatement(SQL);
                pst.setString(1, getNombre());
                pst.setString(2, getID());
            } else {
                String SQL = "UPDATE usuariosconhuellas SET Nombre=?, Foto=? WHERE idusuariosConHuellas=?";

                pst = con.prepareStatement(SQL);
                pst.setString(1, getNombre());
                pst.setBlob(2, Nfoto);
                pst.setString(3, getID());
            }
            if (pst.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Se han actualizado los datos correctamente", "OPERACIÓN EXITOSA", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                pru.closeCon();
            } catch (Exception e) {
                System.out.println("Problemas al cerrar la conexión");
            }
        }
    }

}
