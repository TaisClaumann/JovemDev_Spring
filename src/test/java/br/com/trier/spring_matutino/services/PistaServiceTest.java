package br.com.trier.spring_matutino.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import br.com.trier.spring_matutino.BaseTests;
import br.com.trier.spring_matutino.domain.Pais;
import br.com.trier.spring_matutino.domain.Pista;
import br.com.trier.spring_matutino.services.exceptions.ObjetoNaoEncontrado;
import br.com.trier.spring_matutino.services.exceptions.ViolacaoDeIntegridade;
import br.com.trier.spring_matutino.services.impl.PistaServiceImpl;
import jakarta.transaction.Transactional;

@Transactional
public class PistaServiceTest extends BaseTests{
	
	@Autowired
	PistaService service;

	@Test
	@DisplayName("Teste inserir pista")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void insertTest() {
		var pista = new Pista(null, 10, new Pais(1, "Brasil"));
		service.insert(pista);
		assertEquals(1, service.listAll().size());
		assertEquals("Brasil", service.findById(1).getPais().getName());
	}
	
	@Test
	@DisplayName("Teste inserir pista invalida")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void insertInvalidTest() {
		var pista = new Pista(null, 0, new Pais(1, "Brasil"));
		var exception = assertThrows(ViolacaoDeIntegridade.class, () -> service.insert(pista));
		assertEquals("Tamanho inválido", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste alterar pista")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void updateTest() {
		var pista = new Pista(1, 20, new Pais(1, "Brasil"));
		service.update(pista);
		assertEquals(20, service.findById(1).getTamanho());
	}
	
	@Test
	@DisplayName("Teste alterar pista invalida")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void updateInvalidTest() {
		var pista = new Pista(6, 20, new Pais(1, null));
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.update(pista));
		assertEquals("Essa pista não existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste deletar pista")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void deleteTest() {
		service.delete(1);
		assertEquals(2, service.listAll().size());
	}
	
	@Test
	@DisplayName("Teste deletar pista inválida")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void deleteInvalidTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.delete(7));
		assertEquals("Pista id 7 não existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste listar todas as pistas")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void listAllTest() {
		assertEquals(3, service.listAll().size());
	}
	
	@Test
	@DisplayName("Teste listar todas as pistas com a lista vazia")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	void listAllInvalidTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.listAll());
		assertEquals("Não há pistas cadastradas", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar pista pelo id")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void findByIdTest() {
		var pista = service.findById(1);
		assertEquals(10, pista.getTamanho());
	}
	
	@Test
	@DisplayName("Teste buscar pista por id inexistente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void findByIdNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findById(7));
		assertEquals("Pista id 7 não existe", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar pista pelo pais com tamanho em ordem descescente")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void findByPaisOrderByTamanhoDescTest() {
		var pistas = service.findByPaisOrderByTamanhoDesc(new Pais(1, null));
		assertEquals(3, pistas.size());
		assertEquals(16, pistas.get(0).getTamanho());
	}
	
	@Test
	@DisplayName("Teste buscar pista pelo pais sem achar")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void findByPaisOrderByTamanhoDescNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByPaisOrderByTamanhoDesc(new Pais(2, null)));
		assertEquals("Não há pistas desse pais", exception.getMessage());
	}
	
	@Test
	@DisplayName("Teste buscar pista entre tamanhos x e y")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void findByTamanhoBetweenTest() {
		var pistas = service.findByTamanhoBetween(10, 15);
		assertEquals(2, pistas.size());
	}
	
	@Test
	@DisplayName("Teste buscar pista entre tamanhos x e y sem achar")
	@Sql({"classpath:/resources/sqls/pais.sql"})
	@Sql({"classpath:/resources/sqls/pista.sql"})
	void findByTamanhoBetweenNotFoundTest() {
		var exception = assertThrows(ObjetoNaoEncontrado.class, () -> service.findByTamanhoBetween(25, 30));
		assertEquals("Nenhuma pista encontrada com os tamanhos 25 e 30", exception.getMessage());
	}
}
