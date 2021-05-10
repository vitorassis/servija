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
import servija.controller.respBodies.Response;
import servija.helper.User;
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
	User authenticator;
	@Autowired
	CategoriaRepository catRepository;

	@RequestMapping("/{id}")
	@GetMapping
	public Response<TipoServico> get(@PathVariable int id) {
		return new Response<TipoServico>(true, "Tipo de Serviço", tpRepository.findById(id).orElse(null));
	}
	
	@RequestMapping("/contem/{nome}")
	@GetMapping
	public Response<List<TipoServico>> get(@PathVariable String nome) {
		return new Response<List<TipoServico>>(true, "Tipo de Serviço com '"+nome+"'",tpRepository.getAllByNomeContains(nome));
	}
	
	@RequestMapping("/")
	@PostMapping
	public Response<TipoServico> create(@RequestBody TipoServicoRequest request) {
		TipoServico tpServico = new TipoServico();
		
		Administrador admin = authenticator.authAdmin(request.token);
		if(admin == null)
			return new Response<TipoServico>(false, "Usuário não autorizado!", null);
		
		tpServico.setAdmin(admin);
		
		try {
			tpServico.setCategoria(
				catRepository.findById(request.obj.categoria).get()
			);
		}catch(Exception ex) {
			return new Response<TipoServico>(false, "Categoria não encontrada!", null);
		}
		
		tpServico.setDescricao(request.obj.descricao);
		tpServico.setNome(request.obj.nome);
		
		try {
			tpRepository.save(tpServico);
		}catch(Exception ex) {
			return new Response<TipoServico>(false, "Falha na criação!", null);
		}
		return new Response<TipoServico>(true, "Tipo de Serviço salvo!", tpServico);
	}
	
	@RequestMapping("/editar/{id}")
	@PostMapping
	public Response<TipoServico> edit(@PathVariable int id, @RequestBody TipoServicoRequest request) {
		TipoServico tp = tpRepository.findById(id).orElse(null);
		if(tp == null)
			return new Response<TipoServico>(false, "Tipo de Serviço não encontrado!", null);
		
		Administrador admin = authenticator.authAdmin(request.token);
		if(admin == null)
			return new Response<TipoServico>(false, "Usuário não autorizado!", null);
		
		
		tp.setAdmin(admin);
		try {
			tp.setCategoria(
				catRepository.findById(request.obj.categoria).get()
			);
		}catch(Exception ex) {
			return new Response<TipoServico>(false, "Categoria não encontrada!", null);
		}
		tp.setDescricao(request.obj.descricao);
		tp.setNome(request.obj.nome);
		
		try {
			tpRepository.save(tp);
		}catch(Exception ex) {
			return new Response<TipoServico>(false, "Falha na edição!", null);
		}
		
		return new Response<TipoServico>(true, "Tipo de Serviço editado!", tp);
	}

	@RequestMapping("/deletar/{id}")
	@PostMapping
	public Response<Boolean> delete(@PathVariable int id, @RequestBody TipoServicoRequest request) {
		TipoServico tp = tpRepository.findById(id).orElse(null);
		if(tp == null)
			return new Response<Boolean>(false, "Tipo de Serviço não encontrado!", false);
		
		Administrador admin = authenticator.authAdmin(request.token);
		if(admin == null)
			return new Response<Boolean>(false, "Usuário não autorizado!", false);
		
		tpRepository.delete(tp);
		
		return new Response<Boolean>(true, "Tipo de Serviço removido!", true);
		
	}
}

