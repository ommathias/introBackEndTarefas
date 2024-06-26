package br.inf.ufg.backend.atividade3;

import java.io.IOException;

import br.inf.ufg.backend.tarefas.EmailNotificacaoService;
import br.inf.ufg.backend.tarefas.TarefaService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/tarefa")
public class TaskServlet extends HttpServlet {

	//Refatorando taskServlet para usar o TarefaService
	//A lista vai ser inicializada no TarefaService
	//private List<String> lista = new ArrayList<>();
	
	private static final long serialVersionUID = 1L;
	//declaro como atributo uma instancia da TarefaService aqui
	private TarefaService tarefaService;
	
	//injetando dependencia via construtor
	
	public TaskServlet() {
        this.tarefaService = new TarefaService(new EmailNotificacaoService());

		
	}

	@Override
	// READ
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//A lista vem de tarefaService agora, o TaskServlet apenas manipula.
		var lista = tarefaService.listar();
		
		
		if (lista.isEmpty()) {
			resp.getWriter().append("Está Vazia");
		}

		else {

			resp.getWriter().append(String.format("Índice\tTarefa\n"));

			for (String x : lista) {

				resp.getWriter().append(String.format("%d\t\t%s\n", lista.indexOf(x) + 1, x));

			}
		}
	}

	@Override
	// CREATE
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {
			//Recebe a tarefa do parametro de requisição HTTP
			String tarefa = req.getParameter("task");

			//validaTarefa(tarefa, "task");
			//lista.add(tarefa);
			
			//Validação e inserção feitos no TarefaService
			tarefaService.adicionar(tarefa);			
			resp.getWriter().append("Adicionado à lista");

		} catch (RuntimeException e) {
			resp.getWriter().append(e.getMessage());
		}

	}

	@Override

	// DELETE
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {

			int index = buscaValor(req, "index");
			
			//validação e remoção agora são feitos no tarefaService
			//validaIndex(index);
			//lista.remove(index-1);
			
			tarefaService.remover(index-1);
			resp.getWriter().append("Item removido da lista");

		} catch (RuntimeException e) {
			resp.getWriter().append(e.getMessage());
		}

	}

	@Override
	// UPDATE
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			
			//Recebe índice da requisição HTTP
			int index = buscaValor(req, "index");
			//validaIndex(index);

			String nova = req.getParameter("task");
			//validaTarefa(nova, "task");
			//lista.set(index - 1, nova);
			
			//validação e update feitos no tarefaService
			tarefaService.atualizar(index, nova);
			
			resp.getWriter().append("Item atualizado!");

		} catch (RuntimeException e) {
			resp.getWriter().append(e.getMessage());
		}
	}

	//Este método fica nessa classe. É um utilitário, serve pra buscar e validar o parâmetro da requisição
	private int buscaValor(HttpServletRequest req, String nomeDoParametro) {
		try {
			return Integer.parseInt(req.getParameter(nomeDoParametro));

		} catch (NumberFormatException e) {
			throw new RuntimeException("Formato do " + nomeDoParametro + " é inválido, só trabalhamos com inteiros");

		}

	}

	/*
	 * private void validaIndex(int index) { if (index < 1 || index > lista.size())
	 * { throw new RuntimeException("Indice inválido");
	 * 
	 * }
	 * 
	 * }
	 */

	/*
	 * protected void validaTarefa(String tarefa, String nomeDoParametro) { if
	 * (tarefa == null || tarefa.isBlank()) { throw new RuntimeException("Valor da "
	 * + nomeDoParametro + " inválido");
	 * 
	 * }
	 * 
	 * } 
	 */
	
}