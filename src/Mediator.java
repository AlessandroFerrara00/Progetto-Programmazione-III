import java.util.List;

public interface Mediator {
	public void addUtente(Utente utente);
	public void sendmessage(IMessaggio msg, Utente utente);
	public List<Utente> getListaUtenti();
	public void addMessaggi(IMessaggio msg);
	public List<IMessaggio> getListaMessaggi();
}
