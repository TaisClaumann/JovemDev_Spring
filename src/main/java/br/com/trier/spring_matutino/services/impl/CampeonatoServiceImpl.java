package br.com.trier.spring_matutino.services.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import br.com.trier.spring_matutino.domain.Campeonato;
import br.com.trier.spring_matutino.repositories.CampeonatoRepository;
import br.com.trier.spring_matutino.services.CampeonatoService;
import br.com.trier.spring_matutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.spring_matutino.services.exceptions.ViolacaoDeIntegridade;
import io.micrometer.common.util.StringUtils;

@Service
public class CampeonatoServiceImpl implements CampeonatoService {

	@Autowired
	private CampeonatoRepository repo;

	@Override
	public Campeonato insert(Campeonato campeonato) {
		validaCampeonato(campeonato);
		return repo.save(campeonato);
	}

	private void validaCampeonato(Campeonato campeonato) {
		if (campeonato == null) {
			throw new ViolacaoDeIntegridade("O campeonato está nulo");
		} else if (campeonato.getDescription() == null || campeonato.getDescription().isBlank()) {
			throw new ViolacaoDeIntegridade("A descrição está vazia");
		} else if (campeonato.getAno() == null || campeonato.getAno().isBlank()) {
			throw new ViolacaoDeIntegridade("O ano está vazio");
		}
		validaAno(campeonato.getAno());
	}
	
	private void validaAno(String ano) {
		int anoAtual = LocalDate.now().getYear();
		int anoInserido = Integer.parseInt(ano);
		
		if (anoInserido < 1990 || anoInserido > anoAtual) {
			throw new ViolacaoDeIntegridade("O ano precisa ser maior que 1990 e menor que 2023");
		}
	}

	@Override
	public Campeonato update(Campeonato campeonato) {
		if(!listAll().contains(campeonato)) {
			throw new ObjetoNaoEncontrado("Esse campeonato não existe");
		}
		return insert(campeonato);
	}

	@Override
	public List<Campeonato> listAll() {
		List<Campeonato> campeonatos = repo.findAll();
		if (campeonatos.size() == 0) {
			throw new ObjetoNaoEncontrado("Não há campeonatos cadastrados");
		}
		return campeonatos;
	}

	@Override
	public Campeonato findById(Integer id) {
		Optional<Campeonato> camp = repo.findById(id);
		return camp.orElseThrow(() -> new ObjetoNaoEncontrado("Campeonato %s não encontrado".formatted(id)));
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
		if (campeonatos.size() == 0) {
			throw new ObjetoNaoEncontrado("Campeonato de %s não encontrado".formatted(ano));
		}
		return campeonatos;
	}

	@Override
	public List<Campeonato> findByAnoBetween(String anoInicial, String anoFinal) {
		List<Campeonato> campeonatos = repo.findByAnoBetween(anoInicial, anoFinal);
		if (campeonatos.size() == 0) {
			throw new ObjetoNaoEncontrado("Não há campeonatos entre %s e %s".formatted(anoInicial, anoFinal));
		}
		return campeonatos;
	}

	@Override
	public List<Campeonato> findByDescriptionContainsIgnoreCase(String description) {
		List<Campeonato> campeonatos = repo.findByDescriptionContainsIgnoreCase(description);
		if (campeonatos.size() == 0) {
			throw new ObjetoNaoEncontrado("Campeonato %s não encontrado".formatted(description));
		}
		return campeonatos;
	}

	@Override
	public List<Campeonato> findByDescriptionContainsIgnoreCaseAndAnoEquals(String description, String ano) {
		List<Campeonato> campeonatos = repo.findByDescriptionContainsIgnoreCaseAndAnoEquals(description, ano);
		if (campeonatos.size() == 0) {
			throw new ObjetoNaoEncontrado("Campeonato %s de %s não encontrado".formatted(description, ano));
		}
		return campeonatos;
	}
}
