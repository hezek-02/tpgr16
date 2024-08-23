package excepciones;

@SuppressWarnings("serial")
public class NoPoseePostulacionesException extends Exception {
	public NoPoseePostulacionesException(String str) {
		super(str);
	}
}
