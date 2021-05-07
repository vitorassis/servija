package servija.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import servija.controller.reqBodies.TipoServicoRequest;
import servija.helper.Admin;
import servija.model.Administrador;
import servija.model.TipoServico;
import servija.repository.CategoriaRepository;
import servija.repository.TipoServicoRepository;

@RestController
@RequestMapping("/tiposservico")
public class TipoServicoController {
	
	@Autowired
	TipoServicoRepository tpRepository;
	@Autowired
	Admin authenticator;
	@Autowired
	CategoriaRepository catRepository;

	@RequestMapping("/{id}")
	@GetMapping
	public TipoServico get(@PathVariable int id) {
		return tpRepository.findById(id).orElse(null);
	}
	
	@RequestMapping("/nome/contem/{nome}")
	@GetMapping
	public List<TipoServico> get(@PathVariable String nome) {
		return tpRepository.getAllByNomeContains(nome);
	}
	
	@RequestMapping("/")
	@PostMapping
	public TipoServico create(@RequestBody TipoServicoRequest request) {
		TipoServico tpServico = new TipoServico();
		
		Administrador admin = authenticator.auth(request.token);
		if(admin == null)
			return null;
		
		tpServico.setAdmin(admin);
		tpServico.setCategoria(
			catRepository.findById(request.obj.categoria).get()
		);
		tpServico.setDescricao(request.obj.descricao);
		tpServico.setNome(request.obj.nome);
		
		tpRepository.save(tpServico);
		
		return tpServico;
	}
	
	@RequestMapping("/editar/{id}")
	@PostMapping
	public TipoServico edit(@PathVariable int id, @RequestBody TipoServicoRequest request) {
		TipoServico tp = tpRepository.findById(id).get();
		
		Administrador admin = authenticator.auth(request.token);
		if(admin == null)
			return null;
		
		
		tp.setAdmin(admin);
		tp.setCategoria(
			catRepository.findById(request.obj.categoria).get()
		);
		tp.setDescricao(request.obj.descricao);
		tp.setNome(request.obj.nome);
		
		tpRepository.save(tp);
		
		return tp;
	}

	@RequestMapping("/deletar/{id}")
	@PostMapping
	public boolean delete(@PathVariable int id, @RequestBody TipoServicoRequest request) {
		TipoServico tp = tpRepository.findById(id).get();
		if(tp == null)
			return false;
		
		Administrador admin = authenticator.auth(request.token);
		if(admin == null)
			return false;
		
		tpRepository.delete(tp);
		
		return true;
		
	}
}

