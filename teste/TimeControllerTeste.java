package teste;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import controller.TimeController;
import model.TimeBD;
import model.TimeM;
import view.TimeView;

/**
 * Testa o TimeController sem abrir nenhuma tela e sem precisar do MySQL.
 * Executar: java -cp . teste.TimeControllerTeste
 */
public class TimeControllerTeste {

	// ================= TELA FALSA =================
	static class TelaFalsa implements TimeView {
		// "campos digitados" pelo teste
		String nome = "", hrInicio = "", hrFim = "", descricao = "", tag = "Trabalho";
		TimeM selecionado;

		// o que a tela recebeu do controller
		List<TimeM> lista = new ArrayList<>();
		List<String> mensagens = new ArrayList<>();
		List<String> erros = new ArrayList<>();
		TimeM detalhesMostrado;
		boolean visivel, camposLimpos;

		private ActionListener adicionar, mostrar, remover;

		public void addAdicionarListener(ActionListener l) { adicionar = l; }
		public void addMostrarListener(ActionListener l) { mostrar = l; }
		public void addRemoverListener(ActionListener l) { remover = l; }

		// simulam o clique nos botões
		void clicarAdicionar() { adicionar.actionPerformed(null); }
		void clicarMostrar() { mostrar.actionPerformed(null); }
		void clicarRemover() { remover.actionPerformed(null); }

		void preencher(String nome, String ini, String fim, String desc) {
			this.nome = nome; this.hrInicio = ini; this.hrFim = fim; this.descricao = desc;
		}

		public void setVisible(boolean v) { visivel = v; }
		public void setLista(List<TimeM> itens) { lista = new ArrayList<>(itens); }
		public TimeM getSelecionado() { return selecionado; }
		public void mostrarDetalhes(TimeM t) { detalhesMostrado = t; }
		public void mostrarMensagem(String t) { mensagens.add(t); }
		public void mostrarErro(String t) { erros.add(t); }
		public void LimparCampos() { camposLimpos = true; nome = hrInicio = hrFim = descricao = ""; }
		public String getNome() { return nome; }
		public String getHrInicio() { return hrInicio; }
		public String getHrFim() { return hrFim; }
		public String getDescricao() { return descricao; }
		public String getData() { return "2026-01-01"; }
		public String getTag() { return tag; }
	}

	// ================= BANCO FALSO (em memória) =================
	static class BancoFalso extends TimeBD {
		final List<TimeM> dados = new ArrayList<>();
		int proximoId = 1;
		boolean falhar;

		@Override
		public void salvar(TimeM t) {
			if (falhar) throw new RuntimeException("Erro ao gravar: banco indisponivel");
			TimeM c = new TimeM();
			c.setId(proximoId++);
			c.setNome(t.getNome());
			c.setHRInicio(t.getHRInicio());
			c.setHRFim(t.getHRFim());
			c.setvalor_tag(t.getvalor_tag());
			c.setData(t.getData());
			c.setDescricao(t.getDescricao());
			dados.add(c);
		}

		@Override
		public void excluir(int id) { dados.removeIf(x -> x.getId() == id); }

		@Override
		public List<TimeM> listarTodos() {
			if (falhar) throw new RuntimeException("Erro ao listar: banco indisponivel");
			return new ArrayList<>(dados);
		}

		@Override
		public int contar() { return dados.size(); }
	}

	// ================= MINI FRAMEWORK =================
	static int ok = 0, falhas = 0;
	static String atual;

	static void verifica(boolean cond, String msg) {
		if (cond) { ok++; }
		else { falhas++; System.out.println("   [FALHOU] " + atual + " -> " + msg); }
	}

	static void igual(Object esperado, Object obtido, String msg) {
		verifica(esperado == null ? obtido == null : esperado.equals(obtido),
				msg + " (esperado: " + esperado + ", obtido: " + obtido + ")");
	}

	interface Teste { void rodar(TelaFalsa tela, BancoFalso bd); }

	static void teste(String nome, Teste t) {
		atual = nome;
		int antes = falhas;
		TelaFalsa tela = new TelaFalsa();
		BancoFalso bd = new BancoFalso();
		new TimeController(tela, bd, new TimeM());
		try {
			t.rodar(tela, bd);
		} catch (Throwable e) {
			falhas++;
			System.out.println("   [EXCECAO] " + nome + " -> " + e);
		}
		System.out.println((falhas == antes ? "[OK]     " : "[FALHOU] ") + nome);
	}

	// ================= TESTES =================
	public static void main(String[] args) {

		teste("iniciar mostra a tela e carrega a lista", (tela, bd) -> {
			TimeM m = new TimeM(); m.setNome("Existente"); bd.salvar(m);
			new TimeController(tela, bd, new TimeM()).iniciar();
			verifica(tela.visivel, "tela deveria ficar visivel");
			igual(1, tela.lista.size(), "tamanho da lista");
		});

		teste("adicionar com dados validos salva e limpa campos", (tela, bd) -> {
			tela.preencher("Reuniao", "08:30", "09:30", "Semanal");
			tela.tag = "Trabalho";
			tela.clicarAdicionar();
			igual(1, bd.contar(), "registros no banco");
			igual("Reuniao", bd.dados.get(0).getNome(), "nome salvo");
			igual("Trabalho", bd.dados.get(0).getvalor_tag(), "tag salva");
			igual("2026-01-01", bd.dados.get(0).getData(), "data salva");
			igual("Salvo com sucesso!", tela.mensagens.get(0), "mensagem");
			verifica(tela.camposLimpos, "campos deveriam ser limpos");
			igual(1, tela.lista.size(), "lista da tela atualizada");
			verifica(tela.erros.isEmpty(), "nao deveria haver erros");
		});

		teste("adicionar remove espacos extras", (tela, bd) -> {
			tela.preencher("  Estudar  ", " 10:00 ", " 11:00 ", "  Java  ");
			tela.clicarAdicionar();
			igual("Estudar", bd.dados.get(0).getNome(), "nome");
			igual("10:00", bd.dados.get(0).getHRInicio(), "hora inicio");
			igual("Java", bd.dados.get(0).getDescricao(), "descricao");
		});

		teste("adicionar sem nome mostra erro", (tela, bd) -> {
			tela.preencher("   ", "08:00", "09:00", "");
			tela.clicarAdicionar();
			igual("Informe o nome.", tela.erros.get(0), "mensagem de erro");
			igual(0, bd.contar(), "nada deve ser salvo");
		});

		teste("adicionar com horario em formato invalido", (tela, bd) -> {
			tela.preencher("Teste", "8h", "9h", "");
			tela.clicarAdicionar();
			igual("Use o formato HH:mm nos horários (ex: 08:30).", tela.erros.get(0), "mensagem de erro");
			igual(0, bd.contar(), "nada deve ser salvo");
		});

		teste("adicionar com horario vazio", (tela, bd) -> {
			tela.preencher("Teste", "", "", "");
			tela.clicarAdicionar();
			verifica(tela.erros.size() == 1, "deveria mostrar 1 erro");
			igual(0, bd.contar(), "nada deve ser salvo");
		});

		teste("adicionar com hora fim menor que inicio", (tela, bd) -> {
			tela.preencher("Teste", "10:00", "09:00", "");
			tela.clicarAdicionar();
			igual("O horário final deve ser maior que o inicial.", tela.erros.get(0), "mensagem de erro");
			igual(0, bd.contar(), "nada deve ser salvo");
		});

		teste("adicionar com hora fim igual ao inicio", (tela, bd) -> {
			tela.preencher("Teste", "10:00", "10:00", "");
			tela.clicarAdicionar();
			igual(1, tela.erros.size(), "deveria mostrar erro");
			igual(0, bd.contar(), "nada deve ser salvo");
		});

		teste("adicionar com hora inexistente (25:00)", (tela, bd) -> {
			tela.preencher("Teste", "25:00", "26:00", "");
			tela.clicarAdicionar();
			igual(1, tela.erros.size(), "deveria mostrar erro");
		});

		teste("erro do banco ao salvar e mostrado na tela", (tela, bd) -> {
			bd.falhar = true;
			tela.preencher("Teste", "08:00", "09:00", "");
			tela.clicarAdicionar();
			igual("Erro ao gravar: banco indisponivel", tela.erros.get(0), "mensagem de erro");
			verifica(!tela.camposLimpos, "campos nao devem ser limpos em caso de erro");
		});

		teste("erro do banco ao listar e mostrado na tela", (tela, bd) -> {
			bd.falhar = true;
			new TimeController(tela, bd, new TimeM()).atualizarLista();
			verifica(tela.erros.get(0).startsWith("Erro ao listar"), "mensagem de erro");
		});

		teste("remover sem selecao mostra erro", (tela, bd) -> {
			tela.selecionado = null;
			tela.clicarRemover();
			igual("Selecione um item da lista para remover.", tela.erros.get(0), "mensagem de erro");
		});

		teste("remover item selecionado", (tela, bd) -> {
			tela.preencher("A", "08:00", "09:00", ""); tela.clicarAdicionar();
			tela.preencher("B", "10:00", "11:00", ""); tela.clicarAdicionar();
			tela.selecionado = bd.dados.get(0);
			tela.clicarRemover();
			igual(1, bd.contar(), "registros restantes");
			igual("B", bd.dados.get(0).getNome(), "item que sobrou");
			igual(1, tela.lista.size(), "lista da tela atualizada");
		});

		teste("mostrar detalhes sem selecao mostra erro", (tela, bd) -> {
			tela.clicarMostrar();
			igual("Selecione um lembrete da lista para ver os detalhes.", tela.erros.get(0), "mensagem de erro");
			verifica(tela.detalhesMostrado == null, "nao deve abrir detalhes");
		});

		teste("mostrar detalhes com selecao", (tela, bd) -> {
			TimeM sel = new TimeM(); sel.setNome("Treino");
			tela.selecionado = sel;
			tela.clicarMostrar();
			verifica(tela.detalhesMostrado == sel, "deveria mostrar o item selecionado");
			verifica(tela.erros.isEmpty(), "nao deveria haver erros");
		});

		teste("TimeM.toString usa '-' quando nao ha descricao", (tela, bd) -> {
			TimeM m = new TimeM();
			m.setNome("X"); m.setHRInicio("08:00"); m.setHRFim("09:00"); m.setDescricao("");
			verifica(m.toString().contains("Descrição: -"), "toString: " + m);
		});

		System.out.println("\nResultado: " + ok + " verificacoes OK, " + falhas + " falha(s).");
		if (falhas > 0) System.exit(1);
	}
}
