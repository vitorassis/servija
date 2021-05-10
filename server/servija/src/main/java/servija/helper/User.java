package servija.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import servija.model.Administrador;
import servija.model.Anunciante;
import servija.model.Usuario;
import servija.repository.AdminRepository;
import servija.repository.AnuncianteRepository;
import servija.repository.UsuarioRepository;

@Component
public class User {

	@Autowired
	UsuarioRepository usuRepository;
	@Autowired
	AdminRepository adRepository;
	@Autowired
	AnuncianteRepository anRepository;
	
	public Administrador authAdmin(String token) {
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
	
	public Anunciante authAnunciante(String token, int checkId) {
		Anunciante anunc = new Anunciante();
		if(token == null)
			return null;
		
		Usuario usuario = usuRepository.getUsuarioBySenha(token);
		
		if(usuario != null) 
			anunc = anRepository.getOne(usuario.getId());
		else
			anunc = null;
			
		return anunc != null && anunc.getId() == checkId ? anunc : null;
	}
}
