package br.com.trier.spring_matutino.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PilotoPaisDTO {

	private Integer pilotoId;
	private String pilotoNome;
	private Integer equipeId;
	private String equipeNome;
	private Integer colocacao;
}
