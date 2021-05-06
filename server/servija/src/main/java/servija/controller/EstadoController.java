package servija.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import servija.model.Estado;
import servija.repository.EstadoRepository;


@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository estRepository;
	
	@GetMapping
	public List<Estado> get() {
		return estRepository.findAll();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public List<Estado> refresh() {
		ArrayList<Estado> estados = servija.helper.Estado.fetchAll();
		
		for (Estado estado : estados) {
			if(!estRepository.existsEstadoByNome(estado.getNome()))
			estRepository.save(estado);
		}
		
		if(estRepository.count() == estados.size())
			return estRepository.findAll();
		return null;
	}
	
}
