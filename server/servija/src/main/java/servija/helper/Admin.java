package servija.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import servija.model.Administrador;
import servija.model.Usuario;
import servija.repository.AdminRepository;
import servija.repository.UsuarioRepository;

@Component
public class Admin {

	@Autowired
	UsuarioRepository usuRepository;
	@Autowired
	AdminRepository adRepository;
	
	public Administrador auth(String token) {
		Administrador admin = new Administrador();
		if(token == null)
			return null;
		
		Usuario usuario = usuRepository.getUsuarioBySenha(token);
		
		if(usuario != null && usuario.isAdmin()) 
			admin = adRepository.getOne(usuario.getId());
		else
			admin = null;
			
		return admin;
	}
}
