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

import servija.controller.respBodies.Response;
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
	public Response<List<Cidade>> get() {
		return new Response<List<Cidade>>(true, "Cidades", cidRepository.findAll());
	}
	
	@RequestMapping("/{id}")
	@GetMapping
	public Response<Cidade> get(@PathVariable(value = "id") int id) {
		return new Response<Cidade>(true, "Cidades", cidRepository.findById(id).orElse(null));
	}

	@RequestMapping("/estado/{sigla}")
	@GetMapping
	public Response<List<Cidade>> get(@PathVariable(value="sigla") String sigla){
		Response<List<Cidade>> response = new Response<List<Cidade>>(false, "Estado não encontrado", null);
		
		Estado estado = estRepository.getEstadoBySigla(sigla);
		if(estado == null)
			return response;
		
		return new Response<List<Cidade>>(true, "Cidades de "+sigla.toUpperCase(),cidRepository.getCidadeByEstado(estado, Sort.by(Sort.Direction.ASC, "nome")));
	}
	
	@RequestMapping("/atualizar")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Response<List<Cidade>> refresh() {		
		ArrayList<Estado> estados = (ArrayList) estRepository.findAll();
		ArrayList<Cidade> alCidades = new ArrayList<Cidade>();
		
		boolean sucesso = true;
		
		for (Estado estado : estados) {
			ArrayList<Cidade> cidades = servija.helper.Cidade.fetch(estado);
			alCidades.addAll(cidades);
			try {
				cidRepository.saveAll(cidades);
			}catch(Exception ex) {}
			sucesso &= (cidRepository.getCidadeByEstado(estado).size() == cidades.size());
		}
		
		return new Response<List<Cidade>>(sucesso, 
				sucesso ? "Atualizado com sucesso!" : "Falha na atualização", 
				sucesso ? alCidades : null);
	}
	
	@RequestMapping("/atualizar/estado/{sigla}")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Response<List<Cidade>> refresh(@PathVariable(value="sigla") String sigla) {
		
		Estado estado = estRepository.getEstadoBySigla(sigla);
		if(estado == null)
			return new Response<List<Cidade>>(false, "Estado não encontrado", null);
		
		ArrayList<Cidade> cidades = servija.helper.Cidade.fetch(estado);
		
		try {
			cidRepository.saveAll(cidades);
		}catch(Exception ex) {}
				
		if(cidades.size() == cidRepository.getCidadeByEstado(estado).size())
			return new Response<List<Cidade>>(true, "Cidades de "+sigla.toUpperCase()+" atualizadas!", cidRepository.getCidadeByEstado(estado));
		return new Response<List<Cidade>>(false, "Erro na atualização das cidades de "+sigla.toUpperCase(), null);
	}
}
