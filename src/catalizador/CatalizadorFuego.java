package catalizador;

import receta.Receta;

public class CatalizadorFuego extends Catalizador {

	public CatalizadorFuego(String nombre) {
		super(nombre); 
	}

	@Override
	public void aplicarEfecto(Receta receta) {
		double nuevoTiempo = receta.getTiempoTotal() / 2;
		receta.setTiempoDeCrafteo(nuevoTiempo);
		System.out.println("Catalizador FUEGO aplicado: tiempo de crafteo reducido a " + nuevoTiempo + " minutos.");
	}
}
