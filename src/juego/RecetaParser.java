package juego;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecetaParser {

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

            System.out.println("Se cargaron " + ingredientesMap.size() + " ingredientes correctamente.");

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
            String nombreIng = ingObj.get("item").getAsString();  // ← Campo clave
            int cantidad = ingObj.get("cantidad").getAsInt();

            Ingrediente ingrediente = getIngrediente(nombreIng);  // Llamada recursiva
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

    // Devuelve la lista de recetas completas
    public List<Receta> getRecetas() {
        return listaRecetas;
    }
    
    public void inicializarInventario(Inventario inventario) {
        for (Ingrediente ingrediente : ingredientesMap.values()) { 
            inventario.agregarItem(new AbstractMap.SimpleEntry<>(ingrediente, 0));
        }
    } // con el inventario_del_jugador se setean las cantidades disponibles para ese jugador

}
