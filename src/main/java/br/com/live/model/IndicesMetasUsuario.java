package br.com.live.model;

public class IndicesMetasUsuario {
	int id;
	float totalLancado;
	float totalMeta;
	float percIndice;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getTotalLancado() {
		return totalLancado;
	}
	public void setTotalLancado(float totalLancado) {
		this.totalLancado = totalLancado;
	}
	public float getTotalMeta() {
		return totalMeta;
	}
	public void setTotalMeta(float totalMeta) {
		this.totalMeta = totalMeta;
	}
	public float getPercIndice() {
		return percIndice;
	}
	public void setPercIndice(float percIndice) {
		this.percIndice = percIndice;
	}
	
	IndicesMetasUsuario() {
		
	}
	
	public IndicesMetasUsuario(int id, float totalLancado, float totalMeta, float percIndice) {
		this.id = id;
		this.totalLancado = totalLancado;
		this.totalMeta = totalMeta;
		this.percIndice = percIndice;
	}
}
