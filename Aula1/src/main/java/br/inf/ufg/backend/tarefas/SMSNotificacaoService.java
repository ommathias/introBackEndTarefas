package br.inf.ufg.backend.tarefas;

public class SMSNotificacaoService implements NotificacaoService{

	@Override
	public String enviarNotificacao(String tarefa) {
		
		return "SMS referente a tarefa '" + tarefa + "' enviado";
	}

}
