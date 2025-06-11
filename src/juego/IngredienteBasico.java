package juego;

public class IngredienteBasico extends Ingrediente {

	public IngredienteBasico(String nombre) {
		super(nombre);
	}

	@Override
	public Boolean esIngredienteBasico() {
		return true;
	}

}
