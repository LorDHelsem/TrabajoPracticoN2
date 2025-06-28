package juego;

public class CatalizadorMasaMadre extends Catalizador{

	public CatalizadorMasaMadre(String nombre) {
		super(nombre);
	}
	
	@Override 
	public void aplicarEfecto(Receta receta) {
		receta.setCantidadProducida(receta.getCantidadProducida()*3);
		System.out.println("Catalizador MASA MADRE aplicado");
	}
}
