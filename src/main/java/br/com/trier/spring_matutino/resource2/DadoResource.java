package br.com.trier.spring_matutino.resource2;

import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/aposta")
public class DadoResource {
	
	private Random sorteador = new Random();
	
	@GetMapping("/{qtd}/{aposta}")
	public ResponseEntity<String> quantDados(@PathVariable (name = "qtd") Integer qtd, @PathVariable (name = "aposta") Integer aposta) {
		String msgErro = "";
		
		if(qtd>4 || qtd<1) {
			msgErro = "ERRO! Informe uma qtd maior que 1 e menor que 4";
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msgErro);
		} else if ((qtd*6) < aposta) {
			msgErro = "ERRO! A aposta não deve ser maior que " + (qtd*6);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msgErro);
		} 
		
		return ResponseEntity.ok(sorteiaDados(qtd, aposta));
	}
	
	public String sorteiaDados(Integer qtd, Integer aposta) {
		String resposta = "Aposta: " + aposta + "\n";
		int soma = 0;
		
		int contador = 1;
		
		while(contador <= qtd) {
			int valor = sorteador.nextInt(6)+1;
			soma += valor;
			resposta += "Dado " + contador + ": valor sorteado " + valor + "\n";
			contador++;
		}
		
		return resposta += "Soma: " + soma + "\n" +
			               "Porcentagem " + porcentArcerto(soma, aposta) + "%";
	}
	
	public Double porcentArcerto(Integer soma, Integer aposta) {
		return (aposta*100)/soma.doubleValue();
	}
}
