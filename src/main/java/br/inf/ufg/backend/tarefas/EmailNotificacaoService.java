package br.inf.ufg.backend.tarefas;

public class EmailNotificacaoService implements NotificacaoService {

	@Override
	public String enviarNotificacao(String tarefa) {
		
				
		return "Email referente a tarefa '" + tarefa + "' enviado";
	}

	
	
}
