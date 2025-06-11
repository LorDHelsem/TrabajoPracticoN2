package juego;

public abstract class Ingrediente extends Item {

	public Ingrediente(String nombre) {
		super(nombre);
	}
	public abstract Boolean esIngredienteBasico();

}
