package br.com.trier.spring_matutino.domain.dto;

import br.com.trier.spring_matutino.domain.Campeonato;
import br.com.trier.spring_matutino.domain.Pista;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CorridaDTO {
	
	private Integer id;
	private String data;
	private Integer pistaId;
	private Integer campeonatoId;
	private String campeonatoNome;

}
