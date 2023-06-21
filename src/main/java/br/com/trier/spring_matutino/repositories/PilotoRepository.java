package br.com.trier.spring_matutino.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.spring_matutino.domain.Equipe;
import br.com.trier.spring_matutino.domain.Pais;
import br.com.trier.spring_matutino.domain.Piloto;

@Repository
public interface PilotoRepository extends JpaRepository<Piloto, Integer>{
	
	List<Piloto> findByNameContainsIgnoreCaseOrderByNome(String name);
	List<Piloto> findByPais(Pais pais);
	List<Piloto> findByEquipe(Equipe equipe);

}
