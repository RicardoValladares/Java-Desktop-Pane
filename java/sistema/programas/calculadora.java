package sistema.programas;
import javax.swing.*;
import javax.script.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
public class calculadora extends JInternalFrame implements KeyListener, ActionListener{
    /*declaramos los objetos*/
    private JTextField pantalla;
    private JButton cero, uno, dos, tres, cuatro, cinco, seis, siete, ocho, nueve;
    private JButton parentesis1, parentesis2;
    private JButton reiniciar, borrar;
    private JButton entre, por, menos, mas;
    private JButton punto, igual;
    /*creamos el formulario*/
    public calculadora(String[] args){
        super("Calculadora", true, true, false, true);
        setLayout(null);
        setSize(290, 290);
        setDefaultCloseOperation(JInternalFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        /*creamos los objetos*/
        pantalla = new JTextField(5);
        siete = new JButton ("7");      ocho = new JButton   ("8");     nueve = new JButton  ("9");     borrar = new JButton("<-");      reiniciar = new JButton ("C");
        cuatro = new JButton("4");      cinco = new JButton  ("5");     seis = new JButton   ("6");     entre = new JButton ("/");      por = new JButton       ("*");
        uno = new JButton   ("1");      dos = new JButton    ("2");     tres = new JButton   ("3");     menos = new JButton ("-");      mas = new JButton       ("+");
        cero = new JButton  ("0");  parentesis1 = new JButton("("); parentesis2 = new JButton(")");     punto = new JButton (".");      igual = new JButton     ("=");
        /*agregamos tooltips*/
        borrar.setToolTipText("Borrar");
        reiniciar.setToolTipText("Reiniciar");
        entre.setToolTipText("Division");
        por.setToolTipText("Multiplicacion");
        menos.setToolTipText("Resta");
        mas.setToolTipText("Suma");
        igual.setToolTipText("Resultado");
        /*agregamos los objetos al formulario*/
        add(pantalla);
        add(siete);     add(ocho);          add(nueve);         add(borrar);    add(reiniciar);
        add(cuatro);    add(cinco);         add(seis);          add(entre);     add(por);
        add(uno);       add(dos);           add(tres);          add(menos);     add(mas);
        add(cero);      add(parentesis1);   add(parentesis2);   add(punto);     add(igual);
        /*posicionamos y dimesionamos los objetos*/
        pantalla.setBounds(5, 5, 270, 25);
        cero.setBounds(5, 200, 50, 50);
        uno.setBounds(5, 145, 50, 50);
        dos.setBounds(60, 145, 50, 50);
        tres.setBounds(115, 145, 50, 50);
        cuatro.setBounds(5, 90, 50, 50);
        cinco.setBounds(60, 90, 50, 50);
        seis.setBounds(115, 90, 50, 50);
        siete.setBounds(5, 35, 50, 50);
        ocho.setBounds(60, 35, 50, 50);
        nueve.setBounds(115, 35, 50, 50);
        parentesis1.setBounds(60, 200, 50, 50);
        parentesis2.setBounds(115, 200, 50, 50);
        borrar.setBounds(170, 35, 50, 50);
        reiniciar.setBounds(225, 35, 50, 50);
        entre.setBounds(170, 90, 50, 50);
        por.setBounds(225, 90, 50, 50);
        menos.setBounds(170, 145, 50, 50);
        mas.setBounds(225, 145, 50, 50);
        punto.setBounds(170, 200, 50, 50);
        igual.setBounds(225, 200, 50, 50);
        /*agregamos accionamiento a los botones*/
        cero.addActionListener(this);
        uno.addActionListener(this);
        dos.addActionListener(this);
        tres.addActionListener(this);
        cuatro.addActionListener(this);
        cinco.addActionListener(this);
        seis.addActionListener(this);
        siete.addActionListener(this);
        ocho.addActionListener(this);
        nueve.addActionListener(this);
        parentesis1.addActionListener(this);
        parentesis2.addActionListener(this);
        borrar.addActionListener(this);
        reiniciar.addActionListener(this);
        entre.addActionListener(this);
        por.addActionListener(this);
        menos.addActionListener(this);
        mas.addActionListener(this);
        punto.addActionListener(this);
        igual.addActionListener(this);
        /*cambiamos el alineado de la pantalla*/
        pantalla.setHorizontalAlignment(SwingConstants.RIGHT);
        /*detectamos si presiona teclas*/
        pantalla.addKeyListener(this);
        /*si mandan instrucciones*/
        if(args.length>0){
            String operacion="";
            for(int i=0; i<args.length; i++)
                operacion=operacion+args[i];
            pantalla.setText(String.valueOf(operacion));
        }
    }
    /*metodo que se activa al presionar un boton*/
    public void actionPerformed(ActionEvent a){
        if(a.getSource()==cero){pantalla.setText(String.valueOf(pantalla.getText() + "0"));}
        if(a.getSource()==uno){pantalla.setText(String.valueOf(pantalla.getText() + "1"));}
        if(a.getSource()==dos){pantalla.setText(String.valueOf(pantalla.getText() + "2"));}
        if(a.getSource()==tres){pantalla.setText(String.valueOf(pantalla.getText() + "3"));}
        if(a.getSource()==cuatro){pantalla.setText(String.valueOf(pantalla.getText() + "4"));}
        if(a.getSource()==cinco){pantalla.setText(String.valueOf(pantalla.getText() + "5"));}
        if(a.getSource()==seis){pantalla.setText(String.valueOf(pantalla.getText() + "6"));}
        if(a.getSource()==siete){pantalla.setText(String.valueOf(pantalla.getText() + "7"));}
        if(a.getSource()==ocho){pantalla.setText(String.valueOf(pantalla.getText() + "8"));}
        if(a.getSource()==nueve){pantalla.setText(String.valueOf(pantalla.getText() + "9"));}
        if(a.getSource()==parentesis1){pantalla.setText(String.valueOf(pantalla.getText() + "("));}
        if(a.getSource()==parentesis2){pantalla.setText(String.valueOf(pantalla.getText() + ")"));}
        if(a.getSource()==borrar){if(pantalla.getText().length()!=0){pantalla.setText(pantalla.getText().substring(0, pantalla.getText().length()-1));}}
        if(a.getSource()==reiniciar){pantalla.setText(String.valueOf(""));}
        if(a.getSource()==entre){pantalla.setText(String.valueOf(pantalla.getText() + "/"));}
        if(a.getSource()==por){pantalla.setText(String.valueOf(pantalla.getText() + "*"));}
        if(a.getSource()==menos){pantalla.setText(String.valueOf(pantalla.getText() + "-"));}
        if(a.getSource()==mas){pantalla.setText(String.valueOf(pantalla.getText() + "+"));}
        if(a.getSource()==punto){pantalla.setText(String.valueOf(pantalla.getText() + "."));}
        if(a.getSource()==igual){operar();}
        pantalla.requestFocus();
    }
    /*metodo de tecla digitada*/
    public void keyTyped(KeyEvent e){}
    /*metodo de tecla presionada*/
    public void keyPressed(KeyEvent e){if(e.getKeyCode()==10){operar();}}
    /*metodo de tecla finalizacion de presionar*/
    public void keyReleased(KeyEvent e){}
    /*metodo de ejecucion*/
    public void operar(){
        try{
            ScriptEngineManager mgr = new ScriptEngineManager();
            ScriptEngine scriptEngine = mgr.getEngineByName("JavaScript");
            String expression = pantalla.getText();
            pantalla.setText(String.valueOf(scriptEngine.eval(expression)));
        }catch(Exception ex){pantalla.setText(String.valueOf("Operacion no Valida"));}
    }
}
