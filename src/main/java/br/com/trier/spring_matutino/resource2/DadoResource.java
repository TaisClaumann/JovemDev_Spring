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
	public ResponseEntity<String> quantDados(@PathVariable (name = "qtd") String qtd, @PathVariable (name = "aposta") String aposta) {
		Integer qtdDados = validaValor(qtd);
		Integer valorAposta = validaValor(aposta);
		
		if(qtdDados>4 || qtdDados<1) {
			throw new ValorInvalidoException("ERRO! Informe uma qtd maior que 1 e menor que 4");
		} else if ((qtdDados*6) < valorAposta) {
			throw new ValorInvalidoException("ERRO! A aposta não deve ser maior que " + (qtdDados*6));
		}
		
		return ResponseEntity.ok(sorteiaDados(qtdDados, valorAposta));
	}
	
	public String sorteiaDados(Integer qtd, Integer aposta) {
		String resposta = "Aposta: " + aposta + "\n";
		int soma = 0;
		
		for(int i = 1; i<= qtd; i++) {
			int valor = sorteador.nextInt(6)+1;
			soma += valor;
			resposta += "Dado " + i + ": valor sorteado " + valor + "\n";
		}
		
		return resposta += "Soma: " + soma + "\n" +
			               verificaPorcentagem(soma, aposta);
	}
	
	public Integer validaValor(String valor) {
		if(valor.isBlank()) {
			throw new ValorInvalidoException("ERRO! Preencha os campos");
		} else if (!(valor.matches("[0-9]+"))) {
			throw new ValorInvalidoException("ERRO! Os valores precisam ser números");
		}
		
		Integer numero = Integer.parseInt(valor);
		return numero;
	}
	
	public double porcentagem(Integer soma, Integer aposta) {
		double diferenca = Math.abs(soma-aposta);
		double valorMaior = Math.max(soma, aposta);
		return (diferenca/valorMaior)*100;
	} 
	
	public String verificaPorcentagem(Integer soma, Integer aposta) {
		double porcent = porcentagem(soma, aposta);
		if(porcent == 0) {
			return "Parabens! Você acertou!";
		}
		return "Porcentagem " + String.format("%.2f", porcent)+"%";
	}
}
