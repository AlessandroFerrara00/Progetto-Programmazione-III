import javax.swing.JFrame;
import java.awt.TextField;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Choice;
import java.awt.Dimension;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import java.awt.TextArea;
import java.awt.Label;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;

public class HomePage {

	private JFrame frmHomePage;
	Mediator twitter=Twitter.getIstanza();
	private Utente utente;
	private GestioneFile f=new GestioneFile();
	int numUtenti;

	public HomePage(Utente utente) {
		this.utente=utente;
		frmHomePage = new JFrame();
		frmHomePage.getContentPane().setForeground(Color.BLACK);
		frmHomePage.getContentPane().setBackground(UIManager.getColor("Button.background"));
		frmHomePage.getContentPane().setLayout(null);
		
		frmHomePage.setPreferredSize(new Dimension(630,360));
		frmHomePage.pack();
		frmHomePage.setVisible(true);
		
		TextArea textArea = new TextArea();
		textArea.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		textArea.setBackground(Color.WHITE);
		textArea.setForeground(Color.BLACK);
		textArea.setText("Scrivi tweet...");
		textArea.setBounds(20, 45, 228, 81);
		frmHomePage.getContentPane().add(textArea);
		
		Label label = new Label("Aggiungi utente:");
		label.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		label.setBounds(256, 20, 93, 22);
		frmHomePage.getContentPane().add(label);
		
		Choice choice = new Choice();
		choice.setBounds(354, 20, 109, 20);
		frmHomePage.getContentPane().add(choice);
		f.caricaListaUtenti(twitter);   //carico la lista di Utenti da file
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
			for(int i=1; i<=numUtenti; i++) {
			FileReader reader=new FileReader("C:\\Users\\aleci\\OneDrive\\Desktop\\Università\\Programmazione\\PROG3\\Esercizi\\ProgettoProg3\\Utente"+i+".txt");
			BufferedReader bufreader=new BufferedReader(reader);
			String line=bufreader.readLine();
			while((line=bufreader.readLine()) != null) {
				if(line.contains(this.utente.getUsername())) {
					twitter.getListaUtenti().remove(twitter.getListaUtenti().get(i-1));   //poichè vengono letti tutti i file Utente(i), rimuovo dalla lista di Utenti precedentemente caricata, l'utente di indice i-1 che contiene lo stesso username di chi ha effettuato il login, perchè di fatto è lui che si è loggato
					for(Utente utenti:twitter.getListaUtenti()) {
						choice.addItem(utenti.getUsername());					//tolto dalla lista l'utente che si è loggato, posso aggiungere a choice tutti gli altri utenti presenti in lista e che ora potranno essere aggiungi dall'utente che si trova nella Home page.
								}
							}
						}
				bufreader.close();
					}
			}catch(IOException eccezione) {
				eccezione.printStackTrace();
			} 
		
		JLabel lblNewLabel = new JLabel("#");
		lblNewLabel.setBounds(20, 25, 8, 14);
		frmHomePage.getContentPane().add(lblNewLabel);
		
		TextField textField = new TextField();
		textField.setBounds(30, 20, 150, 22);
		frmHomePage.getContentPane().add(textField);
		frmHomePage.setForeground(Color.RED);
		frmHomePage.setBackground(Color.WHITE);
		frmHomePage.setTitle("HOME PAGE");
		frmHomePage.setBounds(100, 100, 630, 392);
		frmHomePage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
		
		Button button = new Button("Posta tweet");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String h=lblNewLabel.getText();
				String tfield=textField.getText();
				String hashtag=h+tfield;
				String testoMess=textArea.getText();
				IMessaggio msg=new Messaggio(testoMess);
				msg.setHashtag(hashtag);
				Messaggio m=(Messaggio)msg;
				if(textArea.getText().length() > 140) {
					JFrame newframe=new JFrame();
					JLabel l=new JLabel("ERRORE!!! Superato il limite di caratteri");
					newframe.getContentPane().add(l, BorderLayout.CENTER);
					newframe.getContentPane().setBackground(Color.RED);
					
					newframe.setPreferredSize(new Dimension(250,110));
					newframe.pack();
					newframe.setVisible(true);
				}else {
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
					  JFrame fframe = new JFrame("Meccanismo messaggi");
					  console.setBorder(new TitledBorder("Console"));
					  fframe.getContentPane().add(new JScrollPane(console));
					  fframe.setSize(500,400);
					  fframe.setVisible(true);
				utente.write(msg);
				f.numPost(utente);							//setto i post scritti da un determinato utente all'interno del suo file
				m.setModalità(new SitoWeb());				//setto la modalità del messaggio
				f.writeFile(utente, msg);					//richiamare il metodo per aggiornare il contenuto del file contenente i messaggi
				f.writeMessages(msg);
				for(Utente utenti:utente.getListaFollowers()) {
					f.numRicevuti(utenti);
					}
				}
			}
		});
		button.setBounds(20, 132, 109, 22);
		frmHomePage.getContentPane().add(button);
		
		Choice follower = new Choice();
		follower.setBounds(20, 211, 107, 22);
		frmHomePage.getContentPane().add(follower);
		f.aggiornaListaFollower(utente);     //richiamo del metodo che mi aggiorna da file la lista dei follower dell'utente in login
		for(Utente u:utente.getListaFollowers()) {
			follower.addItem(u.getUsername());    //aggiungo nell'interfaccia grafica i follower dell'utente che si trova in login
		}
		
		Choice seguiti = new Choice();
		seguiti.setBounds(208, 211, 107, 22);
		frmHomePage.getContentPane().add(seguiti);
		f.aggiornaLista_seguiti(utente);    //aggiorno la lista dei seguiti dell'utente che ha effettuato il login
		for(Utente u:utente.getListaSeguiti()) {
			seguiti.addItem(u.getUsername());    //aggiungo nell'interfaccia grafica gli utenti seguiti dall'utente che si trova nella Home page
			}
		
		JButton btnNewButton = new JButton("Aggiungi");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					FileReader reader=new FileReader("C:\\Users\\aleci\\OneDrive\\Desktop\\Università\\Programmazione\\PROG3\\Esercizi\\ProgettoProg3\\Lista Seguiti_"+utente.getUsername()+".txt");
					BufferedReader bufreader=new BufferedReader(reader);
					String line;
					while((line=bufreader.readLine()) != null) {
						if(line.contains(choice.getSelectedItem())){    //controllo se all'interno del file della lista dei seguiti dell'utente in login è già presente l'utente che si vuole aggiungere, se è cosi ci sarà un errore
							choice.remove(choice.getSelectedItem());
							JFrame newframe=new JFrame();
							JLabel l=new JLabel("ERRORE !!!, Utente già seguito");
							newframe.getContentPane().add(l, BorderLayout.CENTER);
							newframe.getContentPane().setBackground(Color.RED);
							
							newframe.setPreferredSize(new Dimension(250,110));
							newframe.pack();
							newframe.setVisible(true);
						}else {
							seguiti.add(choice.getSelectedItem());
							f.writeSeguiti(utente, choice.getSelectedItem());
							f.numSeguiti(utente);
 							choice.remove(choice.getSelectedItem());
 							break;
						}
					}
					bufreader.close();
				}catch(IOException ec) {
					ec.printStackTrace();
				} 
			}
		});
		btnNewButton.setBackground(UIManager.getColor("Button.darkShadow"));
		btnNewButton.setBounds(469, 19, 89, 23);
		frmHomePage.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_1 = new JLabel("FOLLOWER");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		lblNewLabel_1.setBounds(36, 190, 68, 14);
		frmHomePage.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("SEGUITI");
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		lblNewLabel_2.setBounds(231, 190, 62, 14);
		frmHomePage.getContentPane().add(lblNewLabel_2);
	}
	
}
