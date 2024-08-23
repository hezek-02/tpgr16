package excepciones;

@SuppressWarnings("serial")
public class TipoPubNoExisteException extends Exception{
	public TipoPubNoExisteException(String str){
		super(str);
	}
}
