package br.com.trier.spring_matutino.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.spring_matutino.domain.Equipe;
import br.com.trier.spring_matutino.domain.Pais;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Integer>{
	
	Optional<Equipe> findByName(String name);
	List<Equipe> findByNameContainsIgnoreCase(String name);

}
