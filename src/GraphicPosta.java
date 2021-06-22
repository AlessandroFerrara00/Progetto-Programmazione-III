import java.awt.Font;
import java.awt.TextArea;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class GraphicPosta {

	private JFrame frmPostaElettronica;
	private final int margine=5;
	private int numUtenti;
	Mediator twitter=Twitter.getIstanza();
	private GestioneFile f=new GestioneFile();
	private JTextField txtInserisciEmail, Textfield;
	private JButton invio;
	private TextArea poEtext;
	private JLabel jlabel;

	public GraphicPosta() {
		initialize();
	}

	private void initialize() {
		frmPostaElettronica = new JFrame();
		frmPostaElettronica.setTitle("POSTA ELETTRONICA");
		frmPostaElettronica.setResizable(false);
		frmPostaElettronica.getContentPane().setForeground(Color.WHITE);
		frmPostaElettronica.setBounds(100, 100, 397, 211);
		frmPostaElettronica.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmPostaElettronica.getContentPane().setLayout(null);
		
		frmPostaElettronica.setPreferredSize(new Dimension(400,240));
		frmPostaElettronica.pack();
		frmPostaElettronica.setVisible(true);
		
		JRadioButton Posta_arrivo = new JRadioButton("Posta in arrivo");
		Posta_arrivo.setSelected(true);
		Posta_arrivo.setBounds(6, 7, 109, 23);
		frmPostaElettronica.getContentPane().add(Posta_arrivo);
		
		JRadioButton bozze = new JRadioButton("Bozze");
		bozze.setBackground(new Color(240, 240, 240));
		bozze.setSelected(true);
		bozze.setBounds(6, 41, 109, 23);
		frmPostaElettronica.getContentPane().add(bozze);
		
		JRadioButton Posta_ind = new JRadioButton("Posta indesiderata");
		Posta_ind.setBounds(6, 78, 120, 23);
		frmPostaElettronica.getContentPane().add(Posta_ind);
		
		JRadioButton Posta_eliminata = new JRadioButton("Posta eliminata");
		Posta_eliminata.setForeground(Color.BLACK);
		Posta_eliminata.setBackground(UIManager.getColor("Button.background"));
		Posta_eliminata.setSelected(true);
		Posta_eliminata.setBounds(6, 114, 109, 23);
		frmPostaElettronica.getContentPane().add(Posta_eliminata);
		
		JButton buttonMess = new JButton("Nuovo Messaggio");
		buttonMess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean isbreakNeeded=false;
				try {
					FileReader reader=new FileReader("C:\\Users\\aleci\\OneDrive\\Desktop\\Università\\Programmazione\\PROG3\\Esercizi\\ProgettoProg3\\Numero Utenti.txt");
					BufferedReader bufreader=new BufferedReader(reader);
					String line= bufreader.readLine();
					numUtenti=Integer.parseInt(line);       //mi vado a prendere il numero degli Utenti dal file "Numero Utenti" per fare l'iterazione successivamente
					
					bufreader.close();
				}catch(IOException eccezione) {
					eccezione.printStackTrace();
				}
				try {
					String email=txtInserisciEmail.getText();
					for(int i=1; i<=numUtenti; i++) {
					FileReader reader=new FileReader("C:\\Users\\aleci\\OneDrive\\Desktop\\Università\\Programmazione\\PROG3\\Esercizi\\ProgettoProg3\\Utente"+i+".txt");
					BufferedReader bufreader=new BufferedReader(reader);
					String line=bufreader.readLine();
					while((line=bufreader.readLine()) != null) {
						StringTokenizer str=new StringTokenizer(line);
						String nome=str.nextToken();
						String cognome=str.nextToken();
						String mail=str.nextToken();
						String username=str.nextToken();
						String password=str.nextToken();
						String data_nascita=str.nextToken();
						String cell=str.nextToken();
						char sesso=str.nextToken().charAt(0);
						String m=mail;
						if(m.equals(email)) {		
							
						frmPostaElettronica.dispose();
						Utente utente=new Utente(twitter, nome, cognome);
						utente.signing_in(email, username, password, data_nascita, sesso, cell);
						isbreakNeeded=true;
						
						JFrame finestramex=new JFrame();
						finestramex.setPreferredSize(new Dimension(610,270));
						finestramex.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						finestramex.setResizable(false);
						jlabel=new JLabel("#");
						Textfield=new JTextField();
						poEtext=new TextArea();
						invio=new JButton("Invia");	
						Textfield.setPreferredSize(new Dimension(165,20));
						finestramex.getContentPane().add(jlabel);
						finestramex.getContentPane().add(Textfield);
						finestramex.getContentPane().add(poEtext);
						finestramex.getContentPane().add(invio);
						SpringLayout layout=new SpringLayout();
						finestramex.getContentPane().setLayout(layout);
						
						poEtext.setFont(new Font("Monospaced", Font.PLAIN, 11));
						poEtext.setBackground(Color.WHITE);
						poEtext.setForeground(Color.BLACK);
						poEtext.setText("Scrivi...");
						
						invio.setForeground(Color.BLACK);
						invio.setBackground(Color.WHITE);
						
						layout.putConstraint(SpringLayout.NORTH, jlabel, margine, SpringLayout.NORTH, finestramex);
						layout.putConstraint(SpringLayout.WEST, jlabel, margine, SpringLayout.WEST, finestramex);
						
						layout.putConstraint(SpringLayout.NORTH, Textfield, margine, SpringLayout.NORTH, finestramex);
						layout.putConstraint(SpringLayout.WEST, Textfield, margine, SpringLayout.EAST, jlabel);
						
						layout.putConstraint(SpringLayout.NORTH, poEtext, margine, SpringLayout.SOUTH, jlabel);
						layout.putConstraint(SpringLayout.WEST, poEtext, margine, SpringLayout.WEST, finestramex);
						layout.putConstraint(SpringLayout.EAST, poEtext, -margine, SpringLayout.EAST, finestramex);
						
						layout.putConstraint(SpringLayout.NORTH, invio, margine, SpringLayout.SOUTH, poEtext);
						layout.putConstraint(SpringLayout.WEST, invio, margine, SpringLayout.WEST, finestramex);
						
						addlistener(utente);
						finestramex.pack();
						finestramex.setVisible(true);
							}
						}
					bufreader.close();
				}
					if(isbreakNeeded==false) {
						JFrame newframe=new JFrame();
						JLabel l=new JLabel("ERRORE !!! Mail non esistente");
						newframe.getContentPane().add(l, BorderLayout.CENTER);
						newframe.getContentPane().setBackground(Color.RED);
						
						newframe.setPreferredSize(new Dimension(250,110));
						newframe.pack();
						newframe.setVisible(true);
					}
			}catch(IOException ecc) {
				ecc.printStackTrace();
				}	
			}
		});
		buttonMess.setBounds(6, 156, 120, 23);
		frmPostaElettronica.getContentPane().add(buttonMess);
		
		txtInserisciEmail = new JTextField();
		txtInserisciEmail.setFont(new Font("Monospaced", Font.PLAIN, 11));
		txtInserisciEmail.setText("Inserisci email...");
		txtInserisciEmail.setBounds(173, 27, 190, 20);
		frmPostaElettronica.getContentPane().add(txtInserisciEmail);
		txtInserisciEmail.setColumns(10);
	}
	
	public void addlistener(Utente utente) {
		invio.addActionListener(new ActionListener()  {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(poEtext.getText().length() > 140) {
					JFrame newframe=new JFrame();
					JLabel l=new JLabel("ERRORE!!! Superato il limite di caratteri");
					newframe.getContentPane().add(l, BorderLayout.CENTER);
					newframe.getContentPane().setBackground(Color.RED);
					
					newframe.setPreferredSize(new Dimension(250,110));
					newframe.pack();
					newframe.setVisible(true);
				}else {
				f.aggiornaListaFollower(utente);
				String h=jlabel.getText();
				String text=Textfield.getText();
				String hashtag=h+text;
				String testomsg=poEtext.getText();
				IMessaggio msg=new Messaggio(testomsg);
				msg.setHashtag(hashtag);
				Messaggio m=(Messaggio)msg;
				
				JFrame frame=new JFrame();
				JLabel label=new JLabel("Messaggio scritto con successo");
				frame.getContentPane().add(label, BorderLayout.CENTER);
				frame.getContentPane().setBackground(Color.GREEN);
				
				frame.setPreferredSize(new Dimension(250,110));
				frame.pack();
				frame.setVisible(true);
				
				JConsole console = new JConsole();
				 console.setEditable(false);

				  //Collegamento del System.out alla JConsole
				  System.setOut(console.getPrintStream());
				  System.setErr(console.getPrintStream());

				  //Interfaccia grafica
				  JFrame fframe = new JFrame("Messaggi posta");
				  console.setBorder(new TitledBorder("Console"));
				  fframe.getContentPane().add(new JScrollPane(console));
				  fframe.setSize(500,400);
				  fframe.setVisible(true);

				utente.write(msg);
				f.numPost(utente);
				m.setModalità(new PostaElettronica());
				f.writeFile(utente, msg);
				f.writeMessages(msg);
				for(Utente utenti:utente.getListaFollowers()) {
					f.numRicevuti(utenti);
					}
				}
			}
		});
	}
}
