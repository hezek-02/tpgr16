package excepciones;

@SuppressWarnings("serial")
public class PostulacionYaExisteException extends Exception {
	public PostulacionYaExisteException(String str) {
		super(str);
	}
}
