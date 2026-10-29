package model;

public class TimeM {
	private String Nome, Data, valor_tag;
	private int HRInicio, HRFim;
	public enum Tag {
	Trabalho("Trabalho"),
	Estudo("Estudo"),
	Comer("Comer"),
	Dormir("Dormir"),
	Lazer("Lazer"),
	Treino("Treino"),
	Outros("Outros")
	;
	private  String desc;
		Tag(String desc){
			this.desc = desc;
		}
		public String getTag() {
			return desc;
		}
	};
	public String getvalor_tag() {
		return valor_tag;
	}
	public void setvalor_tag(String _valor_tag) {
		valor_tag = _valor_tag;
	}
	public String getNome() {
		return Nome;
	}

	public void setNome(String nome) {
		Nome = nome;
	}

	public String getData() {
		return Data;
	}

	public void setData(String data) {
		Data = data;
	}

	public int getHRInicio() {
		return HRInicio;
	}

	public void setHRInicio(int hrInicio) {
		HRInicio = hrInicio;
	}

	public int getHRFim() {
		return HRFim;
	}

	public void setHRFim(int hRFim) {
		HRFim = hRFim;
	}
}
