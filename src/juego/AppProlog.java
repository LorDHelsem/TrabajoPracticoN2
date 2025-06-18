package juego;
import java.io.IOException;
import java.util.List;

public class AppProlog {
	public static void main(String[] args) throws IOException {
		//Preparamos los datos
	    RecetaParser parser = new RecetaParser();
	    parser.cargarRecetasDesdeJSON("archivosJson/recetas.json");

	    Inventario inventarioJugador = new Inventario();
	    parser.inicializarInventario(inventarioJugador);
	    inventarioJugador.cargarDesdeArchivoJSON("archivosJson/inventario.json");

	    //Creamos el archivo
	    PrologConector prolog = new PrologConector();
	    prolog.crearArchivoPL("PuedoHacer.pl", inventarioJugador, parser);
	    
	    if (prolog.cargarArchivo("PuedoHacer.pl")) {
	        System.out.println("Archivo cargado correctamente");

	        List<String> productos = prolog.obtenerProductosPosibles();
	        System.out.println("Productos que puedo hacer:");
	        for (String p : productos) {
	            System.out.println("- " + p);
	        }
	    } else {
	        System.out.println("Error al cargar archivo Prolog.");
	    }
	}
		
}
