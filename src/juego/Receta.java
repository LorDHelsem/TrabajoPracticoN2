package juego;

import java.util.HashMap;
import java.util.Map;

public class Receta {
	private String nombre;
	private Map<Ingrediente, Integer> ingredientes = new HashMap<Ingrediente, Integer>();
	private double tiempo;

	@SafeVarargs
	public Receta(String nombre, double tiempo, Map.Entry<Ingrediente, Integer>... ingredientes) {
		this.nombre = nombre;
		this.tiempo = tiempo;
		for (Map.Entry<Ingrediente, Integer> entry : ingredientes) {
			this.ingredientes.put(entry.getKey(), entry.getValue());
		}
	}

	public String getNombre() {
		return this.nombre;
	}

	public double getTiempo() {
		return this.tiempo;
	}

	public String getIngredientes() {
		StringBuilder retorno = new StringBuilder();
		for (Map.Entry<Ingrediente, Integer> entry : this.ingredientes.entrySet()) {
			Ingrediente ingrediente = entry.getKey();
			Integer cantidad = entry.getValue();
			retorno.append(ingrediente.getNombre() + " " + cantidad + "\n");
		}
		return retorno.toString();
	}

	public String getIngredienteCompleto() {
		StringBuilder retorno = new StringBuilder();
		for (Map.Entry<Ingrediente, Integer> entry : this.ingredientes.entrySet()) {
			Ingrediente ingrediente = entry.getKey();
			Integer cantidad = entry.getValue();

			if (ingrediente.esIngredienteBasico()) {
				retorno.append(ingrediente.getReceta() + " " + cantidad + "\n");
			} else {
				if (cantidad > 1) {
					retorno.append(ingrediente.getRecetaCompleta(cantidad));
				} else {
					retorno.append(ingrediente.getRecetaCompleta());
				}
			}
		}
		return retorno.toString();
	}

	public String getIngredienteCompleto(Integer cant) {
		StringBuilder retorno = new StringBuilder();
		for (Map.Entry<Ingrediente, Integer> entry : this.ingredientes.entrySet()) {
			Ingrediente ingrediente = entry.getKey();
			Integer cantidad = entry.getValue();

			if (ingrediente.esIngredienteBasico()) {
				retorno.append(ingrediente.getReceta() + " " + cantidad * cant + "\n");
			} else {
				retorno.append(ingrediente.getRecetaCompleta(cantidad * cant));
			}
		}
		return retorno.toString();
	}

}
