package servija.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "cidades",
	    uniqueConstraints=
	        @UniqueConstraint(columnNames={"estado_id", "nome"})
)
public class Cidade {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name="estado_id", referencedColumnName = "id")
	private Estado estado;
	
	@ManyToOne
	@JoinColumn(name="localidade_id", referencedColumnName = "id")
	@JsonBackReference
	private Localidade localidade;
	
	@Column
	private String nome;
	
	public Cidade(Estado estado, String nome) {
		this.estado = estado;
		this.localidade = null;
		this.nome = nome;
	}
	
	public Cidade() {	}


	
	//=====================================================================
	
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setLocalidade(Localidade localidade) {
		this.localidade = localidade;
	}
	
	public Localidade getLocalidade() {
		return this.localidade;
	}
	
}
