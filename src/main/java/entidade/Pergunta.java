package entidade;

import java.util.Date;
import java.util.List;

public class Pergunta {

	private int id;
	private String descricao;
	private Date dataCriacao;
	private Usuario usuario;
	private String ativo;
	private List<Resposta> respostas;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getAtivo() {
		return ativo;
	}

	public void setAtivo(String ativo) {
		this.ativo = ativo;
	}

	public List<Resposta> getRespostas() {
		return respostas;
	}

	public void setRespostas(List<Resposta> respostas) {
		this.respostas = respostas;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		Pergunta perguntaEntrada = (Pergunta) obj;
		if(perguntaEntrada.getId() == this.getId()) {
			return true;
		}else {
			return false;
		}
	}

	public Pergunta() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Pergunta(int id, String descricao, Date dataCriacao, Usuario usuario, String ativo,
			List<Resposta> respostas) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.dataCriacao = dataCriacao;
		this.usuario = usuario;
		this.ativo = ativo;
		this.respostas = respostas;
	}

}
