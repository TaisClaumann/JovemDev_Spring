package br.com.trier.spring_matutino;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;

import br.com.trier.spring_matutino.services.CampeonatoService;
import br.com.trier.spring_matutino.services.CorridaService;
import br.com.trier.spring_matutino.services.EquipeService;
import br.com.trier.spring_matutino.services.PaisService;
import br.com.trier.spring_matutino.services.PilotoCorridaService;
import br.com.trier.spring_matutino.services.PilotoService;
import br.com.trier.spring_matutino.services.PistaService;
import br.com.trier.spring_matutino.services.UserService;
import br.com.trier.spring_matutino.services.impl.CampeonatoServiceImpl;
import br.com.trier.spring_matutino.services.impl.CorridaServiceImpl;
import br.com.trier.spring_matutino.services.impl.EquipeServiceImpl;
import br.com.trier.spring_matutino.services.impl.PaisServiceImpl;
import br.com.trier.spring_matutino.services.impl.PilotoCorridaServiceImpl;
import br.com.trier.spring_matutino.services.impl.PilotoServiceImpl;
import br.com.trier.spring_matutino.services.impl.PistaServiceImpl;
import br.com.trier.spring_matutino.services.impl.UserServiceImpl;

@TestConfiguration
@SpringBootTest
@ActiveProfiles("test")
public class BaseTests {
	
	@Bean
	public UserService userService() {
		return new UserServiceImpl();
	}

	@Bean
	public PaisService paisService() {
		return new PaisServiceImpl();
	}
	
	@Bean
	public EquipeService equipeService() {
		return new EquipeServiceImpl();
	}
	
	@Bean
	public CampeonatoService campeonatoService() {
		return new CampeonatoServiceImpl();
	}
	
	@Bean
	public PistaService pistaService() {
		return new PistaServiceImpl();
	}
	
	@Bean
	public PilotoService pilotoService() {
		return new PilotoServiceImpl();
	}
	
	@Bean
	public CorridaService corridaService() {
		return new CorridaServiceImpl();
	}
	
	@Bean
	public PilotoCorridaService pilotoCorridaService() {
		return new PilotoCorridaServiceImpl();
	}
}
