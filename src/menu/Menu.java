package menu;

import java.io.IOException;
import java.util.Map.Entry;

import catalizador.Catalizador;
import inventario.Inventario;
import item.Item;
import jugador.Jugador;
import parseoJson.ParseoJson;
import prolog.PrologConector;
import receta.Receta;
import registro.Registro;

import java.util.List;
import java.util.Scanner;

public class Menu {
	private Jugador jugador;
	private Inventario inventario;
	private ParseoJson parser;

	public Menu(Jugador jugador, Inventario inventario, ParseoJson parser, Registro registro) {
		this.jugador = jugador;
		this.inventario = inventario;
		this.parser = parser; 
	}

	public void mostrarMenu() {
		Scanner scanner = new Scanner(System.in);
		String opc;
		do {
			System.out.println("------ MENÚ PRINCIPAL ------");
			System.out.println("1. Mostrar inventario completo");
			System.out.println("2. Mostrar inventario disponible");
			System.out.println("3. Mostrar todos los Ingredientes con Receta asociada"); //7
			System.out.println("4. ¿Qué necesito para craftear un ingrediente? - Primer nivel");//1
			System.out.println("5. ¿Qué necesito para craftear un ingrediente desde cero?");//2
			System.out.println("6. Mostrar el árbol de crafteo para un Ingrediente");
			System.out.println("7. ¿Qué me falta para craftear un Ingrediente? - Primer nivel");//3
			System.out.println("8. ¿Qué me falta para craftear un Ingrediente desde cero?");//4
			System.out.println("9. ¿Cuánto puedo craftear con todo el inventario?");//5
			System.out.println("10. Prolog - ¿Qué ingredientes puedo craftear?");
			System.out.println("11. Craftear ingrediente (sin catalizador)");
			System.out.println("12. Craftear ingrediente (con catalizador)");
			System.out.println("0. Salir");
			System.out.print("Elige una opción: ");
			opc = scanner.nextLine();

			limpiarConsola();
			switch (opc) {
			case "1":
				inventario.mostrarInventario(); 
				pausar(scanner);
				limpiarConsola();
				break;
			case "2":
				inventario.mostrarInventarioDisponible();
				pausar(scanner);
				limpiarConsola();
				break;
			case "3":
				mostrarTodasLasRecetas(scanner);
				break;
			case "4":
				mostrarIngredienteDeReceta(scanner);
				break;
			case "5":
				mostrarIngredienteBasicoDeReceta(scanner);
				break;
			case "6":
				mostrarArbolDeCrafteo(scanner);
				break;
			case "7":
				queIngredientesFaltanParaReceta(scanner);
				break;
			case "8":
				queIngredientesBasicosFaltanParaReceta(scanner);
				break;
			case "9":
				cantCrafteosPosiblesParaReceta(scanner);
				break;
			case "10":
				posiblesCrafteos(scanner);
				break;
			case "11":
				craftearSinCatalizador(scanner);
				break;
			case "12":
				craftearConCatalizador(scanner);
				break;
			case "0":
				parser.guardarInventarioActual(this.inventario);
				System.out.println("¡Juego finalizado!");
				break;
			default:
				System.out.println("Opción inválida.");
				pausar(scanner);
				limpiarConsola();
				break;

			}

		} while (!opc.equals("0"));
	}

	private void cantCrafteosPosiblesParaReceta(Scanner scanner) {
		limpiarConsola();
		System.out.print("Ingrese nombre de receta: ");
		String nombre = scanner.nextLine();
		Receta receta = parser.getRecetaPorNombre(nombre);
		limpiarConsola();
		Inventario invduplicado = new Inventario();
		for (Entry<Item, Integer> entry : (this.inventario.getObjetos()).entrySet()) {
			invduplicado.agregarItem(entry);
		}
		Jugador jugadorClone = new Jugador("clone", invduplicado, parser, new Registro());
		int cant = 0;
		if (receta != null) {

			while (jugadorClone.craftear(receta)) {
				cant++;
			}
			System.out.println("Puedo craftear la receta: " + receta.getNombre() + " unas " + cant + " veces.");
			System.out.println("Lo que quiere decir obtener un total de " + cant * receta.getCantidadProducida()
					+ " de ingrediente " + receta.getNombre());
		} else {
			System.out.println("Receta no encontrada.");
		}
		pausar(scanner);
		limpiarConsola();
	}

	private void mostrarTodasLasRecetas(Scanner scanner) {
		limpiarConsola();
		System.out.println("------Recetas disponibles------");
		for (Receta r : parser.getRecetas()) {
			System.out.println("- " + r.getNombre());
		}
		pausar(scanner);
		limpiarConsola();
	}

	private void mostrarIngredienteDeReceta(Scanner scanner) {
		limpiarConsola();
		System.out.print("Ingrese nombre de receta: ");
		String nombre = scanner.nextLine();
		Receta r = parser.getRecetaPorNombre(nombre);
		limpiarConsola();
		if (r != null) {
			System.out.println("------Mostrando ingredientes necesarios------");
			System.out.println(r.getIngredientes());
		} else {
			System.out.println("Receta no encontrada.");
		}
		pausar(scanner);
		limpiarConsola();
	}

	private void mostrarIngredienteBasicoDeReceta(Scanner scanner) {
		limpiarConsola();
		System.out.print("Ingrese nombre de receta: ");
		String nombre = scanner.nextLine();
		Receta r = parser.getRecetaPorNombre(nombre);
		limpiarConsola();
		if (r != null) {
			System.out.println("\n------Mostrando ingredientes basicos necesarios------\n");
			System.out.println(r.getIngredienteCompleto());
		} else {
			System.out.println("Receta no encontrada.");
		}
		pausar(scanner);
		limpiarConsola();
	}

	private void mostrarArbolDeCrafteo(Scanner scanner) {
		limpiarConsola();
		System.out.print("Ingrese nombre de receta: ");
		String nombre = scanner.nextLine();
		Receta r = parser.getRecetaPorNombre(nombre);
		limpiarConsola();
		if (r != null) {
			System.out.println("\n------Mostrando árbol de crafteo------\n");
			System.out.println(r.getArbolDeCrafteo());
		} else {
			System.out.println("\nReceta no encontrada.");
		}
		pausar(scanner);
		limpiarConsola();

	}

	private void craftearSinCatalizador(Scanner scanner) {
		limpiarConsola();
		System.out.print("Nombre de receta a craftear: ");
		String nombre = scanner.nextLine();
		Receta receta = parser.getRecetaPorNombre(nombre);
		limpiarConsola();
		if (receta != null) {
			if (jugador.craftearSimple(receta)) {
				System.out.println("Crafteo de " + receta.getNombre() + " exitoso.");
				System.out.println("¡Crafteo exitoso " + this.jugador.getNombre() + "! Se produjo: "
						+ receta.getNombre() + " x " + receta.getCantidadProducida());
			} else {
				System.out.println("Crafteo de " + receta.getNombre() + " no exitoso");
				System.out.println("Ingrediente faltante para:");
				System.out.println(receta.getIngredientesFaltantes(inventario));
			}
		} else {
			System.out.println("Receta no encontrada.");
		}
		pausar(scanner);
		limpiarConsola();
	}

	private void craftearConCatalizador(Scanner scanner) {
		limpiarConsola();
		System.out.print("Nombre de receta a craftear: ");
		String nombre = scanner.nextLine();
		Receta receta = parser.getRecetaPorNombre(nombre);
		if (receta != null) {
			System.out.print("Nombre del catalizador (fuego/masa madre): ");
			String nombreCata = scanner.nextLine();
			Catalizador catalizador = inventario.getCatalizador(nombreCata);
			if (catalizador != null) {
				if (jugador.craftearSimple(receta, catalizador)) {
					System.out.println("Crafteado con catalizador: " + receta.getNombre() + " exitoso.");
					System.out.println("¡Crafteo exitoso " + this.jugador.getNombre() + "! Se produjo: "
							+ receta.getNombre() + " x " + receta.getCantidadProducida());
				} else {
					System.out.println("Crafteado con catalizador " + receta.getNombre() + " no exitoso");
					System.out.println("Ingrediente faltante para:");
					System.out.println(receta.getIngredientesFaltantes(inventario));
				}
			} else {
				System.out.println("Catalizador no disponible o no válido.");
			}
		} else {
			System.out.println("Receta no encontrada.");
		}
		pausar(scanner);
		limpiarConsola();
	}

	private void queIngredientesFaltanParaReceta(Scanner scanner) {
		limpiarConsola();
		System.out.print("Nombre de receta: ");
		String nombre = scanner.nextLine();
		Receta receta = parser.getRecetaPorNombre(nombre);
		limpiarConsola();
		if (receta != null) {
			System.out.println("------Ingredientes faltantes para:------");
			System.out.println(receta.getIngredientesFaltantes(this.inventario));
		} else {
			System.out.println("Receta no encontrada.");
		}
		pausar(scanner);
		limpiarConsola();
	}

	private void queIngredientesBasicosFaltanParaReceta(Scanner scanner) {
		System.out.print("Nombre de receta: ");
		String nombre = scanner.nextLine();
		Receta receta = parser.getRecetaPorNombre(nombre);
		if (receta != null) {
			System.out.println("------Ingredientes basicos faltantes para:------");
			System.out.println(receta.getIngredientesBasicosFaltantes(this.inventario));
		} else {
			System.out.println("Receta no encontrada.");
		}
	}

	public void limpiarConsola() {
		System.out.println("\n".repeat(50));
	}

	private void pausar(Scanner scanner) {
		System.out.println("Presioná Enter para continuar...");
		scanner.nextLine();
	}

	private void posiblesCrafteos(Scanner scanner) {
		PrologConector prolog = new PrologConector();
		try {
			prolog.crearArchivoPL("PuedoHacer.pl", this.inventario, parser);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (prolog.cargarArchivo("PuedoHacer.pl")) {
			System.out.println("Archivo cargado correctamente");
			List<String> productos = prolog.obtenerProductosPosibles();
			limpiarConsola();
			System.out.println("------Ingredientes que puedo craftear------");
			for (String p : productos) {
				System.out.println("- " + p);
			}
		} else {
			System.out.println("Error al cargar archivo Prolog.");
		}
		pausar(scanner);
		limpiarConsola();
	}
}
