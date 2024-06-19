package br.inf.ufg.backend.tarefas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/tarefa")
public class TaskServlet extends HttpServlet {

	private List<String> lista = new ArrayList<>();

	@Override
	// READ
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

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
			String tarefa = req.getParameter("task");

			validaTarefa(tarefa, "task");

			lista.add(tarefa);
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
			validaIndex(index);

			lista.remove(index-1);
			resp.getWriter().append("Item removido da lista");

		} catch (RuntimeException e) {
			resp.getWriter().append(e.getMessage());
		}

	}

	@Override
	// UPDATE
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			int index = buscaValor(req, "index");
			validaIndex(index);

			String nova = req.getParameter("task");
			validaTarefa(nova, "task");

			lista.set(index - 1, nova);
			resp.getWriter().append("Item atualizado!");

		} catch (RuntimeException e) {
			resp.getWriter().append(e.getMessage());
		}
	}

	private int buscaValor(HttpServletRequest req, String nomeDoParametro) {
		try {
			return Integer.parseInt(req.getParameter(nomeDoParametro));

		} catch (NumberFormatException e) {
			throw new RuntimeException("Formato do " + nomeDoParametro + " é inválido, só trabalhamos com inteiros");

		}

	}

	private void validaIndex(int index) {
		if (index < 1 || index > lista.size()) {
			throw new RuntimeException("Indice inválido");

		}

	}

	private void validaTarefa(String tarefa, String nomeDoParametro) {
		if (tarefa == null || tarefa.isBlank()) {
			throw new RuntimeException("Valor da " + nomeDoParametro + " inválido");

		}

	}
}
