package juego;

public class IngredienteBasico extends Ingrediente {

	public IngredienteBasico(String nombre) {
		super(nombre);
	}

	@Override
	public String getReceta() {
		return this.getNombre();
	}

	@Override
	public String getRecetaCompleta() {
		return this.getReceta();
	}

}
