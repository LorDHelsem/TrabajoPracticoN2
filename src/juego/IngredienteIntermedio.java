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

	@Override
	public Boolean esIngredienteBasico() {
		return false;
	}

	@Override
	public String getRecetaCompleta(Integer cant) {
		return this.receta.getIngredienteCompleto(cant);
	}

}
