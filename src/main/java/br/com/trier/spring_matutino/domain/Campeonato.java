package br.com.trier.spring_matutino.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
@Entity
public class Campeonato {
	
	/*
	 * validar update para caso não encontre o campeonato solte um erro
	 * um metodo que valida ano e outro que valida o campeonato, primeiro verificar se o campeonato é nulo e depois verificar demais campos
	 */
	
	@Id
	@Setter
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String description;
	
	@Column
	private String ano;
	
}
