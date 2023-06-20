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
import br.com.trier.spring_matutino.domain.Equipe;
import br.com.trier.spring_matutino.domain.Pais;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Sql(executionPhase=ExecutionPhase.BEFORE_TEST_METHOD,scripts="classpath:/resources/sqls/equipe.sql")
@Sql(executionPhase=ExecutionPhase.AFTER_TEST_METHOD,scripts="classpath:/resources/sqls/limpa_tabelas.sql")
@SpringBootTest(classes = SpringMatutinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EquipeResourceTest {

	@Autowired
	protected TestRestTemplate rest;
	
	private ResponseEntity<Equipe> getEquipe(String url) { //converte o json
		return rest.getForEntity(url, Equipe.class);
	}
	
	@SuppressWarnings("unused")
	private ResponseEntity<List<Equipe>> getEquipes(String url) {//conversão mais de um usuário
		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Equipe>>() {
		});
	}

	@Test
	@DisplayName("Teste inserir equipe")
	@Sql(executionPhase=ExecutionPhase.AFTER_TEST_METHOD,scripts="classpath:/resources/sqls/limpa_tabelas.sql")
	void insertTest() {
		Equipe equipe = new Equipe(null, "teste");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Equipe> requestEntity = new HttpEntity<>(equipe, headers);
		ResponseEntity<Equipe> responseEntity = rest.exchange("/equipe", HttpMethod.POST, requestEntity, Equipe.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("teste", responseEntity.getBody().getName());
	}
	
	@Test
	@DisplayName("Teste alterar equipe")
	void updateTest() {
		Equipe equipe = new Equipe(1, "teste");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Equipe> requestEntity = new HttpEntity<>(equipe, headers);
		ResponseEntity<Equipe> responseEntity = rest.exchange("/equipe/1", HttpMethod.PUT, requestEntity, Equipe.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("teste", responseEntity.getBody().getName());
	}
	
	@Test
	@DisplayName("Teste deletar equipe")
	void deleteTest() {
		ResponseEntity<Void> response = rest.exchange("/equipe/1", HttpMethod.DELETE, null, Void.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Teste buscar equipe pelo id")
	void findByIdTest() {
		ResponseEntity<Equipe> response = getEquipe("/equipe/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals("Incriveis", response.getBody().getName());
	}
	
	@Test
	@DisplayName("Teste buscar equipe pelo nome")
	void findByNameTest() {
		ResponseEntity<Equipe> response = getEquipe("/equipe/name/Incriveis");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals("Incriveis", response.getBody().getName());
	}
	
	@Test
	@DisplayName("Teste buscar equipe pelo nome com like")
	void findByNameContainsIgnoreCaseTest() {
		ResponseEntity<List<Equipe>> response = getEquipes("/equipe/like/cri");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Teste buscar todas as equipes")
	void listAllTest() {
		ResponseEntity<List<Equipe>> response = getEquipes("/equipe");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
}
