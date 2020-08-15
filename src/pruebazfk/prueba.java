
package pruebazfk;

import com.sun.awt.AWTUtilities;
import com.zkteco.biometric.FingerprintSensorErrorCode;
import com.zkteco.biometric.FingerprintSensorEx;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;


public class prueba extends javax.swing.JFrame {
    
    int xE;
    int yE;
    
private long    mhDB = 0;//Catch handle
public long    mhDispositivo = 0;//Si esta en 0 significa que el dispositivo no se encuentra abierto
private boolean mbStop = true;
private byte[] template = new byte[2048];
private int[] longitudPlantilla = new int[1];
private final int nFakeFunOn = 1;
  //Registrarse
private boolean bRegister = true;
  //identificación del dedo
//private int idTemporal = 1;
  //el índice de la función de prerregistro
public int numCiclos;//Te dice el número de registro en el que estas
//plantilla de prerregistro
private byte[][] plantillasPrerregistro = new byte[3][2048];/*matriz temporal de registro,
se almacenan las plantillas registradas temporalmente*/
public static Connection connection;
//el ancho de la imagen de la huella digital
int fpAncho = 0;
//la altura de la imagen de la huella digital
int fpAlto = 0;
 private int toc;
 private WorkThread workThread = null;
private byte[] imgbuf = null;
	//Identificar
private boolean bIdentify = false;

 /*Clases creando nuevos objetos*/
 EscribirMapaBit mapa = new EscribirMapaBit();
 Busqueda buc = new Busqueda();
 Registro re1 = new Registro();
 
 /*Fuentes*/
Font fuente = new Font("AGENCY FB", Font.BOLD,15);

    public prueba() {
        
        initComponents(); 
        lblID.setVisible(false);
        lblIDMostrar.setVisible(false);
        lblNombre.setVisible(false);
        lblNombreMostrar.setVisible(false);
        this.setTitle("prueba ZKTECO");
        btnFoto.setBackground(Color.white);
        
        AWTUtilities.setWindowOpaque(this, false);
        this.setLocationRelativeTo(null);
       
    }
    
    public void Cerrar()
	{
		mbStop = true;
		try { //esperar a que se detenga el hilo
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (0 != mhDB)
		{
			FingerprintSensorEx.DBFree(mhDB);/*Liberamos la biblioteca de algoritmos*/
			mhDB = 0;
		}
		if (0 != mhDispositivo)
		{
			FingerprintSensorEx.CloseDevice(mhDispositivo);/*Especificamos el dispositivo*/
			mhDispositivo = 0;
		}
		FingerprintSensorEx.Terminate();/*liberamos recursos*/
               
	}
      
    private class WorkThread extends Thread {
      
        @Override
	        public void run() {
	            super.run();
	            int ret = 0;
	            while (!mbStop) {//Si mbStop es true esta cerrado si es false esta abierto.
                        longitudPlantilla[0] = 2048;
                      
                        /*Una vez abierto el dispositivo ret será menor a 0, al momento de colocar
                        una huella y sea detectada por AcquireFingerPrint este valor cambiara a 0.*/
	            	if (0 == (ret = FingerprintSensorEx/*Es la clase que se usa para 
                                conectar con los dispositivos de huella*/.AcquireFingerprint/*Se usa para extraer la huella digital*/
        (mhDispositivo/*Nuestro dispositivo*/, imgbuf/*Datos de la imagen(Ancho y alto)*/,
                template/*Datos de la plantilla*/, longitudPlantilla/*Longitud de la plantilla que va a devolver*/)))
	            	{
	            		if (nFakeFunOn == 1)
                    	{
                    		byte[] valoresParametro = new byte[4];
            				int[] size = new int[1];
            				size[0] = 4;
            				int byteArrayConvertido = 0;
                                        
            				//Tomamos los parametros
            				ret = FingerprintSensorEx.GetParameters(mhDispositivo, 2004, valoresParametro, size);
                                        //Mandamos como parametros los valores del parametro devuelto a la función byteArrayToInt
            				byteArrayConvertido = mapa.byteArrayToInt(valoresParametro);
                                        
            				System.out.println("retorno : "+ ret +",byte Array devuelto: " + byteArrayConvertido);
                                        
            				if (0 == ret && (byte)(byteArrayConvertido & 31) != 31)
            				{
            					JOptionPane.showMessageDialog(null,"¿¬¬?");
            					return;
            				}
                    	}
                    	MostrarHuella(imgbuf);//Muestra la imagen de la huella en el botón
                                                      
                                try {
                                    OnExtractOK(template, longitudPlantilla[0]);//Función que llama a otras funciones dependiendo del botón que se use.
                                } catch (FileNotFoundException ex) {
                                    Logger.getLogger(prueba.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (SQLException ex) {
                                    Logger.getLogger(prueba.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (ClassNotFoundException ex) {
                                    Logger.getLogger(prueba.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (IOException ex) {
                                    Logger.getLogger(prueba.class.getName()).log(Level.SEVERE, null, ex);
                                } catch (InterruptedException ex) {
                                    Logger.getLogger(prueba.class.getName()).log(Level.SEVERE, null, ex);
                                }
                        }
	                try {
	                    Thread.sleep(500);/*Se detiene la ejecución del hilo en 0.5 segundos
                            Lo cual es equivalente a 500 milisegundos*/
                            } catch (InterruptedException e) 
                        {
	                    e.printStackTrace();
	                }

	            }
	        }
    }
    
   public static Connection connect() throws ClassNotFoundException{
    try{
         connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/pruebahuellas","root","1234"); //Conexión que contiene nuestra
         //URL, USUARIO Y CONTRASEÑA la almacenamos en connection.
       System.out.println("Conectando...");
        
    }catch(SQLException e){
        JOptionPane.showMessageDialog(null, "No es posible conectar con el servidor\n"
        +"Por favor intentelo mas tarde:\n  " + e,"Error de operación",JOptionPane.ERROR_MESSAGE);
        
    }
    return connection; //Retornamos la conexión para usarla en otras clases.
    }
    
   public void closeCon(){
   try{
   connection.close();
   System.out.println("Desconectado");
   }catch(Exception e){
   System.out.println("Error al cerrar la conexión:\n" + e);
   }
   }
   
   private void OnExtractOK(byte[] template, int longitudPlantilla) throws FileNotFoundException, SQLException, ClassNotFoundException, IOException, InterruptedException//<-WorkThread llama a esta función           
   {    //re1 = new Registro();
			if(getRegister())//Si bRegister es true, se esta usando el botón de registrar
			{
                            
                            re1.registrarse(template, mhDB, numCiclos, plantillasPrerregistro);
                            numCiclos++;
                            System.out.println(numCiclos);
                            
                            if(numCiclos >= 3){//Cuando enroll_idx es igual a 3, significa que ya se ha usado
                                                //3 veces el lector de huellas con el mismo dedo
                            setRegister(false);
                            }
			}
			else
			{
				if (bIdentify)/*bIdentify es true, entonces no se ha usado el botón de verificar.
                                    Si bIdentify es true significa que se esta usando el botón de identiciar, ya
                                    que este devuelve true como valor.*/
                                    /*Esta función identifica las huellas registradas y te dice cual fue la huella que se 
                                    registro, siempre y cuando esta coincida con alguna.*/
				{
                                    
                                    Identificación ident = new Identificación();
                        ident.identificarUsuario(mhDB, template, btnFoto, btnImg,lblID,lblIDMostrar,
                                lblNombre,lblNombreMostrar);
				}
				else/*Si DBIdentify es false, entonces el botón verificar se esta usando. 
                                    Ya que verificar devuelve false como valor*/
                                    
                                    /*La función verificación sirve para verificar si el e identificar al último
                                    Usuario registrado.*/
				{
					if(re1.getLongitudPlantillaTemporal() <= 0)//Si registro e identificación son false
					{//Entonces aparece este mensaje:
                                            JOptionPane.showMessageDialog(null,"¡Por favor regístrese primero!");
					}
					else
					{
						int ret = FingerprintSensorEx.DBMatch(mhDB, re1.getPlantillaTemporal(), template);//Bdmatch comprara.
                                                //Esta función se utiliza para comparar dos plantillas de huellas digitales.
						if(ret > 0)
						{
							JOptionPane.showMessageDialog(null,"Verificación completa, score: " + ret);
						}
						else
						{
							JOptionPane.showMessageDialog(null,"Verificación fallida, ERROR: " + ret);
                                                        
						}
					}
				}
			}
		}
    
    public void abrirRegistro(long mhDB){
    registrarse dg = null;
try {
dg = new registrarse();
} catch (Exception ex) {
Logger.getLogger(prueba.class.getName()).log(Level.SEVERE, null, ex);
}
dg.setMhDB(mhDB);
dg.setLocationRelativeTo(null);
dg.setVisible(true);//Hacer visible el frame a donde vas.
this.setVisible(false);//Hacer invisible el frame en donde estas.
    }

    public void irAlRegistro(){
    
        if(0 == mhDispositivo)//Si mhDevice es diferente a 0, el dispositivo esta activo
				{
					JOptionPane.showMessageDialog(rootPane,"¡Abre el dispositivo primero!");
					return;
				}
				if(!bRegister)
				{
					numCiclos = 0;
					setRegister(true);//Es true al momento de usar el botón
					JOptionPane.showMessageDialog(rootPane,"Por favor tu dedo 3 veces!");
                                        btnFoto.setIcon(null);
                                        btnImg.setIcon(null);
        
				}
    }
       
    public void abrir(){
         // TODO Auto-generated method stub
	EscribirMapaBit mapa = new EscribirMapaBit();
      try{			
      if (0 != mhDispositivo)//Si mhDevice es diferente a 0 significa que el dispositivo ya se encuentra abierto
				{
					//already inited
					JOptionPane.showMessageDialog(rootPane, "¡El dispositivo ya se encuentra abierto!");
					return;
				}
				int ret = FingerprintSensorErrorCode.ZKFP_ERR_OK;
				//Initialize
				re1.setLongitudPlantillaTemporal(0);
				bRegister = false;
				bIdentify = false;
				numCiclos = 0;
                                
                                /*FingerprintSensor.class
                                es una clase para controlar lectores de huellas 
                                digitales, que se puede usar para iniciar y apagar 
                                un lector de huellas digitales, verificar e identificar.*/
                                
				if (FingerprintSensorErrorCode.ZKFP_ERR_OK != FingerprintSensorEx.Init())//Init, sirve para iniciar
				{
					JOptionPane.showMessageDialog(rootPane, "Ah ocurrido un error, verifique que su\n"+
                                                "dispositivo de huella este conectado.","ERROR EN LA OPERACIÓN",JOptionPane.ERROR_MESSAGE);
					return;
				}
				ret = FingerprintSensorEx.GetDeviceCount();
				if (ret < 0)
				{
					JOptionPane.showMessageDialog(rootPane, "No hay dispositivos conectados!");
                                         Cerrar();
					return;
				}
				if (0 == (mhDispositivo = FingerprintSensorEx.OpenDevice(0)))//OpenDevice nos conecta con el dispositivo
				{//Cuando el dispositivo se conecta, el valor de mhDevice cambia, deja de ser 0 para ser otro número.
					JOptionPane.showMessageDialog(rootPane, "Error: " + ret + "!","Fallo al tratar de abrir el dispositivo",JOptionPane.ERROR_MESSAGE);
					Cerrar();
					return;
				}
				if (0 == (mhDB = FingerprintSensorEx.DBInit()))/*DBInit Esta función
                                    se utiliza para inicializar la biblioteca de algoritmos.*/
				{
					JOptionPane.showMessageDialog(rootPane, "ERROR: " + ret + "!","Error al tratar de abrir la biblioteca de algoritmos",JOptionPane.ERROR_MESSAGE);
					Cerrar();
					return;
				}
				
				//For ISO/Ansi
				int ISO = 0;	//Ansi
				if (radioISO.isSelected())
				{
					ISO = 1;	//ISO
				}
				FingerprintSensorEx.DBSetParameter(mhDB,  5010,/*Mandamos 0 como parametro si queremos el estandar AnSI*/ ISO);				
				//Para ISO/Ansi Fin                              Mandamos 1 como parametro si queremos el estandar ISO 
				
				//establecer fakefun off
				//FingerprintSensorEx.SetParameter(mhDevice, 2002, changeByte(nFakeFunOn), 4);
				
				byte[] paramValue = new byte[4];
				int[] size = new int[1];
				//GetFakeOn
				//size[0] = 4;
				//FingerprintSensorEx.GetParameters(mhDevice, 2002, paramValue, size);
				//nFakeFunOn = byteArrayToInt(paramValue);
				
				size[0] = 4;
				FingerprintSensorEx.GetParameters(mhDispositivo/*Nuestro dispositivo*/, 1/*Indica el alcho*/,
                                        paramValue/*Valor del parametro que devuelve*/, size/*Tamaño de datos del parametro*/);
				fpAncho = mapa.byteArrayToInt(paramValue);//ANCHO
				size[0] = 4;
				FingerprintSensorEx.GetParameters(mhDispositivo, 2/*Indica el alto*/, paramValue, size);
				fpAlto = mapa.byteArrayToInt(paramValue);//ALTO
				//width = fingerprintSensor.getImageWidth();
				//height = fingerprintSensor.getImageHeight();
				imgbuf = new byte[fpAncho*fpAlto];
				btnImg.resize(fpAncho, fpAlto);/*usamos la función resize para modificar el tamaño del botón de la imagen
                                le mandamos como parametro el alto y ancho para reescalarla*/
				mbStop = false;
				workThread = new WorkThread();
                                
			    workThread.start();// Inicio de hilo, la función de workThread.
	            
                            JOptionPane.showMessageDialog(rootPane, "El dispositivo se encuentra abierto","ABIERTO",JOptionPane.INFORMATION_MESSAGE);
                   
        }catch(Exception e){JOptionPane.showConfirmDialog(rootPane, e);}
    }
       
    private void MostrarHuella(byte[] imgBuf)
		{
                    EscribirMapaBit wm = new EscribirMapaBit();
			try {
				wm.writeBitmap(imgBuf, fpAncho, fpAlto, "Huella.bmp");
				btnImg.setIcon(new ImageIcon(ImageIO.read(new File("Huella.bmp"))));//Botón
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    
    public void identificación(){
      int ret = FingerprintSensorEx.DBCount(mhDB);
      
        if(ret>0){
        int[] fid = new int[1];
					if(bRegister)//bRegister es un booleanos que es true unicamente para registrar usuarios
				{
					numCiclos = 0;
					bRegister = false;
				}
				if(!bIdentify)
				{
					bIdentify = true;//Pasamos bIdentify a true para ejecutar nuestra siguiente función.
				}                               
//Lo decimos al usuario por medio de un mensaje en pantalla que coleque su dedo en el lector:
JOptionPane.showMessageDialog(null, "Por favor, pon tu dedo en el lector.","VERIFICACIÓN",JOptionPane.INFORMATION_MESSAGE);

 } else{
            bIdentify = false;
       }
    }

    public void setRegister(boolean register){
    this.bRegister=register;
    }
    
    public boolean getRegister(){
    return bRegister;
    }
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        btnImg = new javax.swing.JButton();
        arrastrar = new javax.swing.JLabel();
        close = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblBuscar = new javax.swing.JLabel();
        lblAbrir = new javax.swing.JLabel();
        lblCerrar = new javax.swing.JLabel();
        lblBorrar = new javax.swing.JLabel();
        lblRegistrarse = new javax.swing.JLabel();
        radioISO = new javax.swing.JRadioButton();
        TarjetaUsuario = new javax.swing.JPanel();
        btnFoto = new javax.swing.JButton();
        lblID = new javax.swing.JLabel();
        lblIDMostrar = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblNombreMostrar = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel2.setBackground(new java.awt.Color(205, 231, 254));

        btnImg.setBackground(new java.awt.Color(246, 246, 246));
        btnImg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImgActionPerformed(evt);
            }
        });

        arrastrar.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
        arrastrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                arrastrarMousePressed(evt);
            }
        });
        arrastrar.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                arrastrarMouseDragged(evt);
            }
        });

        close.setIcon(new javax.swing.ImageIcon("C:\\Users\\manuel.vargas\\Documents\\NetBeansProjects\\PruebaZFK\\img\\CERRAR.png")); // NOI18N
        close.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(36, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(btnImg, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(arrastrar, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(close)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(close, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(arrastrar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnImg, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        jPanel3.setBackground(new java.awt.Color(205, 231, 254));

        lblBuscar.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblBuscar.setForeground(new java.awt.Color(255, 255, 255));
        lblBuscar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblBuscar.setText("BUSCAR USUARIO");
        lblBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBuscarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblBuscarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblBuscarMouseExited(evt);
            }
        });

        lblAbrir.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblAbrir.setForeground(new java.awt.Color(255, 255, 255));
        lblAbrir.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblAbrir.setText("ABRIR");
        lblAbrir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblAbrirMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblAbrirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblAbrirMouseExited(evt);
            }
        });

        lblCerrar.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblCerrar.setForeground(new java.awt.Color(255, 255, 255));
        lblCerrar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCerrar.setText("CERRAR");
        lblCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCerrarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblCerrarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblCerrarMouseExited(evt);
            }
        });

        lblBorrar.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblBorrar.setForeground(new java.awt.Color(255, 255, 255));
        lblBorrar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblBorrar.setText("BORRAR");
        lblBorrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBorrarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblBorrarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblBorrarMouseExited(evt);
            }
        });

        lblRegistrarse.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lblRegistrarse.setForeground(new java.awt.Color(255, 255, 255));
        lblRegistrarse.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblRegistrarse.setText("REGISTRARSE");
        lblRegistrarse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblRegistrarseMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblRegistrarseMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblRegistrarseMouseExited(evt);
            }
        });

        radioISO.setBackground(new java.awt.Color(22, 38, 47));
        radioISO.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        radioISO.setForeground(new java.awt.Color(255, 255, 255));
        radioISO.setText("ISO");

        TarjetaUsuario.setBackground(new java.awt.Color(221, 239, 254));
        TarjetaUsuario.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        TarjetaUsuario.setForeground(new java.awt.Color(22, 38, 47));

        lblID.setText("jLabel1");

        lblIDMostrar.setText("jLabel1");

        lblNombre.setText("jLabel1");

        lblNombreMostrar.setText("jLabel1");

        javax.swing.GroupLayout TarjetaUsuarioLayout = new javax.swing.GroupLayout(TarjetaUsuario);
        TarjetaUsuario.setLayout(TarjetaUsuarioLayout);
        TarjetaUsuarioLayout.setHorizontalGroup(
            TarjetaUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TarjetaUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(TarjetaUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(TarjetaUsuarioLayout.createSequentialGroup()
                        .addGroup(TarjetaUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblID)
                            .addComponent(lblNombre))
                        .addGap(18, 18, 18)
                        .addComponent(lblIDMostrar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 57, Short.MAX_VALUE)
                        .addComponent(btnFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(TarjetaUsuarioLayout.createSequentialGroup()
                        .addComponent(lblNombreMostrar)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        TarjetaUsuarioLayout.setVerticalGroup(
            TarjetaUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TarjetaUsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(TarjetaUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(TarjetaUsuarioLayout.createSequentialGroup()
                        .addGroup(TarjetaUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblID)
                            .addComponent(lblIDMostrar))
                        .addGap(35, 35, 35)
                        .addComponent(lblNombre))
                    .addComponent(btnFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNombreMostrar)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblBuscar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCerrar)
                            .addComponent(lblBorrar)
                            .addComponent(lblRegistrarse))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(lblAbrir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(radioISO, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36))
                    .addComponent(TarjetaUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TarjetaUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addComponent(lblBuscar)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAbrir)
                    .addComponent(radioISO))
                .addGap(18, 18, 18)
                .addComponent(lblCerrar)
                .addGap(18, 18, 18)
                .addComponent(lblBorrar)
                .addGap(18, 18, 18)
                .addComponent(lblRegistrarse)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void btnImgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImgActionPerformed
    if(toc>=3){
    JOptionPane.showMessageDialog(rootPane, "YAAAA!");
    }else{
        JOptionPane.showMessageDialog(rootPane, "No me uses como botón ¬¬'");
    toc++;
    }
    }//GEN-LAST:event_btnImgActionPerformed
    
    public void Borrar(int i,long mhDB) throws InterruptedException{
       int ret;
 
       //En el parametro i recibimos el ID del usuario
        ret = FingerprintSensorEx.DBDel(mhDB, i);//Esta linea se encarga de borrar al usuario, que en este caso
        //El usuario es i.
        System.out.println("Se elimino de la memoria al usuario: " + i);
        if(ret!=0){
       JOptionPane.showMessageDialog(rootPane, "Error al intentar borrar usuarios de memoria: " + ret);
                  }
        
    }
    
    private void lblBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBuscarMouseClicked
       if(0 == mhDispositivo)
				{
					JOptionPane.showMessageDialog(null,"Por favor, abre el dispositivo primero!");
					return;
				}
        try {
        buc.buscar( mhDB,template);
        identificación();
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(prueba.class.getName()).log(Level.SEVERE, null, ex);
    } catch (SQLException ex) {
        Logger.getLogger(prueba.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
        Logger.getLogger(prueba.class.getName()).log(Level.SEVERE, null, ex);
    }
    }//GEN-LAST:event_lblBuscarMouseClicked

    private void lblAbrirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAbrirMouseClicked
    abrir();      
    }//GEN-LAST:event_lblAbrirMouseClicked

    private void lblCerrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCerrarMouseClicked
        Cerrar();	
 JOptionPane.showMessageDialog(null,"El dispositivo se encuentra cerrado.","CERRADO",JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_lblCerrarMouseClicked

    private void lblBorrarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBorrarMouseClicked
        borrar dg =  null;
        try{
            dg = new borrar();
        } catch(Exception e){
        Logger.getLogger(prueba.class.getName()).log(Level.SEVERE, null, e);
        }
        dg.setLocationRelativeTo(null);
        dg.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_lblBorrarMouseClicked

    private void lblRegistrarseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRegistrarseMouseClicked
       if(bRegister){
System.out.println("bRegistar ya es true");
}
        irAlRegistro(); 
    }//GEN-LAST:event_lblRegistrarseMouseClicked

    private void lblBuscarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBuscarMouseExited
       this.lblBuscar.setForeground(Color.WHITE);
    }//GEN-LAST:event_lblBuscarMouseExited

    private void lblBuscarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBuscarMouseEntered
       lblBuscar.setForeground(Color.red);
    }//GEN-LAST:event_lblBuscarMouseEntered

    private void lblAbrirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAbrirMouseEntered
      
          this.lblAbrir.setForeground(Color.red);
    }//GEN-LAST:event_lblAbrirMouseEntered

    private void lblAbrirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblAbrirMouseExited
      this.lblAbrir.setForeground(Color.white);
    }//GEN-LAST:event_lblAbrirMouseExited

    private void lblCerrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCerrarMouseEntered
       this.lblCerrar.setForeground(Color.red);
    }//GEN-LAST:event_lblCerrarMouseEntered

    private void lblCerrarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCerrarMouseExited
        this.lblCerrar.setForeground(Color.white);
    }//GEN-LAST:event_lblCerrarMouseExited

    private void lblBorrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBorrarMouseEntered
   
      this.lblBorrar.setForeground(Color.red);
    }//GEN-LAST:event_lblBorrarMouseEntered

    private void lblBorrarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBorrarMouseExited
     this.lblBorrar.setForeground(Color.white);
    }//GEN-LAST:event_lblBorrarMouseExited

    private void lblRegistrarseMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRegistrarseMouseEntered
      this.lblRegistrarse.setForeground(Color.red);
    }//GEN-LAST:event_lblRegistrarseMouseEntered

    private void lblRegistrarseMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRegistrarseMouseExited
        this.lblRegistrarse.setForeground(Color.WHITE);
    }//GEN-LAST:event_lblRegistrarseMouseExited

    private void arrastrarMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_arrastrarMousePressed
       
         xE = evt.getX();
         yE = evt.getY();
    }//GEN-LAST:event_arrastrarMousePressed

    private void arrastrarMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_arrastrarMouseDragged
        this.setLocation(this.getLocation().x + evt.getX() - xE, this.getLocation().y + evt.getY() - yE);
    }//GEN-LAST:event_arrastrarMouseDragged

    private void closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeMouseClicked
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_closeMouseClicked

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
            java.util.logging.Logger.getLogger(prueba.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(prueba.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(prueba.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(prueba.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new prueba().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel TarjetaUsuario;
    private javax.swing.JLabel arrastrar;
    private javax.swing.JButton btnFoto;
    public javax.swing.JButton btnImg;
    private javax.swing.JLabel close;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblAbrir;
    private javax.swing.JLabel lblBorrar;
    private javax.swing.JLabel lblBuscar;
    private javax.swing.JLabel lblCerrar;
    private javax.swing.JLabel lblID;
    private javax.swing.JLabel lblIDMostrar;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNombreMostrar;
    private javax.swing.JLabel lblRegistrarse;
    private javax.swing.JRadioButton radioISO;
    // End of variables declaration//GEN-END:variables
}
