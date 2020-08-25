package búsqueda;

import búsqueda.Busqueda;
import vista.prueba;
import com.zkteco.biometric.FingerprintSensorEx;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author manuel.vargas
 */
public class Identificación {

    public void identificarUsuario(long mhDB, byte[] template, JButton btnFoto, JButton btnImg, JLabel lblfoto, JLabel lblIDMostrar, JLabel lblNombre, JLabel lblNombreMostrar) throws InterruptedException, IOException, SQLException, ClassNotFoundException {
        Busqueda buc = new Busqueda();
        prueba prueba = new prueba();

        int[] idDevuelta = new int[1];
        int[] score = new int[1];
        int ret = FingerprintSensorEx.DBIdentify(mhDB,
                template/*Plantilla de huella digital*/,
                idDevuelta/*El ID del usuario que nos devuelve la función*/,
                score/*El puntaje de comparación*/);/*DBIdentify Esta función se utiliza para realizar una comparación 1: N.
                                        Compara la huella con las otras huellas ya registradas antes*/
        if (ret == 0) //Cuando ret es igual a 0 la identificación es correcta
        {

            buc.busqueda(idDevuelta, btnFoto, score, lblfoto, lblIDMostrar, lblNombre, lblNombreMostrar);/*Manda los parametros a la función búsqueda
                        para mostrar la información del usuario en pantalla*/

            int i = Integer.parseInt(buc.getId());//Parseamos el id del usuario y lo movemos a la variable i
            prueba.Borrar(i, mhDB);/*Ese valor lo mandamos a la función borrar para proceder a eliminar al usuario
                         de la memoria*/
            Thread.sleep(5 * 1000);
            btnFoto.setIcon(null);
            btnImg.setIcon(null);
            lblfoto.setVisible(false);
            lblIDMostrar.setVisible(false);
            lblNombre.setVisible(false);
            lblNombreMostrar.setVisible(false);

        } else {
            JOptionPane.showMessageDialog(null, "Verificación fallida, Por favor,\n" + "Intentelo de nuevo.", "VERIFICACIÓN FALLIDA, ERROR: " + ret, JOptionPane.INFORMATION_MESSAGE);
        }

    }

}
