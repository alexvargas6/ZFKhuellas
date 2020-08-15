
package pruebazfk;

import com.sun.istack.internal.logging.Logger;
import com.zkteco.biometric.FingerprintSensorEx;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import static pruebazfk.prueba.connect;

public class Registro {

    private String ruta;
    private long dispositivo;
    private boolean registr = true;
    private int longitudPlantillaTemporal = 0;
     prueba pru = null;
     private /*final*/ byte[] plantillaTemporal = new byte[2048];/*Copia temporal de última plantilla registrada
     Almacena de manera temporal la última plantilla registrada*/
  
public void registrarse(byte[] template,long mhDB,int numCiclos,byte[][] plantillasPrerregistro) throws InterruptedException{
  pru = new prueba();
       
				int[] idDevuelto = new int[1]; /*<- el id se almacena en este array*/
				int[] score = new int [1]; /*<- puntaje de comparación*/
                int ret = FingerprintSensorEx.DBIdentify(mhDB, template, idDevuelto, score);//Verifica si no existe la huella registrada en memoria
                if (ret == 0)
                {
                    JOptionPane.showMessageDialog(null,"el dedo ya se encuentra registrado\n "+"Y fue el número: " + idDevuelto[0] 
                            + " En inscribirse, Se cancelara el registro");
                    pru.setRegister(false);
                    numCiclos = 0;
                    return;
                }
        if (numCiclos > 0 && FingerprintSensorEx.DBMatch(mhDB, plantillasPrerregistro[numCiclos-1]/*Template 1, es 
                        la última huella que ingresamos antes de esta*/
                        , /*Template 2*/template/*template es la huella que acabamos de ingresar*/) <= 0)
            /*DBmatch compara 2 huellas diferentes*/
                {
                	JOptionPane.showMessageDialog(null,"presione el mismo dedo 3 veces para la inscripción");
                        return;
                }
        //Copiamos template a la plantilla prerregistrada:
         System.arraycopy(template/*array de origen*/, 0/*Posición de array de origen*/,
                        plantillasPrerregistro[numCiclos]/*Array de destino*/, 0/*Posición de inicio del array destino*/, 2048/*Número de elementos a copiar*/);
     
                if (numCiclos == 2) 
                {//Si enroll_idx es igual a 3, significa que se ha registrado el dedo 3 veces
                int[] longitudDevuelta = new int[1];
                      longitudDevuelta[0] = 2048; /*<- Longitud de la plantilla registrada*/
                    byte[] plantillaRegistrada = new byte[longitudDevuelta[0]];//regTemp es plantilla registrada
                    
                    if (0 == (ret = FingerprintSensorEx.DBMerge/*Esta función se usa para combinar plantillas de huellas dactilares registradas*/
                                    (mhDB/*catch handle*/, plantillasPrerregistro[0]/*Plantilla preregistrada1*/,
                                            plantillasPrerregistro[1]/*Plantilla preregistrada2*/,
                                            plantillasPrerregistro[2]/*plantilla preregistrada3*/,
                                            plantillaRegistrada/*Plantilla registrada de vuelta*/,
                                            longitudDevuelta/*Longitud de la plantilla registrada devuelta por la función*/)) &&
                    		0 == (ret = FingerprintSensorEx.DBAdd/*Esta función se utiliza para agregar una plantilla registrada a la memoria.*/
                                            (mhDB, 1/*ID de la huella digital*/, plantillaRegistrada/*Plantilla registrada*/))) {
                  
                    	//longitudPlantillaTemporal = _retLen[0];
                        setLongitudPlantillaTemporal(longitudDevuelta[0]);
                        System.arraycopy(plantillaRegistrada/*Array de origen*/,
                                0/*Posición de inicio de array de origen*/,
                                plantillaTemporal/*Array destino*/,
                                0/*Posición de origen en el array destino*/,
                                getLongitudPlantillaTemporal()/*Número de elementos a copiar*/);/*Método que copia desde la posición 
                        origen de un array a un array destino en una posición específica.
                        El número de elementos copiados también se identifica como parámetro.*/
                        //Base64 Template
                        setPlantillaTemporal(plantillaTemporal);
                        System.out.println("La longitud de la plantilla temporal es: " + getLongitudPlantillaTemporal());
   pru.abrirRegistro(mhDB); /*<- Abrimos la ventana de registro*/
   
   System.out.println(mhDB);
  
  
                    } else {
                        //Si no se pueden combinar las plantillas o añadir la huella a la memoria:
                             JOptionPane.showMessageDialog(null,"error de inscripción, código de error =" + ret);
  
                           }
                  pru.setRegister(false);
                } else {
                	JOptionPane.showMessageDialog(null,"Necesitas presionar en el lector con su dedo " + (2 - numCiclos) + " veces de huella digital");               
                       } 
}
    
public void seleccionarFoto(){
     prueba pru = new prueba();
     JFileChooser j = new JFileChooser();//Llama a la clase JFileChooser
        //Mandamos nuestras extensiones como variables a la clase FilesNameExtensionFilter
        FileNameExtensionFilter fil = new FileNameExtensionFilter("JPG,BMP,PNG & GIF","jpg","png","gif","bmp");
        j.setFileFilter(fil);//Mandamos como variable nuestra clase FileNameExtensionFilter que contiene nuestros formatos de imagen.
        //A la función setFileFilter dentro de la clase JFileChooser.
                           
        int s = j.showOpenDialog(pru);//showOpenDialog abre la ventana de selección
        if(s == JFileChooser.APPROVE_OPTION){
             setRuta(j.getSelectedFile().getAbsolutePath());//En el setter "setRuta" se almacena nuestra imagen
        }//seleccionada.

 
        }
    
public boolean registroBD(String ruta,String name,long mhDB) throws InterruptedException, SQLException, ClassNotFoundException{
  pru = new prueba();
Connection con = connect(); 
     try{                       
                       PreparedStatement st;
                      boolean valido = validar(ruta,name);
                      
                      if(valido){
                      
                      try (FileInputStream is = new FileInputStream("Huella.bmp")/*Aquí recogemos la imagen generada de la
                                        huella y la almacenamos en "IS"*/){
                                    FileInputStream foto = new FileInputStream(ruta);//FileInputStream recoge la imagen según la ruta establecida
                                    st = con.prepareStatement("insert into usuariosconhuellas(Nombre,Foto,Huella) values(?,?,?)");
                                    st.setString(1, name);
                                    st.setBlob(2, foto);
                                    st.setBlob(3, is);
                                    st.execute();
                                }
                      
                          st.close();
                      
                        JOptionPane.showMessageDialog(null,"REGISTRO COMPLETO!"); 
                        System.out.println(mhDB);
                            pru.Borrar(1, mhDB);
                                setRegistr(true);
                       }
                      else{
                          JOptionPane.showMessageDialog(null,"No se pueden registrar usuarios sin foto y/o nombre","NO SE REGISTRO NINGÚN USUARIO",JOptionPane.INFORMATION_MESSAGE);
                                           
                      }
     }
     catch(SQLException | IOException e){JOptionPane.showMessageDialog(null,"Error al tratar de registrar usuario:\n " + e);  registr = false; } finally{
     try{
         pru.closeCon();
     }catch(Exception e){
     System.out.println("Error al tratar de cerrar conexión:\n " + e);
     }
     }       
      return getRegistr();
    }
    
public boolean validar(String ruta,String nombre){  
    
        boolean valido;
        if(ruta == null || nombre.length() <= 2)
    {
        
        JOptionPane.showMessageDialog(null, "No se selecciono ninguna foto y/o nombre","ERROR",JOptionPane.ERROR_MESSAGE);
   
        return valido= false;
    }
        return valido=true;
    }
    
public void setRuta(String ruta){
this.ruta=ruta;
}

public String getRuta(){
return ruta;
}

public void setDispositivo(long dispositivo){
this.dispositivo=dispositivo;
}

public long getDispositivo(){
return dispositivo;
}

public void setRegistr(boolean registr){
this.registr=registr;
}

public boolean getRegistr(){
return registr;
}

public void setLongitudPlantillaTemporal(int longitudPlantillaTemporal){
this.longitudPlantillaTemporal=longitudPlantillaTemporal;
}

public int getLongitudPlantillaTemporal(){
return longitudPlantillaTemporal;
}

public void setPlantillaTemporal(byte[] plantillaTemporal){
this.plantillaTemporal=plantillaTemporal;
}

public byte[] getPlantillaTemporal(){
return plantillaTemporal;
}

}
