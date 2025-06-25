package juego;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RecetaTests {

	private ParseoJson parser;
	
	@BeforeEach
	public void setUp() {
		parser=new ParseoJson();
		parser.cargarRecetasDesdeJSON("archivosJson/recetas.json");
	}
	
	@Test 
	public void testExisteYEsBasicaLaPizzaDeMuzarella() {
		
		String objeto= "pizza muzzarella";
		
		Ingrediente ingrediente= parser.getIngredienteFinal(objeto);
		
		assertNotNull(ingrediente, "Debe existir un ingrediente con nombre: " + objeto);
		assertFalse(ingrediente.esIngredienteBasico());
	}
	
	@Test
	public void testExisteYEsBasicoLaHarina() {
		String objeto= "harina";
		
		Ingrediente ingrediente = parser.getIngredienteFinal(objeto);

	    assertNotNull(ingrediente, "Debe existir un ingrediente con nombre: " + objeto);
	    assertTrue(ingrediente.esIngredienteBasico());
	}
	
	
	
	
}
