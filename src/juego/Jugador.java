package juego;

import java.util.AbstractMap;
import java.util.Map;

public class Jugador {

	private String nombre;
	private Inventario inventario;
	private ParseoJson parser;
	private Registro registro;

	public Jugador(String nombre, Inventario inventario, ParseoJson parser, Registro registro) {
		this.nombre = nombre;
		this.inventario = inventario;
		this.parser = parser;
		this.registro = registro;
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
		int cantidadProducida = receta.getCantidadProducida();
		inventario.agregarItem(new AbstractMap.SimpleEntry<>(productoFinal, cantidadProducida));
		
		StringBuilder log = new StringBuilder();
		log.append("Jugador: ").append(nombre).append("\n");
		log.append("Receta: ").append(receta.getNombre()).append("\n");
		log.append("Cantidad producida: ").append(receta.getCantidadProducida()).append("\n");
		log.append("Tiempo de crafteo: ").append(receta.getTiempoTotal()).append(" min\n");
		
		registro.agregarRegistroTemporal(log.toString());
		registro.guardarRegistroTemporal();

		System.out.println("¡Crafteo exitoso" + this.nombre + "! Se produjo: " + receta.getNombre());
		return true;
	}

	public boolean craftear(Receta receta, Catalizador catalizador) {
		if (catalizador.getNombre() != "ninguno" || catalizador != null) {
			if ((receta.getTipoCatalizador().equals("fuego") && catalizador instanceof CatalizadorFuego)
					|| (receta.getTipoCatalizador().equals("masa_madre")
							&& catalizador instanceof CatalizadorMasaMadre)) {
				catalizador.aplicarEfecto(receta);
				Integer cantidadActual = inventario.getObjetos().get(catalizador);
				if (cantidadActual != null && cantidadActual > 0) {
					inventario.getObjetos().put(catalizador, cantidadActual - 1);
					System.out.println("→ Se consumió 1 unidad de " + catalizador.getNombre());
				}
			} else {
				System.out.println("Catalizador incompatible con esta receta.");
				return false;
			}
		}
		return craftear(receta);
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
