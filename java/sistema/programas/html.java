package sistema.programas;
import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*; 
import java.awt.event.*;
import javax.swing.text.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.filechooser.*;
public class html extends JInternalFrame implements ActionListener{
	/*declaramos los objetos de la interfaz*/
	private static String ruta="";
	private String ruta_local;
	private String separador;
	private JMenuBar barra_de_menus;
	private JPopupMenu menudesplegable;
	private JMenu menu1, menu2;
	private JMenuItem opcion1, opcion2, opcion3, opcion4;
	private JMenuItem opcion5, opcion6, opcion7, opcion8;
	private JMenuItem opcion55, opcion66, opcion77, opcion88;
	private JTextPane areadetexto;
	private JScrollPane scroll;
	//public String palabrasreservadas = "values|VALUES|tables|TABLES|databases|DATABASES|true|TRUE|false|FALSE|AUTO_INCREMENT|auto_increment|null|NULL|as\\u0020|AS\\u0020|limit\\u0020|LIMIT\\u0020|create\\u0020|CREATE\\u0020|drop\\u0020|DROP\\u0020|database\\u0020|DATABASE\\u0020|table\\u0020|TABLE\\u0020|describe\\u0020|DESCRIBE\\u0020|show\\u0020|SHOW\\u0020|insert\\u0020|INSERT\\u0020|into\\u0020|INTO\\u0020|select\\u0020|SELECT\\u0020|from\\u0020|FROM\\u0020|order\\u0020|ORDER\\u0020|by\\u0020|BY\\u0020|where\\u0020|WHERE\\u0020|\\u0020not\\u0020|\\u0020NOT\\u0020|\\u0020and\\u0020|\\u0020AND\\u0020|\\u0020or\\u0020|\\u0020OR\\u0020|like\\u0020|LIKE\\u0020|update\\u0020|UPDATE\\u0020|set\\u0020|SET\\u0020|delete\\u0020|DELETE\\u0020|inner\\u0020|INNER\\u0020|join\\u0020|JOIN\\u0020|\\u0020on\\u0020|\\u0020ON\\u0020|right\\u0020|RIGHT\\u0020|left\\u0020|LEFT\\u0020|outer\\u0020|OUTER\\u0020|union\\u0020|UNION\\u0020|PRIMARY\\u0020KEY\\(.*?\\)|primary\\u0020key\\(.*?\\)|COUNT\\(.*?\\)|count\\(.*?\\)|MAX\\(.*?\\)|max\\(.*?\\)|MIN\\(.*?\\)|min\\(.*?\\)|SUM\\(.*?\\)|sum\\(.*?\\)|CONCAT\\(.*?\\)|concat\\(.*?\\)|CURDATE\\(.*?\\)|curdate\\(.*?\\)|CURTIME\\(.*?\\)|curtime\\(.*?\\)|NOW\\(.*?\\)|now\\(.*?\\)|DATEDIFF\\(.*?\\)|datediff\\(.*?\\)|DATE_ADD\\(.*?\\)|date_add\\(.*?\\)|call\\u0020|CALL\\u0020|INT\\u0020|int\\u0020|VARCHAR\\(.*?\\)|varchar\\(.*?\\)|CHAR\\(.*?\\)|char\\(.*?\\)|DOUBLE\\u0020|double\\u0020|BOOLEAN\\u0020|boolean\\u0020|DATE\\u0020|date\\u0020|DATETIME\\u0020|datetime\\u0020|TIME\\u0020|time\\u0020|MEDIUMBLOB\\u0020|mediumblob\\u0020|BLOB\\u0020|blob\\u0020|LONGBLOB\\u0020|longblob\\u0020|MEDIUMTEXT\\u0020|mediumtext\\u0020|TEXT\\u0020|text\\u0020|LONGTEXT\\u0020|longtext\\u0020|desc|DESC";
	public String caracteres;
	/*creamos formulario*/
	public html(String args){
		super("Editor de HTML", true, true, true, true);
		setSize(585,500);
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
		/*creamos el estilizado del area de trabajo*/
		StyleContext estilo = StyleContext.getDefaultStyleContext();
		AttributeSet negrita = estilo.addAttribute(estilo.getEmptySet(), StyleConstants.Bold, Boolean.TRUE);
		AttributeSet azul = estilo.addAttribute(negrita, StyleConstants.Foreground, Color.BLUE.darker());
		AttributeSet negro = estilo.addAttribute(negrita, StyleConstants.Foreground, Color.BLACK);
		/*aplicamos estilizado al texto cuando se digite o se borre*/
		DefaultStyledDocument estilisado = new DefaultStyledDocument(){
			/*evento al digitar o pegar*/
			public void insertString(int posicioncursor, String textomodificado, AttributeSet a) throws BadLocationException{
				super.insertString(posicioncursor, textomodificado, a);
				String text = getText(0, getLength());
				setCharacterAttributes(0, getLength(), negro, false);
				/*buscamos etiquetas y las estilizamos*/
				Matcher comparador = Pattern.compile("\\<.*?\\>").matcher(text);
				while(comparador.find()){
					String etiqueta = comparador.group();
					int iniciodeetiqueta = comparador.start();
					int finaldeetiqueta = comparador.end();
					setCharacterAttributes(iniciodeetiqueta, etiqueta.length(), azul, false);
				}
			}
			/*evento al borrar o cortar*/
			public void remove(int posicioncursor, int cantidadecaracteres) throws BadLocationException{
				super.remove(posicioncursor, cantidadecaracteres);
				String text = getText(0, getLength());
                		setCharacterAttributes(0, getLength(), negro, false);
                		/*buscamos etiquetas y las estilizamos*/
                		Matcher comparador = Pattern.compile("\\<.*?\\>").matcher(text);
				while (comparador.find()){
					String etiqueta = comparador.group();
					int iniciodeetiqueta = comparador.start();
					int finaldeetiqueta = comparador.end();
					setCharacterAttributes(iniciodeetiqueta, etiqueta.length(), azul, false);
				}
            		}
        	};
        	/*creamos el area de trabajo con menu desplegable*/
        	menudesplegable = new JPopupMenu();
        	opcion55 = new JMenuItem("Copiar");
		opcion66 = new JMenuItem("Cortar");
		opcion77 = new JMenuItem("Pegar");
		opcion88 = new JMenuItem("Seleccionar todo");
        	menudesplegable.add(opcion55);
		menudesplegable.add(opcion66);
		menudesplegable.add(opcion77);
		menudesplegable.addSeparator();
		menudesplegable.add(opcion88);
		opcion55.addActionListener(this);
		opcion66.addActionListener(this);
		opcion77.addActionListener(this);
		opcion88.addActionListener(this);
		areadetexto = new JTextPane(estilisado);
		scroll = new JScrollPane(areadetexto);
		areadetexto.setComponentPopupMenu(menudesplegable);
		areadetexto.setInheritsPopupMenu(true);
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
			File ruta_con_jar = new File(html.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
			File ruta_sin_jar = ruta_con_jar.getParentFile();
			ruta_local = (ruta_sin_jar.getPath())+(separador)+("archivos")+(separador)+("web")+(separador);
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
		if(e.getSource()==opcion5 || e.getSource()==opcion55){
			areadetexto.copy();
		}
		if(e.getSource()==opcion6 || e.getSource()==opcion66){
			areadetexto.cut();
		}
		if(e.getSource()==opcion7 || e.getSource()==opcion77){
			areadetexto.paste();
		}
		if(e.getSource()==opcion8 || e.getSource()==opcion88){
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
			selectordearchivo.addChoosableFileFilter(new FileNameExtensionFilter("WEB", "html"));
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
					FileWriter escritor = new FileWriter(archivo+".html");
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
