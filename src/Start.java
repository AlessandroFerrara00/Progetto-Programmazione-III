import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Start  {
	
	JButton startUtente;
	JButton startAmministratore;
	JLabel x;
	JFrame jframe;
	
	public Start(){
		
		jframe=new JFrame("SCHERMATA INIZIALE");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //fa si che quando si chiude la finestra, termina anche il programma
		jframe.setResizable(false);    //la dimensione è quella, non vogliamo diminuirla
		
		startUtente=new JButton("UTENTE");
		startAmministratore=new JButton("AMMINISTRATORE");
		x=new JLabel("Seleziona la modalità di accesso: ");
		
		jframe.setBounds(100, 100, 369, 218);
		jframe.getContentPane().setLayout(null);
		
		jframe.setPreferredSize(new Dimension(480,200));
		jframe.pack();
		jframe.setVisible(true);
		
		x.setBounds(10, 11, 214, 20);
		jframe.getContentPane().add(x);
		
		startUtente.setBounds(16, 52, 214, 25);
		jframe.getContentPane().add(startUtente);
		
		startAmministratore.setBounds(16, 90, 214, 25);
		jframe.getContentPane().add(startAmministratore);
		
		addListener();
	}
	
	//La classe Listener in generale ci permette di avere metodi che gestiscono eventi quando si clicca un pulsante
		void addListener()    //aggiungiamo un Listener ad ogni oggetto
		{
			startUtente.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					jframe.dispose();
					new GraphicUtente();
				}
			});

			startAmministratore.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					jframe.dispose();
					new GraphicAmm();
					
				}
			});
		} 

	public static void main(String[] args) {
		new Start();
		}
	}