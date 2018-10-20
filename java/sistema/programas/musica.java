package sistema.programas;
import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.sound.sampled.*;
import javax.swing.filechooser.*;
import java.util.concurrent.TimeUnit;
public class musica extends JInternalFrame implements ActionListener, ChangeListener, InternalFrameListener{
	/*declaramos los objetos de la interfaz y variables globales*/
	private JButton reproducir, pausar, detener, adelantar, retroceder;
	private JPanel panel_de_botones;
	private JSlider volumen;
	private JProgressBar barra_de_progreso;
	private FloatControl control;
	private Clip clip;
	private Thread proceso_paralelo;
	private String[] archivos;
	private int posicion = 0;
	private long microsegundos = 0;
	private float decibeles_volumen = -30.0f;
	private String directorio;
	/*metodo de la interfaz grafica*/
	public musica(String args){
		super("Reproductor de Musica", true, true, false, true);
		setSize(480,100);
		setDefaultCloseOperation(JInternalFrame.DO_NOTHING_ON_CLOSE); 
		setResizable(false);
		/*panel de botones*/
		panel_de_botones = new JPanel();
		reproducir = new JButton(">");		pausar = new JButton("||");		detener = new JButton("#");		retroceder = new JButton("|<");		adelantar = new JButton(">|");
		volumen = new JSlider(-60, 6, (int)decibeles_volumen);
		reproducir.addActionListener(this);
		pausar.addActionListener(this);
		detener.addActionListener(this);
		retroceder.addActionListener(this);
		adelantar.addActionListener(this);
		volumen.addChangeListener(this);
		panel_de_botones.add(reproducir); 
		panel_de_botones.add(pausar); 
		panel_de_botones.add(detener); 
		panel_de_botones.add(retroceder); 
		panel_de_botones.add(adelantar); 
		panel_de_botones.add(volumen); 
		/*reproductor de musica*/
		barra_de_progreso = new JProgressBar(0,100);
		barra_de_progreso.setStringPainted(true);
		/*posicionamiento de objetos*/
		GridLayout layout = new GridLayout(2, 2, 0, 0);
		setLayout(layout);
		add(panel_de_botones);
		add(barra_de_progreso);
		/*listar*/
		String ruta = args;
		File archivo = new File(ruta);
		//ruta de directorio
		if(archivo.isDirectory()){
			directorio = ruta;
			String[] lista = archivo.list();
			ArrayList<String> wav = new ArrayList<String>();
			for(int x=0;x<(lista.length);x++){if(lista[x].matches(".*\\.(wav|WAV)")){wav.add(lista[x]);}}
			archivos = wav.toArray(new String[wav.size()]);
			reproductor(new File(directorio+File.separator+archivos[0]));
			posicion = 0;
		}
		//ruta de archivo
		else{
			String nombre = archivo.getName();
			directorio = archivo.getParent();
			archivo = new File(directorio);
			String[] lista = archivo.list();
			ArrayList<String> wav = new ArrayList<String>();
			for(int x=0;x<(lista.length);x++){if(lista[x].matches(".*\\.(wav|WAV)")){wav.add(lista[x]);}}
			archivos = wav.toArray(new String[wav.size()]);
			for(int x=0;x<(archivos.length);x++){if(archivos[x].equals(nombre)){posicion = x;}}
			reproductor(new File(directorio+File.separator+archivos[posicion]));
		}
		reproducir.setEnabled(false);
		decibeles_volumen = -30;
		/*eventos para este formulario*/
		addInternalFrameListener(this);
	}
	
	/*metodos para reproducir*/
	public void reproductor(File audio){
		proceso_paralelo = new Thread("Hilo de procesos"){
			public void run(){
				try{
					/*reproductor*/
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(audio);
					clip = AudioSystem.getClip();
					clip.open(inputStream);
					barra_de_progreso.setMaximum((int) TimeUnit.MICROSECONDS.toSeconds(clip.getMicrosecondLength()) );
					/*controlador del volumen*/
					control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
					float max = control.getMaximum();
					float min = control.getMinimum();
					volumen.setMaximum((int)max);
					volumen.setMinimum((int)-60);
					/*volumen por default*/
					volumen.setValue((int)decibeles_volumen);
					control.setValue(volumen.getValue());
					/*posicion de reproduccion si fue pausado*/
					clip.setMicrosecondPosition(microsegundos);
					clip.start();
					/*barra de progreso con contador de tiempo*/
					while(clip.getMicrosecondLength() != clip.getMicrosecondPosition()){
						Thread.sleep(200);
						barra_de_progreso.setString(String.format("%d:%d:%d",TimeUnit.MICROSECONDS.toHours(clip.getMicrosecondPosition()) /*- TimeUnit.DAYS.toHours(TimeUnit.MICROSECONDS.toDays(difference))*/ ,TimeUnit.MICROSECONDS.toMinutes(clip.getMicrosecondPosition()) - TimeUnit.HOURS.toMinutes(TimeUnit.MICROSECONDS.toHours(clip.getMicrosecondPosition())), TimeUnit.MICROSECONDS.toSeconds(clip.getMicrosecondPosition()) - TimeUnit.MINUTES.toSeconds(TimeUnit.MICROSECONDS.toMinutes(clip.getMicrosecondPosition()))));
						barra_de_progreso.setValue((int) TimeUnit.MICROSECONDS.toSeconds(clip.getMicrosecondPosition()));
					}
					/*fin del primer sound track*/
					clip.stop();
					clip.close();
					barra_de_progreso.setValue(0);
					barra_de_progreso.setString(String.format("0:0:0"));
					/*auto reproduccion de la siguiente melodia*/
					if(posicion==(archivos.length-1)){posicion = 0;}
					else{posicion = posicion + 1;}
					reproductor(new File(directorio+File.separator+archivos[posicion]));
				}catch(Exception e){JOptionPane.showMessageDialog(null,"No se pudo reproducir","ERROR",0);}
			}
		};
		proceso_paralelo.start();
	}
	/*evento de accionamiento del slider*/
	public void stateChanged(ChangeEvent ce){
		if(ce.getSource()==volumen){
			decibeles_volumen = ((int)volumen.getValue());
			control.setValue(volumen.getValue());
		}
	}
	/*evento de accionamiento del boton*/
	public void actionPerformed(ActionEvent ae){
		if(ae.getSource()==retroceder){
			reproducir.setEnabled(false);
			pausar.setEnabled(true);
			detener.setEnabled(true);
			clip.stop();
			clip.close();
			barra_de_progreso.setValue(0);
			barra_de_progreso.setString(String.format("0:0:0"));
			proceso_paralelo.stop();
			/*algoritmo de playlist ciclica de la carpeta*/
			microsegundos = 0;
			if(posicion==0){posicion = archivos.length - 1;}
			else{posicion = posicion - 1;}
			reproductor(new File(directorio+File.separator+archivos[posicion]));
		}
		if(ae.getSource()==adelantar){
			reproducir.setEnabled(false);
			pausar.setEnabled(true);
			detener.setEnabled(true);
			clip.stop();
			clip.close();
			barra_de_progreso.setValue(0);
			barra_de_progreso.setString(String.format("0:0:0"));
			proceso_paralelo.stop();
			/*algoritmo de playlist ciclica de la carpeta*/
			microsegundos = 0;
			if(posicion==(archivos.length-1)){posicion = 0;}
			else{posicion = posicion + 1;}
			reproductor(new File(directorio+File.separator+archivos[posicion]));
		}
		if(ae.getSource()==pausar){
			/*interfaz grafica de botones*/
			reproducir.setEnabled(true);
			pausar.setEnabled(false);
			detener.setEnabled(true);
			/*reproductor de musica*/
			microsegundos = clip.getMicrosecondPosition();
			clip.stop();
			clip.close();
			proceso_paralelo.stop();
			barra_de_progreso.setValue((int) TimeUnit.MICROSECONDS.toSeconds(microsegundos));
		}
		if(ae.getSource()==reproducir){
			/*interfaz grafica de botones*/
			reproducir.setEnabled(false);
			pausar.setEnabled(true);
			detener.setEnabled(true);
			/*reproductor de musica*/
			reproductor(new File(directorio+File.separator+archivos[posicion]));
		}
		if(ae.getSource()==detener){
			/*reproductor de musica*/
			microsegundos = 0;
			barra_de_progreso.setValue(0);
			barra_de_progreso.setString(String.format("0:0:0"));
			clip.stop();
			clip.close();
			proceso_paralelo.stop();
			/*interfaz grafica de botones*/
			reproducir.setEnabled(true);
			pausar.setEnabled(false);
			detener.setEnabled(false);
		}
	}
	/*eventos del formulario*/
	public void internalFrameOpened(InternalFrameEvent event){}
	public void internalFrameClosing(InternalFrameEvent event){
		clip.stop();
		clip.close();
		proceso_paralelo.stop();
		dispose();
	}
	public void internalFrameClosed(InternalFrameEvent event){}
	public void internalFrameIconified(InternalFrameEvent event){}
	public void internalFrameDeiconified(InternalFrameEvent event){}
	public void internalFrameActivated(InternalFrameEvent event){}
	public void internalFrameDeactivated(InternalFrameEvent event){}
}
