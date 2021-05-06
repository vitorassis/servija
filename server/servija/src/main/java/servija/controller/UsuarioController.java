package servija.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import servija.model.Usuario;
import servija.controller.reqBodies.UsuarioRequest;
import servija.repository.UsuarioRepository;

@RestController
public class UsuarioController {

	@Autowired
	UsuarioRepository usuRepository;
	
	@PostMapping
	@RequestMapping("/login")
	public Usuario login(@RequestBody UsuarioRequest request) {
		Usuario usuario = new Usuario(request.login, null, request.senha);
		
		usuario.setSenha(request.senha);
		Usuario logado = new Usuario();
		
		logado = usuRepository.getUsuarioByLoginAndSenha(usuario.getLogin(), usuario.getSenha());
		if(logado == null)
			logado = usuRepository.getUsuarioByEmailAndSenha(usuario.getLogin(), usuario.getSenha());
		
		if(logado != null)
			logado.setToken();
		
		return logado;
	}
}
