package br.com.trier.spring_matutino.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring_matutino.domain.Campeonato;
import br.com.trier.spring_matutino.repositories.CampeonatoRepository;
import br.com.trier.spring_matutino.services.CampeonatoService;
import br.com.trier.spring_matutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.spring_matutino.services.exceptions.ViolacaoDeIntegridade;

@Service
public class CampeonatoServiceImpl implements CampeonatoService{
	
	@Autowired
	private CampeonatoRepository repo;

	@Override
	public Campeonato insert(Campeonato campeonato) {
		if(campeonato == null || campeonato.getDescription().isBlank() || campeonato.getAno().isBlank()) {
			throw new ViolacaoDeIntegridade("Preencha os dados da equipe");
		} else if(Integer.parseInt(campeonato.getAno())<1990 || Integer.parseInt(campeonato.getAno())>2023) {
			throw new ViolacaoDeIntegridade("O ano precisa ser maior que 1990 e menor que 2023");
		}
		return repo.save(campeonato);
	}

	@Override
	public Campeonato update(Campeonato campeonato) {
		return insert(campeonato);
	}

	@Override
	public List<Campeonato> listAll() {
		List<Campeonato> campeonatos = repo.findAll();
		if(campeonatos.size() == 0) {
			throw new ObjetoNaoEncontrado("Não há campeonatos cadastrados");
		}
		return campeonatos;
	}

	@Override
	public Campeonato findById(Integer id) {
		Optional<Campeonato> camp = repo.findById(id);
		return camp.orElseThrow(() ->  new ObjetoNaoEncontrado("Campeonato %s não encontrado".formatted(id)));
	}

	@Override
	public void delete(Integer id) {
		Campeonato camp = findById(id);
		repo.delete(camp);
	}

	@Override
	public Campeonato findByDescriptionEqualsIgnoreCase(String description) {
		Optional<Campeonato> camp = repo.findByDescriptionEqualsIgnoreCase(description);
		return camp.orElseThrow(() -> new ObjetoNaoEncontrado("Campeonato %s não encontrado".formatted(description)));
	}

	@Override
	public List<Campeonato> findByAno(String ano) {
		List<Campeonato> campeonatos = repo.findByAno(ano);
		if(campeonatos.size()==0) {
			throw new ObjetoNaoEncontrado("Campeonato de %s não encontrado".formatted(ano));
		}
		return campeonatos;
	}

	@Override
	public List<Campeonato> findByAnoBetween(String anoInicial, String anoFinal) {
		List<Campeonato> campeonatos = repo.findByAnoBetween(anoInicial, anoFinal);
		if(campeonatos.size()==0) {
			throw new ObjetoNaoEncontrado("Não há campeonatos entre %s e %s".formatted(anoInicial, anoFinal));
		}
		return campeonatos;
	}

	@Override
	public List<Campeonato> findByDescriptionContainsIgnoreCase(String description) {
		List<Campeonato> campeonatos = repo.findByDescriptionContainsIgnoreCase(description);
		if(campeonatos.size()==0) {
			throw new ObjetoNaoEncontrado("Campeonato %s não encontrado".formatted(description));
		}
		return campeonatos;
	}

	@Override
	public List<Campeonato> findByDescriptionContainsIgnoreCaseAndAnoEquals(String description, String ano) {
		List<Campeonato> campeonatos = repo.findByDescriptionContainsIgnoreCaseAndAnoEquals(description, ano);
		if(campeonatos.size()==0) {
			throw new ObjetoNaoEncontrado("Campeonato %s de %s não encontrado".formatted(description, ano));
		}
		return campeonatos;
	}
}
