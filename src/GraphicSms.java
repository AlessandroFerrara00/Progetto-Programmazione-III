import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.TextArea;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.awt.event.ActionEvent;
import java.awt.TextField;

public class GraphicSms {

	private JFrame frmSms;
	private JTextField txtImmettiNumeroCellulare;
	private TextArea smsTextArea;
	private int numUtenti;
	Mediator twitter=Twitter.getIstanza();
	private GestioneFile f=new GestioneFile();
	private JLabel lblNewLabel_1;
	private TextField textField;

	public GraphicSms() {
		initialize();
	}

	private void initialize() {
		frmSms = new JFrame();
		frmSms.setTitle("SMS");
		frmSms.setBounds(100, 100, 409, 250);
		frmSms.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmSms.setResizable(false);
		frmSms.getContentPane().setLayout(null);

		frmSms.setPreferredSize(new Dimension(460,220));
		frmSms.pack();
		frmSms.setVisible(true);
		
		JButton buttoninvio = new JButton("Invia Messaggio");
		buttoninvio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
					String telefono=txtImmettiNumeroCellulare.getText();
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
						String t=cell;
						if(t.equals(telefono)) {
						
						Utente utente=new Utente(twitter, nome, cognome);
						utente.signing_in(mail, username, password, data_nascita, sesso, telefono);
						isbreakNeeded=true;
						
						if(smsTextArea.getText().length() > 140) {
							JFrame newframe=new JFrame();
							JLabel l=new JLabel("ERRORE!!! Superato il limite di caratteri");
							newframe.getContentPane().add(l, BorderLayout.CENTER);
							newframe.getContentPane().setBackground(Color.RED);
							
							newframe.setPreferredSize(new Dimension(250,110));
							newframe.pack();
							newframe.setVisible(true);
						}else {			
						f.aggiornaListaFollower(utente);
						String h=lblNewLabel_1.getText();
						String field=textField.getText();
						String hashtag=h+field;
						String testomsg=smsTextArea.getText();
						IMessaggio msg=new Messaggio(testomsg);
						msg.setHashtag(hashtag);
						
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
						  JFrame fframe = new JFrame("Messaggi SMS");
						  console.setBorder(new TitledBorder("Console"));
						  fframe.getContentPane().add(new JScrollPane(console));
						  fframe.setSize(500,400);
						  fframe.setVisible(true);
						
						utente.write(msg);
						Messaggio m=(Messaggio)msg;
						f.numPost(utente);
						m.setModalità(new SMS());
						f.writeFile(utente, msg);
						f.writeMessages(msg);
						for(Utente utenti:utente.getListaFollowers()) {
							f.numRicevuti(utenti);
									}
								}
						}
					}
					bufreader.close();
					}
					if(isbreakNeeded==false) {
						JFrame newframe=new JFrame();
						JLabel l=new JLabel("ERRORE !!! Numero cellulare errato");
						newframe.getContentPane().add(l, BorderLayout.CENTER);
						newframe.getContentPane().setBackground(Color.RED);
						
						newframe.setPreferredSize(new Dimension(250,110));
						newframe.pack();
						newframe.setVisible(true);
					}
				}catch(IOException eccezione) {
					eccezione.printStackTrace();
				}
			}
		});
		buttoninvio.setForeground(Color.BLACK);
		buttoninvio.setBackground(Color.WHITE);
		buttoninvio.setBounds(289, 150, 109, 23);
		frmSms.getContentPane().add(buttoninvio);
		
		smsTextArea = new TextArea();
		smsTextArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
		smsTextArea.setBackground(Color.WHITE);
		smsTextArea.setForeground(Color.BLACK);
		smsTextArea.setText("Scrivi...");
		smsTextArea.setBounds(10, 106, 269, 65);
		frmSms.getContentPane().add(smsTextArea);
		
		JLabel lblNewLabel = new JLabel("Da:");
		lblNewLabel.setBounds(10, 23, 46, 14);
		frmSms.getContentPane().add(lblNewLabel);
		
		txtImmettiNumeroCellulare = new JTextField();
		txtImmettiNumeroCellulare.setText("Immetti numero cellulare...");
		txtImmettiNumeroCellulare.setBounds(31, 20, 163, 20);
		frmSms.getContentPane().add(txtImmettiNumeroCellulare);
		txtImmettiNumeroCellulare.setColumns(10);
		
		lblNewLabel_1 = new JLabel("#");
		lblNewLabel_1.setBounds(10, 88, 8, 14);
		frmSms.getContentPane().add(lblNewLabel_1);
		
		textField = new TextField();
		textField.setBounds(21, 80, 173, 22);
		frmSms.getContentPane().add(textField);
	}
}
