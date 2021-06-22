import java.util.ArrayList;
import java.util.List;

public class Utente implements IUtente {
	private String nome, cognome, password, username, data_nascita, mail, cell;
	private char sesso;
	private Mediator twitter=Twitter.getIstanza();
	private List<Utente> seguiti=new ArrayList<>();
	private List<Utente> followers=new ArrayList<>();
	
	
	public Utente(Mediator twitter, String nome, String cognome)
	{
		this.twitter=twitter;
		this.nome=nome;
		this.cognome=cognome;
	}
	
	public void signing_in(String mail, String username, String password, String data_nascita, char sesso, String cell)
	{
		this.mail=mail;
		this.username=username;
		this.password=password;
		this.data_nascita=data_nascita;
		this.sesso=sesso;
		this.cell=cell;
	}
	
	public void write(IMessaggio msg) 
	{
		System.out.println(this.username+ " ha postato il seguente messaggio: " +msg.getHashtag()+ " " +msg.getMessaggio());
		twitter.sendmessage(msg, this);
	}
	
	public void receive(IMessaggio msg)
	{
		System.out.println(this.username+ " ha ricevuto il seguente messaggio: " +msg.getHashtag()+ " " +msg.getMessaggio());
	}
	
	public void addFollower(Utente follower)     //metodo che mi permette di aggiungere/seguire altri utenti
	{
		this.seguiti.add(follower);
	}
	
	public void incrementoFollower(Utente u)   //metodo che permette di incrementare il numero di follower di un dato utente nel momento in cui esso è seguito da un altro utente
	{
		this.followers.add(u);
	}
	
	@Override
	public String toString()    //metodo che serve per stampare nome e cognome degli utenti quando si effettua il System.out della lista
	{
		return this.nome+ " " +this.cognome;
	}
	
	public List<Utente> getListaFollowers()
	{
		return this.followers;
	}
	
	public List<Utente> getListaSeguiti()
	{
		return this.seguiti;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public String getNome()
	{
		return nome;
	}
	
	public String getCognome()
	{
		return cognome;
	}
	
	public String getEmail()
	{
		return mail;
	}
	
	public String getData_Nascita()
	{
		return data_nascita;
	}
	
	public String getPd()
	{
		return password;
	}
	
	public char getSesso()
	{
		return sesso;
	}
	
	public String getNumero()
	{
		return cell;
	}
}
