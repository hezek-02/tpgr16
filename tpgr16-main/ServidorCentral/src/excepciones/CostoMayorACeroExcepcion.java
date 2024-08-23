package excepciones;

@SuppressWarnings("serial")
public class CostoMayorACeroExcepcion extends Exception{
	public CostoMayorACeroExcepcion(String str){
		super(str);
	}
}
