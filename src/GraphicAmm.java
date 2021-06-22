import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class GraphicAmm {

	private JFrame frmAmministratore, jframe;
	private JTextField txtId;
	private JTextField txtNome;
	private JTextField txtCognome;
	private JButton button1, button2, button3;
	private Amministratore A;
	private GestioneFile f=new GestioneFile();
	Mediator twitter=Twitter.getIstanza();
	
	public GraphicAmm() {
		initialize();
	}

	private void initialize() {
		frmAmministratore = new JFrame();
		frmAmministratore.setResizable(false);
		frmAmministratore.setTitle("AMMINISTRATORE");
		frmAmministratore.setBounds(100, 100, 369, 218);
		frmAmministratore.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAmministratore.getContentPane().setLayout(null);
		
		frmAmministratore.setPreferredSize(new Dimension(400,180));
		frmAmministratore.pack();
		frmAmministratore.setVisible(true);
		
		txtId = new JTextField();
		txtId.setText("ID");
		txtId.setBounds(10, 11, 214, 20);
		frmAmministratore.getContentPane().add(txtId);
		txtId.setColumns(10);
		
		txtNome = new JTextField();
		txtNome.setText("Nome");
		txtNome.setBounds(10, 42, 214, 20);
		frmAmministratore.getContentPane().add(txtNome);
		txtNome.setColumns(10);
		
		txtCognome = new JTextField();
		txtCognome.setText("Cognome");
		txtCognome.setBounds(10, 73, 214, 20);
		frmAmministratore.getContentPane().add(txtCognome);
		txtCognome.setColumns(10);
		
		JButton enterbutton = new JButton("Entra");
		enterbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controlloAmm();	
			}
		});
		enterbutton.setBounds(10, 104, 89, 23);
		frmAmministratore.getContentPane().add(enterbutton);
	}
	
	public void addListener() {
		button1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JConsole console = new JConsole();
				 console.setEditable(false);

				  //Collegamento del System.out alla JConsole
				  System.setOut(console.getPrintStream());  //metodi che sostanzialmente ci permettono di dirottare il System.out sull'output implementato 
				  System.setErr(console.getPrintStream());

				  //Interfaccia grafica
				  JFrame frame = new JFrame("Elenco utenti in base ai messaggi ricevuti e inviati");
				  console.setBorder(new TitledBorder("Console"));
				  frame.getContentPane().add(new JScrollPane(console));
				  frame.setSize(500,400);
				  frame.setVisible(true);
				jframe.dispose();
				A.show_user(twitter);
	
			}
		});

		button2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JConsole console = new JConsole();
				 console.setEditable(false);

				  //Collegamento del System.out alla JConsole
				  System.setOut(console.getPrintStream());
				  System.setErr(console.getPrintStream());

				  //Interfaccia grafica
				  JFrame frame = new JFrame("Messaggi categorizzati in base agli hashtag");
				  console.setBorder(new TitledBorder("Console"));
				  frame.getContentPane().add(new JScrollPane(console));
				  frame.setSize(500,400);
				  frame.setVisible(true);
				jframe.dispose();
				f.caricaListaMessaggi(twitter);
				A.show_message(twitter);
			}
		});
		
		button3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jframe.dispose();
				JFrame frame=new JFrame();
				frame.setResizable(false);
				JLabel label=new JLabel("Cerca parola: ");
				JTextField textfield=new JTextField();
				JButton bottone=new JButton("CERCA");
				
				frame.setBounds(100, 100, 369, 218);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.getContentPane().setLayout(null);
				
				frame.setPreferredSize(new Dimension(410,140));
				frame.pack();
				frame.setVisible(true);
				
				label.setBounds(10, 11, 214, 20);
				frame.getContentPane().add(label);
				
				textfield.setBounds(10, 42, 214, 20);
				frame.getContentPane().add(textfield);
				
				bottone.setBounds(10, 73, 145, 20);
				frame.getContentPane().add(bottone);
				
				bottone.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JConsole console = new JConsole();
						 console.setEditable(false);

						  //Collegamento del System.out alla JConsole
						  System.setOut(console.getPrintStream());
						  System.setErr(console.getPrintStream());

						  //Interfaccia grafica
						  JFrame fframe = new JFrame("Tutti i messaggi che contengono la parola ricercata");
						  console.setBorder(new TitledBorder("Console"));
						  fframe.getContentPane().add(new JScrollPane(console));
						  fframe.setSize(500,400);
						  fframe.setVisible(true);
						frame.dispose();
						f.caricaListaMessaggi(twitter);
						String testo=textfield.getText();
						A.view(twitter, testo);
					}
				});
				
				frame.pack();
				frame.setVisible(true);
			}
		});
	}
	
	public void controlloAmm() {
		String Id=txtId.getText();
		String nome=txtNome.getText();
		String cognome=txtCognome.getText();

		try {
			FileReader reader=new FileReader("C:\\Users\\aleci\\OneDrive\\Desktop\\Università\\Programmazione\\PROG3\\Esercizi\\ProgettoProg3\\Amministratori.txt");
			BufferedReader bufreader=new BufferedReader(reader);
			String line=bufreader.readLine();
			while((line=bufreader.readLine()) != null) {
				StringTokenizer str=new StringTokenizer(line);
				String id=str.nextToken();
				String name=str.nextToken();
				String surname=str.nextToken();
				
				String i=id;
				String n=name;
				String s=surname;
				
				if(i.equals(Id) && n.equals(nome) && s.equals(cognome)) {
					frmAmministratore.dispose();
					jframe = new JFrame();
					jframe.setResizable(false);
					jframe.setBounds(100, 100, 369, 218);
					jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					jframe.getContentPane().setLayout(null);
					
					jframe.setPreferredSize(new Dimension(580,140));
					jframe.pack();
					jframe.setVisible(true);
					
					button1 = new JButton();
					button1.setText("Mostra elenco utenti in base a messaggi ricevuti o inviati");
					button1.setBounds(10, 11, 400, 20);
					jframe.getContentPane().add(button1);
					
					button2 = new JButton("Visualizza i messaggi categorizzati in base agli hashtag");
					button2.setBounds(10, 42, 400, 20);
					jframe.getContentPane().add(button2);
					
					button3 = new JButton("Data una parola, visualizza tutti i messaggi dei diversi utenti che contengono quella parola");
					button3.setBounds(10, 73, 540, 20);
					jframe.getContentPane().add(button3);
					
					A=new Amministratore(nome, cognome, Id);
					addListener();
					
				}else {
					JFrame newframe=new JFrame();
					JLabel l=new JLabel("ERRORE !!! Credenziali errate");
					newframe.getContentPane().add(l, BorderLayout.CENTER);
					newframe.getContentPane().setBackground(Color.RED);
					
					newframe.setPreferredSize(new Dimension(250,110));
					newframe.pack();
					newframe.setVisible(true);
				}
				break;
			}
			bufreader.close();
		}catch(IOException ecc) {
			ecc.printStackTrace();
			}
	}
}
