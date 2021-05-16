package servija.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import servija.controller.respBodies.Response;
import servija.model.Estado;
import servija.repository.EstadoRepository;


@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository estRepository;
	
	@RequestMapping("/listar")
	@GetMapping
	public Response<List<Estado>> get() {
		return new Response<List<Estado>>(true, "Estados", estRepository.findAll(Sort.by(Sort.Direction.ASC, "nome")));
	}
	
	@RequestMapping("/atualizar")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Response<List<Estado>> refresh() {
		Response<List<Estado>> response = new Response<List<Estado>>(false, "Erro na atuallização!", null);
		
		ArrayList<Estado> estados = servija.helper.Estado.fetchAll();
		
		try {
			estRepository.saveAll(estados);
		}catch(Exception ex) {}

		if(estRepository.count() == estados.size()) {
			response.message="Atualizado com sucesso!";
			response.success=true;
			response.obj=estRepository.findAll();
		}
		return response;
	}
	
}
