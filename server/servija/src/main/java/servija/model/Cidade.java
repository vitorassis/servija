package servija.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cidades")
public class Cidade {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToOne
	@JoinColumn(name="estado_id", referencedColumnName = "id")
	private Estado estado;
	
/*	@OneToOne
	@JoinColumn(name="localidade_id", referencedColumnName = "id")
	@Column(nullable = true)
	@Getter @Setter private Localidade localidade;
*/	
	@Column
	@Getter @Setter private String nome;
	
	public Cidade(Estado estado, String nome) {
		this.estado = estado;
	//	this.localidade = null;
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
	
	
}
