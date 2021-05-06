package servija.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="admins")
@PrimaryKeyJoinColumn(name = "admin_id")
public class Administrador extends Usuario{
	
	
	public Administrador(String login, String email, String senha) {
		super(login, email, senha);
	}



	public Administrador() {
		super();
		this.setAdmin(true);
	}

}
