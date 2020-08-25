
package tablaBorrar;

/**
 *
 * @author manuel.vargas
 */
public class setters {

    public void setIdUsuariosConHuellas(int idUsuariosConHuellas) {
        this.idUsuariosConHuellas = idUsuariosConHuellas;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public void setHuella(byte[] Huella) {
        this.Huella = Huella;
    }

    public int getIdUsuariosConHuellas() {
        return idUsuariosConHuellas;
    }

    public String getNombre() {
        return nombre;
    }

    public byte[] getFoto() {
        return foto;
    }

    public byte[] getHuella() {
        return Huella;
    }
    
    int idUsuariosConHuellas;
    String nombre;
    byte[] foto;
    byte[] Huella;
    
}


