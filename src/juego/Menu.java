package juego;

import java.util.Scanner;

public class Menu {
	private Jugador jugador;
	private Inventario inventario;
	private ParseoJson parser;
	private Registro registro;

	public Menu(Jugador jugador, Inventario inventario, ParseoJson parser, Registro registro) {
		this.jugador = jugador;
		this.inventario = inventario;
		this.parser = parser;
		this.registro = registro;
	}

	public void mostrarMenu() {
		Scanner scanner = new Scanner(System.in);
		int opcion;

		do {

			limpiarConsola();
			System.out.println("------ MENÚ PRINCIPAL ------");
			System.out.println("1. Mostrar inventario completo");
			System.out.println("2. Mostrar inventario disponible");
			System.out.println("3. Mostrar todas las recetas");
			System.out.println("4. Ver una receta en detalle");
			System.out.println("5. Craftear receta (sin catalizador)");
			System.out.println("6. Craftear receta (con catalizador)");
			System.out.println("0. Salir");
			System.out.print("Elige una opción: ");
			opcion = scanner.nextInt();
			scanner.nextLine();

			switch (opcion) {
			case 1 -> inventario.mostrarInventario();
			case 2 -> inventario.mostrarInventarioDisponible();
			case 3 -> mostrarTodasLasRecetas();
			case 4 -> verRecetaEnDetalle(scanner);
			case 5 -> craftearSinCatalizador(scanner);
			case 6 -> craftearConCatalizador(scanner);
			case 7 -> registro.guardarRegistroTemporal();
			case 0 -> System.out.println("¡Juego finalizado!");
			default -> System.out.println("Opción inválida.");

			}

		} while (opcion != 0);
	}

	private void mostrarTodasLasRecetas() {
		limpiarConsola();
		System.out.println("Recetas disponibles:");
		for (Receta r : parser.getRecetas()) {
			System.out.println("- " + r.getNombre());
		}
	}

	private void verRecetaEnDetalle(Scanner scanner) {
		limpiarConsola();
		System.out.print("Ingrese nombre de receta: ");
		String nombre = scanner.nextLine();
		Receta r = parser.getRecetaPorNombre(nombre);
		if (r != null) {
			System.out.println(r.getArbolDeCrafteo());
		} else {
			System.out.println("Receta no encontrada.");
		}
	}

	private void craftearSinCatalizador(Scanner scanner) {
		limpiarConsola();
		System.out.print("Nombre de receta a craftear: ");
		String nombre = scanner.nextLine();
		Receta receta = parser.getRecetaPorNombre(nombre);
		if (receta != null) {
			boolean exito = jugador.craftear(receta);
			if (exito) {
				System.out.println("Crafteo de " + receta.getNombre() + "exitoso.");
			}
		} else {
			System.out.println("Receta no encontrada.");
		}
	}

	private void craftearConCatalizador(Scanner scanner) {
		limpiarConsola();
		System.out.print("Nombre de receta a craftear: ");
		String nombre = scanner.nextLine();
		Receta receta = parser.getRecetaPorNombre(nombre);
		if (receta != null) {
			System.out.print("Nombre del catalizador (catalizador_fuego/catalizador_masa_madre): ");
			String nombreCata = scanner.nextLine();
			Catalizador catalizador = inventario.getCatalizador(nombreCata);
			if (catalizador != null) {
				boolean exito = jugador.craftear(receta, catalizador);
				if (exito) {
					System.out.println("Crafteado con catalizador: " + receta.getNombre());
				}
			} else {
				System.out.println("Catalizador no disponible o no válido.");
			}
		} else {
			System.out.println("Receta no encontrada.");
		}
	}

	public void limpiarConsola() {
		try {
			if (System.getProperty("os.name").contains("Windows")) {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} else {
				System.out.print("\033[H\033[2J");
				System.out.flush();
			}
		} catch (Exception e) {
			System.out.println("\n".repeat(50));
		}
	}
}
