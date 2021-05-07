package servija.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import servija.repository.AnuncioRepository;

@RestController
@RequestMapping("/anuncios")
public class AnuncioController {
	
	@Autowired
	AnuncioRepository anRepository;
	
	@GetMapping
	public String index() {
		return "DEPOIS EU FAÇO";
	}
	
}
