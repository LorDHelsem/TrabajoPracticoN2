package juego;

import java.util.Map;

public class AppNew {

	public static void main(String[] args) {
		
		RecetaParser parser = new RecetaParser();
        parser.cargarRecetasDesdeJSON("archivosJson/recetas.json");

        Ingrediente resultado = parser.getIngredienteFinal("empanada de carne");

        // Para mostrar al usuario todos los nombres de ingredientes disponibles.
        System.out.println("Lista completa de ingredientes:");
        for (Ingrediente ing : parser.getTodos()) {
        	System.out.println("- " + ing.getNombre());
        }
        
        // Para saber qué recetas tiene el sistema.
        // Para generar un menú dinámico o una interfaz de selección.
        System.out.println("\n\nRecetas cargadas:");
        for (Receta r : parser.getRecetas()) {
        	System.out.println("- " + r.getNombre());
        }
        
        if (resultado instanceof IngredienteIntermedio intermedio) {
        	System.out.println("\nReceta encontrada para: " + resultado);
        } else if (resultado != null) {
        	System.out.println("\nEs un ingrediente básico: " + resultado.getNombre());
        } else {
        	System.out.println("\nNo se encontró el ingrediente: " + resultado);
        }
        
        
        if (resultado instanceof IngredienteIntermedio intermedio) {
            System.out.println("- 1. Obtener receta:");
            System.out.println(intermedio.getReceta());

            System.out.println("- 2. Receta completa:");
            System.out.println(intermedio.getRecetaCompleta());

            System.out.println("- Bonus. Arbol de crafteo:");
            System.out.println(intermedio.getArbolCompleto());
            
            
        } else if (resultado != null) {
            System.out.println("Es un ingrediente basico: " + resultado.getNombre());
        } else {
            System.out.println("No se encontro el ingrediente.");
        }
        
        Inventario inventarioJugador = new Inventario();
        parser.inicializarInventario(inventarioJugador);

        // Mostrar inventario recién creado
        inventarioJugador.mostrarInventario();
        
        inventarioJugador.cargarDesdeArchivoJSON("archivosJson/inventario.json"); // Carga cantidades
        
        // Mostrar inventario recién cargado
        inventarioJugador.mostrarInventarioDisponible();
        
        
        
     // Crear algunos ingredientes de prueba
        IngredienteBasico harina = new IngredienteBasico("Harina");
        IngredienteBasico huevo = new IngredienteBasico("Huevo");
        IngredienteBasico sal = new IngredienteBasico("Sal");
        IngredienteBasico leche = new IngredienteBasico("Leche");

        // Crear inventario e inicializar con cantidades
        Inventario inventario = new Inventario();

        // Simular carga desde RecetaParser (todos en cero)
        inventario.agregarItem(Map.entry(harina, 0));
        inventario.agregarItem(Map.entry(huevo, 3));
        inventario.agregarItem(Map.entry(sal, 0));
        inventario.agregarItem(Map.entry(leche, 2));

        // Mostrar inventario (debe mostrar solo huevo y leche)
        System.out.println("\n\n== Mostrar inventario ==");
        inventario.mostrarInventarioDisponible();

        // Guardar inventario actual (debe guardar solo huevo y leche)
        System.out.println("\n== Guardar inventario ==");
        inventario.guardarInventarioActual();
        
	}
}

