package br.com.trier.spring_matutino.domain.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CorridaPaisAnoDTO {
	
	private Integer ano;
	private String country;
	private List<CorridaDTO> corridas;
}
