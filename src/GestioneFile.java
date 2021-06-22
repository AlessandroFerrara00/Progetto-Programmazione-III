import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class GestioneFile {
	int numUtenti, cont, post, ricevuti, numfollower, numseguiti;
	Mediator twitter=Twitter.getIstanza();
	private ArrayList<String> seguiti=new ArrayList<String>();
	private ArrayList<String> follower=new ArrayList<String>();
	
	public void filenumUtenti() {
		File FF=new File("Numero Utenti.txt");
		try {
			FileReader reader=new FileReader(FF);
			BufferedReader bufreader=new BufferedReader(reader);
			String line= bufreader.readLine();
			numUtenti=Integer.parseInt(line);    //mi prendo il numero degli utenti dal file corrispondente
			
			bufreader.close();
		}catch(IOException ecc) {
			ecc.printStackTrace();
		}
		cont=numUtenti+1;   //imposto la variabile cont al valore di numUtenti+1 poichè tale metodo verrà richiamato quando viene registrato un nuovo utente e quindi si dovrà incrementare il numero degli utenti
		
		try {
			FileWriter fw=new FileWriter(FF);
			
			fw.write(""+cont);   //aggiorno il file "Numero Utenti"
			
			fw.flush();
			fw.close();
		}catch(IOException eccezione) {
			eccezione.printStackTrace();
		}
	}
	
	public void scrivifile(String nome, String cognome, String mail, String username, String password, String data_nascita, String cell, char sesso) {
		int i=cont;
		String files="Utente"+i+".txt";     //si crea un file per ogni nuovo Utente registrato e ciò lo si riesce a fare grazie all'assegnazione precedente di i=cont
		try {
			FileWriter ff=new FileWriter(files); 
			
			ff.write("\n" +nome);
			ff.write(" " +cognome);
			ff.write(" " +mail);
			ff.write(" " +username);
			ff.write(" " +password);
			ff.write(" " +data_nascita);
			ff.write(" " +cell);
			ff.write(" " +sesso+ "\n");
			
			ff.flush();
			ff.close();
		}catch(IOException ecc) {
			ecc.printStackTrace();
			}
		}
	
	public void creaFile(Utente utente) {
		try {
			FileWriter writer=new FileWriter(utente.getUsername()+"_numFollowers.txt");
			writer.write("0");
			writer.flush();
			writer.close();
			
			FileWriter fw=new FileWriter(utente.getUsername()+"_numSeguiti.txt");
			fw.write("0");
			fw.flush();
			fw.close();
			
			FileWriter fr=new FileWriter(utente.getUsername()+"_Post.txt");
			fr.write("0");
			fr.flush();
			fr.close();
			
			FileWriter w=new FileWriter(utente.getUsername()+"_Ricevuti.txt");
			w.write("0");
			w.flush();
			w.close();
			
			FileWriter fwriter=new FileWriter("Lista Seguiti_"+utente.getUsername()+".txt");
			fwriter.write("null");
			fwriter.flush();
			fwriter.close();
			
			FileWriter fwrite=new FileWriter("Lista Follower_"+utente.getUsername()+".txt");
			fwrite.write("null");
			fwrite.flush();
			fwrite.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeFile(Utente utente, IMessaggio msg) {
		String file="Messaggi.txt";
		try {
			FileWriter writer=new FileWriter(file, true);
			
			writer.write("\n"+utente.getUsername()+ " ha scritto: \n");
			writer.write(msg.getHashtag()+ " " +msg.getMessaggio()+ "\n");
			
			writer.flush();
			writer.close();
		}catch(IOException eccezione) {
			eccezione.printStackTrace();
		}
	}
	
	public void writeMessages(IMessaggio msg) {
		String file="Lista Messaggi.txt";
		String mess="";
		try {
			FileWriter writer=new FileWriter(file, true);
			
			StringTokenizer str=new StringTokenizer(msg.getMessaggio());  //dividere il messaggio perchè se il messaggio veniva scritto andando a capo c'erano problemi nella memorizzazione all'interno del file
			do{
				String m=str.nextToken();  
				mess=mess+" "+m;
			}while(str.hasMoreTokens());
			writer.write("\n"+msg.getHashtag()+"|" +mess);
			
			writer.flush();
			writer.close();
		}catch(IOException eccezione) {
			eccezione.printStackTrace();
		}
	}
	
	public void caricaListaMessaggi(Mediator twitter) {
		try {
			FileReader reader=new FileReader("C:\\Users\\aleci\\OneDrive\\Desktop\\Università\\Programmazione\\PROG3\\Esercizi\\ProgettoProg3\\Lista Messaggi.txt");
			BufferedReader bufreader=new BufferedReader(reader);
			String line= bufreader.readLine();
			while((line = bufreader.readLine()) != null){
				StringTokenizer str=new StringTokenizer(line, "|");
				String hashtag=str.nextToken();
				String messaggio=str.nextToken();
				
				IMessaggio msg=new Messaggio(messaggio);
				msg.setHashtag(hashtag);
				twitter.addMessaggi(msg);
			}
			bufreader.close();
		}catch(IOException ecc) {
			ecc.printStackTrace();
		}
	}
	
	public void caricaListaUtenti(Mediator twitter) {
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
					
					Utente utente=new Utente(twitter, nome, cognome);
					utente.signing_in(mail, username, password, data_nascita, sesso, cell);
					twitter.addUtente(utente);
					}
				bufreader.close();
			}
		}catch(IOException eccezione) {
			eccezione.printStackTrace();
		}
	}
	
	public void numPost(Utente utente) {    //metodo relativo al numero di post (messaggi) scritti da un dato utente
		try {
			FileReader reader=new FileReader("C:\\Users\\aleci\\OneDrive\\Desktop\\Università\\Programmazione\\PROG3\\Esercizi\\ProgettoProg3\\"+utente.getUsername()+"_Post.txt");
			BufferedReader bufreader=new BufferedReader(reader);
			String line= bufreader.readLine();
			post=Integer.parseInt(line);    //mi prendo il numero dei post dal file corrispondente
			
			bufreader.close();
		}catch(IOException ecc) {
			ecc.printStackTrace();
		}
		cont=post+1;   //la variabile contatore mi consente di aggiornare il numero di post di quell'utente 
		
		try {
			FileWriter fw=new FileWriter("C:\\Users\\aleci\\OneDrive\\Desktop\\Università\\Programmazione\\PROG3\\Esercizi\\ProgettoProg3\\"+utente.getUsername()+"_Post.txt");
			
			fw.write(""+cont);   //aggiorno il file
			
			fw.flush();
			fw.close();
		}catch(IOException eccezione) {
			eccezione.printStackTrace();
		}
	}
	
	public void numRicevuti(Utente utente) {     
		try {
			FileReader reader=new FileReader("C:\\Users\\aleci\\OneDrive\\Desktop\\Università\\Programmazione\\PROG3\\Esercizi\\ProgettoProg3\\"+utente.getUsername()+"_Ricevuti.txt");
			BufferedReader bufreader=new BufferedReader(reader);
			String line= bufreader.readLine();
			ricevuti=Integer.parseInt(line);  
			
			bufreader.close();
		}catch(IOException ecc) {
			ecc.printStackTrace();
		}
		cont=ricevuti+1; 
		
		try {
			FileWriter fw=new FileWriter("C:\\Users\\aleci\\OneDrive\\Desktop\\Università\\Programmazione\\PROG3\\Esercizi\\ProgettoProg3\\"+utente.getUsername()+"_Ricevuti.txt");
			
			fw.write(""+cont);   //aggiorno il file
			
			fw.flush();
			fw.close();
		}catch(IOException eccezione) {
			eccezione.printStackTrace();
		}
	}
	
	public void writeSeguiti(Utente utente, String username) {   //metodo che scrive su file gli username degli utenti seguiti dall'utente passato come parametro
		try {
			FileWriter fw=new FileWriter("C:\\Users\\aleci\\OneDrive\\Desktop\\Università\\Programmazione\\PROG3\\Esercizi\\ProgettoProg3\\Lista Seguiti_"+utente.getUsername()+".txt", true);
			
			fw.write(" "+username); 
			
			fw.flush();
			fw.close();
			
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
				FileReader freader=new FileReader("C:\\Users\\aleci\\OneDrive\\Desktop\\Università\\Programmazione\\PROG3\\Esercizi\\ProgettoProg3\\Utente"+i+".txt");
				BufferedReader breader=new BufferedReader(freader);
				String l=breader.readLine();
				while((l=breader.readLine()) != null) {
					if(l.contains(username)) {     //leggo i file di ogni utente e vedo se è presente l'username scritto nel file
					StringTokenizer str=new StringTokenizer(l);
					String nome=str.nextToken();
					String cognome=str.nextToken();
					String mail=str.nextToken();
					username=str.nextToken();
					String password=str.nextToken();
					String data_nascita=str.nextToken();
					String cell=str.nextToken();
					char sesso=str.nextToken().charAt(0);
					
					Utente u=new Utente(twitter, nome, cognome);
					u.signing_in(mail, username, password, data_nascita, sesso, cell);
					writeFollower(u, utente.getUsername());    //richiamo il metodo che mi consente di scrivere sul file dell'utente seguito per aggiornare la sua lista di follower
							}
						}
					breader.close();
					}
		}catch(IOException ecc) {
			ecc.printStackTrace();
		}
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void aggiornaLista_seguiti(Utente utente) {   //metodo che mi consente di aggiornare la lista dei seguiti relativa all'utente in login
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
			FileReader reader=new FileReader("C:\\Users\\aleci\\OneDrive\\Desktop\\Università\\Programmazione\\PROG3\\Esercizi\\ProgettoProg3\\"+utente.getUsername()+"_numSeguiti.txt");
			BufferedReader bufreader=new BufferedReader(reader);
			String line= bufreader.readLine();
			numseguiti=Integer.parseInt(line);       //mi vado a prendere il numero degli utenti seguiti dall'utente passato come parametro per fare poi successivamente l'iterazione			
			bufreader.close();
		}catch(IOException eccezione) {
			eccezione.printStackTrace();
		}
		
		try {
			for(int j=0; j<numseguiti+1; j++) {    //itero fino al numero dei seguiti (numero preso precedentemente)
			FileReader reader=new FileReader("C:\\Users\\aleci\\OneDrive\\Desktop\\Università\\Programmazione\\PROG3\\Esercizi\\ProgettoProg3\\Lista Seguiti_"+utente.getUsername()+".txt");   //leggere il file della lista seguiti dell'utente che ha effettuato il login per caricare poi la sua lista di seguiti
			BufferedReader bufreader=new BufferedReader(reader);
			String line= bufreader.readLine();
			String []utenti=(line.split(" "));
			seguiti.add(utenti[j]);             //aggiungo nella lista di stringhe seguiti che è una lista di appoggio precedentemente creata tutti gli username degli utenti presenti nel file che rappresenta la lista dei seguiti dell'utente passato come parametro
			
			try {
				for(int i=1; i<=numUtenti; i++) {
				FileReader freader=new FileReader("C:\\Users\\aleci\\OneDrive\\Desktop\\Università\\Programmazione\\PROG3\\Esercizi\\ProgettoProg3\\Utente"+i+".txt");
				BufferedReader breader=new BufferedReader(freader);
				String l=breader.readLine();
				while((l=breader.readLine()) != null) {
					if(l.contains(utenti[j])) {     //leggo i file di ogni utente e se è presente il j-esimo elemento della lista seguiti, vado a utilizzare appunto il Tokenizer che mi permette di dividere ogni token e quindi creare quell'utente per aggiungerlo poi alla lista seguiti dell'utente passato come parametro che sarà l'utente situato in login
					StringTokenizer str=new StringTokenizer(l);
					String nome=str.nextToken();
					String cognome=str.nextToken();
					String mail=str.nextToken();
					String username=str.nextToken();
					String password=str.nextToken();
					String data_nascita=str.nextToken();
					String cell=str.nextToken();
					char sesso=str.nextToken().charAt(0);
					
					Utente u=new Utente(twitter, nome, cognome);
					u.signing_in(mail, username, password, data_nascita, sesso, cell);
					utente.addFollower(u);    //aggiungo l'utente alla lista di utenti seguiti dell'utente in login e passato come parametro
						}
					}
				breader.close();
				bufreader.close();
				}
			}catch(IOException ecc) {
				ecc.printStackTrace();
				}
			}
		}catch(IOException eccezione) {
			eccezione.printStackTrace();
		}
	}
	
	public void numSeguiti(Utente utente) {    //metodo che aggiorna il numenro di seguiti di un dato utente
		try {
			FileReader reader=new FileReader("C:\\Users\\aleci\\OneDrive\\Desktop\\Università\\Programmazione\\PROG3\\Esercizi\\ProgettoProg3\\"+utente.getUsername()+"_numSeguiti.txt");
			BufferedReader bufreader=new BufferedReader(reader);
			String line= bufreader.readLine();
			numseguiti=Integer.parseInt(line);    //mi prendo il numero dei seguiti dal file corrispondente
			
			bufreader.close();
		}catch(IOException ecc) {
			ecc.printStackTrace();
		}
		cont=numseguiti+1;   //la variabile contatore mi consente di aggiornare il numero di seguiti di quell'utente 
		
		try {
			FileWriter fw=new FileWriter("C:\\Users\\aleci\\OneDrive\\Desktop\\Università\\Programmazione\\PROG3\\Esercizi\\ProgettoProg3\\"+utente.getUsername()+"_numSeguiti.txt");
			
			fw.write(""+cont);   //aggiorno il file
			
			fw.flush();
			fw.close();
		}catch(IOException eccezione) {
			eccezione.printStackTrace();
		}
	}
	
	public void numFollowers(Utente utente) {     //metodo che aggiorna il numero di Follower di un dato utente
		try {
			FileReader reader=new FileReader("C:\\Users\\aleci\\OneDrive\\Desktop\\Università\\Programmazione\\PROG3\\Esercizi\\ProgettoProg3\\"+utente.getUsername()+"_numFollowers.txt");
			BufferedReader bufreader=new BufferedReader(reader);
			String line= bufreader.readLine();
			numfollower=Integer.parseInt(line);    //mi prendo il numero dei follower dal file corrispondente
			
			bufreader.close();
		}catch(IOException ecc) {
			ecc.printStackTrace();
		}
		cont=numfollower+1;   //la variabile contatore mi consente di aggiornare il numero di follower di quell'utente 
		
		try {
			FileWriter fw=new FileWriter("C:\\Users\\aleci\\OneDrive\\Desktop\\Università\\Programmazione\\PROG3\\Esercizi\\ProgettoProg3\\"+utente.getUsername()+"_numFollowers.txt");
			
			fw.write(""+cont);   //aggiorno il file
			
			fw.flush();
			fw.close();
		}catch(IOException eccezione) {
			eccezione.printStackTrace();
		}
	}
	
	public void writeFollower(Utente u, String username) {
		try {
			FileWriter fw=new FileWriter("C:\\Users\\aleci\\OneDrive\\Desktop\\Università\\Programmazione\\PROG3\\Esercizi\\ProgettoProg3\\Lista Follower_"+u.getUsername()+".txt", true);
			FileReader reader=new FileReader("C:\\Users\\aleci\\OneDrive\\Desktop\\Università\\Programmazione\\PROG3\\Esercizi\\ProgettoProg3\\Lista Follower_"+u.getUsername()+".txt");
			BufferedReader bufreader=new BufferedReader(reader);
			String line;
			while((line = bufreader.readLine()) != null) {
				if(!line.contains(username)) {
					fw.write(" "+username); 
					numFollowers(u);  //richiamo il metodo per aggiornare il numero di folllower dell'utente appena seguito
				}
			}
			bufreader.close();
			
			fw.flush();
			fw.close();
		}catch(IOException eccezione) {
			eccezione.printStackTrace();
		}
	}
	
	public void aggiornaListaFollower(Utente u) {
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
			FileReader reader=new FileReader("C:\\Users\\aleci\\OneDrive\\Desktop\\Università\\Programmazione\\PROG3\\Esercizi\\ProgettoProg3\\"+u.getUsername()+"_numFollowers.txt");
			BufferedReader bufreader=new BufferedReader(reader);
			String line= bufreader.readLine();
			numfollower=Integer.parseInt(line);       //mi vado a prendere il numero dei follower dall'utente passato come parametro per fare poi successivamente l'iterazione			
			bufreader.close();
		}catch(IOException eccezione) {
			eccezione.printStackTrace();
		}
		
		try {
			for(int j=0; j<numfollower+1; j++) {    //itero fino al numero dei follower (numero preso precedentemente)
			FileReader reader=new FileReader("C:\\Users\\aleci\\OneDrive\\Desktop\\Università\\Programmazione\\PROG3\\Esercizi\\ProgettoProg3\\Lista Follower_"+u.getUsername()+".txt");   //leggere il file della lista seguiti dell'utente che ha effettuato il login per caricare poi la sua lista di seguiti
			BufferedReader bufreader=new BufferedReader(reader);
			String line= bufreader.readLine();
			String []utenti=(line.split(" "));
			follower.add(utenti[j]);           //aggiungo nella lista di stringhe follower che è una lista di appoggio precedentemente creata tutti gli username degli utenti presenti nel file che rappresenta la lista dei follower dell'utente passato come parametro
			
			try {
				for(int i=1; i<=numUtenti; i++) {
				FileReader freader=new FileReader("C:\\Users\\aleci\\OneDrive\\Desktop\\Università\\Programmazione\\PROG3\\Esercizi\\ProgettoProg3\\Utente"+i+".txt");
				BufferedReader breader=new BufferedReader(freader);
				String l=breader.readLine();
				while((l=breader.readLine()) != null) {
					if(l.contains(utenti[j])) {     //leggo i file di ogni utente e se è presente il j-esimo elemento della lista seguiti, vado a utilizzare appunto il Tokenizer che mi permette di dividere ogni token e quindi creare quell'utente per aggiungerlo poi alla lista follower dell'utente passato come parametro che sarà l'utente situato in login
					StringTokenizer str=new StringTokenizer(l);
					String nome=str.nextToken();
					String cognome=str.nextToken();
					String mail=str.nextToken();
					String username=str.nextToken();
					String password=str.nextToken();
					String data_nascita=str.nextToken();
					String cell=str.nextToken();
					char sesso=str.nextToken().charAt(0);
					
					Utente utente=new Utente(twitter, nome, cognome);
					utente.signing_in(mail, username, password, data_nascita, sesso, cell);
					u.incrementoFollower(utente);
						}
					}
				breader.close();
				bufreader.close();
				}
			}catch(IOException ecc) {
				ecc.printStackTrace();
				}
			}
		}catch(IOException eccezione) {
			eccezione.printStackTrace();
		}
	}
}
