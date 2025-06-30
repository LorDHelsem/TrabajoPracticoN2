package juego;

import java.io.IOException;
import java.util.List;

public class AppProlog {
	public static void main(String[] args) throws IOException {
		// Preparamos los datos
		ParseoJson parser = new ParseoJson();
		parser.cargarRecetasDesdeJSON("archivosJSON/recetas.json");

		Inventario inventarioJugador = new Inventario();
		parser.inicializarInventario(inventarioJugador);
		parser.cargarInventarioDesdeArchivoJSON(inventarioJugador, "archivosJSON/inventario.json");

		// Creamos el archivo
		PrologConector prolog = new PrologConector();
		prolog.crearArchivoPL("PuedoHacer.pl", inventarioJugador, parser);

		if (prolog.cargarArchivo("PuedoHacer.pl")) {
			System.out.println("Archivo cargado correctamente");

			List<String> productos = prolog.obtenerProductosPosibles();
			System.out.println("Productos que puedo hacer:");
			for (String p : productos) {
				System.out.println("- " + p);
			}
			System.out.println("Puedo hacer salsa de tomate ? R: "+ (prolog.puedoHacer("salsa de tomate") == true?"SI":"NO"));
		} else {
			System.out.println("Error al cargar archivo Prolog.");
		}
	}

}
