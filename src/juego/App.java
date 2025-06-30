package juego;

//import java.time.LocalDateTime;
import java.util.Map;

public class App {
	public static void main(String[] args) {
		IngredienteBasico i1 = new IngredienteBasico("Tomate");
		IngredienteBasico i2 = new IngredienteBasico("Lechuga");

		IngredienteBasico i3 = new IngredienteBasico("Cebolla");

		IngredienteBasico i4 = new IngredienteBasico("Pollo");
		IngredienteBasico i5 = new IngredienteBasico("Arroz");

		Receta ensaladaReceta = new Receta("Ensalada de tomate y de lechuga", "Fuego", 1, 25.5, Map.entry(i1, 2),
				Map.entry(i2, 3));
		IngredienteIntermedio ensaladaIngrediente = new IngredienteIntermedio("Ensalada de tomate y de lechuga",
				ensaladaReceta);

		Receta ensaladaFinalReceta = new Receta("Ensalada de tomate y de lechuga con cebolla", "Fuego", 2, 5,
				Map.entry(ensaladaIngrediente, 1), Map.entry(i3, 1));
		IngredienteIntermedio ensaladaFinalIngrediente = new IngredienteIntermedio("Ensalada con cebolla",
				ensaladaFinalReceta);

		Receta platoComunReceta = new Receta("Arroz con Pollo", "Fuego", 1, 25.5, Map.entry(i4, 2), Map.entry(i5, 1));
		IngredienteIntermedio platoComunIngrediente = new IngredienteIntermedio("Arroz con Pollo", platoComunReceta);

		Receta platoComunFinalReceta = new Receta("Arroz con Pollo y con Ensalada", "Fuego", 1, 25.5,
				Map.entry(platoComunIngrediente, 5), Map.entry(ensaladaFinalIngrediente, 2));
		IngredienteIntermedio platoComunFinalIngrediente = new IngredienteIntermedio("Arroz con Pollo y con Ensalada",
				platoComunFinalReceta);

		System.out
				.println("- 1. Obtener receta (solo el primer nivel de la receta, sin descomponer los ingredientes):");
		System.out.println(platoComunFinalIngrediente.getReceta());

		System.out.println(
				"- 2. Obtener Receta completa (mostrar solo los elementos basicos necesarios, con sus cantidades totales):");
		// System.out.println(platoComunFinalIngrediente.getTiempoDePreparacion());
		System.out.println(platoComunFinalIngrediente.getRecetaCompleta());

		System.out.println("- Bonus. Obtener Arbol de crafteo (Cantidad y tiempo de cada ingrediente):");
		System.out.println(platoComunFinalIngrediente.getArbolCompleto());

	}
}
