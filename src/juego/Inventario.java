package juego;

import java.util.HashMap;
import java.util.Map;

public class Inventario {
	private Map<Item, Integer> objetos;

	/*
	 * Receta inizializa Item Inventario inizializa Integer
	 */
	public Inventario() {
		this.objetos = new HashMap<Item, Integer>();
	}

	public void agregarItem(Map.Entry<Item, Integer> objeto) {
		this.objetos.put(objeto.getKey(), objeto.getValue());
	}

	public void guardarInventarioActual() {

	}

}
