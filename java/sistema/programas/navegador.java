package sistema.programas;
import javax.swing.*; 
import javafx.scene.*;
import javafx.geometry.*;
import javafx.scene.web.*;
import javafx.application.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.embed.swing.JFXPanel; 
public class navegador extends JInternalFrame{ 
	/*declaramos los objetos*/
	public String link;
	private JFXPanel contedorfx;
	private Scene escenariofx;
	private ProgressBar barra_progresiva;
	private WebView visorweb;
	private WebEngine serviciosweb;
	private WebHistory historial;
	private HBox caja_horizontal;
	private Label label;
	private TextField url;
	private Button ir, historial_siguiente, historial_atras;
	/*creamos el formulario*/
	public navegador(String args){
		super("Navegador web", true, true, true, false);
		setSize(450, 400);
		setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE); 
		/*creamos el contenedor de javafx para integrarlo en swing*/
		contedorfx = new JFXPanel(); 
		Platform.setImplicitExit(false); 
		Platform.runLater(() -> crearfx(contedorfx)); 
		getContentPane().add(contedorfx);
		if(args==""){link="http://www.google.com";}
		else{link=args;}
	}
	/*creamos los objetos javafx*/
	private void crearfx(JFXPanel contedorfx) { 
		caja_horizontal = new HBox(5);
		label = new Label("URL:");	url = new TextField(link);
		HBox.setHgrow(url, Priority.ALWAYS);
		ir = new Button("Ir"); historial_siguiente = new Button("Siguiente"); historial_atras = new Button("Anterior");
		historial_siguiente.setDisable(true);
		historial_atras.setDisable(true);
		visorweb = new WebView();
		serviciosweb = visorweb.getEngine();
		barra_progresiva = new ProgressBar(0);
		barra_progresiva.setMaxWidth(Double.MAX_VALUE);
		/*accionamiento de historial*/
		historial = serviciosweb.getHistory();
		historial.currentIndexProperty().addListener((propiedades, viejo_sitio, nuevo_sitio)->{
			int historial_indexado = nuevo_sitio.intValue();
			/*validar si estamos en la primer opcion del historial*/
			if(historial_indexado <= 0){historial_atras.setDisable(true);} 
			else{historial_atras.setDisable(false);}
			/*validar si estamos en la ultima opcion del historial*/
			if(historial_indexado >= historial.getEntries().size() - 1){historial_siguiente.setDisable(true);} 
			else{historial_siguiente.setDisable(false);}
		});
		serviciosweb.getLoadWorker().progressProperty().addListener((p, o, progreso) -> barra_progresiva.setProgress(progreso.doubleValue()));
		/*accionamiento del botones*/
		ir.setOnAction(a -> serviciosweb.load(url.getText()));
		historial_siguiente.setOnAction(a -> historial.go(+1));
		historial_atras.setOnAction(a -> historial.go(-1));
		/*maquetacion horizontal*/
		caja_horizontal.getChildren().addAll(label, url, ir, historial_atras, historial_siguiente);
		Node barra_de_navegacion = caja_horizontal;
		/*maquetacion vertical*/
		VBox caja_vertical = new VBox(3);
		caja_vertical.getChildren().addAll(barra_de_navegacion, visorweb, barra_progresiva);
		caja_vertical.setPadding(new Insets(5.0));
		Scene escenariofx = new Scene(caja_vertical); 
		contedorfx.setScene(escenariofx); 
	}
}
