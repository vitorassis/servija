package servija.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import servija.model.Cidade;
import servija.model.Estado;
import servija.repository.CidadeRepository;
import servija.repository.EstadoRepository;

@RestController
@RequestMapping("/cidades")
public class CidadeController {
	@Autowired
	private CidadeRepository cidRepository;
	@Autowired
	private EstadoRepository estRepository;
	
	@GetMapping
	public List<Cidade> get() {
		return cidRepository.findAll();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public boolean refresh() {
		
		ArrayList<Estado> estados = (ArrayList) estRepository.findAll();
		
		boolean sucesso = true;
		
		for (Estado estado : estados) {
			ArrayList<Cidade> cidades = servija.helper.Cidade.fetch(estado);
			
			for (Cidade cidade : cidades) {
				if(!cidRepository.existsCidadeByNomeAndEstado(cidade.getNome(), cidade.getEstado()))
					cidRepository.save(cidade);
			}
			
			sucesso &= (cidRepository.count() == cidades.size());
		}
		
		return sucesso;
	}
}
