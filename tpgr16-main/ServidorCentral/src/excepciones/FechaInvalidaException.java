package excepciones;

@SuppressWarnings("serial")
public class FechaInvalidaException extends Exception{
	public FechaInvalidaException(String str) {
		super(str);
	}
}
