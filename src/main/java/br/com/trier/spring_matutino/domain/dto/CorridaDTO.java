package br.com.trier.spring_matutino.domain.dto;

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
