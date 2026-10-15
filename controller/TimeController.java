package controller;

import view.*;
import model.*;

public class TimeController {

	private TimeManagerJanela view;
	private TimeBD bd;
	private TimeM model;
	public TimeController(TimeManagerJanela view, TimeBD bd, TimeM model) {
		this.view = view;
		this.bd = bd;
		this.model = model;
	}
	
	public void AtualizarValores() {
		model.setData();
		model.setHRInicio(Integer.parseInt(view.getHrInicio()));
		model.setHRFim(Integer.parseInt(view.getHrFim()));
		model.setNome(null);
		
	}
	public void adicionar() {
		bd.salvar(model);
}
}	
