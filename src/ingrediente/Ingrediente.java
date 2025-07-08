package ingrediente;

import java.util.Map;

import item.Item;

public abstract class Ingrediente extends Item {

	public Ingrediente(String nombre) {
		super(nombre);
	} 

	public abstract Boolean esIngredienteBasico();

	@Override
	public String toString() {
		return getNombre();
	}

}
