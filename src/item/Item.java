package item;

import java.util.Objects;

public abstract class Item {
	private String nombre;

	public Item(String nombre) {
		this.nombre = nombre; 
	}

	public String getNombre() {
		return nombre;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		Item other = (Item) obj;
		return Objects.equals(nombre, other.nombre);
	}

}
