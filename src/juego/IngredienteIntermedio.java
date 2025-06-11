package juego;

public class IngredienteIntermedio extends Ingrediente {
	private Receta receta;

	public IngredienteIntermedio(String nombre, Receta receta) {
		super(nombre);
		this.receta = receta;
	}

	public String getReceta() {
		return this.receta.getIngredientes();
	}

	public String getRecetaCompleta() {
		return this.receta.getIngredienteCompleto();
	}

	protected String getRecetaCompleta(Integer cant, Integer cantTabs) {
		return this.receta.getIngredienteCompleto(cant, cantTabs);
	}

	public double getTiempoDePreparacion() {
		return this.receta.getTiempo();
	}

	@Override
	public Boolean esIngredienteBasico() {
		return false;
	}
}
