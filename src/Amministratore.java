import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Amministratore {
	private String ID, nome, cognome;
	int numUtenti, numPost, numRicevuti;
	GestioneFile f=new GestioneFile();
	
	public Amministratore(String nome, String cognome, String ID)
	{
		this.nome=nome;
		this.cognome=cognome;
		this.ID=ID;
	}
	
	public void show_user(Mediator twitter)  //mostra elenco utenti in base al numero di messaggi ricevuti o inviati
	{
		f.caricaListaUtenti(twitter);
			for(Utente u:twitter.getListaUtenti()) {
				try {
					FileReader reader=new FileReader("C:\\Users\\aleci\\OneDrive\\Desktop\\Università\\Programmazione\\PROG3\\Esercizi\\ProgettoProg3\\"+u.getUsername()+"_Post.txt");
					BufferedReader bufreader=new BufferedReader(reader);
					String line= bufreader.readLine();
					numPost=Integer.parseInt(line);      
					
					try {
						FileReader read=new FileReader("C:\\Users\\aleci\\OneDrive\\Desktop\\Università\\Programmazione\\PROG3\\Esercizi\\ProgettoProg3\\"+u.getUsername()+"_Ricevuti.txt");
						BufferedReader bufread=new BufferedReader(read);
						String l= bufread.readLine();
						numRicevuti=Integer.parseInt(l);    
						
						int total=numPost + numRicevuti;
						System.out.println("L'utente "+u.getUsername()+ " è stato reso partecipe in " +total+ " messaggi");
						System.out.println("Messaggi inviati: " +numPost+ "\nMessaggi ricevuti: " +numRicevuti);
						
						bufread.close();
					}catch(IOException eccezione) {
						eccezione.printStackTrace();
					}
					
					bufreader.close();
				}catch(IOException eccezione) {
					eccezione.printStackTrace();
				}
			}
	}
	
	public void show_message(Mediator twitter)  //visualizza i messaggi divisi in categorie in base agli hashtag
	{
		int i=0;
		List<IMessaggio> m=new ArrayList<>();   //Lista di appoggio
		IMessaggio u=twitter.getListaMessaggi().get(i);
		for(IMessaggio msg:twitter.getListaMessaggi()) {
			m.add(msg);             //una volta caricata la lista originale, carico tutti i messaggi nella lista di appoggio
		}
		int size=m.size();          //mi segno il size della lista di appoggio 
		do {
		System.out.println("\nMessaggi con " +u.getHashtag()+ ":");       //ogni volta u punterà ad un messaggio della lista originale
		for(IMessaggio msg:twitter.getListaMessaggi()) { 
			if(msg.getHashtag().equals(u.getHashtag())) {          //per tutti i messaggi msg che si trovano nella lista originale verifico quali hanno l'hashtag uguale al messaggio u di riferimento
				System.out.println(msg);      //tutti i messaggi che hanno l'hashtag uguale al messaggio u verranno stampati insieme
				m.remove(msg);                //verranno rimossi dalla lista di appoggio i messaggi appena stampati
				size=m.size();
					}
				}
		if(size == 0) {
			m.add(twitter.getListaMessaggi().get(i));       //quando il size di m sarà=0, aggiungo un messaggio random all'interno della lista per evitare l'eccezione
			}
				i++;                                     //ogni volta incremento l'indice i per fare in modo che u punti ad un nuovo messaggio
				u=twitter.getListaMessaggi().get(i);
				if(!u.getHashtag().equals(m.get(0).getHashtag())) {       //se ora u punta ad un messaggio con hashtag uguale al primo messaggio della lista di appoggio allora esso continuerà a puntare al messaggio i-esimo della lista originale
					u=m.get(0);                                        //altrimenti punterà al primo messaggio della lista di appoggio poichè avendo rimosso i messaggi già stampati, è chiaro che nella lista di appoggio si avranno sempre messaggi con hashtag diversi rispetto a quelli già stampati e quindi ogni volta al primo elemento di tale lista si trova un messaggio con hashtag diverso dovuto dallo shift automatico
					}
			} while(size > 0);                                    //ripeto il do-while fino a quando il size è maggiore di 0
		m.clear();                                           //pulisco la lista di appoggio
	}
	
	public void view(Mediator twitter,String parola)   //data una parola, mostra tutti i messaggi dei diversi utenti che contengono quella parola
	{
		for(IMessaggio msg:twitter.getListaMessaggi()) {
			if(msg.getMessaggio().toLowerCase().contains(parola.toLowerCase())) {
				System.out.println(msg);
			}
		} 
	}
	
	public String getNome()
	{
		return nome;
	}
	
	public String getCognome()
	{
		return cognome;
	}
	
	public String getID()
	{
		return ID;
	}
	
	@Override
	public String toString() {
		return this.nome+ " " +this.cognome;
	}
}
