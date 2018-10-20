package sistema.programas;
import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.filechooser.*;
public class imagen extends JInternalFrame implements KeyListener, ActionListener{
	private JButton adelante, atras;
	private JLabel visor;
	private JScrollPane scroll;
	private JPanel panel_de_botones;
	public String[] archivos;
	public int posicion = 0;
	public String directorio;
	public imagen(String args){
		super("Visor de Imagen", true, true, true, true);
		setSize(420, 480);
		setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE); 
		/*visor de imagen*/
		visor = new JLabel(null, null, JLabel.CENTER);
		visor.setBackground(Color.black);
		visor.setOpaque(true);
		scroll = new JScrollPane(visor);
		/*panel de botones*/
		panel_de_botones = new JPanel();
		atras = new JButton("<");
		adelante = new JButton(">");
		atras.addActionListener(this);
		adelante.addActionListener(this);
		panel_de_botones.add(atras); 
		panel_de_botones.add(adelante);
		/*posicionamiento de objetos*/
		BorderLayout layout = new BorderLayout(0, 0);
		setLayout(layout);
		add(scroll, BorderLayout.CENTER);
		add(panel_de_botones, BorderLayout.SOUTH);
		/*listar*/
		String ruta = args;
		File archivo = new File(ruta);
		//ruta de directorio
		if(archivo.isDirectory()){
			directorio = ruta;
			String[] lista = archivo.list();
			ArrayList<String> png = new ArrayList<String>();
			for(int x=0;x<(lista.length);x++){if(lista[x].matches(".*\\.(png|PNG|jpg|JPG|gif|GIF)")){png.add(lista[x]);}}
			archivos = png.toArray(new String[png.size()]);
			visor.setIcon(new ImageIcon(directorio+File.separator+archivos[0]));
			posicion = 0;
		}
		//ruta de archivo
		else{
			String nombre = archivo.getName();
			directorio = archivo.getParent();
			archivo = new File(directorio);
			String[] lista = archivo.list();
			ArrayList<String> png = new ArrayList<String>();
			for(int x=0;x<(lista.length);x++){if(lista[x].matches(".*\\.(png|PNG|jpg|JPG|gif|GIF)")){png.add(lista[x]);}}
			archivos = png.toArray(new String[png.size()]);
			for(int x=0;x<(archivos.length);x++){if(archivos[x].equals(nombre)){posicion = x;}}
			visor.setIcon(new ImageIcon(directorio+File.separator+archivos[posicion]));
		}
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
	}
	/*evento de teclado*/
	public void keyTyped(KeyEvent e){}
	public void keyReleased(KeyEvent e){}
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode()==KeyEvent.VK_LEFT){
			if(posicion==0){posicion = archivos.length - 1;}
			else{posicion = posicion - 1;}
			visor.setIcon(new ImageIcon(directorio+File.separator+archivos[posicion]));
		}
		if(e.getKeyCode()==KeyEvent.VK_RIGHT){
			if(posicion==(archivos.length-1)){posicion = 0;}
			else{posicion = posicion + 1;}
			visor.setIcon(new ImageIcon(directorio+File.separator+archivos[posicion]));
		}
	}
	/*evento de accionamiento del boton*/
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==atras){
			if(posicion==0){posicion = archivos.length - 1;}
			else{posicion = posicion - 1;}
			visor.setIcon(new ImageIcon(directorio+File.separator+archivos[posicion]));
		}
		if(ae.getSource()==adelante){
			if(posicion==(archivos.length-1)){posicion = 0;}
			else{posicion = posicion + 1;}
			visor.setIcon(new ImageIcon(directorio+File.separator+archivos[posicion]));
		}
	}
}
