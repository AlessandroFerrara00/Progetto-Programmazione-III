
public class Messaggio implements IMessaggio {
	private String msg;
	private String hashtag;
	private Modalit‡Messaggio m;
	
	public Messaggio(String msg)    //Costruttore che ci permette di settare il messaggio da mandare
	{
		this.msg=msg;
	}
	
	public void setHashtag(String hashtag) {
		this.hashtag=hashtag;
	}
	
	public void setModalit‡(Modalit‡Messaggio m)
	{
		this.m=m;
		modalit‡();
	}
	
	public void modalit‡()
	{
		this.m.modalit‡();
	}
	
	public String getMessaggio()
	{
		return msg;
	}
	
	public String getHashtag()
	{
		return hashtag;
	}
	
	@Override
	public String toString()    //metodo che mi permette di stampare il contenuto del messaggio quando si cerca di stampare la lista di messaggi
	{
		return this.hashtag+ " " +this.msg;
	}

}
