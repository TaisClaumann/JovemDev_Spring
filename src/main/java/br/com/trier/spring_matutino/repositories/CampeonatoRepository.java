package br.com.trier.spring_matutino.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.spring_matutino.domain.Campeonato;

@Repository
public interface CampeonatoRepository extends JpaRepository<Campeonato, Integer>{

	Optional<Campeonato> findByDescriptionEqualsIgnoreCase(String description);
	Optional<List<Campeonato>>findByYear(String year);
	List<Campeonato> findByYearBetween(String anoInicial, String anoFinal);
	List<Campeonato> findByDescriptionContainsIgnoreCase(String description);
	List<Campeonato> findByDescriptionContainsIgnoreCaseAndYearEquals(String description, String year);
}
