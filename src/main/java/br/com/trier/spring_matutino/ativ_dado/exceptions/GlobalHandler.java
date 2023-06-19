package br.com.trier.spring_matutino.ativ_dado.exceptions;

import java.net.URI;
import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandler {
	
	@ExceptionHandler(ValorInvalidoException.class)
	ProblemDetail handleValorInvalidoException(ValorInvalidoException e) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_ACCEPTABLE, e.getLocalizedMessage());
		problemDetail.setTitle("Valor Inválido");
		problemDetail.setProperty("timestamp: ", Instant.now());
		problemDetail.setType(URI.create("ERRO!"));
		return problemDetail;
	}
}
