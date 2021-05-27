package servija.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import servija.model.Usuario;
import servija.controller.reqBodies.UsuarioRequest;
import servija.controller.respBodies.Response;
import servija.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
public class UsuarioController {

	@Autowired
	UsuarioRepository usuRepository;
	
	@PostMapping
	@CrossOrigin(origins = "http://localhost:3000")
	@RequestMapping("/login")
	public Response<Usuario> login(@RequestBody UsuarioRequest request) {
		Usuario usuario = new Usuario(request.login, null, request.senha);
		
		Response<Usuario> response = new Response<Usuario>(false, "Falha no login!", null);
		
		usuario.setSenha(request.senha);
		
		response.obj = usuRepository.getUsuarioByLoginAndSenha(usuario.getLogin(), usuario.getSenha());
		if(response.obj == null)
			response.obj = usuRepository.getUsuarioByEmailAndSenha(usuario.getLogin(), usuario.getSenha());
		
		if(response.obj != null) {
			response.obj.setToken();
			response.success = true;
			response.message = "Logado com sucesso!";
		}
		
		return response;
	}
}
