package br.com.trier.spring_matutino.domain;

import br.com.trier.spring_matutino.domain.dto.UserDTO;
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
	
	/*
	 * validar update para caso não encontre o solte um erro
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter
	@Column (name = "id")
	private Integer id;
	
	@Column (name = "nome")
	private String name;
	
	@Column (name = "email", unique = true)
	private String email;
	
	@Column (name = "senha")
	private String password;
	
	public User(UserDTO dto) { //dto p entity
		this(dto.getId(), dto.getName(), dto.getEmail(), dto.getPassword());
	}
	
	public UserDTO toDTO() { //entity p dto
		return new UserDTO(this.id, this.name, this.email, this.password);
	}
}
