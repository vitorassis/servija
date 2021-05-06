package servija.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@RequestMapping("/listar")
	@GetMapping
	public List<Cidade> get() {
		return cidRepository.findAll();
	}
	
	@RequestMapping("/{id}")
	@GetMapping
	public Cidade get(@PathVariable(value = "id") int id) {
		return cidRepository.findById(id).orElse(null);
	}

	@RequestMapping("/estado/{sigla}")
	@GetMapping
	public List<Cidade> get(@PathVariable(value="sigla") String sigla){
		Estado estado = estRepository.getEstadoBySigla(sigla);
		
		return cidRepository.getCidadeByEstado(estado, Sort.by(Sort.Direction.ASC, "nome"));
	}
	
	@RequestMapping("/atualizar")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public boolean refresh() {
		
		ArrayList<Estado> estados = (ArrayList) estRepository.findAll();
		
		boolean sucesso = true;
		
		for (Estado estado : estados) {
			ArrayList<Cidade> cidades = servija.helper.Cidade.fetch(estado);
			
			cidRepository.saveAll(cidades);
			
			sucesso &= (cidRepository.count() == cidades.size());
		}
		
		return sucesso;
	}
	
	@RequestMapping("/atualizar/estado/{siglaUF}")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public List<Cidade> refresh(@PathVariable(value="siglaUF") String siglaUF) {
		
		Estado estado = estRepository.getEstadoBySigla(siglaUF);
		
		boolean sucesso = true;
		
		ArrayList<Cidade> cidades = servija.helper.Cidade.fetch(estado);
		
		cidRepository.saveAll(cidades);	
				
		if(cidades.size() == cidRepository.getCidadeByEstado(estado).size())
			return cidRepository.getCidadeByEstado(estado);
		return null;
	}
}
