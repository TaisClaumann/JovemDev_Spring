package br.com.trier.spring_matutino.resources.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.trier.spring_matutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.spring_matutino.services.exceptions.ViolacaoDeIntegridade;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjetoNaoEncontrado.class)
	public ResponseEntity<StandardError> objetoNaoEncontrado(ObjetoNaoEncontrado ex, HttpServletRequest request){
		StandardError erro = new StandardError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(), ex.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
	
	@ExceptionHandler(ViolacaoDeIntegridade.class)
	public ResponseEntity<StandardError> violacaoDeIntegridade(ViolacaoDeIntegridade ex, HttpServletRequest request){
		StandardError erro = new StandardError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getRequestURI());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
}
