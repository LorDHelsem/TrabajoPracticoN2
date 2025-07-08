package jugadorTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.AbstractMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import catalizador.Catalizador;
import catalizador.CatalizadorFuego;
import catalizador.CatalizadorMasaMadre;
import ingrediente.Ingrediente;
import ingrediente.IngredienteBasico;
import ingrediente.IngredienteIntermedio;
import inventario.Inventario;
import jugador.Jugador;
import parseoJson.ParseoJson;
import receta.Receta;
import registro.Registro;


class JugadorTest {

    private Jugador jugador;
    private Inventario inventario;
    private ParseoJson parser;
    private Registro registro;

    @BeforeEach
    public void Inicializacion()
    {
    	parser = new ParseoJson();
 
        // Cargamos recetas e inventario de prueba
        parser.cargarRecetasDesdeJSON("archivosJSON/recetas.json");
        inventario = new Inventario();
        parser.inicializarInventario(inventario);
        parser.cargarInventarioDesdeArchivoJSON(inventario, "archivosJSON/inventario.json");
        
        jugador = new Jugador("mel", inventario, parser, new Registro());

    }
    
    

    @Test
    public void testCraftearSimpleSinCatalizadorExitoso() {
    	
    	Receta receta = parser.getRecetaPorNombre("salsa de tomate");

        boolean resultado = jugador.craftearSimple(receta); /// Crafteo pan
        assertTrue(resultado);
        
        assertTrue(inventario.tieneIngrediente(new IngredienteIntermedio("salsa de tomate", receta), 1)); // reviso si pan se guardó en el inventario

    }

    @Test
    public void testCraftearSimpleSinCatalizadorFallaPorFaltante() {

    	Receta receta = parser.getRecetaPorNombre("pizza muzzarella");

        boolean resultado = jugador.craftearSimple(receta); /// Crafteo pan
        assertFalse(resultado);
    }
    @Test
    public void testCraftearSimpleSinCatalizadorFallaPorRecetaInexistente() {
    	
    	// armo una receta que no esta en mi lista de recetas
    	IngredienteBasico pato = new IngredienteBasico("pato");
		Receta receta = new Receta("pato al horno", "ninguno", 1, 5,
                new AbstractMap.SimpleEntry<>(pato , 1));

		/// no encontró esa receta, le devuelve false
        boolean resultado = jugador.craftearSimple(receta);
        assertFalse(resultado);
    }
    
    @Test
    public void testCraftearSimpleConCatalizadorExitoso() {
        
    	Receta receta = parser.getRecetaPorNombre("huevo frito");
    	Catalizador catalizador = new CatalizadorFuego("fuego");

        boolean resultado = jugador.craftearSimple(receta, catalizador); /// Crafteo pan
        assertTrue(resultado);
        
        assertTrue(inventario.tieneIngrediente(new IngredienteIntermedio("huevo frito", receta), 1));
        assertEquals(3, inventario.getObjetos().get(catalizador).intValue()); // Catalizador consumido
    } 
    
    
    @Test
    public void testCraftearSimpleConCatalizadorSinCantidad() {
        
    	Receta receta = parser.getRecetaPorNombre("pan");
    	Catalizador catalizador = new CatalizadorMasaMadre("masa madre");

        boolean resultado = jugador.craftearSimple(receta, catalizador); /// Crafteo pan
        assertTrue(resultado, "Debería craftear el pan");
        
        assertTrue(inventario.tieneIngrediente(new IngredienteIntermedio("pan", receta), 1));
        
        assertEquals(0, inventario.getObjetos().get(catalizador).intValue()); // Catalizador insuficiente
    }
    
    
    
    @Test
    public void testCraftearSimpleConCatalizadorIncorrecto() {
    	Receta receta = parser.getRecetaPorNombre("pan");
    	Catalizador catalizador = new CatalizadorMasaMadre("agua");

        boolean resultado = jugador.craftearSimple(receta, catalizador); /// Crafteo pan
        assertFalse(resultado, "No debería craftear el pan, catalizador incorrecto");
    }
    
    @Test
    public void testCraftearSimpleConCatalizadorFallaPorFaltante() {
        
    	Receta receta = parser.getRecetaPorNombre("pizza muzzarella");
    	Catalizador catalizador = new CatalizadorFuego("fuego");

        boolean resultado = jugador.craftearSimple(receta, catalizador); /// Crafteo pizza
       
        assertEquals(4, inventario.getObjetos().get(catalizador).intValue()); // Catalizador NO consumido
        assertFalse(resultado, "No deberia poder craftear pizza muzzarella");/// aca da en realidad false
    } 
    
    
   
    @Test
    public void testCraftearYConsumirTodosLosHuevos() {
    	Receta receta = parser.getRecetaPorNombre("omelette");
    	Catalizador catalizador = new CatalizadorMasaMadre("fuego");

        // Mostrar inventario antes del crafteo
        System.out.println("Inventario antes del crafteo:");
        inventario.getObjetos().forEach((item, cantidad) -> 
            System.out.println(item.getNombre() + ": " + cantidad)
        );

        // Craftear omelette
        boolean resultado = jugador.craftearSimple(receta);
        assertTrue(resultado, "Se esperaba que el crafteo fuera exitoso");

        Ingrediente huevo = new IngredienteBasico("huevo");
     
        // Verificar que el omelette se agregó correctamente
        assertTrue(inventario.tieneIngrediente(new IngredienteIntermedio("omelette", receta), 2), "El inventario debe contener 1 unidad más de omelette");

        // Verificar que los huevos ya no esté presente o sea 0
        
        assertEquals(0, inventario.getCantDeItem(huevo), "Se consumieron todos los huevos");
       

        // Mostrar inventario después del crafteo
        System.out.println("Inventario después del crafteo:");
        inventario.getObjetos().forEach((item, cantidad) -> 
            System.out.println(item.getNombre() + ": " + cantidad)
        );
    }
    
}



