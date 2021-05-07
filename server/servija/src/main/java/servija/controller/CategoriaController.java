package servija.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import servija.controller.reqBodies.CategoriaRequest;
import servija.helper.Admin;
import servija.model.Administrador;
import servija.model.Categoria;
import servija.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	CategoriaRepository catRepository;
	@Autowired
	Admin authenticator;
	
	@RequestMapping("/{id}")
	@GetMapping
	public Categoria get(@PathVariable int id) {
		return catRepository.findById(id).orElse(null);
	}
	
	@RequestMapping("/contem/{nome}")
	@GetMapping
	public List<Categoria> get(@PathVariable String nome) {
		return catRepository.findAllByNomeContains(nome);
	}

	@RequestMapping("/")
	@PostMapping
	public Categoria create(@RequestBody CategoriaRequest request) {
		Categoria categoria = new Categoria();
		
		Administrador admin = authenticator.auth(request.token);
		
		if(admin == null)
			return null;
		
		categoria.setAdmin(admin);
		categoria.setCategoriaPai(
			catRepository.findById(request.obj.categoriaPai).get()
		);
		categoria.setNome(request.obj.nome);
		
		catRepository.save(categoria);
		
		return categoria;
	}

	@RequestMapping("/editar/{id}")
	@PostMapping
	public Categoria edit(@PathVariable int id, @RequestBody CategoriaRequest request) {
		Categoria categoria = catRepository.findById(id).get();
		
		Administrador admin = authenticator.auth(request.token);
		
		if(admin == null)
			return null;
		
		categoria.setAdmin(admin);
		categoria.setCategoriaPai(
			catRepository.findById(request.obj.categoriaPai).get()
		);
		categoria.setNome(request.obj.nome);
		
		catRepository.save(categoria);
		
		return categoria;
	}

	@RequestMapping("/deletar/{id}")
	@PostMapping
	public boolean delete(@PathVariable int id, @RequestBody CategoriaRequest request) {
		Categoria categoria = catRepository.findById(id).get();
		if(categoria == null)
			return false;
		
		Administrador admin = authenticator.auth(request.token);

		if(admin == null)
			return false;
		
		catRepository.delete(categoria);
		
		return true;
	}
}
