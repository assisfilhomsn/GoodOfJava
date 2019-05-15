package dao;

import java.util.List;

import entidade.Pergunta;
import entidade.Usuario;

public interface PerguntaDAO {

	public boolean inserirPergunta(Pergunta pergunta);
	public boolean alterarPergunta(Pergunta pergunta);
	public boolean removerPergunta(Pergunta pergunta);
	public List<Pergunta> listarTodosPerguntas();
	public Pergunta pesquisar(int id);
	public List<Usuario> listarTodosUsuarios();
	public List<Pergunta> pesquisar(Pergunta pergunta);
	
}
