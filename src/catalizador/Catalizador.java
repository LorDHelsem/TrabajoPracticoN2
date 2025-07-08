package catalizador;

import item.Item;
import receta.Receta;

public abstract class Catalizador extends Item {
	
	public Catalizador(String nombre) {
		super(nombre); 
	}
	
	public abstract void aplicarEfecto(Receta receta);
	
}
