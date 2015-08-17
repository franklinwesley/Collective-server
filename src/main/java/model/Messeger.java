package model;

public class Messeger {
	private String origem;
	private String destino;
	private String msg;
	
	public Messeger(String origem, String destino, String msg) {
		this.origem = origem;
		this.destino = destino;
		this.msg = msg;
	}

	public String getOrigem() {
		return origem;
	}

	public String getMsg() {
		return msg;
	}
	
	public String getDestino() {
		return destino;
	}
}
