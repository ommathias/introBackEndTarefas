package br.inf.ufg.backend.tarefas;

import java.util.ArrayList;
import java.util.List;

public class TarefaService {

	private NotificacaoService notificacaoService;
	private List<String> lista = new ArrayList<>();

	// injeção de dependencia via construtor
	public TarefaService(NotificacaoService notificacao) {
		this.notificacaoService = notificacao;
	}

	// CREATE
	public void adicionar(String tarefa) {
		validaTarefa(tarefa, "task");
		lista.add(tarefa);
		notificacaoService.enviarNotificacao(tarefa);

	}

	// READ
	public List<String> listar() {
		return lista;
	}

	// UPDATE
	public void atualizar (int index, String novaTarefa)
	{
		validaIndex(index);
		validaTarefa(novaTarefa, "task");
		lista.set(index-1, novaTarefa);
		notificacaoService.enviarNotificacao(novaTarefa);

	}

	// DELETE
	public void remover(int index)
	{
		validaIndex(index);
		String tarefaRemovida = lista.remove(index-1);
		notificacaoService.enviarNotificacao(tarefaRemovida);
	}

	public void validaTarefa(String tarefa, String nomeDoParametro) {
		if (tarefa == null || tarefa.isBlank()) {
			throw new RuntimeException("Valor da " + nomeDoParametro + " inválido");

		}
	}
	
	private void validaIndex(int index) {
		if (index < 1 || index > lista.size()) {
			throw new RuntimeException("Indice inválido");

		}

	}

}
