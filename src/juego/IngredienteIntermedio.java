package juego;

import java.util.Map;

public class IngredienteIntermedio extends Ingrediente {
	private Receta receta;

	public IngredienteIntermedio(String nombre, Receta receta) {
		super(nombre);
		this.receta = receta;
	}

	public String getReceta() {
		return this.receta.getIngredientes();
	}

	public String getRecetaCompleta() {
		return this.receta.getIngredienteCompleto();
	}

	public String getRecetaCompleta(Integer cant) {
		return this.receta.getIngredienteCompleto(cant);
	}

	public String getArbolCompleto() {
		return this.receta.getArbolDeCrafteo();
	}

	protected String getArbolCompleto(Integer cant, Integer cantTabs) {
		return this.receta.getArbolDeCrafteo(cant, cantTabs);
	}

	public double getTiempoDePreparacion() {
		return this.receta.getTiempo();
	}

	public double getTiempoDePreparacionTotal() {
		return this.receta.getTiempoTotal();
	}

	@Override
	public Boolean esIngredienteBasico() {
		return false;
	}
	@Override
	public Map<String, Integer> getIngredientesBasicosTotales(int cantidad) {
	    return receta.getIngredientesBasicosTotales(cantidad);
	}

}
