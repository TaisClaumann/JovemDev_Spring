package br.com.trier.spring_matutino.resource2;

import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.trier.spring_matutino.resource2.exceptions.ValorInvalidoException;

@RestController
@RequestMapping(value = "/aposta")
public class DadoResource {
	
	private Random sorteador = new Random();
	
	@GetMapping("/{qtd}/{aposta}")
	public ResponseEntity<String> quantDados(@PathVariable (name = "qtd") Integer qtd, @PathVariable (name = "aposta") Integer aposta) {
		String msgErro = "";
		
		if(qtd>4 || qtd<1) {
			throw new ValorInvalidoException("ERRO! Informe uma qtd maior que 1 e menor que 4");
		} else if ((qtd*6) < aposta) {
			throw new ValorInvalidoException("ERRO! A aposta não deve ser maior que " + (qtd*6));
		}
		
		return ResponseEntity.ok(sorteiaDados(qtd, aposta));
	}
	
	public String sorteiaDados(Integer qtd, Integer aposta) {
		String resposta = "Aposta: " + aposta + "\n";
		int soma = 0;
		
		for(int i = 1; i<= qtd; i++) {
			int valor = sorteador.nextInt(6)+1;
			soma += valor;
			resposta += "Dado " + i + ": valor sorteado " + valor + "\n";
		}
	
		int diferenca = aposta - soma;
		double porcentagem = (diferenca*100)/soma;
		
		return resposta += "Soma: " + soma + "\n" +
			               "Porcentagem " + String.format("%.2f", porcentagem) + "%";
	}
}
