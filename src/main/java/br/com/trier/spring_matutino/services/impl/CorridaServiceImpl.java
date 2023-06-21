package br.com.trier.spring_matutino.services.impl;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.trier.spring_matutino.domain.Campeonato;
import br.com.trier.spring_matutino.domain.Corrida;
import br.com.trier.spring_matutino.domain.Pista;
import br.com.trier.spring_matutino.repositories.CorridaRepository;
import br.com.trier.spring_matutino.services.CorridaService;
import br.com.trier.spring_matutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.spring_matutino.services.exceptions.ViolacaoDeIntegridade;

@Service
public class CorridaServiceImpl implements CorridaService{
	
	@Autowired
	private CorridaRepository repository;

	@Override
	public Corrida insert(Corrida corrida) {
		validaCorrida(corrida);
		return repository.save(corrida);
	}

	private void validaCorrida(Corrida corrida) {
		if(corrida == null) {
			throw new ViolacaoDeIntegridade("A corrida está nula");
		} else if(corrida.getData() == null) {
			throw new ViolacaoDeIntegridade("A data está nula");
		}
		validaData(corrida.getData());
	}
	
	private void validaData(ZonedDateTime data) {
		if(data.isBefore(ZonedDateTime.now())) {
			throw new ViolacaoDeIntegridade("A data precisa ser posterior a data atual");
		}
	}
	
	@Override
	public Corrida update(Corrida corrida) {
		if(!listAll().contains(corrida)) {
			throw new ObjetoNaoEncontrado("Essa corrida não existe");
		}
		return insert(corrida);
	}

	@Override
	public void delete(Integer id) {
		repository.delete(findById(id));
	}

	@Override
	public List<Corrida> listAll() {
		if(repository.findAll().size() == 0) {
			throw new ObjetoNaoEncontrado("Não há corridas cadastradas");
		}
		return repository.findAll();
	}

	@Override
	public Corrida findById(Integer id) {
		return repository.findById(id).orElseThrow(() -> new ObjetoNaoEncontrado("Corrida id %s não existe".formatted(id)));
	}

	@Override
	public List<Corrida> findByData(ZonedDateTime data) {
		DateTimeFormatter formatacao = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		if(repository.findByData(data).size() == 0) {
			throw new ObjetoNaoEncontrado("Não há corridas na data " + formatacao.format(data));
		}
		return repository.findByData(data);
	}

	@Override
	public List<Corrida> findByDataBetween(ZonedDateTime dataInicial, ZonedDateTime dataFinal) {
		DateTimeFormatter formatacao = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		List<Corrida> corridas = repository.findByDataBetween(dataInicial, dataFinal);
		if(corridas.size() == 0) {
			throw new ObjetoNaoEncontrado("Não há corridas entre as datas %s e %s".formatted(
										  formatacao.format(dataInicial), formatacao.format(dataFinal)));
		}
		return corridas;
	}

	@Override
	public List<Corrida> findByPista(Pista pista) {
		List<Corrida> corridas = repository.findByPista(pista);
		if(corridas.size() == 0) {
			throw new ObjetoNaoEncontrado("Não há corridas na pista " + pista.getId());
		}
		return corridas;
	}

	@Override
	public List<Corrida> findByCampeonato(Campeonato campeonato) {
		List<Corrida> corridas = repository.findByCampeonato(campeonato);
		if(corridas.size() == 0) {
			throw new ObjetoNaoEncontrado("Não há corridas no campeonato " + campeonato.getDescription());
		}
		return corridas;
	}
}
