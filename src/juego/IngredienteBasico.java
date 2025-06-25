package juego;

import java.util.HashMap;
import java.util.Map;

public class IngredienteBasico extends Ingrediente {

	public IngredienteBasico(String nombre) {
		super(nombre);
	}

	@Override
	public Boolean esIngredienteBasico() {
		return true;
	}

	@Override
	public Map<String, Integer> getIngredientesBasicosTotales(int cantidad) {
		Map<String, Integer> resultado = new HashMap<>();
		resultado.put(this.getNombre(), cantidad);
		return resultado;
	}

}
