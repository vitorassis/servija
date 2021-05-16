package servija.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import servija.controller.reqBodies.AnuncianteRequest;
import servija.controller.reqBodies.base.iRequest;
import servija.controller.respBodies.Response;
import servija.helper.CPF;
import servija.helper.User;
import servija.model.Administrador;
import servija.model.Anunciante;
import servija.repository.AnuncianteRepository;

@RestController
@RequestMapping("/anunciante")
public class AnuncianteController {
	@Autowired
	AnuncianteRepository anRep;
	
	@Autowired
	User authenticator;
	
	@RequestMapping("/{id}")
	@GetMapping
	public Response<Anunciante> get(@PathVariable int id){
		return new Response<Anunciante>(true, "Anunciante",
				anRep.findById(id).orElse(null));
	}
	
	@PostMapping
	public Response<Anunciante> create (@RequestBody AnuncianteRequest request){
		Anunciante an = new Anunciante();
		an.setLogin(request.obj.login);
		an.setEmail(request.obj.email);
		an.setCpf(CPF.removeMask(request.obj.cpf));
		an.setEmpresa(request.obj.empresa);
		an.setNome(request.obj.nome);
		an.setSenha(request.obj.senha);
		
		try {
			anRep.save(an);
			
			return new Response<Anunciante>(true, "Anunciante cadastrado com sucesso!", an);
		}catch(Exception e) {
			return new Response<Anunciante>(true, "Falha no cadastro!", null);
		}
	}
	
	@RequestMapping("editar/{id}")
	@PostMapping
	public Response<Anunciante> edit (@PathVariable int id, @RequestBody AnuncianteRequest request){
		Anunciante an = anRep.findById(id).orElse(null);
		if(an == null)
			return new Response<Anunciante>(false, "Anunciante não encontrado!", null);
		
		an = authenticator.authAnunciante(request.token, id);
		if(an == null)
			return new Response<Anunciante>(false, "Anunciante não autorizado!", null);		
		
		an.setLogin(request.obj.login);
		an.setEmail(request.obj.email);
		an.setCpf(CPF.removeMask(request.obj.cpf));
		an.setEmpresa(request.obj.empresa);
		an.setNome(request.obj.nome);
		an.setSenha(request.obj.senha);
		
		try {
			anRep.save(an);
			
			return new Response<Anunciante>(true, "Anunciante alterado com sucesso!", an);
		}catch(Exception e) {
			return new Response<Anunciante>(true, "Falha no cadastro!", null);
		}
	}
	
	@RequestMapping("/deletar/{id}")
	@PostMapping
	public Response<Boolean> delete(@PathVariable int id, @RequestBody AnuncianteRequest request){
		Anunciante an = anRep.findById(id).orElse(null);
		if(an == null)
			return new Response<Boolean>(false, "Anunciante não encontrado!", false);
		
		an = authenticator.authAnunciante(request.token, id);
		if(an == null) {
			Administrador ad = authenticator.authAdmin(request.token);
			if(ad == null)
				return new Response<Boolean>(false, "Anunciante não autorizado!", false);		
			
			an = anRep.findById(id).get();
		}
		
		try {
			
			anRep.delete(an);
			
			return new Response<Boolean>(true, "Removido com sucesso", true);
		}catch(Exception e) {
			return new Response<Boolean>(false, "Erro na exclusão", false);
		}
	}
}
