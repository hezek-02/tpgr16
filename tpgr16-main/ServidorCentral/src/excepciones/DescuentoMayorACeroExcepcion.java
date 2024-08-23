package excepciones;

@SuppressWarnings("serial")
public class DescuentoMayorACeroExcepcion extends Exception{
	public DescuentoMayorACeroExcepcion(String str){
		super(str);
	}
}