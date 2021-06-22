import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Twitter implements Mediator {
	private List<Utente> utenti;
	private List<IMessaggio> messaggi=new ArrayList<>();
	private static Twitter twitter;
	
	private Twitter()
	{
		this.utenti=new ArrayList<>();    //inizializzo la lista
	}
	
	public static Twitter getIstanza()
	{
		if(twitter == null) {
			return twitter=new Twitter();
		}else return twitter;
	}
	
	public void addUtente(Utente utente)   //metodo che mi permette di aggiungere un utente alla piattaforma
	{
		this.utenti.add(utente);
	}
	
	public void addMessaggi(IMessaggio msg)
	{
		this.messaggi.add(msg);
	}
	
	public List<IMessaggio> getListaMessaggi()
	{
		return this.messaggi;
	}
	
	public void sendmessage(IMessaggio msg, Utente utente)       //metodo che mi permette di notificare soltanto i follower(seguaci) di un dato utente che dovranno ricevere il messaggio da lui postato
	{
			Iterator<Utente> i=utente.getListaFollowers().iterator();
			while(i.hasNext()) {
				i.next().receive(msg);
			}
	}
	
	public List<Utente> getListaUtenti()
	{
		return this.utenti;
	}
}
