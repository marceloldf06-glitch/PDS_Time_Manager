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

public class TimeManagerJanela extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNome;
	private JTextField txtTag;
	private JTextField txtHr_inicio;
	private JTextField txtHr_fim;
	private JTextField txtDescricao;

	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the frame.
	 */
	public TimeManagerJanela() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 745, 691);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[160px,grow][160px,grow][160px][160px][160px]",
				"[100px][100px][100px][100px][100px][100px]"));

		JButton btnAdicionar = new JButton("Adicionar");

		txtNome = new JTextField();
		contentPane.add(txtNome, "cell 0 1,growx");
		txtNome.setColumns(10);

		txtTag = new JTextField();
		contentPane.add(txtTag, "cell 0 2,growx");
		txtTag.setColumns(10);

		txtDescricao = new JTextField();
		contentPane.add(txtDescricao, "cell 1 2,growx");
		txtDescricao.setColumns(10);

		txtHr_inicio = new JTextField();
		contentPane.add(txtHr_inicio, "cell 0 3,growx");
		txtHr_inicio.setColumns(10);

		txtHr_fim = new JTextField();
		contentPane.add(txtHr_fim, "cell 1 3,growx");
		txtHr_fim.setColumns(10);
		contentPane.add(btnAdicionar, "cell 0 4,alignx center,aligny center");

		JButton btnMostrar = new JButton("Mostrar");
		btnMostrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.add(btnMostrar, "cell 2 4,alignx center,aligny center");

		JButton btnRemover = new JButton("Remover");
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.add(btnRemover, "cell 4 4,alignx center,aligny center");

	}

	public void mostrarMensagem(String texto) {
		JOptionPane.showMessageDialog(this, texto);
	}
	
	public void mostrar() {
		
	}
	
	public void LimparCampos() {
		txtNome.setText("");		
		txtTag.setText("");
		txtHr_inicio.setText("");
		txtHr_fim.setText("");
		txtDescricao.setText("");

	}
	
	public void fechar() {
		dispose();	
	}
}
