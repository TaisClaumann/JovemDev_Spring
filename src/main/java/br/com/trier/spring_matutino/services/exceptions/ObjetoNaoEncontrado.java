package br.com.trier.spring_matutino.services.exceptions;

public class ObjetoNaoEncontrado extends RuntimeException{
	
	public ObjetoNaoEncontrado(String mensagem) {
		super(mensagem);
	}

}
