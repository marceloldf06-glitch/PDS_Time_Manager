package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import controller.*;
import model.*;
import view.*;

public class Main {
	public static void main(String[] args) {
		TimeManagerJanela janela = new TimeManagerJanela();
		TimeM model = new TimeM();
		TimeBD BD = new TimeBD();
		TimeController controller = new TimeController(janela, BD, model);
		BD.salvar(model);

	}
}