package sistema;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;
import sistema.programas.*;
public class archivo implements ListCellRenderer, ActionListener, ListSelectionListener, MouseListener, KeyListener{
	/*objetos*/
	public JInternalFrame formulario;
	private JPanel panel;
	private JScrollPane panel_con_scroll;
	private JToolBar herramientas;
	private JTextField url;
	private JButton atras, actualizar, crear, renombrar, copiar, eliminar;
	private JList lista;
	private File ruta;
	/*metodo de la ventana principal*/
	public void ventana(String ruta_local){
		ruta = new File(ruta_local);
		/*barra de herramientas*/
		herramientas = new JToolBar("barra"); 
		atras = new JButton(new ImageIcon(archivo.class.getResource("atras.png"))); 
		actualizar = new JButton(new ImageIcon(archivo.class.getResource("actualizar.png")));
		url = new JTextField(ruta.toString());
		url.setEditable(false);
		url.setBackground(Color.WHITE);
		crear = new JButton(new ImageIcon(archivo.class.getResource("nuevo.png"))); 
		renombrar = new JButton(new ImageIcon(archivo.class.getResource("renombrar.png")));
		copiar = new JButton(new ImageIcon(archivo.class.getResource("copiar.png"))); 
		eliminar = new JButton(new ImageIcon(archivo.class.getResource("eliminiar.png")));
		herramientas.add(atras); 
		herramientas.add(actualizar); 
		herramientas.addSeparator();
		herramientas.add(url); 
		herramientas.addSeparator();
		herramientas.add(crear); 
		herramientas.add(renombrar); 
		herramientas.add(copiar); 
		herramientas.add(eliminar); 
		herramientas.setFloatable(false); //impide que se pueda mover de su sitio
		herramientas.setOrientation(JToolBar.HORIZONTAL);
		/*lista de archivos*/
		lista = new JList(ruta.listFiles());
		lista.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		lista.setCellRenderer(new archivo());
		lista.setLayoutOrientation(javax.swing.JList.HORIZONTAL_WRAP);
		lista.setVisibleRowCount(-1);
		panel_con_scroll = new JScrollPane(lista);
		/*maquetado en panel*/
		panel = new JPanel();
		BorderLayout layout = new BorderLayout(0,0);
		panel.setLayout(layout);
		panel.add(herramientas, BorderLayout.NORTH);
		panel.add(panel_con_scroll, BorderLayout.CENTER);
		panel.setPreferredSize(new Dimension(500, 300));
		/*formulario*/
		formulario = new JInternalFrame("Archivo", true, true, true, true);
		formulario.setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		formulario.setResizable(true);
		formulario.add(panel);
		formulario.pack();
		/*desactivacion de botones por default*/
		renombrar.setEnabled(false);
		copiar.setEnabled(false);
		eliminar.setEnabled(false);
		/*eventos en botones*/
		atras.addActionListener(this);
		actualizar.addActionListener(this);
		crear.addActionListener(this);
		renombrar.addActionListener(this);
		copiar.addActionListener(this);
		eliminar.addActionListener(this);
		lista.addListSelectionListener(this);
		lista.addMouseListener(this);
		lista.addKeyListener(this);
	}
	
	/*eventos del mouse*/
	public void mouseExited(MouseEvent e){}
	public void mouseClicked(MouseEvent e){
		if(e.getClickCount() == 2){
			File file = (File) lista.getSelectedValue();
			if(file.isDirectory()){
				formulario.remove(panel);
				panel = panel_actualizado(file);
				formulario.add(panel);
				formulario.revalidate();
				formulario.repaint();
				formulario.pack();
			}
			if((file.getPath()).matches(".*\\.(png|PNG|jpg|JPG|gif|GIF)")){
				JDesktopPane desktop = formulario.getDesktopPane();
				imagen programa = new imagen(file.getPath()); 
				desktop.add(programa);
				Dimension desktopSize = desktop.getSize();
				Dimension jIFrameSize = programa.getSize();
				programa.setLocation(((desktopSize.width - jIFrameSize.width) / 2), ((desktopSize.height - jIFrameSize.height) / 2));
				programa.setVisible(true);
			}
			if((file.getPath()).matches(".*\\.(txt|TXT|css|CSS|csv|CSV|js|JS)")){
				JDesktopPane desktop = formulario.getDesktopPane();
				texto programa = new texto(file.getPath()); 
				desktop.add(programa);
				Dimension desktopSize = desktop.getSize();
				Dimension jIFrameSize = programa.getSize();
				programa.setLocation(((desktopSize.width - jIFrameSize.width) / 2), ((desktopSize.height - jIFrameSize.height) / 2));
				programa.setVisible(true);
			}
			if((file.getPath()).matches(".*\\.(htm|HTM|html|HTML)")){
				JDesktopPane desktop = formulario.getDesktopPane();
				html programa = new html(file.getPath()); 
				desktop.add(programa);
				Dimension desktopSize = desktop.getSize();
				Dimension jIFrameSize = programa.getSize();
				programa.setLocation(((desktopSize.width - jIFrameSize.width) / 2), ((desktopSize.height - jIFrameSize.height) / 2));
				programa.setVisible(true);
			}
			if((file.getPath()).matches(".*\\.(wav|WAV)")){
				JDesktopPane desktop = formulario.getDesktopPane();
				musica programa = new musica(file.getPath()); 
				desktop.add(programa);
				Dimension desktopSize = desktop.getSize();
				Dimension jIFrameSize = programa.getSize();
				programa.setLocation(((desktopSize.width - jIFrameSize.width) / 2), ((desktopSize.height - jIFrameSize.height) / 2));
				programa.setVisible(true);
			}
			if((file.getPath()).matches(".*\\.(mp4|MP4)")){
				JDesktopPane desktop = formulario.getDesktopPane();
				video programa = new video(file.getPath()); 
				desktop.add(programa);
				Dimension desktopSize = desktop.getSize();
				Dimension jIFrameSize = programa.getSize();
				programa.setLocation(((desktopSize.width - jIFrameSize.width) / 2), ((desktopSize.height - jIFrameSize.height) / 2));
				programa.setVisible(true);
			}
		}
	}
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	
	/*eventos del teclado*/
	public void keyReleased(KeyEvent e){
		if(e.getKeyCode() == 10){
			File file = (File) lista.getSelectedValue();
			if(file.isDirectory()){
				formulario.remove(panel);
				panel = panel_actualizado(file);
				formulario.add(panel);
				formulario.revalidate();
				formulario.repaint();
				formulario.pack();
			}
			if((file.getPath()).matches(".*\\.(png|PNG|jpg|JPG|gif|GIF)")){
				JDesktopPane desktop = formulario.getDesktopPane();
				imagen programa = new imagen(file.getPath()); 
				desktop.add(programa);
				Dimension desktopSize = desktop.getSize();
				Dimension jIFrameSize = programa.getSize();
				programa.setLocation(((desktopSize.width - jIFrameSize.width) / 2), ((desktopSize.height - jIFrameSize.height) / 2));
				programa.setVisible(true);
			}
			if((file.getPath()).matches(".*\\.(txt|TXT)")){
				JDesktopPane desktop = formulario.getDesktopPane();
				texto programa = new texto(file.getPath()); 
				desktop.add(programa);
				Dimension desktopSize = desktop.getSize();
				Dimension jIFrameSize = programa.getSize();
				programa.setLocation(((desktopSize.width - jIFrameSize.width) / 2), ((desktopSize.height - jIFrameSize.height) / 2));
				programa.setVisible(true);
			}
			if((file.getPath()).matches(".*\\.(wav|WAV)")){
				JDesktopPane desktop = formulario.getDesktopPane();
				musica programa = new musica(file.getPath()); 
				desktop.add(programa);
				Dimension desktopSize = desktop.getSize();
				Dimension jIFrameSize = programa.getSize();
				programa.setLocation(((desktopSize.width - jIFrameSize.width) / 2), ((desktopSize.height - jIFrameSize.height) / 2));
				programa.setVisible(true);
			}
			if((file.getPath()).matches(".*\\.(mp4|MP4)")){
				JDesktopPane desktop = formulario.getDesktopPane();
				video programa = new video(file.getPath()); 
				desktop.add(programa);
				Dimension desktopSize = desktop.getSize();
				Dimension jIFrameSize = programa.getSize();
				programa.setLocation(((desktopSize.width - jIFrameSize.width) / 2), ((desktopSize.height - jIFrameSize.height) / 2));
				programa.setVisible(true);
			}
		}
	}
	public void keyPressed(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
	
	/*evento de seleccion en la lista*/
	public void valueChanged(ListSelectionEvent e){
		if(!e.getValueIsAdjusting()){
			File file = (File) lista.getSelectedValue();
			if(file == null){
				renombrar.setEnabled(false);
				copiar.setEnabled(false);
				eliminar.setEnabled(false);
			}
			else{
				renombrar.setEnabled(true);
				copiar.setEnabled(true);
				eliminar.setEnabled(true);
			}
		}
	}
	
	/*evento de accionamiento en botones*/
	public void actionPerformed(ActionEvent e){
		if(e.getSource()==atras){
			formulario.remove(panel);
			panel = panel_actualizado(new File(url.getText()+File.separator+".."));
			formulario.add(panel);
			formulario.revalidate();
			formulario.repaint();
			formulario.pack();
		}
		if(e.getSource()==actualizar){
			formulario.remove(panel);
			panel = panel_actualizado(new File(url.getText()));
			formulario.add(panel);
			formulario.revalidate();
			formulario.repaint();
			formulario.pack();
		}
		if(e.getSource()==crear){
			String nombre = JOptionPane.showInputDialog(formulario, "Nombre de la carpeta:");
			if(!(nombre==null || nombre.equals(""))){
				File carpeta = new File(url.getText()+File.separator+nombre);
				if(!carpeta.exists()){
					carpeta.mkdir();
					formulario.remove(panel);
					panel = panel_actualizado(new File(url.getText()));
					formulario.add(panel);
					formulario.revalidate();
					formulario.repaint();
					formulario.pack();
				}
				else{JOptionPane.showMessageDialog(formulario,"Ya existe el archivo: "+nombre,"ERROR",0);}
			}
		}
		if(e.getSource()==renombrar){
			File file1 = (File) lista.getSelectedValue();
			String nombre = JOptionPane.showInputDialog(formulario, "Nuevo nombre:");
			if(!(nombre==null || nombre.equals(""))){
				File file2 = new File(url.getText()+File.separator+nombre);
				boolean renombrado = file1.renameTo(file2);
				if(renombrado){
					formulario.remove(panel);
					panel = panel_actualizado(new File(url.getText()));
					formulario.add(panel);
					formulario.revalidate();
					formulario.repaint();
					formulario.pack();
				}
				else{JOptionPane.showMessageDialog(formulario,"No se pudo renombrar","ERROR",0);}
			}
		}
		if(e.getSource()==copiar){
			File file1 = (File) lista.getSelectedValue();
			if(file1.isDirectory()){
				try{
					JFileChooser selectordearchivo = new JFileChooser(url.getText()); 
					selectordearchivo.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					int estado = selectordearchivo.showSaveDialog(null);
					if(estado==JFileChooser.APPROVE_OPTION){
						File file2 = selectordearchivo.getSelectedFile();
						CopiarDirectorio(file1, file2);
						formulario.remove(panel);
						panel = panel_actualizado(new File(url.getText()));
						formulario.add(panel);
						formulario.revalidate();
						formulario.repaint();
						formulario.pack();
					}
				}catch(Exception ex){JOptionPane.showMessageDialog(formulario,"No se pudo copiar","ERROR",0);}
			}
			else{
				try{
					JFileChooser selectordearchivo = new JFileChooser(); 
					selectordearchivo.setFileSelectionMode(JFileChooser.FILES_ONLY);
					selectordearchivo.setSelectedFile(file1);
					int estado = selectordearchivo.showSaveDialog(null);
					if(estado==JFileChooser.APPROVE_OPTION){
						File file2 = selectordearchivo.getSelectedFile();
						Copiar(file1, file2);
						formulario.remove(panel);
						panel = panel_actualizado(new File(url.getText()));
						formulario.add(panel);
						formulario.revalidate();
						formulario.repaint();
						formulario.pack();
					}
				}catch(Exception ex){JOptionPane.showMessageDialog(formulario,"No se pudo copiar","ERROR",0);}
			}
		}
		if(e.getSource()==eliminar){
			File file = (File) lista.getSelectedValue();
			if(file.isDirectory()){
				borrarDirectorio(file);
				file.delete();
			}else{file.delete();}
			formulario.remove(panel);
			panel = panel_actualizado(new File(url.getText()));
			formulario.add(panel);
			formulario.revalidate();
			formulario.repaint();
			formulario.pack();
		}
	}
	/*metodos de borrado en cola*/
	public void borrarDirectorio(File directorio){
		File[] ficheros = directorio.listFiles();
		for(int x=0;x<ficheros.length;x++){
			if(ficheros[x].isDirectory()){borrarDirectorio(ficheros[x]);}
			ficheros[x].delete();
		}
	}
	/*metodos de copiado en cola*/
	public void CopiarDirectorio(File dirOrigen, File dirDestino) throws Exception{ 
		if(dirOrigen.isDirectory()){
			if(!dirDestino.exists()){dirDestino.mkdir();}
			String[] hijos = dirOrigen.list();
			for(int i=0; i < hijos.length; i++){CopiarDirectorio(new File(dirOrigen, hijos[i]), new File(dirDestino, hijos[i]));}
		}else{Copiar(dirOrigen, dirDestino);}
	}
	public void Copiar(File dirOrigen, File dirDestino) throws Exception { 
		InputStream in = new FileInputStream(dirOrigen); 
		OutputStream out = new FileOutputStream(dirDestino); 
		byte[] buffer = new byte[1024];
		int len;
		while((len = in.read(buffer)) > 0){out.write(buffer, 0, len);}
		out.flush();
	}
	
	/*renderizado del panel*/
	public JPanel panel_actualizado(File argumento){
		try{
			ruta = argumento;
			url.setText(ruta.getCanonicalPath().toString());
			lista = new JList(ruta.listFiles());
			lista.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
			lista.setCellRenderer(new archivo());
			lista.setLayoutOrientation(javax.swing.JList.HORIZONTAL_WRAP);
			lista.setVisibleRowCount(-1);
			panel_con_scroll = new JScrollPane(lista);
			panel = new JPanel();
			BorderLayout layout = new BorderLayout(0,0);
			panel.setLayout(layout);
			panel.add(herramientas, BorderLayout.NORTH);
			panel.add(panel_con_scroll, BorderLayout.CENTER);
			panel.setPreferredSize(new Dimension(500, 300));
			renombrar.setEnabled(false);
			copiar.setEnabled(false);
			eliminar.setEnabled(false);
			lista.addListSelectionListener(this);
			lista.addMouseListener(this);
			lista.addKeyListener(this);
			return panel;
		}catch(Exception ex){return panel;}
	}
	/*renderizado de la lista de archivos*/
	protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
		JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list, value, index, isSelected,cellHasFocus);
		if(value instanceof File){
			File file = (File) value;
			renderer.setText(file.getName());
			renderer.setIcon(FileSystemView.getFileSystemView().getSystemIcon(file));
			if(isSelected){renderer.setBackground(list.getSelectionBackground());renderer.setForeground(list.getSelectionForeground());}
			else{renderer.setBackground(list.getBackground());renderer.setForeground(list.getForeground());}
			renderer.setEnabled(list.isEnabled());
			renderer.setFont(list.getFont());
			renderer.setOpaque(true);
		}
		return renderer;
	}
}
