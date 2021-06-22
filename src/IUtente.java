
public interface IUtente {
	public void signing_in(String mail, String username, String password, String data_nascita, char sesso, String cell);
	public void write(IMessaggio msg);
	public void receive(IMessaggio msg);
}
