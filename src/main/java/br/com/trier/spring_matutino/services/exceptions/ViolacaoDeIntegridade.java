package br.com.trier.spring_matutino.services.exceptions;

public class ViolacaoDeIntegridade extends RuntimeException{
	
	public ViolacaoDeIntegridade(String mensagem) {
		super(mensagem);
	}

}
