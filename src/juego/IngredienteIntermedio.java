package juego;

public class IngredienteIntermedio extends Ingrediente {
	private Receta receta;

	public IngredienteIntermedio(String nombre, Receta receta) {
		super(nombre);
		this.receta = receta;
	}

	@Override
	public String getReceta() {
		return this.receta.getIngredientes();
	}

	@Override
	public String getRecetaCompleta() {
		return this.receta.getIngredienteCompleto();
	}

}
