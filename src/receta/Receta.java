package receta;

import java.util.HashMap;
import java.util.Map;

import ingrediente.Ingrediente;
import ingrediente.IngredienteIntermedio;
import inventario.Inventario;

public class Receta {
	private String nombre;
	private Map<Ingrediente, Integer> ingredientes;
	private double tiempoTotal;
	private double tiempoDeCrafteo;
	private String tipoCatalizador;
	private int cantidadProducida = 1; 

	@SafeVarargs 
	public Receta(String nombre, String tipoCatalizador, int cantidadProducida, double tiempo,
			Map.Entry<Ingrediente, Integer>... ingredientes) {
		this.ingredientes = new HashMap<>();
		this.nombre = nombre;
		this.tipoCatalizador = tipoCatalizador;
		this.cantidadProducida = cantidadProducida;
		this.tiempoDeCrafteo = tiempo;
		double tiempoAuxiliar = tiempo;
		for (Map.Entry<Ingrediente, Integer> entry : ingredientes) {
			this.ingredientes.put(entry.getKey(), entry.getValue());
			if (!entry.getKey().esIngredienteBasico()) {
				int resto = entry.getValue()
						% ((IngredienteIntermedio) entry.getKey()).getReceta().getCantidadProducida();
				int division = entry.getValue()
						/ ((IngredienteIntermedio) entry.getKey()).getReceta().getCantidadProducida();
				int cantCrafteosNecesariosNuevo = resto == 0 ? division : division + 1;
				tiempoAuxiliar += ((IngredienteIntermedio) entry.getKey()).getTiempoDePreparacionTotal()
						* cantCrafteosNecesariosNuevo;
			}
		}
		this.tiempoTotal = Double.compare(tiempoAuxiliar, 0) == 0 ? tiempo : tiempoAuxiliar;
	}

	public void removerIngrediente(Ingrediente ingrediente) {
	    if (ingredientes.containsKey(ingrediente)) {
	        ingredientes.remove(ingrediente);
	    }
	}

	
	public String getNombre() {
		return this.nombre;
	}

	public double getTiempo() {
		return this.tiempoDeCrafteo;
	}

	public String getTipoCatalizador() {
		return this.tipoCatalizador;
	}

	public int getCantidadProducida() {
		return this.cantidadProducida;
	}

	public double getTiempoTotal() {
		return this.tiempoTotal;
	}

//01 x ingrediente
	public String getIngredientes() {
		StringBuilder retorno = new StringBuilder();
		retorno.append(String.format("%-40s", this.getNombre()) + " Tiempo: " + String.format("%4.1f", this.getTiempo())
				+ " Minutos" + "\n");
		for (Map.Entry<Ingrediente, Integer> entry : this.ingredientes.entrySet()) {
			Ingrediente ingrediente = entry.getKey();
			Integer cantidad = entry.getValue();
			retorno.append("\t" + String.format("%-2d", cantidad) + " x "
					+ String.format("%-40s", ingrediente.getNombre()) + "\n");
		}
		return retorno.toString();
	}

	public String getIngredientesFaltantes(Inventario inventario) {
		StringBuilder retorno = new StringBuilder();
		retorno.append(this.getNombre() + "\n");
		for (Map.Entry<Ingrediente, Integer> entry : this.ingredientes.entrySet()) {
			Integer cantEnInventario = inventario.getCantDeItem(entry.getKey());
			if (cantEnInventario < entry.getValue()) {
				retorno.append(
						"\t" + (entry.getValue() - cantEnInventario) + " x " + entry.getKey().getNombre() + "\n");
			}
		}
		if (retorno.toString().equals(this.getNombre() + "\n")) {
			retorno.append("\t-NADA-\n");
		}
		return retorno.toString();
	}

	public String getIngredientesBasicosFaltantes(Inventario inventario) {
		StringBuilder retorno = new StringBuilder();
		retorno.append(this.getNombre() + "\n");
		Map<String, Integer> totales = this.getIngredientesBasicosTotales(1);
		for (Map.Entry<String, Integer> entry : totales.entrySet()) {
			Integer cantEnInventario = inventario.getCantDeItem(entry.getKey());
			if (cantEnInventario < entry.getValue()) {
				retorno.append("\t" + (entry.getValue() - cantEnInventario) + " x " + entry.getKey() + "\n");
			}
		}
		if (retorno.toString().equals(this.getNombre() + "\n")) {
			retorno.append("\t-NADA-\n");
		}
		return retorno.toString();
	}

	public Map<Ingrediente, Integer> getIngredientesMap() {
		return ingredientes;
	}

	public String getArbolDeCrafteo() {
		StringBuilder retorno = new StringBuilder();
		retorno.append(String.format("%-40s", this.getNombre()) + " Tiempo: "
				+ String.format("%4.1f", this.getTiempoTotal()) + " Minutos" + "\n");
		retorno.append(this.getArbolDeCrafteo(1, 1));
		return retorno.toString();
	}

	public String getArbolDeCrafteo(Integer cant, Integer cantTabs) {
		StringBuilder retorno = new StringBuilder();
		for (Map.Entry<Ingrediente, Integer> entry : this.ingredientes.entrySet()) {
			Ingrediente ingrediente = entry.getKey();
			Integer cantidad = entry.getValue();
			retorno.append("\t".repeat(cantTabs));
			if (ingrediente.esIngredienteBasico()) {
				retorno.append(String.format("%-2d", (cantidad * cant)) + " x " + ingrediente.getNombre() + "\n");
			} else {
				IngredienteIntermedio ingredienteIntermedio = (IngredienteIntermedio) ingrediente;
				int resto = cantidad % ingredienteIntermedio.getReceta().getCantidadProducida();
				int division = cantidad / ingredienteIntermedio.getReceta().getCantidadProducida();
				int cantCrafteosNecesariosNuevo = resto == 0 ? division : division + 1;
				retorno.append(String.format("%-2d", (cantidad * cant)) + " x "
						+ String.format("%-40s", ingrediente.getNombre()) + " Tiempo: "
						+ String.format("%4.1f",
								ingredienteIntermedio.getTiempoDePreparacionTotal() * cantCrafteosNecesariosNuevo)
						+ " Minutos" + "\n");
				retorno.append(ingredienteIntermedio.getArbolCompleto(cantCrafteosNecesariosNuevo, cantTabs + 1));
			}
		}
		return retorno.toString();
	}

	public String getIngredienteCompleto() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%-40s", this.getNombre()) + " Tiempo: " + String.format("%4.1f", this.getTiempoTotal())
				+ " Minutos").append("\n");
		Map<String, Integer> totales = this.getIngredientesBasicosTotales(1);
		for (Map.Entry<String, Integer> entry : totales.entrySet()) {
			sb.append("\t" + String.format("%-2d", entry.getValue()) + " x " + entry.getKey() + "\n");
		}

		return sb.toString();
	}

	// ------------------------------- ESTO HACE QUE SE MUESTRE DE UNA RECETA CUANTO
	// NECESITA DE LOS INGREDIENTE BASICOS
	public Map<String, Integer> getIngredientesBasicosTotales(int cantidadNecesaria) {
		Map<String, Integer> total = new HashMap<>();

		for (Map.Entry<Ingrediente, Integer> entry : this.ingredientes.entrySet()) {
			Ingrediente ingrediente = entry.getKey();
			int cantidad = entry.getValue() * cantidadNecesaria;

			if (ingrediente.esIngredienteBasico()) {
				total.merge(ingrediente.getNombre(), cantidad, Integer::sum);
			} else {
				Map<String, Integer> subtotales = ((IngredienteIntermedio) ingrediente)
						.getIngredientesBasicosTotales(cantidad);
				for (Map.Entry<String, Integer> subEntry : subtotales.entrySet()) {
					total.merge(subEntry.getKey(), subEntry.getValue(), Integer::sum);
				}
			}
		}

		return total;
	}

	public void setCantidadProducida(int cantidadProducida) {
		this.cantidadProducida = cantidadProducida;
	}

	public void setTiempoTotal(double tiempoTotal) {
		this.tiempoTotal = tiempoTotal;
	}

	public void setTiempoDeCrafteo(double tiempoDeCrafteo) {
		this.tiempoDeCrafteo = tiempoDeCrafteo;
	}

}
