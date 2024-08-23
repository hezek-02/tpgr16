package test;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import excepciones.ExisteKeyWord;
import excepciones.TipoPubNoExisteException;
import logica.Fabrica;
import logica.datatypes.DtKeyWord;
import logica.interfaces.IOfertaLab;
import logica.manejadores.ManejadorOfertaLaboral;
import logica.manejadores.ManejadorPaqueteTipoPub;
import logica.manejadores.ManejadorTipoPublicacion;
import logica.manejadores.ManejadorUsuarios;

class KeyWordTest {

	private static IOfertaLab interfazOfertaLaboral;
	private static ManejadorOfertaLaboral manejadorOferta;
	
	@BeforeAll
	public static void iniciar() {
		Fabrica fabrica = Fabrica.getInstance();
		interfazOfertaLaboral = fabrica.getIOfertaLaboral();
		manejadorOferta = ManejadorOfertaLaboral.getInstance();
	}
	
	@BeforeEach
	public void limpiar() {
		manejadorOferta.eliminarKeyWords();
	}
	
	@Test
	void testIngresarKeyWordCorrecto() throws ExisteKeyWord {
		String clave = "supercalifragilisticexpialidociousantidisestablishmentarianismfloccinaucinihilipilificationhippopotomonstrosesquipedaliophobia";
		interfazOfertaLaboral.ingresarKeyWord(clave);
		DtKeyWord  keyword = manejadorOferta.obtenerKeyWord(clave).obtenerDtKeyWord();
		assertNotNull(keyword);
	}
	
	@Test
	void testIngresarKeyWordIncorrecto() throws ExisteKeyWord {
		String clave = "nombreGénerico";
		interfazOfertaLaboral.ingresarKeyWord(clave);
		
	    Exception exception = assertThrows(ExisteKeyWord.class, () -> {
	    	interfazOfertaLaboral.ingresarKeyWord(clave);
	    });

	    String expectedMessage = "La KeyWord ya se encuentra registrada";
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
	@Test
	void testObtenerKeywords() throws Exception {
		setupKeywords();
		DtKeyWord[] keywords =  interfazOfertaLaboral.obtenerKeyWords();
		assertNotNull(keywords);
		assertTrue(keywords.length > 0);
	}
	
	void setupKeywords() throws ExisteKeyWord {
		interfazOfertaLaboral.ingresarKeyWord("Developer");
		interfazOfertaLaboral.ingresarKeyWord("Part-Time");
		interfazOfertaLaboral.ingresarKeyWord("Full-Time");
	}
}
	
