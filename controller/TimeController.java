package controller;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import model.TimeBD;
import model.TimeM;
import view.TimeManagerJanela;

public class TimeController {

	private final TimeManagerJanela view;
	private final TimeBD bd;
	private final TimeM model;

	public TimeController(TimeManagerJanela view, TimeBD bd, TimeM model) {
		this.view = view;
		this.bd = bd;
		this.model = model;

		// liga os botões da janela aos métodos do controller
		view.addAdicionarListener(e -> adicionar());
		view.addMostrarListener(e -> mostrar());
		view.addRemoverListener(e -> remover());
	}

	public void iniciar() {
		view.setVisible(true);
		mostrar();
	}

	public void AtualizarValores() {
		model.setId(0);
		model.setData(view.getData());
		model.setHRInicio(view.getHrInicio().trim());
		model.setHRFim(view.getHrFim().trim());
		model.setNome(view.getNome().trim());
		model.setvalor_tag(view.getTag());
		model.setDescricao(view.getDescricao().trim());
	}

	public void adicionar() {
		AtualizarValores();

		if (model.getNome().isEmpty()) {
			view.mostrarErro("Informe o nome.");
			return;
		}
		try {
			LocalTime ini = LocalTime.parse(model.getHRInicio());
			LocalTime fim = LocalTime.parse(model.getHRFim());
			if (!fim.isAfter(ini)) {
				view.mostrarErro("O horário final deve ser maior que o inicial.");
				return;
			}
		} catch (DateTimeParseException ex) {
			view.mostrarErro("Use o formato HH:mm nos horários (ex: 08:30).");
			return;
		}

		try {
			bd.salvar(model);
			view.mostrarMensagem("Salvo com sucesso!");
			view.LimparCampos();
			mostrar();
		} catch (RuntimeException ex) {
			view.mostrarErro(ex.getMessage());
		}
	}

	public void mostrar() {
		try {
			List<TimeM> lista = bd.listarTodos();
			view.setLista(lista);
		} catch (RuntimeException ex) {
			view.mostrarErro(ex.getMessage());
		}
	}

	public void remover() {
		TimeM selecionado = view.getSelecionado();
		if (selecionado == null) {
			view.mostrarErro("Selecione um item da lista para remover.");
			return;
		}
		try {
			bd.excluir(selecionado.getId());
			mostrar();
		} catch (RuntimeException ex) {
			view.mostrarErro(ex.getMessage());
		}
	}
}
