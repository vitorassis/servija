package servija.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import servija.controller.reqBodies.CategoriaRequest;
import servija.controller.respBodies.Response;
import servija.helper.User;
import servija.model.Administrador;
import servija.model.Categoria;
import servija.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

	@Autowired
	CategoriaRepository catRepository;
	@Autowired
	User authenticator;
	
	@RequestMapping("/{id}")
	@GetMapping
	public Response<Categoria> get(@PathVariable int id) {
		return new Response<Categoria>(true, "Categoria", catRepository.findById(id).orElse(null));
	}
	
	@RequestMapping("/contem/{nome}")
	@GetMapping
	public Response<List<Categoria>> get(@PathVariable String nome) {
		return new Response<List<Categoria>>(true, "Categorias contem '"+nome+"'", catRepository.findAllByNomeContains(nome));
	}

	@RequestMapping("/")
	@PostMapping
	public Response<Categoria> create(@RequestBody CategoriaRequest request) {
		Categoria categoria = new Categoria();
		
		Administrador admin = authenticator.authAdmin(request.token);
		
		if(admin == null)
			return new Response<Categoria>(false, "Usuário não autorizado!", null);
		
		Categoria catPai = null;
		try {
			catPai = request.obj.categoriaPai != 0 ?
				catRepository.findById(request.obj.categoriaPai).get() :
					null;
		}catch(Exception ex) {
			return new Response<Categoria>(false, "Categoria pai não encontrada!", null);
		}
		
		categoria.setAdmin(admin);
		categoria.setCategoriaPai(
			catPai
		);
		categoria.setNome(request.obj.nome);
		
		try {
			catRepository.save(categoria);
		}catch(Exception ex) {
			return new Response<Categoria>(false, "Erro ao gravar a categoria!", categoria);
		}
		return new Response<Categoria>(true, "Categoria salva!", categoria);
	}

	@RequestMapping("/editar/{id}")
	@PostMapping
	public Response<Categoria> edit(@PathVariable int id, @RequestBody CategoriaRequest request) {
		Categoria categoria = catRepository.findById(id).orElse(null);
		if(categoria == null)
			return new Response<Categoria>(false, "Categoria não encontrada!", null);
		
		Administrador admin = authenticator.authAdmin(request.token);
		
		if(admin == null)
			return new Response<Categoria>(false, "Usuário não autorizado!", null);
		
		Categoria catPai = null;
		try {
			catPai = request.obj.categoriaPai != 0 ?
				catRepository.findById(request.obj.categoriaPai).get() :
					null;
		}catch(Exception ex) {
			return new Response<Categoria>(false, "Categoria pai não encontrada!", null);
		}
		categoria.setAdmin(admin);
		categoria.setCategoriaPai(
				catPai
		);
		categoria.setNome(request.obj.nome);

		try {
			catRepository.save(categoria);
		}catch(Exception ex) {
			return new Response<Categoria>(false, "Erro ao gravar a categoria!", categoria);
		}		
		return new Response<Categoria>(true, "Categoria editada!", categoria);
	}

	@RequestMapping("/deletar/{id}")
	@DeleteMapping
	public Response<Boolean> delete(@PathVariable int id, @RequestBody CategoriaRequest request) {
		Categoria categoria = catRepository.findById(id).orElse(null);
		if(categoria == null)
			return new Response<Boolean>(false, "Categoria não encontrada!", false);
		
		Administrador admin = authenticator.authAdmin(request.token);

		if(admin == null)
			return new Response<Boolean>(false, "Administrador não autorizado!", false);
		
		catRepository.delete(categoria);
		
		return new Response<Boolean>(true, "Categoria removida!", true);
	}
}
