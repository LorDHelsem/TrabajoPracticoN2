package juego;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Receta {
	private String nombre;
	private Map<Ingrediente, Integer> ingredientes;
	private double tiempoTotal;
	private double tiempoDeCrafteo;

	@SafeVarargs
	public Receta(String nombre, double tiempo, Map.Entry<Ingrediente, Integer>... ingredientes) {
		this.ingredientes = new HashMap<Ingrediente, Integer>();
		this.nombre = nombre;
		this.tiempoDeCrafteo = tiempo;
		double tiempoAuxiliar = tiempo;
		for (Map.Entry<Ingrediente, Integer> entry : ingredientes) {
			this.ingredientes.put(entry.getKey(), entry.getValue());
			if (!entry.getKey().esIngredienteBasico()) {
				tiempoAuxiliar += ((IngredienteIntermedio) entry.getKey()).getTiempoDePreparacionTotal()
						* entry.getValue();
			}
		}
		this.tiempoTotal = Double.compare(tiempoAuxiliar, 0) == 0 ? tiempo : tiempoAuxiliar;
	}

	public Receta(String nombre2, List<Ingrediente> ingredientes2, double tiempo, Ingrediente produccion) {
		// TODO Auto-generated constructor stub
	}

	public String getNombre() {
		return this.nombre;
	}

	public double getTiempo() {
		return this.tiempoDeCrafteo;
	}

	public double getTiempoTotal() {
		return this.tiempoTotal;
	}

	
	public String getIngredientes() {
		StringBuilder retorno = new StringBuilder();
		retorno.append(this.getNombre() + " " + "\n");
		for (Map.Entry<Ingrediente, Integer> entry : this.ingredientes.entrySet()) {
			Ingrediente ingrediente = entry.getKey();
			Integer cantidad = entry.getValue();
			retorno.append("\t" + ingrediente.getNombre() + " " + cantidad + "\n");
		}
		return retorno.toString();
	}

	public Map<Ingrediente, Integer> getIngredientes2() {
		return ingredientes;
	}

	public String getArbolDeCrafteo() {
		StringBuilder retorno = new StringBuilder();
		retorno.append(this.getNombre() + " " + this.getTiempoTotal() + " " + "Minutos" + "\n");
		retorno.append(this.getArbolDeCrafteo(1, 1));
		return retorno.toString();
	}

	protected String getArbolDeCrafteo(Integer cant, Integer cantTabs) {
		StringBuilder retorno = new StringBuilder();
		for (Map.Entry<Ingrediente, Integer> entry : this.ingredientes.entrySet()) {
			Ingrediente ingrediente = entry.getKey();
			Integer cantidad = entry.getValue();
			retorno.append("\t".repeat(cantTabs));
			if (ingrediente.esIngredienteBasico()) {
				retorno.append(ingrediente.getNombre() + " " + cantidad * cant + "\n");
			} else {
				retorno.append(ingrediente.getNombre() + " " + cantidad * cant + " "
						+ ((IngredienteIntermedio) ingrediente).getTiempoDePreparacionTotal() * cantidad * cant + " "
						+ "Minutos" + "\n");
				retorno.append(((IngredienteIntermedio) ingrediente).getArbolCompleto(cantidad * cant, cantTabs + 1));
			}
		}
		return retorno.toString();
	}

	public String getIngredienteCompleto() {
		StringBuilder retorno = new StringBuilder();
		retorno.append(this.nombre + "\n");
		for (Map.Entry<Ingrediente, Integer> entry : this.ingredientes.entrySet()) {
			Ingrediente ingrediente = entry.getKey();
			Integer cantidad = entry.getValue();

			if (ingrediente.esIngredienteBasico()) {
				retorno.append(ingrediente.getNombre() + " " + cantidad + "\n");
			} else {
				
				retorno.append(((IngredienteIntermedio) ingrediente).getRecetaCompleta(cantidad));
			}
		}
		return retorno.toString();
	}
	//------------------------------- ESTO HACE QUE SE MUESTRE DE UNA RECETA CUANTO NECESITA DE LOS INGREDIENTE BASICOS
	public Map<String, Integer> getIngredientesBasicosTotales(int cantidadNecesaria) {
	    Map<String, Integer> total = new HashMap<>();

	    for (Map.Entry<Ingrediente, Integer> entry : this.ingredientes.entrySet()) {
	        Ingrediente ingrediente = entry.getKey();
	        int cantidad = entry.getValue() * cantidadNecesaria;

	        if (ingrediente.esIngredienteBasico()) {
	            total.merge(ingrediente.getNombre(), cantidad, Integer::sum);
	        } else {
	            Map<String, Integer> subtotales = ((IngredienteIntermedio) ingrediente).getIngredientesBasicosTotales(cantidad);
	            for (Map.Entry<String, Integer> subEntry : subtotales.entrySet()) {
	                total.merge(subEntry.getKey(), subEntry.getValue(), Integer::sum);
	            }
	        }
	    }

	    return total;
	}
	
	public String getIngredientesBasicosComoString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append(this.nombre).append("\n");

	    Map<String, Integer> totales = this.getIngredientesBasicosTotales(1);
	    for (Map.Entry<String, Integer> entry : totales.entrySet()) {
	        sb.append(entry.getKey()).append(" ").append(entry.getValue()).append("\n");
	    }

	    return sb.toString();
	}

	//-------------------------------

	public String getIngredienteCompleto(Integer cant) {
		StringBuilder retorno = new StringBuilder();
		for (Map.Entry<Ingrediente, Integer> entry : this.ingredientes.entrySet()) {
			Ingrediente ingrediente = entry.getKey();
			Integer cantidad = entry.getValue();

			if (ingrediente.esIngredienteBasico()) {
				retorno.append("\t"+ingrediente.getNombre() + " " + cantidad * cant + "\n");
				
			} else {
				retorno.append(((IngredienteIntermedio) ingrediente).getRecetaCompleta(cantidad * cant));
			}
		}
		return retorno.toString();
	}

	
}
