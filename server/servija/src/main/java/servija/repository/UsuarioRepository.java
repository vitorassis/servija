package servija.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import servija.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	Usuario getUsuarioByLoginAndSenha(String login, String senha);
	Usuario getUsuarioByEmailAndSenha(String email, String senha);
	Usuario getUsuarioBySenha(String senha);
}
