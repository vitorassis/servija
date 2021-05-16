package servija.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name="anunciantes")
@PrimaryKeyJoinColumn(name = "usuario_id")
public class Anunciante extends Usuario{
	
	@Column(nullable=false)
	private String nome;
	
	@Column
	private String empresa;
	
	@Column(nullable=false, unique=true, length = 11)
	private String cpf;
	
	@OneToMany(mappedBy = "anunciante")
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonManagedReference
	private Set<Anuncio> anuncios;
	

	public Set<Anuncio> getAnuncios() {
		return anuncios;
	}

	public void setAnuncios(Set<Anuncio> anuncios) {
		this.anuncios = anuncios;
	}

	public Anunciante(String login, String email, String senha, String nome, String empresa, String cpf) {
		super(login, email, senha);
		this.nome = nome;
		this.empresa = empresa;
		this.cpf = cpf;
	}
	
	public Anunciante() {}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
}
