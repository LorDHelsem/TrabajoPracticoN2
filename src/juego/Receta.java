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
		double tiempoAuxiliar = 0;
		for (Map.Entry<Ingrediente, Integer> entry : ingredientes) {
			this.ingredientes.put(entry.getKey(), entry.getValue());
			if (!entry.getKey().esIngredienteBasico()) {
				tiempoAuxiliar += ((IngredienteIntermedio) entry.getKey()).getTiempoDePreparacion() * entry.getValue();
			}
		}
		this.tiempo = Double.compare(tiempoAuxiliar, 0) == 0 ? tiempo : tiempoAuxiliar;
	}

	public String getNombre() {
		return this.nombre;
	}

	public double getTiempo() {
		return this.tiempo;
	}

	public String getIngredientes() {
		StringBuilder retorno = new StringBuilder();
		retorno.append(this.getNombre() + " " + this.getTiempo() + " " + "Minutos" + "\n");
		for (Map.Entry<Ingrediente, Integer> entry : this.ingredientes.entrySet()) {
			Ingrediente ingrediente = entry.getKey();
			Integer cantidad = entry.getValue();
			retorno.append("\t" + ingrediente.getNombre() + " " + cantidad);
			if (ingrediente.esIngredienteBasico()) {
				retorno.append("\n");
			} else {
				retorno.append(" " + ((IngredienteIntermedio) ingrediente).getTiempoDePreparacion() * cantidad + " "
						+ "Minutos" + "\n");
			}
		}
		return retorno.toString();
	}

	public String getIngredienteCompleto() {
		StringBuilder retorno = new StringBuilder();
		retorno.append(this.getNombre() + " " + this.getTiempo() + " " + "Minutos" + "\n");
		retorno.append(this.getIngredienteCompleto(1, 1));
		return retorno.toString();
	}

	protected String getIngredienteCompleto(Integer cant, Integer cantTabs) {
		StringBuilder retorno = new StringBuilder();
		for (Map.Entry<Ingrediente, Integer> entry : this.ingredientes.entrySet()) {
			Ingrediente ingrediente = entry.getKey();
			Integer cantidad = entry.getValue();
			retorno.append("\t".repeat(cantTabs));
			if (ingrediente.esIngredienteBasico()) {
				retorno.append(ingrediente.getNombre() + " " + cantidad * cant + "\n");
			} else {
				retorno.append(ingrediente.getNombre() + " " + cantidad * cant + " "
						+ ((IngredienteIntermedio) ingrediente).getTiempoDePreparacion() * cantidad * cant + " "
						+ "Minutos" + "\n");
				retorno.append(((IngredienteIntermedio) ingrediente).getRecetaCompleta(cantidad * cant, cantTabs + 1));
			}
		}
		return retorno.toString();
	}

}
