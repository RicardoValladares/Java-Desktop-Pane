package sistema.juegos;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import javax.imageio.ImageIO;
public class solitario extends JInternalFrame implements MouseMotionListener, MouseListener, ActionListener, MenuListener{
	private AreaDeJuego arJuego;
	private int posX;
	private int posY;
	private Pila[] pila;
	private Carta[] cartas;
	private JMenuBar barraMenu;
	private JMenu mnuOpciones;
	private JMenuItem mnuNuevo;
	private JMenuItem mnuSalir;
	private JLabel mensaje;
	private JButton okas;
	/*formulario del juego*/
	public solitario(){
		super("Solitario", true, true, false, true);
		setSize(750,600);
		setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
		setResizable(false);
		barraMenu = new JMenuBar();
		mnuOpciones=new JMenu("Opciones");
		barraMenu.add(mnuOpciones);
		mnuOpciones.setMnemonic(KeyEvent.VK_O);
		mnuOpciones.addMenuListener(this);
		mnuNuevo = new JMenuItem("Nuevo juego");
		mnuOpciones.add(mnuNuevo);
		mnuNuevo.setMnemonic(KeyEvent.VK_N);
		mnuNuevo.addActionListener(this);
		mnuSalir = new JMenuItem("Salir");
		//mnuOpciones.add(mnuSalir);
		mnuSalir.setMnemonic(KeyEvent.VK_S);
		mnuSalir.addActionListener(this);
		setJMenuBar(barraMenu);
		mensaje = new JLabel("mensaje",JLabel.CENTER);
		okas = new JButton("Aceptar");
		okas.addActionListener(this);
		pila = new Pila[13];
		pila[0] = new Pila(45, 25); // esquina sup izquierda, cartas de reserva
		pila[1] = new Pila(130, 25); // cartas para mostrar de una en una
		pila[2] = new Pila(1, 0, 305, 30); // primer cuadrante de colocar cartas
		pila[3] = new Pila(1, 0, 405, 30); // segundo cuadrante de colocar cartas
		pila[4] = new Pila(1, 0, 505, 30); // tercer cuadrante de colocar cartas
		pila[5] = new Pila(1, 0, 605, 30); // cuarto cuadrante de colocar cartas
		pila[6] = new Pila(2, 20, 100, 140); // resto de pilas para acomodo de cartas en juego
		pila[7] = new Pila(2, 20, 185, 140);
		pila[8] = new Pila(2, 20, 270, 140);
		pila[9] = new Pila(2, 20, 355, 140);
		pila[10] = new Pila(2, 20, 440, 140);
		pila[11] = new Pila(2, 20, 525, 140);
		pila[12] = new Pila(2, 20, 610, 140);
		repartir();
	}
	/*barajar y repartir*/
	private void repartir(){
		String ruta;
		cartas = new Carta[52];
		int nCarta;
		int enPila;
		int palo;
		int valor;
		boolean pilaInvalida;
		arJuego = new AreaDeJuego();
		add(arJuego);
		arJuego.addMouseMotionListener(this);
		arJuego.addMouseListener(this);
		for(int i=0;i<52;i++){
			do{
				nCarta=(int)Math.floor(Math.random()*52);
			}while(cartas[nCarta]!=null);
			do{
				pilaInvalida=false;
				enPila=(int)Math.floor(Math.random()*13);
				if(enPila==0||enPila>5)
					pilaInvalida=enPila==0?(pila[0].size()<24):(pila[enPila].size()<(enPila-5));
			}while(!pilaInvalida);
			palo=(nCarta<13?1:nCarta<26?2:nCarta<39?3:4);
			valor=(nCarta+(nCarta<13?1:nCarta<26?-12:nCarta<39?-25:-38));
			ruta = "palo"+palo+"_"+valor+".JPG";
			cartas[nCarta] = new Carta(ruta,"reverso.JPG",!(enPila==0?false:(pila[enPila].size()==(enPila-6))),palo,(palo==1||palo==3?1:2),valor,pila[enPila].obtenEsqX(),pila[enPila].obtenEsqY()+((enPila==0?0:20)*pila[enPila].size()));
			cartas[nCarta].setSize(75,98);
			cartas[nCarta].addMouseMotionListener(this);
			cartas[nCarta].addMouseListener(this);
			arJuego.add(cartas[nCarta]);
			arJuego.moveToFront(cartas[nCarta]);
			cartas[nCarta].setLocation(pila[enPila].obtenEsqX(),
			pila[enPila].obtenEsqY()+((enPila==0?0:20)*pila[enPila].size()));
			pila[enPila].push(cartas[nCarta]);
			cartas[nCarta].enPila=enPila;
		}
		arJuego.validate();
	}
	/*evento de arrastre del mouse*/
	public void mouseDragged(MouseEvent e){
		if(e.getComponent() instanceof Carta)
			arrastra((Carta)e.getComponent(), e.getX(), e.getY());
	}
	/*arrastre de la carta grafica*/
	private void arrastra(Carta aux, int cx, int cy){
		if(aux.esReverso || (aux.enPila>1 && aux.enPila<6)) return;
		int nuevaX=aux.getLocation().x+(cx-aux.mouseX);
		int nuevaY=aux.getLocation().y+(cy-aux.mouseY);
		nuevaX=(nuevaX<0?0:(nuevaX>665?665:nuevaX));
		nuevaY=(nuevaY<0?0:(nuevaY>494?494:nuevaY));
		aux.arrastrandoHijas=(aux.cartaHija!=null);
		aux.setLocation(nuevaX,nuevaY);
		arJuego.moveToFront(aux);
		while( (aux=aux.cartaHija)!=null ){
			aux.setVisible(false);
			aux.setLocation(nuevaX,(nuevaY+=20));
		}
	}
	/*evento del mouse al ser movido*/
	public void mouseMoved(MouseEvent e){
		Component control = (Component)e.getComponent();
		if(control instanceof AreaDeJuego){
			posX=e.getX();
			posY=e.getY();
		}
		else if(control instanceof Carta){
			Carta aux = (Carta)control;
			aux.mouseX = e.getX();
			aux.mouseY = e.getY();
		}
	}
	/*evento del mouse al liberar*/
	public void mouseReleased(MouseEvent e){
		if(!(e.getComponent() instanceof Carta)) return;
		Carta tmpCard = (Carta)e.getComponent();
		int aux=tmpCard.enPila;
		for(int i=2;i<13;i++)
			if(aux !=i && pila[i].adoptaCarta(tmpCard)){
				arJuego.actualiza(tmpCard,i);
				pila[aux].sacaCarta(tmpCard);
				haGanado();
				return;
			}
			if(!tmpCard.esReverso)
				arJuego.actualiza(tmpCard,-1);
	}
	/*evento del mouse al ser presionado*/
	public void mousePressed(MouseEvent e){
		Carta aux;
		Carta aux2;
		if(e.getComponent() instanceof Carta){
			aux = (Carta)e.getComponent();
			aux2 = (pila[aux.enPila].size()==0?null:pila[aux.enPila].peek());
			if(aux!=aux2 || aux.cartaHija!=null) return;
			if(e.getButton()==1){
				if(aux.enPila==0){
					pila[0].sacaCarta(aux);
					aux.enPila=1;
					pila[1].push(aux);
					aux.setLocation(130,25);
					aux.esqX=130;
					aux.esqY=25;
				}
				aux.esReverso=false;
				arJuego.moveToFront(aux);
				aux.repaint();
			}
			else if(e.getButton()==3){
				if(aux.esReverso) return;
				for(int i=2;i<6;i++){
					aux2 = (pila[i].size()==0?null:pila[i].peek());
					if( (aux2==null&&aux.num==1) ||(aux.palo==(aux2!=null?aux2.palo:-1)&&aux.num==(aux2!=null?(aux2.num+1):-1)) ){
						aux.setLocation((i*100)+105,30);
						mouseReleased(e);
						arJuego.moveToFront(aux);
						arJuego.validate();
						return;
					}
				}
			}
		}
		else{
			int x=e.getX();
			int y=e.getY();
			if(x>=45 && x<=120 && y>=25 && y<=123){
				while(!pila[1].empty()){
					aux=pila[1].pop();
					aux.esReverso=true;
					aux.enPila=0;
					aux.setLocation(45,25);
					aux.esqX=45;
					aux.esqY=25;
					arJuego.moveToFront(aux);
					pila[0].push(aux);
				}
				arJuego.validate();
			}
		}
	}
	/*evntos del mouse sin ser usados*/
	public void mouseExited(MouseEvent e){}
	public void mouseClicked(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	/*eventos de accionamiento del Menu*/
	public void actionPerformed(ActionEvent e){
		if(e.getSource() instanceof JMenuItem){
			JMenuItem menu = (JMenuItem)e.getSource();
			if(menu==mnuNuevo){
				for(int i=0;i<13;i++)
					while(!pila[i].empty()) pila[i].pop();
					try{
						remove(arJuego);
					}
					catch(Exception er){
						mensaje.setText("Ha ocurrido el error: "+er.getMessage());
						System.exit(0);
					}
				repartir();
				return;
			}
			else System.exit(0);
		}
	}
	/*evento al seleccionar menu grafico*/
	public void menuSelected(MenuEvent e){
		try{
			arJuego.setVisible(false);
		}
		catch(Exception er){
			mensaje.setText("Ha ocurrido el error: "+er.getMessage());
			System.exit(0);
		}
	}
	/*evento al deseleccionar menu grafico*/
	public void menuDeselected(MenuEvent e){
		try{
			arJuego.setVisible(true);
		}
		catch(Exception er){
			mensaje.setText("Ha ocurrido el error: "+er.getMessage());
			System.exit(0);
		}
	}
	/*evento al cancelar menu grafico*/
	public void menuCanceled(MenuEvent e){
		try{
			arJuego.setVisible(true);
		}
		catch(Exception er){
			mensaje.setText("Ha ocurrido el error: "+er.getMessage());
			System.exit(0);
		}
	}
	/*evento al completar las pilas de cartas*/
	private void haGanado(){
		Carta tmp1=(pila[2].size()==0?null:pila[2].peek());
		Carta tmp2=(pila[3].size()==0?null:pila[3].peek());
		Carta tmp3=(pila[4].size()==0?null:pila[4].peek());
		Carta tmp4=(pila[5].size()==0?null:pila[5].peek());
		if(tmp1==null || tmp2==null || tmp3==null || tmp4==null) return;
		if(tmp1.num==13 && tmp2.num==13 && tmp3.num==13 && tmp4.num==13){
			mensaje.setText("Has ganado el juego");
		}
	}
}

class Pila extends Stack<Carta>{
	private boolean adopta; // si puede aceptar cartas por arrastre
	private int modoAdopcion; // si las acomoda en forma (1)creciente o (2)decreciente
							// para el 1er caso que no tenga subcartas
	private int margenSup; // si lleva una distancia d emargen superior entre cada carta
	private int esqX; // coordenada x en esquina izquierda superior
	private int esqY; // coordenada y en esquina izquierda superior
	public Pila(int x, int y){
		adopta=false;
		esqX=x;
		esqY=y;
	}
	public Pila(int mdAdt, int ms, int x, int y){
		adopta=true;
		modoAdopcion=mdAdt;
		margenSup=ms;
		esqX=x;
		esqY=y;
	}
	public boolean adoptaCarta(Carta cd){
		int xC = cd.getLocation().x;
		int yC = cd.getLocation().y;
		Carta tm=(size()<=0?null:peek());
		if(xC+75<esqX || xC>esqX+75 || yC+98<esqY || yC>esqY+98+((size()-1)*margenSup) || !adopta || (tm==null?false:tm.esReverso))
			return false;
		if(modoAdopcion==1){
			if(tm==null && cd.num!=1)
				return false;
			else if(tm==null && cd.num==1){
				adoptaYa(cd);
				return true;
			}
			if(tm.palo!=cd.palo || tm.num!=(cd.num-1) || cd.cartaHija!=null)
				return false;
			adoptaYa(cd);
		}
		else{
			if(tm==null && cd.num!=13)
				return false;
			else if(tm==null && cd.num==13){
				adoptaYa(cd);
				return true;
			}
			if(tm.num!=(cd.num+1) || tm.color==cd.color)
				return false;
			tm.cartaHija=cd;
			adoptaYa(cd);
		}
		return true;
	}
	public void adoptaYa(Carta cd){ // actualiza la location y la pusha dentro de la pila
		cd.esqX=esqX;
		cd.esqY=esqY+(size()*margenSup);
		push(cd);
		if(cd.cartaHija!=null)
			adoptaYa(cd.cartaHija);
	}
	public void sacaCarta(Carta cd){
		Carta tmp;
		while(size()>0 && pop()!=cd) ;
			if(size()>0){
				tmp=pop();
				tmp.cartaHija=null;
				push(tmp);
			}
	}
	public int obtenEsqX(){
		return esqX;
	}
	public int obtenEsqY(){
		return esqY;
	}
}

class AreaDeJuego extends JLayeredPane{
	public void paint(Graphics g){
		g.setColor(new Color(204, 204, 255));
		g.fillRect(45,25,75,98);
		// area de muestra en 130,25
		g.setColor(new Color(204, 204, 255));
		g.fillRect(305,30,75,98);
		g.fillRect(405,30,75,98);
		g.fillRect(505,30,75,98);
		g.fillRect(605,30,75,98);
		g.setColor(new Color(204, 204, 255));
		g.fillRect(100,140,75,98);
		g.fillRect(185,140,75,98);
		g.fillRect(270,140,75,98);
		g.fillRect(355,140,75,98);
		g.fillRect(440,140,75,98);
		g.fillRect(525,140,75,98);
		g.fillRect(610,140,75,98);
	}
	public void actualiza(Carta cd, int pila){ // si pila=-1 dejar la misma pila que tenga
		cd.enPila=(pila==-1?cd.enPila:pila);
		cd.setLocation(cd.esqX,cd.esqY);
		cd.arrastrandoHijas=false;
		cd.setVisible(true);
		cd.repaint();
		moveToFront(cd);
		if(cd.cartaHija!=null)
			actualiza(cd.cartaHija,pila);
		validate();
	}
}

class Carta extends Canvas{
	public int palo; // valores 1, 2, 3 y 4
	public int color; // valores 1=rojo, 2=negro
	public int num; // valores del 1 al 13
	public boolean esReverso; // indica si la carta esta volteada o de reverso
	private Image imgCarta; // imagen de la carta
	private Image imgReverso; // imagen del reverso de la carta
	public Carta cartaHija; /* indica una carta colocada bajo esta (this) en la
	fila de cartas del juego (si la hay) */
	public int esqX; // coordenada x superior donde se ubica la carta
	public int esqY; // coordenada y izquierda donde se ubica la carta
	public int mouseX; // coordenada x donde se encuentra el mouse (cuando el mouse esta sobre la carta)
	public int mouseY; // coordenada y donde se encuentra el mouse (cuando el mouse esta sobre la carta)
	public int enPila; /* indica donde se encuentra la carta 1=repartidor esq sup der, 2=espacio
	muestra, 3a6=posiciones finales, 7a13=pilas de la segunda fila */
	public boolean arrastrandoHijas;
	public Carta(String imCarta, String imgRev, boolean rev, int p, int c, int n, int x, int y){
		palo=p;
		color=c;
		num=n;
		esReverso=rev;
		try{
			imgReverso = ImageIO.read(getClass().getResource(imgRev));
			imgCarta = ImageIO.read(getClass().getResource(imCarta));
		}
		catch(Exception ex){
			System.exit(0);
		}
		cartaHija=null;
		esqX=x;
		esqY=y;
		arrastrandoHijas=false;
	}
	public void paint(Graphics g){
		setSize(75,98);
		if(esReverso){
			g.drawImage(imgReverso,0,0,75,98,this);
			return;
		}
		g.drawImage(imgCarta,1,1,73,96,this);
		g.setColor(Color.BLACK);
		if(arrastrandoHijas){
			Carta aux=cartaHija;
			int y=10;
			while(aux!=null){
				g.drawLine(1,y-1,74,y-1);
				g.drawImage(aux.imgCarta,1,y,73,96-y,this);
				aux=aux.cartaHija;
				y+=10;
			}
		}
		g.drawRect(0,0,74,97);
	}
	public String toString(){
		return ""+num+" de "+(palo==1?"Corazones":palo==2?"Treboles":palo==3?"Diamantes":"Picas");
		//+" enpila "+enPila+" coordenadas ("+esqX+","+esqY+")";
	}
}
