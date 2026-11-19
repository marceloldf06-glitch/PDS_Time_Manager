package view;

import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import model.TimeM;
import net.miginfocom.swing.MigLayout;

public class TimeManagerJanela extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNome;
	private JTextField txtHr_inicio;
	private JTextField txtHr_fim;
	private JTextField txtDescricao;
	private JComboBox<TimeM.Tag> tag;
	private JList<TimeM> list;
	private DefaultListModel<TimeM> listModel = new DefaultListModel<>();
	private JButton btnAdicionar, btnMostrar, btnRemover;

	public TimeManagerJanela() {
		setTitle("Time Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 745, 691);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[160px][160px][160px][160px][160px,grow]",
				"[100px,grow][100px,grow][100px,grow][100px,grow][100px,grow][100px,grow]"));

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(lblNome, "cell 0 0,alignx trailing");

		txtNome = new JTextField(10);
		contentPane.add(txtNome, "cell 1 0 2 1,growx");

		list = new JList<>(listModel);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		contentPane.add(new JScrollPane(list), "cell 3 0 2 5,grow");

		contentPane.add(new JLabel("Tag:"), "cell 0 1,alignx trailing");
		tag = new JComboBox<>(new DefaultComboBoxModel<>(TimeM.Tag.values()));
		contentPane.add(tag, "cell 1 1 2 1,growx");

		contentPane.add(new JLabel("Horario inicial (HH:mm)"), "cell 0 2,alignx trailing");
		txtHr_inicio = new JTextField(10);
		contentPane.add(txtHr_inicio, "cell 1 2 2 1,growx");

		contentPane.add(new JLabel("Horario final (HH:mm)"), "cell 0 3,alignx trailing");
		txtHr_fim = new JTextField(10);
		contentPane.add(txtHr_fim, "cell 1 3 2 1,growx");

		contentPane.add(new JLabel("Descrição"), "cell 0 4,alignx trailing");
		txtDescricao = new JTextField(10);
		contentPane.add(txtDescricao, "cell 1 4 2 1,growx");

		btnAdicionar = new JButton("Adicionar");
		contentPane.add(btnAdicionar, "cell 1 5,alignx center,aligny center");

		btnMostrar = new JButton("Mostrar");
		contentPane.add(btnMostrar, "cell 2 5,alignx center,aligny center");

		btnRemover = new JButton("Remover");
		contentPane.add(btnRemover, "cell 3 5,alignx center,aligny center");
	}

	// ---- ligação com o controller ----
	public void addAdicionarListener(ActionListener l) { btnAdicionar.addActionListener(l); }
	public void addMostrarListener(ActionListener l) { btnMostrar.addActionListener(l); }
	public void addRemoverListener(ActionListener l) { btnRemover.addActionListener(l); }

	public void setLista(List<TimeM> itens) {
		listModel.clear();
		for (TimeM t : itens) {
			listModel.addElement(t);
		}
	}

	public void mostrarDetalhes(TimeM t) {
		new DetalhesJanela(this, t).setVisible(true);
	}

	public TimeM getSelecionado() {
		return list.getSelectedValue();
	}

	// ---- mensagens ----
	public void mostrarMensagem(String texto) {
		JOptionPane.showMessageDialog(this, texto);
	}

	public void mostrarErro(String texto) {
		JOptionPane.showMessageDialog(this, texto, "Atenção", JOptionPane.WARNING_MESSAGE);
	}

	public void LimparCampos() {
		txtNome.setText("");
		txtHr_inicio.setText("");
		txtHr_fim.setText("");
		txtDescricao.setText("");
	}

	public void fechar() {
		dispose();
	}

	// ---- getters ----
	public String getNome() { return txtNome.getText(); }
	public String getHrInicio() { return txtHr_inicio.getText(); }
	public String getHrFim() { return txtHr_fim.getText(); } // antes retornava txtHr_inicio
	public String getDescricao() { return txtDescricao.getText(); }

	public String getData() {
		// formato ISO (yyyy-MM-dd) é o aceito por colunas DATE do MySQL
		return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

	public String getTag() {
		return tag.getSelectedItem().toString();
	}
}
