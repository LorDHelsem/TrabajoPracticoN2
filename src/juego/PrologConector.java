package juego;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jpl7.Query;
import org.jpl7.Term;
import org.jpl7.Variable;

public class PrologConector {

	public void crearArchivoPL(String ruta, Inventario inventario, ParseoJson parser) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(ruta));

		// Escribir hechos del inventario
		for (Map.Entry<Item, java.lang.Integer> entrada : inventario.getObjetos().entrySet()) {
			Item nombre = entrada.getKey();
			int cantidad = entrada.getValue();
			writer.write("tengo('" + nombre + "', " + cantidad + ").\n");
		}

		writer.write("\n");

		// Escribir hechos de las recetas
		for (Receta receta : parser.getRecetas()) {
			String nombreReceta = receta.getNombre();
			for (Map.Entry<Ingrediente, java.lang.Integer> ingrediente : receta.getIngredientes2().entrySet()) {
				writer.write("ingrediente('" + nombreReceta + "', '" + ingrediente.getKey() + "', "
						+ ingrediente.getValue() + ").\n");
			}
		}

		writer.write("\n");

		// Escribir las reglas Prolog
		writer.write("% Para verificar si tengo suficientes unidades de un ingrediente básico\n"
				+ "suficiente(Ingrediente, CantidadNecesaria) :-\n" + "    tengo(Ingrediente, CantidadTengo),\n"
				+ "    CantidadTengo >= CantidadNecesaria.\n" + "\n"
				+ "% Verifica si todos los ingredientes de un producto están disponibles\n"
				+ "puedo_hacer(Producto) :-\n" + "    ingrediente(Producto, _, _),  % El producto existe\n"
				+ "    forall( \n" + "        ingrediente(Producto, Ingrediente, CantidadNecesaria),\n"
				+ "        suficiente(Ingrediente, CantidadNecesaria)\n" + "    ).\n" + "\n"
				+ "% Armamos una lista, solo se guarda 1 vez sin repetidos\n" + "productos_posibles(Lista) :-\n"
				+ "    setof(Producto, puedo_hacer(Producto), Lista).\n");

		writer.close();

		System.out.println("Archivo Prolog generado: " + ruta);
	}

	// Cargar el archivo .pl
	public boolean cargarArchivo(String arch) {
		Query cargar = new Query("consult('" + arch + "')");
		return cargar.hasSolution();
	}

	// Ejecutar consulta Prolog
	public boolean puedoHacer(String producto) {
		Query consulta = new Query("puedo_hacer(" + producto + ").");
		return consulta.hasSolution();
	}

	public List<String> obtenerProductosPosibles() {
		List<String> productos = new ArrayList<>();

		Variable lista = new Variable("Lista");
		Query q = new Query("productos_posibles", new Term[] { lista });

		if (q.hasSolution()) {
			Term resultado = q.oneSolution().get("Lista");

			for (Term t : resultado.toTermArray()) {
				productos.add(t.name());
			}
		}

		return productos;
	}
}
