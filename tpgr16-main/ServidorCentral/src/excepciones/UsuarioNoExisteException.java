package excepciones;

@SuppressWarnings("serial")
public class UsuarioNoExisteException extends Exception{
	public UsuarioNoExisteException(String str){
		super(str);
	}
}
