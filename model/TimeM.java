package model;

public class TimeM {
	private int id;
	private String Nome, Data, valor_tag, HRInicio, HRFim, Descricao;

	public enum Tag {
		Trabalho("Trabalho"), Estudo("Estudo"), Comer("Comer"), Dormir("Dormir"),
		Lazer("Lazer"), Treino("Treino"), Outros("Outros");

		private String desc;

		Tag(String desc) {
			this.desc = desc;
		}

		public String getTag() {
			return desc;
		}
	}

	public int getId() { return id; }
	public void setId(int id) { this.id = id; }

	public String getvalor_tag() { return valor_tag; }
	public void setvalor_tag(String _valor_tag) { valor_tag = _valor_tag; }

	public String getNome() { return Nome; }
	public void setNome(String nome) { Nome = nome; }

	public String getData() { return Data; }
	public void setData(String data) { Data = data; }

	public String getHRInicio() { return HRInicio; }
	public void setHRInicio(String hrInicio) { HRInicio = hrInicio; }

	public String getHRFim() { return HRFim; }
	public void setHRFim(String hRFim) { HRFim = hRFim; }

	public String getDescricao() { return Descricao; }
	public void setDescricao(String descricao) { Descricao = descricao; }

	// texto exibido na JList (uma linha por lembrete, sem estilo)
	@Override
	public String toString() {
		String desc = (Descricao == null || Descricao.isEmpty()) ? "-" : Descricao;
		return "Nome: " + Nome + " | Hora: " + HRInicio + " - " + HRFim + " | Descrição: " + desc;
	}
}
