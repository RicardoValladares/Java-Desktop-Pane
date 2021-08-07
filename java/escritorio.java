import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import sistema.archivo;
import sistema.consola;
import sistema.pantalla;
import sistema.programas.*;
import sistema.juegos.solitario;
public class escritorio extends JFrame implements ActionListener{
	/*declaramos los objetos*/
	public JDesktopPane desktop;
	private JMenuBar menubar;
	private JMenu menu;
	private JMenuItem calculadora, solitario, chat, consola, navegador, pantalla, texto, imagen, archivo, musica, servidor, html, video;
	public String ruta_local;
	private String separador;
	public escritorio(){
		/*creamos escritorio*/
		desktop = new JDesktopPane();
		desktop.setBackground(new Color(204, 204, 255)); 
		/*creamos la barra de menu*/
		menubar = new JMenuBar();
		menu = new JMenu("Inicio");
		menu.setIcon(new ImageIcon(getClass().getResource("inicio.png")));
		menubar.add(menu);
		/*creamos objetos para el menu*/
		archivo = new JMenuItem("Archivo", new ImageIcon(getClass().getResource("programa.png")));
		archivo.addActionListener(this);
		menu.add(archivo);
		/*creamos objetos para el menu*/
		calculadora = new JMenuItem("Calculadora", new ImageIcon(getClass().getResource("programa.png")));
		calculadora.addActionListener(this);
		menu.add(calculadora);
		/*creamos objetos para el menu*/
		chat = new JMenuItem("Chat", new ImageIcon(getClass().getResource("programa.png")));
		chat.addActionListener(this);
		menu.add(chat);
		/*creamos objetos para el menu*/
		consola = new JMenuItem("Consola", new ImageIcon(getClass().getResource("programa.png")));
		consola.addActionListener(this);
		menu.add(consola);
		/*creamos objetos para el menu*/
		html = new JMenuItem("HTML", new ImageIcon(getClass().getResource("programa.png")));
		html.addActionListener(this);
		menu.add(html);
		/*creamos objetos para el menu*/
		imagen = new JMenuItem("Imagen", new ImageIcon(getClass().getResource("programa.png")));
		imagen.addActionListener(this);
		menu.add(imagen);
		/*creamos objetos para el menu*/
		musica = new JMenuItem("Musica", new ImageIcon(getClass().getResource("programa.png")));
		musica.addActionListener(this);
		menu.add(musica);
		/*creamos objetos para el menu*/
		navegador = new JMenuItem("Navegador", new ImageIcon(getClass().getResource("programa.png")));
		navegador.addActionListener(this);
		menu.add(navegador);
		/*creamos objetos para el menu*/
		pantalla = new JMenuItem("Pantalla", new ImageIcon(getClass().getResource("programa.png")));
		pantalla.addActionListener(this);
		menu.add(pantalla);
		/*creamos objetos para el menu*/
		servidor = new JMenuItem("Servidor", new ImageIcon(getClass().getResource("programa.png")));
		servidor.addActionListener(this);
		menu.add(servidor);
		/*creamos objetos para el menu*/
		solitario = new JMenuItem("Solitario", new ImageIcon(getClass().getResource("programa.png")));
		solitario.addActionListener(this);
		menu.add(solitario);
		/*creamos objetos para el menu*/
		texto = new JMenuItem("Texto", new ImageIcon(getClass().getResource("programa.png")));
		texto.addActionListener(this);
		menu.add(texto);
		/*creamos objetos para el menu*/
		video = new JMenuItem("Video", new ImageIcon(getClass().getResource("programa.png")));
		video.addActionListener(this);
		menu.add(video);
		/*agregamos el escritorio y el menu a nuestro formulario*/
		setContentPane(desktop);
		setJMenuBar(menubar);
		/*inicializamos la ruta de trabajo*/
		if((System.getProperty("os.name").substring(0, 3)).equals("Win")){separador = File.separator + File.separator;}
		else{separador = File.separator;}
		/*inicializamos carpeta de trabajo*/
		try{
			File ruta_con_jar = new File(escritorio.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
			File ruta_sin_jar = ruta_con_jar.getParentFile();
			ruta_local = (ruta_sin_jar.getPath())+(separador)+("archivos")+(separador);
			File folder = new File(ruta_local);
			if(!folder.exists()){folder.mkdir();}
			/*descomprimimos archivos*/
			InputStream localInputStream = null;
			FileOutputStream localFileOutputStream = null;
			byte[] arrayOfByte = new byte[1024];
			int i;
			//descomprimimos imagen
			localInputStream = escritorio.class.getResourceAsStream("imagen.png");
			localFileOutputStream = new FileOutputStream(ruta_local+"imagen.png");
			while((i = localInputStream.read(arrayOfByte)) > 0){localFileOutputStream.write(arrayOfByte, 0, i);}
			localInputStream.close();
			localFileOutputStream.close();
			//descomprimimos texto
			localInputStream = escritorio.class.getResourceAsStream("texto.txt");
			localFileOutputStream = new FileOutputStream(ruta_local+"texto.txt");
			while((i = localInputStream.read(arrayOfByte)) > 0){localFileOutputStream.write(arrayOfByte, 0, i);}
			localInputStream.close();
			localFileOutputStream.close();
			//descomprimimos audio
			localInputStream = escritorio.class.getResourceAsStream("video.mp4");
			localFileOutputStream = new FileOutputStream(ruta_local+"video.mp4");
			while((i = localInputStream.read(arrayOfByte)) > 0){localFileOutputStream.write(arrayOfByte, 0, i);}
			localInputStream.close();
			localFileOutputStream.close();
			//descomprimimos video
			localInputStream = escritorio.class.getResourceAsStream("audio.wav");
			localFileOutputStream = new FileOutputStream(ruta_local+"audio.wav");
			while((i = localInputStream.read(arrayOfByte)) > 0){localFileOutputStream.write(arrayOfByte, 0, i);}
			localInputStream.close();
			localFileOutputStream.close();
			//descomprimimos pagina web
			File dir = new File(ruta_local+"web"+(separador));
			if(!dir.exists()){
				dir.mkdir();
				localInputStream = escritorio.class.getResourceAsStream("index.html");
				localFileOutputStream = new FileOutputStream(ruta_local+"web"+(separador)+"index.html");
				while((i = localInputStream.read(arrayOfByte)) > 0){localFileOutputStream.write(arrayOfByte, 0, i);}
				localInputStream.close();
				localFileOutputStream.close();
			}
		}catch(Exception e){}
    }
    /*metodo que se activa al presionar una opcion*/
	public void actionPerformed(ActionEvent a) {
		if(a.getSource()==chat){ programa1(); }
		if(a.getSource()==calculadora){ programa2(""); }
		if(a.getSource()==solitario){ programa3(); }
		if(a.getSource()==consola){ programa4(); }
		if(a.getSource()==navegador){ programa5(); }
		if(a.getSource()==pantalla){ programa6(); }
		if(a.getSource()==texto){ programa7(); }
		if(a.getSource()==imagen){ programa8(); }
		if(a.getSource()==archivo){ programa9(); }
		if(a.getSource()==musica){ programa10(); }
		if(a.getSource()==servidor){ programa11(); }
		if(a.getSource()==html){ programa12(); }
		if(a.getSource()==video){ programa13(); }
	}
	/*metodos para el chat*/
	public void programa1(){
		String user ="";
		JLabel label1 = new JLabel("Usuario: ");	JTextField usuario = new JTextField();
		JLabel label2 = new JLabel("Password: ");	JPasswordField password = new JPasswordField();
		final JComponent[] componentes = new JComponent[]{ label1, usuario, label2, password };
		int entrada = JOptionPane.showInternalConfirmDialog(desktop, componentes, "Login", 2);
		if(entrada == 0){
			user = usuario.getText();
			String pass =  new String(password.getPassword());
			if((user.equals("R_A_V_R_") && pass.equals("0123456789")) || (user.equals("V_N_A_Q_") && pass.equals("0123456789"))){ JOptionPane.showInternalMessageDialog(desktop,"Usuario: "+user,"Datos Validos",3); }
			else{JOptionPane.showInternalMessageDialog(desktop,"Usuario: DEMO","Datos Invalidos",0); user = "DEMO";}
		}else{ user = "DEMO"; }
		chat programa = new chat(user);
		desktop.add(programa);
		Dimension desktopSize = this.getSize();
		Dimension jIFrameSize = programa.getSize();
		programa.setLocation(((desktopSize.width - jIFrameSize.width) / 2), ((desktopSize.height - jIFrameSize.height) / 2));
		programa.setVisible(true);
	}
	/*metodos para la calculadora*/
	public void programa2(String ejercicio){
		calculadora programa = new calculadora(new String[]{ejercicio});
		desktop.add(programa);
		Dimension desktopSize = this.getSize();
		Dimension jIFrameSize = programa.getSize();
		programa.setLocation(((desktopSize.width - jIFrameSize.width) / 2), ((desktopSize.height - jIFrameSize.height) / 2));
		programa.setVisible(true);
	}
	/*metodos para el solitario*/
	public void programa3(){
		solitario programa = new solitario();
		desktop.add(programa);
		Dimension desktopSize = this.getSize();
		Dimension jIFrameSize = programa.getSize();
		programa.setLocation(((desktopSize.width - jIFrameSize.width) / 2), ((desktopSize.height - jIFrameSize.height) / 2));
		programa.setVisible(true);
	}
	/*metodos para la consola*/
	public void programa4(){
		consola programa = new consola(ruta_local);
		desktop.add(programa);
		Dimension desktopSize = this.getSize();
		Dimension jIFrameSize = programa.getSize();
		programa.setLocation(((desktopSize.width - jIFrameSize.width) / 2), ((desktopSize.height - jIFrameSize.height) / 2));
		programa.setVisible(true);
	}
	/*metodos para el navegador web*/
	public void programa5(){
		navegador programa = new navegador("");
		desktop.add(programa);
		Dimension desktopSize = this.getSize();
		Dimension jIFrameSize = programa.getSize();
		programa.setLocation(((desktopSize.width - jIFrameSize.width) / 2), ((desktopSize.height - jIFrameSize.height) / 2));
		programa.setVisible(true);
	}
	/*metodos para la captura de pantalla*/
	public void programa6(){
		int confirmacion = JOptionPane.showConfirmDialog(null,"¿Desea hacer captura de pantalla?","Pantalla",2);
		if(confirmacion == 0){
			Dimension desktopSize = this.getSize();
			Point position = this.getLocationOnScreen();
			pantalla programa = new pantalla();
			programa.ruta = ruta_local;
			programa.dimension = desktopSize;
			programa.posicion = position;
			programa.start();
		}
	}
	/*metodos para el editor de texto*/
	public void programa7(){
		texto programa = new texto(ruta_local);
		desktop.add(programa);
		Dimension desktopSize = this.getSize();
		Dimension jIFrameSize = programa.getSize();
		programa.setLocation(((desktopSize.width - jIFrameSize.width) / 2), ((desktopSize.height - jIFrameSize.height) / 2));
		programa.setVisible(true);
	}
	/*metodos para el visor de imagen*/
	public void programa8(){
		imagen programa = new imagen(ruta_local);
		desktop.add(programa);
		Dimension desktopSize = this.getSize();
		Dimension jIFrameSize = programa.getSize();
		programa.setLocation(((desktopSize.width - jIFrameSize.width) / 2), ((desktopSize.height - jIFrameSize.height) / 2));
		programa.setVisible(true);
	}
	/*metodos para el gestor de archivos*/
	public void programa9(){
		archivo programa = new archivo();
		programa.ventana(ruta_local);
		desktop.add(programa.formulario);
		Dimension desktopSize = this.getSize();
		Dimension jIFrameSize = programa.formulario.getSize();
		programa.formulario.setLocation(((desktopSize.width - jIFrameSize.width) / 2), ((desktopSize.height - jIFrameSize.height) / 2));
		programa.formulario.setVisible(true);
	}
	/*metodos para el reproductor de musica*/
	public void programa10(){
		musica programa = new musica(ruta_local);
		desktop.add(programa);
		Dimension desktopSize = this.getSize();
		Dimension jIFrameSize = programa.getSize();
		programa.setLocation(((desktopSize.width - jIFrameSize.width) / 2), ((desktopSize.height - jIFrameSize.height) / 2));
		programa.setVisible(true);
	}
	/*metodos para el servidor*/
	public void programa11(){
		servidor programa = new servidor((ruta_local)+"web"+(separador));
		desktop.add(programa);
		Dimension desktopSize = this.getSize();
		Dimension jIFrameSize = programa.getSize();
		programa.setLocation(((desktopSize.width - jIFrameSize.width) / 2), ((desktopSize.height - jIFrameSize.height) / 2));
		programa.setVisible(true);
	}
	/*metodos para el editor de html*/
	public void programa12(){
		html programa = new html((ruta_local)+"web"+(separador));
		desktop.add(programa);
		Dimension desktopSize = this.getSize();
		Dimension jIFrameSize = programa.getSize();
		programa.setLocation(((desktopSize.width - jIFrameSize.width) / 2), ((desktopSize.height - jIFrameSize.height) / 2));
		programa.setVisible(true);
	}
	/*metodos para el reproductor de video*/
	public void programa13(){
		video programa = new video(ruta_local);
		desktop.add(programa);
		Dimension desktopSize = this.getSize();
		Dimension jIFrameSize = programa.getSize();
		programa.setLocation(((desktopSize.width - jIFrameSize.width) / 2), ((desktopSize.height - jIFrameSize.height) / 2));
		programa.setVisible(true);
	}
	/*metodo principal*/
	public static void main(String[] args){
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		escritorio formulario = new escritorio();
		formulario.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		formulario.setSize(screenSize.width  - 50*2, screenSize.height - 50*2);
		formulario.setLocationRelativeTo(null);
		formulario.setResizable(true);
		formulario.setVisible(true);
	}
}
