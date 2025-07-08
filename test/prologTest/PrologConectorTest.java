package prologTest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.management.Query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import inventario.Inventario;
import parseoJson.ParseoJson;
import prolog.PrologConector;

public class PrologConectorTest { 

    private PrologConector prolog;
    private Inventario inventario;
    private ParseoJson parser;
    private final String archivoProlog = "test_recetas.pl";

    @BeforeEach 
    public void inicializacion() throws IOException {
        prolog = new PrologConector();
        parser = new ParseoJson();

        // Cargamos recetas e inventario de prueba
        parser.cargarRecetasDesdeJSON("archivosJSON/recetas.json");
        inventario = new Inventario();
        parser.inicializarInventario(inventario);
        parser.cargarInventarioDesdeArchivoJSON(inventario, "archivosJSON/inventario.json");

        // Crear el archivo Prolog de prueba
        prolog.crearArchivoPL(archivoProlog, inventario, parser);
        //assertTrue(prolog.cargarArchivo(archivoProlog), "Error al cargar el archivo Prolog");
    }

    @Test
    public void testPuedoHacerProductoPosible() {
    	prolog.cargarArchivo(archivoProlog);
        assertTrue(prolog.puedoHacer("salsa de tomate"), "Debería poder hacer 'salsa de tomate'");
    }
    
    @Test
    public void testPuedoHacerProductoConCantidadInsuficiente() {
    	prolog.cargarArchivo(archivoProlog);
        assertFalse(prolog.puedoHacer("empanada de carne"), "No Debería poder hacer 'empanada de carne' por cantidad insuficiente");
    }

    @Test
    public void testPuedoHacerProductoImposible() {
    	prolog.cargarArchivo(archivoProlog);
        assertFalse(prolog.puedoHacer("pato al escabeche"), "No debería poder hacer un producto inexistente o sin ingredientes");
    }

    @Test
    public void testObtenerProductosPosiblesConInventarioValido() {
    	prolog.cargarArchivo(archivoProlog);
        List<String> productos = prolog.obtenerProductosPosibles();
        assertNotNull(productos, "La lista de productos no debe ser nula");
        assertFalse(productos.isEmpty(), "Debería haber al menos un producto posible");
        assertTrue(productos.contains("salsa de tomate"), "La lista debería contener 'salsa de tomate'");
    }
 

}
