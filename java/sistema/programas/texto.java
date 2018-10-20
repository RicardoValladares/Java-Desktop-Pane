package sistema.programas;
import java.io.*;
import java.awt.*;
import javax.swing.*; 
import java.awt.event.*;
import javax.swing.filechooser.*;
public class texto extends JInternalFrame implements ActionListener{
	/*declaramos los objetos de la interfaz*/
	private static String ruta="";
	private String ruta_local;
	private String separador;
	private JMenuBar barra_de_menus;
	private JMenu menu1, menu2;
	private JMenuItem opcion1, opcion2, opcion3, opcion4;
	private JMenuItem opcion5, opcion6, opcion7, opcion8;
	private JTextArea areadetexto;
	private JScrollPane scroll;
	public String caracteres;
	/*creamos formulario*/
	public texto(String args){
		super("Editor de Texto", true, true, true, true);
		setSize(400, 300);
		setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE); 
		barra_de_menus = new JMenuBar();
		/*creamos el menu archivo*/
		menu1 = new JMenu ("Archivo");
		opcion1 = new JMenuItem("Nuevo");
		opcion2 = new JMenuItem("Abrir");
		opcion3 = new JMenuItem("Guardar");
		opcion4 = new JMenuItem("Guardar como");
		menu1.add(opcion1);
		menu1.add(opcion2);
		menu1.add(opcion3);
		menu1.add(opcion4);
		opcion1.addActionListener(this);
		opcion2.addActionListener(this);
		opcion3.addActionListener(this);
		opcion4.addActionListener(this);
		barra_de_menus.add(menu1);
		/*creamos el menu edicion*/
		menu2 = new JMenu("Edicion");
		opcion5 = new JMenuItem("Copiar");
		opcion6 = new JMenuItem("Cortar");
		opcion7 = new JMenuItem("Pegar");
		opcion8 = new JMenuItem("Seleccionar todo");
		menu2.add(opcion5);
		menu2.add(opcion6);
		menu2.add(opcion7);
		menu2.addSeparator();
		menu2.add(opcion8);
		opcion5.addActionListener(this);
		opcion6.addActionListener(this);
		opcion7.addActionListener(this);
		opcion8.addActionListener(this);
		barra_de_menus.add(menu2);
		/*creamos area de trabajo*/
		areadetexto = new JTextArea(5,5);
		scroll = new JScrollPane(areadetexto);
		/*creamos layout para que se ajusten lo objetos al redimensionar*/
		BorderLayout layout = new BorderLayout(0,0);
		setLayout(layout);
		/*aderimos los objetos a la interfaz*/
		add(barra_de_menus,BorderLayout.NORTH);
		add(scroll,BorderLayout.CENTER);
		/*inicializamos separadores*/
		if((System.getProperty("os.name").substring(0, 3)).equals("Win")){separador = File.separator + File.separator;}
		else{separador = File.separator;}
		/*inicializamos carpeta de trabajo*/
		try{
			File ruta_con_jar = new File(texto.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
			File ruta_sin_jar = ruta_con_jar.getParentFile();
			ruta_local = (ruta_sin_jar.getPath())+(separador)+("archivos")+(separador);
			ruta = args;
			/*abrimos archivo en caso de peticion*/
			if(!ruta.equals(ruta_local)){
				String linea = ""; caracteres = "";
				File archivo = new File(ruta);
				ruta = archivo.toString();
				FileReader lectura = new FileReader(archivo);
				BufferedReader lector = new BufferedReader(lectura);
				while((linea=lector.readLine())!=null){caracteres = caracteres + linea + "\n";}
				lector.close();
				areadetexto.setText(caracteres);
			}
		}catch(Exception ex){JOptionPane.showMessageDialog(null,"No se ha encontrado el archivo","ADVERTENCIA!!!",2);}
	}
	/*eventos de las opciones del menu*/
	public void actionPerformed(ActionEvent e){
		/*opciones de archivo*/
		if(e.getSource()==opcion1){
			ruta=ruta_local;
			areadetexto.setText("");
		}
		if(e.getSource()==opcion2){
			String archivo = abrir();
			if(!archivo.equals("")){
				areadetexto.setText(archivo);
			}
		}
		if(e.getSource()==opcion3){
			guardar("guardar");
		}
		if(e.getSource()==opcion4){
			guardar("guardar como");
		}
		/*opciones de edicion*/
		if(e.getSource()==opcion5){
			areadetexto.copy();
		}
		if(e.getSource()==opcion6){
			areadetexto.cut();
		}
		if(e.getSource()==opcion7){
			areadetexto.paste();
		}
		if(e.getSource()==opcion8){
			areadetexto.selectAll();
		}
	}
	/*metodo abrir*/
	private String abrir(){
		String linea = ""; 
		caracteres = "";
		try{
			/*creamos selector de archivo con filtro*/
			JFileChooser selectordearchivo = new JFileChooser(ruta_local); 
			selectordearchivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
			selectordearchivo.addChoosableFileFilter(new FileNameExtensionFilter("TEXTO", "txt"));
			selectordearchivo.setAcceptAllFileFilterUsed(false);
			int estado = selectordearchivo.showOpenDialog(null);
			/*validamos haya seleccionado un archivo*/
			if(estado==JFileChooser.APPROVE_OPTION){
				File archivo = selectordearchivo.getSelectedFile();
				ruta = archivo.toString();
				FileReader lectura = new FileReader(archivo);
				BufferedReader lector = new BufferedReader(lectura);
				while((linea=lector.readLine())!=null){caracteres = caracteres + linea + "\n";}
				lector.close();
			}
		}catch(IOException ex){JOptionPane.showMessageDialog(null,"No se ha encontrado el archivo","ADVERTENCIA!!!",2);}
		return caracteres;
	}
	/*metodo guardar y guardar como*/
	private void guardar(String accion){
		if(accion.equals("guardar como") || ruta.equals(ruta_local)){
			try{
				/*creamos selector de archivo con filtro*/
				JFileChooser selectordearchivo = new JFileChooser(ruta_local);
				selectordearchivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int estado = selectordearchivo.showSaveDialog(this);
				/*validamos haya seleccionado un archivo*/
				if(estado==JFileChooser.APPROVE_OPTION){ 
					File archivo = selectordearchivo.getSelectedFile();
					ruta = archivo.toString();
					FileWriter escritor = new FileWriter(archivo+".txt");
					escritor.write(areadetexto.getText());
					escritor.close();
				}
			}catch(IOException ex){JOptionPane.showMessageDialog(null,"ERROR al guardar","ERROR",2);}
		}
		else{
			try{
				/*guardamos directamente en ruta*/
				File archivo = new File(ruta);
				FileWriter escritor = new FileWriter(archivo);
				escritor.write(areadetexto.getText());
				escritor.close();
			}catch(IOException ex){JOptionPane.showMessageDialog(null,"ERROR al guardar","ERROR",2);}
		}
	}
}
