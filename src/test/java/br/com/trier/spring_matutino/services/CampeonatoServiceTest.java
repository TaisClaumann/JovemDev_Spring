package br.com.trier.spring_matutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.spring_matutino.BaseTests;
import br.com.trier.spring_matutino.domain.Campeonato;
import br.com.trier.spring_matutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.spring_matutino.services.exceptions.ViolacaoDeIntegridade;
import jakarta.transaction.Transactional;

@Transactional
public class CampeonatoServiceTest extends BaseTests{
	
	@Autowired
	CampeonatoService service;
	
	@Test
	@DisplayName("Teste inserir campeonato")
	void insertTest() {
		var camp = new Campeonato(null, "Camp1", "2023");
		service.insert(camp);
		List<Campeonato> campeonatos = service.listAll();
		assertEquals(1, campeonatos.size());
	}
	
	@Test
	@DisplayName("Teste inserir campeonato invalido")
	void insertInvalidTest() {
		var camp = new Campeonato(null, "Camp1", "2024");
		var exception = assertThrows(ViolacaoDeIntegridade.class, () -> service.insert(camp));
		assertEquals("O ano precisa ser maior que 1990 e menor que 2023", exception.getMessage());
		
		var camp2 = new Campeonato(null, "", "");
		var exception2 = assertThrows(ViolacaoDeIntegridade.class, () -> service.insert(camp2));
		assertEquals("A descrição está vazia", exception2.getMessage());
	}

	@Test
	@DisplayName("Teste alterar um campeonato")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void updateTest() {
		var camp = new Campeonato(1, "Camp3", "2023");
		service.update(camp);
		List<Campeonato> campeonatos = service.listAll();
		assertEquals("Camp3", campeonatos.get(0).getDescription());
	}
	
	@Test
	@DisplayName("Teste alterar um campeonato invalido")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void updateInvalidTest() {
		var camp = new Campeonato(1, "Camp1", "2024");
		var exception = assertThrows(ViolacaoDeIntegridade.class, () -> service.update(camp));
		assertEquals("O ano precisa ser maior que 1990 e menor que 2023", exception.getMessage());
		
		var camp2 = new Campeonato(1, "", "");
		var exception2 = assertThrows(ViolacaoDeIntegridade.class, () -> service.update(camp2));
		assertEquals("A descrição está vazia", exception2.getMessage());
	}
	
	@Test
	@DisplayName("Teste listar todos os campeonatos")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void listAllTest() {
		List<Campeonato> campeonatos = service.listAll();
		assertEquals(3, campeonatos.size());
	}
	
	@Test
	@DisplayName("Teste listar todos os campeonatos lista vazia")
	void listAllEmptyTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.listAll());
		assertEquals("Não há campeonatos cadastrados", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar campeonato pelo id")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void findByIdTest() {
		var camp = service.findById(1);
		assertThat(camp).isNotNull();
		assertEquals("Camp1", camp.getDescription());
		assertEquals("2023", camp.getAno());
	}
	
	@Test
	@DisplayName("Teste buscar campeonato pelo id inexistente")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void findByIdNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findById(5));
		assertEquals("Campeonato 5 não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste deletar campeonato")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void deleteTest() {
		service.delete(1);
		List<Campeonato> campeonatos = service.listAll();
		assertEquals(2, campeonatos.size());
	}
	
	@Test
	@DisplayName("Teste deletar campeonato inexistente")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void deleteNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findById(5));
		assertEquals("Campeonato 5 não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar campeonato pela descricao ignorando o case")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void findByDescricaoEqualsIgnoreCaseTest() {
		var camp = service.findByDescriptionEqualsIgnoreCase("camp1");
		assertThat(camp).isNotNull();
		assertEquals("2023", camp.getAno());
	}
	
	@Test
	@DisplayName("Teste buscar campeonato pela descricao ignorando o case sem achar")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void findByDescricaoEqualsIgnoreCaseNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByDescriptionEqualsIgnoreCase("po"));
		assertEquals("Campeonato po não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar campeonato pelo ano")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void findByAnoTest() {
		List<Campeonato> campeonatos = service.findByAno("2023");
		assertEquals(1, campeonatos.size());
	}
	
	@Test
	@DisplayName("Teste buscar campeonato pelo ano sem achar")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void findByAnoNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByAno("2019"));
		assertEquals("Campeonato de 2019 não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar campeonatos entre anos")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void findByAnoBetweenTest() {
		List<Campeonato> campeonatos = service.findByAnoBetween("2022", "2023");
		assertEquals(2, campeonatos.size());
	}
	
	@Test
	@DisplayName("Teste buscar campeonatos entre anos sem achar")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void findByAnoBetweenNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByAnoBetween("2010", "2014"));
		assertEquals("Não há campeonatos entre 2010 e 2014", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar pela descricao com like")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void findByDescricaoContainsIgnoreCaseTest() {
		List<Campeonato> campeonatos = service.findByDescriptionContainsIgnoreCase("Camp");
		assertEquals(3, campeonatos.size());
	}
	
	@Test
	@DisplayName("Teste buscar pela descricao com like sem achar")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void findByDescricaoContainsIgnoreCaseNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByDescriptionContainsIgnoreCase("po"));
		assertEquals("Campeonato po não encontrado", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar pela descricao com like e pelo ano")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void findByDescricaoContainsIgnoreCaseAndAnoEqualsTest() {
		List<Campeonato> campeonatos = service.findByDescriptionContainsIgnoreCaseAndAnoEquals("Camp", "2022");
		assertEquals(1, campeonatos.size());
	}
	
	@Test
	@DisplayName("Teste buscar pela descricao com like e pelo ano sem achar")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void findByDescricaoContainsIgnoreCaseAndAnoEqualsNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByDescriptionContainsIgnoreCaseAndAnoEquals("po", "2020"));
		assertEquals("Campeonato po de 2020 não encontrado", exception.getMessage());
	}
}
