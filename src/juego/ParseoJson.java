package juego;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ParseoJson {
	
	private Map<String, Ingrediente> ingredientesMap = new HashMap<>();
	private Map<String, JsonObject> recetasPendientes = new HashMap<>();
	private List<Receta> listaRecetas = new ArrayList<>();

	// Carga el archivo y construye los ingredientes
	public void cargarRecetasDesdeJSON(String archivo) {
		try (Reader reader = new FileReader(archivo)) {
			JsonElement jsonElement = JsonParser.parseReader(reader);
			JsonObject root = jsonElement.getAsJsonObject();
			JsonArray recetas = root.getAsJsonArray("recetas");

			// 1️ Guardar todas las recetas sin procesar
			for (JsonElement recetaElement : recetas) {
				JsonObject recetaObj = recetaElement.getAsJsonObject();
				String nombre = recetaObj.get("nombre").getAsString();
				recetasPendientes.put(nombre, recetaObj);
			}

			// 2️ Procesar y construir los objetos IngredienteIntermedio
			for (String nombre : recetasPendientes.keySet()) {
				getIngrediente(nombre); // ← Construcción recursiva
			}

			// System.out.println("Se cargaron " + ingredientesMap.size() + " ingredientes
			// correctamente.");

		} catch (IOException e) {
			System.err.println("Error al leer el archivo: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("Error al procesar el JSON: " + e.getMessage());
			e.printStackTrace();
		}
	}

	// Construye un ingrediente (básico o intermedio), recursivamente
	private Ingrediente getIngrediente(String nombre) {
		if (ingredientesMap.containsKey(nombre)) {
			return ingredientesMap.get(nombre);
		}

		JsonObject recetaJson = recetasPendientes.get(nombre);
		if (recetaJson == null) {
			// No hay receta → es un ingrediente básico
			IngredienteBasico basico = new IngredienteBasico(nombre);
			ingredientesMap.put(nombre, basico);
			return basico;
		}

		// Hay receta → construir Receta e IngredienteIntermedio
		double tiempo = recetaJson.get("tiempo_crafteo_minutos").getAsDouble();
		JsonArray ingredientesArray = recetaJson.getAsJsonArray("ingredientes");

		List<Map.Entry<Ingrediente, Integer>> listaIngredientes = new ArrayList<>();

		for (JsonElement ingElem : ingredientesArray) {
			JsonObject ingObj = ingElem.getAsJsonObject();
			String nombreIng = ingObj.get("item").getAsString(); // ← Campo clave
			int cantidad = ingObj.get("cantidad").getAsInt();

			Ingrediente ingrediente = getIngrediente(nombreIng); // Llamada recursiva
			listaIngredientes.add(new AbstractMap.SimpleEntry<>(ingrediente, cantidad));
		}

		// Construcción de Receta
		@SuppressWarnings("unchecked")
		Map.Entry<Ingrediente, Integer>[] arregloIngredientes = listaIngredientes.toArray(new Map.Entry[0]);
		Receta receta = new Receta(nombre, tiempo, arregloIngredientes);
		listaRecetas.add(receta);

		// Construcción de IngredienteIntermedio
		IngredienteIntermedio intermedio = new IngredienteIntermedio(nombre, receta);
		ingredientesMap.put(nombre, intermedio);
		return intermedio;
	}

	// Permite obtener un ingrediente (si existe)
	public Ingrediente getIngredienteFinal(String nombre) {
		return ingredientesMap.get(nombre);
	}

	// Lista todos los ingredientes construidos
	public Collection<Ingrediente> getTodos() {
		return ingredientesMap.values();
	}
	
	//Devuelve solamente una receta por su nombre
	public Receta getRecetaPorNombre(String nombre) {
	    for (Receta receta : listaRecetas) {
	        if (receta.getNombre().equalsIgnoreCase(nombre)) {
	            return receta;
	        }
	    }
	    return null;
	}

	// Devuelve la lista de recetas completas
	public List<Receta> getRecetas() {
		return listaRecetas;
	}

	// TODO LO DEL INVENTARIO
	public void cargarInventarioDesdeArchivoJSON(Inventario inventario,String archivo) {
		if (inventario.getObjetos().isEmpty()) {
			System.err.println(
					"El inventario no fue inicializado. Primero llamá a 'inicializarInventario()' desde RecetaParser.");
			return;
		}

		try (Reader reader = new FileReader(archivo)) {
			JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
			JsonObject elementos = root.getAsJsonObject("elementos");

			for (Map.Entry<String, JsonElement> entry : elementos.entrySet()) {
				String nombre = entry.getKey();
				int cantidad = entry.getValue().getAsInt();

				// Buscar el ítem existente en el inventario que coincida por nombre
				boolean encontrado = false;
				for (Item item : inventario.getObjetos().keySet()) {
					if (item.getNombre().equalsIgnoreCase(nombre)) {
						inventario.getObjetos().put(item, cantidad);
						encontrado = true;
						break;
					}
				}

				if (!encontrado) {
					System.err.println("Advertencia: '" + nombre + "' no existe en el inventario inicial. Se ignoró.");
				}
			}

			System.out.println("\n\nInventario actualizado desde archivo JSON.\n\n");
		} catch (IOException e) {
			System.err.println("Error al leer el archivo: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("Error al procesar el JSON: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void guardarInventarioActual(Inventario inventario) {
		JsonObject root = new JsonObject();
		JsonObject elementos = new JsonObject();

		for (Map.Entry<Item, Integer> entry : inventario.getObjetos().entrySet()) {
			int cantidad = entry.getValue();
			if (cantidad > 0) {
				elementos.addProperty(entry.getKey().getNombre(), cantidad);
			}
		}

		root.add("elementos", elementos);

		try (Writer writer = new FileWriter("inventario_actual.json")) {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			gson.toJson(root, writer);
			System.out.println("Inventario guardado correctamente en 'inventario_actual.json'.");
		} catch (IOException e) {
			System.err.println("Error al guardar el inventario: " + e.getMessage());
		}
	}

	
	public void inicializarInventario(Inventario inventario) {
		for (Ingrediente ingrediente : ingredientesMap.values()) {
			inventario.agregarItem(new AbstractMap.SimpleEntry<>(ingrediente, 0));
		}
	} // con el inventario_del_jugador se setean las cantidades disponibles para ese
		// jugador

}
