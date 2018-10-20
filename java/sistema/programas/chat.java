package sistema.programas;
import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;
public class chat extends JInternalFrame implements ActionListener{
	/*declaramos los objetos*/
	private JLabel label1, label2;
	private JTextField nombre, mensaje;
	private JTextArea chat;
	private JScrollPane chat_con_scroll;
	private JButton boton;
	/*creamos el formulario*/
	public chat(String usuario){
		super("Chat", true, true, false, true);
		setLayout(null);
		setSize(350, 400);
		setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		label1 = new JLabel("Usuario:");
		nombre = new JTextField(5);
		nombre.setText(String.valueOf(usuario));
		nombre.setEditable(false);
		label2 = new JLabel("Mensaje:");
		mensaje = new JTextField(5);
		boton = new JButton("enviar mensaje");
		chat = new JTextArea(5, 5);
		chat.setLineWrap(true);
		chat.setWrapStyleWord(true);
		chat_con_scroll = new JScrollPane(chat, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(label1);
		add(nombre);
		add(label2);
		add(mensaje);
		add(boton);
		add(chat_con_scroll);
		label1.setBounds(20, 20, 100, 25);
		nombre.setBounds(120, 20, 200, 25);
		label2.setBounds(20, 55, 100, 25);
		mensaje.setBounds(120, 55, 200, 25);
		boton.setBounds(20, 90, 300, 25);
		chat_con_scroll.setBounds(20, 130, 300, 215);
		boton.addActionListener(this);
		/*hilo de proceso que actualiza la mensajeria cada segundo sin detener el programa*/
		Thread proceso_paralelo = new Thread("Hilos de procesos"){
			public void run(){
				do{
					try{
						String actualizado = CHAT_WEB("","");
						/*solo si hay cambios actualizamos el chat*/
						if(!((chat.getText()).equals(actualizado))){
							chat.setText(actualizado);
						}
						Thread.sleep(1000);
					}catch(Exception e){}
				}while(true);
			}
		};
		proceso_paralelo.start();//ejecutamos hilo de proceso
	}
	/*metodo que se activa al presionar un boton*/
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==boton){
			if(!((nombre.getText()).equals("")) && !((mensaje.getText()).equals(""))){
				String mostrar = CHAT_WEB(nombre.getText(), mensaje.getText());
				mensaje.requestFocus();
				chat.setText(mostrar);
			}else{JOptionPane.showMessageDialog(null,"Ingrese datos en los campos:\nUsuario y Mensaje","ADVERTENCIA",2);}
		}
	}
	private String CHAT_WEB(String nombre, String mensaje){
		try{
			/*creamos url de conexion al servidor web*/
			URL url = new URL("https://ravr.webcindario.com/5_chat/"+"servidor.php");
			HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
			conexion.setDoInput(true); //conexion de entrada
			conexion.setDoOutput(true); //conexion de salida
			conexion.setUseCaches(false); //conexion sin usar cache
			conexion.setRequestMethod("POST"); //enviar datos via POST
			conexion.connect(); //ejecutamos la conexion
			/*creamos objeto de cadena de salida*/
			DataOutputStream salida = new DataOutputStream(conexion.getOutputStream());
			/*cadena de envio de datos*/
			String cadena_de_datos = "nombre=" + URLEncoder.encode(nombre) + "&mensaje=" + URLEncoder.encode(mensaje);
			/*enviamos datos*/
			salida.writeBytes(cadena_de_datos);
			salida.flush();
			salida.close();
			/*creamos objeto de cadena de entrada*/
			DataInputStream entrada = new DataInputStream(conexion.getInputStream());
			String linea = null; //halamos linea por linea
			String retorno_web = "";
			while(null != (linea = entrada.readLine())) {
				retorno_web = retorno_web + linea + "\n";
			}
			entrada.close();
			return(retorno_web);
		}
		catch(Exception e){
			return("\nError de conexion con el servidor web...\n");
		}
	}
}
