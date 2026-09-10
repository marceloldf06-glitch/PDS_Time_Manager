package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TimeBD {
	// Os tres dados da conexao ficam num lugar so. Mudou o servidor,
	// muda uma linha - nao quatro.
	private static final String URL = "jdbc:mysql://localhost:3306/hora";
	private static final String USER = "aluno_cd";
	private static final String SENHA = "aluno_pw";

	// Conexao NOVA a cada chamada. Guardar uma unica conexao num atributo
	// parece economico, mas o servidor a derruba por inatividade e a
	// aplicacao inteira para de funcionar ate ser reiniciada.
	private Connection abrir() throws SQLException {
		return DriverManager.getConnection(URL, USER, SENHA);
	}

	public void salvar(TimeM t) {
		// ---- REGRAS: sempre antes de gravar --------------------------

		//////// tratamento de erro acima
		String sql = "INSERT INTO cadastro (nome, hora_inicio, hora_fim, tag, Data) VALUES (?, ?, ?, ?, ?)";
		// try-with-resources: conexao e comando sao fechados sozinhos,
		// mesmo se der excecao no meio.
		try (Connection con = abrir(); PreparedStatement ps = con.prepareStatement(sql)) {

			// Indices comecam em 1, nunca em 0.
			// O valor viaja SEPARADO do comando: por isso um nome como
			// O'Brien grava sem quebrar o SQL.
			ps.setString(1, t.getNome());
			ps.setInt(2, t.getHRInicio());
			ps.setInt(3, t.getHRFim());
			ps.setInt(4, t.getTag());
			ps.setString(5, t.getData());
			ps.executeUpdate();
		} catch (SQLException erro) {

			throw new RuntimeException("Erro ao gravar: " + erro.getMessage(), erro);

		}
	}

	public boolean existeidhorarios(String Idhorario) {
		String sql = "SELECT idhorarios FROM horarios WHERE idhorarios = ?";
		try (Connection con = abrir(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, Idhorario.trim());
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next(); // achou pelo menos uma linha?

			}
		} catch (SQLException erro) {

			throw new RuntimeException("Erro ao consultar: " + erro.getMessage(), erro);

		}
	}

	public int contar() {
		String sql = "SELECT COUNT(*) FROM horarios";
		try (Connection con = abrir();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			if (rs.next()) {
				return rs.getInt(1); // primeira coluna do resultado

			}
			return 0;

		} catch (SQLException erro) {

			throw new RuntimeException("Erro ao contar: " + erro.getMessage(), erro);

		}
	}

	public List<TimeM> listarTodos() {
		String sql = "SELECT nome, hora_inicio, hora_fim  FROM horarios ORDER BY nome";
		List<TimeM> lista = new ArrayList<>();
		try (Connection con = abrir();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {

				TimeM e = new TimeM();
				e.setNome(rs.getString("nome"));
				e.setHRInicio(Integer.parseInt((rs.getString("hora_inicio"))));
				e.setHRFim(Integer.parseInt((rs.getString("hora_fim"))));
				lista.add(e);
			}
		} catch (SQLException erro) {

			throw new RuntimeException("Erro ao listar: " + erro.getMessage(), erro);

		}
		// Devolve objetos, e nao o ResultSet: ele morre quando a conexao fecha.
		return lista;
	}

	/** Teste de ambiente: rode esta classe antes de depurar qualquer botao. */
	public static void main(String[] args) {
		TimeBD bd = new TimeBD();
		try (Connection con = bd.abrir()) {
			System.out.println("Conexao OK com " + con.getCatalog());
			System.out.println("Cadastros no banco: " + bd.contar());
		} catch (SQLException e) {
			System.out.println("Falha: " + e.getMessage());
		}
	}
}