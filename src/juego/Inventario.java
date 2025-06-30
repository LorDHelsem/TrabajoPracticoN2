package juego;

import java.util.HashMap;
import java.util.Map;

public class Inventario {
	private Map<Item, Integer> objetos;

	public Inventario() {
		this.objetos = new HashMap<>();
	}

	public void agregarItem(Map.Entry<Item, Integer> objeto) {
		this.objetos.put(objeto.getKey(), this.objetos.getOrDefault(objeto.getKey(), 0) + objeto.getValue());
	}

	public void mostrarInventario() {
		if (objetos.isEmpty()) {
			System.out.println("El inventario está vacío.");
			return;
		}

		System.out.println("Contenido del inventario:");
		for (Map.Entry<Item, Integer> entry : objetos.entrySet()) {
			System.out.println("- " + entry.getKey().getNombre() + ": " + entry.getValue());
		}
	}

	public void mostrarInventarioDisponible() {
		boolean hayItems = false;
		for (Map.Entry<Item, Integer> entry : objetos.entrySet()) {
			if (entry.getValue() > 0) {
				hayItems = true;
				break;
			}
		}

		if (!hayItems) {
			System.out.println("El inventario está vacío o todos los ítems tienen cantidad cero.");
			return;
		}

		System.out.println("Inventario Disponible:");
		for (Map.Entry<Item, Integer> entry : objetos.entrySet()) {
			if (entry.getValue() > 0) {
				System.out.println("- " + entry.getKey().getNombre() + ": " + entry.getValue());
			}
		}
	}

	public Map<Item, Integer> getObjetos() {
		return objetos;
	}

	public boolean tieneIngrediente(Item ingrediente, int cantidadRequerida) {
		// Si el ingrediente no está en el inventario, retorna false
		if (!objetos.containsKey(ingrediente)) {
			return false;
		}
		// Si la cantidad disponible es suficiente, retorna true
		return objetos.get(ingrediente) >= cantidadRequerida;
	}

	public void consumirIngrediente(Item ingrediente, int cantidad) {
		if (!tieneIngrediente(ingrediente, cantidad)) {
			throw new IllegalStateException("No hay suficiente " + ingrediente.getNombre() + " en el inventario.");
		}
		// Resta la cantidad usada
		objetos.put(ingrediente, objetos.get(ingrediente) - cantidad);
	}

	public Catalizador getCatalizador(String nombre) {
		for (Item item : objetos.keySet()) {
			if (item instanceof Catalizador && item.getNombre().equalsIgnoreCase(nombre)) {
				return (Catalizador) item;
			}
		}
		return null;
	}

	public Integer getCantDeItem(Ingrediente ingrediente) {

		return this.objetos.get(ingrediente);
	}

	public Integer getCantDeItem(String strIngrediente) {
		Integer cant = 0;
		for (Map.Entry<Item, Integer> entry : objetos.entrySet()) {
			if (entry.getKey().getNombre().equals(strIngrediente)) {
				return entry.getValue();
			}
		}
		return cant;
	}

}
