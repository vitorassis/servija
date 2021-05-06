package servija.controller;

import java.util.HashSet;

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

import servija.controller.reqBodies.LocalRequest;
import servija.controller.reqBodies.base.iRequest;
import servija.helper.Admin;
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
	Admin authenticator;

	@RequestMapping("/{id}")
	@GetMapping
	public Localidade get(@PathVariable int id) {
		Localidade local = new Localidade();
		
		local = locRepository.findById(id).get();
		
		
		return local;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Localidade create(@RequestBody LocalRequest request) {
		Localidade local = new Localidade();
		Administrador admin = authenticator.auth(request.token);

		if(admin == null)
			return null;
		
		locRepository.save(local);
		
		local.setCidadePrincipal(cidRepository.findById(request.obj.cidadePrincipal).get());
		local.setAdmin(admin);
		for(int i=0; i< request.obj.cidades.length; i++) 
			local.addCidade(
					cidRepository.findById(request.obj.cidades[i]).get()
			);
		
		
		cidRepository.saveAll(local.getCidades());
		locRepository.save(local);
		
		return local;
	}
	
	@RequestMapping("/editar/{id}")
	@PostMapping
	public Localidade edit(@PathVariable int id, @RequestBody LocalRequest request) {
		Localidade local = locRepository.findById(id).get();
		
		Administrador admin = authenticator.auth(request.token);

		if(admin == null)
			return null;
		
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
		
		return local;
	}

	@RequestMapping("/deletar/{id}")
	@DeleteMapping
	public boolean delete(@PathVariable int id) {
		Localidade local = locRepository.findById(id).get();
		if(local == null)
			return false;
		
		for (Cidade cidade : local.getCidades())
			cidade.setLocalidade(null);
		cidRepository.saveAll(local.getCidades());
		
		locRepository.delete(local);
		return true;
	}

	@RequestMapping("/consulta/cep/{cep}")
	@GetMapping
	public Localidade get(@PathVariable String cep) {
		Localidade local = new Localidade();
		
		if(cep == null || cep.trim().replaceAll("-", "").length() != 8)
			return null;
		
		cep = cep.trim().replaceAll("-", "");
		
		JSONObject json =  new JSONObject(ExternalJSONReader.access("https://viacep.com.br/ws/"+cep+"/json/"));
		
		Cidade cidade = cidRepository.getCidadeByNome(json.getString("localidade"));
		
		local = cidade.getLocalidade();
		
		return local;
	}
}
