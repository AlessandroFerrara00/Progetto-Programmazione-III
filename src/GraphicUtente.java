import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

public class GraphicUtente {
	private JFrame frmUtente, finestra, registrazione, log_in;    //il Frame è una finestra con una barra indicante il titolo
	private JButton sign_in, registrati, login, enter, homepage, posta, sms, accedi;
	private JTextField email, name, surname, nickname, Dn, sex, numero;
	private JPasswordField pd;
	private String nome, cognome, password, username, data_nascita, mail, cell;
	private char sesso;
	private final int margine=5;
	private int numUtenti;
	Mediator twitter=Twitter.getIstanza();
	GestioneFile f=new GestioneFile();
	Utente utente;
	private File file=new File("Utenti.txt");
	private ArrayList<String> dati=new ArrayList<String>();    //struttura dati di appoggio per fare i vari controlli per l'immissione dei dati relativi ad una registrazione
	
	public GraphicUtente() {
		frmUtente=new JFrame("UTENTE");
		frmUtente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmUtente.setResizable(false);
		accedi=new JButton("Accedi a Twitter");
		posta=new JButton("Vai alla posta");
		sms=new JButton("Digita un SMS");
		
		frmUtente.setBounds(100, 100, 379, 248);
		frmUtente.getContentPane().setLayout(null);
		
		frmUtente.setPreferredSize(new Dimension(400,170));
		frmUtente.pack();
		frmUtente.setVisible(true);
		
		accedi.setBounds(15, 21, 214, 20);
		frmUtente.getContentPane().add(accedi);
		
		posta.setBounds(15, 52, 214, 20);
		frmUtente.getContentPane().add(posta);
		
		sms.setBounds(15, 83, 214, 20);
		frmUtente.getContentPane().add(sms);

		listener();
	}
		
		
	void listener() {
		accedi.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				frmUtente.dispose();
				finestra=new JFrame("TWITTER");
				finestra.setResizable(false);
				finestra.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // ci permette di chiudere il programma nel momento in cui chiudo la mia finestra
				sign_in=new JButton("Registrati");
				login=new JButton("Effettua il login");
				email=new JTextField("Inserisci mail...");
				name=new JTextField("Inserisci nome...");
				surname=new JTextField("Inserisci cognome...");
				nickname=new JTextField("Inserisci username...");
				pd=new JPasswordField("Password");
				Dn=new JTextField("Inserisci data di nascita...");
				sex=new JTextField("Sesso...");
				numero=new JTextField("Telefono...");
				
				finestra.setBounds(100, 100, 379, 248);
				finestra.getContentPane().setLayout(null);
				
				finestra.setPreferredSize(new Dimension(400,170));
				finestra.pack();
				finestra.setVisible(true);
				
				sign_in.setBounds(15, 31, 214, 25);
				finestra.getContentPane().add(sign_in);
				
				login.setBounds(15, 72, 214, 25);
				finestra.getContentPane().add(login);
				
				addListener();	
			}
		});
		
		posta.addMouseListener(new MouseAdapter()  {
			@Override
			public void mouseClicked(MouseEvent e) {
				frmUtente.dispose();
				new GraphicPosta();
			}
		});
		
		sms.addMouseListener(new MouseAdapter()  {
			@Override
			public void mouseClicked(MouseEvent e) {
				frmUtente.dispose();
				new GraphicSms();
			}
		});
	}
	


	void addListener()    //aggiungiamo un Listener ad ogni oggetto
	{
	sign_in.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			finestra.dispose();
			registrazione=new JFrame("REGISTRAZIONE");
			registrazione.setResizable(false);
			registrazione.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			registrati=new JButton("Registrati");
			
			registrazione.add(email);
			registrazione.add(name);
			registrazione.add(surname);
			registrazione.add(Dn);
			registrazione.add(sex);
			registrazione.add(numero);
			registrazione.add(nickname);
			registrazione.add(pd);
			registrazione.add(registrati);
			SpringLayout layout=new SpringLayout();
			registrazione.setLayout(layout);
			
			layout.putConstraint(SpringLayout.NORTH, email, margine, SpringLayout.NORTH, registrazione);
			layout.putConstraint(SpringLayout.WEST, email, margine, SpringLayout.WEST, registrazione);
			layout.putConstraint(SpringLayout.EAST, email, -margine, SpringLayout.EAST, registrazione);
			
			layout.putConstraint(SpringLayout.NORTH, name, margine, SpringLayout.SOUTH, email);
			layout.putConstraint(SpringLayout.WEST, name, margine, SpringLayout.WEST, registrazione);
			layout.putConstraint(SpringLayout.EAST, name, -margine, SpringLayout.EAST, registrazione);
			
			layout.putConstraint(SpringLayout.NORTH, surname, margine, SpringLayout.SOUTH, name);
			layout.putConstraint(SpringLayout.WEST, surname, margine, SpringLayout.WEST, registrazione);
			layout.putConstraint(SpringLayout.EAST, surname, -margine, SpringLayout.EAST, registrazione);
			
			layout.putConstraint(SpringLayout.NORTH, Dn, margine, SpringLayout.SOUTH, surname);
			layout.putConstraint(SpringLayout.WEST, Dn, margine, SpringLayout.WEST, registrazione);
			layout.putConstraint(SpringLayout.EAST, Dn, -margine, SpringLayout.EAST, registrazione);
			
			layout.putConstraint(SpringLayout.NORTH, sex, margine, SpringLayout.SOUTH, Dn);
			layout.putConstraint(SpringLayout.WEST, sex, margine, SpringLayout.WEST, registrazione);
			layout.putConstraint(SpringLayout.EAST, sex, -margine, SpringLayout.EAST, registrazione);
			
			layout.putConstraint(SpringLayout.NORTH, numero, margine, SpringLayout.SOUTH, sex);
			layout.putConstraint(SpringLayout.WEST, numero, margine, SpringLayout.WEST, registrazione);
			layout.putConstraint(SpringLayout.EAST, numero, -margine, SpringLayout.EAST, registrazione);
			
			layout.putConstraint(SpringLayout.NORTH, nickname, margine, SpringLayout.SOUTH, numero);
			layout.putConstraint(SpringLayout.WEST, nickname, margine, SpringLayout.WEST, registrazione);
			layout.putConstraint(SpringLayout.EAST, nickname, -margine, SpringLayout.EAST, registrazione);
			
			layout.putConstraint(SpringLayout.NORTH, pd, margine, SpringLayout.SOUTH, nickname);
			layout.putConstraint(SpringLayout.WEST, pd, margine, SpringLayout.WEST, registrazione);
			layout.putConstraint(SpringLayout.EAST, pd, -margine, SpringLayout.EAST, registrazione);
			
			layout.putConstraint(SpringLayout.NORTH, registrati, margine, SpringLayout.SOUTH, pd);
			layout.putConstraint(SpringLayout.WEST, registrati, margine, SpringLayout.WEST, registrazione);

			registrarsi();
			registrazione.setPreferredSize(new Dimension(500,275));
			registrazione.pack();
			registrazione.setVisible(true);
			}
		});
	
	login.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			finestra.dispose();
			log_in=new JFrame("LOGIN");
			log_in.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			enter=new JButton("Login");
			
			log_in.setResizable(false);
			log_in.setBounds(100, 100, 369, 218);
			log_in.getContentPane().setLayout(null);
			
			log_in.setPreferredSize(new Dimension(400,180));
			log_in.pack();
			log_in.setVisible(true);
			
			nickname.setBounds(10, 11, 214, 20);
			log_in.getContentPane().add(nickname);
			nickname.setColumns(10);
			
			pd.setBounds(10, 42, 214, 20);
			log_in.getContentPane().add(pd);
			pd.setColumns(10);
			
			enter.setBounds(10, 73, 115, 22);
			log_in.getContentPane().add(enter);
			
			accedi();
			}
		});
	}
	
	
	public void registrarsi() {
		
	registrati.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent e) {
		registrati.getActionListeners();
		nome=name.getText();   //associo alla stringa nome il valore che metto nel campo name(jtextfield) all'interno dell'interfaccia
		cognome=surname.getText();
		mail=email.getText();
		username=nickname.getText();
		password=String.valueOf(pd.getPassword());
		data_nascita=Dn.getText(); 
		sesso=sex.getText().charAt(0);
		cell=numero.getText();
		
		controllo();   //richiamo del metodo che mi permette di effettuare un controllo per i dati inseriti nel momento della registrazione di un utente
		
		if(sesso == 'M' || sesso == 'F') {                            //controllo sul campo "sesso"
			registrati.addMouseListener(new MouseAdapter(){
				@Override
				public void mouseClicked(MouseEvent e) {
					registrazione.dispose();
					JFrame frame=new JFrame();
					JLabel x=new JLabel("Registrazione effettuata !!!");
					homepage=new JButton("Vai alla Homepage");
					frame.add(x, BorderLayout.CENTER);
					frame.add(homepage, BorderLayout.SOUTH);
					utente=new Utente(twitter, nome, cognome);
					utente.signing_in(mail, username, password, data_nascita, sesso, cell);
					f.creaFile(utente);
					
					homepage.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent e) {
						frame.dispose();
						new HomePage(utente);    //gli passo l'utente ora in login
							}
						});
					frame.setPreferredSize(new Dimension(250,120));
					frame.pack();
					frame.setVisible(true);
					
					try {
						FileWriter fw=new FileWriter(file,true);    //si scrivono gli utenti registrati all'interno del file
						
						fw.write("\nNome: " +nome);
						fw.write("\nCognome: " +cognome);
						fw.write("\nMail: " +mail);
						fw.write("\nUsername: " +username);
						fw.write("\nPassword: " +password);
						fw.write("\nData di nascita: " +data_nascita);
						fw.write("\nTelefono: " +cell);
						fw.write("\nSesso: " +sesso+ "\n");
						
						fw.flush();
						fw.close();
						f.filenumUtenti();    //richiamo del metodo che ci permette di aggiornare il file relativo al numero degli utenti
						f.scrivifile(nome, cognome, mail, username, password, data_nascita, cell, sesso);    //richiamo del metodo che ci permette di creare un file per ogni nuovo utente registrato
					}catch (IOException eccezione) {
						eccezione.printStackTrace();
						}
					}
				});
			}else {
			JFrame error=new JFrame();  //finestra di errore che si apre poichè il campo "sesso" non è stato riempito correttamente
			
			error.setPreferredSize(new Dimension(310,120));
			JLabel x=new JLabel("ERRORE !!!");
			JLabel y=new JLabel("Il campo 'sesso' non è valido");
			error.getContentPane().setBackground(Color.RED);
			
			error.add(x, BorderLayout.CENTER);
			error.add(y, BorderLayout.SOUTH);
			
			error.pack();
			error.setVisible(true);
				}    
			}
		});
	}
	
	
	void accedi() {   //metodo che ci permette di controllare in fase di login se i dati inseriti sono corretti
		enter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				enter.getActionListeners();
				username=nickname.getText();
				password=String.valueOf(pd.getPassword());
				boolean isbreakNeeded=false;   //variabile booleana che serve per uscire dal ciclo successivamente
				
				//verificare username e password all'interno dei file per vedere se un utente è registrato
				//se è registrato allora sara effettuato il login altrimenti si chiuderà la finestra perchè i dati inseriti sono sbagliati
				
				try {
					FileReader reader=new FileReader("C:\\Users\\aleci\\OneDrive\\Desktop\\Università\\Programmazione\\PROG3\\Esercizi\\ProgettoProg3\\Numero Utenti.txt");
					BufferedReader bufreader=new BufferedReader(reader);
					String line= bufreader.readLine();
					numUtenti=Integer.parseInt(line);       //mi vado a prendere il numero degli Utenti dal file "Numero Utenti" per fare l'iterazione successivamente
					
					bufreader.close();
				}catch(IOException ecc) {
					ecc.printStackTrace();
				}
				
					try {
						for(int i=1; i<=numUtenti; i++) {
						FileReader reader=new FileReader("C:\\Users\\aleci\\OneDrive\\Desktop\\Università\\Programmazione\\PROG3\\Esercizi\\ProgettoProg3\\Utente"+i+".txt");
						BufferedReader bufreader=new BufferedReader(reader);
						String line=bufreader.readLine();
						while((line = bufreader.readLine()) != null) {
							StringTokenizer str=new StringTokenizer(line);  
							nome=str.nextToken();
							cognome=str.nextToken();
							mail=str.nextToken();
							String user=str.nextToken();
							String passw=str.nextToken();
							data_nascita=str.nextToken();
							cell=str.nextToken();
							sesso=str.nextToken().charAt(0);
							String u=user;     //faccio tale assegnamento in seguito all'utilizzo di StringTokenizer, infatti la variabile user contiene la stringa letta da file relativa all'username dell'i-esimo utente
							String p=passw;	   //se non si effettua ciò e quindi non si va ad utilizzare StringTokenizer, il metodo equals non funzionerebbe in quanto line assumerebbe la stringa relativa a tutta la riga presente nel file

							if(u.equals(username) && p.equals(password)) {
								utente=new Utente(twitter, nome, cognome);
								utente.signing_in(mail, username, password, data_nascita, sesso, cell);
								log_in.dispose();
								new HomePage(utente);
								isbreakNeeded=true;
								}
							}
						bufreader.close();
						}
						if(isbreakNeeded==false) {
							JFrame newframe=new JFrame();
							JLabel l=new JLabel("ERRORE !!! Credenziali errate");
							newframe.getContentPane().add(l, BorderLayout.CENTER);
							newframe.getContentPane().setBackground(Color.RED);
							
							newframe.setPreferredSize(new Dimension(250,110));
							newframe.pack();
							newframe.setVisible(true);
						}
					}catch(IOException E) {
						E.printStackTrace();
						}
					}
				});
			}
	
		public void controllo() {     //metodo che controlla che in fase di registrazione non siano immessi dati di username, password o mail già esistenti
			try {
				FileReader reader=new FileReader(file);
				BufferedReader bufreader=new BufferedReader(reader);
				String line= bufreader.readLine();
				while((line = bufreader.readLine())!= null) {
					String[] utenti=(line.split(";"));
					dati.add(utenti[0]);
				}
				bufreader.close();
			}catch(IOException eccezione) {
				eccezione.printStackTrace();
				JOptionPane.showMessageDialog(null, "Errore file");
			}
			
			for(String u:dati) {
				if(u.contains(username)) {
					registrazione.dispose();
					JFrame frame=new JFrame();
					JLabel label=new JLabel("ERRORE !!!");
					JLabel jlabel=new JLabel("L'username è già stato utilizzato");
					frame.add(label, BorderLayout.CENTER);
					frame.add(jlabel, BorderLayout.SOUTH);
					
					frame.setPreferredSize(new Dimension(230,140));
					frame.pack();
					frame.setVisible(true);
					frame.getContentPane().setBackground(Color.RED);
					throw new IllegalArgumentException();
				}
			}
			
			for(String u:dati) {
				if(u.contains(password)) {
					registrazione.dispose();
					JFrame frame=new JFrame();
					JLabel label=new JLabel("ERRORE !!!");
					JLabel jlabel=new JLabel("La password è già stata utilizzata");
					frame.add(label, BorderLayout.CENTER);
					frame.add(jlabel, BorderLayout.SOUTH);
					
					frame.setPreferredSize(new Dimension(230,140));
					frame.pack();
					frame.setVisible(true);
					frame.getContentPane().setBackground(Color.RED);
					throw new IllegalArgumentException();
				}
			}
			
			for(String u:dati) {
				if(u.contains(mail)) {
					registrazione.dispose();
					JFrame frame=new JFrame();
					JLabel label=new JLabel("ERRORE !!!");
					JLabel jlabel=new JLabel("L'indirizzo mail è già stato utilizzato");
					frame.add(label, BorderLayout.CENTER);
					frame.add(jlabel, BorderLayout.SOUTH);
					
					frame.setPreferredSize(new Dimension(230,140));
					frame.pack();
					frame.setVisible(true);
					frame.getContentPane().setBackground(Color.RED);
					throw new IllegalArgumentException();
				}
			}
		}
}
	

