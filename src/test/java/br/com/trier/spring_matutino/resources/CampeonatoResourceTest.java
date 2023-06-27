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
import br.com.trier.spring_matutino.config.jwt.LoginDTO;
import br.com.trier.spring_matutino.domain.Campeonato;
import br.com.trier.spring_matutino.domain.Pais;

@ActiveProfiles("test")
@SpringBootTest(classes = SpringMatutinoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CampeonatoResourceTest {

	@Autowired
	protected TestRestTemplate rest;
	
	private HttpHeaders getHeaders(String email, String password){
		LoginDTO loginDTO = new LoginDTO(email, password);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<LoginDTO> requestEntity = new HttpEntity<>(loginDTO, headers);
		ResponseEntity<String> responseEntity = rest.exchange(
				"/auth/token", 
				HttpMethod.POST,  
				requestEntity,    
				String.class   
				);
		String token = responseEntity.getBody();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		return headers;
	}
	
	private ResponseEntity<Campeonato> getCampeonato(String url) {
		return rest.exchange(url, 
							 HttpMethod.GET, 
							 new HttpEntity<>(getHeaders("test@teste.com.br", "123")),
							 Campeonato.class);
	}
	
	@SuppressWarnings("unused")
	private ResponseEntity<List<Campeonato>> getCampeonatos(String url) {
		return rest.exchange(url, 
							 HttpMethod.GET, 
							 new HttpEntity<>(getHeaders("test@teste.com.br", "123")), 
							 new ParameterizedTypeReference<List<Campeonato>>() {
		});
	}
	
	@Test
	@DisplayName("Teste inserir campeonato com permissão de ADMIN")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void insertAdminTest() {
		Campeonato camp = new Campeonato(null, "teste", "2020");
		HttpHeaders headers = getHeaders("test@teste.com.br", "123");
		HttpEntity<Campeonato> requestEntity = new HttpEntity<>(camp, headers);
		ResponseEntity<Campeonato> responseEntity = rest.exchange("/campeonato", HttpMethod.POST, requestEntity, Campeonato.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("teste", responseEntity.getBody().getDescription());
	}
	
	@Test
	@DisplayName("Teste inserir campeonato com permissão de USER")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	void insertUserTest() {
		Campeonato camp = new Campeonato(null, "teste", "2020");
		HttpHeaders headers = getHeaders("test2@teste.com.br", "123");
		HttpEntity<Campeonato> requestEntity = new HttpEntity<>(camp, headers);
		ResponseEntity<Campeonato> responseEntity = rest.exchange("/campeonato", HttpMethod.POST, requestEntity, Campeonato.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.FORBIDDEN);
	}
	
	@Test
	@DisplayName("Teste alterar campeonato com permissão de ADMIN")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void updateAdminTest() {
		Campeonato camp = new Campeonato(1, "teste", "2020");
		HttpHeaders headers = getHeaders("test@teste.com.br", "123");
		HttpEntity<Campeonato> requestEntity = new HttpEntity<>(camp, headers);
		ResponseEntity<Campeonato> responseEntity = rest.exchange("/campeonato/1", HttpMethod.PUT, requestEntity, Campeonato.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
		assertEquals("teste", responseEntity.getBody().getDescription());
	}
	
	@Test
	@DisplayName("Teste alterar campeonato com permissão de USER")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void updateUserTest() {
		Campeonato camp = new Campeonato(1, "teste", "2020");
		HttpHeaders headers = getHeaders("test2@teste.com.br", "123");
		HttpEntity<Campeonato> requestEntity = new HttpEntity<>(camp, headers);
		ResponseEntity<Campeonato> responseEntity = rest.exchange("/campeonato/1", HttpMethod.PUT, requestEntity, Campeonato.class);
		
		assertEquals(responseEntity.getStatusCode(), HttpStatus.FORBIDDEN);
	}
	
	@Test
	@DisplayName("Teste deletar campeonato com permissão de ADMIN")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void deleteAdminTest() {
		HttpHeaders headers = getHeaders("test@teste.com.br", "123");
		HttpEntity<Campeonato> requestEntity = new HttpEntity<>(null, headers);
		ResponseEntity<Void> response = rest.exchange("/campeonato/1", HttpMethod.DELETE, requestEntity, Void.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Teste deletar campeonato com permissão de USER")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void deleteUserTest() {
		HttpHeaders headers = getHeaders("test2@teste.com.br", "123");
		HttpEntity<Campeonato> requestEntity = new HttpEntity<>(null, headers);
		ResponseEntity<Void> response = rest.exchange("/campeonato/1", HttpMethod.DELETE, requestEntity, Void.class);
		assertEquals(response.getStatusCode(), HttpStatus.FORBIDDEN);
	}
	
	@Test
	@DisplayName("Teste buscar campeonato pelo id")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void findByIdTest() {
		ResponseEntity<Campeonato> response = getCampeonato("/campeonato/1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals("Camp1", response.getBody().getDescription());
	}
	
	@Test
	@DisplayName("Teste buscar todos os campeonatos com permissão de ADMIN")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void listAllAdminTest() {
		ResponseEntity<List<Campeonato>> response = rest.exchange(
				"/campeonato",
				HttpMethod.GET,
				new HttpEntity<>(getHeaders("test@teste.com.br", "123")),
				new ParameterizedTypeReference<List<Campeonato>>() {}
		);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Teste buscar todos os campeonatos com permissão de USER")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void listAllUserTest() {
		ResponseEntity<List<Campeonato>> response = rest.exchange(
				"/campeonato",
				HttpMethod.GET,
				new HttpEntity<>(getHeaders("test2@teste.com.br", "123")),
				new ParameterizedTypeReference<List<Campeonato>>() {}
		);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Teste buscar campeonato pela descricao")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void findByDescriptionEqualsIgnoreCaseTest() {
		ResponseEntity<Campeonato> response = getCampeonato("/campeonato/description/camp1");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals("Camp1", response.getBody().getDescription());
	}
	
	@Test
	@DisplayName("Teste buscar campeonatos pelo ano")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void findByAnoTest() {
		ResponseEntity<List<Campeonato>> response = getCampeonatos("/campeonato/ano/2023");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Teste buscar campeonatos entre dois anos")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void findByAnoBetweenTest() {
		ResponseEntity<List<Campeonato>> response = getCampeonatos("/campeonato/between/2020/2022");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Teste buscar campeonatos pela descricao com like")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void findByDescriptionContainsIgnoreCaseTest() {
		ResponseEntity<List<Campeonato>> response = getCampeonatos("/campeonato/like/cam");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	@DisplayName("Teste buscar campeonatos pela descricao com like e pelo ano")
	@Sql({"classpath:/resources/sqls/limpa_tabelas.sql"})
	@Sql({"classpath:/resources/sqls/usuario.sql"})
	@Sql({"classpath:/resources/sqls/campeonato.sql"})
	void findByDescriptionContainsIgnoreCaseAndAnoEqualsTest() {
		ResponseEntity<List<Campeonato>> response = getCampeonatos("/campeonato/description-ano/camp/2022");
		assertEquals(response.getStatusCode(), HttpStatus.OK);
	}
}
