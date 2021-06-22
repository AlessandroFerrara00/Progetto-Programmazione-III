import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import javax.swing.JTextArea;

public class JConsole extends JTextArea {   //classe JConsole fa da interfaccia con la console di eclipse in modo da stampare i System.out in un'interfaccia grafica. Sostanzialmente prende in input una stringa(quella del System.out), e tale stringa la trasforma in un qualcosa che sia adattabile a JConsole

	private static final long serialVersionUID = 1L;
	private PrintStream printStream;

	  public JConsole() {
	    printStream = new PrintStream(new ConsolePrintStream());
	  }

	  public PrintStream getPrintStream() {
	    return printStream;
	  }

	  //L' output stream definito da noi
	  private class ConsolePrintStream extends ByteArrayOutputStream {
	    public synchronized void write(byte[] b, int off, int len) {  //ridefinizione del metodo write della superclasse ByteArrayOutputStream
	    	//ad ogni flusso di byte che viene scritto sullo stream lo aggiungiamo alla JTextArea tramite il metodo append(). Quindi poi passiamo un'istanza di questa classe al PrintStream (nel costruttore), e forniamo un riferimento di esso esternamente tramite il metodo getPrintStream(). Questa classe è stata modellata in modo tale da essere collegata direttamente ad un qualsiasi PrintStream oltre al System.out, cioè è possibile collagarla ad un qualsiasi output di dati.
	      setCaretPosition(getDocument().getLength());
	      String str = new String(b);
	      append(str.substring(off, len));
	    }
	  }
}
