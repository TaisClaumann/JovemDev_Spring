package br.com.trier.spring_matutino.resource2.exceptions.handler;

import java.net.URI;
import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.trier.spring_matutino.resource2.exceptions.ValorInvalidoException;

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
