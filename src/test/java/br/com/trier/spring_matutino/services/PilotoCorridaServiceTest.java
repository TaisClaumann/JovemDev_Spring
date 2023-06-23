package br.com.trier.spring_matutino.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.spring_matutino.BaseTests;
import br.com.trier.spring_matutino.domain.Corrida;
import br.com.trier.spring_matutino.domain.Piloto;
import br.com.trier.spring_matutino.domain.PilotoCorrida;
import br.com.trier.spring_matutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.spring_matutino.services.exceptions.ViolacaoDeIntegridade;
import jakarta.transaction.Transactional;

@Transactional
public class PilotoCorridaServiceTest extends BaseTests{

	@Autowired
	private PilotoCorridaService service;
	
	@Test
	@DisplayName("Teste inserir piloto_corrida")
	@Sql({"classpath:/resources/sqls/banco_dados.sql"})
	void insertTest() {
		var pilotoCorrida = new PilotoCorrida(null, 
											  new Piloto(1, null, null, null), 
											  new Corrida(1, null, null, null), 
											  1);
		service.insert(pilotoCorrida);
		assertEquals(1, service.listAll().size());
	}
	
	@Test
	@DisplayName("Teste inserir piloto_corrida invalido")
	@Sql({"classpath:/resources/sqls/banco_dados.sql"})
	void insertInvalidTest() {
		var pilotoCorrida = new PilotoCorrida(null, 
											  new Piloto(1, null, null, null), 
											  new Corrida(1, null, null, null), 
											  0);
		var exception = assertThrows(ViolacaoDeIntegridade.class, () -> service.insert(pilotoCorrida));
		assertEquals("A colocação não pode ser nula ou igual a 0", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste alterar piloto_corrida")
	@Sql({"classpath:/resources/sqls/banco_dados.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void updateTest() {
		var pilotoCorrida = new PilotoCorrida(1, 
											  new Piloto(1, null, null, null), 
											  new Corrida(1, null, null, null), 
											  3);
		service.update(pilotoCorrida);
		assertEquals(3, service.listAll().get(0).getColocacao());
	}
	
	@Test
	@DisplayName("Teste alterar piloto_corrida inexistente")
	@Sql({"classpath:/resources/sqls/banco_dados.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void updateInvalidTest() {
		var pilotoCorrida = new PilotoCorrida(10, 
											  new Piloto(1, null, null, null), 
											  new Corrida(1, null, null, null), 
											  3);
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.update(pilotoCorrida));
		assertEquals("Esse cadastro não existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste deletar piloto_corrida")
	@Sql({"classpath:/resources/sqls/banco_dados.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void deleteTest() {
		service.delete(1);
		assertEquals(5, service.listAll().size());
	}
	
	@Test
	@DisplayName("Teste listar todos piloto_corrida")
	@Sql({"classpath:/resources/sqls/banco_dados.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void listAllTest() {
		assertEquals(6, service.listAll().size());
	}
	
	@Test
	@DisplayName("Teste listar todos piloto_corrida com lista vazia")
	@Sql({"classpath:/resources/sqls/banco_dados.sql"})
	void listAllEmptyTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.listAll());
		assertEquals("Não há cadastros até o momento", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar piloto_corrida pelo id")
	@Sql({"classpath:/resources/sqls/banco_dados.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void findByIdTest() {
		var pilotoCorrida = service.findById(1);
		assertEquals(1, pilotoCorrida.getColocacao());
		assertEquals(1, pilotoCorrida.getPiloto().getId());
	}
	
	@Test
	@DisplayName("Teste buscar piloto_corrida pelo id sem achar")
	@Sql({"classpath:/resources/sqls/banco_dados.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void findByIdNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findById(10));
		assertEquals("Piloto-Corrida 10 não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar corridas de um piloto")
	@Sql({"classpath:/resources/sqls/banco_dados.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void findByPilotoTest() {
		var pilotoCorridas = service.findByPiloto(new Piloto(1, null, null, null));
		assertEquals(2, pilotoCorridas.size());
		assertEquals(1, pilotoCorridas.get(0).getCorrida().getId());
		assertEquals(2, pilotoCorridas.get(1).getCorrida().getId());
	}
	
	@Test
	@DisplayName("Teste buscar corridas de um piloto sem achar")
	@Sql({"classpath:/resources/sqls/banco_dados.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void findByPilotoNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByPiloto(new Piloto(4, null, null, null)));
		assertEquals("Não há corridas desse piloto", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar os pilotos de uma corrida")
	@Sql({"classpath:/resources/sqls/banco_dados.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void findByCorridaTest() {
		var corridaPilotos = service.findByCorrida(new Corrida(1, null, null, null));
		assertEquals(3, corridaPilotos.size());
		assertEquals(1, corridaPilotos.get(0).getPiloto().getId());
		assertEquals(2, corridaPilotos.get(1).getPiloto().getId());
	}
	
	@Test
	@DisplayName("Teste buscar os pilotos de uma corrida sem achar")
	@Sql({"classpath:/resources/sqls/banco_dados.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void findByCorridaNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByCorrida(new Corrida(4, null, null, null)));
		assertEquals("Não há pilotos nessa corrida", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar os pilotos de uma corrida ordenados pela colocacao")
	@Sql({"classpath:/resources/sqls/banco_dados.sql"})
	@Sql({"classpath:/resources/sqls/piloto_corrida.sql"})
	void findByCorridaOrderByColocacaoAscTest() {
		var corridaPilotos = service.findByCorridaOrderByColocacaoAsc(new Corrida(1, null, null, null));
		assertEquals(3, corridaPilotos.size());
		assertEquals(1, corridaPilotos.get(0).getPiloto().getId());
		assertEquals(3, corridaPilotos.get(1).getPiloto().getId());
	}
}
