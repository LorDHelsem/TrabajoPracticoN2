package juego;

import java.util.Map;

public abstract class Ingrediente extends Item {

	public Ingrediente(String nombre) {
		super(nombre);
	}
	public abstract Boolean esIngredienteBasico();
	
	@Override
	public String toString() {
	    return getNombre();
	}

	public abstract Map<String, Integer> getIngredientesBasicosTotales(int cantidad);

}
