package br.inf.ufg.backend;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/calculadora")
public class CalculadoraServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

		resp.setContentType("text/html; charset=UTF-8");
		PrintWriter out = resp.getWriter();

		try {
			Double num1 = buscaValor(req,"num1");
			Double num2 = buscaValor(req,"num2");
			String operacao = req.getParameter("operacao");

			switch (operacao) {
			case "soma":
				out.println("O resultado da soma é: " + (num1 + num2));
				break;

			case "subtracao":
				out.println("O resultado da subtração é: " + (num1 - num2));
				break;

			case "multiplicacao":
				out.println("O resultado da multiplicação é: " + (num1 * num2));
				break;

			case "divisao":
				if (num2 != 0)
					out.println("O resultado da divisao é: " + (num1 / num2));
				else
					out.println("Não tratamos divisão por zero");
				break;

			default:
				out.println("Operação Nao conhecida: " + operacao);

			}

		} catch (RuntimeException e) {
			out.println(e.getMessage());
		}

	}
	
	private double buscaValor(HttpServletRequest req, String nomeDoParametro) {
		try {
			return Double.parseDouble(req.getParameter(nomeDoParametro));

		} catch (NumberFormatException e) {
			throw new RuntimeException("Formato do " + nomeDoParametro + " é inválido");

		}

	}

}
