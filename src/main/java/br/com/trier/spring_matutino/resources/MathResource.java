package br.com.trier.spring_matutino.resources;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/calc")
public class MathResource {
	
	@GetMapping("/somar")
	public Integer somar (@RequestParam ("n1") Integer n1, @RequestParam ("n2") Integer n2) {
		return n1+n2;
	}

	@GetMapping("/sub")
	public Integer subtrai (@RequestParam ("n1") Integer n1, @RequestParam("n2") Integer n2) {
		return n1-n2;
	}
	
	@GetMapping("/mult/{n1}/{n2}")
	public Integer mult (@PathVariable (name = "n1") Integer n1, @PathVariable (name = "n2") Integer n2) {
		return n1*n2;
	}
	
	@GetMapping("/div/{n1}/{n2}")
	public Integer div (@PathVariable (name = "n1") Integer n1, @PathVariable (name = "n2") Integer n2) {
		return n1/n2;
	}
}
