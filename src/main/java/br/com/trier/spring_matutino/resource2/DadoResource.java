package br.com.trier.spring_matutino.resource2;

import java.awt.geom.IllegalPathStateException;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/aposta")
public class DadoResource {
	
	private Random sorteador = new Random();
	
	@GetMapping
	public String quantDados(@PathVariable (name = "qtd") Integer qtd, @PathVariable (name = "aposta") Integer aposta) {
		String resposta = "";
		Integer soma = null;
		
		if(qtd>4 || qtd<1) {
			throw new IllegalPathStateException("Informe uma qtd maior que 1 e menor que 4");
		} else {
			int contador = 1;
			
			while(contador <= qtd) {
				int valor = sorteador.nextInt(6)+1;
				soma += valor;
				resposta += "Dado " + contador + ": valor sorteado " + valor;
			}
			
			
		}
		
		return resposta;
	}
	
	public Integer somaNum(Integer qtdDados) {
		Integer soma = null;
		int contador = 0;
		
		while(contador <= qtdDados) {
			soma += sorteador.nextInt(6)+1;
		}
		
		return soma;
	}
	
	
}
