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

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
public class Pais {
	
	/*
	 * validar update para caso não encontre o solte um erro
	 */
	
	@Id
	@Setter
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer id;
	@Column(unique = true)
	private String name;

}
