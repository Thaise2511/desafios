package br.edu.ifpr.restful.beans;

import javax.ws.rs.FormParam;

public class DesafioBean {
	@FormParam("id")
	private Integer id;

	@FormParam("nivel")
	private String nivel;

	@FormParam("descricao")
	private String descricao;

	public DesafioBean() {}
	public DesafioBean(Integer id, String nivel, String descricao) {
		this.id = id;
		this.nivel = nivel;
		this.descricao = descricao;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNivel() {
		return nivel;
	}
	public void setNivel(String nivel) {
		this.nivel = nivel;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nivel == null) ? 0 : nivel.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DesafioBean other = (DesafioBean) obj;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nivel == null) {
			if (other.nivel != null)
				return false;
		} else if (!nivel.equals(other.nivel))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Desafio [Id=" + id + ", Nível=" + nivel + ", Descrição=" + descricao + "]";
	}

	
}
