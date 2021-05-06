package servija.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import servija.helper.MD5;

@Entity
@Table(name="usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(unique = true)
	private String login;
	
	@Column(unique = true)
	private String email;
	
	@Column(name="passwd", length = 32)
	@JsonIgnore
	private String senha;
	
	@Transient
	private boolean admin;
	
	@Transient
	private String token;
	
	public Usuario(String login, String email, String senha) {
		this();
		this.login = login;
		this.email = email;
		this.senha = senha;
	}
	
	public Usuario() {
	}


	
	//==========================================
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		if(senha.length() != 32)
			this.senha = MD5.hash(senha);
		else
			this.senha = senha;
		
		this.token = this.senha;
	}
	
	public String getToken() {
		return this.token;
	}
	
	public void setToken() {
		this.token = this.senha;
	}
	
	public boolean isAdmin() {
		return admin;
	}
	
	public void setAdmin(boolean b) {
		this.admin = b;
	}
}
