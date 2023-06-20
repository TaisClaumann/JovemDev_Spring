package br.com.trier.spring_matutino.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import br.com.trier.spring_matutino.SpringMatutinoApplication;
import br.com.trier.spring_matutino.domain.Campeonato;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Sql(executionPhase=ExecutionPhase.BEFORE_TEST_METHOD,scripts="classpath:/resources/sqls/campeonato.sql")
@Sql(executionPhase=ExecutionPhase.AFTER_TEST_METHOD,scripts="classpath:/resources/sqls/limpa_tabelas.sql")
@SpringBootTest(classes = SpringMatutinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CampeonatoResourceTest {

	@Autowired
	protected TestRestTemplate rest;
	
	private ResponseEntity<Campeonato> getCampeonato(String url) { //converte o json
		return rest.getForEntity(url, Campeonato.class);
	}
	
	@SuppressWarnings("unused")
	private ResponseEntity<List<Campeonato>> getCampeonatos(String url) {//conversão mais de um usuário
		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Campeonato>>() {
		});
	}
	
	@Test
	@DisplayName("Teste inserir campeonato")
	@Sql(executionPhase=ExecutionPhase.AFTER_TEST_METHOD,scripts="classpath:/resources/sqls/limpa_tabelas.sql")
	void insertTest() {
		Campeonato camp = new Campeonato(null, "teste", "2020");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Campeonato> requestEntity = new HttpEntity<>(camp, headers);
		ResponseEntity<Campeonato> responseEntity = rest.exchange("/campeonato", HttpMethod.POST, requestEntity, Campeonato.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("teste", responseEntity.getBody().getDescription());
	}
	
	@Test
	@DisplayName("Teste alterar campeonato")
	void updateTest() {
		Campeonato camp = new Campeonato(1, "teste", "2020");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Campeonato> requestEntity = new HttpEntity<>(camp, headers);
		ResponseEntity<Campeonato> responseEntity = rest.exchange("/campeonato/1", HttpMethod.PUT, requestEntity, Campeonato.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("teste", responseEntity.getBody().getDescription());
	}
	
	@Test
	@DisplayName("Teste deletar campeonato")
	void deleteTest() {
		ResponseEntity<Void> response = rest.exchange("/campeonato/1", HttpMethod.DELETE, null, Void.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Teste buscar campeonato pelo id")
	void findByIdTest() {
		ResponseEntity<Campeonato> response = getCampeonato("/campeonato/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals("Camp1", response.getBody().getDescription());
	}
	
	@Test
	@DisplayName("Teste buscar todos os campeonatos")
	void listAllTest() {
		ResponseEntity<List<Campeonato>> response = getCampeonatos("/campeonato");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Teste buscar campeonato pela descricao")
	void findByDescriptionEqualsIgnoreCaseTest() {
		ResponseEntity<Campeonato> response = getCampeonato("/campeonato/description/camp1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals("Camp1", response.getBody().getDescription());
	}
	
	@Test
	@DisplayName("Teste buscar campeonatos pelo ano")
	void findByAnoTest() {
		ResponseEntity<List<Campeonato>> response = getCampeonatos("/campeonato/ano/2023");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Teste buscar campeonatos entre dois anos")
	void findByAnoBetweenTest() {
		ResponseEntity<List<Campeonato>> response = getCampeonatos("/campeonato/between/2020/2022");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Teste buscar campeonatos pela descricao com like")
	void findByDescriptionContainsIgnoreCaseTest() {
		ResponseEntity<List<Campeonato>> response = getCampeonatos("/campeonato/like/cam");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Teste buscar campeonatos pela descricao com like e pelo ano")
	void findByDescriptionContainsIgnoreCaseAndAnoEqualsTest() {
		ResponseEntity<List<Campeonato>> response = getCampeonatos("/campeonato/description-ano/camp/2022");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
}
