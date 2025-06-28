package juego;

public abstract class Catalizador extends Item {
	
	public Catalizador(String nombre) {
		super(nombre);
	}
	
	public abstract void aplicarEfecto(Receta receta);
	
}
