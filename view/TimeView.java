package view;

import java.awt.event.ActionListener;
import java.util.List;

import model.TimeM;

/**
 * Contrato que o controller precisa da tela.
 * Permite trocar a janela real (Swing) por uma tela falsa nos testes.
 */
public interface TimeView {
	void addAdicionarListener(ActionListener l);
	void addMostrarListener(ActionListener l);
	void addRemoverListener(ActionListener l);

	void setVisible(boolean visivel);
	void setLista(List<TimeM> itens);
	TimeM getSelecionado();
	void mostrarDetalhes(TimeM t);

	void mostrarMensagem(String texto);
	void mostrarErro(String texto);
	void LimparCampos();

	String getNome();
	String getHrInicio();
	String getHrFim();
	String getDescricao();
	String getData();
	String getTag();
}
