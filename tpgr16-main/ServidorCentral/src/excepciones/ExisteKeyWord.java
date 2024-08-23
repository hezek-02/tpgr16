package excepciones;

@SuppressWarnings("serial")
public class ExisteKeyWord extends Exception{
	public ExisteKeyWord(String str){
		super(str);
	}
}
