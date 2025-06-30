package juego;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registro {
	private List<String> historial = new ArrayList<>();
	private static final String PATH = "historial_de_crafteo/";
	private static final String NOMBRE = "HISTORIAL_DE_CRAFTEO.txt";
	private FileWriter fileArch = null;
	private PrintWriter printWriter = null;
	private LocalDateTime tiempoEnElJuego;

	public Registro() {
		super();
		this.tiempoEnElJuego = LocalDateTime.now();
	}

	public void agregarRegistroTemporal(String registro) {
		historial.add(tiempoEnElJuego.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + "\n" + registro);
		Pattern p = Pattern.compile("-?\\d+(\\.\\d+)?");
		Matcher m = p.matcher(registro);
		double minutos = 0;

		while (m.find()) {
			minutos = Double.parseDouble(m.group());
		}
		this.tiempoEnElJuego = this.tiempoEnElJuego.plusSeconds((long) (minutos * 60));
	}

	public void guardarRegistroTemporal() {
		try {
			fileArch = new FileWriter(PATH + NOMBRE, true);
			printWriter = new PrintWriter(fileArch);
			for (String registro : this.historial) {
				printWriter.println(registro);
			}
			this.historial.clear();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fileArch != null) {
				try {
					fileArch.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
