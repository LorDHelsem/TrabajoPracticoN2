package juego;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Inventario {
	private Map<Item, Integer> objetos;

	public Inventario() {
		this.objetos = new HashMap<Item, Integer>();
	}

	public void agregarItem(Map.Entry<Item, Integer> objeto) {
		this.objetos.put(objeto.getKey(), objeto.getValue());
	}

	public void guardarInventarioActual() {
	    JsonObject root = new JsonObject();
	    JsonObject elementos = new JsonObject();

	    for (Map.Entry<Item, Integer> entry : objetos.entrySet()) {
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

	
	
	public void cargarDesdeArchivoJSON(String archivo) {
	    if (objetos.isEmpty()) {
	        System.err.println("El inventario no fue inicializado. Primero llamá a 'inicializarInventario()' desde RecetaParser.");
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
	            for (Item item : objetos.keySet()) {
	                if (item.getNombre().equalsIgnoreCase(nombre)) {
	                    objetos.put(item, cantidad);
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


}
