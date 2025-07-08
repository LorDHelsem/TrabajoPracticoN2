package ingrediente;

import java.util.Map;

import receta.Receta;

public class IngredienteIntermedio extends Ingrediente {
	private Receta receta;

	public IngredienteIntermedio(String nombre, Receta receta) {
		super(nombre);
		this.receta = receta; 
	}

	public Receta getReceta() {
		return this.receta;
	}

	public String getRecetaString() {
		return this.receta.toString();
	}

	public String getRecetaCompleta() {
		return this.receta.getIngredienteCompleto(); 
	}

	public String getArbolCompleto() {
		return this.receta.getArbolDeCrafteo();
	}

	public String getArbolCompleto(Integer cant, Integer cantTabs) {
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

	public Map<String, Integer> getIngredientesBasicosTotales(int cantidad) {
		return receta.getIngredientesBasicosTotales(cantidad);
	}

}
