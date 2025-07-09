package catalizador;

import java.util.Map;

import ingrediente.Ingrediente;
import receta.Receta;

public class CatalizadorDulce extends Catalizador{
	
	public CatalizadorDulce(String nombre) {
		super(nombre); 
	}
	
	public void aplicarEfecto(Receta receta) {
	    Map<Ingrediente, Integer> copia = receta.getIngredientesMap();

	    Ingrediente menor = null;
	    int cantidadMenor = Integer.MAX_VALUE;

	    for (Map.Entry<Ingrediente, Integer> entry : copia.entrySet()) {
	        if (entry.getValue() < cantidadMenor) {
	            cantidadMenor = entry.getValue();
	            menor = entry.getKey();
	        }
	    }

	    if (menor != null) {
	        receta.removerIngrediente(menor);
	    }
	}

}
