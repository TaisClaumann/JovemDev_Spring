package br.com.trier.spring_matutino.domain.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PilotoCorridaPaisDTO {
	
	private Integer paisID;
	private String paisNome;
	private Integer corridaId;
	private List<PilotoPaisDTO> pilotosPais = new ArrayList<>();
}
