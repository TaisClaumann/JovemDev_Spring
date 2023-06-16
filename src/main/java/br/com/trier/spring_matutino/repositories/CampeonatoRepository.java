package br.com.trier.spring_matutino.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.trier.spring_matutino.domain.Campeonato;

@Repository
public interface CampeonatoRepository extends JpaRepository<Campeonato, Integer>{

	Campeonato findByDescricaoEqualsIgnoreCase(String nome);
}
