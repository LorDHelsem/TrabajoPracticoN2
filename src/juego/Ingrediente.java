package juego;

public abstract class Ingrediente extends Item {

	public Ingrediente(String nombre) {
		super(nombre);
	}

	public abstract String getReceta();

	public abstract String getRecetaCompleta();

	public abstract String getRecetaCompleta(Integer cant);

	public abstract Boolean esIngredienteBasico();

}
