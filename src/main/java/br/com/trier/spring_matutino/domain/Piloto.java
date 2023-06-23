package br.com.trier.spring_matutino.domain;

import br.com.trier.spring_matutino.domain.dto.PilotoDTO;
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
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Piloto {
	
	@Id
	@Column
	@Setter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column
	private String nome;
	
	@ManyToOne()
	private Pais pais;
	@ManyToOne()
	private Equipe equipe;
	
	public Piloto(PilotoDTO dto) {
		this(dto.getId(),
			 dto.getNome(), 
			 new Pais(dto.getPaisId(), dto.getPaisNome()),
			 new Equipe(dto.getEquipeId(), dto.getEquipeNome()));
	}
	
	public PilotoDTO toDTO() {
		return new PilotoDTO(id, nome, pais.getId(), pais.getName(), equipe.getId(), equipe.getName());
	}

}
