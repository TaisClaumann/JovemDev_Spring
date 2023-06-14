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
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity (name = "usuario")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter
	@Column (name = "usuario_id")
	private Integer id;
	
	@Column (name = "usuario_nome")
	private String nome;
	
	@Column (name = "usuario_email", unique = true)
	private String email;
	
	@Column (name = "usuario_senha")
	private String password;

}
