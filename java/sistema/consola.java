package sistema;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.text.*;
import javax.swing.event.*;
import sistema.programas.*;
public class consola extends JInternalFrame implements KeyListener, CaretListener{
	/*declaramos los objetos*/
	public String separador;
	public String ruta;
	private static final String peticion = ">";
	private JScrollPane escrol;
	private JTextArea area_de_trabajo;
	private int puntero;
	public String args;
	/*creamos el formulario*/
	public consola(String args){
		super("Consola", true, true, true, true);
		this.args=args;
		setSize(450, 300);
		setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		setResizable(true);
		/*creamos el area de trabajo*/
		area_de_trabajo = new JTextArea();
		area_de_trabajo.setBackground(new Color(228, 228, 251));
		area_de_trabajo.setText(peticion);
		area_de_trabajo.setLineWrap(true); //salto de linea al llegar al tope vertical
		area_de_trabajo.setWrapStyleWord(false);
		/*inicializamos la linea donde empieza la captacion de datos*/
		puntero = area_de_trabajo.getText().length();
		area_de_trabajo.setCaretPosition(puntero);
		/*agregamos eventos al area de trabajo*/
		area_de_trabajo.addCaretListener(this);
		area_de_trabajo.addKeyListener(this);
		/*agregamos scroll vertical al area de trabajo*/
		escrol = new JScrollPane(area_de_trabajo);
		escrol.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		escrol.setBorder(null);
		/*agregamos el objeto al formulario*/
		add(escrol, BorderLayout.CENTER);
		/*inicializamos la ruta de trabajo*/
		if((System.getProperty("os.name").substring(0, 3)).equals("Win")){separador = File.separator + File.separator;}
		else{separador = File.separator;}
		ruta = args;
	}
	/*metodo de tecla digitada*/
	public void keyTyped(KeyEvent e){ }
	/*metodo de tecla finalizacion de presionar*/
	public void keyReleased(KeyEvent e){ }
	/*metodo de tecla presionada*/
	public void keyPressed(KeyEvent e){
		switch(e.getKeyCode()){
			case KeyEvent.VK_ENTER:
				String comando = area_de_trabajo.getText().substring(puntero);
				/*validamos hayan comandos*/
				if(!comando.isEmpty()){
					try{
						/*comando para mostrar la ayuda de la consola*/
						if(comando.equals("ayuda")){
							area_de_trabajo.append(System.lineSeparator() + "\ncalculadora \t\tabre la calculadora");
							area_de_trabajo.append(System.lineSeparator() + "chat \t\tabre el programa para chatear");
							area_de_trabajo.append(System.lineSeparator() + "consola \t\tabre una segunda consola");
							area_de_trabajo.append(System.lineSeparator() + "imagen \t\tabre el programa para ver imagenes");
							area_de_trabajo.append(System.lineSeparator() + "navegador \t\tabre el navegador de internet");
							area_de_trabajo.append(System.lineSeparator() + "texto \t\tabre el editor de texto");
							area_de_trabajo.append(System.lineSeparator() + "musica \t\tabre el reproductor de musica");
							area_de_trabajo.append(System.lineSeparator() + "video \t\tabre el reproductor de video");
							area_de_trabajo.append(System.lineSeparator() + "\nruta \t\tmuestra la ruta actual de trabajo");
							area_de_trabajo.append(System.lineSeparator() + "mostrar \t\tmuestra los archivos en la ruta actual");
							area_de_trabajo.append(System.lineSeparator() + "crear carpeta\t\tcrea una carpeta en la ruta actual");
							area_de_trabajo.append(System.lineSeparator() + "borrar a.txt \t\tborra una carpeta o archivo");
							area_de_trabajo.append(System.lineSeparator() + "abrir .. \t\tnos desplaza una carpeta atras");
							area_de_trabajo.append(System.lineSeparator() + "abrir carpeta \t\tnos desplaza a una carpeta");
							area_de_trabajo.append(System.lineSeparator() + "copiar a.txt .."+separador+"a.txt \tcopea un archivo");
							area_de_trabajo.append(System.lineSeparator() + "mover a.txt .."+separador+"a.txt \tmueve un archivo");
							area_de_trabajo.append(System.lineSeparator());
						}
						/*comando para mostrar la ruta*/
						else if(comando.equals("ruta")){
							area_de_trabajo.append(System.lineSeparator() + ruta + System.lineSeparator());
						}
						/*comando para mostrar los archivo en la ruta actual*/
						else if(comando.equals("mostrar")){
							File dir = new File(ruta);
							String[] ficheros = dir.list();
							if(ficheros == null){area_de_trabajo.append(System.lineSeparator()+"No hay archivos"+System.lineSeparator());}
							else{for(int x=0;x<ficheros.length;x++){area_de_trabajo.append(System.lineSeparator()+ficheros[x]);}}
							area_de_trabajo.append(System.lineSeparator());
						}
						/*comando para regresar un directorio atras*/
						else if(comando.equals("abrir ..")){
							String[] directorios = ruta.split(separador);
							String nueva_ruta = "";
							for(int x=0;x<(directorios.length-1);x++){ nueva_ruta = nueva_ruta + directorios[x] + File.separator; }
							area_de_trabajo.append(System.lineSeparator() + nueva_ruta + System.lineSeparator());
							ruta = nueva_ruta;
						}
						/*comando que abre un programa*/
						else if(comando.equals("consola")){
							JDesktopPane desktop = getDesktopPane();
							consola programa = new consola(args);
							desktop.add(programa);
							Dimension desktopSize = desktop.getSize();
							Dimension jIFrameSize = programa.getSize();
							programa.setLocation(((desktopSize.width - jIFrameSize.width) / 2), ((desktopSize.height - jIFrameSize.height) / 2));
							programa.setVisible(true);
						}
						/*comando que abre un programa*/
						else if(comando.equals("chat")){
							JDesktopPane desktop = getDesktopPane();
							chat programa = new chat("DEMO");
							desktop.add(programa);
							Dimension desktopSize = desktop.getSize();
							Dimension jIFrameSize = programa.getSize();
							programa.setLocation(((desktopSize.width - jIFrameSize.width) / 2), ((desktopSize.height - jIFrameSize.height) / 2));
							programa.setVisible(true);
						}
						/*comando que abre un programa*/
						else if(comando.equals("navegador")){
							JDesktopPane desktop = getDesktopPane();
							navegador programa = new navegador("");
							desktop.add(programa);
							Dimension desktopSize = desktop.getSize();
							Dimension jIFrameSize = programa.getSize();
							programa.setLocation(((desktopSize.width - jIFrameSize.width) / 2), ((desktopSize.height - jIFrameSize.height) / 2));
							programa.setVisible(true);
						}
						/*comando que abre un programa*/
						else if(comando.equals("imagen")){
							JDesktopPane desktop = getDesktopPane();
							imagen programa = new imagen(ruta); 
							desktop.add(programa);
							Dimension desktopSize = desktop.getSize();
							Dimension jIFrameSize = programa.getSize();
							programa.setLocation(((desktopSize.width - jIFrameSize.width) / 2), ((desktopSize.height - jIFrameSize.height) / 2));
							programa.setVisible(true);
						}
						/*comando que abre un programa*/
						else if(comando.equals("texto")){
							JDesktopPane desktop = getDesktopPane();
							texto programa = new texto(ruta); 
							desktop.add(programa);
							Dimension desktopSize = desktop.getSize();
							Dimension jIFrameSize = programa.getSize();
							programa.setLocation(((desktopSize.width - jIFrameSize.width) / 2), ((desktopSize.height - jIFrameSize.height) / 2));
							programa.setVisible(true);
						}
						/*comando que abre un programa*/
						else if(comando.equals("musica")){
							JDesktopPane desktop = getDesktopPane();
							musica programa = new musica(ruta); 
							desktop.add(programa);
							Dimension desktopSize = desktop.getSize();
							Dimension jIFrameSize = programa.getSize();
							programa.setLocation(((desktopSize.width - jIFrameSize.width) / 2), ((desktopSize.height - jIFrameSize.height) / 2));
							programa.setVisible(true);
						}
						/*comando que abre un programa*/
						else if(comando.equals("video")){
							JDesktopPane desktop = getDesktopPane();
							video programa = new video(ruta); 
							desktop.add(programa);
							Dimension desktopSize = desktop.getSize();
							Dimension jIFrameSize = programa.getSize();
							programa.setLocation(((desktopSize.width - jIFrameSize.width) / 2), ((desktopSize.height - jIFrameSize.height) / 2));
							programa.setVisible(true);
						}
						/*comando para abrir o crear un archivo de texto*/
						else if((comando.substring(0, 5)).equals("texto")){
							String nueva_ruta = ruta + comando.substring(6, comando.length());
							JDesktopPane desktop = getDesktopPane();
							texto programa = new texto(nueva_ruta);
							desktop.add(programa);
							Dimension desktopSize = desktop.getSize();
							Dimension jIFrameSize = programa.getSize();
							programa.setLocation(((desktopSize.width - jIFrameSize.width) / 2), ((desktopSize.height - jIFrameSize.height) / 2));
							programa.setVisible(true);
						}
						/*comando para abrir una carpeta, ruta relativa*/
						else if((comando.substring(0, 5)).equals("abrir")){
							String[] directorios = ruta.split(separador);
							String nueva_ruta = ruta + comando.substring(6, comando.length()) + separador;
							File dir = new File(nueva_ruta);
							if(!dir.exists()){area_de_trabajo.append(System.lineSeparator()+"La ruta no existe: "+nueva_ruta+System.lineSeparator());}
							else{area_de_trabajo.append(System.lineSeparator() + nueva_ruta + System.lineSeparator());ruta = nueva_ruta;}
						}
						/*comando para crear un directorio, ruta relativa*/
						else if((comando.substring(0, 5)).equals("crear")){
							String[] rutas = comando.split(" ");
							try{
								File dir = new File(ruta+rutas[1]);
								if(!dir.exists()){dir.mkdir();area_de_trabajo.append(System.lineSeparator() +"Creacion exitosa: " +ruta+rutas[1]+ System.lineSeparator());}
								else{area_de_trabajo.append(System.lineSeparator()+"La ruta ya existe"+System.lineSeparator());}
							}catch(Exception ex){area_de_trabajo.append(System.lineSeparator() + "Imposible de borrar: " + ruta+rutas[1]+ System.lineSeparator());}
						}
						/*comando para mover archivo, ruta relativa*/
						else if((comando.substring(0, 5)).equals("mover")){
							try{
								String[] rutas = comando.split(" ");
								File file1 = new File(ruta+rutas[1]);
								File file2 = new File(ruta+rutas[2]);
								if(!file1.exists()){area_de_trabajo.append(System.lineSeparator()+"La ruta no existe: "+ruta+rutas[1]+System.lineSeparator());}
								else{String resultado = copiar(file1, file2);file1.delete();area_de_trabajo.append(System.lineSeparator() + resultado + System.lineSeparator());}
							}catch(Exception ex){area_de_trabajo.append(System.lineSeparator()+"Comando icompleto: "+comando+System.lineSeparator());}
						}
						/*comando para mover archivo, ruta relativa*/
						else if((comando.substring(0, 5)).equals("video")){
							String nueva_ruta = ruta + comando.substring(7, comando.length());
							JDesktopPane desktop = getDesktopPane();
							video programa = new video(nueva_ruta);
							desktop.add(programa);
							Dimension desktopSize = desktop.getSize();
							Dimension jIFrameSize = programa.getSize();
							programa.setLocation(((desktopSize.width - jIFrameSize.width) / 2), ((desktopSize.height - jIFrameSize.height) / 2));
							programa.setVisible(true);
						}
						
						/*comando para borrar un archivo o directorio, ruta relativa*/
						else if((comando.substring(0, 6)).equals("borrar")){
							String[] rutas = comando.split(" ");
							try{
								File file_dir = new File(ruta+rutas[1]);
								if(!file_dir.exists()){area_de_trabajo.append(System.lineSeparator()+"La ruta no existe: "+ruta+rutas[1]+System.lineSeparator());}
								else{file_dir.delete();area_de_trabajo.append(System.lineSeparator()+"Elminacion exitosa" + System.lineSeparator());}
							}catch(Exception ex){area_de_trabajo.append(System.lineSeparator() + "Imposible de borrar: " + ruta+rutas[1]+ System.lineSeparator());}
						}
						/*comando para copiar un archivo, ruta relativa*/
						else if((comando.substring(0, 6)).equals("copiar")){
							try{
								String[] rutas = comando.split(" ");
								File file1 = new File(ruta+rutas[1]);
								File file2 = new File(ruta+rutas[2]);
								if(!file1.exists()){area_de_trabajo.append(System.lineSeparator()+"La ruta no existe: "+ruta+rutas[1]+System.lineSeparator());}
								else{String resultado = copiar(file1, file2);area_de_trabajo.append(System.lineSeparator() + resultado + System.lineSeparator());}
							}catch(Exception ex){area_de_trabajo.append(System.lineSeparator()+"Comando icompleto: "+comando+System.lineSeparator());}
						}
						/*comando para abrir un archivo de musica*/
						else if((comando.substring(0, 6)).equals("musica")){
							String nueva_ruta = ruta + comando.substring(7, comando.length());
							JDesktopPane desktop = getDesktopPane();
							musica programa = new musica(nueva_ruta);
							desktop.add(programa);
							Dimension desktopSize = desktop.getSize();
							Dimension jIFrameSize = programa.getSize();
							programa.setLocation(((desktopSize.width - jIFrameSize.width) / 2), ((desktopSize.height - jIFrameSize.height) / 2));
							programa.setVisible(true);
						}
						/*comando para abrir una imagen o ruta relativa de imagenes*/
						else if((comando.substring(0, 6)).equals("imagen")){
							String[] rutas = comando.split(" ");
							File file_dir = new File(ruta+rutas[1]);
							if(!file_dir.exists()){area_de_trabajo.append(System.lineSeparator()+"La ruta no existe: "+ruta+rutas[1]+System.lineSeparator());}
							else{
								JDesktopPane desktop = getDesktopPane();
								imagen programa = new imagen(ruta+rutas[1]); 
								desktop.add(programa);
								Dimension desktopSize = desktop.getSize();
								Dimension jIFrameSize = programa.getSize();
								programa.setLocation(((desktopSize.width - jIFrameSize.width) / 2), ((desktopSize.height - jIFrameSize.height) / 2));
								programa.setVisible(true);
							}
						}
						/*comando que abre un programa*/
						else if((comando.substring(0, 9)).equals("navegador")){
							String[] url = comando.split(" ");
							JDesktopPane desktop = getDesktopPane();
							navegador programa = new navegador(url[1]);
							desktop.add(programa);
							Dimension desktopSize = desktop.getSize();
							Dimension jIFrameSize = programa.getSize();
							programa.setLocation(((desktopSize.width - jIFrameSize.width) / 2), ((desktopSize.height - jIFrameSize.height) / 2));
							programa.setVisible(true);
						}
						/*comando que abre un programa*/
						else if((comando.substring(0, 11)).equals("calculadora")){
							JDesktopPane desktop = getDesktopPane();
							calculadora programa = new calculadora(new String[]{ comando.substring(11,comando.length())});
							desktop.add(programa);
							Dimension desktopSize = desktop.getSize();
							Dimension jIFrameSize = programa.getSize();
							programa.setLocation(((desktopSize.width - jIFrameSize.width) / 2), ((desktopSize.height - jIFrameSize.height) / 2));
							programa.setVisible(true);
						}
						/*en caso de no introducir comandos validos*/
						else{
							area_de_trabajo.append(System.lineSeparator() + "Comando no existe: " + comando + System.lineSeparator());
						}
					}catch(Exception ex){area_de_trabajo.append(System.lineSeparator()+"Ejecucion Invalida: "+comando+System.lineSeparator());}
				}
				/*actualizamos la linea donde empieza la captacion de datos*/
				area_de_trabajo.append(System.lineSeparator() + peticion);
				puntero = area_de_trabajo.getText().length();
				/*al presionar enter se ejecuta un salto de linea y el evento*/
				/*para rechazar el salto de lines ejecutamos e.consume() */
				e.consume();
			break;
			case KeyEvent.VK_BACK_SPACE:
				if(area_de_trabajo.getCaretPosition() <= puntero){
					e.consume();
					Toolkit.getDefaultToolkit().beep();
				}
			break;
			default:
				//evento de otras teclas
			break;
		}
	}
	/*metodo que se ejecuta al actulizar la posicion del puntero en la textarea*/
	public void caretUpdate(CaretEvent e){
		if(e.getDot() < puntero){
			area_de_trabajo.setCaretPosition(puntero);
			Toolkit.getDefaultToolkit().beep();
		}
	}
	/*metodo para la transferencia de archivos*/
	private String copiar(File archivo1, File archivo2){
		try{
			InputStream in = new FileInputStream(archivo1); 
			OutputStream out = new FileOutputStream(archivo2); 
			byte[] buffer = new byte[1024];
			int len;
			while((len = in.read(buffer)) > 0){out.write(buffer, 0, len);}
			out.flush();
			in.close();
			out.close();
			return "Transferencia de archivo exitosa";
		}catch(Exception e){return "Error al tranferir puede que no este definido el destino";}
	}
}
