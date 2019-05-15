package controle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.PrimeFaces;

import dao.PerguntaDAO;
import dao.PerguntaDAOImpl;
import entidade.Pergunta;
import entidade.Usuario;
import factory.ConexaoFB;

@ManagedBean(name = "PerguntaBean")
@SessionScoped
public class PerguntaBean implements Serializable {

	private static final String MANTER = "/paginas/pergunta/manterPergunta.xhtml";
	private static final String PESQUISAR = "/paginas/pergunta/pesquisarPergunta.xhtml";
	private static final String INDICE = "/index.xhtml"; 
	
	private PerguntaDAO perguntaDAO;
	private Pergunta pergunta;
	private List<Usuario> listaUsuarios;
	private List<Pergunta> listaPerguntas;
	private int idPergunta;
	
	public PerguntaBean() {
		this.pergunta = new Pergunta();
		this.pergunta.setUsuario(new Usuario());
		this.pergunta.setDataCriacao(new Date());

		this.perguntaDAO = PerguntaDAOImpl.getInstance();

		listaUsuarios = this.perguntaDAO.listarTodosUsuarios();
		listaPerguntas = new ArrayList<Pergunta>();

	}

	public void salvar() {
		
		try {

			if (this.idPergunta != 0) {
				this.perguntaDAO.alterarPergunta(pergunta);
				
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Sucesso."));

			} else {
				if (this.perguntaDAO.inserirPergunta(this.pergunta)) {

					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Sucesso."));

					this.pergunta = new Pergunta();
					this.pergunta.setUsuario(new Usuario());
					this.pergunta.setDataCriacao(new Date());

				} else {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falha!", "Pergunta já existe!!!"));
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));

		}

		for (Pergunta pergunta : perguntaDAO.listarTodosPerguntas()) {
			System.out.println("======== " + pergunta.getId() + " =========");
		}
	}

	public void limpar() {

		PrimeFaces.current().resetInputs("form:painel");

	}

	public void pesquisar() {
		this.listaPerguntas = this.perguntaDAO.pesquisar(pergunta);
		System.out.println("###############" + this.listaPerguntas.size());
	}

	public String editar() {
		try {
			pergunta = perguntaDAO.pesquisar(idPergunta);

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Sucesso."));

		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));
		}
		return MANTER;
	}

	public String deletar() {
		try {
			Pergunta perguntaDeletar = perguntaDAO.pesquisar(idPergunta);
			perguntaDAO.removerPergunta(perguntaDeletar);

			this.pesquisar();

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Sucesso."));
		} catch (Exception ex) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", ex.getMessage()));
		}
		return PESQUISAR;
	}

	public Pergunta getPergunta() {
		return pergunta;
	}

	public void setPergunta(Pergunta pergunta) {
		this.pergunta = pergunta;
	}

	public List<Usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public List<Pergunta> getListaPerguntas() {
		return listaPerguntas;
	}

	public void setListaPerguntas(List<Pergunta> listaPerguntas) {
		this.listaPerguntas = listaPerguntas;
	}

	public int getIdPergunta() {
		return idPergunta;
	}

	public void setIdPergunta(int idPergunta) {
		this.idPergunta = idPergunta;
	}
	
		
	public void conetado() {
		ConexaoFB conexao = new ConexaoFB();
		try {
			conexao.conectado();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Sucesso."));
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", e.getMessage()));
		}
		
	}

}
