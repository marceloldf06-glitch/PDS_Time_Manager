package main;

import java.awt.EventQueue;

import controller.TimeController;
import model.TimeBD;
import model.TimeM;
import view.TimeManagerJanela;

public class Main {
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			TimeManagerJanela janela = new TimeManagerJanela();
			TimeM model = new TimeM();
			TimeBD bd = new TimeBD();
			TimeController controller = new TimeController(janela, bd, model);
			controller.iniciar();
		});
	}
}
