package servija.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import net.bytebuddy.asm.Advice.Local;
import servija.controller.reqBodies.LocalRequest;
import servija.controller.reqBodies.base.iRequest;
import servija.controller.respBodies.Response;
import servija.helper.User;
import servija.helper.ExternalJSONReader;
import servija.model.Administrador;
import servija.model.Cidade;
import servija.model.Localidade;
import servija.model.Usuario;
import servija.repository.AdminRepository;
import servija.repository.CidadeRepository;
import servija.repository.LocalidadeRepository;
import servija.repository.UsuarioRepository;

@RestController
@RequestMapping("/localidades")
public class LocalidadeController {
	
	@Autowired
	LocalidadeRepository locRepository;
	@Autowired
	CidadeRepository cidRepository;
	@Autowired
	User authenticator;
	
	@RequestMapping("/listar")
	@GetMapping
	public Response<List<Localidade>> get(){
		return new Response<List<Localidade>>(true, "Localidade", locRepository.findAllOrderByEstado());
	}

	@RequestMapping("/{id}")
	@GetMapping
	public Response<Localidade> get(@PathVariable int id) {
		return new Response<Localidade>(true, "Localidade", locRepository.findById(id).orElse(null));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Response<Localidade> create(@RequestBody LocalRequest request) {
		Localidade local = new Localidade();
		Administrador admin = authenticator.authAdmin(request.token);

		if(admin == null)
			return new Response<Localidade>(false, "Usuário não autorizado", null);
		
		locRepository.save(local);
		
		local.setCidadePrincipal(cidRepository.findById(request.obj.cidadePrincipal).get());
		local.setAdmin(admin);
		for(int i=0; i< request.obj.cidades.length; i++) 
			local.addCidade(
					cidRepository.findById(request.obj.cidades[i]).get()
			);
		
		
		cidRepository.saveAll(local.getCidades());
		locRepository.save(local);
		
		return new Response<Localidade>(true, "Localidade criada!", local);
	}
	
	@RequestMapping("/editar/{id}")
	@PostMapping
	public Response<Localidade> edit(@PathVariable int id, @RequestBody LocalRequest request) {
		Administrador admin = authenticator.authAdmin(request.token);
		if(admin == null)
			return new Response<Localidade>(false, "Usuário não autorizado", null);
		
		Localidade local = locRepository.findById(id).orElse(null);
		if(local == null)
			return new Response<Localidade>(false, "Localidade não encontrada!", null);
		
		local.setCidadePrincipal(
			cidRepository.findById(request.obj.cidadePrincipal).get()	
		);
		local.setAdmin(admin);
		local.clearCidades();
		for(int i=0; i< request.obj.cidades.length; i++) 
			local.addCidade(
					cidRepository.findById(request.obj.cidades[i]).get()
			);

		cidRepository.saveAll(local.getCidades());
		locRepository.save(local);
		
		return new Response<Localidade>(true, "Localidade alterada!", local);
	}

	@RequestMapping("/deletar/{id}")
	@DeleteMapping
	public Response<Boolean> delete(@PathVariable int id, @RequestBody LocalRequest request) {
		Localidade local = locRepository.findById(id).orElse(null);
		if(local == null)
			return new Response<Boolean>(false, "Localidade não encontrada!", false);
		
		Administrador admin = authenticator.authAdmin(request.token);

		if(admin == null)
			return new Response<Boolean>(false, "Usuário não autorizado", false);
		
		
		for (Cidade cidade : local.getCidades())
			cidade.setLocalidade(null);
		cidRepository.saveAll(local.getCidades());
		
		locRepository.delete(local);
		return new Response<Boolean>(true, "Localidade removida!", true);
	}

	@RequestMapping("/consulta/cep/{cep}")
	@GetMapping
	public Response<Localidade> get(@PathVariable String cep) {
		Localidade local = new Localidade();
		
		if(cep == null || cep.trim().replaceAll("-", "").length() != 8)
			return new Response<Localidade>(false, "CEP incorreto!", null);
		
		cep = cep.trim().replaceAll("-", "");
		
		JSONObject json =  new JSONObject(ExternalJSONReader.access("https://viacep.com.br/ws/"+cep+"/json/"));
		
		Cidade cidade = cidRepository.getCidadeByNome(json.getString("localidade"));
		
		local = cidade.getLocalidade();
		
		return new Response<Localidade>(local != null,
				local== null ? "Cidade sem localidade!": "Localidade encontrada!", local);
	}
}
