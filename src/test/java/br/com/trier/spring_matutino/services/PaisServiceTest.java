package br.com.trier.spring_matutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.spring_matutino.BaseTests;
import br.com.trier.spring_matutino.domain.Pais;
import jakarta.transaction.Transactional;

@Transactional
public class PaisServiceTest extends BaseTests{
	
	@Autowired
	PaisService service;
	
	@Test
	@DisplayName("Teste salvar um pais")
	void salvarTest() {
		var pais = new Pais(null, "Brasil");
		service.salvar(pais);
		List<Pais> paises = service.listAll();
		assertEquals(1, paises.size());
		assertEquals("Brasil", paises.get(0).getNome());
	}

	@Test
	@DisplayName("Teste alterar um pais")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void updateTest() {
		var pais = new Pais(1, "Pais");
		service.update(pais);
		List<Pais> paises = service.listAll();
		assertEquals("Pais", paises.get(1).getNome());
	}
	
	@Test
	@DisplayName("Teste deletar um pais")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void deleteTest() {
		service.delete(1);
		List<Pais> paises = service.listAll();
		assertEquals(1, paises.size());
	}
	
	@Test
	@DisplayName("Teste listar paises")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void listAllTest() {
		List<Pais> paises = service.listAll();
		assertEquals(2, paises.size());
	}
	
	@Test
	@DisplayName("Teste buscar pais por id")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void findByIdTest() {
		var pais = service.findById(1);
		assertThat(pais).isNotNull();
		assertEquals("Brasil", pais.getNome());
	}
}
