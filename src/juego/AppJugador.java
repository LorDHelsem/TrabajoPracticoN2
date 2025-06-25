package juego;

public class AppJugador {

	public static void main(String[] args) {

		ParseoJson parser = new ParseoJson();
		parser.cargarRecetasDesdeJSON("archivosJson/recetas.json");

		// 1. Obtener la receta de "empanada de carne"
		Receta recetaEmpanada = parser.getRecetaPorNombre("empanada de carne");

		if (recetaEmpanada != null) {
			// 2. Mostrar la receta completa (ingredientes básicos + sub-recetas)
			System.out.println("=== Receta completa de 'empanada de carne' ===");
			System.out.println(recetaEmpanada.getIngredienteCompleto());

			// Opcional: Mostrar el árbol de crafteo con tiempos
			System.out.println("\n=== Árbol de crafteo ===");
			System.out.println(recetaEmpanada.getArbolDeCrafteo());

			// Opcional: Mostrar solo los ingredientes básicos necesarios
			System.out.println("\n=== Ingredientes básicos necesarios ===");
			System.out.println(recetaEmpanada.getIngredientesBasicosComoString());
		} else {
			System.out.println("No se encontró la receta 'empanada de carne'.");
		}
		
		Inventario inventarioJugador= new Inventario();
		parser.inicializarInventario(inventarioJugador);
		parser.cargarInventarioDesdeArchivoJSON(inventarioJugador, "archivosJson/inventario.json");
		
		Jugador jug1= new Jugador("tomas m", inventarioJugador, parser);
		
		if(recetaEmpanada!= null) {
			boolean sePuedoRealizarCrafteo= jug1.craftear(recetaEmpanada);
			if(sePuedoRealizarCrafteo) {
				System.out.println("¡Crafteo exitoso!");
             	inventarioJugador.mostrarInventarioDisponible();
			}else {
				System.out.println("No se pudo craftear.");
			}
		}
		parser.guardarInventarioActual(inventarioJugador);
	}
}

