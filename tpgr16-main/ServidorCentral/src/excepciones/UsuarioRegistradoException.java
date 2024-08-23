package excepciones;

@SuppressWarnings("serial")
public class UsuarioRegistradoException extends Exception{
	public UsuarioRegistradoException(String str) {
		super(str);
	}
}
