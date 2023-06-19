package br.com.trier.spring_matutino.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring_matutino.domain.Campeonato;
import br.com.trier.spring_matutino.repositories.CampeonatoRepository;
import br.com.trier.spring_matutino.services.CampeonatoService;

@Service
public class CampeonatoServiceImpl implements CampeonatoService{
	
	@Autowired
	private CampeonatoRepository repo;

	@Override
	public Campeonato insert(Campeonato campeonato) {
		return repo.save(campeonato);
	}

	@Override
	public Campeonato update(Campeonato campeonato) {
		return repo.save(campeonato);
	}

	@Override
	public List<Campeonato> listAll() {
		return repo.findAll();
	}

	@Override
	public Campeonato findById(Integer id) {
		Optional<Campeonato> camp = repo.findById(id);
		return camp.orElse(null);
	}

	@Override
	public void delete(Integer id) {
		Campeonato camp = findById(id);
		if(camp != null) {
			repo.delete(camp);
		}
	}

	@Override
	public Campeonato findByDescriptionEqualsIgnoreCase(String description) {
		Optional<Campeonato> camp = repo.findByDescriptionEqualsIgnoreCase(description);
		return camp.orElse(null);
	}

	@Override
	public List<Campeonato> findByYear(String year) {
		Optional<List<Campeonato>> campeonatos = repo.findByYear(year);
		return campeonatos.orElse(null);
		
	}

	@Override
	public List<Campeonato> findByYearBetween(String anoInicial, String anoFinal) {
		return repo.findByYearBetween(anoInicial, anoFinal);
	}

	@Override
	public List<Campeonato> findByDescriptionContainsIgnoreCase(String description) {
		return repo.findByDescriptionContainsIgnoreCase(description);
	}

	@Override
	public List<Campeonato> findByDescriptionContainsIgnoreCaseAndYearEquals(String description, String year) {
		return repo.findByDescriptionContainsIgnoreCaseAndYearEquals(description, year);
	}
}
