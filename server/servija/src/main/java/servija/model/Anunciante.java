package servija.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

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
