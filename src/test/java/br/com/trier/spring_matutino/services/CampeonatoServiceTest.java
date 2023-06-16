package br.com.trier.spring_matutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.spring_matutino.BaseTests;
import br.com.trier.spring_matutino.domain.Campeonato;
import jakarta.transaction.Transactional;

@Transactional
public class CampeonatoServiceTest extends BaseTests{
	
	@Autowired
	CampeonatoService service;
	
	@Test
	@DisplayName("Teste inserir campeonato")
	void insertTest() {
		var camp = new Campeonato(null, "2023", "Camp1");
		service.insert(camp);
		List<Campeonato> campeonatos = service.listAll();
		assertEquals(1, campeonatos.size());
	}

	@Test
	@DisplayName("Teste inserir campeonato")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void updateTest() {
		var camp = new Campeonato(1, "Camp3", "2023");
		service.update(camp);
		List<Campeonato> campeonatos = service.listAll();
		assertEquals("Camp3", campeonatos.get(0).getDescricao());
	}
	
	@Test
	@DisplayName("Teste listar todos os campeonatos")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void listAllTest() {
		List<Campeonato> campeonatos = service.listAll();
		assertEquals(3, campeonatos.size());
	}
	
	@Test
	@DisplayName("Teste buscar campeonato pelo id")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void findByIdTest() {
		var camp = service.findById(1);
		assertThat(camp).isNotNull();
		assertEquals("Camp1", camp.getDescricao());
		assertEquals("2023", camp.getAno());
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
	@DisplayName("Teste buscar campeonato pela descricao ignorando o case")
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void findByDescricaoEqualsIgnoreCaseTest() {
		var camp = service.findByDescricaoEqualsIgnoreCase("camp1");
		assertThat(camp).isNotNull();
	}
}
