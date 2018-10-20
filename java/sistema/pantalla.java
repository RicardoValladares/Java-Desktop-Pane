package sistema;
import java.io.*;
import java.awt.*;
import javax.swing.*; 
import java.awt.image.*;
import javax.imageio.ImageIO;
import javax.swing.filechooser.*;
public class pantalla extends Thread{
	public String ruta;
	public Dimension dimension;
	public Point posicion;
	public void run(){ 
        	try{ 
			/*capturamos la pantalla*/
			Thread.sleep(4*1000);
			Rectangle rectangulo = new Rectangle(posicion, dimension);
			Robot robot = new Robot();
			BufferedImage imagen = robot.createScreenCapture(rectangulo);
			Thread.sleep(1000);
			/*creamos selector de archivo con filtro*/
			JFileChooser selectordearchivo = new JFileChooser();
			FileNameExtensionFilter filtro=new FileNameExtensionFilter("*.PNG","png");
			selectordearchivo.setFileFilter(filtro);
			/*mostramos el selector de archivo con un nombre por default*/
			String separador;
			if((System.getProperty("os.name").substring(0, 3)).equals("Win")){separador = File.separator + File.separator;}
			else{separador = File.separator;}
			selectordearchivo.setSelectedFile(new File(ruta+separador+"captura.png"));
			int estado = selectordearchivo.showSaveDialog(null);
			/*captamos el archivo a escribir*/
			File archivo = selectordearchivo.getSelectedFile();
			/*validamos la opcion guardar y cancelar*/
			if(estado==JFileChooser.APPROVE_OPTION){
				/*validamos extension*/
				if((archivo.getName()).matches(".*\\.(png|PNG)")){
					ImageIO.write(imagen, "png", archivo);
				}
				else{
					archivo = new File(archivo.toString() + ".png");
					ImageIO.write(imagen, "png", archivo);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		} 
	} 
}
