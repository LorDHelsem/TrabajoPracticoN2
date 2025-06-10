package juego;

import java.util.Map;

public class App {
	public static void main(String[] args) {
		IngredienteBasico i1 = new IngredienteBasico("Tomate");
		IngredienteBasico i2 = new IngredienteBasico("Lechuga");
		IngredienteBasico i3 = new IngredienteBasico("Cebolla");
		IngredienteBasico i4 = new IngredienteBasico("Pollo");
		IngredienteBasico i5 = new IngredienteBasico("Arroz");

		Receta ensaladaReceta = new Receta("Ensalada", 25.5, Map.entry(i1, 1), Map.entry(i2, 1), Map.entry(i3, 1));
		IngredienteIntermedio ensaladaIngrediente = new IngredienteIntermedio("Ensalada", ensaladaReceta);

		Receta platoComunReceta = new Receta("Arroz con Pollo", 25.5, Map.entry(ensaladaIngrediente, 1),
				Map.entry(i4, 1), Map.entry(i5, 1));
		IngredienteIntermedio platoComunIngrediente = new IngredienteIntermedio("Arroz con Pollo y con Ensalada",
				platoComunReceta);

		System.out.println(platoComunIngrediente.getNombre());
		System.out.println(platoComunIngrediente.getRecetaCompleta());

	}
}
