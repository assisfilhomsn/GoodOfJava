package dao;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import entidade.Curso;
import entidade.Perfil;
import entidade.Pergunta;
import entidade.Usuario;


public class PerguntaDAOImpl implements PerguntaDAO{

	private List<Pergunta> listaPerguntas;
	private List<Usuario> listaUsuario;
	private List<Perfil> listaPerfil;
	private List<Curso> listaCurso;
	
	private static PerguntaDAOImpl instanciaUnica = new PerguntaDAOImpl();
	
	public static PerguntaDAOImpl getInstance() {
		return instanciaUnica;
	}

	private PerguntaDAOImpl() {
		this.listaPerguntas = new ArrayList<Pergunta>();
		this.cargaInicialDados();
	}
	
	private void cargaInicialDados() {
		Perfil p1 = new Perfil();
		p1.setCodigo(1);
		p1.setDescricao("Gestor");
		
		Perfil p2 = new Perfil();
		p2.setCodigo(2);
		p2.setDescricao("Aluno");
		
		this.listaPerfil = new ArrayList<Perfil>();
		this.listaPerfil.add(p1);
		this.listaPerfil.add(p2);
		
		Curso c1 = new Curso();
		c1.setCodigo(1);
		c1.setDescricao("Java Modulo 3");
		
		Curso c2 = new Curso();
		c2.setCodigo(2);
		c2.setDescricao("Java Modulo 4");
		
		this.listaCurso = new ArrayList<Curso>();
		this.listaCurso.add(c1);
		this.listaCurso.add(c2);
		
		Usuario u1 = new Usuario();
		u1.setCpf("111111111");
		u1.setNome("Cleiton Silva");
		u1.setDataNascimento(new Date());
		u1.setSexo("H");
		u1.setAtivo("S");
		u1.setEmail("cleiton@gmail.com");
		u1.setCurso(c1);
		u1.setPerfil(p1);

		Usuario u2 = new Usuario();
		u2.setCpf("222222222");
		u2.setNome("Tonho das Mulas");
		u2.setDataNascimento(new Date());
		u2.setSexo("H");
		u2.setAtivo("S");
		u2.setEmail("Tonho@gmail.com");
		u2.setCurso(c2);
		u2.setPerfil(p2);
		
		this.listaUsuario = new ArrayList<Usuario>();
		this.listaUsuario.add(u1);
		this.listaUsuario.add(u2);

	}
	
	public boolean inserirPergunta(Pergunta pergunta) {

		boolean retorno = true;
		if(pergunta.getId() == 0) {
			pergunta.setId(proximoId());
		}
		
		if(existePergunta(pergunta) == null) {
			pergunta.setUsuario(this.existeUsuario(pergunta.getUsuario()));
			this.listaPerguntas.add(pergunta);
		}else {
			return false;
		}
		
		return retorno;
	}

	
	public boolean alterarPergunta(Pergunta pergunta) {
		
		boolean retorno = true;
		Pergunta perguntaEncontrada = existePergunta(pergunta);
		if(perguntaEncontrada != null) {
			perguntaEncontrada.setDescricao(pergunta.getDescricao());
			perguntaEncontrada.setAtivo(pergunta.getAtivo());
		}else {
			retorno = false;
		}
		
		return retorno;
	}

	/**
	 * Metodo utilizado para verificar se existe uma pergunta com o mesmo id
	 * @param pergunta
	 * @return Pergunta
	 */
	private Pergunta existePergunta(Pergunta pergunta) {
		int index = this.listaPerguntas.indexOf(pergunta);
		if(index != -1) {
			return this.listaPerguntas.get(index);
		}else {
			return null;
		}
	}

	private int proximoId() {
		if(this.listaPerguntas.size() == 0) {
			return 1;
		}else {
			int ultimoId = 1;
			for (Pergunta pergunta : listaPerguntas) {
				if(pergunta.getId() > ultimoId) {
					ultimoId = pergunta.getId();
				}
			}
			return ++ultimoId;
		}
	}

	@Override
	public boolean removerPergunta(Pergunta pergunta) {
	
		Pergunta perguntaRemover = this.existePergunta(pergunta);
		if(perguntaRemover != null) {
			this.listaPerguntas.remove(perguntaRemover);
			return true;
		}
		
		return false;
	}

	@Override
	public List<Pergunta> listarTodosPerguntas() {	
		return this.listaPerguntas;
	}

	@Override
	public Pergunta pesquisar(int id) {
		Pergunta p = new Pergunta();
		p.setId(id);
		return this.existePergunta(p);
	}

	@Override
	public List<Usuario> listarTodosUsuarios() {	
		return this.listaUsuario;
	}

	
	private Usuario existeUsuario(Usuario usuario) {
		int index = this.listaUsuario.indexOf(usuario);
		if(index != -1) {
			return this.listaUsuario.get(index);
		}else {
			return null;
		}
	}
	
	public List<Pergunta> pesquisar(Pergunta pergunta){
		List<Pergunta> listaRetorno = new ArrayList<Pergunta>();
		//Se id tiver preenchido, deve retornar só por ele
		if(pergunta.getId() != 0) {
			Pergunta retorno = this.existePergunta(pergunta);
			listaRetorno.add(retorno);
		}else {
		//Devemos verificar os campos preenchidos do pergunta, para assim fazer a arvore	
			String tipoConsulta = "";
			
			if(!pergunta.getDescricao().isEmpty()) {
				tipoConsulta += "T";
			}
			if(pergunta.getDataCriacao() != null) {
				tipoConsulta += "D";
			}
			if(pergunta.getUsuario() != null && !pergunta.getUsuario().getCpf().isEmpty()) {
				tipoConsulta += "U";
			}
			if(!pergunta.getAtivo().isEmpty()) {
				tipoConsulta += "A";
			}
			
			//Senão tiver nenhum preenchido, retorna todos
			if(tipoConsulta.isEmpty()) {
				listaRetorno = this.listaPerguntas;
			}
			
			//Criamos a arvore T-D-U-A, agora vamos criar as condições para cada uma
			for (Pergunta p : this.listaPerguntas) {

				if(tipoConsulta.equals("T")) { //Descrição
					if(p.getDescricao().contains(pergunta.getDescricao())) {
						listaRetorno.add(p);
					}
				}
				
				if(tipoConsulta.equals("D")) { //Data de Criação
					if(p.getDataCriacao().getTime() >= pergunta.getDataCriacao().getTime()) {
						listaRetorno.add(p);
					}
				}
				
				if(tipoConsulta.equals("U")) { //Usuario
					if(p.getUsuario().getCpf().equals(pergunta.getUsuario().getCpf())) {
						listaRetorno.add(p);
					}
				}
				
				if(tipoConsulta.equals("A")) { //Ativo
					if(p.getAtivo().equals(pergunta.getAtivo())) {
						listaRetorno.add(p);
					}
				}
				
				if(tipoConsulta.equals("TD")) { //Descrição e Data
					if(p.getDescricao().contains(pergunta.getDescricao()) && 
					   (p.getDataCriacao().getTime() >= pergunta.getDataCriacao().getTime())) {
						listaRetorno.add(p);
					}
				}
				
				if(tipoConsulta.equals("TU")) { //Descricao e Usuario
					if(p.getDescricao().contains(pergunta.getDescricao()) &&
					   (p.getUsuario().getCpf().equals(pergunta.getUsuario().getCpf()))) {
						listaRetorno.add(p);
					}
				}
				
				if(tipoConsulta.equals("TA")) { //Descrição e Ativo
					if(p.getDescricao().contains(pergunta.getDescricao()) && 
					   (p.getAtivo().equals(pergunta.getAtivo()))) {
						listaRetorno.add(p);
					}
				}
				
				if(tipoConsulta.equals("DU")) { //Data e Usuario
					if(p.getDataCriacao().getTime() >= pergunta.getDataCriacao().getTime() &&
					   (p.getUsuario().getCpf().equals(pergunta.getUsuario().getCpf()))) {
						listaRetorno.add(p);
					}
				}
				
				if(tipoConsulta.equals("DA")) { //Data e Ativo
					if(p.getDataCriacao().getTime() >= pergunta.getDataCriacao().getTime() && 
					   (p.getAtivo().equals(pergunta.getAtivo()))) {
						listaRetorno.add(p);
					}
				}
				
				if(tipoConsulta.equals("UA")) { //Usuario e Ativo
					if(p.getUsuario().getCpf().equals(pergunta.getUsuario().getCpf()) && 
					   (p.getAtivo().equals(pergunta.getAtivo()))) {
						listaRetorno.add(p);
					}
				}
				
				if(tipoConsulta.equals("TDU")) { //DEscricao, Data e Usuario
					if(p.getDescricao().contains(pergunta.getDescricao()) &&
					  (p.getDataCriacao().getTime() >= pergunta.getDataCriacao().getTime()) &&
					  (p.getUsuario().getCpf().equals(pergunta.getUsuario().getCpf()))) {
						listaRetorno.add(p);
					}
				}
				
				if(tipoConsulta.equals("TUA")) { //Descricao, Usuario e Ativo
					if(p.getDescricao().contains(pergunta.getDescricao()) &&
					  (p.getUsuario().getCpf().equals(pergunta.getUsuario().getCpf())) && 
					  (p.getAtivo().equals(pergunta.getAtivo()))) {
						listaRetorno.add(p);
					}
				}
				
				if(tipoConsulta.equals("DUA")) { //Data, Usuario e Ativo
					if(p.getDataCriacao().getTime() >= pergunta.getDataCriacao().getTime() &&
					  (p.getUsuario().getCpf().equals(pergunta.getUsuario().getCpf())) && 
					  (p.getAtivo().equals(pergunta.getAtivo()))) {
						listaRetorno.add(p);
					}
				}
				
				if(tipoConsulta.equals("TDUA")) { //Descricao, Data, Usuario e Ativo
					if(p.getDescricao().contains(pergunta.getDescricao()) &&
							(p.getDataCriacao().getTime() >= pergunta.getDataCriacao().getTime()) &&
							(p.getUsuario().getCpf().equals(pergunta.getUsuario().getCpf())) && 
							(p.getAtivo().equals(pergunta.getAtivo()))) {
						listaRetorno.add(p);
					}
				}
			}
		}
		
		return listaRetorno;
	}
	
	
	
}
