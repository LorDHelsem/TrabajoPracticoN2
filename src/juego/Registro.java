package juego;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Registro {
	private List<String> historial = new ArrayList<String>();
	private static final String PATH = "historial_de_crafteo/";
	private static final String NOMBRE = "HISTORIAL_DE_CRAFTEO.txt";
	private FileWriter fileArch = null;
	private PrintWriter printWriter = null;

	public Registro() {
		super();
	}

	public void agregarRegistroTemporal(String registro) {
		historial.add(LocalDateTime.now().getHour() + " : " + LocalDateTime.now().getMinute() + " : "
				+ LocalDateTime.now().getSecond() + "\n" + registro);
	}
	/*
	 * 2 opciones 
	 * A un HISTORIAL_DE_CRAFTEO_(fecha del dia).txt adentro tiene las horas de esa fecha de cada crafteo
	 * B un HISTORIAL_DE_CRAFTEO.txt adentro fecha hora de cada crafteo
	 * */
	
	public void guardarRegistroTemporal() {
		try {
			fileArch = new FileWriter(PATH + NOMBRE, true);
			printWriter = new PrintWriter(fileArch);
			for (String registro : this.historial) {
				printWriter.println(registro);
			}
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
