package juego;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Map.Entry;

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

	public boolean craftearSimple(Receta receta) {

		if (!receta.getIngredientesFaltantes(this.inventario).contains("-NADA-")) {
			return false;
		}
		for (Entry<Ingrediente, Integer> entry : receta.getIngredientes2().entrySet()) {
			inventario.consumirIngrediente(entry.getKey(), entry.getValue());
		}
		Ingrediente productoFinal = parser.getIngredienteFinal(receta.getNombre());
		int cantidadProducida = receta.getCantidadProducida();
		inventario.agregarItem(new AbstractMap.SimpleEntry<>(productoFinal, cantidadProducida));

		StringBuilder log = new StringBuilder();
		log.append("Jugador: ").append(nombre).append("\n");
		log.append("Receta: ").append(receta.getNombre()).append("\n");
		log.append("Cantidad producida: ").append(receta.getCantidadProducida()).append("\n");
		log.append("Tiempo de crafteo: ").append(receta.getTiempo()).append(" min\n");
		registro.agregarRegistroTemporal(log.toString());
		registro.guardarRegistroTemporal();

		return true;
	}

	public boolean craftear(Receta receta) {

		if (!puedeCraftear(receta, 1)) {
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
		return true;
	}

	public boolean craftearSimple(Receta receta, Catalizador catalizador) {
		if (catalizador.getNombre() != "ninguno" || catalizador != null) {
			// primero va catalizador != null
			if ((receta.getTipoCatalizador().equals("fuego") && catalizador instanceof CatalizadorFuego)
					// jUnit para verificar que un catalizador es de cierto tipo.
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
		return craftearSimple(receta);
	}

	private boolean puedeCraftear(Receta receta, int cantCrafteosNecesarios) {
		for (Map.Entry<Ingrediente, Integer> entry : receta.getIngredientes2().entrySet()) {
			Ingrediente ingrediente = entry.getKey();
			int cantidadNecesitada = entry.getValue();

			if (ingrediente.esIngredienteBasico()) {
				if (!inventario.tieneIngrediente(ingrediente, cantidadNecesitada * cantCrafteosNecesarios)) {
					return false;
				}
			} else {
				IngredienteIntermedio intermedio = (IngredienteIntermedio) ingrediente;
				Receta recetaIntermedia = intermedio.getReceta();
				if (!inventario.tieneIngrediente(intermedio, cantidadNecesitada)) {
					/*
					 * Tomar en cuenta que una receta puede entregar mas de una unidad a la hora de
					 * craftear
					 */
					int resto = cantidadNecesitada % recetaIntermedia.getCantidadProducida();
					int division = cantidadNecesitada / recetaIntermedia.getCantidadProducida();
					int cantCrafteosNecesariosNuevo = resto == 0 ? division : division + 1;
					if (!puedeCraftear(recetaIntermedia, cantCrafteosNecesariosNuevo)) {
						return false;
					}
					// Craftear el intermedio y agregarlo al inventario clonado
					for (Map.Entry<Ingrediente, Integer> subEntry : recetaIntermedia.getIngredientes2().entrySet()) {
						inventario.consumirIngrediente(subEntry.getKey(),
								subEntry.getValue() * cantCrafteosNecesariosNuevo);
					}
					inventario.agregarItem(new AbstractMap.SimpleEntry<>(intermedio,
							recetaIntermedia.getCantidadProducida() * cantCrafteosNecesariosNuevo));
				}
			}
		}
		return true;
	}

	public String getNombre() {
		return this.nombre;
	}

}
