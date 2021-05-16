package servija.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="localidades")
public class Localidade {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToOne
	@JoinColumn(name="cidadeprinc_id", referencedColumnName = "id", nullable=true)
	@JsonManagedReference
	private Cidade cidadePrincipal;
	
	@OneToMany(mappedBy = "localidade")
	@JsonManagedReference
	private Set<Cidade> cidades;
	
	@ManyToOne
	@JoinColumn(name="admin_id", referencedColumnName = "admin_id")
	@JsonIgnore
	private Administrador admin;
	
	
	public Localidade() {
		this.cidades = new HashSet<Cidade>();
		this.cidadePrincipal = null;
	}
	
	public Localidade(Administrador admin, Cidade cidadePrincipal) {
		this();
		this.admin = admin;
		this.cidadePrincipal = cidadePrincipal;
	}
	
	public void addCidade(Cidade nova) {
		if(!this.cidades.contains(nova)) {
			nova.setLocalidade(this);
			this.cidades.add(nova);
		}
	}
	
	public void clearCidades() {
		this.setCidades(new HashSet<Cidade>());
	}
	
	//=================================

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Cidade getCidadePrincipal() {
		return cidadePrincipal;
	}

	public void setCidadePrincipal(Cidade cidadePrincipal) {
		this.cidadePrincipal = cidadePrincipal;
	}

	public Set<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(Set<Cidade> cidades) {
		this.cidades = cidades;
	}

	public Administrador getAdmin() {
		return admin;
	}

	public void setAdmin(Administrador admin) {
		this.admin = admin;
	}
	
}