package sistema.programas;
import java.io.*;
import java.util.*;
import javax.swing.*; 
import javafx.scene.*;
import javafx.application.*;
import javafx.scene.layout.*;
import javafx.embed.swing.JFXPanel; 
import javafx.scene.input.*;
import javafx.scene.image.*;
import javafx.scene.media.*;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.beans.binding.Bindings;
import javax.swing.filechooser.*;
import javafx.util.Duration;
import javax.swing.event.*;
public class video extends JInternalFrame implements InternalFrameListener{ 
	/*declaramos los objetos*/
	public JFXPanel contedorfx;
	public Scene scene;
	public Media multimedia;
	public MediaPlayer reproductor;
	public MediaView visor;
	public ImageView icono;
	public Image imagen;
	public double volumen;
	public boolean reproduciendo;
	public String[] archivos;
	public int posicion = 0;
	public String directorio;
	/*creamos el formulario*/
	public video(String args){
		super("Reproductor de Video", true, true, true, true);
		setSize(600, 365);
		setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE); 
		/*listar*/
		String ruta = args;
		File archivo = new File(ruta);
		//ruta de directorio
		if(archivo.isDirectory()){
			directorio = ruta;
			String[] lista = archivo.list();
			ArrayList<String> mp4 = new ArrayList<String>();
			for(int x=0;x<(lista.length);x++){if(lista[x].matches(".*\\.(mp4|MP4)")){mp4.add(directorio+File.separator+lista[x]);}}
			archivos = mp4.toArray(new String[mp4.size()]);
			posicion = 0;
		}
		//ruta de archivo
		else{
			String nombre = archivo.getName();
			directorio = archivo.getParent();
			archivo = new File(directorio);
			String[] lista = archivo.list();
			ArrayList<String> mp4 = new ArrayList<String>();
			for(int x=0;x<(lista.length);x++){if(lista[x].matches(".*\\.(mp4|MP4)")){mp4.add(directorio+File.separator+lista[x]);}}
			archivos = mp4.toArray(new String[mp4.size()]);
			for(int x=0;x<(archivos.length);x++){if(archivos[x].equals(directorio+File.separator+nombre)){posicion = x;}}
		}
		/*creamos el contenedor de javafx para integrarlo en swing*/
		contedorfx = new JFXPanel(); 
		Platform.setImplicitExit(false); 
		Platform.runLater(() -> crearfx(contedorfx)); 
		getContentPane().add(contedorfx); 
		addInternalFrameListener(this);
	}
	/*creamos los objetos javafx*/
	private void crearfx(JFXPanel contedorfx){ 
		/*construir reproductor*/
		multimedia = new Media((new File(archivos[posicion])).toURI().toString());
		reproductor = new MediaPlayer(multimedia);
		reproductor.setAutoPlay(true);
		/*evento al terminar de reproducir un video*/
		reproductor.setOnEndOfMedia(new Runnable(){
		    @Override 
            		public void run(){
                		reproductor.stop();
                		autoreproducir();
            		}
        	});
		visor = new MediaView(reproductor);
		/*redimensionado automatico del video*/
		(visor.fitWidthProperty()).bind(Bindings.selectDouble(visor.sceneProperty(), "width"));
		(visor.fitHeightProperty()).bind(Bindings.selectDouble(visor.sceneProperty(), "height"));
		visor.setPreserveRatio(true);
		/*crear escenario interno del formulario*/
		StackPane root = new StackPane();
		root.getChildren().add(visor);
		scene = new Scene(root);
		scene.setFill(Color.BLACK);
		icono = new ImageView();
		/*evento del teclado*/
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            		@Override
            		public void handle(KeyEvent event){
				/*adelantar y reproduccion secuencial*/
				if(event.getCode() == KeyCode.RIGHT){
					if(reproduciendo==false){root.getChildren().remove(icono);}
					if(posicion==(archivos.length-1)){posicion = 0;}
					else{posicion = posicion + 1;}
					reproductor.stop();
					multimedia = new Media((new File(archivos[posicion])).toURI().toString());
					reproductor = new MediaPlayer(multimedia);
					reproductor.setAutoPlay(true);	
					reproductor.setOnEndOfMedia(new Runnable(){
					@Override 
						public void run(){
							reproductor.stop();
							autoreproducir();
						}
					});
        				visor.setMediaPlayer(reproductor);
					reproduciendo = true;
					reproductor.setVolume(volumen);
					reproductor.play();
				}
				/*atrazar y reproduccion secuencial*/
				else if(event.getCode() == KeyCode.LEFT){
					if(reproduciendo==false){root.getChildren().remove(icono);}
					if(posicion==0){posicion = archivos.length - 1;}
					else{posicion = posicion - 1;}
					reproductor.stop();
					multimedia = new Media((new File(archivos[posicion])).toURI().toString());
					reproductor = new MediaPlayer(multimedia);
					reproductor.setAutoPlay(true);	
					reproductor.setOnEndOfMedia(new Runnable(){
					@Override 
						public void run(){
							reproductor.stop();
							autoreproducir();
						}
					});
        				visor.setMediaPlayer(reproductor);
					reproduciendo = true;
					reproductor.setVolume(volumen);
					reproductor.play();
				}
				/*subir volumen*/
				else if((event.getCode()==KeyCode.UP) && (volumen<=1.0)){
					volumen = volumen + 0.1;
					reproductor.setVolume(volumen);
				}
				/*bajar volumen*/
				else if((event.getCode()==KeyCode.DOWN) && (volumen>=0.0)){
					volumen = volumen - 0.1;
					reproductor.setVolume(volumen);
				}
				/*pausa*/
                		else if((event.getCode()==KeyCode.SPACE) && (reproduciendo==true)){
					icono.setImage(new Image(getClass().getResourceAsStream("pausa.png")));
					reproduciendo=false;
                    			reproductor.pause();
                    			root.getChildren().add(icono);
                		}
                		/*play*/
                		else if((event.getCode()==KeyCode.SPACE) && (reproduciendo==false)){
					root.getChildren().remove(icono);
					reproduciendo=true;
					reproductor.play();
				}
				/*stop*/
				else if((event.getCode()==KeyCode.ENTER) && (reproduciendo==true)){
					icono.setImage(new Image(getClass().getResourceAsStream("stop.png")));
					reproduciendo=false;
					reproductor.stop();
					root.getChildren().add(icono);
				}
				else if((event.getCode()==KeyCode.ENTER) && (reproduciendo==false)){
					root.getChildren().remove(icono);
					icono.setImage(new Image(getClass().getResourceAsStream("stop.png")));
					reproduciendo=false;
					reproductor.stop();
					root.getChildren().add(icono);
				}
			}
		});
		/*pordefault*/
		reproduciendo = true;
		volumen = 0.4;
		reproductor.setVolume(volumen);
		reproductor.play();
		contedorfx.setScene(scene); 
	}	
	/*metodo de autoreproduccion*/
	private void autoreproducir(){
		if(posicion==(archivos.length-1)){posicion = 0;}
		else{posicion = posicion + 1;}
		multimedia = new Media((new File(archivos[posicion])).toURI().toString());
		reproductor = new MediaPlayer(multimedia);
		reproductor.setAutoPlay(true);	
		/*evento recursivo de autoreproduccion*/
		reproductor.setOnEndOfMedia(new Runnable(){
            		@Override 
            		public void run(){
                		reproductor.stop();
                		autoreproducir();
            		}
        	});
        	visor.setMediaPlayer(reproductor);
        	reproduciendo = true;
		reproductor.setVolume(volumen);
		reproductor.play();
	}
	
	/*eventos del formulario*/
	public void internalFrameOpened(InternalFrameEvent event){}
	public void internalFrameClosing(InternalFrameEvent event){
		reproductor.stop();
		reproductor.dispose();
		dispose();
	}
	public void internalFrameClosed(InternalFrameEvent event){}
	public void internalFrameIconified(InternalFrameEvent event){}
	public void internalFrameDeiconified(InternalFrameEvent event){}
	public void internalFrameActivated(InternalFrameEvent event){}
	public void internalFrameDeactivated(InternalFrameEvent event){}
}
