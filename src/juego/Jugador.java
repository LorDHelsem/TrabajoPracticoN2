package juego;

import java.util.AbstractMap;
import java.util.Map;

public class Jugador {

	private String nombre;
	private Inventario inventario;
	private ParseoJson parser;

	public Jugador(String nombre, Inventario inventario, ParseoJson parser) {
		this.nombre = nombre;
		this.inventario = inventario;
		this.parser=parser;
	}

	public boolean craftear(Receta receta) {

		if (!puedeCraftear(receta)) {
			return false;
		}

		for (Map.Entry<Ingrediente, Integer> entry : receta.getIngredientes2().entrySet()) {
			Ingrediente ingrediente = entry.getKey();
			int cantidad = entry.getValue();
			inventario.consumirIngrediente(ingrediente, cantidad);
		}

		Ingrediente productoFinal = parser.getIngredienteFinal(receta.getNombre());
		inventario.agregarItem(new AbstractMap.SimpleEntry<>(productoFinal, 1));

		System.out.println("¡Crafteo exitoso"+this.nombre+"! Se produjo: " + receta.getNombre());
		return true;
	}

	private boolean puedeCraftear(Receta receta) {
		for (Map.Entry<Ingrediente, Integer> entry : receta.getIngredientes2().entrySet()) {
			Ingrediente ingrediente = entry.getKey();
			int cantidad = entry.getValue();

			if (ingrediente.esIngredienteBasico()) {
				if (!inventario.tieneIngrediente(ingrediente, cantidad)) {
					System.out.println(
							"Falta ingrediente básico: " + ingrediente.getNombre() + " (Necesitas " + cantidad + ")");
					return false;
				}
			} else {
				IngredienteIntermedio intermedio = (IngredienteIntermedio) ingrediente;
				Receta recetaIntermedia = intermedio.getReceta();

				if (!inventario.tieneIngrediente(intermedio, cantidad)) {
					System.out.println(
							"Intentando craftear " + cantidad + " unidad(es) de " + intermedio.getNombre() + "...");
					if (!puedeCraftear(recetaIntermedia)) {
						return false;
					}
					// Craftear el intermedio y agregarlo al inventario
					for (Map.Entry<Ingrediente, Integer> subEntry : recetaIntermedia.getIngredientes2().entrySet()) {
						inventario.consumirIngrediente(subEntry.getKey(), subEntry.getValue() * cantidad);
					}
					inventario.agregarItem(new AbstractMap.SimpleEntry<>(intermedio, cantidad));
				}
			}
		}
		return true;
	}
}
