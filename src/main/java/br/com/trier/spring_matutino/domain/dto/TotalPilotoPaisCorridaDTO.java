package br.com.trier.spring_matutino.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TotalPilotoPaisCorridaDTO {
	
	private String paisNome;
	private Integer corridaId;
	private Integer totalPilotos;
}
