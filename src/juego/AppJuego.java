package juego;

import java.util.Scanner;

public class AppJuego {
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
	
		System.out.println("Bienvenido al sistema de crafteo 🍽️");
		System.out.print("Por favor, ingresá tu nombre: ");
		String nombreJugador = scanner.nextLine();
		
		ParseoJson parser= new ParseoJson();
		Inventario inventario = new Inventario();
		Registro registro= new Registro();
		
		parser.cargarRecetasDesdeJSON("archivosJson/recetas.json");
		parser.inicializarInventario(inventario);
		parser.cargarInventarioDesdeArchivoJSON(inventario,"archivosJson/inventario.json");
		
		Jugador jugador= new Jugador(nombreJugador,inventario,parser,registro);
		
		Menu menu= new Menu(jugador,inventario,parser,registro);
		menu.mostrarMenu();
		
		scanner.close();
	}
}
