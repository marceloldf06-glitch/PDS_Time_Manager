package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;

public class TimeManagerJanela extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNome;
	private JTextField txtHr_inicio;
	private JTextField txtHr_fim;
	private JTextField txtDescricao;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JLabel lblNewLabel_4;
	private JComboBox tag;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TimeManagerJanela frame = new TimeManagerJanela();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public TimeManagerJanela() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 745, 691);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[160px,grow][160px,grow][160px][160px][160px]",
				"[100px][100px][100px][100px][100px][100px]"));

		lblNewLabel = new JLabel("Nome:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(lblNewLabel, "cell 0 0,alignx trailing");

		txtNome = new JTextField();
		contentPane.add(txtNome, "cell 1 0 2 1,growx");
		txtNome.setColumns(10);

		lblNewLabel_1 = new JLabel("Tag:");
		contentPane.add(lblNewLabel_1, "cell 0 1,alignx trailing");

		tag = new JComboBox();
		contentPane.add(tag, "cell 1 1 2 1,growx");

		lblNewLabel_2 = new JLabel("Horario inicial");
		contentPane.add(lblNewLabel_2, "cell 0 2,alignx trailing");

		txtHr_inicio = new JTextField();
		contentPane.add(txtHr_inicio, "cell 1 2 2 1,growx");
		txtHr_inicio.setColumns(10);

		lblNewLabel_3 = new JLabel("Horario final");
		contentPane.add(lblNewLabel_3, "cell 0 3,alignx trailing");

		txtHr_fim = new JTextField();
		contentPane.add(txtHr_fim, "cell 1 3 2 1,growx");
		txtHr_fim.setColumns(10);

		lblNewLabel_4 = new JLabel("Descrição");
		contentPane.add(lblNewLabel_4, "cell 0 4,alignx trailing");

		txtDescricao = new JTextField();
		contentPane.add(txtDescricao, "cell 1 4 2 1,growx");
		txtDescricao.setColumns(10);

		JButton btnAdicionar = new JButton("Adicionar");
		contentPane.add(btnAdicionar, "cell 1 5,alignx center,aligny center");

		JButton btnMostrar = new JButton("Mostrar");
		btnMostrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.add(btnMostrar, "cell 2 5,alignx center,aligny center");

		JButton btnRemover = new JButton("Remover");
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.add(btnRemover, "cell 3 5,alignx center,aligny center");

	}

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

	public String getNome() {
		return txtNome.getText();
	}

	public String getHrInicio() {
		return txtHr_inicio.getText();
	}

	public String getHrFim() {
		return txtHr_inicio.getText();
	}

	public String txtDescricao() {
		return txtDescricao.getText();
	}
}
