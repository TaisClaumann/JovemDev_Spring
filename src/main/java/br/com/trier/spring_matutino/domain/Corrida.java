package br.com.trier.spring_matutino.domain;

import java.time.ZonedDateTime;

import br.com.trier.spring_matutino.domain.dto.CorridaDTO;
import br.com.trier.spring_matutino.utils.DateUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@EqualsAndHashCode(of = "id")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Corrida {
	
	@Id
	@Setter
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private Integer id;
	@Column
	private ZonedDateTime data;
	
	@ManyToOne
	private Pista pista;
	@ManyToOne
	private Campeonato campeonato;
	
	public Corrida(CorridaDTO dto) {
		this(dto.getId(), 
			 DateUtils.strToZonedDateTime(dto.getData()), 
			 new Pista(dto.getPistaId(), null, null), 
			 new Campeonato(dto.getCampeonatoId(), dto.getCampeonatoNome(), null));
	}
	
	public Corrida(CorridaDTO dto, Campeonato campeonato, Pista pista) {
		this(dto.getId(), DateUtils.strToZonedDateTime(dto.getData()), pista, campeonato);
	}
	
	public CorridaDTO toDTO() {
		return new CorridaDTO(id, DateUtils.zonedDateTimeToStr(data), pista.getId(), campeonato.getId(), campeonato.getDescription());
	}
}
