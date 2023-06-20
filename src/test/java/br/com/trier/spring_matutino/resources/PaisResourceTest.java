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
import br.com.trier.spring_matutino.domain.Pais;
import br.com.trier.spring_matutino.domain.dto.UserDTO;

@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = Replace.ANY)
@Sql(executionPhase=ExecutionPhase.BEFORE_TEST_METHOD,scripts="classpath:/resources/sqls/pais.sql")
@Sql(executionPhase=ExecutionPhase.AFTER_TEST_METHOD,scripts="classpath:/resources/sqls/limpa_tabelas.sql")
@SpringBootTest(classes = SpringMatutinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaisResourceTest {
	
	@Autowired
	protected TestRestTemplate rest;
	
	private ResponseEntity<Pais> getPais(String url) { //converte o json
		return rest.getForEntity(url, Pais.class);
	}
	
	@SuppressWarnings("unused")
	private ResponseEntity<List<Pais>> getPaises(String url) {//conversão mais de um usuário
		return rest.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Pais>>() {
		});
	}

	@Test
	@DisplayName("Teste inserir pais")
	@Sql(executionPhase=ExecutionPhase.AFTER_TEST_METHOD,scripts="classpath:/resources/sqls/limpa_tabelas.sql")
	void insertTest() {
		Pais pais = new Pais(null, "teste");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Pais> requestEntity = new HttpEntity<>(pais, headers);
		ResponseEntity<Pais> responseEntity = rest.exchange("/pais", HttpMethod.POST, requestEntity, Pais.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("teste", responseEntity.getBody().getName());
	}
	
	@Test
	@DisplayName("Teste alterar pais")
	void updateTest() {
		Pais pais = new Pais(1, "teste");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Pais> requestEntity = new HttpEntity<>(pais, headers);
		ResponseEntity<Pais> responseEntity = rest.exchange("/pais/1", HttpMethod.PUT, requestEntity, Pais.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("teste", responseEntity.getBody().getName());
	}
	
	@Test
	@DisplayName("Teste deletar pais")
	void deleteTest() {
		ResponseEntity<Void> response = rest.exchange("/pais/1", HttpMethod.DELETE, null, Void.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Teste listar todos os paises")
	void listAllTest() {
		ResponseEntity<List<Pais>> response = getPaises("/pais");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Teste buscar pais pelo id")
	void findByIdTest() {
		ResponseEntity<Pais> response = getPais("/pais/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		
		Pais pais = response.getBody();
		assertEquals("Brasil", pais.getName());
	}
	
	@Test
	@DisplayName("Teste buscar pais pelo nome ignorando o case")
	void findByNameEqualsIgnoreCaseTest() {
		ResponseEntity<Pais> response = getPais("/pais/name/brasil");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		
		Pais pais = response.getBody();
		assertEquals("Brasil", pais.getName());
	}
	
	@Test
	@DisplayName("Teste buscar pais pelo nome com like")
	void findByNameContainsIgnoreCaseTest() {
		ResponseEntity<List<Pais>> response = getPaises("/pais/like/ra");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
}
