package servija.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tiposServico")
public class TipoServico {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="admin_id", referencedColumnName = "admin_id")
	@JsonIgnore
	private Administrador admin;

	private String nome;
	
	private String descricao;
	
	@ManyToOne
	@JoinColumn(name="categoria_id", referencedColumnName = "id")
	private Categoria categoria;
	

	public TipoServico() {
		super();
	}

	public TipoServico(int id, Administrador admin, Categoria categoria, String nome, String descricao) {
		super();
		this.id = id;
		this.admin = admin;
		this.categoria = categoria;
		this.nome = nome;
		this.descricao = descricao;
	}

	
	//==============================================================
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Administrador getAdmin() {
		return admin;
	}

	public void setAdmin(Administrador admin) {
		this.admin = admin;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}
