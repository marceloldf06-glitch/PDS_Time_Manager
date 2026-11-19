package view;

import java.awt.BorderLayout;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import model.TimeM;

public class DetalhesJanela extends JDialog {
	private static final long serialVersionUID = 1L;

	public DetalhesJanela(Window dono, TimeM t) {
		super(dono, "Detalhes do lembrete", ModalityType.APPLICATION_MODAL);
		setSize(360, 260);
		setLocationRelativeTo(dono);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		String descricao = (t.getDescricao() == null || t.getDescricao().isEmpty()) ? "-" : t.getDescricao();

		JTextArea texto = new JTextArea();
		texto.setEditable(false);
		texto.setLineWrap(true);
		texto.setWrapStyleWord(true);
		texto.setBorder(new EmptyBorder(10, 10, 10, 10));
		texto.setText(
				"Nome:       " + t.getNome() + "\n"
				+ "Tag:        " + t.getvalor_tag() + "\n"
				+ "Hora:       " + t.getHRInicio() + " - " + t.getHRFim() + "\n"
				+ "Descrição:  " + descricao);

		JButton btnFechar = new JButton("Fechar");
		btnFechar.addActionListener(e -> dispose());
		JPanel rodape = new JPanel();
		rodape.add(btnFechar);

		getContentPane().add(new JScrollPane(texto), BorderLayout.CENTER);
		getContentPane().add(rodape, BorderLayout.SOUTH);
	}
}
