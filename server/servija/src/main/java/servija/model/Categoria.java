package servija.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "categorias")
public class Categoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="admin_id", referencedColumnName = "admin_id")
	@JsonIgnore
	private Administrador admin;
	
	private String nome;
	
	@ManyToOne
	@JoinColumn(name="catpai_id", referencedColumnName = "id")
	private Categoria categoriaPai;
	

	public Categoria() {
		super();
	}

	public Categoria(int id, Administrador administrador, Categoria categoriaPai) {
		super();
		this.id = id;
		this.admin = administrador;
		this.categoriaPai = categoriaPai;
	}

	
	
	//=========================================================
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Administrador getAdmin() {
		return admin;
	}

	public void setAdmin(Administrador administrador) {
		this.admin = administrador;
	}

	public Categoria getCategoriaPai() {
		return categoriaPai;
	}

	public void setCategoriaPai(Categoria categoriaPai) {
		this.categoriaPai = categoriaPai;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
