package br.com.trier.spring_matutino.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.spring_matutino.domain.Pais;
import br.com.trier.spring_matutino.domain.User;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Integer>{
	
	Optional<Pais> findByNameEqualsIgnoreCase(String name);
	List<Pais> findByNameContainsIgnoreCase(String name);
}
